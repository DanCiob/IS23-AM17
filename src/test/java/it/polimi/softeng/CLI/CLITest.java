package it.polimi.softeng.CLI;

import it.polimi.softeng.client.view.*;
import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


public class CLITest {

    @Test
    public void shelfieVisualizerTest(){
        Shelfie shelfie = new Shelfie();
        ArrayList<Tile> tilesToBeInserted = new ArrayList<>();
        Tile tile1, tile2, tile3, tile4, tile5, tile6;

        tile1 = new Tile(1, Tile.TileColor.BLUE);
        tilesToBeInserted.add(tile1);
        tile2 = new Tile(2, Tile.TileColor.GREEN);
        tilesToBeInserted.add(tile2);
        tile3 = new Tile(3, Tile.TileColor.WHITE);
        tilesToBeInserted.add(tile3);
        shelfie.insertTileForTesting(tilesToBeInserted, 0);
        tilesToBeInserted.clear();

        tile1 = new Tile(4, Tile.TileColor.CYAN);
        tilesToBeInserted.add(tile1);
        tile2 = new Tile(5, Tile.TileColor.PURPLE);
        tilesToBeInserted.add(tile2);
        tile3 = new Tile(6, Tile.TileColor.BLUE);
        tilesToBeInserted.add(tile3);
        tile4 = new Tile(7, Tile.TileColor.YELLOW);
        tilesToBeInserted.add(tile4);
        shelfie.insertTileForTesting(tilesToBeInserted, 1);
        tilesToBeInserted.clear();

        tile1 = new Tile(8, Tile.TileColor.PURPLE);
        tilesToBeInserted.add(tile1);
        tile2 = new Tile(9, Tile.TileColor.CYAN);
        tilesToBeInserted.add(tile2);
        shelfie.insertTileForTesting(tilesToBeInserted, 2);
        tilesToBeInserted.clear();

        tile1 = new Tile(10, Tile.TileColor.PURPLE);
        tilesToBeInserted.add(tile1);
        tile2 = new Tile(11, Tile.TileColor.CYAN);
        tilesToBeInserted.add(tile2);
        tile3 = new Tile(12, Tile.TileColor.BLUE);
        tilesToBeInserted.add(tile3);
        tile4 = new Tile(13, Tile.TileColor.YELLOW);
        tilesToBeInserted.add(tile4);
        tile5 = new Tile(14, Tile.TileColor.GREEN);
        tilesToBeInserted.add(tile5);
        tile6 = new Tile(15, Tile.TileColor.BLUE);
        tilesToBeInserted.add(tile6);
        shelfie.insertTileForTesting(tilesToBeInserted, 4);
        tilesToBeInserted.clear();

        CLI cli = new CLI();
        cli.shelfieVisualizer(shelfie.getGrid());
    }

}
