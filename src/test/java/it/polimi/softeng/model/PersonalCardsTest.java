package it.polimi.softeng.model;

import it.polimi.softeng.customExceptions.IllegalInsertException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static it.polimi.softeng.model.PersonalCards.FillPersonalCardsBag;
import static it.polimi.softeng.model.PersonalCards.PersonalCardToString;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Check personal card main functionalities
 */
class PersonalCardsTest {

    ArrayList<Tile> tiles = new ArrayList<>();
    Shelfie shelfie = new Shelfie();

    /**
     * Test if a successful personal objective obtains 12 points -> PersonalCard2
     */
    @Test
    void TestScore12pts () throws IllegalInsertException {
        /*
            *   *   *   *   P
            W   P   B   C   B
            G   W   Y   W   C
            B   G   G   W   W
            W   W   G   C   C
            W   W   Y   W   B
        */

        //Each tile is tile(row)(column)
        Tile tile_0_0 = new Tile(1, Tile.TileColor.WHITE);
        Tile tile_1_0 = new Tile(2, Tile.TileColor.WHITE);
        Tile tile_2_0 = new Tile(3, Tile.TileColor.BLUE);
        Tile tile_3_0 = new Tile(22, Tile.TileColor.GREEN);
        Tile tile_4_0 = new Tile(23, Tile.TileColor.WHITE);

        Tile tile_0_1 = new Tile(4, Tile.TileColor.WHITE);
        Tile tile_1_1 = new Tile(5, Tile.TileColor.WHITE);
        Tile tile_2_1 = new Tile(6, Tile.TileColor.GREEN);
        Tile tile_3_1 = new Tile(24, Tile.TileColor.WHITE);
        Tile tile_4_1 = new Tile(25, Tile.TileColor.PURPLE);

        Tile tile_0_2 = new Tile(7, Tile.TileColor.YELLOW);
        Tile tile_1_2 = new Tile(18, Tile.TileColor.GREEN);
        Tile tile_2_2 = new Tile(19, Tile.TileColor.GREEN);
        Tile tile_3_2 = new Tile(20, Tile.TileColor.YELLOW);
        Tile tile_4_2 = new Tile(26, Tile.TileColor.BLUE);

        Tile tile_0_3 = new Tile(8, Tile.TileColor.WHITE);
        Tile tile_1_3 = new Tile(9, Tile.TileColor.CYAN);
        Tile tile_2_3 = new Tile(10, Tile.TileColor.WHITE);
        Tile tile_3_3 = new Tile(11, Tile.TileColor.WHITE);
        Tile tile_4_3 = new Tile (21, Tile.TileColor.CYAN);

        Tile tile_0_4 = new Tile(12, Tile.TileColor.BLUE);
        Tile tile_1_4 = new Tile(13, Tile.TileColor.CYAN);
        Tile tile_2_4 = new Tile(14, Tile.TileColor.WHITE);
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

        ArrayList<PersonalCards> personalCardsBag;
        personalCardsBag = FillPersonalCardsBag();
        PersonalCards p = personalCardsBag.get(1);

        int score = PersonalCards.getCurrentScore(shelfie, p);

        assertEquals(12, score);
    }

    /**
     * Test if a partially successful personal objective obtains 4 points -> PersonalCard4
     */
    @Test
    void TestScore4pts () throws IllegalInsertException {
        /*
            *   *   *   *   P
            W   P   B   C   B
            C   W   B   W   C
            B   G   G   W   W
            W   Y   G   C   C
            W   W   Y   W   B
        */

        //Each tile is tile(row)(column)
        Tile tile_0_0 = new Tile(1, Tile.TileColor.WHITE);
        Tile tile_1_0 = new Tile(2, Tile.TileColor.WHITE);
        Tile tile_2_0 = new Tile(3, Tile.TileColor.BLUE);
        Tile tile_3_0 = new Tile(22, Tile.TileColor.CYAN);
        Tile tile_4_0 = new Tile(23, Tile.TileColor.WHITE);

        Tile tile_0_1 = new Tile(4, Tile.TileColor.WHITE);
        Tile tile_1_1 = new Tile(5, Tile.TileColor.YELLOW);
        Tile tile_2_1 = new Tile(6, Tile.TileColor.GREEN);
        Tile tile_3_1 = new Tile(24, Tile.TileColor.WHITE);
        Tile tile_4_1 = new Tile(25, Tile.TileColor.PURPLE);

        Tile tile_0_2 = new Tile(7, Tile.TileColor.YELLOW);
        Tile tile_1_2 = new Tile(18, Tile.TileColor.GREEN);
        Tile tile_2_2 = new Tile(19, Tile.TileColor.GREEN);
        Tile tile_3_2 = new Tile(20, Tile.TileColor.BLUE);
        Tile tile_4_2 = new Tile(26, Tile.TileColor.BLUE);

        Tile tile_0_3 = new Tile(8, Tile.TileColor.WHITE);
        Tile tile_1_3 = new Tile(9, Tile.TileColor.CYAN);
        Tile tile_2_3 = new Tile(10, Tile.TileColor.WHITE);
        Tile tile_3_3 = new Tile(11, Tile.TileColor.WHITE);
        Tile tile_4_3 = new Tile (21, Tile.TileColor.CYAN);

        Tile tile_0_4 = new Tile(12, Tile.TileColor.BLUE);
        Tile tile_1_4 = new Tile(13, Tile.TileColor.CYAN);
        Tile tile_2_4 = new Tile(14, Tile.TileColor.WHITE);
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

        ArrayList<PersonalCards> personalCardsBag;
        personalCardsBag = FillPersonalCardsBag();
        PersonalCards p = personalCardsBag.get(3);

        int score = PersonalCards.getCurrentScore(shelfie, p);

        assertEquals(4, score);
    }

    /**
     * Test if a unsuccessful personal objective obtains 4 points -> PersonalCard11
     */
    @Test
    void TestScore0pts () throws IllegalInsertException {
        /*
            *   *   *   *   P
            W   P   B   C   B
            C   W   B   W   C
            B   G   G   W   W
            W   Y   G   C   C
            W   W   Y   W   B
        */

        //Each tile is tile(row)(column)
        Tile tile_0_0 = new Tile(1, Tile.TileColor.WHITE);
        Tile tile_1_0 = new Tile(2, Tile.TileColor.WHITE);
        Tile tile_2_0 = new Tile(3, Tile.TileColor.BLUE);
        Tile tile_3_0 = new Tile(22, Tile.TileColor.CYAN);
        Tile tile_4_0 = new Tile(23, Tile.TileColor.WHITE);

        Tile tile_0_1 = new Tile(4, Tile.TileColor.WHITE);
        Tile tile_1_1 = new Tile(5, Tile.TileColor.YELLOW);
        Tile tile_2_1 = new Tile(6, Tile.TileColor.GREEN);
        Tile tile_3_1 = new Tile(24, Tile.TileColor.WHITE);
        Tile tile_4_1 = new Tile(25, Tile.TileColor.PURPLE);

        Tile tile_0_2 = new Tile(7, Tile.TileColor.YELLOW);
        Tile tile_1_2 = new Tile(18, Tile.TileColor.GREEN);
        Tile tile_2_2 = new Tile(19, Tile.TileColor.GREEN);
        Tile tile_3_2 = new Tile(20, Tile.TileColor.BLUE);
        Tile tile_4_2 = new Tile(26, Tile.TileColor.BLUE);

        Tile tile_0_3 = new Tile(8, Tile.TileColor.WHITE);
        Tile tile_1_3 = new Tile(9, Tile.TileColor.CYAN);
        Tile tile_2_3 = new Tile(10, Tile.TileColor.WHITE);
        Tile tile_3_3 = new Tile(11, Tile.TileColor.WHITE);
        Tile tile_4_3 = new Tile (21, Tile.TileColor.CYAN);

        Tile tile_0_4 = new Tile(12, Tile.TileColor.BLUE);
        Tile tile_1_4 = new Tile(13, Tile.TileColor.CYAN);
        Tile tile_2_4 = new Tile(14, Tile.TileColor.WHITE);
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

        ArrayList<PersonalCards> personalCardsBag;
        personalCardsBag = FillPersonalCardsBag();
        PersonalCards p = personalCardsBag.get(10);

        int score = PersonalCards.getCurrentScore(shelfie, p);
        PersonalCardToString(p);

        assertEquals(0, score);
    }
}