package it.polimi.softeng.controller;

import it.polimi.softeng.JSONWriter.BoardWriter;
import it.polimi.softeng.JSONWriter.ServerSignatureWriter;
import it.polimi.softeng.customExceptions.IllegalInsertException;
import it.polimi.softeng.model.Cell;
import it.polimi.softeng.model.Game;
import it.polimi.softeng.model.GameBoard;
import it.polimi.softeng.model.Tile;
import it.polimi.softeng.model.interfaces.BoardInterface;

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
    public void sendGameMove(ArrayList<Cell> tilesToBeRemoved, int column, String requester) {

        //GameBoard update
        //Shelfie update
        //Update client ?
    }

    /**
     * Manage setup of model
     */
    public void startGame (ArrayList<String> nameList)
    {
        game = new Game();
        game.beginGame(nameList);
    }
}
