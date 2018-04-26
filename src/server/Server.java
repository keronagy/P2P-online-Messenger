/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.CommunicationLink;
import utility.*;


/**
 *
 * @author fadia
 */
//extends thread maybe tweak its priority in the future
public class Server extends Thread implements CallbackOnReceiveHandler {

    HashMap<String, Client> clients;
    HashMap<String, Room> rooms;

    private Server(){
        // to deny access to default public constructor
    }
    
    @Override
    public void handleReceivedData(HashMap<String, String> msg){
        try {
            java.lang.reflect.Method handle;
            handle = this.getClass().getMethod(msg.get(GeneralConstants.REQUESTTYPEATTR),HashMap.class);
            handle.invoke(this, msg);
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void initiateServer(){
        new Server().start();
    }
    
    @Override
    public void run(){
        try {
            clients = new HashMap();
            rooms = new HashMap();
            ServerSocket ss = new ServerSocket(ServerConstants.SERVERPORT);
            while(true){               
                handleClientRequest(ss.accept());
            }
        } catch (IOException ex) {
            System.out.println("server.Server.run()");
        }
    }
    
    private void sendNewClientToOtherClients(Client newClient){
        
        for(Client c : clients.values()){
            sendClient(newClient, c.getCommunicationLink());
        }
    }
    
    private void sendNewRoomToOtherClients(Room newRoom){
        
        for(Client c : clients.values()){
            sendRoom(newRoom, c.getCommunicationLink());
        }
    }
    
    private Client createClient(String id, Socket s, String clientName){
        String initialStatus = ClientConstants.INITSTATUS;
        String name = clientName;
        return new Client(id, s.getInetAddress().toString(), initialStatus, name, CommunicationLink.generateCommunicationLink(this, s));
    }
    
    private void handleClientRequest(Socket clientSocket){

        try {
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            HashMap<String, String> connectionRequest = (HashMap<String, String>)ois.readObject();
            
            //2) check client Request type (control connection or room creation/joining connection)
            String connectionType = connectionRequest.get(GeneralConstants.REQUESTTYPEATTR);
            if(connectionType.equals(ClientConstants.MAINCONNECTION)){
                //create new client on server
                String id = IDGenerator.generateClientID();
                oos.writeUTF(id);
                oos.flush();
                Client newClient = createClient(id, clientSocket, connectionRequest.get(GeneralConstants.CLIENTNAMEATTR));
                //send the new client current server state
                sendClients(newClient.getCommunicationLink());
                sendRooms(newClient.getCommunicationLink());
                //add client to server state
                
                //update other clients of the new added client
                sendNewClientToOtherClients(newClient);
                clients.put(newClient.getId(), newClient);
            }
            
        } catch (Exception ex) {
            System.out.println("server.Server.handleClientRequest()");
        } 
        
    }
    
    public void sendClient(Client c, CommunicationLink cl) {
        HashMap<String, String> message = new HashMap<>();
        message.put(GeneralConstants.REPLYTYPEATTR, ServerConstants.ADDNEWCLIENTORDER);
        message.put(GeneralConstants.CLIENTIDATTR, c.getId());
        message.put(GeneralConstants.CLIENTNAMEATTR, c.getName());
        message.put(GeneralConstants.CLIENTSTATUSATTR, c.getStatus());
        message.put(GeneralConstants.CLIENTIPATTR, c.getIp());
        cl.send(message);
    }
    public void sendClients(CommunicationLink cl) {
        //need to put before adding in the new client so s/he doesnt get sent to her/im self
        clients.values().forEach((c) -> {
            sendClient(c, cl);
        });
    }
    public void sendRoom(Room r, CommunicationLink cl) {
        HashMap<String, String> message = new HashMap<>();
        message.put(GeneralConstants.REPLYTYPEATTR, ServerConstants.ADDNEWROOMORDER);
        message.put(GeneralConstants.ROOMIDATTR, r.getId());
        message.put(GeneralConstants.ROOMNAMEATTR, r.getName());
        cl.send(message);
    }
    public void sendRooms(CommunicationLink cl) {
        rooms.values().forEach((r) -> {
            sendRoom(r, cl);
        });
    }
    
    public void handleRoomMessage(HashMap<String,String> message)
    {
        String roomID,senderID,msg;
        roomID = message.get(GeneralConstants.ROOMIDATTR);
        senderID = message.get(GeneralConstants.CLIENTIDATTR);
        msg = message.get(MessageConstants.MESSAGE);
        rooms.get(roomID).sendMessageToParticipants(senderID, msg);
    }
    
    public void handleRoomCreate(HashMap<String,String> message)
    {
        String roomID,senderID,roomName;
        roomID = IDGenerator.generateRoomID();
        senderID = message.get(GeneralConstants.CLIENTIDATTR);
        roomName = message.get(GeneralConstants.ROOMNAMEATTR);
        Client sender = clients.get(senderID);
        Room r = new Room(roomID, sender, roomName);
        rooms.put(roomID, r);
        sendNewRoomToOtherClients(r);
        HashMap<String,String> confirmation = new HashMap();
        confirmation.put(GeneralConstants.REPLYTYPEATTR, ServerConstants.CONFIRMJOINROOMORDER);
        confirmation.put(GeneralConstants.ROOMIDATTR, roomID);
        sender.server_cl.send(confirmation);
    }
    
    public void handleRoomJoin(HashMap<String,String> message)
    {
        String roomID,senderID;
        senderID = message.get(GeneralConstants.CLIENTIDATTR);
        roomID = message.get(GeneralConstants.ROOMIDATTR);
        Client sender = clients.get(senderID);
        rooms.get(roomID).addClient(sender);
        HashMap<String,String> confirmation = new HashMap();
        confirmation.put(GeneralConstants.REPLYTYPEATTR, ServerConstants.CONFIRMJOINROOMORDER);
        confirmation.put(GeneralConstants.ROOMIDATTR, roomID);
        sender.server_cl.send(confirmation);
    }
    
    public void handleRoomLeave(HashMap<String,String> message)
    {
        String roomID,senderID;
        senderID = message.get(GeneralConstants.CLIENTIDATTR);
        roomID = message.get(GeneralConstants.ROOMIDATTR);
        Client sender = clients.get(senderID);
        rooms.get(roomID).removeClient(sender);
        HashMap<String,String> confirmation = new HashMap();
        confirmation.put(GeneralConstants.REPLYTYPEATTR, ServerConstants.CONFIRMLEAVEROOMORDER);
        confirmation.put(GeneralConstants.ROOMIDATTR, roomID);
        sender.server_cl.send(confirmation);
    }
    
    
}
