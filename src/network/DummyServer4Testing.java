/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import utility.CallbackOnReceiveHandler;

/**
 *
 * @author fadia
 */
public class DummyServer4Testing {

    public static void main(String[] args) throws ClassNotFoundException {
        CommunicationLink cl = null;
        try {
            ServerSocket ss = new ServerSocket(5555);
            Socket s = ss.accept();
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());

            for (int i = 0; i < 5; i++) {
                dos.writeUTF(dis.readUTF());
                dos.flush();
                oos.writeObject(ois.readObject());
                oos.flush();
            }

//            cl = CommunicationLink.generateCommunicationLink(new CallbackOnReceiveHandler() {
//                @Override
//                public void handleReceivedData(ArrayList<String> msg) {
//                    System.out.println(msg.toString());
//                }
//            },"server",s);
        } catch (IOException ex) {
            Logger.getLogger(DummyServer4Testing.class.getName()).log(Level.SEVERE, null, ex);
        }
//        Scanner s = new Scanner(System.in);
//        while(true){
//            ArrayList<String> msg = new ArrayList<>();
//            msg.add(s.next());
//            cl.send(msg);
//        }
    }
}
