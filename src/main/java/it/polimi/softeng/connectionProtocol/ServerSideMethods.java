package it.polimi.softeng.connectionProtocol;

import it.polimi.softeng.controller.ChatController;
import it.polimi.softeng.model.Player;
import it.polimi.softeng.model.Tile;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.util.ArrayList;

public class ServerSideMethods implements ServerRemoteInterface{
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
                    ClientRemoteInterface stub = (ClientRemoteInterface) LocateRegistry.getRegistry(host, 1099);
                    serverSideRMI.addRMIClient(name,stub);
                    return true;
                }
                case ("gameStarted") -> {
                    if (loginManager.getDisconnectedPlayerList().contains(name)) {
                        loginManager.addNickName(name);
                        ClientRemoteInterface stub = (ClientRemoteInterface) LocateRegistry.getRegistry(host, 1099);
                        serverSideRMI.addRMIClient(name,stub);
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
