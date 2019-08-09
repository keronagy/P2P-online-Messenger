# P2P-online-Messenger
## Instructions:
1- Start the server application.

2- Start the client application (same or different machine).

3- You'll be greeted with login window (Figure 1), write the name that you want to be shown to other users.

4- Enter server ip in the area specified (if server on local machine leave default ip)

## Network Layer
The class Communication link is the nerve of the connection. It has a simple socket, a send function and an interface callbackhandler. This interface forces classes to implement handleReceivedData. This function is responsible for receiving data. The goal here is to add functionalities to receive like receiving from multiple rooms in the same time, typing feature and etc. There are three main handlers that implements the interface.
### 1- ClientHandler
From the client side this handler is responsible for the received data, changing status, creating, joining or sending messages to a specific room, and kick another client or request room delete(if the user is admin).
### 2- ServerHandler
This handler is responsible for nearly the same activities like the client handler but it handles it from the server side. In addition to some more jobs like keeping the room history.
### 3- PeerHandler
Peer client is an extension for client class, similarly, it’s handler adds some additional functionalities like handling messages from peers, seen feature (like fb indicates the other peer has read sent message) and typing (like fb when a user is typing it appears for the other users)
## Application Layer
### 1- Client Server: The client connect to the previously initiated server. The server respond by an ID, list of all other connected users and rooms. There is a copy from the list that contains the users saved on every client device and the server is responsible for updating it.

![alt text](https://i.gyazo.com/4ff59d20001a08a58e5ff8e2dd8cde7c.png)

there are two important concepts in the client server model that do not appear in p2p chat.
Room:
Rooms are created by server and users can start chat in it. By default messages are sent to a specific room if the users didn’t choose another one.
Admin:
Server Admin is the first client to join the server and has the following permissions: Kick user from server- Delete Room - Kick user from room
Room Admin is the client who creates the room and has the following permissions: Delete Room - Kick user from room

### 2- P2P
Every client has a seperate from GUI thread that runs when the program start. This thread contains a server socket listening to port number 2501. This socket waits for p2p connections, A client can establish a p2p connection by getting the ip of another user from the server provided list then connect to the peer on his server socket provided the port is open. Beside the server main list each client has a seperate list that contains his p2p connections. No client can have the p2p list of another client.

![alt text](https://i.gyazo.com/0b9d94df3a6952fb8f72b84e46588f41.png)

Figure 1 - Login Window

![alt text](https://i.gyazo.com/56f703892e780125945b78ba2c12a9d9.png)

Figure 2 - Chat Window

![alt text](https://i.gyazo.com/918e4f1950b1ef97b9655d30b79fd939.png)

Figure 3- Peer2peer chat

![alt text](https://i.gyazo.com/8cf88cabe6ae3bc22c146bc19b0a17c2.png)

