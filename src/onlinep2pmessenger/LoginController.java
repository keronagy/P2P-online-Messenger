/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlinep2pmessenger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void GoToHomePage()
    {
        
        Parent root;
        try {
            String name = NameTxt.getText();
            String IP = IPTxt.getText();
            String Port = PortTxt.getText();
            if(name.equals("") || name.replace(" ", "").length()==0 ||IP.equals("") || IP.replace(" ", "").length()==0 || Port.equals("") || Port.replace(" ", "").length()==0 )
        {
            error.setText("please enter the name");
            error.setStyle("-fx-background-color: red;");
        }
            else
            {
                FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("FXMLDocument.fxml"));
            
            
            
            FXMLDocumentController controller = new FXMLDocumentController();
            controller.setUserName(NameTxt.getText());
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
            
            stage.show();
        }

        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
}
