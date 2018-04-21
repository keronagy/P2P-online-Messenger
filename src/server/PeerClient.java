/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public void createRoom(String name)
    {
        HashMap<String,String> message = new HashMap();
        message.put(GeneralConstants.REQUESTTYPEATTR, ClientConstants.ROOMCREATECONNECTION);
        message.put(GeneralConstants.CLIENTIDATTR, this.id);
        message.put(GeneralConstants.ROOMNAMEATTR, name);
        this.cl.send(message);
    }
    public void joinRoom(String roomID)
    {
        HashMap<String,String> message = new HashMap();
        message.put(GeneralConstants.REQUESTTYPEATTR, ClientConstants.ROOMJOINCONNECTION);
        message.put(GeneralConstants.CLIENTIDATTR, this.id);
        message.put(GeneralConstants.ROOMIDATTR, roomID);
        this.cl.send(message);
    }
    public void LeaveRoom(String roomID)
    {
        HashMap<String,String> message = new HashMap();
        message.put(GeneralConstants.REQUESTTYPEATTR, ClientConstants.ROOMLEAVECONNECTION);
        message.put(GeneralConstants.CLIENTIDATTR, this.id);
        message.put(GeneralConstants.ROOMIDATTR, roomID);
        this.cl.send(message);
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
        try {
            java.lang.reflect.Method handle;
            handle = this.getClass().getMethod(msg.get(GeneralConstants.REPLYTYPEATTR),HashMap.class);
            handle.invoke(this, msg);
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void handleRoomJoined(HashMap<String, String> msg)
    {
        String roomID = msg.get(GeneralConstants.ROOMIDATTR);
        //GUI open room
    }
    
    public void handleRoomLeft(HashMap<String, String> msg)
    {
        String roomID = msg.get(GeneralConstants.ROOMIDATTR);
        //GUI close room
    }
    
    public void handleClientAddedtoRoom(HashMap<String, String> msg)
    {
        String name = msg.get(GeneralConstants.CLIENTNAMEATTR);
        String id = msg.get(GeneralConstants.CLIENTIDATTR);
        String status = msg.get(GeneralConstants.CLIENTSTATUSATTR);
        //GUI add client to room
    }
    
    public void handleClientRemovedFromRoom(HashMap<String, String> msg)
    {
        String name = msg.get(GeneralConstants.CLIENTNAMEATTR);//not needed?
        String id = msg.get(GeneralConstants.CLIENTIDATTR);
        String status = msg.get(GeneralConstants.CLIENTSTATUSATTR);//not needed?
        //GUI remove client from room
    }
    
    public void handleMessageFromRoom(HashMap<String, String> msg)
    {
        String roomID = msg.get(GeneralConstants.ROOMIDATTR);
        String senderID = msg.get(GeneralConstants.CLIENTIDATTR);
        String message = msg.get(MessageConstants.MESSAGE);
        //GUI add message to chat
    }
    
    public void handleNewClient(HashMap<String, String> msg)
    {
        String name = msg.get(GeneralConstants.CLIENTNAMEATTR);
        String id = msg.get(GeneralConstants.CLIENTIDATTR);
        String status = msg.get(GeneralConstants.CLIENTSTATUSATTR);
        //GUI add new client
    }
    
    public void handleNewRoom(HashMap<String, String> msg)
    {
        String name = msg.get(GeneralConstants.ROOMNAMEATTR);
        String id = msg.get(GeneralConstants.ROOMIDATTR);
        //GUI add new room
    }
    
}
