/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import utility.CallbackOnReceiveHandler;
import utility.GeneralConstants;

/**
 *
 * @author fadia
 */
public class CommunicationLink extends Thread {

    private CallbackOnReceiveHandler callBackHandler;
    private Socket s;
    private String id;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    /**
     * @return the id
     */
    private CommunicationLink(CallbackOnReceiveHandler callBackHandler, String id, Socket s) {
        this.s = s;
        this.callBackHandler = callBackHandler;
        this.id = id;
        try {
            this.oos = new ObjectOutputStream(s.getOutputStream());
            this.ois = new ObjectInputStream(s.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(CommunicationLink.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    //CommunicationLink factory to run code after object construction
    public static CommunicationLink generateCommunicationLink(CallbackOnReceiveHandler callBackHandler, String id, Socket s) {
        CommunicationLink cl = new CommunicationLink(callBackHandler, id, s);
        cl.start();
        return cl;
    }

    @Override
    public void run() {
        while (true) {
            HashMap<String, String> received;
            try {
                received = (HashMap<String, String>) ois.readObject();
                callBackHandler.handleReceivedData(received);
            } catch (IOException ex) {
                Logger.getLogger(CommunicationLink.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(CommunicationLink.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void send(HashMap<String, String> msg) {
        try {
            oos.writeObject(msg);
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(CommunicationLink.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getCommunicationLinkId() {
        return id;
    }
}
