/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import server.listener;
import java.io.File;
import java.io.FileInputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author koko_
 */
public class app extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        
        listener t = new listener("from GUI");
        t.start();
        
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        final Font f = Font.loadFont(new FileInputStream(new File("OpenSansEmoji.ttf")), 10);

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/GUI/tabPaneCustomCss.css");

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
