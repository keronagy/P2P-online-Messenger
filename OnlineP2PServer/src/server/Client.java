/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import network.CommunicationLink;

/**
 *
 * @author fadia
 */
public class Client {

    private String id;
    private String name;
    private String status;
    private String ip;
    private CommunicationLink server_cl;

    public Client(String id, String ip, String name, String status, CommunicationLink cl) {
        this.id = id;
        this.ip = ip;
        this.name = name;
        this.status = status;
        this.server_cl = cl;

    }

    public Client(String id, String name) {
        this.id = id;
        this.name = name;
    }

    
    public CommunicationLink getCommunicationLink() {
        return this.server_cl;
    }

    public void setCommunicationLink(CommunicationLink server_cl) {
        this.server_cl = server_cl;
    }
    
    

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    


    
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    

}
