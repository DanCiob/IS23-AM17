package it.polimi.softeng.model.CommonCards;

import it.polimi.softeng.customExceptions.IllegalInsertException;
import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;
import it.polimi.softeng.model.commonCards.CommonCards;
import it.polimi.softeng.model.commonCards.EightEquals;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class EightEqualsTest {

    @Test
    public void verifyShapeTest1(){
        CommonCards card = new EightEquals();
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
            System.out.println("Errore 1");
        }
        tiles.clear();

        tile1 = new Tile(3, Tile.TileColor.BLUE);
        tiles.add(tile1);
        tile2 = new Tile(5, Tile.TileColor.WHITE);
        tiles.add(tile2);
        tile3 = new Tile(6, Tile.TileColor.BLUE);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles, 1);
        } catch (IllegalInsertException e) {
            System.out.println("Errore 2");
        }
        tiles.clear();

        tile1 = new Tile(7, Tile.TileColor.BLUE);
        tiles.add(tile1);
        tile2 = new Tile(8, Tile.TileColor.BLUE);
        tiles.add(tile2);
        tile3 = new Tile(9, Tile.TileColor.BLUE);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles, 3);
        } catch (IllegalInsertException e) {
            System.out.println("Errore 3");
        }
        tiles.clear();
        assertFalse(card.verifyShape(shelfie));

        tile1 = new Tile(7, Tile.TileColor.BLUE);
        tiles.add(tile1);
        tile2 = new Tile(8, Tile.TileColor.BLUE);
        tiles.add(tile2);
        tile3 = new Tile(9, Tile.TileColor.BLUE);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles, 4);
        } catch (IllegalInsertException e) {
            System.out.println("Errore 4");
        }

        tiles.clear();
        assertTrue(card.verifyShape(shelfie));
    }

    @Test
    public void verifyShapeTest2(){
        CommonCards card = new EightEquals();
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
            System.out.println("Errore 1");
        }
        tiles.clear();

        tile1 = new Tile(3, Tile.TileColor.BLUE);
        tiles.add(tile1);
        tile2 = new Tile(5, Tile.TileColor.WHITE);
        tiles.add(tile2);
        tile3 = new Tile(6, Tile.TileColor.BLUE);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles, 1);
        } catch (IllegalInsertException e) {
            System.out.println("Errore 2");
        }
        tiles.clear();

        tile1 = new Tile(7, Tile.TileColor.BLUE);
        tiles.add(tile1);
        tile2 = new Tile(8, Tile.TileColor.BLUE);
        tiles.add(tile2);
        tile3 = new Tile(9, Tile.TileColor.BLUE);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles, 3);
        } catch (IllegalInsertException e) {
            System.out.println("Errore 3");
        }
        tiles.clear();

        tile1 = new Tile(7, Tile.TileColor.BLUE);
        tiles.add(tile1);
        try {
            shelfie.insertTile(tiles, 4);
        } catch (IllegalInsertException e) {
            System.out.println("Errore 4");
        }

        tiles.clear();
        assertTrue(card.verifyShape(shelfie));
    }
}
