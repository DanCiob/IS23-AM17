package it.polimi.softeng.connectionProtocol;

import it.polimi.softeng.controller.ServerMessageHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerSide {
    private ServerSocket serverSocket = null;
    private int portNumber = 1234;

    private ArrayList<ClientHandler> clientList = new ArrayList<>();
    private ArrayList<String> nickNameList = new ArrayList<>();
    private ArrayList<String> disconnectedPlayerList = new ArrayList<>();
    private ArrayList<String> finalNickNameList = new ArrayList<>();

    private Map<String,ClientHandler> nickNameToClientHandler = new HashMap<>();
    private ServerMessageHandler serverMessageHandler = null;
    int playerNumber = 4;
    Boolean numberOfPlayersNotConfirmed = true;
    private String status;
    private String gameLobby = "gameLobby";
    private String gameStarted = "gameStarted";

    public ServerSide(ServerMessageHandler serverMessageHandler) {
        status = gameLobby;
        this.serverMessageHandler = serverMessageHandler;

        try {
            serverSocket = new ServerSocket(portNumber);
            System.out.println("server socket up");
        } catch (IOException e) {
            e.printStackTrace();
        }

        accept(status);
        //at the moment serverside closes when the game starts ?
    }

    public void clientAcceptor(ServerSocket serverSocket, ArrayList<ClientHandler> clientList, ServerMessageHandler serverMessageHandler,String mode){
        switch(status){
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

    public void gameLobbyClientAcceptor(ServerSocket serverSocket, ArrayList<ClientHandler> clientList, ServerMessageHandler serverMessageHandler){
        Socket clientSocket = null;
        ExecutorService executor = Executors.newCachedThreadPool();

        while(clientList.size() < playerNumber){
            try {
                clientSocket = serverSocket.accept();
                System.out.println("client accepted ");
                ClientHandler clientHandler = new ClientHandler(clientSocket,this, serverMessageHandler);
                executor.submit(clientHandler);
                clientList.add(clientHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Reached desired number of player");
        for (String player : nickNameList){
            finalNickNameList.add(player);
        }
        serverMessageHandler.getController().startGame();
        System.out.println("game started !");
        status = gameStarted;
    }

    public void gameStartedClientAcceptor(ServerSocket serverSocket, ArrayList<ClientHandler> clientList, ServerMessageHandler serverMessageHandler){
        Socket clientSocket = null;
        ExecutorService executor = Executors.newCachedThreadPool();

        for(String player : disconnectedPlayerList){
            System.out.println(player);
        }
        while(clientList.size() < playerNumber){
            try {
                clientSocket = serverSocket.accept();
                System.out.println("client accepted ");
                ClientHandler clientHandler = new ClientHandler(clientSocket,this, serverMessageHandler);
                executor.submit(clientHandler);
                clientList.add(clientHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //todo dopo una riconessione non vengono esposti i comandi di myshelfie
        }
    }

    public void sendMessageToAll(String message){
        for(ClientHandler client : clientList){
            client.sendMessage(message);
        }
    }

    public void addUser(ClientHandler client, String nickName){
        nickNameToClientHandler.put(nickName,client);
        nickNameList.add(nickName);
    }

    public void sendMessage(String message, String nickName){
            nickNameToClientHandler.get(nickName).sendMessage(message);
    }

    public void sendMessageExcept(String message, String nickName){
        for(ClientHandler client : clientList){
            if(nickNameToClientHandler.get(nickName) != client){
                client.sendMessage(message);
            }
        }
    }

    //TODO function to manage disconnection, remove player from active players and adding to disconnected

    //TODO function to manage reconnection, remove player from disconnected players and adding to active players

    public ArrayList<String> getNickNameList() {
        return nickNameList;
    }

    public ServerMessageHandler getServerMessageHandler() {
        return serverMessageHandler;
    }

    public void setPlayerNumber(int playerNumber){
        if(numberOfPlayersNotConfirmed){
            this.playerNumber = playerNumber;
            numberOfPlayersNotConfirmed = false;
        }
    }

    public void accept(String mode){
        Thread t = new Thread(() -> clientAcceptor(serverSocket,clientList, serverMessageHandler,mode));
        t.start();
    }

    public void addDisconnectedPlayer(String player) {
        //inserting the nickName in the list of nicknames that left the game
        if(status.equals("gameStarted")) {
            disconnectedPlayerList.add(player);
            System.out.println("added " + player + " to disconnected player list");
        }
        //TODO refactor of this code
        //deleting such nickname from active nicknames
        int j = 43;
        for(int i = 0; i < nickNameList.size(); i++){
            if(nickNameList.get(i).equals(player)){
                j = i;
                System.out.println("found string to be removed from the list of active nickNames");
            }
        }
        if(j != 43){
            nickNameList.remove(j);
        }
        //deleting the client with such nickname from the list of clientHandlers
        j = 43;
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
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<String> getDisconnectedPlayerList() {
        return disconnectedPlayerList;
    }

    public ArrayList<String> getFinalNickNameList() {
        return finalNickNameList;
    }
    public void restartAccepting(){
        accept(status);
    }
}