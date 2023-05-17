package it.polimi.softeng.connectionProtocol.server;

import it.polimi.softeng.connectionProtocol.client.ClientRemoteInterface;
import it.polimi.softeng.controller.ChatController;
import it.polimi.softeng.controller.Controller;
import it.polimi.softeng.model.Player;
import it.polimi.softeng.model.Tile;

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

    public ServerSideMethods(LoginManagerV2 loginManager,ServerSideRMI serverSideRMI,ServerSide serverSide){
        this.loginManager = loginManager;
        this.serverSideRMI = serverSideRMI;
        this.serverSide = serverSide;
    }

    @Override
    public Boolean login(String name, int playerNumber, String mode) throws RemoteException {
        if(!loginManager.getNickNameList().contains(name)) {
            String host;
            try {
                host = RemoteServer.getClientHost();
            } catch (ServerNotActiveException e) {
                throw new RuntimeException(e);
            }

            switch (loginManager.getStatus()) {
                case ("gameLobby") -> {
                    loginManager.addNickName(name);
                    ClientRemoteInterface stub = (ClientRemoteInterface) LocateRegistry.getRegistry(host, 1099);//TODO same
                    serverSideRMI.addRMIClient(name,stub);
                    loginManager.addStub(name,stub);
                    loginManager.setPlayerNumber(playerNumber);
                    //TODO add gestione modalità semplificata
                    return true;
                }
                case ("gameStarted") -> {
                    if (loginManager.getDisconnectedPlayerList().contains(name)) {
                        loginManager.addNickName(name);
                        ClientRemoteInterface stub = (ClientRemoteInterface) LocateRegistry.getRegistry(host, 1099);//TODO same
                        serverSideRMI.addRMIClient(name,stub);
                        loginManager.addStub(name,stub);
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
    public Boolean login(String name) throws RemoteException {
        if(!loginManager.getNickNameList().contains(name)) {
            String host;
            try {
                host = RemoteServer.getClientHost();
            } catch (ServerNotActiveException e) {
                throw new RuntimeException(e);
            }

            switch (loginManager.getStatus()) {
                case ("gameLobby") -> {
                    loginManager.addNickName(name);
                    ClientRemoteInterface stub = (ClientRemoteInterface) LocateRegistry.getRegistry(host, 1099);//TODO da riscrivere perchè errato
                    serverSideRMI.addRMIClient(name,stub);
                    loginManager.addStub(name,stub);
                    return true;
                }
                case ("gameStarted") -> {
                    if (loginManager.getDisconnectedPlayerList().contains(name)) {
                        loginManager.addNickName(name);
                        ClientRemoteInterface stub = (ClientRemoteInterface) LocateRegistry.getRegistry(host, 1099);//TODO da riscrivere perchè errato
                        serverSideRMI.addRMIClient(name,stub);
                        loginManager.addStub(name,stub);
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
    public Boolean login(String name, int port) throws RemoteException {
        if(!loginManager.getNickNameList().contains(name)) {
            String host;
            try {
                host = RemoteServer.getClientHost();
            } catch (ServerNotActiveException e) {
                throw new RuntimeException(e);
            }

            switch (loginManager.getStatus()) {
                case ("gameLobby") -> {
                    loginManager.addNickName(name);
                    ClientRemoteInterface stub = null;
                    try {
                        stub = (ClientRemoteInterface) LocateRegistry.getRegistry("127.0.0.1", port).lookup("ClientRemoteInterface");
                    } catch (NotBoundException e) {
                        e.printStackTrace();
                    }
                    serverSideRMI.addRMIClient(name,stub);
                    loginManager.addStub(name,stub);
                    return true;
                }
                case ("gameStarted") -> {
                    if (loginManager.getDisconnectedPlayerList().contains(name)) {
                        loginManager.addNickName(name);
                        ClientRemoteInterface stub = null;
                        try {
                            stub = (ClientRemoteInterface) LocateRegistry.getRegistry("127.0.0.1", port).lookup("ClientRemoteInterface");
                        } catch (NotBoundException e) {
                            e.printStackTrace();
                        }
                        serverSideRMI.addRMIClient(name,stub);
                        loginManager.addStub(name,stub);
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
    public Boolean login(String name, int playerNumber, String mode, int port) throws RemoteException {
        if(!loginManager.getNickNameList().contains(name)) {
            String host;
            try {
                host = RemoteServer.getClientHost();
            } catch (ServerNotActiveException e) {
                throw new RuntimeException(e);
            }

            switch (loginManager.getStatus()) {
                case ("gameLobby") -> {
                    loginManager.addNickName(name);
                    ClientRemoteInterface stub = null;
                    try {
                        stub = (ClientRemoteInterface) LocateRegistry.getRegistry("127.0.0.1", port).lookup("ClientRemoteInterface");
                    } catch (NotBoundException e) {
                        throw new RuntimeException(e);
                    }
                    serverSideRMI.addRMIClient(name,stub);
                    loginManager.addStub(name,stub);
                    loginManager.setPlayerNumber(playerNumber);
                    //TODO add gestione modalità semplificata
                    return true;
                }
                case ("gameStarted") -> {
                    if (loginManager.getDisconnectedPlayerList().contains(name)) {
                        loginManager.addNickName(name);
                        ClientRemoteInterface stub = null;
                        try {
                            stub = (ClientRemoteInterface) LocateRegistry.getRegistry("127.0.0.1", port).lookup("ClientRemoteInterface");
                        } catch (NotBoundException e) {
                            throw new RuntimeException(e);
                        }
                        serverSideRMI.addRMIClient(name,stub);
                        loginManager.addStub(name,stub);
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
    public void sendMove(ArrayList<Tile> tiles, int column) throws RemoteException {

    }

    @Override
    public ArrayList<Player> getPlayersAndScore() throws RemoteException {
        return null;
    }


    @Override
    public void sendMessageToAll(String message, String sender) throws RemoteException {
        chatController.sendChatMessage("all", message, serverSide,sender);

        for(String player : loginManager.getNickNameList()){
            if(serverSideRMI.getNameToStub().containsKey(player) && !sender.equals(player)){
                serverSideRMI.getNameToStub().get(player).displayChatMessage(message,sender);
            }
        }
    }

    @Override
    public void sendMessage(String message, String nickName, String sender) throws RemoteException {
        chatController.sendChatMessage(nickName, message, serverSide, sender);

        for(String player : loginManager.getNickNameList()){
            if(serverSideRMI.getNameToStub().containsKey(player) && nickName.equals(player)){
                serverSideRMI.getNameToStub().get(player).displayChatMessage(message,sender);
            }
        }
    }
}
