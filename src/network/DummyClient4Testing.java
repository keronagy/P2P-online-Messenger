/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import utility.CallbackOnReceiveHandler;
import java.util.Scanner;

/**
 *
 * @author fadia
 */
public class DummyClient4Testing {
    public static void main(String[] args) {
        CommunicationLink cl = null;
        try {
            Socket s = new Socket("localhost", 5555);
            cl = CommunicationLink.generateCommunicationLink(new CallbackOnReceiveHandler() {
                @Override
                public void handleReceivedData(ArrayList<String> msg) {
                    
                    System.out.println(msg.toString());
                }
            },"client",s);
        } catch (IOException ex) {
            Logger.getLogger(DummyServer4Testing.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scanner s = new Scanner(System.in);
        while(true){
            ArrayList<String> msg = new ArrayList<>();
            msg.add(s.next());
            cl.send(msg);
        }
    }
}
