package it.polimi.softeng.controller;

import it.polimi.softeng.connectionProtocol.ServerSide;
import it.polimi.softeng.model.Cell;
import it.polimi.softeng.model.Game;
import it.polimi.softeng.model.Tile;

import javax.naming.InvalidNameException;
import java.util.ArrayList;

/**
 * General controller managing game, gameController, boardController, shelfieController, chatController and communicating using serverSide
 */
public class Controller {
    private final Game currentGame;
    private final GameController gameController;
    private final LoginController loginController;

    private final BoardController boardController;
    private final ShelfieController shelfieController;
    private final ChatController chatController;
    private final ServerSide serverSide;
    private final ServerMessageHandler messageHandler;

    public Controller() {
        this.messageHandler = new ServerMessageHandler();
        this.currentGame = new Game();
        this.gameController = new GameController();
        this.loginController = new LoginController();
        this.boardController = new BoardController();
        this.shelfieController = new ShelfieController();
        this.chatController = new ChatController();
        this.serverSide = new ServerSide();
    }

    /**
     * Process chat request decoded from JSON by ServerMessageHandler
     *
     * @param requester
     * @param message
     */
    public boolean fetchChatRequest(String requester, String message) {
        try {
            chatController.sendChatMessage(requester, message);
            return true;
        } catch (InvalidNameException e) {
            return false;
        }
    }

    /**
     * Process chat request decoded from JSON by ServerMessageHandler
     *
     * @param positionsToBeRemoved
     * @param column
     * @param requester
     * @return true if move is done by currentPlayerTurn, false if not
     */
    public boolean fetchGameMoveRequest(ArrayList<Cell> positionsToBeRemoved, int column, String requester) {
        if (currentGame.getCurrentPlayer().getNickname().equals(requester)) {
            boolean confirm = checkLegalGameMove(positionsToBeRemoved, column, requester);
            if (!confirm)
                return false;

            gameController.sendGameMove(positionsToBeRemoved, column, requester);
            return true;
        } else
            return false;
    }

    public boolean fetchLoginRequest(String nickname, int gameMode, int numOfPlayer, int startGame) {
        if (currentGame.getPlayers().contains(nickname))
            return false;
        /*TODO else if (currentGame.getDisconnectedPlayers().contains(nickname)) {
            //reconnect
        }*/

        //TODO startGame for multiple games
        loginController.sendLoginRequest(nickname, gameMode, numOfPlayer, startGame);
        return true;
    }

    /**
     * Server-side legal move checker
     * @param positionsToBeRemoved
     * @param column
     * @param requester
     * @return true if move is legal
     */
    public boolean checkLegalGameMove (ArrayList<Cell> positionsToBeRemoved, int column, String requester)
    {
        if (currentGame.getGameBoard().checkLegalChoice(positionsToBeRemoved))
            return false;

        //Create an arrayList of tile from arrayList of cell
        ArrayList<Tile> tilesToBeRemoved = new ArrayList<>();
        for (Cell c : positionsToBeRemoved)
            tilesToBeRemoved.add(currentGame.getGameBoard().getBoard()[c.getRow()][c.getColumn()]);

        if (currentGame.getCurrentPlayer().getShelfie().checkLegalInsert(tilesToBeRemoved, column))
            return false;

        return true;
    }
}


