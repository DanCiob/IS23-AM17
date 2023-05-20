package it.polimi.softeng.connectionProtocol.client;

import it.polimi.softeng.model.*;
import it.polimi.softeng.model.commonCards.CommonCards;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public interface ClientRemoteInterface extends Remote {
    void playerUpdate(Player player) throws RemoteException;
    void shelfieUpdate(Shelfie shelfie)throws RemoteException;
    void gameBoardUpdate(GameBoard board) throws RemoteException;
    void sendPersonalCard(PersonalCards personalCards) throws RemoteException;
    void sendCommonCard (ArrayList<CommonCards> commonCards) throws RemoteException;
    void sendBadge(Badge badge) throws RemoteException;
    void displayChatMessage(String message, String sender) throws RemoteException;
    void playerListUpdate(ArrayList<Player> playerList) throws RemoteException;
    void startGame() throws RemoteException;
    void endGame(boolean winner) throws RemoteException;
    void notifyTurn() throws RemoteException;
    Boolean ping() throws RemoteException;
}
