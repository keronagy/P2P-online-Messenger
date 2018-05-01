/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlinep2pmessenger;

import com.jfoenix.controls.JFXPopup;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author koko_
 */
public class CustomStackPane extends StackPane {
    private String ID;
    private String Name;
    private StringProperty UserStatus;
    private Label lbl = new Label();
    private Label lbl2 = new Label();

    public CustomStackPane(String RoomID, String RoomName) {
        
        this.ID = RoomID;
        this.Name = RoomName;
        this.getStyleClass().add("group-pane");
        this.setPadding(new Insets(5));
        
        lbl.setPadding(new Insets(5));
        lbl.setText(RoomName);
        lbl.setTextFill(Color.BLACK);
        this.getChildren().add(lbl);  
    }
    public CustomStackPane(String ClientID, String ClientName, String Status) {
        
        this.ID = ClientID;
        this.Name = ClientName;
        this.UserStatus.setValue(Status);
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
    
    
}
