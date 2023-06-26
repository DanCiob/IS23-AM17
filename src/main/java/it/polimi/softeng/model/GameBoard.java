package it.polimi.softeng.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import static it.polimi.softeng.Constants.*;

/**
 * Model representation of GameBoard
 */
public class GameBoard implements Serializable {
    /**
     * Matrix representing the board.
     * Column numbering: goes from left to right.
     * Row numbering: goes from top to bottom.
     */
    private Tile[][] board = new Tile[boardRows][boardColumns];
    /**
     * ArrayList with the cells that can't be used.
     */
    private ArrayList<Cell> notAvailable = new ArrayList<>();

    public Tile[][] getBoard() {
        return board;
    }

    /**
     * This method define the notAvailable cells that are in the matrix but not in the board depending on the number of players.
     * @param numberOfPlayers indicates how many player are partecipating to the current game.
     */
    public void setNotAvailable(int numberOfPlayers) {
        Cell temp;

        /*
          the following loops define the notAvailable cells using the symmetries of the board
         */
        for(int j = 0; j < 3; j++){ //columns
            for(int i = 3 - j; i >= 0 ; i--){ //rows
                    temp = new Cell();//top left corner
                    temp.setRow(i);
                    temp.setColumn(j);
                    notAvailable.add(temp);

                    temp = new Cell(); //bottom right corner
                    temp.setRow(boardRows-1-i);
                    temp.setColumn(boardColumns-1-j);
                    notAvailable.add(temp);

                    temp = new Cell();//top right corner
                    temp.setRow(j);
                    temp.setColumn(boardColumns-1-i);
                    notAvailable.add(temp);

                    temp = new Cell(); //bottom left corner
                    temp.setRow(boardRows-1-j);
                    temp.setColumn(i);
                    notAvailable.add(temp);

            }
        }


        if(numberOfPlayers < 4){
            /*
              the following cells are used only if there are 4 players
             */
            temp = new Cell();
            temp.setRow(0);
            temp.setColumn(4);
            notAvailable.add(temp);

            temp = new Cell();
            temp.setRow(1);
            temp.setColumn(5);
            notAvailable.add(temp);

            temp = new Cell();
            temp.setRow(3);
            temp.setColumn(1);
            notAvailable.add(temp);

            temp = new Cell();
            temp.setRow(4);
            temp.setColumn(0);
            notAvailable.add(temp);

            temp = new Cell();
            temp.setRow(4);
            temp.setColumn(8);
            notAvailable.add(temp);

            temp = new Cell();
            temp.setRow(5);
            temp.setColumn(7);
            notAvailable.add(temp);

            temp = new Cell();
            temp.setRow(7);
            temp.setColumn(3);
            notAvailable.add(temp);

            temp = new Cell();
            temp.setRow(8);
            temp.setColumn(4);
            notAvailable.add(temp);
        }

        if(numberOfPlayers == 2){
            /*
              the following cells are used only if there are 3 players
             */
            temp = new Cell();
            temp.setRow(0);
            temp.setColumn(3);
            notAvailable.add(temp);

            temp = new Cell();
            temp.setRow(2);
            temp.setColumn(2);
            notAvailable.add(temp);

            temp = new Cell();
            temp.setRow(2);
            temp.setColumn(6);
            notAvailable.add(temp);

            temp = new Cell();
            temp.setRow(3);
            temp.setColumn(8);
            notAvailable.add(temp);

            temp = new Cell();
            temp.setRow(5);
            temp.setColumn(0);
            notAvailable.add(temp);

            temp = new Cell();
            temp.setRow(6);
            temp.setColumn(2);
            notAvailable.add(temp);

            temp = new Cell();
            temp.setRow(6);
            temp.setColumn(6);
            notAvailable.add(temp);


            temp = new Cell();
            temp.setRow(8);
            temp.setColumn(5);
            notAvailable.add(temp);
        }

    }

    public ArrayList<Cell> getNotAvailable() {
        return notAvailable;
    }

    /**
     * This method calls {@link #setNotAvailable(int)} method and initializes every tile of the board with null.
     * @param numberOfPlayers number of players in the current game.
     */
    public void resetBoard(int numberOfPlayers){
        setNotAvailable(numberOfPlayers);
        for(int i = 0; i<boardRows;i++){
            for(int j = 0; j<boardColumns;j++){
                board[i][j] = null;
            }
        }
    }


    /**
     * This method update board after gamemove.
     * @param positionsToBeRemoved contains coordinates of tiles to be removed.
     * @return true if removal is successful.
     */
    public boolean updateBoard(ArrayList<Cell> positionsToBeRemoved){
        /*
          after every move, it removes the tiles
         */

        if(checkLegalChoice(positionsToBeRemoved)) {
            /*
              it removes the tiles
             */
            System.out.println("Valid chosen tiles");
            for (Cell cell : positionsToBeRemoved)
                board[cell.getRow()][cell.getColumn()] = null;
            return true;
            }
        else {
            System.out.println("Error in chosen tiles");
            return false;
        }

    }

    /**
     * This method positions the tiles contained in the bag, in the available cells of the board.
     * @param bag contains the tiles of the game.
     */
    public void positionTiles(ArrayList<Tile> bag){
        Random random = new Random();
        boolean posNotUsable; //position in which we cannot position Tiles
        int pos;
        for(int i=0;i<boardRows;i++){
            for(int j=0;j<boardColumns;j++){
                posNotUsable = false;
                for(Cell cell1 : notAvailable){
                    if(cell1.getRow() == i && cell1.getColumn() == j)
                        posNotUsable = true;
                }
                if(board[i][j] == null && !posNotUsable){
                    pos = random.nextInt(bag.size());
                    board[i][j] = bag.get(pos);
                }
            }
        }
    }

    /**
     * This method checks if the tiles that the player want to pick up, can be removed or not.
     * @param positionsToBeRemoved ArrayList of cells of positions to be removed
     * @return true if the choice is legal, false if not.
     */
    public boolean checkLegalChoice(ArrayList<Cell> positionsToBeRemoved){
        int i, j;
        boolean posNotUsable;
        Cell cell2, cell3, cell4;
        /*The tiles you take must be adjacent to each other and form a straight line
          All the tiles you take must have at least one side free (not touching directly other tiles) at the beginning of your turn
        */

        for(Cell cell : positionsToBeRemoved){
            i = cell.getRow();
            j = cell.getColumn();
            posNotUsable = false;

            //You're choosing null tiles
            if (board[i][j] == null)
                return false;

            //Check if chosen tiles are only from available cells
            for(Cell cell1 : notAvailable){
                if(cell1.getRow() == i && cell1.getColumn() == j)
                    posNotUsable = true;
            }
            if(posNotUsable){
                return false;
            }
            if((i!=0 && i!=8 && j!=0 && j!=8) && (board[i+1][j] != null) && (board[i][j+1] != null) && (board[i-1][j] != null) && (board[i][j-1] != null)) {
                return false; /*false if the tile is completely surrounded by other tiles (up, down, left and right)*/
            }
        }

        if(positionsToBeRemoved.size() == 1){
            return true; //if the move takes one single tile, we don't need to verify it is aligned with others
        }
        if(positionsToBeRemoved.size() == 2){
            cell2 = positionsToBeRemoved.get(0);
            cell3 = positionsToBeRemoved.get(1);
            if(cell2.getRow() == cell3.getRow()) {
                if(cell2.getColumn() == cell3.getColumn() + 1)
                    return true;
                if(cell2.getColumn() == cell3.getColumn() - 1)
                    return true;
            }
            if(cell2.getColumn() == cell3.getColumn()){
                if(cell2.getRow() == cell3.getRow() + 1)
                    return true;
                if(cell2.getRow() == cell3.getRow() - 1)
                    return true;

            }
        }
        if(positionsToBeRemoved.size() == 3){
            cell2 = positionsToBeRemoved.get(0);
            cell3 = positionsToBeRemoved.get(1);
            cell4 = positionsToBeRemoved.get(2);
            if(cell2.getRow() == cell3.getRow() && cell2.getRow() == cell4.getRow()){
                int c2 = cell2.getColumn(), c3 = cell3.getColumn(), c4 = cell4.getColumn();
                if(c2==c3-1 && c3==c4-1)
                    return true;
                if(c2==c4-1 && c4==c3-1)
                    return true;
                if(c3==c2-1 && c2==c4-1)
                    return true;
                if(c3==c4-1 && c4==c2-1)
                    return true;
                if(c4==c2-1 && c2==c3-1)
                    return true;
                if(c4==c3-1 && c3==c2-1)
                    return true;
            }
            if(cell2.getColumn() == cell3.getColumn() && cell2.getColumn() == cell4.getColumn()){
                int r2 = cell2.getRow(), r3 = cell3.getRow(), r4 = cell4.getRow();
                if(r2==r3-1 && r3==r4-1)
                    return true;
                if(r2==r4-1 && r4==r3-1)
                    return true;
                if(r3==r2-1 && r2==r4-1)
                    return true;
                if(r3==r4-1 && r4==r2-1)
                    return true;
                if(r4==r2-1 && r2==r3-1)
                    return true;
                if(r4==r3-1 && r3==r2-1)
                    return true;
            }
        }

        return false;
    }

    /**
     * This method checks if there are only islands on the board.
     * @return true if there are only island Tiles, false if not.
     */

    public boolean checkIslands(){
        for(int i=0; i<boardRows; i++){
            for(int j=0; j<boardColumns; j++){
                if(board[i][j]!=null && ((i>0 && board[i-1][j]!=null) || (j>0 && board[i][j-1]!=null) || (i<boardRows-1 && board[i+1][j]!=null) || (j<boardRows-1 && board[i][j+1]!=null))){
                    return false;
                }
            }
        }
        return true; //there are only islands
    }

    /*
    This method is only for testing, to quickly insert a tile in a specific position.
    */
    public void setBoardforTest(int  i, int j) {
        board[i][j] = new Tile(0, Tile.TileColor.BLUE);
    }

    public void setBoard(int  i, int j, Tile tile) {
        board[i][j] = tile;
    }

    /**
     * This method is used when a player try to insert more tiles than the free places of a row, so we need to reinsert the tiles in the board.
     */
    public void reinsertTiles(ArrayList<Tile> tiles, ArrayList<Cell> cells){
        for(int i=0;i< cells.size();i++){
            setBoard(cells.get(i).getRow(), cells.get(i).getColumn(),  tiles.get(i));
        }
    }
}
