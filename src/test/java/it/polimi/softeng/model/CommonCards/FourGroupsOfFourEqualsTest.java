package it.polimi.softeng.model.CommonCards;

import it.polimi.softeng.customExceptions.IllegalInsertException;
import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;
import it.polimi.softeng.model.graphForScoreCount.graphNode;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static it.polimi.softeng.model.graphForScoreCount.graphNode.mapShelfieToGraph;
import static org.junit.jupiter.api.Assertions.*;

class FourGroupsOfFourEqualsTest {
    /*
    *   *   *   *   P
    *   *   *   W   W
    *   *   W   W   W
    B   G   W   W   W
    W   W   W   W   C
    W   W   W   W   G
    This is the shelfie analyzed, four groups of four W should be found
    */
    ArrayList<Tile> tiles = new ArrayList<>();
    Shelfie shelfie = new Shelfie();

    //Each tile is tile(row)(column)
    Tile tile_0_0 = new Tile(1, Tile.TileColor.WHITE);
    Tile tile_1_0 = new Tile(2, Tile.TileColor.WHITE);
    Tile tile_2_0 = new Tile(3, Tile.TileColor.BLUE);

    Tile tile_0_1 = new Tile(4, Tile.TileColor.WHITE);
    Tile tile_1_1 = new Tile(5, Tile.TileColor.WHITE);
    Tile tile_2_1 = new Tile(6, Tile.TileColor.GREEN);

    Tile tile_0_2 = new Tile(7, Tile.TileColor.WHITE);
    Tile tile_1_2 = new Tile(18, Tile.TileColor.WHITE);
    Tile tile_2_2 = new Tile(19, Tile.TileColor.WHITE);
    Tile tile_3_2 = new Tile(20, Tile.TileColor.WHITE);

    Tile tile_0_3 = new Tile(8, Tile.TileColor.WHITE);
    Tile tile_1_3 = new Tile(9, Tile.TileColor.WHITE);
    Tile tile_2_3 = new Tile(10, Tile.TileColor.WHITE);
    Tile tile_3_3 = new Tile(11, Tile.TileColor.WHITE);
    Tile tile_4_3 = new Tile (21, Tile.TileColor.WHITE);

    Tile tile_0_4 = new Tile(12, Tile.TileColor.GREEN);
    Tile tile_1_4 = new Tile(13, Tile.TileColor.CYAN);
    Tile tile_2_4 = new Tile(14, Tile.TileColor.WHITE);
    Tile tile_3_4 = new Tile(15, Tile.TileColor.WHITE);
    Tile tile_4_4 = new Tile(16, Tile.TileColor.WHITE);
    Tile tile_5_4 = new Tile(17, Tile.TileColor.PURPLE);

    @Test
    void verifyShape() throws IllegalInsertException {
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
        tiles.add(tile_1_2);
        tiles.add(tile_2_2);
        shelfie.insertTile(tiles, 2);
        tiles.remove(tile_0_2);
        tiles.remove(tile_1_2);
        tiles.remove(tile_2_2);
        tiles.add(tile_3_2);
        shelfie.insertTile(tiles, 2);
        tiles.remove(tile_3_2);

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


        /*
        Arrays.stream(shelfie.getGrid()).flatMap(Arrays::stream)
                .filter (x -> x != null)
                .map(x -> x.getColor())
                .forEach(System.out::println);

         */

        int tileCounted = 0;
        int counter = 0;
        int groupCounter = 0;
        ArrayList<graphNode> Nodes = new ArrayList<>();
        Nodes = mapShelfieToGraph(shelfie);

        for (graphNode n: Nodes)
        {
            counter = 0;
            tileCounted = 0;

            if (!n.isVisited()) {
                try
                {
                    tileCounted = n.visitGraphWithUpperBound(n, counter, 4);
                }
                catch (Exception UpperBoundReached)
                {
                    tileCounted = 4;
                }
            }

            if (tileCounted == 4)
                groupCounter++;
        }

        assertEquals(4, groupCounter);
    }
}