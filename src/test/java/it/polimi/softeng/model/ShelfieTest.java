package it.polimi.softeng.model;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.softeng.customExceptions.IllegalInsertException;
import org.junit.jupiter.api.Test;

import java.util.*;

class ShelfieTest {

    /**
     * checks for insertTile correctness by inserting 3 tiles in an empty shelfie in the 0 column; then checks if the
     * tiles are in the correct place
     */
    @Test
    public void testInsertTile(){
        ArrayList<Tile> tiles = new ArrayList<>();
        ArrayList<Tile> controlTiles = new ArrayList<>();
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
            System.out.println("illegal insert !");
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
            System.out.println("illegal insert !");
        }

        assertEquals(4,shelfie.getTile(3,0).id);
        assertEquals(5,shelfie.getTile(4,0).id);
        assertEquals(6,shelfie.getTile(5,0).id);


    }
}