package it.polimi.softeng.model;

import it.polimi.softeng.customExceptions.IllegalInsertException;
import static it.polimi.softeng.Constants.*;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class is the representation of shelfie.
 */
public class Shelfie implements Serializable {
    /**
     * This is the matrix that represent the shelfie.
     * Column numbering: goes from sx to dx (ie 0 --> the furthest left).
     * Row numbering: goes from bottom to top (ie 0 --> the lowest).
     */
    private final Tile [][] grid;

    /**
     * It creates the grid object used to represent shelfie.
     */
    public Shelfie() {
        grid = new Tile[shelfieRows][shelfieColumns];
    }

    /**
     * This method takes an arrayList of tiles and, if legal, inserts them into the shelfie.
     * @param tilesToBeInserted arraylist of tiles; the tile in position 0 in the arrayList goes first, so on for the following.
     * @param column int that indicates the selected shelfie column; the numbering goes from left to right (ie 0-->the furthest left).
     * @throws IllegalInsertException exception thrown when there is not enough space in the selected column of the shelfie.
     */
    public void insertTile(ArrayList<Tile> tilesToBeInserted, int column) throws IllegalInsertException {
        if(checkLegalInsert(tilesToBeInserted, column)){
            int i = 0;
            int k = 0;
            // I find the first null cell in the shelfie
            while (i < shelfieRows && grid[i][column] != null) {
                i++;
            }
            // loop for tiles insertion
            for(int j = i; j < i + tilesToBeInserted.size(); j++) {
                grid[j][column] = tilesToBeInserted.get(k);
                k++;
            }
        }else{
            if(tilesToBeInserted.isEmpty()){
                throw new IllegalInsertException("illegal insert caused by empty array");
            }
            else if(tilesToBeInserted.size() > maxTilesForMove){
                throw new IllegalInsertException("illegal insert caused by oversized array");
            }
            else{
                int i = 0;
                while (i < shelfieRows && grid[i][column] != null) {
                    i++;
                }
                throw new IllegalInsertException("illegal insert caused by overflow of column " + column + " because array size is " + tilesToBeInserted.size() + " but first free cell is in position "+ i);
            }
        }
    }

    /**
     * Getter method for the shelfie.
     * @param row row numbering: goes from bottom to top (ie 0 --> the lowest).
     * @param column column numbering: goes from sx to dx (ie 0-->the furthest left).
     * @return Tile from the desired position.
     */
    public Tile getTile(int row, int column){
        return grid[row][column];
    }

    /**
     * Getter method for when you need the whole grid.
     * @return the Tile matrix representing the shelfie grid.
     */
    public Tile[][] getGrid(){
        return grid;
    }

    /**
     * This method checks whether the shelfie is full or not by controlling the last row.
     * @return a boolean value.
     */
    public boolean checkFull(){
        int count = 0;

        //i check for the last row cells counting the number of occupied places
        for(int i = 0; i < shelfieColumns; i++){
            if(grid[shelfieRows - 1][i] != null) count++;
        }

        //count == shelfieColumns <==> shelfie is full
        return count == shelfieColumns;
    }

    /**
     * This method checks whether the requested insert is allowed by checking if there are too many tiles for the selected column.
     * @param tilesToBeInserted arraylist of tiles.
     * @param column int that indicates the selected shelfie column; the numbering goes from sx to dx (ie 0-->the furthest left).
     * @return boolean value.
     */
    public boolean checkLegalInsert(ArrayList<Tile> tilesToBeInserted, int column){
        //controls of correctness of the given array (just to be safe)
        if(tilesToBeInserted.size() > maxTilesForMove) return false;
        if(tilesToBeInserted.isEmpty()) return false;

        //I find the row number of the first free cell from the selected column
        int i = 0;
        while (i < shelfieRows && grid[i][column] != null) {
            i++;
        }

        return tilesToBeInserted.size() + i <= shelfieRows;
    }

    /**
     * Testing method for tile insertion.
     * @param tilesToBeInserted arrayList of tiles to be inserted.
     * @param column chosen column for the insertion.
     */
    public void insertTileForTesting(ArrayList<Tile> tilesToBeInserted, int column){
            int i = 0;
            int k = 0;
            // I find the first null cell in the shelfie
            while (i < shelfieRows && grid[i][column] != null) {
                i++;
            }
            // loop for tiles insertion
            for(int j = i; j < tilesToBeInserted.size(); j++) {
                grid[j][column] = tilesToBeInserted.get(k);
                k++;
            }
    }

    /**
     * This method is used to set a grid in a fast way; it creates a new tile with the desired id and color.
     * @param row selected row.
     * @param column selected column.
     * @param id id of selected tile.
     * @param color color of chosen tile.
     */
    public void setGrid(int row, int column, int id, Tile.TileColor color){
        grid[row][column] = new Tile(id, color);
    }

    /**
     * This method sets desired cell of shelfie to null.
     * @param row selected row.
     * @param column selected column.
     */
    public void setGridAtNull(int row, int column){
        grid[row][column] = null;
    }
}
