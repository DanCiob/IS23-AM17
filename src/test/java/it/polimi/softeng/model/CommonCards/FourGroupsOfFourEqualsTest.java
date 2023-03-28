package it.polimi.softeng.model.CommonCards;

import it.polimi.softeng.customExceptions.IllegalInsertException;
import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static it.polimi.softeng.model.scoreCount.Score.GroupsOfEqualTiles;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Covered cases: more than four groups of four, exactly four groups of four, less than four groups of four, empty shelfie
 * (more than four tiles in a groups counts like a group of four
 */



class FourGroupsOfFourEqualsTest {
    ArrayList<Tile> tiles = new ArrayList<>();
    Shelfie shelfie = new Shelfie();

    /**
     * Receive a shelfie with more than four groups of equal tiles
     * @throws IllegalInsertException
     */
    @Test
    void verifyShapeTest1_atLeastFourGroups() throws IllegalInsertException {
        /*
            *   *   *   *   P
            W   W   B   C   B
            W   W   G   W   C
            B   G   G   W   C
            W   W   G   W   C
            W   W   B   W   C
        */

        //Each tile is tile(row)(column)
        Tile tile_0_0 = new Tile(1, Tile.TileColor.WHITE);
        Tile tile_1_0 = new Tile(2, Tile.TileColor.WHITE);
        Tile tile_2_0 = new Tile(3, Tile.TileColor.BLUE);
        Tile tile_3_0 = new Tile(22, Tile.TileColor.WHITE);
        Tile tile_4_0 = new Tile(23, Tile.TileColor.WHITE);

        Tile tile_0_1 = new Tile(4, Tile.TileColor.WHITE);
        Tile tile_1_1 = new Tile(5, Tile.TileColor.WHITE);
        Tile tile_2_1 = new Tile(6, Tile.TileColor.GREEN);
        Tile tile_3_1 = new Tile(24, Tile.TileColor.WHITE);
        Tile tile_4_1 = new Tile(25, Tile.TileColor.WHITE);

        Tile tile_0_2 = new Tile(7, Tile.TileColor.YELLOW);
        Tile tile_1_2 = new Tile(18, Tile.TileColor.GREEN);
        Tile tile_2_2 = new Tile(19, Tile.TileColor.GREEN);
        Tile tile_3_2 = new Tile(20, Tile.TileColor.GREEN);
        Tile tile_4_2 = new Tile(26, Tile.TileColor.BLUE);

        Tile tile_0_3 = new Tile(8, Tile.TileColor.WHITE);
        Tile tile_1_3 = new Tile(9, Tile.TileColor.WHITE);
        Tile tile_2_3 = new Tile(10, Tile.TileColor.WHITE);
        Tile tile_3_3 = new Tile(11, Tile.TileColor.WHITE);
        Tile tile_4_3 = new Tile (21, Tile.TileColor.CYAN);

        Tile tile_0_4 = new Tile(12, Tile.TileColor.CYAN);
        Tile tile_1_4 = new Tile(13, Tile.TileColor.CYAN);
        Tile tile_2_4 = new Tile(14, Tile.TileColor.CYAN);
        Tile tile_3_4 = new Tile(15, Tile.TileColor.CYAN);
        Tile tile_4_4 = new Tile(16, Tile.TileColor.BLUE);
        Tile tile_5_4 = new Tile(17, Tile.TileColor.PURPLE);

        tiles.add(tile_0_0);
        tiles.add(tile_1_0);
        tiles.add(tile_2_0);
        shelfie.insertTile(tiles, 0);
        tiles.remove(tile_0_0);
        tiles.remove(tile_1_0);
        tiles.remove(tile_2_0);
        tiles.add(tile_3_0);
        tiles.add(tile_4_0);
        shelfie.insertTile(tiles, 0);
        tiles.remove(tile_3_0);
        tiles.remove(tile_4_0);

        tiles.add(tile_0_1);
        tiles.add(tile_1_1);
        tiles.add(tile_2_1);
        shelfie.insertTile(tiles, 1);
        tiles.remove(tile_0_1);
        tiles.remove(tile_1_1);
        tiles.remove(tile_2_1);
        tiles.add(tile_3_1);
        tiles.add(tile_4_1);
        shelfie.insertTile(tiles, 1);
        tiles.remove(tile_3_1);
        tiles.remove(tile_4_1);

        tiles.add(tile_0_2);
        tiles.add(tile_1_2);
        tiles.add(tile_2_2);
        shelfie.insertTile(tiles, 2);
        tiles.remove(tile_0_2);
        tiles.remove(tile_1_2);
        tiles.remove(tile_2_2);
        tiles.add(tile_3_2);
        tiles.add(tile_4_2);
        shelfie.insertTile(tiles, 2);
        tiles.remove(tile_3_2);
        tiles.remove(tile_4_2);

        tiles.add(tile_0_3);
        tiles.add(tile_1_3);
        tiles.add(tile_2_3);
        shelfie.insertTile(tiles, 3);
        tiles.remove(tile_0_3);
        tiles.remove(tile_1_3);
        tiles.remove(tile_2_3);
        tiles.add(tile_3_3);
        tiles.add(tile_4_3);
        shelfie.insertTile(tiles, 3);
        tiles.remove(tile_3_3);
        tiles.remove(tile_4_3);


        tiles.add(tile_0_4);
        tiles.add(tile_1_4);
        tiles.add(tile_2_4);
        shelfie.insertTile(tiles, 4);
        tiles.remove(tile_0_4);
        tiles.remove(tile_1_4);
        tiles.remove(tile_2_4);
        tiles.add(tile_3_4);
        tiles.add(tile_4_4);
        tiles.add(tile_5_4);
        shelfie.insertTile(tiles, 4);
        tiles.remove(tile_3_4);
        tiles.remove(tile_4_4);
        tiles.remove(tile_5_4);


        System.out.println("Begin of Test1\n--------------------------------------");
        assertTrue(GroupsOfEqualTiles(shelfie, 4, 4));
        System.out.println("--------------------------------------\nEnd of Test1");
    }

    /**
     * Receive a shelfie with exactly four groups of equal tiles
     * @throws IllegalInsertException
     */
    @Test
    void verifyShapeTest2_exactlyFourGroups() throws IllegalInsertException {
    /*
    W   W   *   *   P
    W   G   *   *   B
    W   G   *   *   C
    B   G   G   *   C
    W   W   Y   W   C
    W   W   B   W   C
    This is the shelfie analyzed, exactly four groups should be found
    */

        Tile tile_0_0 = new Tile(1, Tile.TileColor.WHITE);
        Tile tile_1_0 = new Tile(2, Tile.TileColor.WHITE);
        Tile tile_2_0 = new Tile(3, Tile.TileColor.BLUE);
        Tile tile_3_0 = new Tile(22, Tile.TileColor.WHITE);
        Tile tile_4_0 = new Tile(23, Tile.TileColor.WHITE);
        Tile tile_5_0 = new Tile(24, Tile.TileColor.WHITE);

        Tile tile_0_1 = new Tile(4, Tile.TileColor.WHITE);
        Tile tile_1_1 = new Tile(5, Tile.TileColor.WHITE);
        Tile tile_2_1 = new Tile(6, Tile.TileColor.GREEN);
        Tile tile_3_1 = new Tile(24, Tile.TileColor.GREEN);
        Tile tile_4_1 = new Tile(25, Tile.TileColor.GREEN);
        Tile tile_5_1 = new Tile(26, Tile.TileColor.WHITE);

        Tile tile_0_2 = new Tile(7, Tile.TileColor.BLUE);
        Tile tile_1_2 = new Tile(18, Tile.TileColor.YELLOW);
        Tile tile_2_2 = new Tile(19, Tile.TileColor.GREEN);

        Tile tile_0_3 = new Tile(8, Tile.TileColor.WHITE);
        Tile tile_1_3 = new Tile(9, Tile.TileColor.WHITE);

        Tile tile_0_4 = new Tile(12, Tile.TileColor.CYAN);
        Tile tile_1_4 = new Tile(13, Tile.TileColor.CYAN);
        Tile tile_2_4 = new Tile(14, Tile.TileColor.CYAN);
        Tile tile_3_4 = new Tile(15, Tile.TileColor.CYAN);
        Tile tile_4_4 = new Tile(16, Tile.TileColor.BLUE);
        Tile tile_5_4 = new Tile(17, Tile.TileColor.PURPLE);

        tiles.add(tile_0_0);
        tiles.add(tile_1_0);
        tiles.add(tile_2_0);
        shelfie.insertTile(tiles, 0);
        tiles.remove(tile_0_0);
        tiles.remove(tile_1_0);
        tiles.remove(tile_2_0);
        tiles.add(tile_3_0);
        tiles.add(tile_4_0);
        tiles.add(tile_5_0);
        shelfie.insertTile(tiles, 0);
        tiles.remove(tile_3_0);
        tiles.remove(tile_4_0);
        tiles.remove(tile_5_0);

        tiles.add(tile_0_1);
        tiles.add(tile_1_1);
        tiles.add(tile_2_1);
        shelfie.insertTile(tiles, 1);
        tiles.remove(tile_0_1);
        tiles.remove(tile_1_1);
        tiles.remove(tile_2_1);
        tiles.add(tile_3_1);
        tiles.add(tile_4_1);
        tiles.add(tile_5_1);
        shelfie.insertTile(tiles, 1);
        tiles.remove(tile_3_1);
        tiles.remove(tile_4_1);
        tiles.remove(tile_5_1);

        tiles.add(tile_0_2);
        tiles.add(tile_1_2);
        tiles.add(tile_2_2);
        shelfie.insertTile(tiles, 2);
        tiles.remove(tile_0_2);
        tiles.remove(tile_1_2);
        tiles.remove(tile_2_2);

        tiles.add(tile_0_3);
        tiles.add(tile_1_3);
        shelfie.insertTile(tiles, 3);
        tiles.remove(tile_0_3);
        tiles.remove(tile_1_3);

        tiles.add(tile_0_4);
        tiles.add(tile_1_4);
        tiles.add(tile_2_4);
        shelfie.insertTile(tiles, 4);
        tiles.remove(tile_0_4);
        tiles.remove(tile_1_4);
        tiles.remove(tile_2_4);
        tiles.add(tile_3_4);
        tiles.add(tile_4_4);
        tiles.add(tile_5_4);
        shelfie.insertTile(tiles, 4);
        tiles.remove(tile_3_4);
        tiles.remove(tile_4_4);
        tiles.remove(tile_5_4);


        System.out.println("Begin of Test2\n--------------------------------------");
        assertTrue(GroupsOfEqualTiles(shelfie, 4, 4));
        System.out.println("--------------------------------------\nEnd of Test2\n");
    }

    /**
     * Receive a shelfie with less than four groups of equal tiles
     * @throws IllegalInsertException
     */
    @Test
    void verifyShapeTest3_lessThanFourGroups() throws IllegalInsertException {
    /*
    W   *   *   *   P
    W   *   *   *   B
    W   *   *   *   C
    B   *   *   C   C
    W   W   Y   W   C
    W   W   B   W   P
    This is the shelfie analyzed, less than four groups should be found
    */

        Tile tile_0_0 = new Tile(1, Tile.TileColor.WHITE);
        Tile tile_1_0 = new Tile(2, Tile.TileColor.WHITE);
        Tile tile_2_0 = new Tile(3, Tile.TileColor.BLUE);
        Tile tile_3_0 = new Tile(22, Tile.TileColor.WHITE);
        Tile tile_4_0 = new Tile(23, Tile.TileColor.WHITE);
        Tile tile_5_0 = new Tile(24, Tile.TileColor.WHITE);

        Tile tile_0_1 = new Tile(4, Tile.TileColor.WHITE);
        Tile tile_1_1 = new Tile(5, Tile.TileColor.WHITE);

        Tile tile_0_2 = new Tile(7, Tile.TileColor.BLUE);
        Tile tile_1_2 = new Tile(18, Tile.TileColor.YELLOW);

        Tile tile_0_3 = new Tile(8, Tile.TileColor.WHITE);
        Tile tile_1_3 = new Tile(9, Tile.TileColor.WHITE);
        Tile tile_2_3 = new Tile(25, Tile.TileColor.CYAN);

        Tile tile_0_4 = new Tile(12, Tile.TileColor.PURPLE);
        Tile tile_1_4 = new Tile(13, Tile.TileColor.CYAN);
        Tile tile_2_4 = new Tile(14, Tile.TileColor.CYAN);
        Tile tile_3_4 = new Tile(15, Tile.TileColor.CYAN);
        Tile tile_4_4 = new Tile(16, Tile.TileColor.BLUE);
        Tile tile_5_4 = new Tile(17, Tile.TileColor.PURPLE);

        tiles.add(tile_0_0);
        tiles.add(tile_1_0);
        tiles.add(tile_2_0);
        shelfie.insertTile(tiles, 0);
        tiles.remove(tile_0_0);
        tiles.remove(tile_1_0);
        tiles.remove(tile_2_0);
        tiles.add(tile_3_0);
        tiles.add(tile_4_0);
        tiles.add(tile_5_0);
        shelfie.insertTile(tiles, 0);
        tiles.remove(tile_3_0);
        tiles.remove(tile_4_0);
        tiles.remove(tile_5_0);

        tiles.add(tile_0_1);
        tiles.add(tile_1_1);
        shelfie.insertTile(tiles, 1);
        tiles.remove(tile_0_1);
        tiles.remove(tile_1_1);

        tiles.add(tile_0_2);
        tiles.add(tile_1_2);
        shelfie.insertTile(tiles, 2);
        tiles.remove(tile_0_2);
        tiles.remove(tile_1_2);

        tiles.add(tile_0_3);
        tiles.add(tile_1_3);
        tiles.add(tile_2_3);
        shelfie.insertTile(tiles, 3);
        tiles.remove(tile_0_3);
        tiles.remove(tile_1_3);
        tiles.remove(tile_2_3);

        tiles.add(tile_0_4);
        tiles.add(tile_1_4);
        tiles.add(tile_2_4);
        shelfie.insertTile(tiles, 4);
        tiles.remove(tile_0_4);
        tiles.remove(tile_1_4);
        tiles.remove(tile_2_4);
        tiles.add(tile_3_4);
        tiles.add(tile_4_4);
        tiles.add(tile_5_4);
        shelfie.insertTile(tiles, 4);
        tiles.remove(tile_3_4);
        tiles.remove(tile_4_4);
        tiles.remove(tile_5_4);


        System.out.println("Begin of Test3\n--------------------------------------");
        assertFalse(GroupsOfEqualTiles(shelfie, 4, 4));
        System.out.println("--------------------------------------\nEnd of Test3\n");
    }

    /**
     * Receive an empty
     * @throws IllegalInsertException
     */
    @Test
    void verifyShapeTest4_emptyShelfie() throws IllegalInsertException {
    /*
    An empty shelfie is tested, exactly four groups should be found
    */
        System.out.println("Begin of Test4\n--------------------------------------");
        assertFalse(GroupsOfEqualTiles(shelfie, 4, 4));
        System.out.println("--------------------------------------\nEnd of Test4\n");
    }

}