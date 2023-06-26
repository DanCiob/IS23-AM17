package it.polimi.softeng.model;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Check gameboard main functionalities
 */
class GameBoardTest {

    @Test
    public void resetBoardTest(){
        GameBoard gameBoard = new GameBoard();
        gameBoard.resetBoard(2);

        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                assertNull(gameBoard.getBoard()[i][j]);
            }
        }

        gameBoard.resetBoard(3);

        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                assertNull(gameBoard.getBoard()[i][j]);
            }
        }

        gameBoard.resetBoard(4);

        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                assertNull(gameBoard.getBoard()[i][j]);
            }
        }

    }

    @Test
    public void setNotAvailable2Players(){
        int count = 0;
        GameBoard gameBoard = new GameBoard();
        gameBoard.resetBoard(2);

        for(Cell cell1 : gameBoard.getNotAvailable()){
            if(cell1.getColumn() == 5 && cell1.getRow() == 8)
                count++;
            if(cell1.getRow() == 7 && cell1.getColumn() == 3)
                count++;
            if(cell1.getRow() == 5 && cell1.getColumn() == 1)
                count--;
            if(cell1.getRow() == 4 && cell1.getColumn() == 8)
                count++;
            if(cell1.getRow() == 8 && cell1.getColumn() == 5)
                count++;
        }


        assertTrue(count == 4);
    }

    @Test
    public void setNotAvailable3Players(){
        int count = 0;
        GameBoard gameBoard = new GameBoard();
        gameBoard.resetBoard(3);

        for(Cell cell1 : gameBoard.getNotAvailable()){
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
            if(cell1.getRow() == 5 && cell1.getColumn() == 0)
                fail();
        }

        assertTrue(count == 6);
    }

    @Test
    public void setNotAvailable4Players(){
        int count = 0;
        GameBoard gameBoard = new GameBoard();
        gameBoard.resetBoard(4);
        for(Cell cell1 : gameBoard.getNotAvailable()){
            if(cell1.getRow() == 5 && cell1.getColumn() == 0)
                fail();
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

        assertTrue(count == 19);
    }

    @Test
    public void setNotAvailable2PlayersB(){
        int count = 0;
        GameBoard gameBoard = new GameBoard();
        gameBoard.resetBoard(2);

        for(Cell cell1 : gameBoard.getNotAvailable()){
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

            if(cell1.getRow() == 3 && cell1.getColumn() == 4)//random cells in the centre of the gameBoard
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
        GameBoard gameBoard = new GameBoard();
        gameBoard.resetBoard(3);

        for(Cell cell1 : gameBoard.getNotAvailable()){
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

            if(cell1.getRow() == 3 && cell1.getColumn() == 5)//random cells in the centre of the gameBoard
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
        GameBoard gameBoard = new GameBoard();
        gameBoard.resetBoard(4);

        for(Cell cell1 : gameBoard.getNotAvailable()){
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

            if(cell1.getRow() == 3 && cell1.getColumn() == 2)//random cells in the centre of the gameBoard
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
        GameBoard gameBoard = new GameBoard();
        ArrayList<Cell> positionsToBeRemoved = new ArrayList<>();
        Cell cell = new Cell();
        Game game = new Game();



        //set the  tiles of the gameBoard at a value to check if it removes it correctly, using the method from the game to initialize the bag
        gameBoard.resetBoard(2);
        game.initializeTile();
        gameBoard.positionTiles(game.getTileBag());

        assertNotNull(gameBoard.getBoard()[5][1]);
        assertNotNull(gameBoard.getBoard()[4][1]);
        positionsToBeRemoved.add(cell);
        assertFalse(gameBoard.checkLegalChoice(positionsToBeRemoved));
        gameBoard.updateBoard(positionsToBeRemoved);
        assertNotNull(gameBoard.getBoard()[4][1]);
        assertNotNull(gameBoard.getBoard()[5][1]);
    }

    @Test
    public void updateBoard1Tiles(){
        GameBoard gameBoard = new GameBoard();
        ArrayList<Cell> positionsToBeRemoved = new ArrayList<>();
        //ArrayList<Cell> bag = new ArrayList<>();
        Cell cell1 = new Cell();
        Game game = new Game();



        //set the  tiles of the gameBoard at a value to check if it removes it correctly, using the method from the game to initialize the bag
        gameBoard.resetBoard(2);
        game.initializeTile();
        assertNull(gameBoard.getBoard()[1][3]);
        gameBoard.positionTiles(game.getTileBag());

        assertNotNull(gameBoard.getBoard()[1][3]);
        cell1.setRow(1);
        cell1.setColumn(3);
        positionsToBeRemoved.add(cell1);
        assertTrue(gameBoard.checkLegalChoice(positionsToBeRemoved));
        gameBoard.updateBoard(positionsToBeRemoved);
        assertNull(gameBoard.getBoard()[1][3]);
    }

    @Test
    public void updateBoard2Tiles(){
        GameBoard gameBoard = new GameBoard();
        ArrayList<Cell> positionsToBeRemoved = new ArrayList<>();
        //ArrayList<Cell> bag = new ArrayList<>();
        Cell cell1 = new Cell();
        Cell cell2 = new Cell();
        Game game = new Game();



        //set the  tiles of the gameBoard at a value to check if it removes it correctly, using the method from the game to initialize the bag
        gameBoard.resetBoard(2);
        game.initializeTile();
        assertNull(gameBoard.getBoard()[5][1]);
        assertNull(gameBoard.getBoard()[4][1]);
        gameBoard.positionTiles(game.getTileBag());

        assertNotNull(gameBoard.getBoard()[5][1]);
        assertNotNull(gameBoard.getBoard()[4][1]);
        /* cell.setX(4);
        cell.setY(0);
        positionsToBeRemoved.add(cell);*/
        cell1.setRow(5);
        cell1.setColumn(1);
        positionsToBeRemoved.add(cell1);
        cell2.setRow(4);
        cell2.setColumn(1);
        positionsToBeRemoved.add(cell2);
        assertTrue(gameBoard.checkLegalChoice(positionsToBeRemoved));
        gameBoard.updateBoard(positionsToBeRemoved);
        assertNull(gameBoard.getBoard()[4][1]);
        assertNull(gameBoard.getBoard()[5][1]);
    }

    @Test
    public void updateBoard3Tiles(){
        GameBoard gameBoard = new GameBoard();
        ArrayList<Cell> positionsToBeRemoved = new ArrayList<>();
        //ArrayList<Cell> bag = new ArrayList<>();
        Cell cell1 = new Cell();
        Cell cell2 = new Cell();
        Cell cell3 = new Cell();
        Game game = new Game();



        //set the  tiles of the gameBoard at a value to check if it removes it correctly, using the method from the game to initialize the bag
        gameBoard.resetBoard(2);
        game.initializeTile();
        assertNull(gameBoard.getBoard()[1][3]);
        assertNull(gameBoard.getBoard()[1][4]);
        assertNull(gameBoard.getBoard()[2][3]);
        assertNull(gameBoard.getBoard()[2][4]);
        assertNull(gameBoard.getBoard()[2][5]);
        gameBoard.positionTiles(game.getTileBag());

        assertNotNull(gameBoard.getBoard()[1][3]);
        assertNotNull(gameBoard.getBoard()[1][4]);
        cell1.setRow(1);
        cell1.setColumn(3);
        positionsToBeRemoved.add(cell1);
        cell2.setRow(1);
        cell2.setColumn(4);
        positionsToBeRemoved.add(cell2);
        assertTrue(gameBoard.checkLegalChoice(positionsToBeRemoved));
        gameBoard.updateBoard(positionsToBeRemoved);
        assertNull(gameBoard.getBoard()[1][3]);
        assertNull(gameBoard.getBoard()[1][4]);
        positionsToBeRemoved.clear();


        assertNotNull(gameBoard.getBoard()[2][3]);
        assertNotNull(gameBoard.getBoard()[2][4]);
        assertNotNull(gameBoard.getBoard()[2][5]);
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
        assertTrue(gameBoard.checkLegalChoice(positionsToBeRemoved));
        gameBoard.updateBoard(positionsToBeRemoved);
        assertNull(gameBoard.getBoard()[2][3]);
        assertNull(gameBoard.getBoard()[2][4]);
        assertNull(gameBoard.getBoard()[2][5]);
    }

    @Test
    public void checkLegalChoiceTest(){
        GameBoard gameBoard = new GameBoard();
        ArrayList<Cell> positionsToBeRemoved = new ArrayList<>();
        Cell cell = new Cell();
        Game game = new Game();
        gameBoard.resetBoard(4);
        game.initializeTile();
        gameBoard.positionTiles(game.getTileBag());

        cell.setRow(0);
        cell.setColumn(0);
        positionsToBeRemoved.add(cell);
        assertFalse(gameBoard.checkLegalChoice(positionsToBeRemoved));

        positionsToBeRemoved = new ArrayList<>();
        cell = new Cell();
        cell.setRow(5);
        cell.setColumn(0);
        positionsToBeRemoved.add(cell);
        assertTrue(gameBoard.checkLegalChoice(positionsToBeRemoved));
    }

    @Test
    public void checkLegalChoiceLinear(){
        GameBoard gameBoard = new GameBoard();
        ArrayList<Cell> positionsToBeRemoved = new ArrayList<>();
        Cell cell = new Cell();
        Game game = new Game();
        gameBoard.resetBoard(4);
        game.initializeTile();
        gameBoard.positionTiles(game.getTileBag());
        cell.setRow(0);
        cell.setColumn(3);
        positionsToBeRemoved.add(cell);
        cell = new Cell();
        cell.setRow(1);
        cell.setColumn(5);
        positionsToBeRemoved.add(cell);
        assertFalse(gameBoard.checkLegalChoice(positionsToBeRemoved));

    }

    @Test
    public void positionTilesTest(){
        GameBoard gameBoard = new GameBoard();
        Game game = new Game();
        ArrayList<Cell> notAvailable;
        boolean posNotUsed;
        game.initializeTile();
        gameBoard.resetBoard(4);
        notAvailable = gameBoard.getNotAvailable();
        gameBoard.positionTiles(game.getTileBag());
        for(int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                posNotUsed = false;
                for(Cell cell1 : notAvailable){
                    if(cell1.getRow() == i && cell1.getColumn() == j)
                        posNotUsed = true;
                }
                if(!posNotUsed){
                    assertNotNull(gameBoard.getBoard()[i][j]);
                }
            }
        }
    }

    @Test
    public void positionTilesAfterMovesOf2Tiles(){
        GameBoard gameBoard = new GameBoard();
        Game game = new Game();
        ArrayList<Cell> tbr = new ArrayList<>();
        game.initializeTile();
        gameBoard.resetBoard(4);
        gameBoard.positionTiles(game.getTileBag());

        Cell cell1 = new Cell();
        cell1.setRow(0);
        cell1.setColumn(3);
        tbr.add(cell1);
        Cell cell2 = new Cell();
        cell2.setRow(1);
        cell2.setColumn(3);
        tbr.add(cell2);
        gameBoard.updateBoard(tbr);
        assertNull(gameBoard.getBoard()[0][3]);
        assertNull(gameBoard.getBoard()[1][3]);
        gameBoard.positionTiles(game.getTileBag());
        assertNotNull(gameBoard.getBoard()[0][3]);
        assertNotNull(gameBoard.getBoard()[1][3]);
    }

    @Test
    public void positionTilesAfterMovesOf3Tiles(){
        GameBoard gameBoard = new GameBoard();
        Game game = new Game();
        ArrayList<Cell> tbr = new ArrayList<>();
        game.initializeTile();
        gameBoard.resetBoard(4);
        gameBoard.positionTiles(game.getTileBag());

        Cell cell1 = new Cell();
        cell1.setRow(3);
        cell1.setColumn(8);
        tbr.add(cell1);
        Cell cell2 = new Cell();
        cell2.setRow(4);
        cell2.setColumn(8);
        tbr.add(cell2);
        gameBoard.updateBoard(tbr);
        assertNull(gameBoard.getBoard()[3][8]);
        assertNull(gameBoard.getBoard()[4][8]);
        tbr.clear();

        Cell cell3 = new Cell();
        cell3.setRow(3);
        cell3.setColumn(7);
        tbr.add(cell3);
        Cell cell4 = new Cell();
        cell4.setRow(4);
        cell4.setColumn(7);
        tbr.add(cell4);
        Cell cell5 = new Cell();
        cell5.setRow(5);
        cell5.setColumn(7);
        tbr.add(cell5);
        assertTrue(gameBoard.checkLegalChoice(tbr));
        gameBoard.updateBoard(tbr);
        assertNull(gameBoard.getBoard()[3][7]);
        assertNull(gameBoard.getBoard()[4][7]);
        assertNull(gameBoard.getBoard()[5][7]);

        gameBoard.positionTiles(game.getTileBag());
        assertNotNull(gameBoard.getBoard()[3][8]);
        assertNotNull(gameBoard.getBoard()[4][8]);
        assertNotNull(gameBoard.getBoard()[3][7]);
        assertNotNull(gameBoard.getBoard()[4][7]);
        assertNotNull(gameBoard.getBoard()[5][7]);
    }


    @Test
    public void checkIslandsTest(){
        GameBoard gameBoard = new GameBoard();
        Game game = new Game();
        game.initializeTile();
        gameBoard.resetBoard(4);
        gameBoard.setBoardforTest(1, 3);
        gameBoard.setBoardforTest(0, 4);
        assertTrue(gameBoard.checkIslands());
    }

    @Test
    public void checkIslandsTestFalse(){
        GameBoard gameBoard = new GameBoard();
        Game game = new Game();
        game.initializeTile();
        gameBoard.resetBoard(4);
        gameBoard.setBoardforTest(0, 3);
        gameBoard.setBoardforTest(0, 4);
        assertNotNull(gameBoard.getBoard()[0][3]);
        assertNotNull(gameBoard.getBoard()[0][4]);
        assertFalse(gameBoard.checkIslands());
    }

    @Test
    public void checkIslandEdges(){
        GameBoard gameBoard = new GameBoard();
        Game game = new Game();
        game.initializeTile();
        gameBoard.resetBoard(3);
        gameBoard.setBoardforTest(0, 3);
        assertNotNull(gameBoard.getBoard()[0][3]);
        assertTrue(gameBoard.checkIslands());

        gameBoard.setBoardforTest(1, 4);
        assertNotNull(gameBoard.getBoard()[1][4]);
        assertTrue(gameBoard.checkIslands());

        gameBoard.setBoardforTest(2, 5);
        assertNotNull(gameBoard.getBoard()[2][5]);
        assertTrue(gameBoard.checkIslands());

        gameBoard.setBoardforTest(3, 8);
        assertNotNull(gameBoard.getBoard()[3][8]);
        assertTrue(gameBoard.checkIslands());

        gameBoard.setBoardforTest(8, 5);
        assertNotNull(gameBoard.getBoard()[8][5]);
        assertTrue(gameBoard.checkIslands());

        gameBoard.setBoardforTest(7, 5);
        assertNotNull(gameBoard.getBoard()[7][5]);
        assertFalse(gameBoard.checkIslands());
    }

    @Test
    void setBoard() {
        GameBoard gameBoard = new GameBoard();
        gameBoard.resetBoard(2);
        Tile t1 = new Tile(0, Tile.TileColor.WHITE);
        Tile t2 = new Tile(1, Tile.TileColor.BLUE);
        Tile t3 = new Tile(2, Tile.TileColor.GREEN);

        gameBoard.setBoard(4, 4, t1);
        gameBoard.setBoard(2, 3, t2);
        gameBoard.setBoard(4, 7, t3);

        Tile t4 = gameBoard.getBoard()[4][4];
        assertEquals(0, t4.getId());
        assertEquals(Tile.TileColor.WHITE, t4.getColor());

        Tile t5 = gameBoard.getBoard()[2][3];
        assertEquals(1, t5.getId());
        assertEquals(Tile.TileColor.BLUE, t5.getColor());

        Tile t6 = gameBoard.getBoard()[4][7];
        assertEquals(2, t6.getId());
        assertEquals(Tile.TileColor.GREEN, t6.getColor());

    }
}
