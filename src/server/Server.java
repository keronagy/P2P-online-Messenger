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
import utility.GeneralConstants;
import utility.ServerConstatns;
import utility.ClientConstants;
import utility.IDGenerator;
import utility.CallbackOnReceiveHandler;


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
        
    }
    
    public static void initiateServer(){
        new Server().start();
    }
    
    @Override
    public void run(){
        try {
            while(true){
                ServerSocket ss = new ServerSocket(ServerConstatns.serverPort);
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
    
    
    private Client createClient(Socket s, String clientName){
        String id = IDGenerator.generateClientID();
        String initialStatus = ClientConstants.INITSTATUS;
        String name = clientName;
        return new Client(id, initialStatus, name, CommunicationLink.generateCommunicationLink(this, id, s));
    }
    
    private void handleClientRequest(Socket clientSocket){

        try {
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            //1) read message
            HashMap<String, String> connctionRequest = (HashMap<String, String>)ois.readObject();
            //2) check client Request type (control connection or room creation/joining connection)
            String connectionType = connctionRequest.get(GeneralConstants.REQUESTTYPEATTR);
            if(connectionType.equals(ClientConstants.MAINCONNECTION)){
                //create new client on server
                Client newClient = createClient(clientSocket, connctionRequest.get(GeneralConstants.CLIENTNAMEATTR));
                //send the new client current server state
                sendClients(newClient.getCommunicationLink());
                sendRooms(newClient.getCommunicationLink());
                //add client to server state
                clients.put(newClient.getId(), newClient);
                //update other clients of the new added client
                sendNewClientToOtherClients(newClient);
                
            }else if(connectionType.equals(ClientConstants.ROOMCREATECONNECTION)){
                
            }else if(connectionType.equals(ClientConstants.ROOMJOINCONNECTION)){
                
            }
        } catch (Exception ex) {
            System.out.println("server.Server.handleClientRequest()");
        } 
        
    }
    
    public void sendClient(Client c, CommunicationLink cl) {
        HashMap<String, String> message = new HashMap<>();
        message.put(GeneralConstants.UPDATETYPEATTR, ServerConstatns.ADDNEWCLIENTORDER);
        message.put(GeneralConstants.CLIENTIDATTR, c.getId());
        message.put(GeneralConstants.CLIENTNAMEATTR, c.getName());
        message.put(GeneralConstants.CLIENTSTATUSATTR, c.getStatus());
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
        message.put(GeneralConstants.UPDATETYPEATTR, ServerConstatns.ADDNEWROOMORDER);
        message.put(GeneralConstants.ROOMIDATTR, r.getId());
        message.put(GeneralConstants.ROOMNAMEATTR, r.getName());
        cl.send(message);
    }
    public void sendRooms(CommunicationLink cl) {
        rooms.values().forEach((r) -> {
            sendRoom(r, cl);
        });
    }
}
