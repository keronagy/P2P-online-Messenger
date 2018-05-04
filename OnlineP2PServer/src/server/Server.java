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
public class Server extends Thread {

    HashMap<String, Client> clients;
    HashMap<String, Room> rooms;
    String adminID;
    boolean first;

    public static void main(String[] args) {
        initiateServer();
    }

    private Server() {
        // to deny access to default public constructor
        first = true;
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
        String name = clientName;
        return new Client(id, s.getInetAddress().toString(), name, CommunicationLink.generateCommunicationLink(new ClientHandler(id), s));
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

                String id = connectionRequest.get(Constants.CLIENTIDATTR);
                Client client;
                if (verifyID(id)) {
                    client = clients.get(id);
                    oos.writeUTF(id);
                    oos.flush();
                    client.setCommunicationLink(CommunicationLink.generateCommunicationLink(new ClientHandler(id), clientSocket));
                    reJoinRooms(client);

                } else {
                    id = IDGenerator.generateClientID();
                    oos.writeUTF(id);
                    oos.flush();
                    if (first) {
                        adminID = id;
                        first = false;
                    }
                    client = createClient(id, clientSocket, connectionRequest.get(Constants.CLIENTNAMEATTR));
                    sendNewClientToOtherClients(client, Constants.ADDNEWCLIENTORDER);
                    clients.put(id, client);
                }

                //send the new client current server state
                sendClients(client.getCommunicationLink());
                sendRooms(client.getCommunicationLink());
                sendNewClientStatusToAllOtherClients(id, Constants.INITSTATUS);
            }

        } catch (Exception ex) {
            System.out.println("server.Server.handleClientRequest()");
        }

    }

    private void reJoinRooms(Client client) {
        String clientID = client.getId();
        rooms.values().forEach((r) -> {
            if (r.clientExists(clientID)) {
                sendConfirmation(r, client);
            }
        });
    }

    private boolean verifyID(String ID) {
        return (clients.get(ID) != null);

    }

    public void sendClientAdd(Client c, CommunicationLink cl) {
        HashMap<String, String> message = new HashMap<>();
        message.put(Constants.REPLYTYPEATTR, Constants.ADDNEWCLIENTORDER);
        message.put(Constants.CLIENTIDATTR, c.getId());
        message.put(Constants.CLIENTNAMEATTR, c.getName());
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

    public void sendNewClientStatusToAllOtherClients(String clientID, String newStatus) {
        //message construction
        HashMap<String, String> message = new HashMap<>();
        message.put(Constants.REPLYTYPEATTR, Constants.NEWPEERSTATUSUPDATE);
        message.put(Constants.CLIENTIDATTR, clientID);
        message.put(Constants.CLIENTSTATUSATTR, newStatus);

        //sending to all clients except sender client
        for (Client peer : clients.values()) {
            if (!peer.getId().equals(clientID)) {
                peer.getCommunicationLink().send(message);
            }
        }
    }

    private void sendConfirmation(Room r, Client sender) {
        HashMap<String, String> confirmation = new HashMap();
        confirmation.put(Constants.REPLYTYPEATTR, Constants.CONFIRMJOINROOMORDER);
        confirmation.put(Constants.ROOMIDATTR, r.getId());
        confirmation.put(Constants.ROOMNAMEATTR, r.getName());
        confirmation.put(Constants.ADMINIDATTR, r.getAdminID());
        sender.getCommunicationLink().send(confirmation);

    }

    class ClientHandler implements CallbackOnReceiveHandler {

        private final String clientID;

        public ClientHandler(String clientID) {
            this.clientID = clientID;
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

        public void handleRoomMessage(HashMap<String, String> message) {
            String roomID, senderID, msg;
            roomID = message.get(Constants.ROOMIDATTR);
            senderID = this.clientID;
            msg = message.get(Constants.MESSAGE);
            rooms.get(roomID).sendMessageToParticipants(senderID, msg);
        }

        public void handleClientStatusChange(HashMap<String, String> message) {
            String clientIDWhoChangedStatus = this.clientID;
            String newStatus = message.get(Constants.CLIENTSTATUSATTR);
            sendNewClientStatusToAllOtherClients(clientIDWhoChangedStatus, newStatus);
        }

        public void handleRoomCreate(HashMap<String, String> message) {
            String roomID, senderID, roomName;

            roomID = IDGenerator.generateRoomID();
            senderID = this.clientID;
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
            senderID = this.clientID;
            roomID = message.get(Constants.ROOMIDATTR);
            Room r = rooms.get(roomID);
            Client sender = clients.get(senderID);
            sendConfirmation(r, sender);
            r.addClient(sender);
        }

        public void handleRoomLeave(HashMap<String, String> message) {
            String roomID, senderID;
            senderID = this.clientID;
            roomID = message.get(Constants.ROOMIDATTR);
            Client sender = clients.get(senderID);
            rooms.get(roomID).removeClient(sender);
            HashMap<String, String> confirmation = new HashMap();
            confirmation.put(Constants.REPLYTYPEATTR, Constants.CONFIRMLEAVEROOMORDER);
            confirmation.put(Constants.ROOMIDATTR, roomID);
            sender.getCommunicationLink().send(confirmation);
        }

        public void handleClientClosed(HashMap<String, String> message) {
//            Client client = clients.get(this.clientID);
//            rooms.values().forEach((r) -> {
//                r.removeClient(client);
//            });
            //sendNewClientToOtherClients(client, Constants.REMOVECLIENTORDER);
//            clients.remove(client.getId());
            sendNewClientStatusToAllOtherClients(this.clientID, "offline");
        }

        public void handleRoomDeleteRequest(HashMap<String, String> message) {
            String roomID, senderID;
            senderID = this.clientID;
            roomID = message.get(Constants.ROOMIDATTR);
            Room r = rooms.get(roomID);
            if (senderID.equals(r.getAdminID()) || senderID.equals(adminID)) {
                r.deleteRoom();
                rooms.remove(roomID);
            }
        }

        public void handleClientKickFromRoom(HashMap<String, String> message) {
            String senderID, roomID, kickedClientID;
            senderID = this.clientID;
            roomID = message.get(Constants.ROOMIDATTR);
            kickedClientID = message.get(Constants.CLIENTIDATTR);
            Room r = rooms.get(roomID);
            if (senderID.equals(r.getAdminID()) || senderID.equals(adminID)) {
                r.removeClient(clients.get(kickedClientID));
                HashMap<String, String> confirmation = new HashMap();
                confirmation.put(Constants.REPLYTYPEATTR, Constants.CONFIRMLEAVEROOMORDER);
                confirmation.put(Constants.ROOMIDATTR, roomID);
                clients.get(kickedClientID).getCommunicationLink().send(confirmation);
            }
        }

        public void handleClientKick(HashMap<String, String> message) {
            String requester = this.clientID;
            Client client = clients.get(message.get(Constants.CLIENTIDATTR));
            if (requester.equals(adminID)) {
                handleClientClosed(message);
                HashMap<String, String> close = new HashMap();
                close.put(Constants.REPLYTYPEATTR, Constants.CONNECTIONCLOSED);
                client.getCommunicationLink().send(close);
            }

        }
    }

}
