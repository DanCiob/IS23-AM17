package it.polimi.softeng.model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Board {
    private Tile[][] board = new Tile[9][9];
    private ArrayList<Cell> notAvailable = new ArrayList<Cell>(); /**arrayList with the cell that can't be used**/


    public Tile[][] getBoard() {
        return board;
    }

    public void setNotAvailable(int numberOfPlayers) {
        Cell temp = new Cell();
        /**
         * the following loops define the notAvailable cells that are in the matrix but not in the board
         */


        /**
         * top left and bottom right (symmetrical)
         */
        for(int i =0;i<4;i++){
            for(int j = 3-i; j>=0 ; j--){
                if((i!=0)||(j!=3)){
                    temp.setX(i);
                    temp.setY(j);
                    notAvailable.add(temp);

                    temp.setX(8-i);
                    temp.setY(8-j);
                    notAvailable.add(temp);
                }
            }
        }


        /**
         * top right and bottom left (symmetrical)
         */
        for(int i =0;i<3;i++){
            for(int j = i+5; j<9 ; j++){
                temp.setX(i);
                temp.setY(j);
                notAvailable.add(temp);

                temp.setX(8-i);
                temp.setY(8-j);
                notAvailable.add(temp);

            }
        }



        if(numberOfPlayers < 4){
            /**
             * the following cells are used only if there are 4 players
             */
            temp.setX(0);
            temp.setY(4);
            notAvailable.add(temp);

            temp.setX(1);
            temp.setY(5);
            notAvailable.add(temp);

            temp.setX(3);
            temp.setY(1);
            notAvailable.add(temp);

            temp.setX(4);
            temp.setY(0);
            notAvailable.add(temp);

            temp.setX(4);
            temp.setY(8);
            notAvailable.add(temp);

            temp.setX(5);
            temp.setY(7);
            notAvailable.add(temp);

            temp.setX(7);
            temp.setY(3);
            notAvailable.add(temp);

            temp.setX(8);
            temp.setY(4);
            notAvailable.add(temp);
        }

        if(numberOfPlayers == 2){
            /**
             * the following cells are used only if there are 3 players
             */
            temp.setX(0);
            temp.setY(3);
            notAvailable.add(temp);

            temp.setX(2);
            temp.setY(2);
            notAvailable.add(temp);

            temp.setX(2);
            temp.setY(6);
            notAvailable.add(temp);

            temp.setX(3);
            temp.setY(8);
            notAvailable.add(temp);

            temp.setX(5);
            temp.setY(0);
            notAvailable.add(temp);

            temp.setX(6);
            temp.setY(2);
            notAvailable.add(temp);

            temp.setX(6);
            temp.setY(6);
            notAvailable.add(temp);

            temp.setX(8);
            temp.setY(5);
            notAvailable.add(temp);
        }







    }

    public Board resetBoard(int numberOfPlayers){
        /**
         *it inizialize every tiles of the board with null
         */

        setNotAvailable(numberOfPlayers);
        for(int i = 0; i<9;i++){
            for(int j = 0; j<9;j++){
                board[i][j] = null;
            }
        }
    }
}

    public void updateBoard(ArrayList<Cell> positionsToBeRemoved){
        /**
         * after every moves, it removes the tiles
         */

        if(checkLegalChoice(positionsToBeRemoved) == 1){
            /**
             * it removes the tiles
             */
            for(Cell cell : ArrayList<Cell>){
                board[cell.getX()][cell.getY()] = null;
            }
        }
    }

    public boolean checkLegalChoice(ArrayList<Cell> positionsToBeRemoved){
        int i, j, k=0;
        int mat[][] = new int[positionsToBeRemoved.size()][2]; /**it contains the positions of the tile to be removed, to check if they are adjacent**/
        /**The tiles you take must be adjacent to each other and form a straight line
         * All the tiles you take must have at least one side free (not touching directly other tiles) at the beginning of your turn
         */

        for(Cell cell : ArrayList<Cell>){
            i = cell.getX();
            j = cell.getY();
            if((board[i+1][j] != null) && (board[i][j+1] != null) && (board[i-1][j] != null) && (board[i][j-1] != null))
                return 0; /**the tiles don't have at least one side free**/
            mat[k][0] = i;
            mat[k][1] = j;
            k++;
        }

        for(i=0;i<positionsToBeRemoved.size();i++){ /**it verifies the coordinates of the rows**/
            if(mat[i][0] != mat[i+1][0])
                break;
        }

        for(i=0;i<positionsToBeRemoved.size();i++){/**it verifies the coordinates of the columns**/
            if(mat[i][1] != mat[i+1][1]) {
                /**
                 * the chosen tiles aren't adjacent because they don't have one coordination in common
                 */
                return 0;
            }
        }
        return 1;
    }

    public String boardChangeNotifier(){

    }


}
