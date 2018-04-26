/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
        } catch (Exception ex) {
            System.out.println("Connection failed");
        }
    }

    public void receivePeerConnection(Socket s) {
        try {
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            //CommunicationLink cl = CommunicationLink.generateCommunicationLink(this, s);
            String peerID = ois.readUTF();
            //this.cls.put(peerID, cl);
        } catch (Exception e) {
        }

    }

    public void connectToPeer(String peerID, String ip, int port) {
        CommunicationLink cl = null;

        try {
            Socket s = new Socket(ip, port);
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            //cl = CommunicationLink.generateCommunicationLink(this, s);
            oos.writeObject(this.id);

            // this.cls.put(peerID, cl);
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
        message.put(GeneralConstants.CLIENTIDATTR, this.id);
        message.put(MessageConstants.MESSAGE, msg);
        cl.send(message);
    }

}
