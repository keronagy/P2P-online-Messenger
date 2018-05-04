/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlinep2pmessenger;


import java.util.HashMap;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;

/**
 *
 * @author koko_
 */
public class CustomCirclesHBox extends HBox {

    HashMap<String, RoomCircleBtn> circles = new HashMap<>();
    
    public CustomCirclesHBox() {
        this.setMaxHeight(80);
        this.setMinHeight(80);
        this.setStyle("-fx-border-width:5; -fx-border-color: #555; -fx-border-radius: 50px; -fx-background-radius: 50px;");
        this.setPadding(new Insets(13));
  
    }

    public HashMap<String, RoomCircleBtn> getCircles() {
        return circles;
    }

    
    
}
