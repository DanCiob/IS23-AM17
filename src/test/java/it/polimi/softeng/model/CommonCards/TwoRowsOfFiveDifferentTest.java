package it.polimi.softeng.model.CommonCards;


import it.polimi.softeng.customExceptions.IllegalInsertException;
import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;
import it.polimi.softeng.model.commonCards.CommonCards;
import it.polimi.softeng.model.commonCards.TwoRowsOfFiveDifferent;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TwoRowsOfFiveDifferentTest {

    @Test
    public void verifyShapeTestTrue() {
        CommonCards card = new TwoRowsOfFiveDifferent();
        ArrayList<Tile> tiles = new ArrayList<>();
        Shelfie shelfie = new Shelfie();
        Tile tile1, tile2, tile3;

        tile1 = new Tile(1, Tile.TileColor.BLUE);
        tiles.add(tile1);
        tile2 = new Tile(2, Tile.TileColor.WHITE);
        tiles.add(tile2);
        tile3 = new Tile(3, Tile.TileColor.CYAN);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles, 0);
        } catch (IllegalInsertException e) {
            System.out.println("Errore 1");
        }
        tiles.clear();

        tile1 = new Tile(1, Tile.TileColor.WHITE);
        tiles.add(tile1);
        tile2 = new Tile(2, Tile.TileColor.BLUE);
        tiles.add(tile2);
        tile3 = new Tile(3, Tile.TileColor.BLUE);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles, 1);
        } catch (IllegalInsertException e) {
            System.out.println("Errore 2");
        }
        tiles.clear();

        tile1 = new Tile(1, Tile.TileColor.YELLOW);
        tiles.add(tile1);
        tile2 = new Tile(2, Tile.TileColor.PURPLE);
        tiles.add(tile2);
        tile3 = new Tile(3, Tile.TileColor.PURPLE);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles, 2);
        } catch (IllegalInsertException e) {
            System.out.println("Errore 3");
        }
        tiles.clear();

        tile1 = new Tile(1, Tile.TileColor.GREEN);
        tiles.add(tile1);
        tile2 = new Tile(2, Tile.TileColor.GREEN);
        tiles.add(tile2);
        tile3 = new Tile(3, Tile.TileColor.WHITE);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles, 3);
        } catch (IllegalInsertException e) {
            System.out.println("Errore 4");
        }
        tiles.clear();

        tile1 = new Tile(1, Tile.TileColor.PURPLE);
        tiles.add(tile1);
        tile2 = new Tile(2, Tile.TileColor.YELLOW);
        tiles.add(tile2);
        tile3 = new Tile(3, Tile.TileColor.YELLOW);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles, 4);
        } catch (IllegalInsertException e) {
            System.out.println("Errore 5");
        }
        tiles.clear();

        assertTrue(card.verifyShape(shelfie));

    }

    @Test
    public void verifyShapeTestFalse() {
        CommonCards card = new TwoRowsOfFiveDifferent();
        ArrayList<Tile> tiles = new ArrayList<>();
        Shelfie shelfie = new Shelfie();
        Tile tile1, tile2, tile3;

        tile1 = new Tile(1, Tile.TileColor.GREEN);
        tiles.add(tile1);
        tile2 = new Tile(2, Tile.TileColor.BLUE);
        tiles.add(tile2);
        tile3 = new Tile(3, Tile.TileColor.CYAN);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles, 0);
        } catch (IllegalInsertException e) {
            System.out.println("Errore 1");
        }
        tiles.clear();

        tile1 = new Tile(1, Tile.TileColor.WHITE);
        tiles.add(tile1);
        tile2 = new Tile(2, Tile.TileColor.BLUE);
        tiles.add(tile2);
        tile3 = new Tile(3, Tile.TileColor.BLUE);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles, 1);
        } catch (IllegalInsertException e) {
            System.out.println("Errore 2");
        }
        tiles.clear();

        tile1 = new Tile(1, Tile.TileColor.YELLOW);
        tiles.add(tile1);
        tile2 = new Tile(2, Tile.TileColor.PURPLE);
        tiles.add(tile2);
        tile3 = new Tile(3, Tile.TileColor.PURPLE);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles, 2);
        } catch (IllegalInsertException e) {
            System.out.println("Errore 3");
        }
        tiles.clear();

        tile1 = new Tile(1, Tile.TileColor.GREEN);
        tiles.add(tile1);
        tile2 = new Tile(2, Tile.TileColor.GREEN);
        tiles.add(tile2);
        tile3 = new Tile(3, Tile.TileColor.WHITE);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles, 3);
        } catch (IllegalInsertException e) {
            System.out.println("Errore 4");
        }
        tiles.clear();

        tile1 = new Tile(1, Tile.TileColor.GREEN);
        tiles.add(tile1);
        tile2 = new Tile(2, Tile.TileColor.YELLOW);
        tiles.add(tile2);
        tile3 = new Tile(3, Tile.TileColor.WHITE);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles, 4);
        } catch (IllegalInsertException e) {
            System.out.println("Errore 5");
        }
        tiles.clear();

        for(int i=0;i<6;i++){
            for(int j=0;j<5;j++){
                if(shelfie.getGrid()[i][j] == null)
                    System.out.print(" null ");
                else
                    System.out.print(shelfie.getGrid()[i][j].getColor());
            }
            System.out.println();
        }

        assertFalse(card.verifyShape(shelfie));

    }
}
