/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlinep2pmessenger;

/**
 *
 * @author Islam
 */
public class ClientTuple {
    private String ip;
    private String name;
    private String status;

    public ClientTuple(String ip, String name, String status) {
        this.ip = ip;
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }
    
    
}
