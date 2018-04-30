/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlinep2pmessenger;

import javafx.scene.layout.StackPane;

/**
 *
 * @author koko_
 */
public class RoomStackPane extends StackPane {
    private String RoomID;
    private String RoomName;

    public RoomStackPane(String RoomID, String RoomName) {
        
        this.RoomID = RoomID;
        this.RoomName = RoomName;
    }

    public void setRoomID(String RoomID) {
        this.RoomID = RoomID;
    }

    public void setRoomName(String RoomName) {
        this.RoomName = RoomName;
    }

    public String getRoomID() {
        return RoomID;
    }

    public String getRoomName() {
        return RoomName;
    }
    
    
}
