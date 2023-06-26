package it.polimi.softeng.model;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.softeng.customExceptions.IllegalInsertException;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * Check Shelfie main functionalities
 */
class ShelfieTest {

    /**
     * Checks for insertTile correctness by inserting 3 tiles in an empty shelfie in the 0 column; then checks if the
     * tiles are in the correct place
     */
    @Test
    public void testInsertTile(){
        ArrayList<Tile> tiles = new ArrayList<>();
        Shelfie shelfie = new Shelfie();

        Tile tile1 = new Tile(1, Tile.TileColor.BLUE);
        Tile tile2 = new Tile(2, Tile.TileColor.WHITE);
        Tile tile3 = new Tile(3, Tile.TileColor.GREEN);
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);

        try {
            shelfie.insertTile(tiles,0);
        }catch(IllegalInsertException e){
            fail();
        }

        assertEquals(1,shelfie.getTile(0,0).id);
        assertEquals(2,shelfie.getTile(1,0).id);
        assertEquals(3,shelfie.getTile(2,0).id);

        tiles.clear();
        tile1 = new Tile(4, Tile.TileColor.BLUE);
        tile2 = new Tile(5, Tile.TileColor.WHITE);
        tile3 = new Tile(6, Tile.TileColor.GREEN);
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);

        try {
            shelfie.insertTile(tiles,0);
        }catch(IllegalInsertException e){
            fail();
        }

        assertEquals(4,shelfie.getTile(3,0).id);
        assertEquals(5,shelfie.getTile(4,0).id);
        assertEquals(6,shelfie.getTile(5,0).id);


    }

    /**
     * testing an illegal insertion searching for IllegalInsertException
     */
    @Test
    public void testIllegalInsertion(){
        ArrayList<Tile> tiles = new ArrayList<>();
        Shelfie shelfie = new Shelfie();
        int result = 0;

        Tile tile1 = new Tile(1, Tile.TileColor.BLUE);
        Tile tile2 = new Tile(2, Tile.TileColor.WHITE);
        Tile tile3 = new Tile(3, Tile.TileColor.GREEN);
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);

        try {
            shelfie.insertTile(tiles,0);
        }catch(IllegalInsertException e){
            fail();
        }

        tile1 = new Tile(4, Tile.TileColor.BLUE);
        tile2 = new Tile(5, Tile.TileColor.WHITE);
        tile3 = new Tile(6, Tile.TileColor.GREEN);
        tiles.clear();
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);

        try {
            shelfie.insertTile(tiles,0);
        }catch(IllegalInsertException e){
            fail();
        }

        tile1 = new Tile(7, Tile.TileColor.BLUE);
        try {
            shelfie.insertTile(tiles,0);
        }catch(IllegalInsertException e){
            result = 1;
        }
        assertEquals(1, result);
    }

    /**
     * testing with an empty array expecting IllegalInsertException
     */
    @Test
    public void noEmptyArrayTest(){
        ArrayList<Tile> tiles = new ArrayList<>();
        Shelfie shelfie = new Shelfie();
        int result = 0;

        try {
            shelfie.insertTile(tiles,0);
        }catch(IllegalInsertException e){
            result = 1;
        }
        assertEquals(1, result);
    }

    /**
     * testing with an array of 4 elements expecting IllegalInsertException
     */
    @Test
    public void noMoreThanThreeTilesTest(){
        ArrayList<Tile> tiles = new ArrayList<>();
        Shelfie shelfie = new Shelfie();
        int result = 0;

        Tile tile1 = new Tile(1, Tile.TileColor.BLUE);
        Tile tile2 = new Tile(2, Tile.TileColor.WHITE);
        Tile tile3 = new Tile(3, Tile.TileColor.GREEN);
        Tile tile4 = new Tile(4, Tile.TileColor.PURPLE);
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        tiles.add(tile4);

        try {
            shelfie.insertTile(tiles,0);
        }catch(IllegalInsertException e){
            result = 1;
        }
        assertEquals(1, result);
    }

    /**
     * testing the method checkFull by gradually filling the shelfie checking that checkFull is true only at the end
     */
    @Test
    public void checkFullTest(){
        ArrayList<Tile> tiles = new ArrayList<>();
        Shelfie shelfie = new Shelfie();
        Tile tile1;
        Tile tile2;
        Tile tile3;
        int j = 0;

        for(int i = 0;i < 30 ; i = i+3){
            tile1 = new Tile(i, Tile.TileColor.BLUE);
            tile2 = new Tile(i+1, Tile.TileColor.WHITE);
            tile3 = new Tile(i+2, Tile.TileColor.GREEN);

            if(i != 0) tiles.clear();
            tiles.add(tile1);
            tiles.add(tile2);
            tiles.add(tile3);

            try {
                shelfie.insertTile(tiles,j);
            } catch (IllegalInsertException e) {
                fail();
            }
            if(i % 2 != 0) j++;

            if(i < 27){
                assertFalse(shelfie.checkFull());
            }
        }
        assertTrue(shelfie.checkFull());
    }
}