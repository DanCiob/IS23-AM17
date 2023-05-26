package it.polimi.softeng.client.view.GUI;

import it.polimi.softeng.JSONWriter.ChatWriter;
import it.polimi.softeng.JSONWriter.GameMoveWriter;
import it.polimi.softeng.client.view.CLI.CLI;
import it.polimi.softeng.client.view.CommonOperationsFramework;
import it.polimi.softeng.client.view.MessageHandler;
import it.polimi.softeng.client.view.UI;
import it.polimi.softeng.connectionProtocol.client.ClientSide;
import it.polimi.softeng.connectionProtocol.client.ClientSideRMI;
import it.polimi.softeng.customExceptions.IllegalInsertException;
import it.polimi.softeng.model.*;
import org.json.simple.JSONObject;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static it.polimi.softeng.Constants.*;
import static it.polimi.softeng.Constants.gameCommandREGEX;
import static it.polimi.softeng.JSONWriter.ClientSignatureWriter.clientSignObject;

public class GUIClientSide extends CommonOperationsFramework implements UI {
    /**
     * Local representation of GameBoard
     */
    protected GameBoard UserGameBoard;

    /**
     * Local representation of Shelfie
     */
    protected Shelfie UserShelfie;

    /**
     * Player information
     */
    protected int UserScore;
    protected PersonalCards PersonalCard;
    protected String Nickname;
    protected String CommonCard1 = null;
    protected String CommonCard2 = null;
    protected String ServerAddress;
    protected int Port;

    /**
     * 1 -> player want to create new game (FA: multi-game management)
     * 2 -> player want to join (if present) a game which is already started
     */
    protected int StartGame;

    /**
     * 1 -> Easy Mode (only one Common Card is used the game)
     * 2 -> Normal Mode (two Common Cards are used during the game)
     */
    protected int GameMode;

    /**
     * NumOfPlayer for current game
     */
    protected int NumOfPlayer;

    /**
     * 1 -> Connected with Socket
     * 2 -> Connected with RMI
     */
    protected int ConnectionMode = 0;

    protected boolean GameIsOn = false;

    private boolean okNickname;

    protected final MessageHandler messageHandler;

    /**
     * Manage connection -> Socket
     */
    protected ClientSide clientSide;

    /**
     * Manage connection -> RMI
     */
    protected ClientSideRMI RemoteMethods;

    protected boolean isYourTurn;

    GUIGameController gameController;
    GUILoginController loginController;


    public GUIClientSide() {
        messageHandler = new MessageHandler(this);
        clientSide = new ClientSide(messageHandler);
    }

    public void setupGUI(int connectionMode, String serverAddress, int port, int startGame, int numPlayers, int mode){
        setConnectionMode(connectionMode);
        switch (getConnectionMode()) {
            case 1 -> { //socket
                setServerAddress(serverAddress);
                setPort(port);
                setStartGame(startGame);
                if(startGame == 1){ //create new game
                    setNumOfPlayer(numPlayers);
                    setGameMode(mode);
                }
            }
            case 2 -> { //TODO: RMI
            }
        }
    }

    public ClientSide getClientSide() {
        return clientSide;
    }


    public void run() {
        boolean firstRun = true;
        boolean GameIsOn = false;

        System.out.println("Waiting for other players to join...");
        //Waiting for beginning of game
        while (!GameIsOn) {
            System.out.println("Waiting for other players to join...");
        }

        while (GameIsOn) {
            try {
                //Wait for errors
                TimeUnit.SECONDS.sleep(1);
                game(firstRun);
            } catch (InterruptedException | RemoteException e) {
                throw new RuntimeException(e);
            }
            firstRun = false;
        }
    }

    public void game(boolean firstRun) throws RemoteException {
        String command = null;

        //Check empty command
        if (command == null) {
            System.out.println("Empty command!");
            return;
        }
        //Check if player inserted a command that is not on the table
        if (!isOkCommand(command, 1)) {
            System.out.println("Please write a command that you can see in the table");
            return;
        }

        if (command.equalsIgnoreCase("@CMND") || command.equalsIgnoreCase("@VBOR") || command.equalsIgnoreCase("@VSHE") || command.equalsIgnoreCase("@VPCA") || command.equalsIgnoreCase("@VPLA") || command.equalsIgnoreCase("@VCCA")) {
            switch (command.toUpperCase()) {
                case ("@CMND") -> {
                    return;
                }
                case ("@VBOR") -> {
                    boardVisualizer(UserGameBoard.getBoard(), UserGameBoard.getNotAvailable());
                    return;
                }
                case ("@VSHE") -> {
                    shelfieVisualizer(UserShelfie.getGrid());
                    return;
                }
                case ("@VPCA") -> {
                    personalCardVisualizer(PersonalCard);
                    return;
                }
                case ("@VCCA") -> {
                    commonCardsVisualizer(CommonCard1);
                    if (CommonCard2 != null) commonCardsVisualizer(CommonCard2);
                    return;
                }
                case ("@VPLA") -> {
                    switch (ConnectionMode) {
                        //Socket
                        case 1 -> {
                            JSONObject dummy = new JSONObject();
                            clientSide.sendMessage(clientSignObject(dummy, "@VPLA", Nickname).toJSONString());
                            return;
                        }
                        //RMI
                        case 2 -> {
                            eventManager("playerEvent");
                            scoreVisualizer(RemoteMethods.getStub().getPlayersAndScore());
                            return;
                        }
                    }
                }
            }
        }

        if (!isOkCommand(command, 2) && !isOkCommand(command, 3)){
            System.out.println("Please write a command that you can see in the table");
            return;
        }

        //Command is in @CMND format (every command is 4 letters), uppercase avoid case sensitivity
        String op = command.substring(0, 5).toUpperCase();
        String action = command.substring(6);

        if (!op.equals("@GAME") && !op.equals("@CHAT") && !op.equals("@VPLA")) {
            System.out.println("Please write a command that you can see in the table");
            return;
        }

        switch (ConnectionMode) {
            //Socket
            case 1 -> {
                //Block illegal move
                if (op.equals("@GAME")) {
                    if (!isOkCommand(command, 3)) {
                        System.out.println("Please, check gameMove syntax");
                        return;
                    }
                }
                //Block badly formatted messages
                if (op.equals("@CHAT")) {
                    if (!isOkCommand(command, 2)) {
                        System.out.println("Please, check chat syntax");
                        return;
                    }
                }

                //Create JSON messages containing request
                JSONObject toBeSent = null;
                try {
                    toBeSent = actionToJSON(op, action);
                } catch (IllegalInsertException e) {
                    throw new RuntimeException(e);
                }

                //Send message to server
                if (toBeSent != null)
                    clientSide.sendMessage(clientSignObject(toBeSent, op, Nickname).toJSONString());
                isYourTurn = false;
            }


            case 2 -> {
                if (op.equals("@GAME")) {
                    if (!isOkCommand(command, 3)) {
                        System.out.println("Please, check gameMove syntax");
                        return;
                    }
                }

                if (op.equals("@CHAT")) {
                    if (!isOkCommand(command, 2)) {
                        System.out.println("Please, check chat syntax");
                        return;
                    }
                }
                isYourTurn = false;
                RMIInvoker(op, action);
            }
        }
    }

    //////////////////
    //REGEX CHECKERS//
    //////////////////

    public boolean isOkNickname() {
        Pattern pattern = Pattern.compile(nicknameREGEX);
        Matcher matcher = pattern.matcher(this.Nickname);
        return matcher.matches();
    }

    public boolean isOkCommand(String command, int typeOfCommand) {
        switch (typeOfCommand) {
            case 1 -> {
                Pattern pattern = Pattern.compile(commandREGEX);
                Matcher matcher = pattern.matcher(command);
                return matcher.lookingAt();
            }
            case 2 -> {
                Pattern pattern = Pattern.compile(chatCommandREGEX);
                Matcher matcher = pattern.matcher(command);
                return matcher.lookingAt();
            }
            case 3 -> {
                Pattern pattern = Pattern.compile(gameCommandREGEX);
                Matcher matcher = pattern.matcher(command);
                return matcher.lookingAt();
            }
        }
        return false;
    }


    ///////////
    //SETTERS//
    ///////////

    public void setUserGameBoard(GameBoard userGameBoard) {
        UserGameBoard = userGameBoard;
    }

    public void setUserShelfie(Shelfie userShelfie) {
        UserShelfie = userShelfie;
    }

    public void setUserScore(int userScore) {
        UserScore = userScore;
    }

    public void setPersonalCard(PersonalCards personalCard) {
        PersonalCard = personalCard;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    public void setCommonCard1(String commonCard1) {
        CommonCard1 = commonCard1;
    }

    public void setCommonCard2(String commonCard2) {
        CommonCard2 = commonCard2;
    }

    @Override
    public void boardVisualizer(Tile[][] board, ArrayList<Cell> notAvailable) {

    }

    @Override
    public void shelfieVisualizer(Tile[][] shelfie) {

    }

    @Override
    public void commonCardsVisualizer(String commonCard) {

    }

    @Override
    public void personalCardVisualizer(PersonalCards personalCard) {

    }

    @Override
    public void chatVisualizer(JSONObject jsonMessage) {

    }

    @Override
    public void scoreVisualizer(ArrayList<Player> players) {

    }

    @Override
    public void eventManager(String event) {

    }

    @Override
    public void boardUpdater(GameBoard b) {
        UserGameBoard = b;
    }

    @Override
    public void shelfieUpdater(Shelfie s) {
        UserShelfie = s;
    }

    @Override
    public void scoreUpdater(int s) {

    }

    @Override
    public void personalCardUpdater(PersonalCards pc) {
        this.PersonalCard = pc;
    }

    @Override
    public void commonCardUpdater(String nameOfCommonCard, int whatCommonCard) {
        if (whatCommonCard == 1)
            CommonCard1 = nameOfCommonCard;
        else
            CommonCard2 = nameOfCommonCard;
    }

    @Override
    public void setServerAddress(String serverAddress) {
        ServerAddress = serverAddress;
    }

    @Override
    public void setPort(int port) {
        Port = port;
    }

    @Override
    public void endGame(boolean winner) {

    }

    @Override
    public void beginGame(boolean b) {

    }

    public void setStartGame(int startGame) {
        StartGame = startGame;
    }

    public void setGameMode(int gameMode) {
        GameMode = gameMode;
    }

    public void setNumOfPlayer(int numOfPlayer) {
        NumOfPlayer = numOfPlayer;
    }

    public void setConnectionMode(int connectionMode) {
        ConnectionMode = connectionMode;
    }

    public void setGameIsOn(boolean gameIsOn) {
        GameIsOn = gameIsOn;
    }

    public void setOkNickname(boolean okNickname) {
        this.okNickname = okNickname;
    }

    public void setClientSide(ClientSide clientSide) {
        this.clientSide = clientSide;
    }

    public void setRemoteMethods(ClientSideRMI remoteMethods) {
        RemoteMethods = remoteMethods;
    }

    public void setGameController(GUIGameController gameController) {
        this.gameController = gameController;
    }

    public void setLoginController(GUILoginController loginController) {
        this.loginController = loginController;
    }

    public void setYourTurn(boolean yourTurn) {
        isYourTurn = yourTurn;
    }

    ///////////
    //GETTERS//
    ///////////


    public GameBoard getUserGameBoard() {
        return UserGameBoard;
    }

    public Shelfie getUserShelfie() {
        return UserShelfie;
    }

    public int getUserScore() {
        return UserScore;
    }

    public PersonalCards getPersonalCard() {
        return PersonalCard;
    }

    public String getNickname() {
        return Nickname;
    }

    public String getCommonCard1() {
        return CommonCard1;
    }

    public String getCommonCard2() {
        return CommonCard2;
    }

    public String getServerAddress() {
        return ServerAddress;
    }

    public int getPort() {
        return Port;
    }

    public int getStartGame() {
        return StartGame;
    }

    public int getGameMode() {
        return GameMode;
    }

    public int getNumOfPlayer() {
        return NumOfPlayer;
    }

    public int getConnectionMode() {
        return ConnectionMode;
    }

    public boolean isGameIsOn() {
        return GameIsOn;
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

    public ClientSideRMI getRemoteMethods() {
        return RemoteMethods;
    }

    public GUIGameController getGameController() {
        return gameController;
    }

    public GUILoginController getLoginController() {
        return loginController;
    }

    public boolean isYourTurn() {
        return isYourTurn;
    }

    @Override
    public JSONObject actionToJSON(String op, String action) throws IllegalInsertException {
        switch (op) {
            case ("@CHAT") -> {
                if (!ChatWriter.chatMessageRegex(action) || ChatWriter.writeChatMessage(action) == null) {
                    eventManager("chatError");
                    return null;
                } else
                    return ChatWriter.writeChatMessage(action);
            }
            case ("@GAME") -> {
                if (!GameMoveWriter.gameMoveRegex(action) || GameMoveWriter.writeGameMove(action) == null) {
                    eventManager("gameMoveError");
                    return null;
                } else
                    return GameMoveWriter.writeGameMove(action);
            }

            default -> System.out.println("Unrecognized operation!");
        }
        return null;
    }

    @Override
    public boolean RMIInvoker(String op, String action) {
        return false;
    }
}
