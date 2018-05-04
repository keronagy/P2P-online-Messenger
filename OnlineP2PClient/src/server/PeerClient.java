/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import network.CommunicationLink;
import utility.*;

/**
 *
 * @author Islam
 */
public class PeerClient extends Client {

    protected HashMap<String, CommunicationLink> cls;

    private ServerSocket peerSocket;
    private boolean admin = false;

    public PeerClient(String status, String name) {
        super("", status, name);
        cls = new HashMap();
    }

    public void connect(String ip, int port, CallbackOnReceiveHandler handler) {
        try {
            Socket s = new Socket(ip, port);
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());

            HashMap<String, String> connectionRequest = new HashMap();
            connectionRequest.put(Constants.REQUESTTYPEATTR, Constants.MAINCONNECTION);
            connectionRequest.put(Constants.CLIENTNAMEATTR, name);
            id = readID();
            connectionRequest.put(Constants.CLIENTIDATTR, id);
            oos.writeObject(connectionRequest);
            oos.flush();
            this.id = ois.readUTF();
            writeID(this.id);
            if (this.id.substring(0, 3).equals("c-0")) {
                admin = true;
            }
            this.server_cl = CommunicationLink.generateCommunicationLink(handler, s);
            peerSocket = new ServerSocket(Constants.SERVERPORT + 1);
        } catch (Exception ex) {
            System.out.println("Connection failed");
        }
    }

    private String readID() {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("ID.txt"));
            String ID = br.readLine();
            br.close();
            return ID;

        } catch (Exception e) {
            return "";
        }
    }

    private void writeID(String id) {
        PrintWriter pw;
        try {
            pw = new PrintWriter(new FileWriter("ID.txt"));
            pw.write(id);
            pw.close();
        } catch (Exception e) {

        }
    }

    public boolean isAdmin() {
        return this.admin;
    }

    // server part
    public void createRoom(String name) {
        HashMap<String, String> message = new HashMap();
        message.put(Constants.REQUESTTYPEATTR, Constants.ROOMCREATECONNECTION);
        //message.put(Constants.CLIENTIDATTR, this.id);
        message.put(Constants.ROOMNAMEATTR, name);
        this.server_cl.send(message);
    }

    public void changeStatus(String newStatus) {
        HashMap<String, String> message = new HashMap();
        message.put(Constants.REQUESTTYPEATTR, Constants.CHANGESTATUS);
        //message.put(Constants.CLIENTIDATTR, this.id);
        message.put(Constants.CLIENTSTATUSATTR, newStatus);
        this.server_cl.send(message);
    }

    public void joinRoom(String roomID) {
        HashMap<String, String> message = new HashMap();
        message.put(Constants.REQUESTTYPEATTR, Constants.ROOMJOINCONNECTION);
        //message.put(Constants.CLIENTIDATTR, this.id);
        message.put(Constants.ROOMIDATTR, roomID);
        this.server_cl.send(message);
    }

    public void LeaveRoom(String roomID) {
        HashMap<String, String> message = new HashMap();
        message.put(Constants.REQUESTTYPEATTR, Constants.ROOMLEAVECONNECTION);
        //message.put(Constants.CLIENTIDATTR, this.id);
        message.put(Constants.ROOMIDATTR, roomID);
        this.server_cl.send(message);
    }

    public void sendMessageToRoom(String roomID, String msg) {
        HashMap<String, String> message = new HashMap();
        message.put(Constants.REQUESTTYPEATTR, Constants.ROOMSENDMESSAGE);
        message.put(Constants.ROOMIDATTR, roomID);
        //message.put(Constants.CLIENTIDATTR, this.id);
        message.put(Constants.MESSAGE, msg);
        this.server_cl.send(message);
    }

    public void deleteRoom(String roomID) {
        HashMap<String, String> message = new HashMap();
        message.put(Constants.REQUESTTYPEATTR, Constants.ROOMDELETEORDER);
        //message.put(Constants.CLIENTIDATTR, this.id);
        message.put(Constants.ROOMIDATTR, roomID);
        this.server_cl.send(message);
    }

    public void kickClient(String clientID) //elgabha
    {
        HashMap<String, String> message = new HashMap();
        message.put(Constants.REQUESTTYPEATTR, Constants.KICKCLIENTORDER);
        message.put(Constants.CLIENTIDATTR, clientID);
        //message.put(Constants.ADMINIDATTR, this.getId());
        this.server_cl.send(message);
    }

    public void kickClientFromRoom(String roomID, String clientID) //elgabha round2
    {
        HashMap<String, String> message = new HashMap();
        message.put(Constants.REQUESTTYPEATTR, Constants.KICKCLIENTFROMROOMORDER);
        message.put(Constants.CLIENTIDATTR, clientID);
        //message.put(Constants.ADMINIDATTR, this.getId());
        message.put(Constants.ROOMIDATTR, roomID);
        this.server_cl.send(message);
    }

    //p2p part
    public String waitForConnection(CallbackOnReceiveHandler handler) {
        try {
            Socket s = peerSocket.accept();
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            String peerID = ois.readUTF();
            CommunicationLink cl = CommunicationLink.generateCommunicationLink(handler, s);
            this.cls.put(peerID, cl);
            return peerID;
        } catch (Exception e) {
            return "";
        }
    }

    public void connectToPeer(String peerID, String ip, int port, CallbackOnReceiveHandler handler) {
        try {
            Socket s = new Socket(ip, port);
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeUTF(this.id);
            oos.flush();
            CommunicationLink cl = CommunicationLink.generateCommunicationLink(handler, s);
            this.cls.put(peerID, cl);
        } catch (IOException ex) {
        }
    }

    public void sendMessageToPeer(String peerID, String msg) {
        CommunicationLink cl = this.cls.get(peerID);
        HashMap<String, String> message = new HashMap();
        message.put(Constants.REQUESTTYPEATTR, Constants.MESSAGEFROMCLIENT);
        message.put(Constants.PEERMESSAGE, msg);
        cl.send(message);
    }

    public void isTyping(String peerID) {
        CommunicationLink cl = this.cls.get(peerID);
        HashMap<String, String> message = new HashMap();
        message.put(Constants.REQUESTTYPEATTR, Constants.TYPINGHANDLE);
        message.put(Constants.TYPINGSTATUS, Constants.ISTYPING);
        cl.send(message);
    }

    public void stoppedTyping(String peerID) {
        CommunicationLink cl = this.cls.get(peerID);
        HashMap<String, String> message = new HashMap();
        message.put(Constants.REQUESTTYPEATTR, Constants.TYPINGHANDLE);
        message.put(Constants.TYPINGSTATUS, Constants.NOTTYPING);
        cl.send(message);
    }

    public void confirmSeen(String peerID, String seenAt) {
        CommunicationLink cl = this.cls.get(peerID);
        HashMap<String, String> message = new HashMap();
        message.put(Constants.REQUESTTYPEATTR, Constants.SEENHANDLE);
        message.put(Constants.SEENTIME, seenAt);
        cl.send(message);
    }

}
