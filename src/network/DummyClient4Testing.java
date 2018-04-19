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
import java.net.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import utility.CallbackOnReceiveHandler;
import java.util.Scanner;

/**
 *
 * @author fadia
 */
public class DummyClient4Testing {
    public static void main(String[] args) throws ClassNotFoundException {
        CommunicationLink cl = null;
        try {
            Socket s = new Socket("localhost", 5555);
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            HashMap<String, String> myarr =  new HashMap<>();
            myarr.put(1+"", 1+"");
            myarr.put(2+"", 2+"");
            myarr.put(3+"", 3+"");
            myarr.put(4+"", 4+"");
            for(int i = 0; i < 5; i++){
                dos.writeUTF(i+"");
                dos.flush();
                System.out.println(dis.readUTF());
                
                oos.writeObject(myarr);
                oos.flush();
                System.out.println(ois.readObject());
            }
            
//            cl = CommunicationLink.generateCommunicationLink(new CallbackOnReceiveHandler() {
//                @Override
//                public void handleReceivedData(ArrayList<String> msg) {
//                    
//                    System.out.println(msg.toString());
//                }
//            },"client",s);
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
