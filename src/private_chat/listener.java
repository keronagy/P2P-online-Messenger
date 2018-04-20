/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package private_chat;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author khaled
 */
public class listener implements Runnable {

    ServerSocket ss;
    Socket sc;

    @Override
    public void run() {
        try {
            ss = new ServerSocket(12345);
            sc = ss.accept();
            System.out.println("server started");
            privateChat pc = new privateChat(ss, sc);
            
        } catch (IOException ex) {
        }
    }

}
