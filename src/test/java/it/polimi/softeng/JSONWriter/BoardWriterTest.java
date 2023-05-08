package it.polimi.softeng.JSONWriter;

import it.polimi.softeng.model.GameBoard;
import it.polimi.softeng.model.Game;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import java.io.FileWriter;
import java.io.IOException;

public class BoardWriterTest {
    @Test
    public void boardChangeNotifier(){
        GameBoard board = new GameBoard();
        Game game = new Game();
        game.initializeTile(); //initialize and insert tiles in tilesBag
        board.resetBoard(2); //set notAvailable positions and null all the others
        board.positionTiles(game.getTileBag());
        BoardWriter boardWriter = new BoardWriter();
        JSONObject jsonObject = boardWriter.boardChangeNotifier(board);
        try (FileWriter file = new FileWriter("src/main/java/it/polimi/softeng/JSONMessages/Test/BoardWriterMessage.json")) {
            file.write(jsonObject.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
