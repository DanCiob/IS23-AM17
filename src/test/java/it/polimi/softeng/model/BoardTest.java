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
            if(cell1.getColumn() == 5 && cell1.getRow() == 8)
                count++;
            if(cell1.getRow() == 7 && cell1.getColumn() == 3)
                count++;
            if(cell1.getRow() == 5 && cell1.getColumn() == 1)
                count--;
            if(cell1.getRow() == 4 && cell1.getColumn() == 8)
                count++;
        }
        assertEquals(3, count);
    }

    @Test
    public void setNotAvailable3Players(){
        int count = 0;
        Board board = new Board();
        board.setNotAvailable(3);

        for(Cell cell1 : board.getNotAvailable()){
            if(cell1.getRow() == 5 && cell1.getColumn() == 8)
                count++;
            if(cell1.getRow() == 5 && cell1.getColumn() == 7)
                count++;
            if(cell1.getRow() == 8 && cell1.getColumn() == 4)
                count++;
            if(cell1.getRow() == 7 && cell1.getColumn() == 3)
                count++;
            if(cell1.getRow() == 4 && cell1.getColumn() == 0)
                count++;
            if(cell1.getRow() == 3 && cell1.getColumn() == 1)
                count++;
            if(cell1.getRow() == 0 && cell1.getColumn() == 3)
                count=0;
            if(cell1.getRow() == 2 && cell1.getColumn() == 2)
                count=0;
        }

        assertEquals(6, count);
    }

    @Test
    public void setNotAvailable4Players(){
        int count = 0;
        Board board = new Board();
        board.setNotAvailable(4);
        for(Cell cell1 : board.getNotAvailable()){
            if(cell1.getColumn() == 0 && cell1.getRow() == 0)
                count++;
            if(cell1.getRow() == 0 && cell1.getColumn() == 1)
                count++;
            if(cell1.getRow() == 1 && cell1.getColumn() == 1)
                count++;
            if(cell1.getRow() == 0 && cell1.getColumn() == 1)
                count++;
            if(cell1.getRow() == 3 && cell1.getColumn() == 0)
                count++;
            if(cell1.getRow() == 0 && cell1.getColumn() == 2)
                count++;
            if(cell1.getRow() == 1 && cell1.getColumn() == 2)
                count++;
            if(cell1.getRow() == 2 && cell1.getColumn() == 1)
                count++;
            if(cell1.getRow() == 6 && cell1.getColumn() == 1)
                count++;
            if(cell1.getRow() == 7 && cell1.getColumn() == 2)
                count++;
            if(cell1.getRow() == 8 && cell1.getColumn() == 3)
                count++;
            if(cell1.getRow() == 0 && cell1.getColumn() == 5)
                count++;
            if(cell1.getRow() == 1 && cell1.getColumn() == 6)
                count++;
            if(cell1.getRow() == 2 && cell1.getColumn() == 7)
                count++;
            if(cell1.getRow() == 5 && cell1.getColumn() == 8)
                count++;
            if(cell1.getRow() == 6 && cell1.getColumn() == 7)
                count++;
            if(cell1.getRow() == 7 && cell1.getColumn() == 6)
                count++;
            if(cell1.getRow() == 8 && cell1.getColumn() == 6)
                count++;
            if(cell1.getRow() == 0 && cell1.getColumn() == 3)
                fail();
            if(cell1.getRow() == 0 && cell1.getColumn() == 5)
                count++;
            if(cell1.getRow() == 0 && cell1.getColumn() == 4)
                fail();


        }

        assertEquals(19, count);
    }

    @Test
    public void setNotAvailable2PlayersB(){
        int count = 0;
        Board board = new Board();

        board.setNotAvailable(2);
        for(Cell cell1 : board.getNotAvailable()){
            if(cell1.getRow() == 0 && cell1.getColumn() == 0)//top left (all not available cells)
                count++;
            if(cell1.getRow() == 0 && cell1.getColumn() == 1)
                count++;
            if(cell1.getRow() == 0 && cell1.getColumn() == 2)
                count++;
            if(cell1.getRow() == 1 && cell1.getColumn() == 0)
                count++;
            if(cell1.getRow() == 1 && cell1.getColumn() == 1)
                count++;
            if(cell1.getRow() == 1 && cell1.getColumn() == 2)
                count++;
            if(cell1.getRow() == 2 && cell1.getColumn() == 0)
                count++;
            if(cell1.getRow() == 2 && cell1.getColumn() == 1)
                count++;
            if(cell1.getRow() == 3 && cell1.getColumn() == 0)
                count++;

            if(cell1.getRow() == 6 && cell1.getColumn() == 0)//bottom left (border cells)
                count++;
            if(cell1.getRow() == 6 && cell1.getColumn() == 1)
                count++;
            if(cell1.getRow() == 7 && cell1.getColumn() == 2)
                count++;
            if(cell1.getRow() == 8 && cell1.getColumn() == 3)
                count++;

            if(cell1.getRow() == 0 && cell1.getColumn() == 5)//top right
                count++;
            if(cell1.getRow() == 1 && cell1.getColumn() == 6)
                count++;
            if(cell1.getRow() == 2 && cell1.getColumn() == 7)
                count++;
            if(cell1.getRow() == 2 && cell1.getColumn() == 8)
                count++;

            if(cell1.getRow() == 5 && cell1.getColumn() == 8)//bottom right
                count++;
            if(cell1.getRow() == 6 && cell1.getColumn() == 7)
                count++;
            if(cell1.getRow() == 7 && cell1.getColumn() == 6)
                count++;
            if(cell1.getRow() == 8 && cell1.getColumn() == 6)
                count++;

            if(cell1.getRow() == 0 && cell1.getColumn() == 3)//cell available for 3+ players
                count++;
            if(cell1.getRow() == 2 && cell1.getColumn() == 2)
                count++;
            if(cell1.getRow() == 2 && cell1.getColumn() == 6)
                count++;
            if(cell1.getRow() == 3 && cell1.getColumn() == 8)
                count++;
            if(cell1.getRow() == 5 && cell1.getColumn() == 0)
                count++;
            if(cell1.getRow() == 6 && cell1.getColumn() == 2)
                count++;
            if(cell1.getRow() == 6 && cell1.getColumn() == 6)
                count++;
            if(cell1.getRow() == 8 && cell1.getColumn() == 5)
                count++;

            if(cell1.getRow() == 0 && cell1.getColumn() == 4)//cells available for 4 players
                count++;
            if(cell1.getRow() == 1 && cell1.getColumn() == 5)
                count++;
            if(cell1.getRow() == 3 && cell1.getColumn() == 1)
                count++;
            if(cell1.getRow() == 4 && cell1.getColumn() == 0)
                count++;
            if(cell1.getRow() == 4 && cell1.getColumn() == 8)
                count++;
            if(cell1.getRow() == 5 && cell1.getColumn() == 7)
                count++;
            if(cell1.getRow() == 7 && cell1.getColumn() == 3)
                count++;
            if(cell1.getRow() == 8 && cell1.getColumn() == 4)
                count++;

            if(cell1.getRow() == 3 && cell1.getColumn() == 4)//random cells in the centre of the board
                fail();
            if(cell1.getRow() == 7 && cell1.getColumn() == 4)
                fail();
            if(cell1.getRow() == 4 && cell1.getColumn() == 2)
                fail();
        }

        assertEquals(37, count);
    }

    @Test
    public void setNotAvailable3PlayersB(){
        int count = 0;
        Board board = new Board();
        board.setNotAvailable(3);

        for(Cell cell1 : board.getNotAvailable()){
            if(cell1.getRow() == 0 && cell1.getColumn() == 0)//top left (all not available cells)
                count++;
            if(cell1.getRow() == 0 && cell1.getColumn() == 1)
                count++;
            if(cell1.getRow() == 0 && cell1.getColumn() == 2)
                count++;
            if(cell1.getRow() == 1 && cell1.getColumn() == 0)
                count++;
            if(cell1.getRow() == 1 && cell1.getColumn() == 1)
                count++;
            if(cell1.getRow() == 1 && cell1.getColumn() == 2)
                count++;
            if(cell1.getRow() == 2 && cell1.getColumn() == 0)
                count++;
            if(cell1.getRow() == 2 && cell1.getColumn() == 1)
                count++;
            if(cell1.getRow() == 3 && cell1.getColumn() == 0)
                count++;

            if(cell1.getRow() == 6 && cell1.getColumn() == 0)//bottom left (border cells)
                count++;
            if(cell1.getRow() == 6 && cell1.getColumn() == 1)
                count++;
            if(cell1.getRow() == 7 && cell1.getColumn() == 2)
                count++;
            if(cell1.getRow() == 8 && cell1.getColumn() == 3)
                count++;

            if(cell1.getRow() == 0 && cell1.getColumn() == 5)//top right
                count++;
            if(cell1.getRow() == 1 && cell1.getColumn() == 6)
                count++;
            if(cell1.getRow() == 2 && cell1.getColumn() == 7)
                count++;
            if(cell1.getRow() == 2 && cell1.getColumn() == 8)
                count++;

            if(cell1.getRow() == 5 && cell1.getColumn() == 8)//bottom right
                count++;
            if(cell1.getRow() == 6 && cell1.getColumn() == 7)
                count++;
            if(cell1.getRow() == 7 && cell1.getColumn() == 6)
                count++;
            if(cell1.getRow() == 8 && cell1.getColumn() == 6)
                count++;

            if(cell1.getRow() == 0 && cell1.getColumn() == 3)//cell available for 3+ players
                fail();
            if(cell1.getRow() == 2 && cell1.getColumn() == 2)
                fail();
            if(cell1.getRow() == 2 && cell1.getColumn() == 6)
                fail();
            if(cell1.getRow() == 3 && cell1.getColumn() == 8)
                fail();
            if(cell1.getRow() == 5 && cell1.getColumn() == 0)
                fail();
            if(cell1.getRow() == 6 && cell1.getColumn() == 2)
                fail();
            if(cell1.getRow() == 6 && cell1.getColumn() == 6)
                fail();
            if(cell1.getRow() == 8 && cell1.getColumn() == 5)
                fail();

            if(cell1.getRow() == 0 && cell1.getColumn() == 4)//cells available for 4 players
                count++;
            if(cell1.getRow() == 1 && cell1.getColumn() == 5)
                count++;
            if(cell1.getRow() == 3 && cell1.getColumn() == 1)
                count++;
            if(cell1.getRow() == 4 && cell1.getColumn() == 0)
                count++;
            if(cell1.getRow() == 4 && cell1.getColumn() == 8)
                count++;
            if(cell1.getRow() == 5 && cell1.getColumn() == 7)
                count++;
            if(cell1.getRow() == 7 && cell1.getColumn() == 3)
                count++;
            if(cell1.getRow() == 8 && cell1.getColumn() == 4)
                count++;

            if(cell1.getRow() == 3 && cell1.getColumn() == 5)//random cells in the centre of the board
                fail();
            if(cell1.getRow() == 6 && cell1.getColumn() == 4)
                fail();
            if(cell1.getRow() == 4 && cell1.getColumn() == 7)
                fail();
        }

        assertEquals(29, count);
    }

    @Test
    public void setNotAvailable4PlayersB(){
        int count = 0;
        Board board = new Board();

        board.setNotAvailable(4);
        for(Cell cell1 : board.getNotAvailable()){
            if(cell1.getRow() == 0 && cell1.getColumn() == 0)//top left (all not available cells)
                count++;
            if(cell1.getRow() == 0 && cell1.getColumn() == 1)
                count++;
            if(cell1.getRow() == 0 && cell1.getColumn() == 2)
                count++;
            if(cell1.getRow() == 1 && cell1.getColumn() == 0)
                count++;
            if(cell1.getRow() == 1 && cell1.getColumn() == 1)
                count++;
            if(cell1.getRow() == 1 && cell1.getColumn() == 2)
                count++;
            if(cell1.getRow() == 2 && cell1.getColumn() == 0)
                count++;
            if(cell1.getRow() == 2 && cell1.getColumn() == 1)
                count++;
            if(cell1.getRow() == 3 && cell1.getColumn() == 0)
                count++;

            if(cell1.getRow() == 6 && cell1.getColumn() == 0)//bottom left (border cells)
                count++;
            if(cell1.getRow() == 6 && cell1.getColumn() == 1)
                count++;
            if(cell1.getRow() == 7 && cell1.getColumn() == 2)
                count++;
            if(cell1.getRow() == 8 && cell1.getColumn() == 3)
                count++;

            if(cell1.getRow() == 0 && cell1.getColumn() == 5)//top right
                count++;
            if(cell1.getRow() == 1 && cell1.getColumn() == 6)
                count++;
            if(cell1.getRow() == 2 && cell1.getColumn() == 7)
                count++;
            if(cell1.getRow() == 2 && cell1.getColumn() == 8)
                count++;

            if(cell1.getRow() == 5 && cell1.getColumn() == 8)//bottom right
                count++;
            if(cell1.getRow() == 6 && cell1.getColumn() == 7)
                count++;
            if(cell1.getRow() == 7 && cell1.getColumn() == 6)
                count++;
            if(cell1.getRow() == 8 && cell1.getColumn() == 6)
                count++;

            if(cell1.getRow() == 0 && cell1.getColumn() == 3)//cell available for 3+ players
                fail();
            if(cell1.getRow() == 2 && cell1.getColumn() == 2)
                fail();
            if(cell1.getRow() == 2 && cell1.getColumn() == 6)
                fail();
            if(cell1.getRow() == 3 && cell1.getColumn() == 8)
                fail();
            if(cell1.getRow() == 5 && cell1.getColumn() == 0)
                fail();
            if(cell1.getRow() == 6 && cell1.getColumn() == 2)
                fail();
            if(cell1.getRow() == 6 && cell1.getColumn() == 6)
                fail();
            if(cell1.getRow() == 8 && cell1.getColumn() == 5)
                fail();

            if(cell1.getRow() == 0 && cell1.getColumn() == 4)//cells available for 4 players
                fail();
            if(cell1.getRow() == 1 && cell1.getColumn() == 5)
                fail();
            if(cell1.getRow() == 3 && cell1.getColumn() == 1)
                fail();
            if(cell1.getRow() == 4 && cell1.getColumn() == 0)
                fail();
            if(cell1.getRow() == 4 && cell1.getColumn() == 8)
                fail();
            if(cell1.getRow() == 5 && cell1.getColumn() == 7)
                fail();
            if(cell1.getRow() == 7 && cell1.getColumn() == 3)
                fail();
            if(cell1.getRow() == 8 && cell1.getColumn() == 4)
                fail();

            if(cell1.getRow() == 3 && cell1.getColumn() == 2)//random cells in the centre of the board
                fail();
            if(cell1.getRow() == 3 && cell1.getColumn() == 7)
                fail();
            if(cell1.getRow() == 4 && cell1.getColumn() == 6)
                fail();
        }

        assertEquals(21, count);
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
        cell1.setRow(1);
        cell1.setColumn(3);
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
        cell1.setRow(5);
        cell1.setColumn(1);
        positionsToBeRemoved.add(cell1);
        cell2.setRow(4);
        cell2.setColumn(1);
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
        cell1.setRow(1);
        cell1.setColumn(3);
        positionsToBeRemoved.add(cell1);
        cell2.setRow(1);
        cell2.setColumn(4);
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
        cell1.setRow(2);
        cell1.setColumn(3);
        positionsToBeRemoved.add(cell1);
        cell2.setRow(2);
        cell2.setColumn(4);
        positionsToBeRemoved.add(cell2);
        cell3.setRow(2);
        cell3.setColumn(5);
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

        cell.setRow(0);
        cell.setColumn(0);
        positionsToBeRemoved.add(cell);
        assertFalse(board.checkLegalChoice(positionsToBeRemoved));

        positionsToBeRemoved = new ArrayList<>();
        cell = new Cell();
        cell.setRow(5);
        cell.setColumn(0);
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
                    if(cell1.getRow() == i && cell1.getColumn() == j)
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
