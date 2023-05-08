package it.polimi.softeng.controller;

import it.polimi.softeng.JSONWriter.BoardWriter;
import it.polimi.softeng.JSONWriter.ServerSignatureWriter;
import it.polimi.softeng.customExceptions.IllegalInsertException;
import it.polimi.softeng.model.Cell;
import it.polimi.softeng.model.Game;
import it.polimi.softeng.model.Tile;

import java.util.ArrayList;

/**
 * Communicate gameMoves to model, communicate via interfaces
 */
public class GameController {
    private Game game;
    private Controller controller;

    public GameController (Controller controller)
    {
        this.controller = controller;
    }

    /**
     * Call model update with information collected by client
     * @param tilesToBeRemoved is positions of cell to be removed
     * @param column is column of insertion
     * @param requester is receiver of updates
     */
    public boolean sendGameMove(ArrayList<Cell> tilesToBeRemoved, int column, String requester) {
        Tile[][] board= game.getGameBoard().getBoard();
        ArrayList<Tile> tiles = new ArrayList<>();
        for(Cell position : tilesToBeRemoved){
            tiles.add(board[position.getRow()][position.getColumn()]);
        }
        //GameBoard update
        boolean confirm = game.getGameBoard().updateBoard(tilesToBeRemoved);

        if (!confirm)
            return false;
        //Shelfie update
        try {
            game.getCurrentPlayer().getShelfie().insertTile(tiles, column);
        } catch (IllegalInsertException e) {
            throw new RuntimeException(e);
        }
        //Update board
        controller.getServerSide().sendMessageToAll(ServerSignatureWriter.serverSignObject(BoardWriter.boardChangeNotifier(game.getGameBoard()), "@BORD", "all").toJSONString());
        //Update shelfie
        //TODO make shelfies visible by everybody
        //TODO controller.getServerSide().sendMessage(ServerSignatureWriter.serverSignObject(ShelfieWriter.shelfieChangeNotifier(game.getCurrentPlayer().getShelfie()), "@SHEL", requester).toJSONString(), requester);
        return true;
    }

    /**
     * Manage setup of model
     */
    public void startGame (ArrayList<String> nameList)
    {
        game = new Game();
        game.beginGame(nameList);
        controller.getServerSide().sendMessageToAll(ServerSignatureWriter.serverSignObject(BoardWriter.boardChangeNotifier(game.getGameBoard()), "@BORD", "all").toJSONString());
        //TODO send first update of Shelfie to everybody
        //TODO send personal Cards to everyone
        //TODO send CommonCards to everyone
    }
}
