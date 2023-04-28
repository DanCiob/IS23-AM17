package it.polimi.softeng.model.CommonCards;

import it.polimi.softeng.customExceptions.IllegalInsertException;
import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static it.polimi.softeng.model.scoreCount.Score.GroupsOfEqualTiles;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Covered cases: more than six groups of two, exactly six groups of two, less than six groups of two, empty shelfie
 * (more than two tiles in a groups counts like a group of two)
 */
class SixGroupsOfTwoEqualsTest {
    ArrayList<Tile> tiles = new ArrayList<>();
    Shelfie shelfie = new Shelfie();

    //Each tile is tile(row)(column)


    /**
     * Receives a shelfie with more than six group of two equals
     */
    @Test
    void verifyShapeTest1_moreThanSixGroup() throws IllegalInsertException {
    /*
        *   *   *   *   P
        *   *   *   *   P
        *   *   *   C   G
        B   G   *   C   G
        P   W   *   P   W
        P   W   P   P   W
    This is the shelfie analyzed, six group of two should be found
    */
        Tile tile_0_0 = new Tile(1, Tile.TileColor.PURPLE);
        Tile tile_1_0 = new Tile(2, Tile.TileColor.PURPLE);
        Tile tile_2_0 = new Tile(3, Tile.TileColor.BLUE);

        Tile tile_0_1 = new Tile(4, Tile.TileColor.WHITE);
        Tile tile_1_1 = new Tile(5, Tile.TileColor.WHITE);
        Tile tile_2_1 = new Tile(6, Tile.TileColor.GREEN);

        Tile tile_0_2 = new Tile(7, Tile.TileColor.PURPLE);

        Tile tile_0_3 = new Tile(8, Tile.TileColor.PURPLE);
        Tile tile_1_3 = new Tile(9, Tile.TileColor.PURPLE);
        Tile tile_2_3 = new Tile(10, Tile.TileColor.CYAN);
        Tile tile_3_3 = new Tile(11, Tile.TileColor.CYAN);

        Tile tile_0_4 = new Tile(12, Tile.TileColor.WHITE);
        Tile tile_1_4 = new Tile(13, Tile.TileColor.WHITE);
        Tile tile_2_4 = new Tile(14, Tile.TileColor.GREEN);
        Tile tile_3_4 = new Tile(15, Tile.TileColor.GREEN);
        Tile tile_4_4 = new Tile(16, Tile.TileColor.PURPLE);
        Tile tile_5_4 = new Tile(17, Tile.TileColor.PURPLE);

        tiles.add(tile_0_0);
        tiles.add(tile_1_0);
        tiles.add(tile_2_0);
        shelfie.insertTile(tiles, 0);
        tiles.remove(tile_0_0);
        tiles.remove(tile_1_0);
        tiles.remove(tile_2_0);

        tiles.add(tile_0_1);
        tiles.add(tile_1_1);
        tiles.add(tile_2_1);
        shelfie.insertTile(tiles, 1);
        tiles.remove(tile_0_1);
        tiles.remove(tile_1_1);
        tiles.remove(tile_2_1);

        tiles.add(tile_0_2);
        shelfie.insertTile(tiles, 2);
        tiles.remove(tile_0_2);

        tiles.add(tile_0_3);
        tiles.add(tile_1_3);
        tiles.add(tile_2_3);
        shelfie.insertTile(tiles, 3);
        tiles.remove(tile_0_3);
        tiles.remove(tile_1_3);
        tiles.remove(tile_2_3);
        tiles.add(tile_3_3);
        shelfie.insertTile(tiles, 3);
        tiles.remove(tile_3_3);


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

        //Check some random positions in shelfie
        assertEquals(shelfie.getTile(2, 0).getColor(), Tile.TileColor.BLUE);
        assertEquals(shelfie.getTile(0, 2).getColor(), Tile.TileColor.PURPLE);
        assertEquals(shelfie.getTile(5, 4).getColor(), Tile.TileColor.PURPLE);
        assertTrue(GroupsOfEqualTiles(shelfie, 6, 2));
    }

    /**
     * Receives a shelfie with exactly six group of two equals
     */
    @Test
    void verifyShapeTest2_exactlySixGroup() throws IllegalInsertException{
        /*
        *   *   *   *   *
        *   C   *   P   P
        *   B   *   C   G
        B   G   *   C   G
        P   G   *   Y   G
        P   W   P   Y   G
    This is the shelfie analyzed, six group of two should be found
    */
        Tile tile_0_0 = new Tile(1, Tile.TileColor.PURPLE);
        Tile tile_1_0 = new Tile(2, Tile.TileColor.PURPLE);
        Tile tile_2_0 = new Tile(3, Tile.TileColor.BLUE);

        Tile tile_0_1 = new Tile(4, Tile.TileColor.WHITE);
        Tile tile_1_1 = new Tile(5, Tile.TileColor.GREEN);
        Tile tile_2_1 = new Tile(6, Tile.TileColor.GREEN);
        Tile tile_3_1 = new Tile(25, Tile.TileColor.BLUE);
        Tile tile_4_1 = new Tile(26, Tile.TileColor.CYAN);

        Tile tile_0_2 = new Tile(7, Tile.TileColor.PURPLE);

        Tile tile_0_3 = new Tile(8, Tile.TileColor.YELLOW);
        Tile tile_1_3 = new Tile(9, Tile.TileColor.YELLOW);
        Tile tile_2_3 = new Tile(10, Tile.TileColor.CYAN);
        Tile tile_3_3 = new Tile(11, Tile.TileColor.CYAN);
        Tile tile_4_3 = new Tile(10, Tile.TileColor.PURPLE);

        Tile tile_0_4 = new Tile(12, Tile.TileColor.GREEN);
        Tile tile_1_4 = new Tile(13, Tile.TileColor.GREEN);
        Tile tile_2_4 = new Tile(14, Tile.TileColor.GREEN);
        Tile tile_3_4 = new Tile(15, Tile.TileColor.GREEN);
        Tile tile_4_4 = new Tile(16, Tile.TileColor.PURPLE);

        tiles.add(tile_0_0);
        tiles.add(tile_1_0);
        tiles.add(tile_2_0);
        shelfie.insertTile(tiles, 0);
        tiles.remove(tile_0_0);
        tiles.remove(tile_1_0);
        tiles.remove(tile_2_0);

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
        shelfie.insertTile(tiles, 2);
        tiles.remove(tile_0_2);

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
        shelfie.insertTile(tiles, 4);
        tiles.remove(tile_3_4);
        tiles.remove(tile_4_4);

        //Check some random positions in shelfie
        assertEquals(shelfie.getTile(4, 1).getColor(), Tile.TileColor.CYAN);
        assertEquals(shelfie.getTile(0, 3).getColor(), Tile.TileColor.YELLOW);
        assertEquals(shelfie.getTile(1, 3).getColor(), Tile.TileColor.YELLOW);
        assertTrue(GroupsOfEqualTiles(shelfie, 6, 2));
    }

    /**
     * Receives a shelfie with less than six group of two equals
     */
    @Test
    void verifyShapeTest3_lessThanSixGroup() throws IllegalInsertException{
        /*
        *   *   *   *   *
        *   *   *   C   C
        *   *   *   C   G
        B   *   *   C   G
        P   *   *   Y   G
        P   W   P   Y   G
    This is the shelfie analyzed, less than six group of two should be found
    */
        Tile tile_0_0 = new Tile(1, Tile.TileColor.PURPLE);
        Tile tile_1_0 = new Tile(2, Tile.TileColor.PURPLE);
        Tile tile_2_0 = new Tile(3, Tile.TileColor.BLUE);

        Tile tile_0_1 = new Tile(4, Tile.TileColor.WHITE);

        Tile tile_0_2 = new Tile(7, Tile.TileColor.PURPLE);

        Tile tile_0_3 = new Tile(8, Tile.TileColor.YELLOW);
        Tile tile_1_3 = new Tile(9, Tile.TileColor.YELLOW);
        Tile tile_2_3 = new Tile(10, Tile.TileColor.CYAN);
        Tile tile_3_3 = new Tile(11, Tile.TileColor.CYAN);
        Tile tile_4_3 = new Tile(10, Tile.TileColor.CYAN);

        Tile tile_0_4 = new Tile(12, Tile.TileColor.GREEN);
        Tile tile_1_4 = new Tile(13, Tile.TileColor.GREEN);
        Tile tile_2_4 = new Tile(14, Tile.TileColor.GREEN);
        Tile tile_3_4 = new Tile(15, Tile.TileColor.GREEN);
        Tile tile_4_4 = new Tile(16, Tile.TileColor.CYAN);

        tiles.add(tile_0_0);
        tiles.add(tile_1_0);
        tiles.add(tile_2_0);
        shelfie.insertTile(tiles, 0);
        tiles.remove(tile_0_0);
        tiles.remove(tile_1_0);
        tiles.remove(tile_2_0);

        tiles.add(tile_0_1);
        shelfie.insertTile(tiles, 1);
        tiles.remove(tile_0_1);

        tiles.add(tile_0_2);
        shelfie.insertTile(tiles, 2);
        tiles.remove(tile_0_2);

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
        shelfie.insertTile(tiles, 4);
        tiles.remove(tile_3_4);
        tiles.remove(tile_4_4);


        //Check some random positions in shelfie
        assertEquals(shelfie.getTile(0, 1).getColor(), Tile.TileColor.WHITE);
        assertEquals(shelfie.getTile(0, 2).getColor(), Tile.TileColor.PURPLE);
        assertEquals(shelfie.getTile(0, 3).getColor(), Tile.TileColor.YELLOW);
        assertFalse(GroupsOfEqualTiles(shelfie, 6, 2));
    }

    /**
     * Receives an empty shelfie
     */
    @Test
    void verifyShapeTest4_emptyShelfie() {
        /*
            Receive an empty shelfie
        */
        assertFalse(GroupsOfEqualTiles(shelfie, 6, 2));

    }
}