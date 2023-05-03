package it.polimi.softeng.JSONWriter;

import it.polimi.softeng.model.Board;
import it.polimi.softeng.model.Game;
import org.junit.jupiter.api.Test;

public class BoardWriterTest {
    @Test
    public void boardChangeNotifier(){
        Board board = new Board();
        Game game = new Game();
        game.initializeTile();
        board.resetBoard(3);
        board.positionTiles(game.getTileBag());
        BoardWriter boardWriter = new BoardWriter();
        boardWriter.boardChangeNotifier(board, board.getNotAvailable());
    }
}
