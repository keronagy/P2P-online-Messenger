/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlinep2pmessenger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import java.lang.reflect.Method;
import javafx.scene.layout.VBox;
import server.PeerClient;

/**
 *
 * @author koko_
 */
public class CustomGroupOptionBtn extends JFXButton {

    JFXPopup RoomPopUp = new JFXPopup();
    VBox BtnsPop;

    public CustomGroupOptionBtn(PeerClient peer, String roomID, String adminID) {
        try {
            this.setText("Room Options");
            BtnsPop = new VBox();
            addOption(peer, roomID, "Leave Room", PeerClient.class.getMethod("leaveRoom", String.class));
            if (peer.getId().equals(adminID) || peer.isAdmin()) {
                addOption(peer, roomID, "Delete Room", PeerClient.class.getMethod("deleteRoom", String.class));
            }
            RoomPopUp.setPopupContent(BtnsPop);
            JFXButton GroupOptions = new JFXButton("Group Options");
        } catch (Exception ex) {
        }
    }

    public void addOption(PeerClient peer, String roomID, String name, Method f) {

        JFXButton Option = new JFXButton(name);
        Option.setOnMouseClicked(e -> {
            try {
                f.invoke(peer, roomID);
                RoomPopUp.hide();
            } catch (Exception ex) {
                //Logger.getLogger(CustomGroupOptionBtn.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        BtnsPop.getChildren().add(Option);
    }

    public JFXPopup getRoomPopUp() {
        return RoomPopUp;
    }

}
