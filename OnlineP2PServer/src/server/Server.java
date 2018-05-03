/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
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
    String adminID;
    boolean first;

    private Server() {
        // to deny access to default public constructor
        first = true;
    }

    @Override
    public void handleReceivedData(HashMap<String, String> msg) {
        try {
            java.lang.reflect.Method handle;
            handle = this.getClass().getMethod(msg.get(Constants.REQUESTTYPEATTR), HashMap.class);
            handle.invoke(this, msg);
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void initiateServer() {
        new Server().start();
    }

    @Override
    public void run() {
        try {
            clients = new HashMap();
            rooms = new HashMap();
            ServerSocket ss = new ServerSocket(Constants.SERVERPORT);
            while (true) {
                handleClientRequest(ss.accept());
            }
        } catch (IOException ex) {
            System.out.println("server.Server.run()");
        }
    }

    private void sendNewClientToOtherClients(Client newClient, String order) {
        if (order.equals(Constants.ADDNEWCLIENTORDER)) {
            for (Client c : clients.values()) {
                sendClientAdd(newClient, c.getCommunicationLink());
            }
        } else {
            for (Client c : clients.values()) {
                sendClientRemove(newClient, c.getCommunicationLink());
            }
        }
    }

    private void sendNewRoomToOtherClients(Room newRoom) {

        for (Client c : clients.values()) {
            sendRoom(newRoom, c.getCommunicationLink());
        }
    }

    private Client createClient(String id, Socket s, String clientName) {
        String initialStatus = Constants.INITSTATUS;
        String name = clientName;
        return new Client(id, s.getInetAddress().toString(), initialStatus, name, CommunicationLink.generateCommunicationLink(this, s, id));
    }

    private void handleClientRequest(Socket clientSocket) {

        try {
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            HashMap<String, String> connectionRequest = (HashMap<String, String>) ois.readObject();

            //2) check client Request type (control connection or room creation/joining connection)
            String connectionType = connectionRequest.get(Constants.REQUESTTYPEATTR);
            if (connectionType.equals(Constants.MAINCONNECTION)) {
                //create new client on server
                String id = IDGenerator.generateClientID();
                if (first) {
                    adminID = id;
                    first = false;
                }
                oos.writeUTF(id);
                oos.flush();
                Client newClient = createClient(id, clientSocket, connectionRequest.get(Constants.CLIENTNAMEATTR));
                //send the new client current server state
                sendClients(newClient.getCommunicationLink());
                sendRooms(newClient.getCommunicationLink());
                //add client to server state

                //update other clients of the new added client
                sendNewClientToOtherClients(newClient, Constants.ADDNEWCLIENTORDER);
                clients.put(newClient.getId(), newClient);
            }

        } catch (Exception ex) {
            System.out.println("server.Server.handleClientRequest()");
        }

    }

    public void sendClientAdd(Client c, CommunicationLink cl) {
        HashMap<String, String> message = new HashMap<>();
        message.put(Constants.REPLYTYPEATTR, Constants.ADDNEWCLIENTORDER);
        message.put(Constants.CLIENTIDATTR, c.getId());
        message.put(Constants.CLIENTNAMEATTR, c.getName());
        message.put(Constants.CLIENTSTATUSATTR, c.getStatus());
        message.put(Constants.CLIENTIPATTR, c.getIp());
        cl.send(message);
    }

    public void sendClientRemove(Client c, CommunicationLink cl) {
        HashMap<String, String> message = new HashMap<>();
        message.put(Constants.REPLYTYPEATTR, Constants.REMOVECLIENTORDER);
        message.put(Constants.CLIENTIDATTR, c.getId());
        cl.send(message);
    }

    public void sendClients(CommunicationLink cl) {
        //need to put before adding in the new client so s/he doesnt get sent to her/im self
        clients.values().forEach((c) -> {
            sendClientAdd(c, cl);
        });
    }

    public void sendRoom(Room r, CommunicationLink cl) {
        HashMap<String, String> message = new HashMap<>();
        message.put(Constants.REPLYTYPEATTR, Constants.ADDNEWROOMORDER);
        message.put(Constants.ROOMIDATTR, r.getId());
        message.put(Constants.ROOMNAMEATTR, r.getName());
        message.put(Constants.ADMINIDATTR, r.getAdminID());
        cl.send(message);
    }

    public void sendRooms(CommunicationLink cl) {
        rooms.values().forEach((r) -> {
            sendRoom(r, cl);
        });
    }

    public void handleRoomMessage(HashMap<String, String> message) {
        String roomID, senderID, msg;
        roomID = message.get(Constants.ROOMIDATTR);
        senderID = message.get(Constants.CLIENTIDATTR);
        msg = message.get(Constants.MESSAGE);
        rooms.get(roomID).sendMessageToParticipants(senderID, msg);
    }

    public void handleRoomCreate(HashMap<String, String> message) {
        String roomID, senderID, roomName;
        roomID = IDGenerator.generateRoomID();
        senderID = message.get(Constants.CLIENTIDATTR);
        roomName = message.get(Constants.ROOMNAMEATTR);
        Client sender = clients.get(senderID);
        Room r = new Room(roomID, senderID, roomName);
        rooms.put(roomID, r);
        HashMap<String, String> confirmation = new HashMap();
        confirmation.put(Constants.REPLYTYPEATTR, Constants.CONFIRMCREATEROOMORDER);
        confirmation.put(Constants.ROOMIDATTR, roomID);
        sendNewRoomToOtherClients(r);
        sender.getCommunicationLink().send(confirmation);
    }

    public void handleRoomJoin(HashMap<String, String> message) {
        String roomID, senderID;
        senderID = message.get(Constants.CLIENTIDATTR);
        roomID = message.get(Constants.ROOMIDATTR);
        Room r = rooms.get(roomID);
        Client sender = clients.get(senderID);
        HashMap<String, String> confirmation = new HashMap();
        confirmation.put(Constants.REPLYTYPEATTR, Constants.CONFIRMJOINROOMORDER);
        confirmation.put(Constants.ROOMIDATTR, roomID);
        confirmation.put(Constants.ROOMNAMEATTR, r.getName());
        confirmation.put(Constants.ADMINIDATTR, r.getAdminID());
        sender.getCommunicationLink().send(confirmation);
        r.addClient(sender);
    }

    public void handleRoomLeave(HashMap<String, String> message) {
        String roomID, senderID;
        senderID = message.get(Constants.CLIENTIDATTR);
        roomID = message.get(Constants.ROOMIDATTR);
        Client sender = clients.get(senderID);
        rooms.get(roomID).removeClient(sender);
        HashMap<String, String> confirmation = new HashMap();
        confirmation.put(Constants.REPLYTYPEATTR, Constants.CONFIRMLEAVEROOMORDER);
        confirmation.put(Constants.ROOMIDATTR, roomID);
        sender.getCommunicationLink().send(confirmation);
    }

    public void handleClientClosed(HashMap<String, String> message) {
        Client client = clients.get(message.get(Constants.CLIENTIDATTR));
        rooms.values().forEach((r) -> {
            r.removeClient(client);
        });
        sendNewClientToOtherClients(client, Constants.REMOVECLIENTORDER);
        clients.remove(client.getId());
    }

    public void handleRoomDeleteRequest(HashMap<String, String> message) {
        String roomID, senderID;
        senderID = message.get(Constants.CLIENTIDATTR);
        roomID = message.get(Constants.ROOMIDATTR);
        Room r = rooms.get(roomID);
        if (senderID.equals(r.getAdminID()) || senderID.equals(adminID)) {
            r.deleteRoom();
            rooms.remove(roomID);
        }
    }
    public void handleClientKickFromRoom(HashMap<String,String> message){
        String senderID,roomID,clientID;
        senderID = message.get(Constants.ADMINIDATTR);
        roomID = message.get(Constants.ROOMIDATTR);
        clientID = message.get(Constants.CLIENTIDATTR);
        Room r = rooms.get(roomID);
        if (senderID.equals(r.getAdminID()) || senderID.equals(adminID)) {
            r.removeClient(clients.get(clientID));
            HashMap<String, String> confirmation = new HashMap();
            confirmation.put(Constants.REPLYTYPEATTR, Constants.CONFIRMLEAVEROOMORDER);
            confirmation.put(Constants.ROOMIDATTR, roomID);
            clients.get(clientID).getCommunicationLink().send(confirmation);
        }
    }
    public void handleClientKick(HashMap<String, String> message) {
        String requester = message.get(Constants.ADMINIDATTR);
        Client client = clients.get(message.get(Constants.CLIENTIDATTR));
        if (requester.equals(adminID)) {
            handleClientClosed(message);
            HashMap<String, String> close = new HashMap();
            close.put(Constants.REPLYTYPEATTR, Constants.CONNECTIONCLOSED);
            client.getCommunicationLink().send(close);
        }

    }

    public static void main(String[] args) {
        initiateServer();
    }

}
