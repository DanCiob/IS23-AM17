package it.polimi.softeng.JSONWriter;

import it.polimi.softeng.model.Board;
import it.polimi.softeng.model.Game;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import java.io.FileWriter;
import java.io.IOException;

public class BoardWriterTest {
    @Test
    public void boardChangeNotifier(){
        Board board = new Board();
        Game game = new Game();
        game.initializeTile();
        board.resetBoard(2);
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
