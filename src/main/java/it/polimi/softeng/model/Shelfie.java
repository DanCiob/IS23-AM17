package it.polimi.softeng.model;
import it.polimi.softeng.customExceptions.IllegalInsertException;


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
     * insertTile takes an arrayList of tiles and, if legal, inserts them into the shelfie
     * @param tilesToBeInserted  arraylist of tiles ; the one in position 0 in the arrayList goes first, so on for the following
     * @param column  int that indicates the selected shelfie column; the numbering goes from sx to dx (ie 0-->the furthest left)
     * @throws IllegalInsertException exception thrown as you try to insert too many tiles in a column
     */
    public void insertTile(ArrayList<Tile> tilesToBeInserted, int column) throws IllegalInsertException {
        if(checkLegalInsert(tilesToBeInserted, column)){
            int i = 0;
            int k = 0;
            // I find the first null cell in the shelfie
            while (i < 6 && grid[i][column] != null) {
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
            else if(tilesToBeInserted.size() > 3){
                throw new IllegalInsertException("illegal insert caused by oversized array");
            }
            else{
                int i = 0;
                while (i < 6 && grid[i][column] != null) {
                    i++;
                }
                throw new IllegalInsertException("illegal insert caused by overflow of column " + column + " because array size is " + tilesToBeInserted.size() + " but first free cell is in position "+ i);
            }
        }

        //notify changes
        shelfieChangeNotifier();
    }

    /**
     * getter method for the shelfie
     * @param row row numbering : goes from bottom to top (ie 0 --> the lowest)
     * @param column column numbering : goes from sx to dx (ie 0-->the furthest left)
     * @return Tile from the desired position
     */
    public Tile getTile(int row, int column){
        return grid[row][column];
    }

    /**
     * getter method for when you need the whole grid
     * @return the Tile matrix representing the shelfie grid
     */
    public Tile[][] getGrid(){
        return grid;
    }
    /**
     *  checks whether the shelfie is full or not by controlling the last row
     * @return a boolean value
     */
    public boolean checkFull(){
        int count = 0;

        //i check for the last row cells counting the number of occupied places
        for(int i = 0; i < 5; i++){
            if(grid[5][i] != null) count++;
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
        //controls of correctness of the given array (just to be safe)
        if(tilesToBeInserted.size() > 3) return false;
        if(tilesToBeInserted.isEmpty()) return false;

        //I find the row number of the first free cell from the selected column
        int i = 0;
        while (i < 6 && grid[i][column] != null) {
            i++;
        }

        return tilesToBeInserted.size() + i <= 6;
    }

    public void insertTileForTesting(ArrayList<Tile> tilesToBeInserted, int column){
            int i = 0;
            int k = 0;
            // I find the first null cell in the shelfie
            while (i < 6 && grid[i][column] != null) {
                i++;
            }
            // loop for tiles insertion
            for(int j = i; j < i + tilesToBeInserted.size(); j++) {
                grid[j][column] = tilesToBeInserted.get(k);
                k++;
            }
    }




    public String shelfieChangeNotifier(){
        return null;
    }
}
