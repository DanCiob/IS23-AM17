package it.polimi.softeng.model.CommonCards;

import it.polimi.softeng.customExceptions.IllegalInsertException;
import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;
import it.polimi.softeng.model.commonCards.CommonCards;
import it.polimi.softeng.model.commonCards.TwoColumnsOfSixDifferent;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TwoColumnsOfSixDifferentTest {

    @Test
    public void verifyShapeTest() {
        CommonCards card = new TwoColumnsOfSixDifferent();
        ArrayList<Tile> tiles = new ArrayList<>();
        Shelfie shelfie = new Shelfie();
        Tile tile1, tile2, tile3, tile4, tile5, tile6;

        tile1 = new Tile(1, Tile.TileColor.BLUE);
        tiles.add(tile1);
        tile2 = new Tile(2, Tile.TileColor.WHITE);
        tiles.add(tile2);
        tile3 = new Tile(3, Tile.TileColor.YELLOW);
        tiles.add(tile3);
        tile4 = new Tile(1, Tile.TileColor.PURPLE);
        tiles.add(tile4);
        tile5 = new Tile(2, Tile.TileColor.CYAN);
        tiles.add(tile5);
        tile6 = new Tile(3, Tile.TileColor.GREEN);
        tiles.add(tile6);
        try {
            shelfie.insertTile(tiles, 0);
        } catch (IllegalInsertException e) {
            System.out.println("Errore 1");
        }
        tiles.clear();

        //assertFalse(card.verifyShape(shelfie));


        tile1 = new Tile(1, Tile.TileColor.BLUE);
        tiles.add(tile1);
        tile2 = new Tile(2, Tile.TileColor.WHITE);
        tiles.add(tile2);
        tile3 = new Tile(3, Tile.TileColor.YELLOW);
        tiles.add(tile3);
        tile4 = new Tile(4, Tile.TileColor.PURPLE);
        tiles.add(tile4);
        tile5 = new Tile(5, Tile.TileColor.CYAN);
        tiles.add(tile5);
        tile6 = new Tile(6, Tile.TileColor.GREEN);
        tiles.add(tile6);
        try {
            shelfie.insertTile(tiles, 1);
        } catch (IllegalInsertException e) {
            System.out.println("Errore 2");
        }
        tiles.clear();
        assertTrue(card.verifyShape(shelfie));



    }
}
