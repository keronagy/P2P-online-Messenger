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
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;
import server.Client;
import server.PeerClient;
import server.Room;
import server.Server;
import utility.CallbackOnReceiveHandler;
import utility.ClientConstants;
import utility.GeneralConstants;
import utility.MessageConstants;
import utility.ServerConstants;

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
//    ImageView[] imojies = new ImageView[] {i1,i2,i3,i4,i5,i6,i7,i8,i9,i10,i11,i12,i13,i14,i15,i16};
    @FXML
    private Pane emojiPane;
    private boolean emojispanevis = false;
    @FXML
    private Button AddTab;
    @FXML
    private TabPane tabs;
    HashMap<String, VBox> vboxes = new HashMap();
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
    HashMap<String, HBox> membersInRomPane = new HashMap();
    private JFXPopup LeftUsersPopUp = new JFXPopup();
    private JFXPopup EmojiesPopUp = new JFXPopup();
    VBox EmojiesPopupVbox = new VBox();
    @FXML
    private ImageView emojies;

    private PeerClient hamed; //client
    private HashMap<String, ClientTuple> clients = new HashMap();
    public String userName;
    private String IPAddress;
    private String ProtNum;

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public void setProtNum(String ProtNum) {
        this.ProtNum = ProtNum;
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Server.initiateServer(); // initiating the server
            hamed = new PeerClient("online",userName);
            hamed.connect(ServerConstants.SERVERIP, ServerConstants.SERVERPORT, new ServerHandler());

            handleNewConnections();

            final Font f = Font.loadFont(new FileInputStream(new File("OpenSansEmoji.ttf")), 12);
            if (f
                    == null) {
                throw new IllegalArgumentException("Can't load font for url ");
            }

            ChatTxt.setFont(f);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ImageView[] imgsv = {i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13, i14, i15, i16};

        for (int i = 1; i <= 16; i++) {
            final int value = i;
            imgsv[i - 1].setOnMouseClicked(e -> appendEmoji(value));
        }

        TestGroup.setOnMouseClicked(e -> createUserPane("u1", "staus", "kero"));
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
        hamed.connectToPeer(clientID, clients.get(clientID).getIp(), ServerConstants.SERVERPORT + 1, new PeerHandler(clientID)
        );
        JoinClient(clientID );
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
        byte[] emojiBytes;
        String emojiAsString;
        switch (index) {
            case 1:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x82}; // shit
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

    public void showEmojis(MouseEvent e) {
        EmojiesPopUp.show(Toproot, JFXPopup.PopupVPosition.BOTTOM, JFXPopup.PopupHPosition.LEFT, e.getX() + 200, e.getY() - 50);

    }

    public void AddNewUser(String ID, String Name, String Status) {
        CustomStackPane user = new CustomStackPane(ID, Name, Status);
        
        user.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        createPrivateChat(ID);
                    }
                }
            }
        });
        UserTabVbox.setSpacing(5);
        UserTabVbox.getChildren().add(user);
        UserTabVboxes.put(ID, user);
    }

    public void AddNewGroup(String roomName, String roomID) {
        CustomStackPane group = new CustomStackPane(roomID,roomName);
        group.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        hamed.joinRoom(roomID);
                    }
                }
            }
        });
        GroupTabVbox.setSpacing(5);
        GroupTabVbox.getChildren().add(group);
        GroupTabVboxes.put(roomID, group);
    }

    public void AddTab(String ID, String UserName) {
        

        Tab t = new Tab(UserName);

        tabs.getTabs().add(t);
        t.setId(ID);
        t.setOnCloseRequest((e -> onTabClose(t.getId())));
        t.setOnSelectionChanged((e -> onTabClick(t.getId())));
        tabs.getSelectionModel().select(t);
        ScrollPane scrollPane = new ScrollPane();
        t.setContent(scrollPane);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        VBox root = new VBox();
        root.setSpacing(10);
        root.setPadding(new Insets(10));
        scrollPane.setContent(root);
        scrollPane.vvalueProperty().bind(root.heightProperty());
        vboxes.put(t.getId(), root);
        if (ID.charAt(0) == 'r') {
            JFXPopup RoomPopUp = new JFXPopup();
            JFXButton AddMember = new JFXButton("Add Member");
            JFXButton RemoveMember = new JFXButton("Remove Member");
            JFXButton MakeAdmin = new JFXButton("Make Admin");
            JFXButton LeaveRoom = new JFXButton("Leave Room");
            LeaveRoom.setOnMousePressed(e-> hamed.LeaveRoom(ID));
            VBox BtnsPop = new VBox(AddMember, RemoveMember, MakeAdmin, LeaveRoom);
            RoomPopUp.setPopupContent(BtnsPop);
            JFXButton GroupOptions = new JFXButton("Group Options");
            
            GroupOptions.setOnMouseClicked(e -> ShowPopupRoom(RoomPopUp,GroupOptions, e));
            ScrollPane MembersScroll = new ScrollPane();

            MembersScroll.setMaxHeight(80);
            MembersScroll.setMinHeight(80);
            MembersScroll.setFitToWidth(true);
            HBox MembersCircles = new HBox();
            MembersCircles.setMaxHeight(80);
            MembersCircles.setMinHeight(80);
            MembersCircles.setStyle("-fx-border-width:5; -fx-border-color: #555; -fx-border-radius: 50px; -fx-background-radius: 50px;");
            MembersScroll.setContent(MembersCircles);
            root.getChildren().add(GroupOptions);
            root.getChildren().add(MembersScroll);
            MembersCircles.setPadding(new Insets(13));
            membersInRomPane.put(ID, MembersCircles);
//            EnterRoomUserCircle(UserName,ID, "online now", ID);
        }

    }
    
    public void EnterRoomUserCircle(String UserName, String UserID, String Status,String RoomID)
    {
        
        
        RoomCircleBtn UserBtn = new RoomCircleBtn(UserName, UserID, Status, RoomID);
        
        JFXPopup CirclePopUp = new JFXPopup();
            
            JFXButton RemoveMember = new JFXButton("Remove Member");
            JFXButton MakeAdmin = new JFXButton("Make Admin");
            
            VBox BtnsPop = new VBox(RemoveMember, MakeAdmin);
            CirclePopUp.setPopupContent(BtnsPop);
            
            
            membersInRomPane.get(RoomID).getChildren().add(UserBtn);
            
            UserBtn.setOnMouseClicked(e -> ShowPopupRoom(CirclePopUp,UserBtn, e));
    }

    public void ShowPopupRoom(JFXPopup RoomPopUp,JFXButton GroupOptions, MouseEvent e) {
        if (e.getButton() == MouseButton.SECONDARY) {
            RoomPopUp.show(GroupOptions, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT,e.getX(),e.getY());
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

    public HBox createReceivedMsgStackPane(String Msg, int Type) {
        // type is 1 for client or 2 for room
        StackPane p1 = new StackPane();
        p1.setStyle("-fx-background-color: #fff; -fx-background-radius: 30; -fx-border-radius: 30; -fx-border-width:5;");
        p1.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        p1.setMinHeight(Region.USE_PREF_SIZE);
        Label lbl1 = new Label();
        lbl1.setPadding(new Insets(10));
        if(Type == 1)
            lbl1.setText(Msg);
        else
            lbl1.setText(Msg.substring( Msg.indexOf(":")+1,Msg.length()));
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
    
    private Label createDateLbl()
    {
        //yyyy.MM.dd.HH.mm.ss
        String datelbl = new SimpleDateFormat("HH:mm:ss").format(new java.util.Date());
        Label Timelbl = new Label();
        Timelbl.setPadding(new Insets(5));
        Timelbl.setText(datelbl);
        Timelbl.setTextFill(Color.WHITE);
        return Timelbl;
    }

    public void receiveClient(String Msg, String ClientID, String ClientName) {

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
                    }
                    break;
                }
            }
        } else {
            AddTab(ClientID, ClientName);
            createUserPane(ClientID, "online", ClientName);

        }
        vboxes.get(ClientID).getChildren().add(createReceivedMsgStackPane(Msg,1));
    }

    public void receiveRoom(String Msg, String RoomID, String UserID, String UserName, String RoomName) {

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
                    }
                    break;
                }
            }

        } else {
            AddTab(RoomID, RoomName);
            createGroupPane(RoomID, RoomName);

        }
        vboxes.get(RoomID).getChildren().add(createReceivedMsgStackPane(UserName + ": " + Msg,2));
    }

    public void sendBtn() {
        if (!ChatTxt.getText().equals("")) {

            String msg = ChatTxt.getText();
            String ID = tabs.getSelectionModel().getSelectedItem().getId();
            StackPane p = new StackPane();
            p.setMinHeight(Region.USE_PREF_SIZE);
            p.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            p.setStyle("-fx-background-color: #00FFFF; -fx-background-radius: 30; -fx-border-radius: 30; -fx-border-width:5;");
            
            Label lbl = new Label(msg);
            lbl.setPadding(new Insets(10));
            lbl.setText(msg);
            lbl.setTextFill(Color.BLACK);
            lbl.setWrapText(true);
            //= tr
            
            p.getChildren().add(lbl);
            VBox MsgPane = new VBox(p,createDateLbl());
            vboxes.get(ID).getChildren().add(MsgPane);
            ChatTxt.setText("");
            emojiPane.setVisible(false);
            if(ID.charAt(0)=='r')
                hamed.sendMessageToRoom(ID, msg);
            else
                hamed.sendMessageToPeer(ID, msg);

        }
    }

    public void createUserPane(String UserID, String Status, String UserName) {
        CustomStackPane user = new CustomStackPane(UserID, UserName, Status);
        
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
        usersVboxes.put(UserID, user);
        UsersVbox.setSpacing(5);
        UsersVbox.getChildren().add(user);

    }

    public void createGroupPane(String groupID, String GroupName) {
        CustomStackPane group = new CustomStackPane(groupID, GroupName);
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
        groupVboxes.put(groupID, group);
        groupVbox.setSpacing(5);
        groupVbox.getChildren().add(group);

    }

    public void TestBtn() throws IOException {

    }
    
    public void setUserName(String name)
    {
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
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);
            stage.setResizable(false);
            stage.show();
            AddRomController controller = loader.<AddRomController>getController();
            stage.setOnHidden(e -> setRoomName(controller));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    public void setRoomName(AddRomController cont) {

        String RoomName = cont.onClose();

        if (!RoomName.equals("")) {
            System.out.println(RoomName);
            hamed.createRoom(RoomName);
        }
    }

    public void onReceive() {
        AddNewGroup("kero", "keork");

    }

    private void onTabClick(String id) {
        if (tabs.getSelectionModel().getSelectedItem().getStyleClass().contains("receiveMsg")) {
            Tab ta = tabs.getSelectionModel().getSelectedItem();
            ta.getStyleClass().remove("receiveMsg");
            ta.setText(ta.getText().replace("!!!", ""));
        }

    }

    /*public void RemoveUserFromTabsAndLeftPanels(String ID) {
        
                if(usersVboxes.get(ID)!=null){
                UsersVbox.getChildren().remove(usersVboxes.get(ID));

                for (int j = 0; j < tabs.getTabs().size(); j++) {
                    if (tabs.getTabs().get(j).getId().equals(ID)) {
                        tabs.getTabs().remove(j);
                        break;
                    }
                }

                vboxes.remove(ID);
                usersVboxes.remove(ID);
                }
        
    }
*/
    public void RemoveVboxFromTabsAndLeftPanels(VBox VB,HashMap VBs,String ID) {
            if (VBs.get(ID)!=null) {
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
    
    public void RemoveVboxFromRightPanels(VBox VB,HashMap VBs,String ID) {
        /* UserTabVbox , UserTabVboxes for user panel
            GroupTabVbox ,GroupTabVboxes for room panel
        */
            
        if (VBs.get(ID)!=null) {
                VB.getChildren().remove(VBs.get(ID));
                VBs.remove(ID);
            }
    }
     public void JoinRoom(String roomID , String roomName )
        {
            AddTab(roomID, roomName);
            createGroupPane(roomID,roomName);
        }
      public void JoinClient(String clientID )
        {
            AddTab(clientID, clients.get(clientID).getName());
            createUserPane(clientID,clients.get(clientID).getStatus(),clients.get(clientID).getName());
        }
    private class PeerHandler implements CallbackOnReceiveHandler {

        private String peerID;
        private boolean first = true;
        public PeerHandler() {
        }

        public PeerHandler(String peerID) {
            this.peerID = peerID;
        }

        public void setPeerID(String peerID) {
            this.peerID = peerID;
        }

        public void handleReceivedData(HashMap<String, String> msg) {
            try {
                java.lang.reflect.Method handle;
                handle = this.getClass().getMethod(msg.get(GeneralConstants.REQUESTTYPEATTR), HashMap.class);
                handle.invoke(this, msg);
            } catch (Exception ex) {
                System.out.println(ex.getLocalizedMessage());

                for (StackTraceElement ste : ex.getStackTrace()) {
                    System.out.println(ste);
                }

            }
        }
        public void handleMessageFromPeer(HashMap<String,String> msg){
            if(first)
            {
                   Platform.runLater(()-> JoinClient(peerID));
                    first = false;
            }
            String message = msg.get(ClientConstants.PEERMESSAGE);
            String clientName = clients.get(peerID).getName();
            //gui, show the message in the desired location using "peerID"
            Platform.runLater(()->receiveClient(message, peerID, clientName));
        }
        public void handleTyping(HashMap<String,String> msg){
            String status = msg.get(ClientConstants.TYPINGSTATUS);
            String clientName = clients.get(peerID).getName();
            if(status.isEmpty())
            {
                //not typing
                //set label text to: ""
            }
            else
            {
                //typing
                //set label text to: clientName+status
            }
        }
        public void handleSeen(HashMap<String,String> msg){
            String timeSeenAt = msg.get(ClientConstants.SEENTIME);
            //set label text to: "seen at " + timeSeenAt
        }

        private String getPeerID() {
            return peerID;
        }

    }

    private class ServerHandler implements CallbackOnReceiveHandler {

        public void handleReceivedData(HashMap<String, String> msg) {
            try {
                java.lang.reflect.Method handle;
                handle = this.getClass().getMethod(msg.get(GeneralConstants.REPLYTYPEATTR), HashMap.class);
                handle.invoke(this, msg);
            } catch (Exception ex) {
                System.out.println(ex.getLocalizedMessage());

                for (StackTraceElement ste : ex.getStackTrace()) {
                    System.out.println(ste);
                }

            }
        }

        public void handleRoomJoined(HashMap<String, String> msg) {
            String roomID = msg.get(GeneralConstants.ROOMIDATTR);
            String roomName = ((CustomStackPane)GroupTabVboxes.get(roomID)).getName();
            //GUI open room
            //AddNewGroup(roomID, roomID);
            //AddTab(roomID, roomID);
            //createGroupPane(roomID, roomName);
            //double click on right room
            Platform.runLater(()->JoinRoom(roomID,roomName));
        }
       

        public void handleRoomLeft(HashMap<String, String> msg) {
            String roomID = msg.get(GeneralConstants.ROOMIDATTR);
            //GUI close room
            Platform.runLater(()->RemoveVboxFromTabsAndLeftPanels(groupVbox,groupVboxes,roomID));
        }

        public void handleClientAddedtoRoom(HashMap<String, String> msg) {
            String clientID = msg.get(GeneralConstants.CLIENTIDATTR);
            String clientName = clients.get(clientID).getName();
            String roomID = msg.get(GeneralConstants.ROOMIDATTR);
            //GUI add client to room
        }

        public void handleClientRemovedFromRoom(HashMap<String, String> msg) {
            String clientID = msg.get(GeneralConstants.CLIENTIDATTR);
            String roomID = msg.get(GeneralConstants.ROOMIDATTR);
            //GUI remove client from room
        }

        public void handleMessageFromRoom(HashMap<String, String> msg) {
            String roomID = msg.get(GeneralConstants.ROOMIDATTR);
            String senderID = msg.get(GeneralConstants.CLIENTIDATTR);
            String message = msg.get(MessageConstants.MESSAGE);
            String roomName = ((CustomStackPane)GroupTabVboxes.get(roomID)).getName();
            //GUI add message to chat
            Platform.runLater(() -> receiveRoom(message, roomID, senderID, clients.get(senderID).getName(), roomName));
        }

        public void handleNewClient(HashMap<String, String> msg) {
            String clientName = msg.get(GeneralConstants.CLIENTNAMEATTR);
            String clientID = msg.get(GeneralConstants.CLIENTIDATTR);
            String clientStatus = msg.get(GeneralConstants.CLIENTSTATUSATTR);
            String clientIp = msg.get(GeneralConstants.CLIENTIPATTR);
            clients.put(clientID, new ClientTuple(clientIp.substring(1), clientName, clientStatus));
            //GUI add new client
            Platform.runLater(() -> AddNewUser(clientID, clientName, clientStatus));
        }

        public void handleNewRoom(HashMap<String, String> msg) {
            String roomName = msg.get(GeneralConstants.ROOMNAMEATTR);
            String roomID = msg.get(GeneralConstants.ROOMIDATTR);
            //GUI add room
            Platform.runLater(() -> AddNewGroup(roomName, roomID));
            

        }
    }

}
