package it.polimi.softeng.model.interfaces;

import it.polimi.softeng.model.Game;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Communicate with Game Class (CONTROLLER-MODEL)
 */
public interface GameInterface {
    public void beginGame(ArrayList<String> nameList);
}
