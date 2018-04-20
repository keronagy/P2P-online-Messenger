/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;
import java.net.Socket;
import java.util.HashMap;
import network.CommunicationLink;
import utility.GeneralConstants;
import utility.MessageConstants;
import utility.ServerConstants;
/**
 *
 * @author fadia
 */
public class Room{
    private String id;
    private String adminID;
    private String name;
    private HashMap<String, Client> participants;

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

    public Room(String id, Client admin, String name) {
        this.id = id;
        this.adminID = admin.getId();
        this.name = name;
        participants = new HashMap();
        participants.put(this.adminID, admin);
    }
    
    public void addClient(Client client)
    {
        sendNewParticipantToOtherParticipants(client);
        sendParticipants(client);
        participants.put(client.getId(), client);
    }
    public void removeClient(Client client)
    {
        participants.remove(client.getId());
        if(participants.isEmpty())
            deleteRoom();
        if(client.getId().equals(adminID))
            adminID = participants.keySet().iterator().next();
    }
    public void sendParticipants(Client newClient) {
        //need to put before adding in the new client so s/he doesnt get sent to her/im self
        participants.values().forEach((c) -> {
            sendClient(c, newClient.getCommunicationLink());
        });
    }
    public void sendClient(Client c, CommunicationLink cl) {
        HashMap<String, String> message = new HashMap<>();
        message.put(GeneralConstants.UPDATETYPEATTR, ServerConstants.ADDNEWCLIENTTOROOMORDER);
        message.put(GeneralConstants.CLIENTIDATTR, c.getId());
        message.put(GeneralConstants.CLIENTNAMEATTR, c.getName());
        message.put(GeneralConstants.CLIENTSTATUSATTR, c.getStatus());
        cl.send(message);
    }
    private void sendNewParticipantToOtherParticipants(Client newClient){
        
        participants.values().forEach((c) -> {
            sendClient(newClient, c.getCommunicationLink());
        });
    }
    public void sendMessageToParticipants(String senderID,String msg)
    {
        HashMap<String, String> message = new HashMap();
        message.put(MessageConstants.MESSAGEFROM, MessageConstants.FROMROOM);
        message.put(GeneralConstants.ROOMIDATTR, this.getId());
        message.put(GeneralConstants.SENDERIDATTR, senderID);
        message.put(MessageConstants.MESSAGE, msg);
        participants.values().forEach((c) -> {
            if(!c.getId().equals(senderID))
                c.getCommunicationLink().send(message);
        });
    }
    private void deleteRoom() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
