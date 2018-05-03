/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlinep2pmessenger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author koko_
 */
public class LoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXButton StartBtn;
    @FXML
    private JFXTextField NameTxt;
    @FXML
    private Label error;
    @FXML
    private JFXTextField IPTxt;
    @FXML
    private JFXTextField PortTxt;

    private Pattern VALID_IPV4_PATTERN = null;
    private Pattern VALID_PORT_NUMBER = null;
    private final String IPADDRESS_PATTERN = "(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])";
    private final String PORT_NUMBER = "^([0-9]{1,4}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        VALID_IPV4_PATTERN = Pattern.compile(IPADDRESS_PATTERN, Pattern.CASE_INSENSITIVE);
        VALID_PORT_NUMBER = Pattern.compile(PORT_NUMBER, Pattern.CASE_INSENSITIVE);
        
    }
    

    public void GoToHomePage() {

        Parent root;
        try {
            String name = NameTxt.getText();
            String IP = IPTxt.getText();
            String Port = PortTxt.getText();
            if(name.equals("") || name.replace(" ", "").length()==0  )
            {
                error.setText("please enter the name");
                error.setStyle("-fx-background-color: red;");
            }
            else if(IP.equals("") || IP.replace(" ", "").length()==0 || !VALID_IPV4_PATTERN.matcher(IP).matches() )
            {
             
                error.setText("please enter valid IP Address");
                error.setStyle("-fx-background-color: red;");
            }
            else if(Port.equals("") || Port.replace(" ", "").length()==0 || !VALID_PORT_NUMBER.matcher(Port).matches() )
            {
                error.setText("please enter valid Port Number");
                error.setStyle("-fx-background-color: red;");
            }
            else
            {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("FXMLDocument.fxml"));



                FXMLDocumentController controller = new FXMLDocumentController();
                controller.setUserName(name);
                controller.setIPAddress(IP);
                controller.setProtNum(Port);
                loader.setController(controller);
                root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                StartBtn.getScene().getWindow().hide();
                stage.setTitle("Regaletna Messenger");
                stage.setHeight(635);
                stage.setWidth(920);
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2); 
                stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);
                stage.setResizable(false);
                stage.setOnCloseRequest(e -> closeWindow());

                stage.show();
            }

            

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void closeWindow() {
        System.exit(0);
    }

}
