package it.polimi.softeng.model.interfaces;

import it.polimi.softeng.customExceptions.IllegalInsertException;
import it.polimi.softeng.model.Tile;

import java.util.ArrayList;

/**
 * Communicate with Shelfie (CONTROLLER-MODEL)
 */
public interface ShelfieInterface {
    void insertTile(ArrayList<Tile> tilesToBeInserted, int column) throws IllegalInsertException;
}
