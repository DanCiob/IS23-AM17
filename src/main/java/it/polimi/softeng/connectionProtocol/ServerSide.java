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

import static java.lang.Thread.sleep;

public class ServerSide {
    private ServerSocket serverSocket = null;
    private int portNumber = 1234;

    private ArrayList<ClientHandler> clientList = new ArrayList<>();
    private ArrayList<String> nickNameList = new ArrayList<>();
    private Map<String,ClientHandler> clientToName = new HashMap<>();
    private ServerMessageHandler serverMessageHandler = null;
    int playerNumber = 4;
    Boolean numberOfPlayersNotConfirmed = true;


    public ServerSide(ServerMessageHandler serverMessageHandler) {

        this.serverMessageHandler = serverMessageHandler;

        try {
            serverSocket = new ServerSocket(portNumber);
            System.out.println("server socket up");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread t = new Thread(() -> clientAcceptor(serverSocket,clientList, serverMessageHandler));
        t.start();
    }

    public void clientAcceptor(ServerSocket serverSocket, ArrayList<ClientHandler> clientList, ServerMessageHandler serverMessageHandler){
        Socket clientSocket = null;
        ExecutorService executor = Executors.newCachedThreadPool();
        int i = 0;

        while(i < playerNumber){
            try {
                clientSocket = serverSocket.accept();
                System.out.println("client accepted ");
                ClientHandler clientHandler = new ClientHandler(clientSocket,this, serverMessageHandler);
                executor.submit(clientHandler);
                clientList.add(clientHandler);
                i++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("raggiunto il numero di giocatori selezionato");
    }

    public void sendMessageToAll(String message){
        for(ClientHandler client : clientList){
            client.sendMessage(message);
        }
    }

    public void addUser(ClientHandler client, String nickName){
        clientToName.put(nickName,client);
        nickNameList.add(nickName);
    }

    public void sendMessage(String message, String nickName){
            clientToName.get(nickName).sendMessage(message);
    }

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
}
