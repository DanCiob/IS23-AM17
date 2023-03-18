package it.polimi.softeng.model;
import it.polimi.softeng.customExceptions.illegalInsertException;

import java.util.ArrayList;

public class Shelfie {
    /**
     * matrix that represent the shelfie;
     * column numbering : goes from sx to dx (ie 0-->the furthest left)
     * row numbering : goes from bottom to top (ie 0 --> the lowest)
     */
    private final Tile [][] grid;

    /**
     * creates the grid object used to represent shelfie
     */
    public Shelfie() {
        grid = new Tile[6][5];
    }

    /**
     * inserTile takes an arrayList of tiles and, if legal, inserts them into the shelfie
     * @param tilesToBeInserted  arraylist of tiles ; the one in position 0 in the arrayList goes first, so on for the following
     * @param column  int that indicates the selected shelfie column; the numbering goes from sx to dx (ie 0-->the furthest left)
     * @throws illegalInsertException exception thrown as you try to insert too many tiles in a column
     */
    public void inserTile(ArrayList<Tile> tilesToBeInserted, int column) throws illegalInsertException {
        if(checkLegalInsert(tilesToBeInserted, column)){
            int i = 0;
            // i find the first null cell in the shelfie
            while (i < 6 && grid[i][column] != null) {
                i++;
            }
            // loop for tiles insertion
            for(int j = i; i < tilesToBeInserted.size(); j++) {
                grid[j][column] = tilesToBeInserted.get(j);
            }
        }else{
            throw new illegalInsertException();
        }

        //notify changes
        shelfieChangeNotifier();
    }

    /**
     *  checks whether the shelfie is full or not by controlling the last row
     * @return a boolean value
     */
    public boolean checkFull(){
        int count = 0;

        //i check for the last row cells counting the number of occupied places
        for(int i = 0; i < 5; i++){
            if(grid[6][i] != null) count++;
        }

        //count == 5 <==> shelfie is full
        return count == 5;
    }

    /**
     * checks whether the requested insert is allowed by checking if there are too many tiles for the selected column
     * @param tilesToBeInserted arraylist of tiles
     * @param column int that indicates the selected shelfie column; the numbering goes from sx to dx (ie 0-->the furthest left)
     * @return boolean value
     */
    public boolean checkLegalInsert(ArrayList<Tile> tilesToBeInserted, int column){

        //i find the row number of the first free cell from the selected column
        int i = 0;
        while (i < 6 && grid[i][column] != null) {
            i++;
        }

        return tilesToBeInserted.size() + i <= 6;
    }

    public Tile[][] getGrid() {
        return grid;
    }

    public String shelfieChangeNotifier(){
        return null;
    }
}
