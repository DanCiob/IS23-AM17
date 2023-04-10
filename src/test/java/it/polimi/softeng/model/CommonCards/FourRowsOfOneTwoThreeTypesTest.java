package it.polimi.softeng.model.CommonCards;

import it.polimi.softeng.customExceptions.IllegalInsertException;
import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;
import it.polimi.softeng.model.commonCards.CommonCards;
import it.polimi.softeng.model.commonCards.FourRowsOfOneTwoThreeTypes;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The test verifies if FourRowsOfOneTwoThreeTypes is correct, considering both incompleted rows and
 *  rows with 1, 2, 3, 4, 5 different types of Tiles
 */

class FourRowsOfOneTwoThreeTypesTest {

    @Test
    void FourRowsOfOneThreeTypes(){
        /* We analyze the following shelfie, who has 4 rows with no more than 3 different types
           -> true
                                     -   -   -    -  -
                                     W   W   Y   C   C <
                                     W   Y   C   W   Y <
                                     W   Y   C   G   B
                                     W   W   W   Y   Y <
                                     W   W   W   W   W <

        */
        CommonCards card = new FourRowsOfOneTwoThreeTypes();
        ArrayList<Tile> tiles = new ArrayList<>();
        Shelfie shelfie = new Shelfie();
        Tile t1, t2, t3;

        assertFalse(card.verifyShape(shelfie));

        //COLUMN 0
        t1 = new Tile(1, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(2, Tile.TileColor.WHITE);
        tiles.add(t2);
        t3 = new Tile(3, Tile.TileColor.WHITE);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 0);
        } catch (IllegalInsertException e) {
            fail();
        }
        assertFalse(card.verifyShape(shelfie));

        tiles.clear();
        t1 = new Tile(4, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(5, Tile.TileColor.WHITE);
        tiles.add(t2);
        try {
            shelfie.insertTile(tiles, 0);
        } catch (IllegalInsertException e) {
            fail();
        }
        assertFalse(card.verifyShape(shelfie));

        //COLUMN 1
        tiles.clear();
        t1 = new Tile(6, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(7, Tile.TileColor.WHITE);
        tiles.add(t2);
        t3 = new Tile(8, Tile.TileColor.YELLOW);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 1);
        } catch (IllegalInsertException e) {
            fail();
        }
        assertFalse(card.verifyShape(shelfie));

        tiles.clear();
        t1 = new Tile(9, Tile.TileColor.YELLOW);
        tiles.add(t1);
        t2 = new Tile(10, Tile.TileColor.WHITE);
        tiles.add(t2);
        try {
            shelfie.insertTile(tiles, 1);
        } catch (IllegalInsertException e) {
            fail();
        }
        assertFalse(card.verifyShape(shelfie));

        //COLUMN 2
        tiles.clear();
        t1 = new Tile(11, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(12, Tile.TileColor.WHITE);
        tiles.add(t2);
        t3 = new Tile(13, Tile.TileColor.CYAN);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 2);
        } catch (IllegalInsertException e) {
            fail();
        }
        assertFalse(card.verifyShape(shelfie));

        tiles.clear();
        t1 = new Tile(14, Tile.TileColor.CYAN);
        tiles.add(t1);
        t2 = new Tile(15, Tile.TileColor.YELLOW);
        tiles.add(t2);
        try {
            shelfie.insertTile(tiles, 2);
        } catch (IllegalInsertException e) {
            fail();
        }
        assertFalse(card.verifyShape(shelfie));

        //COLUMN 3
        tiles.clear();
        t1 = new Tile(16, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(17, Tile.TileColor.YELLOW);
        tiles.add(t2);
        t3 = new Tile(18, Tile.TileColor.GREEN);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 3);
        } catch (IllegalInsertException e) {
            fail();
        }
        assertFalse(card.verifyShape(shelfie));

        tiles.clear();
        t1 = new Tile(19, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(20, Tile.TileColor.CYAN);
        tiles.add(t2);
        try {
            shelfie.insertTile(tiles, 3);
        } catch (IllegalInsertException e) {
            fail();
        }
        assertFalse(card.verifyShape(shelfie));

        //COLUMN 4
        tiles.clear();
        t1 = new Tile(21, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(22, Tile.TileColor.YELLOW);
        tiles.add(t2);
        t3 = new Tile(23, Tile.TileColor.BLUE);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 4);
        } catch (IllegalInsertException e) {
            fail();
        }
        assertFalse(card.verifyShape(shelfie));

        tiles.clear();
        t1 = new Tile(24, Tile.TileColor.YELLOW);
        tiles.add(t1);
        t2 = new Tile(25, Tile.TileColor.CYAN);
        tiles.add(t2);
        try {
            shelfie.insertTile(tiles, 4);
        } catch (IllegalInsertException e) {
            fail();
        }
        assertTrue(card.verifyShape(shelfie));

    }

    @Test
    void FiveRowsOfOneThreeTypes(){
        /* We analyze the following shelfie, who has 5 rows with no more than 3 different types
           -> true
                                     -   -   -   -   -
                                     W   W   Y   C   C <
                                     W   Y   C   W   Y <
                                     W   Y   W   G   Y <
                                     W   W   W   Y   Y <
                                     W   W   W   W   W <
        */
        CommonCards card = new FourRowsOfOneTwoThreeTypes();
        ArrayList<Tile> tiles = new ArrayList<>();
        Shelfie shelfie = new Shelfie();
        Tile t1, t2, t3;

        assertFalse(card.verifyShape(shelfie));

        //COLUMN 0
        t1 = new Tile(1, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(2, Tile.TileColor.WHITE);
        tiles.add(t2);
        t3 = new Tile(3, Tile.TileColor.WHITE);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 0);
        } catch (IllegalInsertException e) {
            fail();
        }
        assertFalse(card.verifyShape(shelfie));

        tiles.clear();
        t1 = new Tile(4, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(5, Tile.TileColor.WHITE);
        tiles.add(t2);
        try {
            shelfie.insertTile(tiles, 0);
        } catch (IllegalInsertException e) {
            fail();
        }
        assertFalse(card.verifyShape(shelfie));

        //COLUMN 1
        tiles.clear();
        t1 = new Tile(6, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(7, Tile.TileColor.WHITE);
        tiles.add(t2);
        t3 = new Tile(8, Tile.TileColor.YELLOW);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 1);
        } catch (IllegalInsertException e) {
            fail();
        }
        assertFalse(card.verifyShape(shelfie));

        tiles.clear();
        t1 = new Tile(9, Tile.TileColor.YELLOW);
        tiles.add(t1);
        t2 = new Tile(10, Tile.TileColor.WHITE);
        tiles.add(t2);
        try {
            shelfie.insertTile(tiles, 1);
        } catch (IllegalInsertException e) {
            fail();
        }
        assertFalse(card.verifyShape(shelfie));

        //COLUMN 2
        tiles.clear();
        t1 = new Tile(11, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(12, Tile.TileColor.WHITE);
        tiles.add(t2);
        t3 = new Tile(13, Tile.TileColor.WHITE);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 2);
        } catch (IllegalInsertException e) {
            fail();
        }
        assertFalse(card.verifyShape(shelfie));

        tiles.clear();
        t1 = new Tile(14, Tile.TileColor.CYAN);
        tiles.add(t1);
        t2 = new Tile(15, Tile.TileColor.YELLOW);
        tiles.add(t2);
        try {
            shelfie.insertTile(tiles, 2);
        } catch (IllegalInsertException e) {
            fail();
        }
        assertFalse(card.verifyShape(shelfie));

        //COLUMN 3
        tiles.clear();
        t1 = new Tile(16, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(17, Tile.TileColor.YELLOW);
        tiles.add(t2);
        t3 = new Tile(18, Tile.TileColor.GREEN);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 3);
        } catch (IllegalInsertException e) {
            fail();
        }
        assertFalse(card.verifyShape(shelfie));

        tiles.clear();
        t1 = new Tile(19, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(20, Tile.TileColor.CYAN);
        tiles.add(t2);
        try {
            shelfie.insertTile(tiles, 3);
        } catch (IllegalInsertException e) {
            fail();
        }
        assertFalse(card.verifyShape(shelfie));

        //COLUMN 4
        tiles.clear();
        t1 = new Tile(21, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(22, Tile.TileColor.YELLOW);
        tiles.add(t2);
        t3 = new Tile(23, Tile.TileColor.YELLOW);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 4);
        } catch (IllegalInsertException e) {
            fail();
        }
        assertFalse(card.verifyShape(shelfie));

        tiles.clear();
        t1 = new Tile(24, Tile.TileColor.YELLOW);
        tiles.add(t1);
        t2 = new Tile(25, Tile.TileColor.CYAN);
        tiles.add(t2);
        try {
            shelfie.insertTile(tiles, 4);
        } catch (IllegalInsertException e) {
            fail();
        }
        assertTrue(card.verifyShape(shelfie));

    }

    @Test
    void ThreeRowsOfOneThreeTypes(){
        /* We analyze the following shelfie, who has only 3 rows with no more than 3 different types
           -> false
                                     -   -   -   -   -
                                     -   -   -   -   -
                                     W   Y   C   W   P
                                     W   Y   W   G   Y <
                                     W   W   W   Y   Y <
                                     W   W   W   W   W <
        */
        CommonCards card = new FourRowsOfOneTwoThreeTypes();
        ArrayList<Tile> tiles = new ArrayList<>();
        Shelfie shelfie = new Shelfie();
        Tile t1, t2, t3;

        assertFalse(card.verifyShape(shelfie));

        //COLUMN 0
        t1 = new Tile(1, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(2, Tile.TileColor.WHITE);
        tiles.add(t2);
        t3 = new Tile(3, Tile.TileColor.WHITE);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 0);
        } catch (IllegalInsertException e) {
            fail();
        }
        assertFalse(card.verifyShape(shelfie));

        tiles.clear();
        t1 = new Tile(4, Tile.TileColor.WHITE);
        tiles.add(t1);
        try {
            shelfie.insertTile(tiles, 0);
        } catch (IllegalInsertException e) {
            fail();
        }
        assertFalse(card.verifyShape(shelfie));

        //COLUMN 1
        tiles.clear();
        t1 = new Tile(6, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(7, Tile.TileColor.WHITE);
        tiles.add(t2);
        t3 = new Tile(8, Tile.TileColor.YELLOW);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 1);
        } catch (IllegalInsertException e) {
            fail();
        }
        assertFalse(card.verifyShape(shelfie));

        tiles.clear();
        t1 = new Tile(9, Tile.TileColor.YELLOW);
        tiles.add(t1);
        try {
            shelfie.insertTile(tiles, 1);
        } catch (IllegalInsertException e) {
            fail();
        }
        assertFalse(card.verifyShape(shelfie));

        //COLUMN 2
        tiles.clear();
        t1 = new Tile(11, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(12, Tile.TileColor.WHITE);
        tiles.add(t2);
        t3 = new Tile(13, Tile.TileColor.WHITE);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 2);
        } catch (IllegalInsertException e) {
            fail();
        }
        assertFalse(card.verifyShape(shelfie));

        tiles.clear();
        t1 = new Tile(14, Tile.TileColor.CYAN);
        tiles.add(t1);
        try {
            shelfie.insertTile(tiles, 2);
        } catch (IllegalInsertException e) {
            fail();
        }
        assertFalse(card.verifyShape(shelfie));

        //COLUMN 3
        tiles.clear();
        t1 = new Tile(16, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(17, Tile.TileColor.YELLOW);
        tiles.add(t2);
        t3 = new Tile(18, Tile.TileColor.GREEN);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 3);
        } catch (IllegalInsertException e) {
            fail();
        }
        assertFalse(card.verifyShape(shelfie));

        tiles.clear();
        t1 = new Tile(19, Tile.TileColor.WHITE);
        tiles.add(t1);
        try {
            shelfie.insertTile(tiles, 3);
        } catch (IllegalInsertException e) {
            fail();
        }
        assertFalse(card.verifyShape(shelfie));

        //COLUMN 4
        tiles.clear();
        t1 = new Tile(21, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(22, Tile.TileColor.YELLOW);
        tiles.add(t2);
        t3 = new Tile(23, Tile.TileColor.YELLOW);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 4);
        } catch (IllegalInsertException e) {
            fail();
        }
        assertFalse(card.verifyShape(shelfie));

        tiles.clear();
        t1 = new Tile(24, Tile.TileColor.PURPLE);
        tiles.add(t1);
        try {
            shelfie.insertTile(tiles, 4);
        } catch (IllegalInsertException e) {
            fail();
        }
        assertFalse(card.verifyShape(shelfie));

    }

    @Test
    void ZeroRowsOfOneThreeTypes(){
        /* We analyze the following shelfie, who has only 3 rows with no more than 3 different types
           -> false
                                     -   -   -   -   -
                                     -   -   -   -   -
                                     W   W   W   -   -
                                     W   W   W   -   -
                                     W   W   W   -   -
                                     W   W   W   -   -
        */
        CommonCards card = new FourRowsOfOneTwoThreeTypes();
        ArrayList<Tile> tiles = new ArrayList<>();
        Shelfie shelfie = new Shelfie();
        Tile t1, t2, t3;

        assertFalse(card.verifyShape(shelfie));

        //COLUMN 0
        t1 = new Tile(1, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(2, Tile.TileColor.WHITE);
        tiles.add(t2);
        t3 = new Tile(3, Tile.TileColor.WHITE);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 0);
        } catch (IllegalInsertException e) {
            fail();
        }
        assertFalse(card.verifyShape(shelfie));

        tiles.clear();
        t1 = new Tile(4, Tile.TileColor.WHITE);
        tiles.add(t1);
        try {
            shelfie.insertTile(tiles, 0);
        } catch (IllegalInsertException e) {
            fail();
        }
        assertFalse(card.verifyShape(shelfie));

        //COLUMN 1
        tiles.clear();
        t1 = new Tile(6, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(7, Tile.TileColor.WHITE);
        tiles.add(t2);
        t3 = new Tile(8, Tile.TileColor.WHITE);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 1);
        } catch (IllegalInsertException e) {
            fail();
        }
        assertFalse(card.verifyShape(shelfie));

        tiles.clear();
        t1 = new Tile(9, Tile.TileColor.WHITE);
        tiles.add(t1);
        try {
            shelfie.insertTile(tiles, 1);
        } catch (IllegalInsertException e) {
            fail();
        }
        assertFalse(card.verifyShape(shelfie));

        //COLUMN 2
        tiles.clear();
        t1 = new Tile(11, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(12, Tile.TileColor.WHITE);
        tiles.add(t2);
        t3 = new Tile(13, Tile.TileColor.WHITE);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 2);
        } catch (IllegalInsertException e) {
            fail();
        }
        assertFalse(card.verifyShape(shelfie));

        tiles.clear();
        t1 = new Tile(14, Tile.TileColor.WHITE);
        tiles.add(t1);
        try {
            shelfie.insertTile(tiles, 2);
        } catch (IllegalInsertException e) {
            fail();
        }
        assertFalse(card.verifyShape(shelfie));

    }

}