package it.polimi.softeng.model;
import java.util.ArrayList;

public class Board {
    private Tile[][] board = new Tile[9][9];
    private ArrayList<Cell> notAvailable = new ArrayList<Cell>(); //arrayList with the cell that can't be used


    public Tile[][] getBoard() {
        return board;
    }

    public void setNotAvailable(int numberOfPlayer) {

    }

    public Board resetBoard(int numberOfPlayer){
        //it places the tiles at the beginning of the game and at the resetting of the gameboard during the game

    }

    public void updateBoard(ArrayList<Cell> positionsToBeRemoved){
        //after every moves, it removes the tiles
        if(checkLegalChoice(positionsToBeRemoved) == 1){
            //it removes the tiles
            for(Cell cell : ArrayList<Cell>){
                board[cell.getX()][cell.getY()] = null;
            }
        }
    }

    public boolean checkLegalChoice(ArrayList<Cell> positionsToBeRemoved){
        int i, j, k=0;
        int mat[positionsToBeRemoved.size()][2]; //it contains the positions of the tile to be removed, to check if they are adjacent
        //The tiles you take must be adjacent to each other and form a straight line.
        //All the tiles you take must have at least one side free (not touching directly other tiles) at the beginning of your turn (i.e. you cannot take a tile that becomes free after your first pick).
        for(Cell cell : ArrayList<Cell>){
            i = cell.getX();
            j = cell.getY();
            if((board[i+1][j] != null) && (board[i][j+1] != null) && (board[i-1][j] != null) && (board[i][j-1] != null))
                return 0; //the tiles don't have at least one side free
            mat[k][0] = i;
            mat[k][1] = j;
            k++;
        }

        for(i=0;i<positionsToBeRemoved.size();i++){ //it verifies the coordinates of the rows
            if(mat[i][0] != mat[i+1][0])
                break;
        }

        for(i=0;i<positionsToBeRemoved.size();i++){//it verifies the coordinates of the columns
            if(mat[i][1] != mat[i+1][1]) {
                return 0;//the chosen tiles aren't adjacent because they don't have one coordination in common
            }
        }
        return 1;
    }

    public String boardChangeNotifier(){

    }


}
