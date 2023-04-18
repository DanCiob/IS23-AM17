package it.polimi.softeng.model.CommonCards;

import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;
import it.polimi.softeng.model.commonCards.CommonCards;
import it.polimi.softeng.model.commonCards.TwoColumnsOfSixDifferent;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class TwoColumnsOfSixDifferentTest {

    @Test
    public void verifyShapeVoid(){
        CommonCards card = new TwoColumnsOfSixDifferent();
        Shelfie shelfie = new Shelfie();
        assertFalse(card.verifyShape(shelfie));
    }

    @Test
    public void verifyShapeTrue() {
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
        shelfie.insertTileForTesting(tiles, 1);
        tiles.clear();

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
        shelfie.insertTileForTesting(tiles, 3);
        tiles.clear();

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
        shelfie.insertTileForTesting(tiles, 4);
        tiles.clear();
        assertTrue(card.verifyShape(shelfie));
    }

    @Test
    public void verifyShapeFalse1() {
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
        shelfie.insertTileForTesting(tiles, 3);
        tiles.clear();

        tile1 = new Tile(1, Tile.TileColor.PURPLE);
        tiles.add(tile1);
        tile2 = new Tile(2, Tile.TileColor.CYAN);
        tiles.add(tile2);
        tile3 = new Tile(3, Tile.TileColor.YELLOW);
        tiles.add(tile3);
        tile4 = new Tile(4, Tile.TileColor.PURPLE);
        tiles.add(tile4);
        tile5 = new Tile(5, Tile.TileColor.GREEN);
        tiles.add(tile5);
        tile6 = new Tile(6, Tile.TileColor.PURPLE);
        tiles.add(tile6);
        shelfie.insertTileForTesting(tiles, 4);
        tiles.clear();

        assertFalse(card.verifyShape(shelfie));
    }

    @Test
    public void verifyShapeFalse2() {
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
        shelfie.insertTileForTesting(tiles, 1);
        tiles.clear();

        tile1 = new Tile(1, Tile.TileColor.PURPLE);
        tiles.add(tile1);
        tile2 = new Tile(2, Tile.TileColor.CYAN);
        tiles.add(tile2);
        tile3 = new Tile(3, Tile.TileColor.YELLOW);
        tiles.add(tile3);
        tile4 = new Tile(4, Tile.TileColor.PURPLE);
        tiles.add(tile4);
        tile5 = new Tile(5, Tile.TileColor.GREEN);
        tiles.add(tile5);
        shelfie.insertTileForTesting(tiles, 3);
        tiles.clear();
        for(int i=5;i>=0;i--){
            for(int j=0;j<5;j++){
                if(shelfie.getGrid()[i][j] != null)
                    System.out.print(shelfie.getGrid()[i][j].getColor() + "       ");
                else
                    System.out.print(" null     ");
            }
            System.out.println();
        }
        assertFalse(card.verifyShape(shelfie));
    }
}
