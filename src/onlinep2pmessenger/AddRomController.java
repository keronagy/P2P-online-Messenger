/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlinep2pmessenger;

import com.jfoenix.controls.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author koko_
 */
public class AddRomController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXTextField RoomName;
    @FXML
    private Label error;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public String onClose()
    {
        return RoomName.getText();
    }
    public void close(){
        if(RoomName.getText().equals(""))
        {
            error.setText("please enter the name");
            error.setStyle("-fx-background-color: red;");
            
        }
        else
        {
            RoomName.getScene().getWindow().hide();

        }
    }
    
}
