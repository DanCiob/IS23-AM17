package it.polimi.softeng.model;

import it.polimi.softeng.customExceptions.IllegalInsertException;

import java.util.ArrayList;

public interface ShelfieSetter {
    void insertTile(ArrayList<Tile> tilesToBeInserted, int column) throws IllegalInsertException;
}
