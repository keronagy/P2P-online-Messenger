/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import server.Client;
import server.Room;
import private_chat.*;

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
    ArrayList<Pair<String, VBox>> vboxes = new ArrayList<>();
    int id = 1;
    @FXML
    private VBox groupVbox;
    ArrayList<Pair<String, StackPane>> groupVboxes = new ArrayList<>();
    @FXML
    private Button TestGroup;
    @FXML
    private ScrollPane chatScroll;
    @FXML
    private VBox UsersVbox;
    ArrayList<Pair<String, VBox>> usersVboxes = new ArrayList<>();
    @FXML
    private VBox UserTabVbox;
    @FXML
    private VBox GroupTabVbox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            final Font f = Font.loadFont(new FileInputStream(new File("OpenSansEmoji.ttf")), 12);
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
        TestGroup.setOnMouseClicked(e -> createUserPane("u1", "staus", "kero"));

    }

    public void appendEmoji(int index) {
        byte[] emojiBytes;
        String emojiAsString;
        switch (index) {
            case 1:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x82};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 2:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x83};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 3:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x84};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 4:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x89};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 5:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x8D};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 6:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x98};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 7:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x94};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 8:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x9C};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 9:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0xA1};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 10:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0xA2};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 11:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x99, (byte) 0x88};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 12:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x91};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 13:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0xA6};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 14:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0xB4};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString + "  ");
                break;
            case 15:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x8E};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 16:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x91, (byte) 0x8D};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            default:
                System.out.println("error");
                break;
        }
    }

    public void showEmojis() {
        if (emojispanevis == false) {
            emojiPane.setVisible(true);
            emojispanevis = true;
        } else {
            emojiPane.setVisible(false);
            emojispanevis = false;
        }

    }

    public void AddNewUser(Client c1) {
        StackPane user = new StackPane();
        user.getStyleClass().add("group-pane");
        VBox lblsvbox = new VBox();
        Label lbl = new Label();
        lbl.setPadding(new Insets(5));
        lbl.setText(c1.getName());
        lbl.setTextFill(Color.CYAN);

        Label lbl2 = new Label();
        lbl2.setPadding(new Insets(5));
        lbl2.setText(c1.getStatus());
        lbl2.setTextFill(Color.BLACK);
        lblsvbox.getChildren().add(lbl);
        lblsvbox.getChildren().add(lbl2);
        user.getChildren().add(lblsvbox);
        user.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        AddTab(c1.getId(), c1.getName());
                        createUserPane(c1.getId(), c1.getStatus(), c1.getName());
                    }
                }
            }
        });
        UserTabVbox.setSpacing(5);
        UserTabVbox.getChildren().add(user);
    }

    public void AddNewGroup(Room r1) {
        StackPane group = new StackPane();
        group.getStyleClass().add("group-pane");
        Label lbl = new Label();
        lbl.setPadding(new Insets(5));
        lbl.setText(r1.getName());
        lbl.setTextFill(Color.BLACK);
        group.getChildren().add(lbl);
        group.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        AddTab(r1.getId(), r1.getId());
                    }
                }
            }
        });
        GroupTabVbox.setSpacing(5);
        GroupTabVbox.getChildren().add(group);
    }

    public void AddTab(String ID, String UserName) {

        Tab t = new Tab(UserName);

        tabs.getTabs().add(t);
        t.setId(ID);
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
        scrollPane.vvalueProperty().bind(root.heightProperty());
        vboxes.add(new Pair(t.getId(), root));
        for (int i = 0; i < vboxes.size(); i++) {
            if (vboxes.get(i).getKey() == tabs.getSelectionModel().getSelectedItem().getId()) {
                //    vboxes.get(i).getValue().getChildren().add(rect);
            }
        }
        for (int i = 0; i < vboxes.size(); i++) {
            System.out.println(vboxes.get(i).getKey().toString());
        }

        System.out.println();
    }

    public void onTabClose(String id) {
        for (int i = 0; i < vboxes.size(); i++) {
            if (vboxes.get(i).getKey() == id) {
                vboxes.remove(i);
                break;
            }
        }
        for (int i = 0; i < vboxes.size(); i++) {
            System.out.println(vboxes.get(i).getKey().toString());
        }
        System.out.println();

    }

    public void receive(String Msg, String ID, String UserName) {
        boolean found = false;
        for (int i = 0; i < vboxes.size(); i++) {
            if (vboxes.get(i).getKey() == ID) {
                found = true;
                StackPane p1 = new StackPane();

                p1.setStyle("-fx-background-color: #fff; -fx-background-radius: 30; -fx-border-radius: 30; -fx-border-width:5;");
                p1.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
                p1.setMinHeight(Region.USE_PREF_SIZE);
                Label lbl1 = new Label(Msg);
                lbl1.setPadding(new Insets(10));
                lbl1.setText(Msg);
                lbl1.setTextFill(Color.BLACK);
                HBox hob = new HBox();
                hob.setPrefWidth(470);
                hob.setAlignment(Pos.CENTER_RIGHT);
                p1.getChildren().add(lbl1);
                hob.getChildren().add(p1);
                vboxes.get(i).getValue().getChildren().add(hob);
                break;
            }

        }
        if (found == false) {
            AddTab(ID, UserName);
            createGroupPane(ID, UserName);
            StackPane p1 = new StackPane();

            p1.setStyle("-fx-background-color: #fff; -fx-background-radius: 30; -fx-border-radius: 30; -fx-border-width:5;");
            p1.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            p1.setMinHeight(Region.USE_PREF_SIZE);
            Label lbl1 = new Label(Msg);
            lbl1.setPadding(new Insets(10));
            lbl1.setText(Msg);
            lbl1.setTextFill(Color.BLACK);
            HBox hob = new HBox();
            hob.setPrefWidth(470);
            hob.setAlignment(Pos.CENTER_RIGHT);
            p1.getChildren().add(lbl1);
            hob.getChildren().add(p1);
            vboxes.get(vboxes.size() - 1).getValue().getChildren().add(hob);
        }

    }

    public void sendBtn() {
        if (!ChatTxt.getText().equals("")) {
            for (int i = 0; i < vboxes.size(); i++) {
                if (vboxes.get(i).getKey() == tabs.getSelectionModel().getSelectedItem().getId()) {
                    String msg = ChatTxt.getText();

                    StackPane p = new StackPane();
                    p.setMinHeight(Region.USE_PREF_SIZE);
                    p.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
                    p.setStyle("-fx-background-color: #00FFFF; -fx-background-radius: 30; -fx-border-radius: 30; -fx-border-width:5;");
                    Label lbl = new Label(msg);
                    lbl.setPadding(new Insets(10));
                    lbl.setText(msg);
                    lbl.setTextFill(Color.BLACK);
                    p.getChildren().add(lbl);
                    vboxes.get(i).getValue().getChildren().add(p);
                    ChatTxt.setText("");
                    emojiPane.setVisible(false);
                    break;
                }
            }

        }
    }

    public void createUserPane(String UserID, String Status, String UserName) {
        StackPane user = new StackPane();
        user.getStyleClass().add("group-pane");
        VBox lblsvbox = new VBox();
        Label lbl = new Label();
        lbl.setPadding(new Insets(5));
        lbl.setText(UserName);
        lbl.setTextFill(Color.CYAN);

        Label lbl2 = new Label();
        lbl2.setPadding(new Insets(5));
        lbl2.setText(Status);
        lbl2.setTextFill(Color.BLACK);
        lblsvbox.getChildren().add(lbl);
        lblsvbox.getChildren().add(lbl2);
        user.getChildren().add(lblsvbox);
        user.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        AddTab(UserID, UserName);
                    }
                }
            }
        });
        usersVboxes.add(new Pair<>(UserID, lblsvbox));
        UsersVbox.setSpacing(5);
        UsersVbox.getChildren().add(user);

    }

    public void createGroupPane(String groupID, String GroupName) {
        StackPane group = new StackPane();
        group.getStyleClass().add("group-pane");
        Label lbl = new Label();
        lbl.setPadding(new Insets(5));
        lbl.setText(GroupName);
        lbl.setTextFill(Color.BLACK);
        group.getChildren().add(lbl);
        group.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        AddTab(groupID, GroupName);
                    }
                }
            }
        });
        groupVboxes.add(new Pair<>(groupID, group));
        groupVbox.setSpacing(5);
        groupVbox.getChildren().add(group);

    }

    public void TestBtn() throws IOException {
        Client c1 = new Client("1", "online", "kero");
        Client c2 = new Client("2", "online", "kord");
        Client c3 = new Client("3", "online", "fadi");

        AddNewUser(c1);
        AddNewUser(c2);
        AddNewUser(c3);
        //test with kyrollos
        class tmp implements Runnable {

            @Override
            public void run() {
                privateChat pc = new privateChat(null, null);
                pc.Join("41.47.189.54", 1234);
            }
        }

        Thread t = new Thread(new tmp());
    }
}
