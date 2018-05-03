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
import utility.Constants;

/**
 *
 * @author fadia
 */
public class CommunicationLink extends Thread {

    private CallbackOnReceiveHandler callBackHandler;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    /**
     * @return the id
     */
    private CommunicationLink(CallbackOnReceiveHandler callBackHandler, Socket s) {
        this.callBackHandler = callBackHandler;
        try {
            this.oos = new ObjectOutputStream(s.getOutputStream());
            this.ois = new ObjectInputStream(s.getInputStream());
        } catch (Exception ex) {
        }

    }

    //CommunicationLink factory to run code after object construction
    public static CommunicationLink generateCommunicationLink(CallbackOnReceiveHandler callBackHandler, Socket s) {
        CommunicationLink cl = new CommunicationLink(callBackHandler, s);
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
            } catch (Exception ex) {
                HashMap<String, String> message = new HashMap();
                message.put(Constants.REPLYTYPEATTR, Constants.CONNECTIONCLOSED);
                callBackHandler.handleReceivedData(message);
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
}
