/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package private_chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author khaled
 */
public class privateChat {

    Socket sc;
    ServerSocket ss;
    DataInputStream dis;
    DataOutputStream dos;

    public privateChat(ServerSocket ss, Socket sc) {
        this.ss = ss;
        this.sc = sc;
    }

    public privateChat() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void Join(String IP, int port) {
        try {
            this.sc = new Socket(IP, port);
            startChat();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void startChat() {

        while (true) {
            try {
                dis = new DataInputStream(sc.getInputStream());
                dos = new DataOutputStream(sc.getOutputStream());
                System.out.println(dis.readUTF());
                Scanner s = new Scanner(System.in);
                dos.writeUTF(s.nextLine());

            } catch (IOException ex) {
            }

        }

    }

}
