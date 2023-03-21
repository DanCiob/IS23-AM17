package it.polimi.softeng.model.CommonCards;

import it.polimi.softeng.customExceptions.IllegalInsertException;
import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;
import it.polimi.softeng.model.commonCards.CommonCards;
import it.polimi.softeng.model.commonCards.FourCornerOfEquals;
import it.polimi.softeng.model.commonCards.ThreeColumnsOfOneTwoThreeTypes;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The test verifies if ThreeColumnsOfOneTwoThreeTypes is correct, considering both empty columns and
 *  columns with 1, 2, 3, 4, 5, 6 different types of Tiles.
 */

class ThreeColumnsOfOneTwoThreeTypesTest {
    /**
     * The following test considers a shelfie with:
     * - empty column;
     * - column with 4 types of Tiles;
     * - column with 1 type of Tiles;    *
     * - column with 2 types of Tiles;   *
     * - column with 3 types of Tiles;   *   ->  true
     */
    @Test
    void ThreeColumnsOfOneTwoThreeTypes() {
        /*
        We analyze the following shelfie, with 3 columns with no more than 3 different types
        (columns 2, 3, 4) -> true
                                     \   Y   W   B   C
                                     \   Y   W   B   C
                                     \   Y   W   B   Y
                                     \   G   W   W   Y
                                     \   W   W   W   W
                                     \   B   W   W   W
                                             ^   ^   ^
        */
        CommonCards card = new ThreeColumnsOfOneTwoThreeTypes();
        ArrayList<Tile> tiles = new ArrayList<>();
        Shelfie shelfie = new Shelfie();
        Tile t1, t2, t3;

        assertFalse(card.verifyShape(shelfie));

        //first column empty

        //Column 1 of four different types
        t1 = new Tile(1, Tile.TileColor.BLUE);
        tiles.add(t1);
        t2 = new Tile(2, Tile.TileColor.WHITE);
        tiles.add(t2);
        t3 = new Tile(3, Tile.TileColor.GREEN);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 1);
        } catch (IllegalInsertException e) {
            System.out.println("Error");
        }
        assertFalse(card.verifyShape(shelfie));

        tiles.clear();
        t1 = new Tile(4, Tile.TileColor.YELLOW);
        tiles.add(t1);
        t2 = new Tile(5, Tile.TileColor.YELLOW);
        tiles.add(t2);
        t3 = new Tile(6, Tile.TileColor.YELLOW);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 1);
        } catch (IllegalInsertException e) {
            System.out.println("Error");
        }
        assertFalse(card.verifyShape(shelfie));


        //column 2 of one type count+1
        tiles.clear();
        t1 = new Tile(7, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(8, Tile.TileColor.WHITE);
        tiles.add(t2);
        t3 = new Tile(9, Tile.TileColor.WHITE);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 2);
        } catch (IllegalInsertException e) {
            System.out.println("Error");
        }
        assertFalse(card.verifyShape(shelfie));

        tiles.clear();
        t1 = new Tile(10, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(11, Tile.TileColor.WHITE);
        tiles.add(t2);
        t3 = new Tile(12, Tile.TileColor.WHITE);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 2);
        } catch (IllegalInsertException e) {
            System.out.println("Error");
        }
        assertFalse(card.verifyShape(shelfie));


        //column 3 of two types count+1
        tiles.clear();
        t1 = new Tile(13, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(14, Tile.TileColor.WHITE);
        tiles.add(t2);
        t3 = new Tile(15, Tile.TileColor.WHITE);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 3);
        } catch (IllegalInsertException e) {
            System.out.println("Error");
        }
        assertFalse(card.verifyShape(shelfie));

        tiles.clear();
        t1 = new Tile(16, Tile.TileColor.BLUE);
        tiles.add(t1);
        t2 = new Tile(17, Tile.TileColor.BLUE);
        tiles.add(t2);
        t3 = new Tile(18, Tile.TileColor.BLUE);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 3);
        } catch (IllegalInsertException e) {
            System.out.println("Error");
        }
        assertFalse(card.verifyShape(shelfie));


        //column 4 of three types count+1
        tiles.clear();
        t1 = new Tile(19, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(20, Tile.TileColor.WHITE);
        tiles.add(t2);
        t3 = new Tile(21, Tile.TileColor.YELLOW);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 4);
        } catch (IllegalInsertException e) {
            System.out.println("Error");
        }
        assertFalse(card.verifyShape(shelfie));

        tiles.clear();
        t1 = new Tile(22, Tile.TileColor.YELLOW);
        tiles.add(t1);
        t2 = new Tile(23, Tile.TileColor.CYAN);
        tiles.add(t2);
        t3 = new Tile(24, Tile.TileColor.CYAN);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 4);
        } catch (IllegalInsertException e) {
            System.out.println("Error");
        }
        assertTrue(card.verifyShape(shelfie));

    }

    /**
     * The following test considers a shelfie with:
     * - column with 1 type of Tiles;    *
     * - column with 2 types of Tiles;   *
     * - column with 6 types of Tiles;
     * - column with 4 types of Tiles;
     * - column with 5 types of Tiles;       ->  false
     */
    @Test
    void TwoRowsOfOneTwoThreeTypes() {
        /*
        We analyze the following shelfie, with 2 columns with no more than 3 different types
        (columns 0, 1) -> false
                                     Y   B   B   C   C
                                     Y   B   C   C   C
                                     Y   B   P   C   G
                                     Y   W   G   Y   Y
                                     Y   W   Y   B   B
                                     Y   W   W   W   W
                                     ^   ^
        */
        CommonCards card = new ThreeColumnsOfOneTwoThreeTypes();
        ArrayList<Tile> tiles = new ArrayList<>();
        Shelfie shelfie = new Shelfie();
        Tile t1, t2, t3;

        assertFalse(card.verifyShape(shelfie));

        //Column 0 of One type count+1
        t1 = new Tile(1, Tile.TileColor.YELLOW);
        tiles.add(t1);
        t2 = new Tile(2, Tile.TileColor.YELLOW);
        tiles.add(t2);
        t3 = new Tile(3, Tile.TileColor.YELLOW);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 0);
        } catch (IllegalInsertException e) {
            System.out.println("Error");
        }
        assertFalse(card.verifyShape(shelfie));

        tiles.clear();
        t1 = new Tile(4, Tile.TileColor.YELLOW);
        tiles.add(t1);
        t2 = new Tile(5, Tile.TileColor.YELLOW);
        tiles.add(t2);
        t3 = new Tile(6, Tile.TileColor.YELLOW);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 0);
        } catch (IllegalInsertException e) {
            System.out.println("Error");
        }
        assertFalse(card.verifyShape(shelfie));


        //column 1 of Two types count+1
        tiles.clear();
        t1 = new Tile(7, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(8, Tile.TileColor.WHITE);
        tiles.add(t2);
        t3 = new Tile(9, Tile.TileColor.WHITE);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 1);
        } catch (IllegalInsertException e) {
            System.out.println("Error");
        }
        assertFalse(card.verifyShape(shelfie));

        tiles.clear();
        t1 = new Tile(10, Tile.TileColor.BLUE);
        tiles.add(t1);
        t2 = new Tile(11, Tile.TileColor.BLUE);
        tiles.add(t2);
        t3 = new Tile(12, Tile.TileColor.BLUE);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 1);
        } catch (IllegalInsertException e) {
            System.out.println("Error");
        }
        assertFalse(card.verifyShape(shelfie));


        //column 2 of Six types
        tiles.clear();
        t1 = new Tile(13, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(14, Tile.TileColor.YELLOW);
        tiles.add(t2);
        t3 = new Tile(15, Tile.TileColor.GREEN);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 2);
        } catch (IllegalInsertException e) {
            System.out.println("Error");
        }
        assertFalse(card.verifyShape(shelfie));

        tiles.clear();
        t1 = new Tile(16, Tile.TileColor.PURPLE);
        tiles.add(t1);
        t2 = new Tile(17, Tile.TileColor.CYAN);
        tiles.add(t2);
        t3 = new Tile(18, Tile.TileColor.BLUE);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 2);
        } catch (IllegalInsertException e) {
            System.out.println("Error");
        }
        assertFalse(card.verifyShape(shelfie));


        //column 3 of Four types
        tiles.clear();
        t1 = new Tile(19, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(20, Tile.TileColor.BLUE);
        tiles.add(t2);
        t3 = new Tile(21, Tile.TileColor.YELLOW);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 3);
        } catch (IllegalInsertException e) {
            System.out.println("Error");
        }
        assertFalse(card.verifyShape(shelfie));

        tiles.clear();
        t1 = new Tile(22, Tile.TileColor.CYAN);
        tiles.add(t1);
        t2 = new Tile(23, Tile.TileColor.CYAN);
        tiles.add(t2);
        t3 = new Tile(24, Tile.TileColor.CYAN);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 3);
        } catch (IllegalInsertException e) {
            System.out.println("Error");
        }
        assertFalse(card.verifyShape(shelfie));

        //column 4 of Five types
        tiles.clear();
        t1 = new Tile(19, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(20, Tile.TileColor.BLUE);
        tiles.add(t2);
        t3 = new Tile(21, Tile.TileColor.YELLOW);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 4);
        } catch (IllegalInsertException e) {
            System.out.println("Error");
        }
        assertFalse(card.verifyShape(shelfie));

        tiles.clear();
        t1 = new Tile(22, Tile.TileColor.GREEN);
        tiles.add(t1);
        t2 = new Tile(23, Tile.TileColor.CYAN);
        tiles.add(t2);
        t3 = new Tile(24, Tile.TileColor.CYAN);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 4);
        } catch (IllegalInsertException e) {
            System.out.println("Error");
        }
        assertFalse(card.verifyShape(shelfie));

    }

    /**
     * The following test considers a shelfie with:
     * - column with 1 type of Tiles;    *
     * - column with 2 types of Tiles;   *
     * - column with 3 types of Tiles;
     * - column with 4 types of Tiles;
     * - column with 1 types of Tiles;   *    ->  true
     */
    @Test
    void FourRowsOfOneTwoThreeTypes() {
        /*
        We analyze the following shelfie, with 4 columns with no more than 3 different types
        (columns 0, 1, 2, 4) -> true
                                     Y   B   C   C   W
                                     Y   B   C   C   W
                                     Y   B   G   C   W
                                     Y   W   G   Y   W
                                     Y   W   W   B   W
                                     Y   W   W   W   W
                                     ^   ^   ^       ^
        */
        CommonCards card = new ThreeColumnsOfOneTwoThreeTypes();
        ArrayList<Tile> tiles = new ArrayList<>();
        Shelfie shelfie = new Shelfie();
        Tile t1, t2, t3;

        assertFalse(card.verifyShape(shelfie));

        //Column 0 of One type count+1
        t1 = new Tile(1, Tile.TileColor.YELLOW);
        tiles.add(t1);
        t2 = new Tile(2, Tile.TileColor.YELLOW);
        tiles.add(t2);
        t3 = new Tile(3, Tile.TileColor.YELLOW);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 0);
        } catch (IllegalInsertException e) {
            System.out.println("Error");
        }
        assertFalse(card.verifyShape(shelfie));

        tiles.clear();
        t1 = new Tile(4, Tile.TileColor.YELLOW);
        tiles.add(t1);
        t2 = new Tile(5, Tile.TileColor.YELLOW);
        tiles.add(t2);
        t3 = new Tile(6, Tile.TileColor.YELLOW);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 0);
        } catch (IllegalInsertException e) {
            System.out.println("Error");
        }
        assertFalse(card.verifyShape(shelfie));


        //column 1 of Two types count+1
        tiles.clear();
        t1 = new Tile(7, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(8, Tile.TileColor.WHITE);
        tiles.add(t2);
        t3 = new Tile(9, Tile.TileColor.WHITE);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 1);
        } catch (IllegalInsertException e) {
            System.out.println("Error");
        }
        assertFalse(card.verifyShape(shelfie));

        tiles.clear();
        t1 = new Tile(10, Tile.TileColor.BLUE);
        tiles.add(t1);
        t2 = new Tile(11, Tile.TileColor.BLUE);
        tiles.add(t2);
        t3 = new Tile(12, Tile.TileColor.BLUE);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 1);
        } catch (IllegalInsertException e) {
            System.out.println("Error");
        }
        assertFalse(card.verifyShape(shelfie));


        //column 2 of three types count+1
        tiles.clear();
        t1 = new Tile(13, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(14, Tile.TileColor.WHITE);
        tiles.add(t2);
        t3 = new Tile(15, Tile.TileColor.GREEN);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 2);
        } catch (IllegalInsertException e) {
            System.out.println("Error");
        }
        assertFalse(card.verifyShape(shelfie));

        tiles.clear();
        t1 = new Tile(16, Tile.TileColor.GREEN);
        tiles.add(t1);
        t2 = new Tile(17, Tile.TileColor.CYAN);
        tiles.add(t2);
        t3 = new Tile(18, Tile.TileColor.CYAN);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 2);
        } catch (IllegalInsertException e) {
            System.out.println("Error");
        }
        assertTrue(card.verifyShape(shelfie));


        //column 3 of Four types
        tiles.clear();
        t1 = new Tile(19, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(20, Tile.TileColor.BLUE);
        tiles.add(t2);
        t3 = new Tile(21, Tile.TileColor.YELLOW);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 3);
        } catch (IllegalInsertException e) {
            System.out.println("Error");
        }
        assertTrue(card.verifyShape(shelfie));

        tiles.clear();
        t1 = new Tile(22, Tile.TileColor.CYAN);
        tiles.add(t1);
        t2 = new Tile(23, Tile.TileColor.CYAN);
        tiles.add(t2);
        t3 = new Tile(24, Tile.TileColor.CYAN);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 3);
        } catch (IllegalInsertException e) {
            System.out.println("Error");
        }
        assertTrue(card.verifyShape(shelfie));

        //column 4 of one type count+1
        tiles.clear();
        t1 = new Tile(19, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(20, Tile.TileColor.WHITE);
        tiles.add(t2);
        t3 = new Tile(21, Tile.TileColor.WHITE);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 4);
        } catch (IllegalInsertException e) {
            System.out.println("Error");
        }
        assertTrue(card.verifyShape(shelfie));

        tiles.clear();
        t1 = new Tile(22, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(23, Tile.TileColor.WHITE);
        tiles.add(t2);
        t3 = new Tile(24, Tile.TileColor.WHITE);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 4);
        } catch (IllegalInsertException e) {
            System.out.println("Error");
        }
        assertTrue(card.verifyShape(shelfie));

    }
}