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
import network.CommunicationLink;
import utility.*;
import onlinep2pmessenger.FXMLDocumentController;

/**
 *
 * @author Islam
 */
public class PeerClient extends Client {

    protected HashMap<String, CommunicationLink> cls;

    private ServerSocket peerSocket;

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
            connectionRequest.put(GeneralConstants.REQUESTTYPEATTR, ClientConstants.MAINCONNECTION);
            connectionRequest.put(GeneralConstants.CLIENTNAMEATTR, name);
            oos.writeObject(connectionRequest);
            oos.flush();
            this.id = ois.readUTF();
            this.server_cl = CommunicationLink.generateCommunicationLink(handler, s);
            peerSocket = new ServerSocket(ServerConstants.SERVERPORT + 1);
        } catch (Exception ex) {
            System.out.println("Connection failed");
        }
    }

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

    public void createRoom(String name) {
        HashMap<String, String> message = new HashMap();
        message.put(GeneralConstants.REQUESTTYPEATTR, ClientConstants.ROOMCREATECONNECTION);
        message.put(GeneralConstants.CLIENTIDATTR, this.id);
        message.put(GeneralConstants.ROOMNAMEATTR, name);
        this.server_cl.send(message);
    }

    public void joinRoom(String roomID) {
        HashMap<String, String> message = new HashMap();
        message.put(GeneralConstants.REQUESTTYPEATTR, ClientConstants.ROOMJOINCONNECTION);
        message.put(GeneralConstants.CLIENTIDATTR, this.id);
        message.put(GeneralConstants.ROOMIDATTR, roomID);
        this.server_cl.send(message);
    }

    public void LeaveRoom(String roomID) {
        HashMap<String, String> message = new HashMap();
        message.put(GeneralConstants.REQUESTTYPEATTR, ClientConstants.ROOMLEAVECONNECTION);
        message.put(GeneralConstants.CLIENTIDATTR, this.id);
        message.put(GeneralConstants.ROOMIDATTR, roomID);
        this.server_cl.send(message);
    }

    public void sendMessageToRoom(String roomID, String msg) {
        HashMap<String, String> message = new HashMap();
        message.put(GeneralConstants.REQUESTTYPEATTR, ClientConstants.ROOMSENDMESSAGE);
        message.put(GeneralConstants.ROOMIDATTR, roomID);
        message.put(GeneralConstants.CLIENTIDATTR, this.id);
        message.put(MessageConstants.MESSAGE, msg);
        this.server_cl.send(message);
    }

    public void sendMessageToPeer(String peerID, String msg) {
        CommunicationLink cl = this.cls.get(peerID);
        HashMap<String, String> message = new HashMap();
        message.put(GeneralConstants.REQUESTTYPEATTR, MessageConstants.MESSAGEFROMCLIENT);
        message.put(ClientConstants.PEERMESSAGE, msg);
        cl.send(message);
    }

    public void isTyping(String peerID) {
        CommunicationLink cl = this.cls.get(peerID);
        HashMap<String, String> message = new HashMap();
        message.put(GeneralConstants.REQUESTTYPEATTR, MessageConstants.TYPINGHANDLE);
        message.put(ClientConstants.TYPINGSTATUS, MessageConstants.ISTYPING);
        cl.send(message);
    }

    public void stoppedTyping(String peerID) {
        CommunicationLink cl = this.cls.get(peerID);
        HashMap<String, String> message = new HashMap();
        message.put(GeneralConstants.REQUESTTYPEATTR, MessageConstants.TYPINGHANDLE);
        message.put(ClientConstants.TYPINGSTATUS, MessageConstants.NOTTYPING);
        cl.send(message);
    }

    public void confirmSeen(String peerID, String seenAt) {
        CommunicationLink cl = this.cls.get(peerID);
        HashMap<String, String> message = new HashMap();
        message.put(GeneralConstants.REQUESTTYPEATTR, MessageConstants.SEENHANDLE);
        message.put(ClientConstants.SEENTIME, seenAt);
        cl.send(message);
    }

}
