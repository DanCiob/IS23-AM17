package it.polimi.softeng.model;
import java.util.ArrayList;
import java.util.Random;
import static it.polimi.softeng.model.Constants.*;


public class Board implements BoardSetter{
    /**
     * matrix that represents the board;
     * column numbering : goes from left to right
     * row numbering : goes from top to bottom
     */
    private final Tile[][] board = new Tile[boardRows][boardColumns];
    private ArrayList<Cell> notAvailable = new ArrayList<>(); /**arrayList with the cells that can't be used**/

    public Tile[][] getBoard() {
        return board;
    }

    public void setNotAvailable(int numberOfPlayers) {
        Cell temp;
        /*
          the following loops define the notAvailable cells that are in the matrix but not in the board
         */

        /*
          top left and bottom right (symmetrical)
         */

        for(int j = 0; j < 3; j++){ //columns
            for(int i = 3 - j; i >= 0 ; i--){ //rows
                    temp = new Cell();
                    temp.setRow(i);
                    temp.setColumn(j);
                    notAvailable.add(temp);

                    temp = new Cell();
                    temp.setRow(boardColumns-1-i);
                    temp.setColumn(boardRows-1-j);
                    notAvailable.add(temp);
            }
        }

        /*
          top right and bottom left (symmetrical)
         */
        for(int i =0;i<3;i++){
            for(int j = i+5; j<boardColumns ; j++){
                temp = new Cell();
                temp.setRow(i);
                temp.setColumn(j);
                notAvailable.add(temp);

                temp = new Cell();
                temp.setRow(boardColumns-1-i);
                temp.setColumn(boardRows-1-j);
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

    public void resetBoard(int numberOfPlayers){
        /*
         it initializes every tile of the board with null
         */
        setNotAvailable(numberOfPlayers);
        for(int i = 0; i<boardRows;i++){
            for(int j = 0; j<boardColumns;j++){
                board[i][j] = null;
            }
        }
    }

    public void updateBoard(ArrayList<Cell> positionsToBeRemoved){
        /*
          after every move, it removes the tiles
         */

        if(checkLegalChoice(positionsToBeRemoved)){
            /*
              it removes the tiles
             */
            for(Cell cell : positionsToBeRemoved){
                board[cell.getRow()][cell.getColumn()] = null;
            }
        }
    }

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
            if((cell2.getRow() == cell3.getRow()) || (cell2.getColumn() == cell3.getColumn()))
                return true;
        }
        if(positionsToBeRemoved.size() == 3){
            cell2 = positionsToBeRemoved.get(0);
            cell3 = positionsToBeRemoved.get(1);
            cell4 = positionsToBeRemoved.get(2);
            if((cell2.getRow() == cell3.getRow() && cell2.getRow() == cell4.getRow()) || (cell2.getColumn() == cell3.getColumn() && cell2.getColumn() == cell4.getColumn()))
                return true;
        }

        return false;
    }

    /**
     * This method checks if every Tile on the board is an island
     * @return true if there are only island Tiles, false if not
     */

    public boolean checkIslands(){
        for(int i=0; i<boardRows-1; i++){
            for(int j=0; j<boardColumns-1; j++){
                if(board[i][j]!=null && ((i>0 && board[i-1][j]!=null) || (j>0 && board[i][j-1]!=null) || (i<boardRows-1 && board[i+1][j]!=null) || (j<boardRows-1 &&board[i][j+1]!=null))){
                    return false;
                }
            }
        }
        return true; //there are only islands
    }

    /*
    public String boardChangeNotifier(){
    }
    */


}
