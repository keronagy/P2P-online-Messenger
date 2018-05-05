/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlinep2pmessenger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import server.PeerClient;

/**
 *
 * @author koko_
 */
public class CustomStackPane extends StackPane {

    private String ID;
    private String Name;
    private String adminID;
    private SimpleStringProperty UserStatus;
    private Label lbl = new Label();
    private Label lbl2 = new Label();
    private JFXPopup KickPop= new JFXPopup(); ;
    private VBox BtnsPop = new VBox();
    public CustomStackPane(String RoomID, String RoomName, String adminID) {
        super();
        this.ID = RoomID;
        this.Name = RoomName;
        this.adminID = adminID;
        this.getStyleClass().add("group-pane");
        this.setPadding(new Insets(5));

        lbl.setPadding(new Insets(5));
        lbl.setText(RoomName);
        lbl.setTextFill(Color.CYAN);
        this.getChildren().add(lbl);
    }

    public CustomStackPane(String ClientID, String ClientName, SimpleStringProperty Status, PeerClient peer) {
        super();

        if(peer.isAdmin()){
            System.out.println("enterd popup init");
         
        JFXButton kickClient = new JFXButton("Kick Client");
        
        
        addOption("Kick Client",e->{
            peer.kickClient(ClientID);
                KickPop.hide();
                });
        
        }
        KickPop.setPopupContent(BtnsPop);
    
        this.ID = ClientID;
        this.Name = ClientName;
        this.UserStatus = Status;
        this.getStyleClass().add("group-pane");
        this.setPadding(new Insets(5));
        VBox lblsvbox = new VBox();

        lbl.setPadding(new Insets(5));
        lbl.setText(Name);
        lbl.setTextFill(Color.CYAN);
        lbl.setPadding(new Insets(5));

        lbl2.setPadding(new Insets(5));
        lbl2.textProperty().bind(UserStatus);
        lbl2.setTextFill(Color.BLACK);
        lblsvbox.getChildren().add(lbl);
        lblsvbox.getChildren().add(lbl2);
        this.getChildren().add(lblsvbox);
    }
    public void addOption(String name, EventHandler e){
        JFXButton Option = new JFXButton(name);
        Option.setOnMouseClicked(e);
        BtnsPop.getChildren().add(Option);
        
    }
   

    public void setID(String RoomID) {
        this.ID = RoomID;
    }

    public void setName(String RoomName) {
        this.Name = RoomName;
    }

    public void setUserStatus(String UserStatus) {
        this.UserStatus.setValue(UserStatus);
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public String getUserStatus() {
        return UserStatus.getValue();
    }

    public String getAdminID() {
        return adminID;
    }

    public JFXPopup getKickPop() {
        return KickPop;
    }
    
    
    

}
