/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;
import java.net.Socket;
import java.util.HashMap;
import utility.CallbackOnReceiveHandler;
/**
 *
 * @author fadia
 */
public class Room implements CallbackOnReceiveHandler{
    private String id;
    private String name;
    private HashMap<String, Socket> participants;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    @Override
    public void handleReceivedData(HashMap<String, String> msg){
        
    }
}
