package it.polimi.softeng.connectionProtocol;

import it.polimi.softeng.model.Player;
import it.polimi.softeng.model.Tile;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServerRemoteInterface extends Remote {
    Boolean login(String name) throws RemoteException;
    Boolean login(String name,int port) throws RemoteException;
    void sendMove(ArrayList<Tile> tiles, int column) throws RemoteException;
    ArrayList<Player> getPlayersAndScore () throws RemoteException;
    void sendMessageToAll(String message, String sender) throws RemoteException;
    void sendMessage(String message, String nickName, String sender) throws RemoteException;

}
