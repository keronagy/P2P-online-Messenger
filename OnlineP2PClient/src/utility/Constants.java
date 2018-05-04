/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

/**
 *
 * @author fadia
 */
public class Constants {

    public static final String SERVERIP = "127.0.0.1";
    public static final int SERVERPORT = 2500;
    public static final String ADDNEWCLIENTORDER = "handleNewClient";
    public static final String ADDNEWROOMORDER = "handleNewRoom";
    public static final String ADDNEWCLIENTTOROOMORDER = "handleClientAddedtoRoom";
    public static final String REMOVECLIENTFROMROOMORDER = "handleClientRemovedFromRoom";
    public static final String CONFIRMCREATEROOMORDER = "handleRoomCreated";
    public static final String CONFIRMJOINROOMORDER = "handleRoomJoined";
    public static final String CONFIRMLEAVEROOMORDER = "handleRoomLeft";

    public static final String INITSTATUS = "online";
    public static final String MAINCONNECTION = "serverConn";
    public static final String ROOMJOINCONNECTION = "handleRoomJoin";
    public static final String ROOMCREATECONNECTION = "handleRoomCreate";
    public static final String ROOMSENDMESSAGE = "handleRoomMessage";
    public static final String ROOMLEAVECONNECTION = "handleRoomLeave";
    public static final String PEERMESSAGE = "peermessage";
    public static final String TYPINGSTATUS = "typingstatus";
    public static final String SEENTIME = "seentime";
    public static final int P2PPORT = 5555;

    //used to indicate the end of sending a data structure's content
    public static final String EOF = "end";
    //used to indicate the entity is a room
    public static final String isARoom = "r";
    //used to indicate the entity is a client
    public static final String isAClient = "c";
    //used to indicate the entity is a server
    public static final String isAServer = "s";

    //public static final String SENDERIDATTR = "senderid";
    public static final String ADMINIDATTR = "AID";
    public static final String CLIENTIDATTR = "CID";
    public static final String CLIENTSTATUSATTR = "CSTATUS";
    public static final String CLIENTNAMEATTR = "CNAME";
    public static final String CLIENTIPATTR = "CIP";
    public static final String REQUESTTYPEATTR = "reqtype";
    public static final String REPLYTYPEATTR = "reptype";
    public static final String UPDATETYPEATTR = "ut";
    public static final String ROOMIDATTR = "RID";
    public static final String ROOMNAMEATTR = "RNA";

    public static final String MESSAGEFROM = "mfrom";

    public static final String MESSAGEFROMROOM = "handleMessageFromRoom";
    public static final String MESSAGEFROMCLIENT = "handleMessageFromPeer";
    public static final String TYPINGHANDLE = "handleTyping";
    public static final String SEENHANDLE = "handleSeen";
    public static final String ISTYPING = " is typing...";
    public static final String NOTTYPING = "";
    public static final String MESSAGE = "msg";

    public static final String REMOVECLIENTORDER = "handleRemoveClient";

    public static final String CONNECTIONCLOSED = "handleConnectionClosed";
    public static final String ROOMDELETEORDER = "handleRoomDeleteRequest";
    public static final String ROOMDELETED = "handleRoomDeleted";
    public static final String KICKCLIENTORDER = "handleClientKick";
    public static final String KICKCLIENTFROMROOMORDER = "handleClientKickFromRoom";
    public static final String CHANGESTATUS = "handleClientStatusChange";
    public static final String NEWPEERSTATUSUPDATE = "handleNewPeerStatus";

    public static final String ROOMHISTORY = "handleRoomHistory";
    public static final String ROOMCHAT = "chat";

}
