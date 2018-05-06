/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlinep2pmessenger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import server.PeerClient;
import utility.CallbackOnReceiveHandler;
import utility.Constants;

/**
 *
 * @author koko_
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    AnchorPane Toproot;
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
    ConcurrentHashMap<String, VBox[]> vboxes = new ConcurrentHashMap();
    @FXML
    private VBox groupVbox;
    HashMap<String, StackPane> groupVboxes = new HashMap();
    @FXML
    private Button TestGroup;
    @FXML
    private ScrollPane chatScroll;
    @FXML
    private VBox UsersVbox;
    HashMap<String, StackPane> usersVboxes = new HashMap();
    @FXML
    private VBox UserTabVbox;
    HashMap<String, StackPane> UserTabVboxes = new HashMap();
    @FXML
    private VBox GroupTabVbox;
    HashMap<String, StackPane> GroupTabVboxes = new HashMap();
    @FXML
    private JFXButton AddRoomBtn;
    @FXML
    private Button receiveTest;
    HashMap<String, CustomCirclesHBox> membersInRomPane = new HashMap();
    private JFXPopup LeftUsersPopUp = new JFXPopup();
    private JFXPopup EmojiesPopUp = new JFXPopup();
    VBox EmojiesPopupVbox = new VBox();
    @FXML
    private ImageView emojies;
    @FXML
    Label TypingLbl;

    private PeerClient hamed; //client
    private HashMap<String, ClientTuple> clients = new HashMap();
    private String userName;
    private String IPAddress;
    private int PortNum;
    private boolean type = false;
    private final String ClientSound = "Sounds/ClientNotification.mp3";     // For example
    private final String RoomSound = "Sounds/RoomNotification.mp3";     // For example
    Media ClientSoundMedia = new Media(new File(ClientSound).toURI().toString());
    Media RoomSoundMedia = new Media(new File(RoomSound).toURI().toString());

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public void setProtNum(String ProtNum) {
        this.PortNum = Integer.parseInt(ProtNum);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        hamed = new PeerClient(Constants.INITSTATUS, userName);
        hamed.connect(this.IPAddress, this.PortNum, new ServerHandler());

        handleNewConnections();


        ChatTxt.setOnKeyPressed(e -> {

            if (e.getCode().equals(KeyCode.ENTER) || e.getCharacter().getBytes()[0] == '\n' || e.getCharacter().getBytes()[0] == '\r') {
                sendBtn();
            }

        });

        tabs.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
                try {
                    if (t.getId().charAt(0) == 'c') {
                        hamed.stoppedTyping(t.getId());
                        TypingLbl.setText("");
                        type = false;
                    }
                } catch (Exception ex) {
                }

            }
        }
        );
        ImageView[] imgsv = {i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13, i14, i15, i16};

        for (int i = 1; i <= 16; i++) {
            final int value = i;
            imgsv[i - 1].setOnMouseClicked(e -> appendEmoji(value));
        }
        ChatTxt.textProperty().addListener(e -> typing());

        EmojiesPopupVbox.setStyle("-fx-background-color:  #2e2f30;");
        for (int i = 0; i < 16; i += 4) {
            HBox kk = new HBox(imgsv[i], imgsv[i + 1], imgsv[i + 2], imgsv[i + 3]);
            kk.setSpacing(5);
            kk.setPadding(new Insets(5));
            EmojiesPopupVbox.getChildren().add(kk);

        }

        EmojiesPopUp.setPopupContent(EmojiesPopupVbox);

        emojies.setOnMouseClicked(e -> showEmojis(e));
        AddRoomBtn.setOnMouseClicked(e -> AddRoomDialog());
    }

    public void createPrivateChat(String clientID) {
        hamed.connectToPeer(clientID, clients.get(clientID).getIp(), Constants.SERVERPORT + 1, new PeerHandler(clientID)
        );
        JoinClient(clientID);
    }

    private void handleNewConnections() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    PeerHandler handler = new PeerHandler();
                    handler.setPeerID(hamed.waitForConnection(handler));
                }
            }
        }
        ).start();
    }

    public void appendEmoji(int index) {
        byte[] emojiBytes = null;
        String emojiAsString;
        switch (index) {
            case 1:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x82};
                break;
            case 2:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x83};
                break;
            case 3:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x84};
                break;
            case 4:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x89};
                break;
            case 5:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x8D};
                break;
            case 6:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x98};
                break;
            case 7:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x94};
                break;
            case 8:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x9C};
                break;
            case 9:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0xA1};
                break;
            case 10:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0xA2};
                break;
            case 11:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x99, (byte) 0x88};
                break;
            case 12:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x91};
                break;
            case 13:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0xA6};
                break;
            case 14:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0xB4};
                break;
            case 15:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x8E};
                break;
            case 16:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x91, (byte) 0x8D};
                break;
            default:
                System.out.println("error");
                break;
        }
        emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
        ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
    }

    public void showEmojis(MouseEvent e) {
        EmojiesPopUp.show(Toproot, JFXPopup.PopupVPosition.BOTTOM, JFXPopup.PopupHPosition.LEFT, e.getX() + 200, e.getY() - 50);

    }

    public void AddNewUser(String ID, String Name, SimpleStringProperty Status) {
        CustomStackPane user = new CustomStackPane(ID, Name, Status, hamed);
        user.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {

                user.getKickPop().show(user, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, e.getX(), e.getY());
            } else if (e.getButton().equals(MouseButton.PRIMARY)) {
                if (e.getClickCount() == 2) {
                    if (usersVboxes.get(ID) == null) {
                        createPrivateChat(ID);
                    }
                }
            }
        });

        UserTabVbox.setSpacing(5);
        UserTabVbox.getChildren().add(user);
        UserTabVboxes.put(ID, user);
    }

    public void AddNewGroup(String roomName, String roomID, String adminID) {
        CustomStackPane group = new CustomStackPane(roomID, roomName, adminID);
        group.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        if (groupVboxes.get(roomID) == null) {
                            hamed.joinRoom(roomID);
                        }
                    }
                }
            }
        });
        GroupTabVbox.setSpacing(5);
        GroupTabVbox.getChildren().add(group);
        GroupTabVboxes.put(roomID, group);
    }

    public void AddTab(String ID, String UserName, String adminID) {
        if (vboxes.get(ID) == null) {
            Tab t = new Tab(UserName);

            VBox root1 = new VBox();
            tabs.getTabs().add(t);
            t.setId(ID);
            t.setOnCloseRequest((e -> onTabClose(t.getId())));
            t.setOnSelectionChanged((e -> onTabClick(t.getId())));
            ScrollPane scrollPane = new ScrollPane();
            t.setContent(root1);
            scrollPane.setFitToHeight(true);
            scrollPane.setFitToWidth(true);
            VBox root = new VBox();
            
            root.setSpacing(10);
            root.setPadding(new Insets(10));
            
            
            
            if (ID.charAt(0) == 'r') {
                VBox Msgs = new VBox();
                Msgs.setSpacing(10);
                Msgs.setPadding(new Insets(10));
                
                
                vboxes.put(t.getId(), new VBox[] {root,Msgs});
                CustomGroupOptionBtn GroupOptions = new CustomGroupOptionBtn(hamed, ID, adminID);

                GroupOptions.setOnMouseClicked(e -> GroupOptions.getRoomPopUp().show(GroupOptions, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, e.getX(), e.getY()));
                ScrollPane MembersScroll = new ScrollPane();

                MembersScroll.setMaxHeight(80);
                MembersScroll.setMinHeight(80);
                MembersScroll.setFitToWidth(true);
                CustomCirclesHBox MembersCircles = new CustomCirclesHBox();
                MembersScroll.setContent(MembersCircles);
                root1.getChildren().add(GroupOptions);
                root1.getChildren().add(MembersScroll);
                scrollPane.setContent(Msgs);
                scrollPane.vvalueProperty().bind(Msgs.heightProperty());
                root1.getChildren().add(scrollPane);
                membersInRomPane.put(ID, MembersCircles);
            }
            else 
            {
                root1.getChildren().add(scrollPane);
                scrollPane.setContent(root);
                scrollPane.vvalueProperty().bind(root.heightProperty());

                vboxes.put(t.getId(), new VBox[] {null, root});
            }
        }

    }

    public void EnterRoomUserCircle(String UserName, String UserID, SimpleStringProperty Status, String RoomID, String adminID) {

        if (membersInRomPane.get(RoomID).getCircles().get(UserID) == null) {
            RoomCircleBtn UserBtn = new RoomCircleBtn(UserName, UserID, Status, RoomID);
            VBox BtnsPop = new VBox();
            JFXPopup CirclePopUp = new JFXPopup();
            if (adminID.equals(hamed.getId()) || hamed.isAdmin()) {
                JFXButton RemoveMember = new JFXButton("Kick Member");
                RemoveMember.setOnMouseClicked(e
                        -> {
                    hamed.kickClientFromRoom(RoomID, UserID);
                    CirclePopUp.hide();
                });
                BtnsPop.getChildren().add(RemoveMember);

            }

            CirclePopUp.setPopupContent(BtnsPop);

            membersInRomPane.get(RoomID).getChildren().add(UserBtn);
            membersInRomPane.get(RoomID).getCircles().put(UserID, UserBtn);

            UserBtn.setOnMouseClicked(e -> ShowPopupRoom(CirclePopUp, UserBtn, e));
        }
    }

    public void ShowPopupRoom(JFXPopup RoomPopUp, JFXButton GroupOptions, MouseEvent e) {
        if (e.getButton() == MouseButton.SECONDARY) {
            RoomPopUp.show(GroupOptions, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, e.getX(), e.getY());
        }
    }

    public void onTabClose(String id) {

        vboxes.remove(id);
        for (int j = 0; j < tabs.getTabs().size(); j++) {
            if (tabs.getTabs().get(j).getId().equals(id)) {
                tabs.getTabs().remove(j);
                break;
            }
        }
    }

    public VBox createSentMsgStackPane(String msg) {
        StackPane p = new StackPane();
        p.setMinHeight(Region.USE_PREF_SIZE);
        p.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        p.setStyle("-fx-background-color: #00FFFF; -fx-background-radius: 30; -fx-border-radius: 30; -fx-border-width:5;");

        Label lbl = new Label(msg);
        lbl.setPadding(new Insets(10));
        lbl.setText(msg);
        lbl.setTextFill(Color.BLACK);
        lbl.setWrapText(true);

        p.getChildren().add(lbl);
        return new VBox(p, createDateLbl());
    }

    public HBox createReceivedMsgStackPane(String Msg, int Type) {
        // type is 1 for client or 2 for room
        StackPane p1 = new StackPane();
        p1.setStyle("-fx-background-color: #fff; -fx-background-radius: 30; -fx-border-radius: 30; -fx-border-width:5;");
        p1.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        p1.setMinHeight(Region.USE_PREF_SIZE);
        Label lbl1 = new Label();
        lbl1.setPadding(new Insets(10));
        if (Type == 1) {
            lbl1.setText(Msg);
        } else {
            lbl1.setText(Msg.substring(Msg.indexOf(":") + 1, Msg.length()));
        }
        lbl1.setTextFill(Color.BLACK);
        HBox hob = new HBox();
        hob.setPrefWidth(470);
        hob.setAlignment(Pos.CENTER_RIGHT);
        p1.getChildren().add(lbl1);
        Label Timelbl = createDateLbl();
        VBox MessageVbox = new VBox();
        if (Type == 2) {

            Label lbl = new Label();
            lbl.setPadding(new Insets(5));
            lbl.setText(Msg.substring(0, Msg.indexOf(":")));
            lbl.setTextFill(Color.WHITE);
            MessageVbox.getChildren().add(lbl);

        }
        MessageVbox.getChildren().add(p1);
        MessageVbox.getChildren().add(Timelbl);
        hob.getChildren().add(MessageVbox);

        return hob;
    }

    private Label createDateLbl() {
        //yyyy.MM.dd.HH.mm.ss
        String datelbl = new SimpleDateFormat("HH:mm:ss").format(new java.util.Date());
        Label Timelbl = new Label();
        Timelbl.setPadding(new Insets(5));
        Timelbl.setText(datelbl);
        Timelbl.setTextFill(Color.WHITE);
        return Timelbl;
    }

    public void receiveClient(String Msg, String ClientID) {
        String ClientName = clients.get(ClientID).getName();
        if (vboxes.get(ClientID) != null) {

            for (int j = 0; j < tabs.getTabs().size(); j++) {
                if (tabs.getTabs().get(j).getId().equals(ClientID)) {
                    Tab ta = tabs.getTabs().get(j);
                    if (tabs.getSelectionModel().getSelectedItem() == ta) {
                        break;
                    }
                    if (!ta.getStyleClass().contains("receiveMsg")) {
                        ta.setText(ta.getText() + "!!!");
                        ta.getStyleClass().add("receiveMsg");
                        ta.getStyleClass().add("sound");
                        Platform.runLater(() -> RunSound(1));
                    }
                    if (ta.getStyleClass().contains("sound")) {
                        Platform.runLater(() -> RunSound(1));
                    }
                    break;
                }
            }
        } else {
            AddTab(ClientID, ClientName, null);

        }
        vboxes.get(ClientID)[1].getChildren().add(createReceivedMsgStackPane(Msg, 1));
        if (ClientID.equals(tabs.getSelectionModel().getSelectedItem().getId())) {//yyyy.MM.dd.HH.mm.ss
            String datelbl = new SimpleDateFormat("HH:mm:ss").format(new java.util.Date());
            hamed.confirmSeen(ClientID, datelbl);
        }
    }

    public void receiveRoom(String Msg, String RoomID, String UserID, String RoomName, String adminID) {

        if (vboxes.get(RoomID) != null) {

            for (int j = 0; j < tabs.getTabs().size(); j++) {
                if (tabs.getTabs().get(j).getId().equals(RoomID)) {
                    Tab ta = tabs.getTabs().get(j);
                    if (tabs.getSelectionModel().getSelectedItem() == ta) {
                        break;
                    }
                    if (!ta.getStyleClass().contains("receiveMsg")) {
                        ta.setText(ta.getText() + "!!!");
                        ta.getStyleClass().add("receiveMsg");
                        ta.getStyleClass().add("sound");
                        Platform.runLater(() -> RunSound(2));
                    }
                    if (ta.getStyleClass().contains("sound")) {
                        Platform.runLater(() -> RunSound(2));
                    }
                    break;
                }
            }

        } else {
            AddTab(RoomID, RoomName, adminID);

        }
        if (clients.get(UserID) == null) {
            vboxes.get(RoomID)[1].getChildren().add(createSentMsgStackPane(Msg));
        } else {
            vboxes.get(RoomID)[1].getChildren().add(createReceivedMsgStackPane(clients.get(UserID).getName() + ": " + Msg, 2));
        }
    }

    public void RunSound(int type) {// 1 for client
        if (type == 1) {
            final MediaPlayer mediaPlayerClient = new MediaPlayer(ClientSoundMedia);

            mediaPlayerClient.play();
        } else {
            final MediaPlayer mediaPlayerRoom = new MediaPlayer(RoomSoundMedia);
            mediaPlayerRoom.play();
        }
    }

    public void sendBtn() {
        if (!ChatTxt.getText().equals("")) {

            String ID = tabs.getSelectionModel().getSelectedItem().getId();
            String msg = ChatTxt.getText();
            VBox MsgPane = createSentMsgStackPane(msg);
            vboxes.get(ID)[1].getChildren().add(MsgPane);
            ChatTxt.setText("");
            emojiPane.setVisible(false);
            if (ID.charAt(0) == 'r') {
                hamed.sendMessageToRoom(ID, msg);
            } else {
                hamed.sendMessageToPeer(ID, msg);
            }

        }
    }

    public void createUserPane(String UserID, SimpleStringProperty Status, String UserName) {
        if (usersVboxes.get(UserID) == null) {
            CustomStackPane user = new CustomStackPane(UserID, UserName, Status, hamed);

            user.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                        if (mouseEvent.getClickCount() == 2) {
                            AddTab(UserID, UserName, null);
                        }
                    }
                }
            });
            usersVboxes.put(UserID, user);
            UsersVbox.setSpacing(5);
            UsersVbox.getChildren().add(user);
        }
    }

    public void createGroupPane(String groupID, String GroupName, String adminID) {
        if (groupVboxes.get(groupID) == null) {
            CustomStackPane group = new CustomStackPane(groupID, GroupName, adminID);

            group.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                        if (mouseEvent.getClickCount() == 2) {
                            AddTab(groupID, GroupName, adminID);
                        }
                    }
                }
            });
            groupVboxes.put(groupID, group);
            groupVbox.setSpacing(5);
            try {
                groupVbox.getChildren().add(group);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void TestBtn() throws IOException {
        clients.get("c-0").setStatus("fafafd");
    }

    public void setUserName(String name) {
        userName = name;
    }

    public String AddRoomDialog() {

        Parent root;
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AddRom.fxml"));
            root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Adding Room");
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image(FXMLDocumentController.class.getResourceAsStream("imgs/icon.png")));

            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);
            stage.setResizable(false);
            stage.show();
            AddRomController controller = loader.<AddRomController>getController();
            stage.setOnHidden(e -> setRoomName(controller));
        } catch (IOException e) {
            System.out.println("error in add room dialog");
            System.out.println(e.getMessage());
        }
        return "";
    }

    public void setRoomName(AddRomController cont) {

        String RoomName = cont.onClose();

        if (!RoomName.equals("")) {
            hamed.createRoom(RoomName);
        }
    }

    public void ChangeStatusDialog() {

        Parent root;
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("StatusChange.fxml"));
            root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Changing status");
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image(FXMLDocumentController.class.getResourceAsStream("imgs/icon.png")));
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);
            stage.setResizable(false);
            stage.show();
            StatusChangeController controller = loader.<StatusChangeController>getController();
            stage.setOnHidden(e -> setStatus(controller));
        } catch (IOException e) {
            System.out.println("error in changing status");
            System.out.println(e.getMessage());
        }

    }

    public void setStatus(StatusChangeController cont) {

        String newStatus = cont.onClose();

        if (!newStatus.equals("")) {
            //change statushere
            hamed.changeStatus(newStatus);

        }
    }

    private void onTabClick(String id) {
        if (tabs.getSelectionModel().getSelectedItem().getStyleClass().contains("receiveMsg")) {
            Tab ta = tabs.getSelectionModel().getSelectedItem();
            ta.getStyleClass().remove("receiveMsg");
            ta.setText(ta.getText().replace("!!!", ""));
            ta.getStyleClass().remove("sound");
            if (id.charAt(0) == 'c') {//yyyy.MM.dd.HH.mm.ss
                String datelbl = new SimpleDateFormat("HH:mm:ss").format(new java.util.Date());
                hamed.confirmSeen(id, datelbl);
            }
        }

    }
    public void RemoveVboxFromTabsAndLeftPanels(VBox VB, HashMap VBs, String ID) {
        if (VBs.get(ID) != null) {
            VB.getChildren().remove(VBs.get(ID));
            for (int j = 0; j < tabs.getTabs().size(); j++) {
                if (tabs.getTabs().get(j).getId().equals(ID)) {
                    tabs.getTabs().remove(j);
                    break;
                }
            }

            vboxes.remove(ID);

            VBs.remove(ID);
        }
    }

    public void RemoveVboxFromRightPanels(VBox VB, HashMap VBs, String ID) {
        /* UserTabVbox , UserTabVboxes for user panel
            GroupTabVbox ,GroupTabVboxes for room panel
         */

        if (VBs.get(ID) != null) {
            VB.getChildren().remove(VBs.get(ID));
            VBs.remove(ID);
        }
    }

    public void JoinRoom(String roomID, String roomName, String adminID) {
        AddTab(roomID, roomName, adminID);
        createGroupPane(roomID, roomName, adminID);
    }

    public void JoinClient(String clientID) {
        AddTab(clientID, clients.get(clientID).getName(), null);
        createUserPane(clientID, clients.get(clientID).getStatus(), clients.get(clientID).getName());
    }

    private class PeerHandler implements CallbackOnReceiveHandler {

        private String peerID;
        private boolean first = true;

        public PeerHandler() {
            first = true;
        }

        public PeerHandler(String peerID) {
            this.peerID = peerID;
            first = false;
        }

        public void setPeerID(String peerID) {
            this.peerID = peerID;
        }

        @Override
        public void handleReceivedData(HashMap<String, String> msg) {

            try {
                java.lang.reflect.Method handle;
                handle = this.getClass().getMethod(msg.get(Constants.REQUESTTYPEATTR), HashMap.class);
                handle.invoke(this, msg);
            } catch (Exception ex) {
                System.out.println(ex.getLocalizedMessage());

                for (StackTraceElement ste : ex.getStackTrace()) {
                    System.out.println(ste);
                }

            }
        }

        public void handleMessageFromPeer(HashMap<String, String> msg) {
            if (first) {
                first = false;
                Platform.runLater(() -> JoinClient(peerID));
            }
            String message = msg.get(Constants.PEERMESSAGE);
            //gui, show the message in the desired location using "peerID"
            Platform.runLater(() -> receiveClient(message, peerID));
        }

        public void handleTyping(HashMap<String, String> msg) {
            String status = msg.get(Constants.TYPINGSTATUS);
            Platform.runLater(() -> changeTypingStatus(peerID, status));
        }

        public void handleSeen(HashMap<String, String> msg) {
            String timeSeenAt = msg.get(Constants.SEENTIME);
            //set label text to: "seen at " + timeSeenAt
            Label seen = new Label();
            seen.setPadding(new Insets(5));
            seen.setTextFill(Color.WHITE);
            seen.setText("seen at " + timeSeenAt);
            Platform.runLater(() -> vboxes.get(peerID)[1].getChildren().add(seen));
        }

        private String getPeerID() {
            return peerID;
        }

        public void handleConnectionClosed(HashMap<String, String> msg) {
            // peer connection closed
            Platform.runLater(() -> {
                RemoveVboxFromTabsAndLeftPanels(UsersVbox, usersVboxes, peerID);
                RemoveVboxFromRightPanels(UserTabVbox, UserTabVboxes, peerID);

            });
        }

    }

    private class ServerHandler implements CallbackOnReceiveHandler {

        @Override
        public void handleReceivedData(HashMap<String, String> msg) {
            try {
                java.lang.reflect.Method handle;
                handle = this.getClass().getMethod(msg.get(Constants.REPLYTYPEATTR), HashMap.class);
                handle.invoke(this, msg);
            } catch (Exception ex) {
                System.out.println(msg.get(Constants.REPLYTYPEATTR));
                for (StackTraceElement ste : ex.getStackTrace()) {
                    System.out.println(ste);
                }

            }
        }

        public void handleRoomJoined(HashMap<String, String> msg) {
            String roomID = msg.get(Constants.ROOMIDATTR);
            String roomName = msg.get(Constants.ROOMNAMEATTR);
            String adminID = msg.get(Constants.ADMINIDATTR);
            //GUI open room
            //AddNewGroup(roomID, roomID);
            //AddTab(roomID, roomID);
            //createGroupPane(roomID, roomName);
            //double click on right room
            Platform.runLater(() -> JoinRoom(roomID, roomName, adminID));
        }

        public void handleRoomCreated(HashMap<String, String> msg) {
            String roomID = msg.get(Constants.ROOMIDATTR);
            //GUI open room
            //AddNewGroup(roomID, roomID);
            //AddTab(roomID, roomID);
            //createGroupPane(roomID, roomName);
            //double click on right room
            hamed.joinRoom(roomID);
        }

        public void handleRoomLeft(HashMap<String, String> msg) {
            String roomID = msg.get(Constants.ROOMIDATTR);
            //GUI close room
            Platform.runLater(() -> RemoveVboxFromTabsAndLeftPanels(groupVbox, groupVboxes, roomID));
        }

        public void handleClientAddedtoRoom(HashMap<String, String> msg) {
            String clientID = msg.get(Constants.CLIENTIDATTR);
            String clientName = clients.get(clientID).getName();
            SimpleStringProperty clientStatus = clients.get(clientID).getStatus();
            String roomID = msg.get(Constants.ROOMIDATTR);

            //GUI add client to room
            Platform.runLater(() -> {
                String adminID = ((CustomStackPane) GroupTabVboxes.get(roomID)).getAdminID();
                EnterRoomUserCircle(clientName, clientID, clientStatus, roomID, adminID);

            });
        }

        public void handleClientRemovedFromRoom(HashMap<String, String> msg) {
            String clientID = msg.get(Constants.CLIENTIDATTR);
            String roomID = msg.get(Constants.ROOMIDATTR);
            //GUI remove client from room
            Platform.runLater(() -> removeCircleFromRoom(roomID, clientID));
        }

        public void handleNewPeerStatus(HashMap<String, String> msg) {
            String senderID = msg.get(Constants.CLIENTIDATTR);
            String newStatus = msg.get(Constants.CLIENTSTATUSATTR);
            Platform.runLater(() -> clients.get(senderID).setStatus(newStatus));
        }

        public void handleMessageFromRoom(HashMap<String, String> msg) {
            String roomID = msg.get(Constants.ROOMIDATTR);
            String senderID = msg.get(Constants.CLIENTIDATTR);
            String message = msg.get(Constants.MESSAGE);
            //GUI add message to chat
            Platform.runLater(() -> {
                String roomName = ((CustomStackPane) GroupTabVboxes.get(roomID)).getName();
                String adminID = ((CustomStackPane) GroupTabVboxes.get(roomID)).getAdminID();
                receiveRoom(message, roomID, senderID, roomName, adminID);
            });
        }

        public void handleNewClient(HashMap<String, String> msg) {
            String clientName = msg.get(Constants.CLIENTNAMEATTR);
            String clientStatus = msg.get(Constants.CLIENTSTATUSATTR);
            String clientID = msg.get(Constants.CLIENTIDATTR);
            String clientIp = msg.get(Constants.CLIENTIPATTR);
            clients.put(clientID, new ClientTuple(clientIp.substring(1), clientName, clientStatus));
            //GUI add new client
            Platform.runLater(() -> AddNewUser(clientID, clientName, clients.get(clientID).getStatus()));
        }

        public void handleNewRoom(HashMap<String, String> msg) {
            String roomName = msg.get(Constants.ROOMNAMEATTR);
            String roomID = msg.get(Constants.ROOMIDATTR);
            String adminID = msg.get(Constants.ADMINIDATTR);
            //GUI add room
            Platform.runLater(() -> AddNewGroup(roomName, roomID, adminID));

        }

        public void handleRemoveClient(HashMap<String, String> msg) {
            String clientID = msg.get(Constants.CLIENTIDATTR);
            //gui remove client from the server
            Platform.runLater(() -> {
                RemoveVboxFromTabsAndLeftPanels(UsersVbox, usersVboxes, clientID);
                RemoveVboxFromRightPanels(UserTabVbox, UserTabVboxes, clientID);

            });
        }

        public void handleRoomDeleted(HashMap<String, String> msg) {
            String roomID = msg.get(Constants.ROOMIDATTR);
            Platform.runLater(() -> {
                RemoveVboxFromTabsAndLeftPanels(groupVbox, groupVboxes, roomID);
                RemoveVboxFromRightPanels(GroupTabVbox, GroupTabVboxes, roomID);

            });
        }

        public void handleConnectionClosed(HashMap<String, String> msg) {

            //gui server closed           
            try {

                Platform.runLater(() -> {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Server Error");
                    alert.setHeaderText("Server Error ");
                    alert.setContentText("Sorry the Application will be closed now, please try again later");

                    alert.showAndWait();
                    exit();
                });
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

        public void handleRoomHistory(HashMap<String, String> msg) {

            String roomID = msg.get(Constants.ROOMIDATTR);
            String chat = msg.get(Constants.ROOMCHAT); //id,msg \n
            //GUI add message to chat
            if (!chat.isEmpty()) {
                String[] messages = chat.split("\n");
                for (int i = 0; i < messages.length; i++) {
                    String[] message = messages[i].split(",");
                    Platform.runLater(() -> {
                        String roomName = ((CustomStackPane) GroupTabVboxes.get(roomID)).getName();
                        String adminID = ((CustomStackPane) GroupTabVboxes.get(roomID)).getAdminID();
                        receiveRoom(message[1], roomID, message[0], roomName, adminID);
                    });
                }
            }
        }
    }

    public void typing() {
        try {
            String ID = tabs.getSelectionModel().getSelectedItem().getId();
            if (ID.charAt(0) == 'c') {
                if (ChatTxt.getText().isEmpty()) {
                    type = false;
                    hamed.stoppedTyping(ID);
                } else if (!type) {
                    type = true;
                    hamed.isTyping(ID);
                }
            }
        } catch (Exception e) {

        }
    }

    public void changeTypingStatus(String clientID, String status) {
        String ID = tabs.getSelectionModel().getSelectedItem().getId();
        if (clientID.equals(ID)) {
            String clientName = clients.get(clientID).getName();
            if (status.isEmpty()) {
                TypingLbl.setText("");
            } else {
                TypingLbl.setText(clientName + status);
            }
        }
    }

    public void exit() {
        System.exit(0);

    }

    public void removeCircleFromRoom(String roomID, String clientID) {
        RoomCircleBtn circle = membersInRomPane.get(roomID).getCircles().get(clientID);
        if (circle != null) {
            membersInRomPane.get(roomID).getChildren().remove(circle);
            membersInRomPane.get(roomID).getCircles().remove(clientID);
        }
    }
}
