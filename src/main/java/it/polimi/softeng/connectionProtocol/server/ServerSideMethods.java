package it.polimi.softeng.connectionProtocol.server;

import it.polimi.softeng.JSONWriter.ClientSignatureWriter;
import it.polimi.softeng.connectionProtocol.client.ClientRemoteInterface;
import it.polimi.softeng.controller.ChatController;
import it.polimi.softeng.controller.Controller;
import it.polimi.softeng.model.Cell;
import it.polimi.softeng.model.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.util.ArrayList;

public class ServerSideMethods implements ServerRemoteInterface {
    /**
     * login manager needed to manage the game
     */
    private LoginManagerV2 loginManager;
    /**
     * needed to add the clients
     */
    private ServerSideRMI serverSideRMI;
    /**
     * needed to send the chat messages from rmi to tcp users
     */
    private ServerSide serverSide;
    /**
     * needed to prepare chat messages for tcp users
     */
    private ChatController chatController = new ChatController();
    /**
     * needed to change the model with the moves received
     */
    private Controller controller;

    /**
     * constructor method for serverSideMethods
     * @param loginManager the login manager of the match
     * @param serverSideRMI the serverside handling rmi connection
     * @param serverSide general serverside of the match
     * @param controller general controller of the match
     */
    public ServerSideMethods(LoginManagerV2 loginManager,ServerSideRMI serverSideRMI,ServerSide serverSide,Controller controller){
        this.loginManager = loginManager;
        this.serverSideRMI = serverSideRMI;
        this.serverSide = serverSide;
        this.controller = controller;
    }

    /**
     * login method for rmi users which intend to create a new match
     * @param name player's nickname
     * @param playerNumber decided player number for the match
     * @param mode use "easy" for easy mode, "normal" for normal mode; else will not be accepted and it will return false
     * @param port the port used to open the client registry
     * @return boolean value indicating whether the login was successful or not
     * @throws RemoteException remote exception
     */
    @Override
    public Boolean login(String name, int playerNumber, String mode,int port) throws RemoteException {
        System.out.println("login request from " + name);
        if(!loginManager.getNickNameList().contains(name)) {
            String host;
            try {
                host = RemoteServer.getClientHost();
                System.out.println(host);
            } catch (ServerNotActiveException e) {
                throw new RuntimeException(e);
            }

            switch (loginManager.getStatus()) {
                case ("gameLobby") -> {
                    ClientRemoteInterface stub = null;
                    try {
                        stub = (ClientRemoteInterface) LocateRegistry.getRegistry(host, port).lookup("ClientRemoteInterface");
                    } catch (NotBoundException e) {
                        e.printStackTrace();
                    }
                    loginManager.setPlayerNumber(playerNumber);
                    loginManager.addStub(name,stub);
                    serverSideRMI.addRMIClient(name,stub);
                    loginManager.addNickName(name);
                    //TODO add gestione modalità semplificata
                    return true;
                }
                case ("gameStarted") -> {
                    if (loginManager.getDisconnectedPlayerList().contains(name)) {
                        ClientRemoteInterface stub = null;
                        try {
                            stub =(ClientRemoteInterface) LocateRegistry.getRegistry(host,port).lookup("ClientRemoteInterface");
                        } catch (NotBoundException e) {
                            e.printStackTrace();
                        }
                        loginManager.addStub(name,stub);
                        serverSideRMI.addRMIClient(name,stub);
                        loginManager.addNickName(name);
                        System.out.println(name + " reconnected !");
                        stub.startGame();
                        return true;
                    } else {
                        System.out.println("name not present in disconnected names list");
                        return false;
                    }
                }
            }
        }
        System.out.println("name already in use !");
        return false;
    }
    /**
     * login method for rmi users which intend to join an existing match
     * @param name player's nickname
     * @param port the port used to open the client registry
     * @return boolean value indicating whether the login was successful or not
     * @throws RemoteException remote exception
     */
    @Override
    public Boolean login(String name, int port) throws RemoteException {
        System.out.println("login request from " + name);
        if(!loginManager.getNickNameList().contains(name)) {
            String host;
            try {
                host = RemoteServer.getClientHost();
            } catch (ServerNotActiveException e) {
                throw new RuntimeException(e);
            }

            switch (loginManager.getStatus()) {
                case ("gameLobby") -> {
                    ClientRemoteInterface stub = null;
                    try {
                        stub =(ClientRemoteInterface) LocateRegistry.getRegistry(host, port).lookup("ClientRemoteInterface");
                    } catch (NotBoundException e) {
                        e.printStackTrace();
                    }
                    loginManager.addStub(name,stub);
                    serverSideRMI.addRMIClient(name,stub);
                    loginManager.addNickName(name);
                    return true;
                }
                case ("gameStarted") -> {
                    if (loginManager.getDisconnectedPlayerList().contains(name)) {
                        ClientRemoteInterface stub = null;
                        try {
                            stub = (ClientRemoteInterface) LocateRegistry.getRegistry(host,port).lookup("ClientRemoteInterface");
                        } catch (NotBoundException e) {
                            throw new RuntimeException(e);
                        }
                        loginManager.addStub(name,stub);
                        serverSideRMI.addRMIClient(name,stub);
                        loginManager.addNickName(name);
                        System.out.println(name + " reconnected !");
                        stub.startGame();
                        return true;
                    } else {
                        System.out.println("name not present in disconnected names list");
                        return false;
                    }
                }
            }
        }
        System.out.println("name already in use !");
        return false;
    }
    /**
     * login method for rmi users which intend to join an existing match (local use only)
     * @param name player's nickname
     * @param port the port used to open the client registry
     * @return boolean value indicating whether the login was successful or not
     * @throws RemoteException remote exception
     */
    @Override
    public Boolean localLogin(String name, int port) throws RemoteException {
        System.out.println("login request from " + name);
        if(!loginManager.getNickNameList().contains(name)) {
            String host;
            try {
                host = RemoteServer.getClientHost();
            } catch (ServerNotActiveException e) {
                throw new RuntimeException(e);
            }

            switch (loginManager.getStatus()) {
                case ("gameLobby") -> {
                    ClientRemoteInterface stub = null;
                    try {
                        stub = (ClientRemoteInterface) LocateRegistry.getRegistry("127.0.0.1", port).lookup("ClientRemoteInterface");
                    } catch (NotBoundException e) {
                        e.printStackTrace();
                    }
                    loginManager.addStub(name,stub);
                    serverSideRMI.addRMIClient(name,stub);
                    loginManager.addNickName(name);
                    return true;
                }
                case ("gameStarted") -> {
                    if (loginManager.getDisconnectedPlayerList().contains(name)) {
                        ClientRemoteInterface stub = null;
                        try {
                            stub = (ClientRemoteInterface) LocateRegistry.getRegistry("127.0.0.1", port).lookup("ClientRemoteInterface");
                        } catch (NotBoundException e) {
                            e.printStackTrace();
                        }
                        loginManager.addStub(name,stub);
                        serverSideRMI.addRMIClient(name,stub);
                        loginManager.addNickName(name);
                        stub.startGame();
                        return true;
                    } else {
                        System.out.println("name not present in disconnected names list");
                        return false;
                    }
                }
            }
        }
        System.out.println("name already in use !");
        return false;
    }
    /**
     * login method for rmi users which intend to create a new match (local use only)
     * @param name player's nickname
     * @param playerNumber decided player number for the match
     * @param mode use "easy" for easy mode, "normal" for normal mode; else will not be accepted and it will return false
     * @param port the port used to open the client registry
     * @return boolean value indicating whether the login was successful or not
     * @throws RemoteException remote exception
     */
    public Boolean localLogin(String name, int playerNumber, String mode, int port) throws RemoteException {
        System.out.println("login request from " + name);
        if(!loginManager.getNickNameList().contains(name)) {
            String host;
            try {
                host = RemoteServer.getClientHost();
            } catch (ServerNotActiveException e) {
                throw new RuntimeException(e);
            }

            switch (loginManager.getStatus()) {
                case ("gameLobby") -> {
                    ClientRemoteInterface stub = null;
                    try {
                        stub = (ClientRemoteInterface) LocateRegistry.getRegistry("127.0.0.1", port).lookup("ClientRemoteInterface");
                    } catch (NotBoundException e) {
                        e.printStackTrace();
                    }
                    loginManager.addStub(name,stub);
                    System.out.println("added " + name + " stub !");
                    serverSideRMI.addRMIClient(name,stub);
                    loginManager.setPlayerNumber(playerNumber);
                    loginManager.addNickName(name);
                    //TODO add gestione modalità semplificata
                    return true;
                }
                case ("gameStarted") -> {
                    if (loginManager.getDisconnectedPlayerList().contains(name)) {
                        ClientRemoteInterface stub = null;
                        try {
                            stub = (ClientRemoteInterface) LocateRegistry.getRegistry("127.0.0.1", port).lookup("ClientRemoteInterface");
                        } catch (NotBoundException e) {
                            throw new RuntimeException(e);
                        }
                        loginManager.addStub(name,stub);
                        serverSideRMI.addRMIClient(name,stub);
                        loginManager.addNickName(name);
                        System.out.println(name + " reconnected !");
                        stub.startGame();
                        return true;
                    } else {
                        System.out.println("name not present in disconnected names list");
                        return false;
                    }
                }
            }
        }
        System.out.println("name already in use !");
        return false;
    }

    /**
     * method used to send a game move
     * @param cells
     * @param column
     * @param nickName
     * @return
     * @throws RemoteException
     */
    @Override
    public Boolean sendMove(ArrayList<Cell> cells, int column, String nickName) throws RemoteException {
        return controller.SocketGameMoveRequest(cells,column,nickName);
    }

    /**
     * method used to get players refernces
     * @return arraylist of players
     * @throws RemoteException remote exception
     */
    @Override
    public ArrayList<Player> getPlayersAndScore() throws RemoteException {
        return controller.getGameController().getCurrentGame().getPlayers();
    }

    /**
     * method used to send a chat message to all
     * @param message the message to be displayed by the clients
     * @param sender the sender of the message
     * @throws RemoteException
     */
    @Override
    public void sendMessageToAll(String message, String sender) throws RemoteException {
        String messageOut = addInfo(message,sender);
        chatController.sendChatMessage("all", messageOut, serverSide,sender);

        for(String player : loginManager.getNickNameList()){
            if(serverSideRMI.getNameToStub().containsKey(player) && !sender.equals(player)){
                serverSideRMI.getNameToStub().get(player).displayChatMessage(message,sender);
            }
        }
    }

    /**
     * method used to send a chat message to a specified client
     * @param message the message to be displayed by the clients
     * @param nickName the nickname of the receiver
     * @param sender the sender of the message
     * @throws RemoteException
     */
    @Override
    public void sendMessage(String message, String nickName, String sender) throws RemoteException {
        String messageOut = addInfo(message,sender);
        chatController.sendChatMessage(nickName, messageOut, serverSide, sender);

        for(String player : loginManager.getNickNameList()){
            if(serverSideRMI.getNameToStub().containsKey(player) && nickName.equals(player)){
                serverSideRMI.getNameToStub().get(player).displayChatMessage(message,sender);
            }
        }
    }

    /**
     * private method used by the chat methods that adds the required infos to the json messages for tcp users
     * @param message
     * @param requester
     * @return
     */
    private String addInfo(String message, String requester){
        JSONParser parser = new JSONParser();
        JSONObject obj = null;
        try {
            obj = (JSONObject) parser.parse(message);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ClientSignatureWriter.clientSignObject(obj,"@CHAT", requester);

        return obj.toString();
    }
}
