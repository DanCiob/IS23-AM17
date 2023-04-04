package it.polimi.softeng.model.CommonCards;

import it.polimi.softeng.customExceptions.IllegalInsertException;
import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;
import it.polimi.softeng.model.commonCards.CommonCards;
import it.polimi.softeng.model.commonCards.TwoSquareOfEquals;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TwoSquareOfEqualsTest {
    /**
     * testing with a grid of W,B,G,W,B,G rows (no squares) looking for a false response
     */
    @Test
    public void noSquaresTest(){
        ArrayList<Tile> tiles = new ArrayList<>();
        Shelfie shelfie = new Shelfie();
        CommonCards card = new TwoSquareOfEquals();
        Tile tile1;
        Tile tile2;
        Tile tile3;
        int j = 0;

        for(int i = 0;i < 30 ; i = i+3){
            tile1 = new Tile(i, Tile.TileColor.BLUE);
            tile2 = new Tile(i+1, Tile.TileColor.WHITE);
            tile3 = new Tile(i+2, Tile.TileColor.GREEN);

            if(i != 0) tiles.clear();
            tiles.add(tile1);
            tiles.add(tile2);
            tiles.add(tile3);

            try {
                shelfie.insertTile(tiles,j);
            } catch (IllegalInsertException e) {
                assertFalse(true);
            }
            if(i % 2 != 0) j++;
        }
        assertFalse(card.verifyShape(shelfie));
    }

    /**
     * testing with a square in the test grid looking for a false response
     */
    @Test
    public void oneSquareTest(){
        ArrayList<Tile> tiles = new ArrayList<>();
        Shelfie shelfie = new Shelfie();
        CommonCards card = new TwoSquareOfEquals();
        Tile tile1;
        Tile tile2;
        Tile tile3;
        int j = 0;

        for(int i = 0;i < 30 ; i = i+3){
            if(i == 0 || i == 6){
                tile1 = new Tile(i, Tile.TileColor.WHITE);
            }else{
                tile1 = new Tile(i, Tile.TileColor.BLUE);
            }
            tile2 = new Tile(i+1, Tile.TileColor.WHITE);
            tile3 = new Tile(i+2, Tile.TileColor.GREEN);

            if(i != 0) tiles.clear();
            tiles.add(tile1);
            tiles.add(tile2);
            tiles.add(tile3);

            try {
                shelfie.insertTile(tiles,j);
            } catch (IllegalInsertException e) {
                assertFalse(true);
            }
            if(i % 2 != 0) j++;
        }
        assertFalse(card.verifyShape(shelfie));
    }

    /**
     * testing with a grid containing two squares looking for a positive response
     */
    @Test
    public void twoSquaresTest(){
        ArrayList<Tile> tiles = new ArrayList<>();
        Shelfie shelfie = new Shelfie();
        CommonCards card = new TwoSquareOfEquals();
        Tile tile1;
        Tile tile2;
        Tile tile3;
        int j = 0;

        for(int i = 0;i < 30 ; i = i+3){
            if(i == 0 || i == 6 || i == 12 || i == 18){
                tile1 = new Tile(i, Tile.TileColor.WHITE);
            }else{
                tile1 = new Tile(i, Tile.TileColor.BLUE);
            }
            tile2 = new Tile(i+1, Tile.TileColor.WHITE);
            tile3 = new Tile(i+2, Tile.TileColor.GREEN);

            if(i != 0) tiles.clear();
            tiles.add(tile1);
            tiles.add(tile2);
            tiles.add(tile3);

            try {
                shelfie.insertTile(tiles,j);
            } catch (IllegalInsertException e) {
                assertFalse(true);
            }
            if(i % 2 != 0) j++;
        }
        assertTrue(card.verifyShape(shelfie));
    }

}