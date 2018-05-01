/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlinep2pmessenger;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;

/**
 *
 * @author koko_
 */
public class RoomCircleBtn extends JFXButton {
    String UserName;
    String UserID;
    StringProperty Status;
    Tooltip StatusTT;
    String RoomID;
            
    public RoomCircleBtn(String UserName, String UserID, String Status,String RoomID) {
        String[] letters = UserName.split(" ");
        if(letters.length <2)
        {
            System.out.println("1 char "+ letters[0].charAt(0)+"");
            this.setText(letters[0].charAt(0)+"");
        }
        else
        {
            System.out.println("else 2 chars "+ letters[0].charAt(0)+letters[1].charAt(0)+"");
            this.setText(letters[0].charAt(0)+""+letters[1].charAt(0)+"");
        }
        this.getStyleClass().add("roundbutton");
        
        Font font= null;
        try {
            font = Font.loadFont(new FileInputStream(new File("Fonts/ingeborg.otf")), 17);
            this.setFont(font);
            this.setMinSize(Region.USE_COMPUTED_SIZE,Region.USE_COMPUTED_SIZE);
            
            
        } catch (FileNotFoundException ex) {
            System.out.println("error");
        }
    }
    
    
}
