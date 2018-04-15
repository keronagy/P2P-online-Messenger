/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlinep2pmessenger;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author koko_
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private TextField searchTxt;
    @FXML
    private TextField ChatTxt;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       // searchTxt.setStyle("-fx-text-fill: white;");
    }    
    
    public void appendEmoji()
    {
        byte[] emojiBytes = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x83};
        String emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
        ChatTxt.appendText(emojiAsString);
    }
    
}
