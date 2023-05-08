package it.polimi.softeng.controller;

import it.polimi.softeng.connectionProtocol.ServerSide;
import it.polimi.softeng.model.Cell;
import it.polimi.softeng.model.Game;
import it.polimi.softeng.model.Tile;

import java.util.ArrayList;

/**
 * General controller managing game, gameController, boardController, shelfieController, chatController and communicating using serverSide
 */
public class Controller {
    private final GameController gameController;
    private final PlayerController playerController;

    private final BoardController boardController;
    private final ShelfieController shelfieController;
    private final ChatController chatController;
    private final ServerSide serverSide;
    private final ServerMessageHandler messageHandler;

    public Controller() {
        this.messageHandler = new ServerMessageHandler(this);
        this.gameController = new GameController(this);
        this.playerController = new PlayerController();
        this.boardController = new BoardController();
        this.shelfieController = new ShelfieController();
        this.chatController = new ChatController();
        this.serverSide = new ServerSide(this.messageHandler);
    }

    /**
     * Process chat request decoded from JSON by ServerMessageHandler
     *
     * @param receiver is receiver of chat message
     * @param message is JSON message encoded
     */
    public boolean fetchChatRequest(String receiver, String message, String sender) {
            if((!serverSide.getNickNameList().contains(receiver) || receiver.equals(sender)) && !receiver.equals("all"))
                return false;
            chatController.sendChatMessage(receiver, message, serverSide, sender);
            return true;
    }

    /**
     * Process game move request decoded from JSON by ServerMessageHandler
     *
     * @param positionsToBeRemoved is positions to be removed from board
     * @param column is column of insertion in shelfie
     * @param requester is requester of game move
     * @return true if move is done by currentPlayerTurn, false if not
     */
    public boolean fetchGameMoveRequest(ArrayList<Cell> positionsToBeRemoved, int column, String requester) {
        if (gameController.getCurrentGame().getCurrentPlayer().getNickname().equals(requester)) {
            try {
                boolean confirm = gameController.sendGameMove(positionsToBeRemoved, column, requester);
                return confirm;
            }catch (RuntimeException e)
            {
                return false;
            }

        } else
            //Not player's turn
            return false;
    }

    /**
     * Process login request from client
     * @param nickname is nickname of player
     * @param gameMode is easy or normal mode
     * @param numOfPlayer is number of player (2-4)
     * @param startGame if 1 a new game will be created, if 2 join a lobby
     * @return true if request is successful
     */
    public boolean fetchLoginRequest(String nickname, int gameMode, int numOfPlayer, int startGame) {
        //TODO check uniqueness
        /*if (gameController.getCurrentGame().getPlayers().contains(nickname))
            return false;*/
        /*TODO else if (currentGame.getDisconnectedPlayers().contains(nickname)) {
            //reconnect
        }*/

        //TODO startGame for multiple games
        playerController.sendLoginRequest(nickname, gameMode, numOfPlayer, startGame);
        return true;
    }

    /**
     * Server-side legal move checker
     * @param positionsToBeRemoved contains tile positions that will be removed from board
     * @param column is shelfie column of insertion
     * @param requester is game move requester
     * @return true if move is legal
     */
    public boolean checkLegalGameMove (ArrayList<Cell> positionsToBeRemoved, int column, String requester)
    {
        if (gameController.getCurrentGame().getGameBoard().checkLegalChoice(positionsToBeRemoved))
            return false;

        //Create an arrayList of tile from arrayList of cell
        ArrayList<Tile> tilesToBeRemoved = new ArrayList<>();
        for (Cell c : positionsToBeRemoved)
            tilesToBeRemoved.add(gameController.getCurrentGame().getGameBoard().getBoard()[c.getRow()][c.getColumn()]);

        if (gameController.getCurrentGame().getCurrentPlayer().getShelfie().checkLegalInsert(tilesToBeRemoved, column))
            return false;

        return true;
    }

    /**
     * Used to send error messages to client
     * @param error contains error type
     * @param receiver is error receiver
     */
    public void sendErrorMessage (String error, String receiver) {
        serverSide.sendMessage(error, receiver);
    }

    /**
     * Start game by creating model
     */
    public void startGame ()
    {
        gameController.startGame(serverSide.getNickNameList());
    }

    public ServerSide getServerSide() {
        return this.serverSide;
    }
}


