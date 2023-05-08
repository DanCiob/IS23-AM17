package it.polimi.softeng.JSONWriter;

import it.polimi.softeng.model.GameBoard;
import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ShelfieWriterTest {

    @Test
    void shelfieChangeNotifier() {
        Shelfie shelfie = new Shelfie();
        shelfie.setGrid(0, 0, 0, Tile.TileColor.WHITE);
        shelfie.setGrid(1, 0, 1, Tile.TileColor.BLUE);
        shelfie.setGrid(2, 0, 2, Tile.TileColor.YELLOW);
        shelfie.setGrid(0, 1, 3, Tile.TileColor.PURPLE);
        shelfie.setGrid(1, 1, 4, Tile.TileColor.CYAN);
        shelfie.setGrid(0, 2, 5, Tile.TileColor.GREEN);
        shelfie.setGrid(0, 4, 6, Tile.TileColor.WHITE);
        shelfie.setGrid(1, 4, 7, Tile.TileColor.BLUE);
        shelfie.setGrid(2, 4, 8, Tile.TileColor.YELLOW);
        shelfie.setGrid(3, 4, 9, Tile.TileColor.PURPLE);
        shelfie.setGrid(4, 4, 10, Tile.TileColor.CYAN);
        shelfie.setGrid(5, 4, 11, Tile.TileColor.GREEN);

        ShelfieWriter shelfieWriter = new ShelfieWriter();
        JSONObject jsonObject = shelfieWriter.shelfieChangeNotifier(shelfie);
        try (FileWriter file = new FileWriter("src/main/java/it/polimi/softeng/JSONMessages/Test/ShelfieWriterMessage.json")) {
            file.write(jsonObject.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}