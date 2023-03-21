package it.polimi.softeng.model.CommonCards;

import it.polimi.softeng.customExceptions.IllegalInsertException;
import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;
import it.polimi.softeng.model.commonCards.CommonCards;
import it.polimi.softeng.model.commonCards.FourCornerOfEquals;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The test verifies FourCornerOfEquals, considering empty and different types corners
 */

class FourCornerOfEqualsTest {
    /**
     * The test verifies if FourCornerOfEquals is correct, progressively fulfilling the shelfie till
     *  the four corners are equals -> true
     */
    @Test
    void FourCornerOfEqualsTestTrue() {
        CommonCards card = new FourCornerOfEquals();
        ArrayList<Tile> tiles = new ArrayList<>();
        Shelfie shelfie = new Shelfie();
        Tile t1, t2, t3;

        assertFalse(card.verifyShape(shelfie));

        t1 = new Tile(1, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(2, Tile.TileColor.WHITE);
        tiles.add(t2);
        t3 = new Tile(3, Tile.TileColor.WHITE);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 0);
        } catch (IllegalInsertException e) {
            System.out.println("Error");
        }
        assertFalse(card.verifyShape(shelfie));

        tiles.clear();
        t1 = new Tile(4, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(5, Tile.TileColor.WHITE);
        tiles.add(t2);
        t3 = new Tile(6, Tile.TileColor.WHITE);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 0);
        } catch (IllegalInsertException e) {
            System.out.println("Error");
        }
        assertFalse(card.verifyShape(shelfie));

        tiles.clear();
        t1 = new Tile(7, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(8, Tile.TileColor.WHITE);
        tiles.add(t2);
        t3 = new Tile(9, Tile.TileColor.WHITE);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 4);
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
            shelfie.insertTile(tiles, 4);
        } catch (IllegalInsertException e) {
            System.out.println("Error");
        }
        assertTrue(card.verifyShape(shelfie));
    }

    /**
     * The test verifies if FourCornerOfEquals is correct, progressively fulfilling the shelfie,
     *  but with two different types of Tiles -> false
     */
    @Test
    void FourCornerOfEqualsTestFalse() {
        CommonCards card = new FourCornerOfEquals();
        ArrayList<Tile> tiles = new ArrayList<>();
        Shelfie shelfie = new Shelfie();
        Tile t1, t2, t3;

        assertFalse(card.verifyShape(shelfie));

        t1 = new Tile(1, Tile.TileColor.CYAN);
        tiles.add(t1);
        t2 = new Tile(2, Tile.TileColor.WHITE);
        tiles.add(t2);
        t3 = new Tile(3, Tile.TileColor.WHITE);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 0);
        } catch (IllegalInsertException e) {
            System.out.println("Error");
        }
        assertFalse(card.verifyShape(shelfie));

        tiles.clear();
        t1 = new Tile(4, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(5, Tile.TileColor.WHITE);
        tiles.add(t2);
        t3 = new Tile(6, Tile.TileColor.WHITE);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 0);
        } catch (IllegalInsertException e) {
            System.out.println("Error");
        }
        assertFalse(card.verifyShape(shelfie));

        tiles.clear();
        t1 = new Tile(7, Tile.TileColor.WHITE);
        tiles.add(t1);
        t2 = new Tile(8, Tile.TileColor.WHITE);
        tiles.add(t2);
        t3 = new Tile(9, Tile.TileColor.WHITE);
        tiles.add(t3);
        try {
            shelfie.insertTile(tiles, 4);
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
            shelfie.insertTile(tiles, 4);
        } catch (IllegalInsertException e) {
            System.out.println("Error");
        }
        assertFalse(card.verifyShape(shelfie));
    }
}