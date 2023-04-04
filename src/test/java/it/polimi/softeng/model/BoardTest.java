package it.polimi.softeng.model;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    public void resetBoardTest(){
        Board board = new Board();
        board.resetBoard(2);

        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                assertNull(board.getBoard()[i][j]);
            }
        }

        board.resetBoard(3);

        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                assertNull(board.getBoard()[i][j]);
            }
        }

        board.resetBoard(4);

        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                assertNull(board.getBoard()[i][j]);
            }
        }

    }

    @Test
    public void setNotAvailableTest(){
        Board board = new Board();
        board.setNotAvailable(2);
        boolean test = false;

        for(Cell cell1 : board.getNotAvailable()){
            System.out.println(cell1.getX() + " " + cell1.getY());
            if(cell1.getY() == 5 && cell1.getX() == 8)
                test = true;
        }

        assertTrue(test);
    }


    @Test
    public void updateBoardTest(){
        Board board = new Board();
        ArrayList<Cell> positionsToBeRemoved = new ArrayList<>();
        //ArrayList<Cell> bag = new ArrayList<>();
        Cell cell = new Cell();
        GameForTest game = new GameForTest();



        //set the  tiles of the board at a value to check if it removes it correctly, using the method from the game to initialize the bag
        board.setNotAvailable(2);
        game.initializeTile();
        board.positionTiles(game.tileBag);
        assertNull(board.getBoard()[4][0]);
        assertNull(board.getBoard()[5][0]);

        /* cell.setX(4);
        cell.setY(0);
        positionsToBeRemoved.add(cell);*/
        cell.setX(5);
        cell.setY(0);
        positionsToBeRemoved.add(cell);
        assertFalse(board.checkLegalChoice(positionsToBeRemoved));
        board.updateBoard(positionsToBeRemoved);
        //assertNull(board.getBoard()[4][0]);
        assertNull(board.getBoard()[5][0]);
    }

    @Test
    public void checkLegalChoiceTest(){
        Board board = new Board();
        ArrayList<Cell> positionsToBeRemoved = new ArrayList<>();
        Cell cell = new Cell();
        GameForTest game = new GameForTest();
        board.setNotAvailable(4);
        board.resetBoard(4);
        game.initializeTile();
        board.positionTiles(game.tileBag);

        cell.setX(0);
        cell.setY(0);
        positionsToBeRemoved.add(cell);
        assertFalse(board.checkLegalChoice(positionsToBeRemoved));

        positionsToBeRemoved = new ArrayList<>();
        cell = new Cell();
        cell.setX(5);
        cell.setY(0);
        positionsToBeRemoved.add(cell);
        System.out.println(positionsToBeRemoved.get(0).getX() + " " + positionsToBeRemoved.get(0).getY());
        assertTrue(board.checkLegalChoice(positionsToBeRemoved));
    }
}
