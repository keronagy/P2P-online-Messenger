/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlinep2pmessenger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

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
    private ImageView i1;
    @FXML
    private ImageView i2;
    @FXML
    private ImageView i3;
    @FXML
    private ImageView i4;
    @FXML
    private ImageView i5;
    @FXML
    private ImageView i6;
    @FXML
    private ImageView i7;
    @FXML
    private ImageView i8;
    @FXML
    private ImageView i9;
    @FXML
    private ImageView i10;
    @FXML
    private ImageView i11;
    @FXML
    private ImageView i12;
    @FXML
    private ImageView i13;
    @FXML
    private ImageView i14;
    @FXML
    private ImageView i15;
    @FXML
    private ImageView i16;
    @FXML
    private Pane emojiPane;
    private boolean emojispanevis = false;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            final Font f = Font.loadFont(new FileInputStream(new File("OpenSansEmoji.ttf")), 18);
            ChatTxt.setFont(f);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        i1.setOnMouseClicked(e -> appendEmoji(1));
        i2.setOnMouseClicked(e -> appendEmoji(2));
        i3.setOnMouseClicked(e -> appendEmoji(3));
        i4.setOnMouseClicked(e -> appendEmoji(4));
        i5.setOnMouseClicked(e -> appendEmoji(5));
        i6.setOnMouseClicked(e -> appendEmoji(6));
        i7.setOnMouseClicked(e -> appendEmoji(7));
        i8.setOnMouseClicked(e -> appendEmoji(8));
        i9.setOnMouseClicked(e -> appendEmoji(9));
        i10.setOnMouseClicked(e -> appendEmoji(10));
        i11.setOnMouseClicked(e -> appendEmoji(11));
        i12.setOnMouseClicked(e -> appendEmoji(12));
        i13.setOnMouseClicked(e -> appendEmoji(13));
        i14.setOnMouseClicked(e -> appendEmoji(14));
        i15.setOnMouseClicked(e -> appendEmoji(15));
        i16.setOnMouseClicked(e -> appendEmoji(16));
        
    }    
    
    public void appendEmoji(int index)
    {
        byte[] emojiBytes ;
        String emojiAsString ;
        switch(index)
        {
            case 1:
                //\xF0\x9F\x98\x82
                emojiBytes = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x82};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 2:
                emojiBytes = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x83};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 3:
                emojiBytes = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x84};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 4:
                emojiBytes = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x89};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 5:
                emojiBytes = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x8D};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 6:
                emojiBytes = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x98};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 7:
                emojiBytes = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x94};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 8:
                emojiBytes = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x9C};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 9:
                emojiBytes = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0xA1};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 10:
                emojiBytes = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0xA2};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 11:
                emojiBytes = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x99, (byte)0x88};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 12:
                emojiBytes = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x91};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 13:
                emojiBytes = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0xA6};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 14:
                emojiBytes = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0xB4};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString+"  ");
                break;
            case 15:
                emojiBytes = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x8E};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 16:
                emojiBytes = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x91, (byte)0x8D};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            default:
                System.out.println("error");
                break; 
        }
    }
    public void showEmojis()
    {
        if(emojispanevis == false)
        {
            emojiPane.setVisible(true);
            emojispanevis = true;
        }
        else
        {
            emojiPane.setVisible(false);
            emojispanevis = false;
        }
        
    }
}
