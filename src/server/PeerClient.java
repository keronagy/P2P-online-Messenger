/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
            
    }
    public void connect(String ip,int port)
    {
        try {
            Socket s = new Socket(ip, port);
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            
            HashMap<String, String> connectionRequest = (HashMap<String, String>)ois.readObject();
            connectionRequest.put(GeneralConstants.REQUESTTYPEATTR, ClientConstants.MAINCONNECTION);
            connectionRequest.put(GeneralConstants.CLIENTNAMEATTR, name);
            oos.writeObject(connectionRequest);
            
            this.id = ois.readUTF();
            this.cl = CommunicationLink.generateCommunicationLink(this, s);
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
        String clientID = msg.get(GeneralConstants.CLIENTIDATTR);
        //GUI add client to room
    }
    
    public void handleClientRemovedFromRoom(HashMap<String, String> msg)
    {
        String clientID = msg.get(GeneralConstants.CLIENTIDATTR);
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
        String clientName = msg.get(GeneralConstants.CLIENTNAMEATTR);
        String clientID = msg.get(GeneralConstants.CLIENTIDATTR);
        String clientStatus = msg.get(GeneralConstants.CLIENTSTATUSATTR);
        String clientIp = msg.get(GeneralConstants.CLIENTIPATTR);
        //GUI add new client
    }
    
    public void handleNewRoom(HashMap<String, String> msg)
    {
        String roomName = msg.get(GeneralConstants.ROOMNAMEATTR);
        String roomID = msg.get(GeneralConstants.ROOMIDATTR);
        //GUI add new room
    }
    
}
