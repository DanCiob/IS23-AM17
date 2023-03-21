package it.polimi.softeng.model;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    public void resetBoardTest(){
        Board board = new Board();
        board.resetBoard(2);

        for(int i=0;i<6;i++){
            for(int j=0;j<5;j++){
                assertNull(board.getBoard()[i][j]);
            }
        }

        board.resetBoard(3);

        for(int i=0;i<6;i++){
            for(int j=0;j<5;j++){
                assertNull(board.getBoard()[i][j]);
            }
        }

        board.resetBoard(4);

        for(int i=0;i<6;i++){
            for(int j=0;j<5;j++){
                assertNull(board.getBoard()[i][j]);
            }
        }

    }


    @Test
    public void updateBoardTest(){
        Board board = new Board();
        ArrayList<Cell> positionsToBeRemoved = new ArrayList<>();
        Cell cell = new Cell();


        //!! TO DO: set the  tiles of the board at a value to check if it removes it correctly
        assertNotNull(board.getBoard()[1][4]);

        cell.setX(1);
        cell.setY(4);
        positionsToBeRemoved.add(cell);
        board.updateBoard(positionsToBeRemoved);
        assertNull(board.getBoard()[1][4]);
    }
}
