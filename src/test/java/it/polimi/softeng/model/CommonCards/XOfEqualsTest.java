package it.polimi.softeng.model.CommonCards;

import it.polimi.softeng.customExceptions.IllegalInsertException;
import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;
import it.polimi.softeng.model.commonCards.CommonCards;
import it.polimi.softeng.model.commonCards.NEqualTiles;
import it.polimi.softeng.model.commonCards.XOfEquals;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class XOfEqualsTest {

    @Test
    public void verifyShapeVoid(){
        CommonCards card = new NEqualTiles();
        Shelfie shelfie = new Shelfie();
        assertFalse(card.verifyShape(shelfie));
    }

    @Test
    public void verifyShapeTest1(){
        CommonCards card = new XOfEquals();
        ArrayList<Tile> tiles = new ArrayList<>();
        Shelfie shelfie = new Shelfie();
        Tile tile1, tile2, tile3;

        tile1 = new Tile(1, Tile.TileColor.BLUE);
        tiles.add(tile1);
        tile2 = new Tile(2, Tile.TileColor.WHITE);
        tiles.add(tile2);
        tile3 = new Tile(3, Tile.TileColor.BLUE);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles, 0);
        } catch (IllegalInsertException e) {
            assertFalse(true);
        }
        tiles.clear();


        tile1 = new Tile(1, Tile.TileColor.BLUE);
        tiles.add(tile1);
        tile2 = new Tile(2, Tile.TileColor.BLUE);
        tiles.add(tile2);
        tile3 = new Tile(3, Tile.TileColor.BLUE);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles, 1);
        } catch (IllegalInsertException e) {
            assertFalse(true);
        }
        tiles.clear();

        tile1 = new Tile(1, Tile.TileColor.BLUE);
        tiles.add(tile1);
        tile2 = new Tile(2, Tile.TileColor.BLUE);
        tiles.add(tile2);
        tile3 = new Tile(3, Tile.TileColor.BLUE);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles, 2);
        } catch (IllegalInsertException e) {
            assertFalse(true);
        }
        tiles.clear();

        assertTrue(card.verifyShape(shelfie));

    }

    @Test
    public void verifyShapeTest2(){
        CommonCards card = new XOfEquals();
        ArrayList<Tile> tiles = new ArrayList<>();
        Shelfie shelfie = new Shelfie();
        Tile tile1, tile2, tile3;

        tile1 = new Tile(1, Tile.TileColor.BLUE);
        tiles.add(tile1);
        tile2 = new Tile(2, Tile.TileColor.WHITE);
        tiles.add(tile2);
        tile3 = new Tile(3, Tile.TileColor.BLUE);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles, 0);
        } catch (IllegalInsertException e) {
            assertFalse(true);
        }
        tiles.clear();


        tile1 = new Tile(1, Tile.TileColor.YELLOW);
        tiles.add(tile1);
        tile2 = new Tile(2, Tile.TileColor.BLUE);
        tiles.add(tile2);
        tile3 = new Tile(3, Tile.TileColor.GREEN);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles, 1);
        } catch (IllegalInsertException e) {
            assertFalse(true);
        }
        tiles.clear();

        tile1 = new Tile(1, Tile.TileColor.BLUE);
        tiles.add(tile1);
        tile2 = new Tile(2, Tile.TileColor.CYAN);
        tiles.add(tile2);
        tile3 = new Tile(3, Tile.TileColor.BLUE);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles, 2);
        } catch (IllegalInsertException e) {
            assertFalse(true);
        }
        tiles.clear();

        assertTrue(card.verifyShape(shelfie));

    }

    @Test
    public void verifyShapeTest3(){
        CommonCards card = new XOfEquals();
        ArrayList<Tile> tiles = new ArrayList<>();
        Shelfie shelfie = new Shelfie();
        Tile tile1, tile2, tile3;

        tile1 = new Tile(1, Tile.TileColor.BLUE);
        tiles.add(tile1);
        tile2 = new Tile(2, Tile.TileColor.WHITE);
        tiles.add(tile2);
        tile3 = new Tile(3, Tile.TileColor.BLUE);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles, 0);
        } catch (IllegalInsertException e) {
            assertFalse(true);
        }
        tiles.clear();


        tile1 = new Tile(1, Tile.TileColor.BLUE);
        tiles.add(tile1);
        tile2 = new Tile(2, Tile.TileColor.BLUE);
        tiles.add(tile2);
        tile3 = new Tile(3, Tile.TileColor.BLUE);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles, 1);
        } catch (IllegalInsertException e) {
            assertFalse(true);
        }
        tiles.clear();

        tile1 = new Tile(1, Tile.TileColor.BLUE);
        tiles.add(tile1);
        tile2 = new Tile(2, Tile.TileColor.BLUE);
        tiles.add(tile2);
        tile3 = new Tile(3, Tile.TileColor.CYAN);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles, 2);
        } catch (IllegalInsertException e) {
            assertFalse(true);
        }
        tiles.clear();

        assertFalse(card.verifyShape(shelfie));

    }

    @Test
    public void verifyShapeTest4(){
        CommonCards card = new XOfEquals();
        ArrayList<Tile> tiles = new ArrayList<>();
        Shelfie shelfie = new Shelfie();
        Tile tile1, tile2, tile3;

        tile1 = new Tile(1, Tile.TileColor.GREEN);
        tiles.add(tile1);
        tile2 = new Tile(2, Tile.TileColor.GREEN);
        tiles.add(tile2);
        tile3 = new Tile(3, Tile.TileColor.GREEN);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles, 0);
        } catch (IllegalInsertException e) {
            assertFalse(true);
        }
        tiles.clear();
        assertFalse(card.verifyShape(shelfie));

        tile1 = new Tile(1, Tile.TileColor.GREEN);
        tiles.add(tile1);
        tile2 = new Tile(2, Tile.TileColor.GREEN);
        tiles.add(tile2);
        tile3 = new Tile(3, Tile.TileColor.GREEN);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles, 1);
        } catch (IllegalInsertException e) {
            assertFalse(true);
        }
        tiles.clear();
        assertFalse(card.verifyShape(shelfie));

        tile1 = new Tile(1, Tile.TileColor.GREEN);
        tiles.add(tile1);
        tile2 = new Tile(2, Tile.TileColor.GREEN);
        tiles.add(tile2);
        tile3 = new Tile(3, Tile.TileColor.GREEN);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles, 2);
        } catch (IllegalInsertException e) {
            assertFalse(true);
        }
        tiles.clear();
        assertTrue(card.verifyShape(shelfie));

        tile1 = new Tile(1, Tile.TileColor.GREEN);
        tiles.add(tile1);
        tile2 = new Tile(2, Tile.TileColor.GREEN);
        tiles.add(tile2);
        tile3 = new Tile(3, Tile.TileColor.GREEN);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles, 3);
        } catch (IllegalInsertException e) {
            assertFalse(true);
        }
        tiles.clear();
        assertTrue(card.verifyShape(shelfie));

    }
}
