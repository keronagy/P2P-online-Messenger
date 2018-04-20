/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.net.Socket;
import network.CommunicationLink;

/**
 *
 * @author fadia
 */
public class Client {
    protected String id;
    protected String status;
    protected String name;
    protected CommunicationLink cl;

    public Client(String id, String status, String name, CommunicationLink cl) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.cl = cl;
    }
    public Client(String id, String status, String name ) {
        this.id = id;
        this.status = status;
        this.name = name;
    }    

   
    
    /**
     * @return the id
     */
    public CommunicationLink getCommunicationLink() {
        return this.cl;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    
}
