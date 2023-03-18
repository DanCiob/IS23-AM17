package it.polimi.softeng.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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

        shelfie.insertTile(tiles,0);
        /*
        controlTiles.add(shelfie.getTile(0,0));
        controlTiles.add(shelfie.getTile(1,0));
        controlTiles.add(shelfie.getTile(2,0));
         */

        assertEquals(shelfie.getTile(0,0).id,1);
        assertEquals(shelfie.getTile(1,0).id,2);
        assertEquals(shelfie.getTile(2,0).id,3);
    }
}