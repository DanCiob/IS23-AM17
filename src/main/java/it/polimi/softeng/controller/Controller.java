package it.polimi.softeng.controller;

import it.polimi.softeng.connectionProtocol.server.ServerSide;
import it.polimi.softeng.model.Cell;

import java.util.ArrayList;

import static it.polimi.softeng.JSONWriter.PlayerWriter.playerAndScoreWriter;
import static it.polimi.softeng.JSONWriter.ServerSignatureWriter.serverSignObject;

/**
 * General controller with functions of:
 * - managing {@link ChatController} and {@link GameController} (which also controls the shelfie and the board);
 * - communicating using {@link ServerMessageHandler}
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
     * This method processes chat requests sent by {@link ServerMessageHandler}.
     * It also makes some checks on the receiver and sender, then forward to {@link ChatController}.
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
     * This method processes move requests sent by {@link ServerMessageHandler}, only if the requester is the current player.
     * Then it forwards the move request to {@link GameController}.
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
     * This method processes login requests sent from {@link ServerMessageHandler}r.
     * It verifies if the nickname is unique and ensures a reconnection for disconnected players.
     * @param nickname is nickname of player
     * @param gameMode is easy or normal mode
     * @param numOfPlayer is number of player (2-4)
     * @param startGame if 1 a new game will be created, if 2 join a lobby
     * @return true if request is successful
     */
    public boolean SocketLoginRequest(String nickname, int gameMode, int numOfPlayer, int startGame) {
        return !serverSide.getNickNameList().contains(nickname);
    }

    /**
     * This method receives Socket PlayerScore Requests from {@link ServerMessageHandler}
     *  and forwards them to {@link ServerSide}
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
     * This method manage the requests of start of a game sent by {@link it.polimi.softeng.connectionProtocol.server.LoginManagerV2}
     *  invoking the corresponding method of {@link GameController}
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


