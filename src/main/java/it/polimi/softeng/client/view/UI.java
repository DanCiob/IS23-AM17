package it.polimi.softeng.client.view;

import it.polimi.softeng.model.Cell;
import it.polimi.softeng.model.PersonalCards;
import it.polimi.softeng.model.Player;
import it.polimi.softeng.model.Tile;
import org.json.simple.JSONObject;

import java.util.ArrayList;

/**
 * UI interfaces shows method that CLI and GUI implements to show information about game
 */
public interface UI {
    /**
     * Visualizer of GameBoard state
     */
    public void boardVisualizer(Tile[][] board, ArrayList<Cell> notAvailable);

    /**
     * Visualizer of Shelfie state
     */
    public void shelfieVisualizer(Tile[][] shelfie);

    /**
     * Visualizer of CommonCards used in game
     */
    public void commonCardsVisualizer(String commonCard);

    /**
     * Visualizer of PersonalCard
     */
    public void personalCardVisualizer(PersonalCards personalCard);

    /**
     * Visualizer of Chat
     */
    public void chatVisualizer(JSONObject jsonMessage);

    /**
     * Visualizer of current connected players
     */
    public void scoreVisualizer(ArrayList<Player> players);

    public void loginListener(String nickname);
}
