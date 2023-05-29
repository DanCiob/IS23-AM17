package it.polimi.softeng.connectionProtocol.server;


import it.polimi.softeng.connectionProtocol.CommunicationProtocolParser;
import it.polimi.softeng.controller.ServerMessageHandler;
import it.polimi.softeng.model.commonCards.CommonCards;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//TODO controlla se viene killato se si chiudono tutti i socket tcp

public class ServerSideTCP {
    CommunicationProtocolParser communicationProtocolParser = new CommunicationProtocolParser();
    private ServerSocket serverSocket = null;
    private int portNumber;

    private ServerSide serverSide;
    private ArrayList<ClientHandler> clientList = new ArrayList<>();
    private Map<String,ClientHandler> nickNameToClientHandler = new HashMap<>();
    private ServerMessageHandler serverMessageHandler = null;
    private LoginManagerV2 loginManager;

    public ServerSideTCP(LoginManagerV2 loginManager, ServerMessageHandler serverMessageHandler){
        communicationProtocolParser.parser("server");
        portNumber = communicationProtocolParser.getPortNumber();
        this.loginManager = loginManager;
        this.serverMessageHandler = serverMessageHandler;
        try {
            serverSocket = new ServerSocket(portNumber);
            System.out.println("server socket up");
        } catch (IOException e) {
            e.printStackTrace();
        }

        accept(loginManager.getStatus());
    }

    /**
     * This method process the acceptance of a player, starting a thread
     * @param mode gameLobby or gameStarted
     */
    public void accept(String mode){
        Thread t = new Thread(() -> clientAcceptor(serverSocket,clientList, serverMessageHandler,mode));
        t.start();
    }

    //????????????????????????????
    public void restartAccepting(){
        accept(loginManager.getStatus());
    }

    /**
     * This method process the acceptance of a player, invoking two possible different methods depending on @param mode
     * @param serverSocket
     * @param clientList list of ClientHandler
     * @param serverMessageHandler reference to SMH
     * @param mode gameLobby or gameStarted
     */
    public void clientAcceptor(ServerSocket serverSocket, ArrayList<ClientHandler> clientList, ServerMessageHandler serverMessageHandler, String mode){
        switch(mode){
            case "gameLobby" ->{
                System.out.println("created game lobby");
                gameLobbyClientAcceptor(serverSocket,clientList,serverMessageHandler);
            }
            case "gameStarted" ->{
                System.out.println("started waiting for players trying to rejoin");
                gameStartedClientAcceptor(serverSocket,clientList,serverMessageHandler);
            }
        }
    }

    /**
     * This method process the acceptance of a player who wants to participate a game which is not started yet
     * @param serverSocket
     * @param clientList list of ClientHandler
     * @param serverMessageHandler reference to SMH
     */
    public void gameLobbyClientAcceptor(ServerSocket serverSocket, ArrayList<ClientHandler> clientList, ServerMessageHandler serverMessageHandler){
        Socket clientSocket = null;
        ExecutorService executor = Executors.newCachedThreadPool();

        while(loginManager.getNickNameList().size() < loginManager.getPlayerNumber()){
            try {
                clientSocket = serverSocket.accept();
                System.out.println("client accepted ");
                ClientHandler clientHandler = new ClientHandler(clientSocket,serverSide, serverMessageHandler,this,loginManager);
                executor.submit(clientHandler);
                clientList.add(clientHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method process the acceptance of a player who wants to participate a game which is already started
     * @param serverSocket
     * @param clientList list of ClientHandler
     * @param serverMessageHandler reference to SMH
     */
    public void gameStartedClientAcceptor(ServerSocket serverSocket, ArrayList<ClientHandler> clientList, ServerMessageHandler serverMessageHandler){
        Socket clientSocket = null;
        ExecutorService executor = Executors.newCachedThreadPool();

        for(String player : loginManager.getDisconnectedPlayerList()){
            System.out.println(player);
        }
        while(loginManager.getNickNameList().size() < loginManager.getPlayerNumber()){
            try {
                clientSocket = serverSocket.accept();
                System.out.println("client accepted ");
                ClientHandler clientHandler = new ClientHandler(clientSocket,serverSide, serverMessageHandler,this,loginManager);
                executor.submit(clientHandler);
                clientList.add(clientHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //todo dopo una riconessione non vengono esposti i comandi di myshelfie
        }
    }

    /**
     * This method manage the disconnection of a specific player
     * @param player who disconnected
     */
    public void addDisconnectedPlayer(String player) {
        loginManager.addDisconnectedPlayer(player);
        //deleting the client with such nickname from the list of clientHandlers
        int j = 43;
        for(int i = 0; i < clientList.size(); i++){
            if(clientList.get(i).equals(nickNameToClientHandler.get(player))){
                j = i;
                System.out.println("found clienthandler to be removed from the list of active nickNames");
            }
        }
        if(j != 43){
            clientList.remove(j);
        }

        //deleting the reference between a nickname and a clientHandler
        nickNameToClientHandler.remove(player);
        System.out.println("deleting " + player + " mapping to his clientHandler");
        accept(loginManager.getStatus());
    }

    /**
     * This method manages the log-in of a player adding its ClientHandler in nickNameToClientHandler
     *  and nickname in {@link LoginManagerV2}
     * @param client ClientHandler of the player
     * @param nickName of the player
     */
    public void addUser(ClientHandler client, String nickName){
        nickNameToClientHandler.put(nickName,client);
        loginManager.addNickName(nickName);
    }

    ///////////section with message send functions

    /**
     * This method sends a Move message using {@link ClientHandler}
     * @param message
     */
    public void sendMessageToAll(String message){
        for(ClientHandler client : clientList){
            client.sendMessage(message);
        }
    }

    /**
     * This method send a Chat message to a specific player using {@link ClientHandler}
     * @param message
     * @param nickName receiver
     */
    public void sendMessage(String message, String nickName){
        if(nickNameToClientHandler.containsKey(nickName)){
            nickNameToClientHandler.get(nickName).sendMessage(message);
        }
    }

    /**
     * This method send a Chat message to all players using {@link ClientHandler}
     * @param message
     * @param nickName sender
     */
    public void sendMessageExcept(String message, String nickName){
        for(ClientHandler client : clientList){
            if(nickNameToClientHandler.get(nickName) != client){
                client.sendMessage(message);
            }
        }
    }

    public Map<String, ClientHandler> getNickNameToClientHandler() {
        return nickNameToClientHandler;
    }
}


