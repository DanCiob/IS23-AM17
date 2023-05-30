package it.polimi.softeng.connectionProtocol.client;

import it.polimi.softeng.client.view.UI;
import it.polimi.softeng.model.*;
import it.polimi.softeng.model.commonCards.CommonCards;
import org.json.simple.JSONObject;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * class that implements the methods for the RMI client interface
 */
public class ClientSideMethods implements ClientRemoteInterface {
    /**
     * reference to the ui object of which methods will be called
     */
    private UI ui;

    /**
     * constructor method for client's methods
     * @param ui the ui object of the client (ie cli or gui)
     */
    public ClientSideMethods(UI ui){
        this.ui = ui;
    }

    /**
     * invokes a player update
     * @param player player whose shelfie is sent (?)
     * @param s shelfie sent
     * @throws RemoteException remote exception
     */
    @Override
    public void playerUpdate(Player player, Shelfie s) throws RemoteException {
        ui.eventManager("systemEvent");
        JSONObject obj = new JSONObject();
        obj.put("requester", "System");
        obj.put("message", "Received " + player.getNickname() + "'s shelfie");
        ui.chatVisualizer(obj);
        ui.shelfieVisualizer(s.getGrid());
        ui.shelfieUpdater(s, player.getNickname());
    }

    /**
     * invokes a shelfie update
     * @param shelfie new shelfie
     * @throws RemoteException remote exception
     */
    @Override
    public void shelfieUpdate(Shelfie shelfie) throws RemoteException {
        ui.eventManager("shelfieEvent");
        ui.shelfieUpdater(shelfie);
        ui.shelfieVisualizer(shelfie.getGrid());
    }

    /**
     * invokes a gameboard update
     * @param board new gameboard
     * @throws RemoteException remote exception
     */
    @Override
    public void gameBoardUpdate(GameBoard board) throws RemoteException {
        ui.eventManager("boardEvent");
        ui.boardUpdater(board);
        ui.boardVisualizer(board.getBoard(), board.getNotAvailable());
    }

    /**
     * shows the personal cards
     * @param personalCards player's personal cards
     * @throws RemoteException remote exception
     */
    @Override
    public void sendPersonalCard(PersonalCards personalCards) throws RemoteException {
        ui.eventManager("personalCardEvent");
        ui.personalCardUpdater(personalCards);
        ui.personalCardVisualizer(personalCards);
    }

    /**
     * show common card to player
     * @param commonCards common card object
     * @param show it's true if commoncards will be show to user
     * @throws RemoteException remote exception
     */
    @Override
    public void sendCommonCard(ArrayList<CommonCards> commonCards, boolean show) throws RemoteException {
        ui.eventManager("commonCardEvent");
        int i = 1;
        if (show) {
            for (CommonCards commonCard : commonCards) {
                ui.commonCardUpdater(commonCard.getName(), i);
                i++;
                ui.commonCardsVisualizer(commonCard.getName());
            }
        }
        else
            for (CommonCards commonCard : commonCards) {
                ui.commonCardUpdater(commonCard.getName(), i);
                i++;
            }
    }

    /**
     * shows badge to player
     * @param badge badge to be shown
     * @throws RemoteException remote exception
     */
    @Override
    public void sendBadge(Badge badge) throws RemoteException {
        ui.scoreUpdater(badge.getScore());
    }

    /**
     * shows chat message
     * @param message message to be shown
     * @param sender sender of the message
     * @throws RemoteException remote exception
     */
    @Override
    public void displayChatMessage(String message, String sender) throws RemoteException {

        String receiver = null;

        JSONObject obj = new JSONObject();
        obj.put("requester", sender);
        obj.put("message", message);
        ui.chatVisualizer(obj);
    }

    /**
     * shows the new player list
     * @param playerList current player list
     * @throws RemoteException remote exception
     */
    @Override
    public void playerListUpdate(ArrayList<Player> playerList) throws RemoteException {
        ui.eventManager("playerEvent");
        ui.scoreVisualizer(playerList);
    }

    /**
     * gives the command to start the game to the ui
     * @throws RemoteException remote exception
     */
    @Override
    public void startGame() throws RemoteException {
        ui.beginGame(true);
    }

    /**
     * gives the command to end the game
     * @param winner boolean indicating whether the player to which this method is called is the winner
     * @throws RemoteException remote exception
     */
    @Override
    public void endGame(boolean winner) throws RemoteException {
        ui.endGame(winner);
    }

    /**
     * notifies the player that it is its turn
     * @throws RemoteException remote exception
     */
    @Override
    public void notifyTurn() throws RemoteException {
        ui.eventManager("myTurn");
    }

    /**
     * method used from the server to assert the connection with a client
     * @return generic boolean value true; if the connection doesn't work it will throw remote exception anyway
     * @throws RemoteException remote exception
     */
    @Override
    public Boolean ping() throws RemoteException {
        return true;
    }

}
