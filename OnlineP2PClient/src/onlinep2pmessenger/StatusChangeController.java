/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlinep2pmessenger;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author koko_
 */
public class StatusChangeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXTextField StatusTxt;
    @FXML
    private Label error;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        StatusTxt.setOnKeyPressed(e->onEnter(e));
        
    }

    public String onClose() {
        return StatusTxt.getText();
    }

    public void close() {
        String txt = StatusTxt.getText();
        if (txt.equals("") || txt.replace(" ", "").length() == 0) {
            error.setText("please enter the name");
            error.setStyle("-fx-background-color: red;");

        } else {
            StatusTxt.getScene().getWindow().hide();

        }
    }
    
    public void onEnter(KeyEvent event)
    {
        if(event.getCode().equals(KeyCode.ENTER) || event.getCharacter().getBytes()[0] == '\n' || event.getCharacter().getBytes()[0] == '\r') {
        // your action
        close();
        }
    }

}
    

