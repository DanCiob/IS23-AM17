package it.polimi.softeng.client.view;

import it.polimi.softeng.model.*;
import it.polimi.softeng.model.commonCards.CommonCards;
import org.json.simple.JSONObject;

import java.util.ArrayList;

/**
 * UI interfaces shows method that CLI and GUI implements to show information about game
 */
public interface UI {

    ///////////////
    //VISUALIZERS//
    ///////////////

    /**
     * Visualizer of GameBoard state
     */
    void boardVisualizer(Tile[][] board, ArrayList<Cell> notAvailable);

    /**
     * Visualizer of Shelfie state
     */
     void shelfieVisualizer(Tile[][] shelfie);

    /**
     * Visualizer of CommonCards used in game
     */
     void commonCardsVisualizer(String commonCard);

    void commonCardsVisualizer(CommonCards commonCard);

    /**
     * Visualizer of PersonalCard
     */
     void personalCardVisualizer(PersonalCards personalCard);

    /**
     * Visualizer of Chat
     */
     void chatVisualizer(JSONObject jsonMessage);

    /**
     * Visualizer of current connected players
     */
     void scoreVisualizer(ArrayList<Player> players);

    /**
     * Manage event received from server
     * @param event is string containing event
     */
    void eventManager(String event);

    ////////////
    //UPDATERS//
    ////////////

    void boardUpdater(GameBoard b);

    void shelfieUpdater(Shelfie s);

    void shelfieUpdater(Shelfie s, String nickname);

    void scoreUpdater(int s);

    void personalCardUpdater (PersonalCards pc);

    void commonCardUpdater (String nameOfCommonCard, int whatCommonCard);

    void setServerAddress(String serverAddress);

    void setPort(int port);

    //////////////
    //GAME PHASE//
    //////////////
    void endGame(boolean winner);

    void beginGame(boolean b);

}

