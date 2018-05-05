/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlinep2pmessenger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;
import server.PeerClient;

/**
 *
 * @author koko_
 */
public class CustomGroupOptionBtn extends JFXButton{
    JFXPopup RoomPopUp = new JFXPopup();
    VBox BtnsPop;
    public CustomGroupOptionBtn(PeerClient peer , String roomID, String adminID) {
                
    this.setText("Room Options");
    BtnsPop = new VBox();
    addOption("Leave Room",e->{
        peer.LeaveRoom(roomID);
        RoomPopUp.hide();
    });
    if(peer.getId().equals(adminID) || peer.isAdmin())
    addOption("Delete Room",e->{peer.deleteRoom(roomID);
        RoomPopUp.hide();
    });
    RoomPopUp.setPopupContent(BtnsPop);
    JFXButton GroupOptions = new JFXButton("Group Options");
    }
    public void addOption(String name, EventHandler e){
        JFXButton Option = new JFXButton(name);
        Option.setOnMouseClicked(e);
        BtnsPop.getChildren().add(Option);
        
    }

    public JFXPopup getRoomPopUp() {
        return RoomPopUp;
    }
    
    
    
    
}
