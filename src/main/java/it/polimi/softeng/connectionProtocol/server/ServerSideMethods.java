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
    private LoginManagerV2 loginManager;
    private ServerSideRMI serverSideRMI;
    private ServerSide serverSide;
    private ChatController chatController = new ChatController();
    private Controller controller;

    public ServerSideMethods(LoginManagerV2 loginManager,ServerSideRMI serverSideRMI,ServerSide serverSide,Controller controller){
        this.loginManager = loginManager;
        this.serverSideRMI = serverSideRMI;
        this.serverSide = serverSide;
        this.controller = controller;
    }

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
    //technically useless
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
    //useless
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

    @Override
    public Boolean sendMove(ArrayList<Cell> cells, int column, String nickName) throws RemoteException {
        return controller.SocketGameMoveRequest(cells,column,nickName);
    }

    @Override
    public ArrayList<Player> getPlayersAndScore() throws RemoteException {
        return controller.getGameController().getCurrentGame().getPlayers();
    }


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
