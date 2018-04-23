/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.CommunicationLink;
import server.Client;
import server.PeerClient;
import utility.CallbackOnReceiveHandler;

/**
 *
 * @author khaled
 */
public class listener extends Thread {

    ServerSocket ss;
    Socket sc;
    String userID;
    PeerClient pc;
    int port;

    public listener(String userID, int port) {
        this.userID = userID;
        pc = new PeerClient("Online", userID);
        this.port = port;
    }

    @Override
    public void run() {
        try {
            ss = new ServerSocket(port);
            while (true) {
                sc = ss.accept();
                pc.receivePeerConnection(sc);
            }
        } catch (IOException e) {
        }
    }

}
