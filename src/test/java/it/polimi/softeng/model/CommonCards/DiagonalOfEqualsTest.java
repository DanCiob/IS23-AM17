package it.polimi.softeng.model.CommonCards;

import it.polimi.softeng.customExceptions.IllegalInsertException;
import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;
import it.polimi.softeng.model.commonCards.CommonCards;
import it.polimi.softeng.model.commonCards.DiagonalOfEquals;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DiagonalOfEqualsTest {
    /**
     * testing with a dx diagonal with offset zero waiting for a positive response
     */
    @Test
    public void equalsTrueTestDxOffsetZero(){
        CommonCards card = new DiagonalOfEquals();
        ArrayList<Tile> tiles = new ArrayList<>();
        Shelfie shelfie = new Shelfie();
        Tile tile1;
        Tile tile2;
        Tile tile3;
        //going for a dx stair of offset 0
        //filling column 0
        tile1 = new Tile(1, Tile.TileColor.WHITE);
        tiles.add(tile1);
        try {
            shelfie.insertTile(tiles,0);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }

        //filling column 1
        tiles.clear();
        tile1 = new Tile(2, Tile.TileColor.WHITE);
        tile2 = new Tile(3, Tile.TileColor.WHITE);
        tiles.add(tile1);
        tiles.add(tile2);
        try {
            shelfie.insertTile(tiles,1);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        assertFalse(card.verifyShape(shelfie));

        //filling column 2
        tiles.clear();
        tile1 = new Tile(4, Tile.TileColor.WHITE);
        tile2 = new Tile(5, Tile.TileColor.WHITE);
        tile3 = new Tile(6, Tile.TileColor.WHITE);
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles,2);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        assertFalse(card.verifyShape(shelfie));

        //filling column 3
        tiles.clear();
        tile1 = new Tile(7, Tile.TileColor.WHITE);
        tile2 = new Tile(8, Tile.TileColor.WHITE);
        tile3 = new Tile(9, Tile.TileColor.WHITE);
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles,3);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        tiles.clear();
        tile1 = new Tile(10, Tile.TileColor.WHITE);
        tiles.add(tile1);
        try {
            shelfie.insertTile(tiles,3);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        assertFalse(card.verifyShape(shelfie));

        //filling column 4
        tiles.clear();
        tile1 = new Tile(11, Tile.TileColor.WHITE);
        tile2 = new Tile(12, Tile.TileColor.WHITE);
        tile3 = new Tile(13, Tile.TileColor.WHITE);
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles,4);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        tiles.clear();
        tile1 = new Tile(14, Tile.TileColor.WHITE);
        tile2 = new Tile(15, Tile.TileColor.WHITE);
        tile3 = new Tile(16, Tile.TileColor.WHITE);
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles,4);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }

        assertTrue(card.verifyShape(shelfie));
    }

    /**
     * testing with a dx diagonal with offset one waiting for a positive response
     */
    @Test
    public void equalsTrueTestDxOffsetOne(){
        CommonCards card = new DiagonalOfEquals();
        ArrayList<Tile> tiles = new ArrayList<>();
        Shelfie shelfie = new Shelfie();
        Tile tile1;
        Tile tile2;
        Tile tile3;
        //going for a dx stair of offset 1
        //filling column 0
        tile1 = new Tile(1, Tile.TileColor.WHITE);
        tiles.add(tile1);
        tile2 = new Tile(2, Tile.TileColor.WHITE);
        tiles.add(tile2);
        try {
            shelfie.insertTile(tiles,0);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }

        //filling column 1
        tiles.clear();
        tile1 = new Tile(3, Tile.TileColor.WHITE);
        tile2 = new Tile(4, Tile.TileColor.WHITE);
        tile3 = new Tile(5,Tile.TileColor.WHITE);
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles,1);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        assertFalse(card.verifyShape(shelfie));

        //filling column 2
        tiles.clear();
        tile1 = new Tile(4, Tile.TileColor.WHITE);
        tile2 = new Tile(5, Tile.TileColor.WHITE);
        tile3 = new Tile(6, Tile.TileColor.WHITE);
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles,2);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        tiles.clear();
        tile1 = new Tile(7, Tile.TileColor.WHITE);
        tiles.add(tile1);
        try {
            shelfie.insertTile(tiles,2);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }

        assertFalse(card.verifyShape(shelfie));

        //filling column 3
        tiles.clear();
        tile1 = new Tile(8, Tile.TileColor.WHITE);
        tile2 = new Tile(9, Tile.TileColor.WHITE);
        tile3 = new Tile(10, Tile.TileColor.WHITE);
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles,3);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        tiles.clear();
        tile1 = new Tile(11, Tile.TileColor.WHITE);
        tile2 = new Tile(12, Tile.TileColor.WHITE);
        tile3 = new Tile(24, Tile.TileColor.WHITE);
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles,3);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        assertFalse(card.verifyShape(shelfie));

        //filling column 4
        tiles.clear();
        tile1 = new Tile(13, Tile.TileColor.WHITE);
        tile2 = new Tile(14, Tile.TileColor.WHITE);
        tile3 = new Tile(15, Tile.TileColor.WHITE);
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles,4);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        tiles.clear();
        tile1 = new Tile(16, Tile.TileColor.WHITE);
        tile2 = new Tile(17, Tile.TileColor.WHITE);
        tile3 = new Tile(18,Tile.TileColor.WHITE);
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles,4);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }

        assertTrue(card.verifyShape(shelfie));
    }

    /**
     * testing with a sx diagonal with offset zero waiting for a positive response
     */
    @Test
    public void equalsTrueTestSxOffsetZero(){
        CommonCards card = new DiagonalOfEquals();
        ArrayList<Tile> tiles = new ArrayList<>();
        Shelfie shelfie = new Shelfie();
        Tile tile1;
        Tile tile2;
        Tile tile3;

        //going for an sx stair of offset 0
        //filling column 0
        tile1 = new Tile(1, Tile.TileColor.WHITE);
        tile2 = new Tile(2, Tile.TileColor.WHITE);
        tile3 = new Tile(3, Tile.TileColor.WHITE);
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles,0);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        tiles.clear();
        tile1 = new Tile(4, Tile.TileColor.WHITE);
        tile2 = new Tile(5, Tile.TileColor.WHITE);
        tile2 = new Tile(23,Tile.TileColor.WHITE);
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles,0);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        assertFalse(card.verifyShape(shelfie));


        //filling column 1
        tile1 = new Tile(6, Tile.TileColor.WHITE);
        tile2 = new Tile(7, Tile.TileColor.WHITE);
        tile3 = new Tile(8, Tile.TileColor.WHITE);
        tiles.clear();
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles,1);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        tiles.clear();
        tile1 = new Tile(9, Tile.TileColor.WHITE);
        tiles.add(tile1);
        try {
            shelfie.insertTile(tiles,1);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        assertFalse(card.verifyShape(shelfie));

        //filling column 2
        tile1 = new Tile(10, Tile.TileColor.WHITE);
        tile2 = new Tile(11, Tile.TileColor.WHITE);
        tile3 = new Tile(12, Tile.TileColor.WHITE);
        tiles.clear();
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles,2);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        assertFalse(card.verifyShape(shelfie));

        //filling column 3
        tile1 = new Tile(13, Tile.TileColor.WHITE);
        tile2 = new Tile(14, Tile.TileColor.WHITE);
        tiles.clear();
        tiles.add(tile1);
        tiles.add(tile2);
        try {
            shelfie.insertTile(tiles,3);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        assertFalse(card.verifyShape(shelfie));

        //filling column 4
        tile1 = new Tile(14, Tile.TileColor.WHITE);
        tile2 = new Tile(15, Tile.TileColor.WHITE);
        tiles.clear();
        tiles.add(tile1);
        tiles.add(tile2);
        try {
            shelfie.insertTile(tiles,4);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        assertTrue(card.verifyShape(shelfie));
    }

    /**
     * testing with a sx diagonal with offset one waiting for a positive response
     */
    @Test
    public void equalsTrueTestSxOffsetOne(){
        CommonCards card = new DiagonalOfEquals();
        ArrayList<Tile> tiles = new ArrayList<>();
        Shelfie shelfie = new Shelfie();
        Tile tile1;
        Tile tile2;
        Tile tile3;

        //going for an sx stair of offset 0
        //filling column 0
        tile1 = new Tile(1, Tile.TileColor.WHITE);
        tile2 = new Tile(2, Tile.TileColor.WHITE);
        tile3 = new Tile(3, Tile.TileColor.WHITE);
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles,0);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        tiles.clear();
        tile1 = new Tile(4, Tile.TileColor.WHITE);
        tile2 = new Tile(5, Tile.TileColor.WHITE);
        tile3 = new Tile(6,Tile.TileColor.WHITE);
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles,0);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        assertFalse(card.verifyShape(shelfie));


        //filling column 1
        tile1 = new Tile(7, Tile.TileColor.WHITE);
        tile2 = new Tile(8, Tile.TileColor.WHITE);
        tile3 = new Tile(9, Tile.TileColor.WHITE);
        tiles.clear();
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles,1);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        tiles.clear();
        tile1 = new Tile(10, Tile.TileColor.WHITE);
        tile2 = new Tile(11, Tile.TileColor.WHITE);
        tiles.add(tile1);
        tiles.add(tile2);
        try {
            shelfie.insertTile(tiles,1);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        assertFalse(card.verifyShape(shelfie));

        //filling column 2
        tile1 = new Tile(12, Tile.TileColor.WHITE);
        tile2 = new Tile(13, Tile.TileColor.WHITE);
        tile3 = new Tile(14, Tile.TileColor.WHITE);
        tiles.clear();
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles,2);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        tiles.clear();
        tile1 = new Tile(15, Tile.TileColor.WHITE);
        tiles.add(tile1);
        try {
            shelfie.insertTile(tiles,2);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        assertFalse(card.verifyShape(shelfie));

        //filling column 3
        tile1 = new Tile(16, Tile.TileColor.WHITE);
        tile2 = new Tile(17, Tile.TileColor.WHITE);
        tile3 = new Tile(18, Tile.TileColor.WHITE);
        tiles.clear();
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles,3);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        assertFalse(card.verifyShape(shelfie));

        //filling column 4
        tile1 = new Tile(19, Tile.TileColor.WHITE);
        tile2 = new Tile(20, Tile.TileColor.WHITE);
        tile3 = new Tile(24, Tile.TileColor.WHITE);
        tiles.clear();
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles,4);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        assertTrue(card.verifyShape(shelfie));
    }

    /**
     * testing with a diverse color tile on a diagonal waiting for a negative response
     */
    @Test
    public void notEqualsTest(){
        CommonCards card = new DiagonalOfEquals();
        ArrayList<Tile> tiles = new ArrayList<>();
        Shelfie shelfie = new Shelfie();
        Tile tile1;
        Tile tile2;
        Tile tile3;
        //going for a dx stair of offset 0, adding a tile too many to get a stair
        //filling column 0
        tile1 = new Tile(1, Tile.TileColor.GREEN);
        tiles.add(tile1);
        try {
            shelfie.insertTile(tiles,0);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }

        //filling column 1
        tiles.clear();
        tile1 = new Tile(2, Tile.TileColor.WHITE);
        tile2 = new Tile(3, Tile.TileColor.WHITE);
        tiles.add(tile1);
        tiles.add(tile2);
        try {
            shelfie.insertTile(tiles,1);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        assertFalse(card.verifyShape(shelfie));

        //filling column 2
        tiles.clear();
        tile1 = new Tile(4, Tile.TileColor.WHITE);
        tile2 = new Tile(5, Tile.TileColor.WHITE);
        tile3 = new Tile(6, Tile.TileColor.WHITE);
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles,2);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        assertFalse(card.verifyShape(shelfie));

        //filling column 3
        tiles.clear();
        tile1 = new Tile(7, Tile.TileColor.WHITE);
        tile2 = new Tile(8, Tile.TileColor.WHITE);
        tile3 = new Tile(9, Tile.TileColor.WHITE);
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles,3);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        tiles.clear();
        tile1 = new Tile(10, Tile.TileColor.WHITE);
        tiles.add(tile1);
        try {
            shelfie.insertTile(tiles,3);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        assertFalse(card.verifyShape(shelfie));

        //filling column 4 with the required tiles for a stair plus 1
        tiles.clear();
        tile1 = new Tile(11, Tile.TileColor.WHITE);
        tile2 = new Tile(12, Tile.TileColor.WHITE);
        tile3 = new Tile(13, Tile.TileColor.WHITE);
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles,4);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        tiles.clear();
        tile1 = new Tile(14, Tile.TileColor.WHITE);
        tile2 = new Tile(15, Tile.TileColor.WHITE);
        tile3 = new Tile(16, Tile.TileColor.WHITE);
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles,4);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }

        assertFalse(card.verifyShape(shelfie));
    }
}