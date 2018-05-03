/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlinep2pmessenger;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Islam
 */
public class ClientTuple {

    private String ip;
    private String name;
    private SimpleStringProperty status;

    public ClientTuple(String ip, String name, String status) {
        this.status = new SimpleStringProperty();

        this.ip = ip;
        this.name = name;
        this.status.setValue(status);
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
        this.status.setValue(status);
    }

    public void setName(String name) {
        this.name = name;
    }

    public SimpleStringProperty getStatus() {
        return status;
    }

}
