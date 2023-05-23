package it.polimi.softeng.controller;

import it.polimi.softeng.connectionProtocol.server.ServerSide;
import it.polimi.softeng.model.Cell;

import java.util.ArrayList;

import static it.polimi.softeng.JSONWriter.PlayerWriter.playerAndScoreWriter;
import static it.polimi.softeng.JSONWriter.ServerSignatureWriter.serverSignObject;

/**
 * General controller with functions of:
 * - managing gameController, boardController, shelfieController, chatController;
 * - communicating using serverSide.
 */
public class Controller {
    private final GameController gameController;
    private final ChatController chatController;

    private final ServerSide serverSide;
    private final ServerMessageHandler messageHandler;

    public Controller() {
        this.messageHandler = new ServerMessageHandler(this);
        this.gameController = new GameController(this);
        this.chatController = new ChatController();
        this.serverSide = new ServerSide(this.messageHandler,this);
    }

    /**
     * Process chat request decoded from JSON by ServerMessageHandler:
     *  it makes some checks on the receiver and sender, then send the chat message
     *
     * @param receiver is receiver of chat message
     * @param message is JSON message encoded
     * @param sender is the sender of the message
     */
    public boolean SocketChatRequest(String receiver, String message, String sender) {
            if((!serverSide.getNickNameList().contains(receiver) || receiver.equals(sender)) && !receiver.equals("all"))
                return false;
            chatController.sendChatMessage(receiver, message, serverSide, sender);
            return true;
    }

    /**
     * Process game move request decoded from JSON by ServerMessageHandler:
     *  it makes some checks on the requester of the move, then send it
     *
     * @param positionsToBeRemoved is positions to be removed from board
     * @param column is column of insertion in shelfie
     * @param requester is requester of game move
     * @return true if move is done by currentPlayerTurn, false if not
     */
    public boolean SocketGameMoveRequest(ArrayList<Cell> positionsToBeRemoved, int column, String requester) {
        if (gameController.getCurrentGame().getCurrentPlayer().getNickname().equals(requester)) {
            try {
                return gameController.sendGameMove(positionsToBeRemoved, column, requester);
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
    public boolean SocketLoginRequest(String nickname, int gameMode, int numOfPlayer, int startGame) {
        //TODO check uniqueness
        /*if (gameController.getCurrentGame().getPlayers().contains(nickname))
            return false;*/
        /*TODO else if (currentGame.getDisconnectedPlayers().contains(nickname)) {
            //reconnect
        }*/
        //TODO startGame for multiple games
        return true;
    }

    /**
     * Send a JSONObject containing player-score info
     * @param requester is requester
     * @return true if everything went correctly
     */
    public boolean SocketPlayerScoreRequest (String requester)
    {
        serverSide.sendMessage(serverSignObject(playerAndScoreWriter(gameController.getCurrentGame().getPlayers()), "@VPLA", requester).toJSONString(), requester);
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

    public GameController getGameController() {
        return gameController;
    }
}


