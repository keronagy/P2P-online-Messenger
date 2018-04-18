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
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

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
    @FXML
    private Button AddTab;
    @FXML
    private TabPane tabs;
    ArrayList <Pair<String,VBox> > vboxes = new ArrayList<>();
    int id=1;
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
    
    public void AddTabBtn()
    {
        
        Tab t = new Tab("kero"+ id);
        
        tabs.getTabs().add(t);
        t.setId("kero"+ id);
        t.setOnCloseRequest((e -> onTabClose(t.getId())));
        id++;
        tabs.getSelectionModel().select(t);
        ScrollPane scrollPane = new ScrollPane();
        t.setContent(scrollPane);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        final Rectangle rect = new Rectangle(200, 200, 800, 600);
        rect.setFill(Color.BLACK);
        VBox root = new VBox();
        
       // root.getChildren().addAll(new Button("button1"), new Button("button2"), new Button("button3"));
        root.setSpacing(10);
        root.setPadding(new Insets(10));
        scrollPane.setContent(root);
        vboxes.add(new Pair(t.getId(),root));
        for (int i = 0; i < vboxes.size(); i++) {
            if(vboxes.get(i).getKey() == tabs.getSelectionModel().getSelectedItem().getId()){
            //    vboxes.get(i).getValue().getChildren().add(rect);
            }
        }
        for (int i = 0; i < vboxes.size(); i++) {
                System.out.println(vboxes.get(i).getKey().toString());
            }
        
        System.out.println();
    }
    public void onTabClose(String id)
    {
        for (int i = 0; i < vboxes.size(); i++) {
            if(vboxes.get(i).getKey() == id)
            {
                vboxes.remove(i);
                break;
            }  
        }
        for (int i = 0; i < vboxes.size(); i++) {
                System.out.println(vboxes.get(i).getKey().toString());
            }
        System.out.println();

    }
    
    
    public void sendBtn()
    {   
        if(!ChatTxt.getText().equals(""))
        {
            for (int i = 0; i < vboxes.size(); i++) {
                if(vboxes.get(i).getKey() == tabs.getSelectionModel().getSelectedItem().getId())
                {
                    String msg = ChatTxt.getText();
                    
                    StackPane p = new StackPane();
                    p.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
                    p.setStyle("-fx-background-color: #00FFFF; -fx-background-radius: 30; -fx-border-radius: 30; -fx-border-width:5;");
                    Label lbl = new Label(msg);
                    lbl.setPadding(new Insets(10));
                    lbl.setText(msg);
                    lbl.setTextFill(Color.BLACK);
                    p.getChildren().add(lbl);
                    vboxes.get(i).getValue().getChildren().add(p);

                    Label lbl1 = new Label(msg);
                    
                    StackPane p1 = new StackPane();

                    p1.setStyle("-fx-background-color: #fff; -fx-background-radius: 30; -fx-border-radius: 30; -fx-border-width:5;");
                    p1.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
                    lbl1.setPadding(new Insets(10));
                    lbl1.setText("wooooooow");
                    lbl1.setTextFill(Color.BLACK);
                    HBox hob = new HBox();
                    hob.setPrefWidth(470);
                    hob.setAlignment(Pos.CENTER_RIGHT);
                    p1.getChildren().add(lbl1);
                    hob.getChildren().add(p1);
                    vboxes.get(i).getValue().getChildren().add(hob);
                    
                    StackPane p2 = new StackPane();
                    p2.setMaxSize(Region.USE_COMPUTED_SIZE,Region.BASELINE_OFFSET_SAME_AS_HEIGHT);
                    p2.setStyle("-fx-background-color: #00FFFF; -fx-background-radius: 30; -fx-border-radius: 30; -fx-border-width:5;");
                    Label lbl3 = new Label(msg);
                    lbl3.setMaxWidth(470);
                    lbl3.setWrapText(true);
                    lbl3.setPadding(new Insets(10));
                    lbl3.setText("test test test test testtest test test test testtest test  test test testtest test  test test testtest test  test test testtest test test test testtest test test test testtest test test test test ");
                    lbl3.setTextFill(Color.BLACK);
                    p2.getChildren().add(lbl3);
                    vboxes.get(i).getValue().getChildren().add(p2);
                    ChatTxt.setText("");
                    emojiPane.setVisible(false);
                    break;
                }  
            }
        
        }
    }
}

