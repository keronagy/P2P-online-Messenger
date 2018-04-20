/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.HashMap;
import network.CommunicationLink;
import utility.*;

/**
 *
 * @author Islam
 */
public class PeerClient extends Client implements CallbackOnReceiveHandler {
    
    public PeerClient(String status, String name) {
            super("", status, name);
        try {
            Socket s = new Socket(ServerConstants.SERVERIP, ServerConstants.SERVERPORT);
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            this.id = ois.readUTF();
            this.cl = CommunicationLink.generateCommunicationLink(this, id, s);
        } catch (Exception ex) {
            System.out.println("Connection failed");
        }

    }
    public String createRoom(String name)
    {
        return "";
    }
    public void joinRoom(String roomID)
    {
        
    }
    public void sendMessageToRoom(String roomID,String msg){
        HashMap<String,String> message = new HashMap();
        message.put(GeneralConstants.REQUESTTYPEATTR, ClientConstants.ROOMSENDMESSAGE);
        message.put(GeneralConstants.ROOMIDATTR, roomID);
        message.put(GeneralConstants.CLIENTIDATTR, this.id);
        message.put(MessageConstants.MESSAGE, msg);
        this.cl.send(message);
    }
    
    @Override
    public void handleReceivedData(HashMap<String, String> msg) {
        
    }

}
