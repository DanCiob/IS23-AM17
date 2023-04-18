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
    public void setNotAvailable2Players(){
        int count = 0;
        Board board = new Board();
        board.setNotAvailable(2);

        for(Cell cell1 : board.getNotAvailable()){
            if(cell1.getY() == 5 && cell1.getX() == 8)
                count++;
            if(cell1.getX() == 7 && cell1.getY() == 3)
                count++;
            if(cell1.getX() == 5 && cell1.getY() == 1)
                count--;
            if(cell1.getX() == 4 && cell1.getY() == 8)
                count++;
        }
        assertTrue(count == 3);
    }

    @Test
    public void setNotAvailable3Players(){
        int count = 0;
        Board board = new Board();
        board.setNotAvailable(3);

        for(Cell cell1 : board.getNotAvailable()){
            if(cell1.getX() == 5 && cell1.getY() == 8)
                count++;
            if(cell1.getX() == 5 && cell1.getY() == 7)
                count++;
            if(cell1.getX() == 8 && cell1.getY() == 4)
                count++;
            if(cell1.getX() == 7 && cell1.getY() == 3)
                count++;
            if(cell1.getX() == 4 && cell1.getY() == 0)
                count++;
            if(cell1.getX() == 3 && cell1.getY() == 1)
                count++;
            if(cell1.getX() == 0 && cell1.getY() == 3)
                count=0;
            if(cell1.getX() == 2 && cell1.getY() == 2)
                count=0;
        }

        assertTrue(count == 6);
    }

    @Test
    public void setNotAvailable4Players(){
        int count = 0;
        Board board = new Board();
        board.setNotAvailable(4);
        for(Cell cell1 : board.getNotAvailable()){
            if(cell1.getY() == 0 && cell1.getX() == 0)
                count++;
            if(cell1.getX() == 0 && cell1.getY() == 1)
                count++;
            if(cell1.getX() == 1 && cell1.getY() == 1)
                count++;
            if(cell1.getX() == 0 && cell1.getY() == 1)
                count++;
            if(cell1.getX() == 3 && cell1.getY() == 0)
                count++;
            if(cell1.getX() == 0 && cell1.getY() == 2)
                count++;
            if(cell1.getX() == 1 && cell1.getY() == 2)
                count++;
            if(cell1.getX() == 2 && cell1.getY() == 1)
                count++;
            if(cell1.getX() == 6 && cell1.getY() == 1)
                count++;
            if(cell1.getX() == 7 && cell1.getY() == 2)
                count++;
            if(cell1.getX() == 8 && cell1.getY() == 3)
                count++;
            if(cell1.getX() == 0 && cell1.getY() == 5)
                count++;
            if(cell1.getX() == 1 && cell1.getY() == 6)
                count++;
            if(cell1.getX() == 2 && cell1.getY() == 7)
                count++;
            if(cell1.getX() == 5 && cell1.getY() == 8)
                count++;
            if(cell1.getX() == 6 && cell1.getY() == 7)
                count++;
            if(cell1.getX() == 7 && cell1.getY() == 6)
                count++;
            if(cell1.getX() == 8 && cell1.getY() == 6)
                count++;
            if(cell1.getX() == 0 && cell1.getY() == 3)
                fail();
            if(cell1.getX() == 0 && cell1.getY() == 5)
                count++;
            if(cell1.getX() == 0 && cell1.getY() == 4)
                fail();


        }

        assertTrue(count == 19);
    }




    @Test
    public void updateBoard0Tiles(){
        Board board = new Board();
        ArrayList<Cell> positionsToBeRemoved = new ArrayList<>();
        Cell cell = new Cell();
        Game game = new Game();



        //set the  tiles of the board at a value to check if it removes it correctly, using the method from the game to initialize the bag
        board.setNotAvailable(2);
        game.initializeTile();
        board.positionTiles(game.getTileBag());

        assertNotNull(board.getBoard()[5][1]);
        assertNotNull(board.getBoard()[4][1]);
        positionsToBeRemoved.add(cell);
        assertFalse(board.checkLegalChoice(positionsToBeRemoved));
        board.updateBoard(positionsToBeRemoved);
        assertNotNull(board.getBoard()[4][1]);
        assertNotNull(board.getBoard()[5][1]);
    }

    @Test
    public void updateBoard1Tiles(){
        Board board = new Board();
        ArrayList<Cell> positionsToBeRemoved = new ArrayList<>();
        //ArrayList<Cell> bag = new ArrayList<>();
        Cell cell1 = new Cell();
        Game game = new Game();



        //set the  tiles of the board at a value to check if it removes it correctly, using the method from the game to initialize the bag
        board.setNotAvailable(2);
        game.initializeTile();
        assertNull(board.getBoard()[1][3]);
        board.positionTiles(game.getTileBag());

        assertNotNull(board.getBoard()[1][3]);
        cell1.setX(1);
        cell1.setY(3);
        positionsToBeRemoved.add(cell1);
        assertTrue(board.checkLegalChoice(positionsToBeRemoved));
        board.updateBoard(positionsToBeRemoved);
        assertNull(board.getBoard()[1][3]);
    }

    @Test
    public void updateBoard2Tiles(){
        Board board = new Board();
        ArrayList<Cell> positionsToBeRemoved = new ArrayList<>();
        //ArrayList<Cell> bag = new ArrayList<>();
        Cell cell1 = new Cell();
        Cell cell2 = new Cell();
        Game game = new Game();



        //set the  tiles of the board at a value to check if it removes it correctly, using the method from the game to initialize the bag
        board.setNotAvailable(2);
        game.initializeTile();
        assertNull(board.getBoard()[5][1]);
        assertNull(board.getBoard()[4][1]);
        board.positionTiles(game.getTileBag());

        assertNotNull(board.getBoard()[5][1]);
        assertNotNull(board.getBoard()[4][1]);
        /* cell.setX(4);
        cell.setY(0);
        positionsToBeRemoved.add(cell);*/
        cell1.setX(5);
        cell1.setY(1);
        positionsToBeRemoved.add(cell1);
        cell2.setX(4);
        cell2.setY(1);
        positionsToBeRemoved.add(cell2);
        assertTrue(board.checkLegalChoice(positionsToBeRemoved));
        board.updateBoard(positionsToBeRemoved);
        assertNull(board.getBoard()[4][1]);
        assertNull(board.getBoard()[5][1]);
    }

    @Test
    public void updateBoard3Tiles(){
        Board board = new Board();
        ArrayList<Cell> positionsToBeRemoved = new ArrayList<>();
        //ArrayList<Cell> bag = new ArrayList<>();
        Cell cell1 = new Cell();
        Cell cell2 = new Cell();
        Cell cell3 = new Cell();
        Game game = new Game();



        //set the  tiles of the board at a value to check if it removes it correctly, using the method from the game to initialize the bag
        board.setNotAvailable(2);
        game.initializeTile();
        assertNull(board.getBoard()[1][3]);
        assertNull(board.getBoard()[1][4]);
        assertNull(board.getBoard()[2][3]);
        assertNull(board.getBoard()[2][4]);
        assertNull(board.getBoard()[2][5]);
        board.positionTiles(game.getTileBag());

        assertNotNull(board.getBoard()[1][3]);
        assertNotNull(board.getBoard()[1][4]);
        cell1.setX(1);
        cell1.setY(3);
        positionsToBeRemoved.add(cell1);
        cell2.setX(1);
        cell2.setY(4);
        positionsToBeRemoved.add(cell2);
        assertTrue(board.checkLegalChoice(positionsToBeRemoved));
        board.updateBoard(positionsToBeRemoved);
        assertNull(board.getBoard()[1][3]);
        assertNull(board.getBoard()[1][4]);
        positionsToBeRemoved.clear();


        assertNotNull(board.getBoard()[2][3]);
        assertNotNull(board.getBoard()[2][4]);
        assertNotNull(board.getBoard()[2][5]);
        cell1 = new Cell();
        cell2 = new Cell();
        cell3 = new Cell();
        cell1.setX(2);
        cell1.setY(3);
        positionsToBeRemoved.add(cell1);
        cell2.setX(2);
        cell2.setY(4);
        positionsToBeRemoved.add(cell2);
        cell3.setX(2);
        cell3.setY(5);
        positionsToBeRemoved.add(cell3);
        assertTrue(board.checkLegalChoice(positionsToBeRemoved));
        board.updateBoard(positionsToBeRemoved);
        assertNull(board.getBoard()[2][3]);
        assertNull(board.getBoard()[2][4]);
        assertNull(board.getBoard()[2][5]);
    }

    @Test
    public void checkLegalChoiceTest(){
        Board board = new Board();
        ArrayList<Cell> positionsToBeRemoved = new ArrayList<>();
        Cell cell = new Cell();
        Game game = new Game();
        board.setNotAvailable(4);
        board.resetBoard(4);
        game.initializeTile();
        board.positionTiles(game.getTileBag());

        cell.setX(0);
        cell.setY(0);
        positionsToBeRemoved.add(cell);
        assertFalse(board.checkLegalChoice(positionsToBeRemoved));

        positionsToBeRemoved = new ArrayList<>();
        cell = new Cell();
        cell.setX(5);
        cell.setY(0);
        positionsToBeRemoved.add(cell);
        assertTrue(board.checkLegalChoice(positionsToBeRemoved));
    }

    @Test
    public void positionTilesTest(){
        Board board = new Board();
        Game game = new Game();
        ArrayList<Cell> notAvailable = board.getNotAvailable();
        boolean posNotUsed;
        game.initializeTile();
        board.resetBoard(4);
        board.positionTiles(game.getTileBag());
        for(int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                posNotUsed = false;
                for(Cell cell1 : notAvailable){
                    if(cell1.getX() == i && cell1.getY() == j)
                        posNotUsed = true;
                }
                if(!posNotUsed){
                    assertNotNull(board.getBoard()[i][j]);
                }
            }
        }
    }


    @Test
    public void checkIslandsTest(){
        Board board = new Board();

    }
}
