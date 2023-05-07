package it.polimi.softeng.model.interfaces;

import it.polimi.softeng.model.Player;

/**
 * Communicate with Player class (CONTROLLER-MODEL)
 */
public interface PlayerInterface {
    public void createNewPlayer (String nickname);

    /**
     * Add logged players or reconnected to game
     * @param nickname
     */
    public void moveToActivePlayers (String nickname);

    /**
     * Remove disconnected players to game
     * @param nickname
     */
    public void moveToDisconnectedPlayers (String nickname);

    public Player getCurrentPlayer();
    public void setNextPlayer();
}
