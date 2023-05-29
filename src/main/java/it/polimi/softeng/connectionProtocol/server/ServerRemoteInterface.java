package it.polimi.softeng.connectionProtocol.server;

import it.polimi.softeng.model.Cell;
import it.polimi.softeng.model.Player;
import it.polimi.softeng.model.Tile;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServerRemoteInterface extends Remote {

    /**
     * login method for rmi users which intend to create a new match
     * @param name player's nickname
     * @param playerNumber decided player number for the match
     * @param mode use "easy" for easy mode, "normal" for normal mode; else will not be accepted and it will return false
     * @param port the port used to open the client registry
     * @return boolean value indicating whether the login was successful or not
     * @throws RemoteException remote exception
     */
    Boolean login(String name, int playerNumber,String mode,int port) throws RemoteException;
    /**
     * login method for rmi users which intend to join an existing match
     * @param name player's nickname
     * @param port the port used to open the client registry
     * @return boolean value indicating whether the login was successful or not
     * @throws RemoteException remote exception
     */
    Boolean login(String name,int port) throws RemoteException;
    /**
     * login method for rmi users which intend to join an existing match (local use only)
     * @param name player's nickname
     * @param port the port used to open the client registry
     * @return boolean value indicating whether the login was successful or not
     * @throws RemoteException remote exception
     */
    Boolean localLogin(String name,int port) throws RemoteException;
    /**
     * login method for rmi users which intend to create a new match (local use only)
     * @param name player's nickname
     * @param playerNumber decided player number for the match
     * @param mode use "easy" for easy mode, "normal" for normal mode; else will not be accepted and it will return false
     * @param port the port used to open the client registry
     * @return boolean value indicating whether the login was successful or not
     * @throws RemoteException remote exception
     */
    Boolean localLogin(String name, int playerNumber,String mode ,int port) throws RemoteException;
    /**
     * method used to send a game move
     * @param cells
     * @param column
     * @param nickName
     * @return
     * @throws RemoteException
     */
    Boolean sendMove(ArrayList<Cell> cells, int column, String nickName) throws RemoteException;
    /**
     * method used to get players refernces
     * @return arraylist of players
     * @throws RemoteException remote exception
     */
    ArrayList<Player> getPlayersAndScore () throws RemoteException;
    /**
     * method used to send a chat message to all
     * @param message the message to be displayed by the clients
     * @param sender the sender of the message
     * @throws RemoteException
     */
    void sendMessageToAll(String message, String sender) throws RemoteException;
    /**
     * method used to send a chat message to a specified client
     * @param message the message to be displayed by the clients
     * @param nickName the nickname of the receiver
     * @param sender the sender of the message
     * @throws RemoteException
     */
    void sendMessage(String message, String nickName, String sender) throws RemoteException;

}
