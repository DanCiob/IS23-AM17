package it.polimi.softeng.connectionProtocol.client;

import it.polimi.softeng.model.*;
import it.polimi.softeng.model.commonCards.CommonCards;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.ArrayList;

/**
 * interface that defines the available RMI client methods
 */
public interface ClientRemoteInterface extends Remote {
    /**
     * invokes a player update
     * @param player player whose shelfie is sent (?)
     * @param s shelfie sent
     * @throws RemoteException remote exception
     */
    void playerUpdate(Player player, Shelfie s) throws RemoteException;
    /**
     * invokes a shelfie update
     * @param shelfie new shelfie
     * @throws RemoteException remote exception
     */
    void shelfieUpdate(Shelfie shelfie)throws RemoteException;
    /**
     * invokes a gameboard update
     * @param board new gameboard
     * @throws RemoteException remote exception
     */
    void gameBoardUpdate(GameBoard board) throws RemoteException;
    /**
     * shows the personal cards
     * @param personalCards player's personal cards
     * @throws RemoteException remote exception
     */
    void sendPersonalCard(PersonalCards personalCards) throws RemoteException;
    /**
     * show common card to player
     * @param commonCards common card object
     * @throws RemoteException remote exception
     */
    void sendCommonCard (ArrayList<CommonCards> commonCards) throws RemoteException;
    /**
     * shows badge to player
     * @param badge badge to be shown
     * @throws RemoteException remote exception
     */
    void sendBadge(Badge badge) throws RemoteException;
    /**
     * shows chat message
     * @param message message to be shown
     * @param sender sender of the message
     * @throws RemoteException remote exception
     */
    void displayChatMessage(String message, String sender) throws RemoteException;
    /**
     * shows the new player list
     * @param playerList current player list
     * @throws RemoteException remote exception
     */
    void playerListUpdate(ArrayList<Player> playerList) throws RemoteException;
    /**
     * gives the command to start the game to the ui
     * @throws RemoteException remote exception
     */
    void startGame() throws RemoteException;
    /**
     * gives the command to end the game
     * @param winner boolean indicating whether the player to which this method is called is the winner
     * @throws RemoteException remote exception
     */
    void endGame(boolean winner) throws RemoteException;
    /**
     * notifies the player that it is its turn
     * @throws RemoteException remote exception
     */
    void notifyTurn() throws RemoteException;
    /**
     * method used from the server to assert the connection with a client
     * @return generic boolean value true; if the connection doesn't work it will throw remote exception anyway
     * @throws RemoteException remote exception
     */
    Boolean ping() throws RemoteException;
}
