package it.polimi.softeng.client.view.GUI;

import it.polimi.softeng.JSONParser.ChatParser;
import it.polimi.softeng.JSONParser.GameMoveParser;
import it.polimi.softeng.JSONWriter.ChatWriter;
import it.polimi.softeng.JSONWriter.GameMoveWriter;
import it.polimi.softeng.client.view.CommonOperationsFramework;
import it.polimi.softeng.client.view.MessageHandler;
import it.polimi.softeng.client.view.UI;
import it.polimi.softeng.connectionProtocol.client.ClientSide;
import it.polimi.softeng.connectionProtocol.client.ClientSideRMI;
import it.polimi.softeng.customExceptions.IllegalInsertException;
import it.polimi.softeng.model.*;
import it.polimi.softeng.model.commonCards.CommonCards;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static it.polimi.softeng.Constants.*;

/**
 * Main class of Graphic User Interface for MyShelfie
 */
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
    protected Boolean isFirst = false;
    protected String CommonCard1 = null;
    protected String CommonCard2 = null;
    protected String ServerAddress;
    protected int Port;

    protected Map <String, Shelfie> nicknameShelfie = new HashMap<>();

    protected int StartGame;

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

    protected MessageHandler messageHandler;

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

    EndGameController endGameController;

    Stage stage;

    public GUIClientSide() {
    }

    public void setupGUI(int connectionMode, String serverAddress, int port, int numPlayers) {
        setConnectionMode(connectionMode);
        setServerAddress(serverAddress);
        setPort(port);
    }

    public ClientSide getClientSide() {
        return clientSide;
    }


    //////////////////
    //REGEX CHECKERS//
    //////////////////

    public boolean isOkNickname() {
        Pattern pattern = Pattern.compile(nicknameREGEX);
        Matcher matcher = pattern.matcher(this.Nickname);
        return matcher.matches();
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
    public void commonCardsVisualizer(CommonCards commonCard) {
//TODO:remove
    }

    @Override
    public void personalCardVisualizer(PersonalCards personalCard) {

    }

    @Override
    public void chatVisualizer(JSONObject jsonMessage) {
        ChatParser chatParser = new ChatParser();
        chatParser.chatParser(jsonMessage.toJSONString());
        if(!(chatParser.getRequester().equals("System")) && gameController!=null)
            gameController.setChatMessage(chatParser.getRequester() + ": " + chatParser.getMessage());
    }

    @Override
    public void scoreVisualizer(ArrayList<Player> players) {
        setNumOfPlayer(players.size());
        for (Player p: players) {
            nicknameShelfie.put(p.getNickname(), new Shelfie());

            if (p.isFirst() && p.getNickname().equals(Nickname)) {
                isFirst = true;
                if(gameController!=null)
                    gameController.setFirstPlayer();
            }

            //todo:add first player token to other players
        }
        if(endGameController!=null){
            Service New_Service = new Service() {
                @Override
                protected Task createTask() {
                    return new Task() {
                        @Override
                        protected Object call() {
                            Platform.runLater(() -> {
                                endGameController.scoreVisualizer(players);
                            });
                            return null;
                        }
                    };
                }
            };
            New_Service.start();
        }
    }

    @Override
    public void eventManager(String event) {
        switch (event) {
            //Client-side errors
            case ("chatError") -> {
                System.out.println("Error in chat message syntax, try again!");
            }
            case ("gameMoveError") -> {
                System.out.println("Error in game move syntax, try again!");
                if(gameController != null){
                    gameController.setMoveError(true);
                    gameController.resetAfterMove();
                }
            }

            //Events
            case ("chatEvent") -> {
                System.out.println(ANSI_CYAN);
                System.out.println("+-----------------------+");
                System.out.println("| Received chat message |");
                System.out.println("+-----------------------+");
                System.out.println(ANSI_RESET);
            }
            case ("globalChatEvent") -> {
                System.out.println(ANSI_CYAN);
                System.out.println("+------------------------------+");
                System.out.println("| Received global chat message |");
                System.out.println("+------------------------------+");
                System.out.println(ANSI_RESET);
            }
            case ("systemEvent") -> {
                System.out.println(ANSI_CYAN);
                System.out.println("+-------------------------+");
                System.out.println("| Received system message |");
                System.out.println("+-------------------------+");
                System.out.println(ANSI_RESET);

            }
            case ("boardEvent") -> {
                System.out.println(ANSI_CYAN);
                System.out.println("+-----------------------+");
                System.out.println("| Received board update |");
                System.out.println("+-----------------------+");
                System.out.println(ANSI_RESET);
                if(gameController != null)
                    gameController.updateBoard();
                if(gameController != null && isYourTurn){
                    gameController.setMoveConfirmed(true);
                    gameController.resetAfterMove();
                    isYourTurn = false;
                }
            }
            case ("shelfieEvent") -> {
                System.out.println(ANSI_CYAN);
                System.out.println("+-------------------------+");
                System.out.println("| Received shelfie update |");
                System.out.println("+-------------------------+");
                System.out.println(ANSI_RESET);

            }
            case ("personalCardEvent") -> {
                System.out.println(ANSI_CYAN);
                System.out.println("+----------------------------------+");
                System.out.println("| Your personal card for this game |");
                System.out.println("+----------------------------------+");
                System.out.println(ANSI_RESET);
            }
            case ("commonCardEvent") -> {
                System.out.println(ANSI_CYAN);
                System.out.println("+----------------------------+");
                System.out.println("| Common cards for this game |");
                System.out.println("+----------------------------+");
                System.out.println(ANSI_RESET);
            }
            case ("playerEvent") -> {
                System.out.println(ANSI_CYAN);
                System.out.println("+--------------------------------------+");
                System.out.println("| List of connected players with score |");
                System.out.println("+--------------------------------------+");
                System.out.println(ANSI_RESET);
            }
            case ("myTurn") -> {
                System.out.println(ANSI_CYAN);
                System.out.println("+-----------------+");
                System.out.println("| It's your turn! |");
                System.out.println("+-----------------+");
                System.out.println(ANSI_RESET);
                isYourTurn = true;
                if (gameController != null)
                    gameController.startTurn();
            }

            //Servers-side errors
            case (NICKNAME_NOT_UNIQUE) -> {
                System.out.println(NICKNAME_NOT_UNIQUE);
                okNickname = false;
                System.out.println("nickname not unique in guiclientside");
                switchToLogin();
            }
            case (PLAYER_DISCONNECTED) -> {
                System.out.println(PLAYER_DISCONNECTED);
                Service New_Service = new Service() {
                    @Override
                    protected Task createTask() {
                        return new Task() {
                            @Override
                            protected Object call() {
                                Platform.runLater(() -> {
                                    if(gameController!=null){
                                        gameController.setChatMessage("Player disconnected");
                                    }
                                });
                                return null;
                            }
                        };
                    }
                };
                New_Service.start();
            }
            case (INVALID_NUMBER_OF_PLAYERS) -> System.out.println(INVALID_NUMBER_OF_PLAYERS);
            case (INVALID_CHOICE_OF_TILES) -> System.out.println(INVALID_CHOICE_OF_TILES);
            case (INVALID_COLUMN) -> System.out.println(INVALID_COLUMN);
            case (INVALID_RECEIVER) -> {
                System.out.println(INVALID_RECEIVER);
                Service New_Service = new Service() {
                    @Override
                    protected Task createTask() {
                        return new Task() {
                            @Override
                            protected Object call() {
                                Platform.runLater(() -> {
                                    if(gameController!=null) {
                                        gameController.setChatMessage("Invalid receiver");
                                    }
                                });
                                return null;
                            }
                        };
                    }
                };
                New_Service.start();
            }
            case (ALREADY_LOGGED_IN) -> System.out.println(ALREADY_LOGGED_IN);
            case (YOU_ARE_RECEIVER) -> System.out.println(YOU_ARE_RECEIVER);
            case (ERROR_IN_GAMEMOVE) ->{
                System.out.println((ERROR_IN_GAMEMOVE));
                if(gameController != null){
                    gameController.setMoveError(true);
                    gameController.resetAfterMove();
                }
            }
            case (ALL_PLAYERS_DISCONNECTED) -> {
                System.out.println(ALL_PLAYERS_DISCONNECTED);
                Service New_Service = new Service() {
                    @Override
                    protected Task createTask() {
                        return new Task() {
                            @Override
                            protected Object call() {
                                Platform.runLater(() -> {
                                    if(gameController!=null) {
                                        gameController.setChatMessage("All players disconnected");
                                    }
                                });
                                return null;
                            }
                        };
                    }
                };
                New_Service.start();
            }


            default -> System.out.println("Unrecognized event!");
        }

    }

    @Override
    public void boardUpdater(GameBoard b) {
        UserGameBoard = b;
        if(gameController!=null)
            gameController.updateBoard();
    }

    @Override
    public void shelfieUpdater(Shelfie s) {
        UserShelfie = s;
        if(gameController!=null)
            gameController.updatePersonalShelfie();
    }

    @Override
    public void shelfieUpdater(Shelfie s, String nickname) {
        nicknameShelfie.put(nickname, s);
        if(gameController!=null){
            gameController.updateShelfies();
        }
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
        if (whatCommonCard == 1) {
            CommonCard1 = nameOfCommonCard;
        }else {
            CommonCard2 = nameOfCommonCard;
        }
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
    public void endGame(boolean winner){
        System.out.println("In endGame");
        if(gameController!=null){
            System.out.println("gameController is not null");
            if(isYourTurn && gameController!=null){
                System.out.println("isYourTurn");
                gameController.resetAfterMove();
            }
            Service New_Service = new Service() {
                @Override
                protected Task createTask() {
                    return new Task() {
                        @Override
                        protected Object call() {
                            Platform.runLater(() -> {
                                System.out.println("runLater");
                                gameController.switchToEndGame();
                                if(winner){
                                    endGameController.setWinner("YOU WON");
                                }else{
                                    endGameController.setWinner("YOU LOST");
                                }
                            });
                            return null;
                        }
                    };
                }
            };
            New_Service.start();

        }
    }

    @Override
    public void beginGame(boolean b){
        boolean switchScene = !GameIsOn;
        this.GameIsOn = b;
        if(switchScene && b){
            if(loginController == null)
                System.out.println("loginController is null");
            else{
                Service New_Service = new Service() {
                    @Override
                    protected Task createTask() {
                        return new Task() {
                            @Override
                            protected Object call() {
                                Platform.runLater(() -> {
                                    try {
                                        loginController.switchToGame();
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                });
                                return null;
                            }
                        };
                    }
                };
                New_Service.start();
            }
        }
    }

    public void setStartGame(int startGame) {
        StartGame = startGame;
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

    public void setEndGameController(EndGameController endGameController) {
        this.endGameController = endGameController;
    }

    public void setLoginController(GUILoginController loginController) {
        this.loginController = loginController;
    }

    public void setYourTurn(boolean yourTurn) {
        isYourTurn = yourTurn;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
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

    public int getNumOfPlayer() {
        return NumOfPlayer;
    }

    public int getConnectionMode() {
        return ConnectionMode;
    }

    public Stage getStage() {
        if(stage!=null)
            return stage;
        else
            return new Stage();
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
                System.out.println(GameMoveWriter.writeGameMove(action));
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
        switch (op) {
            case ("@CHAT") -> {
                if (!ChatWriter.chatMessageRegex(action)) {
                    System.out.println("Error in Chat message syntax, try again!");
                    if(gameController!=null){
                        gameController.chatMessage.setText("Error in chat message, try again!");
                    }
                    return false;
                }

                JSONObject obj;
                obj = ChatWriter.writeChatMessage(action);

                if (obj != null) {
                    if (obj.get("receiver").toString().equals("all")) {
                        try {
                            RemoteMethods.getStub().sendMessageToAll(obj.toJSONString(), Nickname);
                        } catch (RemoteException e) {
                            System.out.println("Please, reinsert your message!");
                            if(gameController!=null) {
                                gameController.chatMessage.setText("Error in chat message, try again!");
                            }
                        }
                    } else {
                        try {
                            RemoteMethods.getStub().sendMessage(obj.toJSONString(), (String) obj.get("receiver"), Nickname);
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
            case ("@GAME") -> {
                if (!GameMoveWriter.gameMoveRegex(action)) {
                    System.out.println("Error in Game Move syntax, try again!");
                    return false;
                }
                GameMoveParser gmp = new GameMoveParser();
                gmp.gameMoveParser(GameMoveWriter.writeGameMove(action).toJSONString());

                ArrayList<Cell> cells = gmp.getTilesToBeRemoved();
                int column = gmp.getColumn();
                try {
                    RemoteMethods.getStub().sendMove(cells, column, Nickname);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }

            case ("@LOGN") -> {
                //TODO Right now we don't receive GameMode, StartGame, NumOfPlayer...
                try {
                    boolean successfulLogin = RemoteMethods.getStub().login(Nickname, NumOfPlayer, action,RemoteMethods.getPort());   //dan: modified this as I changed some rmi methods, should work still
                    System.out.println("Successful login is " + successfulLogin);
                    if(!successfulLogin){
                        System.out.println("Not successful login");
                        try {
                            Parent root = FXMLLoader.load(getClass().getResource("/it.polimi.softeng.client.view.GUI/login.fxml"));
                            Scene scene = new Scene(root);
                            getStage().setScene(scene);
                            getStage().show();
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                    }
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            default -> System.out.println("Unrecognized operation!");
        }
        return true;
    }

    public void switchToLogin(){
        Service New_Service = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Object call() {
                        Platform.runLater(() -> {
                            try {
                                Parent root = FXMLLoader.load(getClass().getResource("/it.polimi.softeng.client.view.GUI/login.fxml"));
                                Scene scene = new Scene(root);
                                getStage().setScene(scene);
                                getStage().show();
                            } catch (IOException e) {
                                e.printStackTrace();
                                throw new RuntimeException(e);
                            }

                        });
                        return null;
                    }
                };
            }
        };
        New_Service.start();
    }
}
