package it.polimi.softeng.connectionProtocol.server;

import it.polimi.softeng.model.Player;
import it.polimi.softeng.model.Tile;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServerRemoteInterface extends Remote {
    Boolean login(String name, int playerNumber,String mode) throws RemoteException;
    Boolean login(String name) throws RemoteException;
    Boolean login(String name,int port) throws RemoteException;
    Boolean login(String name, int playerNumber,String mode ,int port) throws RemoteException;
    void sendMove(ArrayList<Tile> tiles, int column) throws RemoteException;
    ArrayList<Player> getPlayersAndScore () throws RemoteException;

    /**
     * this method sends a chat messagge to all the users except the sender
     * @param message the message to be displayed by the clients
     * @param sender the sender of the message
     * @throws RemoteException
     */
    void sendMessageToAll(String message, String sender) throws RemoteException;
    void sendMessage(String message, String nickName, String sender) throws RemoteException;

}
