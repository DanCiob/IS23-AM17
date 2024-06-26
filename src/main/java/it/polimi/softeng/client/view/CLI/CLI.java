package it.polimi.softeng.client.view.CLI;

import it.polimi.softeng.JSONParser.ChatParser;
import it.polimi.softeng.JSONParser.GameMoveParser;
import it.polimi.softeng.JSONWriter.ChatWriter;
import it.polimi.softeng.JSONWriter.ClientSignatureWriter;
import it.polimi.softeng.JSONWriter.GameMoveWriter;
import it.polimi.softeng.JSONWriter.LoginWriter;
import it.polimi.softeng.client.view.MessageHandler;
import it.polimi.softeng.connectionProtocol.client.ClientSide;
import it.polimi.softeng.connectionProtocol.client.ClientSideRMI;
import it.polimi.softeng.model.*;
import it.polimi.softeng.client.view.CommonOperationsFramework;
import it.polimi.softeng.client.view.UI;
import it.polimi.softeng.model.PersonalCards;
import it.polimi.softeng.model.commonCards.CommonCards;
import org.json.simple.JSONObject;

import java.io.ByteArrayInputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static it.polimi.softeng.Constants.*;
import static it.polimi.softeng.JSONWriter.ClientSignatureWriter.clientSignObject;

/**
 * Command Line Interface for MyShelfie
 */
public class CLI extends CommonOperationsFramework implements UI, Runnable {
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

    /**
     * It's personal card of user
     */
    protected PersonalCards PersonalCard;

    /**
     * It's nickname of user
     */
    protected String Nickname;

    /**
     * It's first common card of user
     */
    protected String CommonCard1 = null;

    /**
     * It's second common card of user
     */
    protected String CommonCard2 = null;

    /**
     * Server address for current game
     */
    protected String ServerAddress;

    /**
     * Server port for current game
     */
    protected int Port;

    /**
     * Confirm of message to start the game
     */

    protected int StartGame;

    /**
     * NumOfPlayer for current game
     */
    protected int NumOfPlayer;

    /**
     * 1 -> Connected with Socket
     * 2 -> Connected with RMI
     * 3 -> Connection in localhost with RMI
     */
    protected int ConnectionMode = 0;

    /**
     * Trigger to activate game phase
     */
    protected boolean GameIsOn = false;

    /**
     * Used in login procedure to avoid duplicate nickname
     */
    private boolean okNickname = true;

    /**
     * CLI input
     */
    protected final Scanner input;

    /**
     * Manage reception of message -> Socket
     */
    protected final MessageHandler messageHandler;

    /**
     * Manage connection -> Socket
     */
    protected ClientSide clientSide;

    /**
     * Manage connection -> RMI
     */
    protected ClientSideRMI RemoteMethods;

    public CLI() {
        this.messageHandler = new MessageHandler(this);
        this.input = new Scanner(System.in);
    }

    /**
     * Just for testing purposes
     *
     * @param inputStream is directed input stream
     */
    public CLI(ByteArrayInputStream inputStream) {
        this.messageHandler = new MessageHandler(this);
        this.input = new Scanner(inputStream);
    }

    ////////////////
    //SETUP METHOD//
    ////////////////

    /**
     * CLI initialization, connection to server, choose of gameMode
     * After setup CLI is ready to be used
     */
    public void setupCLI() {
        String PortString;

        System.out.println("Initializing CLI...");
        System.out.println("Do you want to connect using Socket(1),RMI(2) or local RMI(3)?");
        System.out.println(">");
        do {
            try {
                ConnectionMode = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(ANSI_RED + "Please relaunch client and select a valid option for connection" + ANSI_RESET);
                System.exit(0);
            }

            switch (ConnectionMode) {
                //Socket
                case 1 -> {
                    System.out.println("Connection with Socket...");
                    System.out.println("Digit server IP");
                    System.out.println(">");
                    ServerAddress = input.nextLine();
                    System.out.println("Digit server Port");
                    System.out.println(">");
                    PortString = input.nextLine();
                    System.out.println("If you want to reconnect to a previous game choose the same nickname and same number of players of old match");
                    System.out.println(ANSI_YELLOW + "WARNING:" + ANSI_RESET + "If there's already an active lobby you'll join the lobby");
                    System.out.println(">");

                    do {
                        System.out.println("Insert the number of players(2-4)");
                        System.out.println(">");
                        NumOfPlayer = input.nextInt();
                        input.nextLine();
                    } while (NumOfPlayer < 2 || NumOfPlayer > 4);

                    //this is so that if you press enter it connects to the server specified in the json file
                    if (!ServerAddress.equals("") && !PortString.equals("")) {
                        Port = Integer.parseInt(PortString);
                        this.clientSide = new ClientSide(ServerAddress, Port, messageHandler);
                    } else {
                        this.clientSide = new ClientSide(messageHandler);
                    }

                    do {
                        do {
                            System.out.println("Insert nickname (ONLY characters a-z A-Z 0-9 and _ allowed, nickname: System/system isn't allowed)");
                            System.out.println(">");
                            Nickname = input.nextLine();
                        } while (!isOkNickname() || Nickname.equalsIgnoreCase("system"));
                        okNickname = true;
                        String login = ClientSignatureWriter.clientSignObject(LoginWriter.writeLogin(Nickname, 2, StartGame, NumOfPlayer), "@LOGN", Nickname).toJSONString();
                        clientSide.sendMessage(login);

                        try {
                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException e) {
                            System.out.println("Server did not respond, try again");
                            okNickname = false;
                        }
                    } while (!okNickname);
                }

                //RMI
                case 2 -> {
                    System.out.println("Connection with RMI...");
                    System.out.println("Digit server IP");
                    System.out.println(">");
                    ServerAddress = input.nextLine();
                    System.out.println("If you want to reconnect to a previous game choose the same nickname and same number of players of old match");
                    System.out.println(ANSI_YELLOW + "WARNING:" + ANSI_RESET + "If there's already an active lobby you'll join the lobby");
                    System.out.println(">");

                    do {
                        System.out.println("Insert the number of players(2-4)");
                        System.out.println(">");
                        NumOfPlayer = input.nextInt();
                        input.nextLine();
                    } while (NumOfPlayer < 2 || NumOfPlayer > 4);

                    do {
                        do {
                            System.out.println("Insert nickname (ONLY characters a-z A-Z 0-9 and _ allowed, nickname: System/system isn't allowed)");
                            System.out.println(">");
                            Nickname = input.nextLine();

                            //if the user pressed enter it connects to the server specified in the json file
                            if (!ServerAddress.equals("")) {
                                this.RemoteMethods = new ClientSideRMI(ServerAddress, this);
                            } else this.RemoteMethods = new ClientSideRMI(this);

                        } while (!isOkNickname() || Nickname.equalsIgnoreCase("system"));
                        okNickname = RMIInvoker("@LOGN", "n");
                    } while (!okNickname);
                }

                //Local RMI
                case 3 -> {
                    System.out.println("If you want to reconnect to a previous game choose the same nickname and same number of players of old match");
                    System.out.println(ANSI_YELLOW + "WARNING:" + ANSI_RESET + "If there's already an active lobby you'll join the lobby");
                    System.out.println(">");

                    do {
                        System.out.println("Insert the number of players(2-4)");
                        System.out.println(">");
                        NumOfPlayer = input.nextInt();
                        input.nextLine();
                    } while (NumOfPlayer < 2 || NumOfPlayer > 4);

                    do {
                        System.out.println("Insert nickname (ONLY characters a-z A-Z 0-9 and _ allowed)");
                        System.out.println(">");
                        Nickname = input.nextLine();

                        if (isOkNickname()) {
                            //in case of malfunction check the json client file to see if the server address is "127.0.0.1"
                            this.RemoteMethods = new ClientSideRMI(this);
                            //does the login here as it's basically just for testing
                            try {
                                okNickname = RemoteMethods.getStub().localLogin(Nickname, NumOfPlayer, "n", RemoteMethods.getPort());
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } while (!okNickname);

                    //This attribute identifies connection mode (it's RMI)
                    ConnectionMode = 2;

                }
                default -> System.out.println("Unrecognized connection method, please digit 1,2 or 3...");
            }
        } while (ConnectionMode != 1 && ConnectionMode != 2 && ConnectionMode != 3);
    }

    ////////////////
    //GAME METHODS//
    ////////////////

    /**
     * Notify CLI when game begins, toggle game start
     *
     * @param value is boolean
     */
    public void beginGame(boolean value) {
        this.GameIsOn = value;
    }

    /**
     * Main class of CLI, continuously run from begin of game to the end
     */
    @Override
    public void run() {
        boolean firstRun = true;
        setupCLI();

        System.out.println("Waiting for other players to join...");

        //Waiting for beginning of game
        while (!GameIsOn) {
            try {
                loadingScreen();
                System.out.println("Waiting for other players to join...");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        //Game phase
        while (GameIsOn) {
            try {
                TimeUnit.SECONDS.sleep(1);
                game(firstRun);
            } catch (InterruptedException | RemoteException e) {
                throw new RuntimeException(e);
            }
            firstRun = false;
        }
        input.close();
    }

    /**
     * Game routine that wait for commands, check syntax and send JSON/invoke method to server if needed
     *
     * @param firstRun is true if it's the first call of game
     */
    public void game(boolean firstRun) throws RemoteException {
        //Print of the available commands (only at the first run), that the user can digit
        if (firstRun)
            commands(true);

        String command = input.nextLine();

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

        //These commands do not need server, except for @VPLA
        if (command.equalsIgnoreCase("@CMND") || command.equalsIgnoreCase("@VBOR") || command.equalsIgnoreCase("@VSHE") || command.equalsIgnoreCase("@VPCA") || command.equalsIgnoreCase("@VPLA") || command.equalsIgnoreCase("@VCCA") || command.equalsIgnoreCase("@HELP")) {
            switch (command.toUpperCase()) {
                case ("@HELP") -> {
                    help();
                    return;
                }
                case ("@CMND") -> {
                    commands(true);
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
                            JSONObject VPLA = new JSONObject();
                            clientSide.sendMessage(clientSignObject(VPLA, "@VPLA", Nickname).toJSONString());
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

        //Check if it's a chat or game request
        if (!isOkCommand(command, 2) && !isOkCommand(command, 3)) {
            System.out.println("Please write a command that you can see in the table");
            return;
        }

        //Command is in @CMND format (every command is 4 letters), uppercase avoid case sensitivity
        String op = command.substring(0, 5).toUpperCase();
        String action = command.substring(6);

        if (!op.equals("@GAME") && !op.equals("@CHAT")) {
            System.out.println("Please write a command that you can see in the table");
            return;
        }

        switch (ConnectionMode) {
            //Socket
            case 1 -> {
                //Block badly formatted game move
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
                JSONObject toBeSent = actionToJSON(op, action);

                //Send message to server
                if (toBeSent != null)
                    clientSide.sendMessage(clientSignObject(toBeSent, op, Nickname).toJSONString());
            }

            //RMI
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

                //Invoke methods
                RMIInvoker(op, action);
            }
        }
    }

    /////////////////
    //REGEX MATCHER//
    /////////////////

    /**
     * Verify that inserted nickname follows regex standard defined for nicknames
     *
     * @return true if Nickname follow regex standard defined for nicknames
     */
    public boolean isOkNickname() {
        Pattern pattern = Pattern.compile(nicknameREGEX);
        Matcher matcher = pattern.matcher(this.Nickname);
        return matcher.matches();
    }

    /**
     * Verify that inserted command follows regex standard defined for commands
     *
     * @param command       is command defined by user in console
     * @param typeOfCommand 1 for generic command, 2 for chatCheck, 3 for gameCheck
     * @return true if command follows regex standard
     */
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

    ////////////////////////
    //CLIENT-SERVER SENDER//
    ////////////////////////

    /**
     * @param op     is command
     * @param action is command text sent by UI
     * @return a JSONObject containing encoded action
     */
    public JSONObject actionToJSON(String op, String action) {
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

    /**
     * @param op     is command
     * @param action is command text sent by UI
     * @return true if action is correctly executed
     */
    public boolean RMIInvoker(String op, String action) {
        switch (op) {
            case ("@CHAT") -> {
                if (!ChatWriter.chatMessageRegex(action)) {
                    System.out.println("Error in Chat message syntax, try again!");
                    return false;
                }

                JSONObject obj;
                obj = ChatWriter.writeChatMessage(action);

                if (obj != null && obj.get("receiver").toString().equals("all")) {
                    try {
                        RemoteMethods.getStub().sendMessageToAll(obj.toJSONString(), Nickname);
                    } catch (RemoteException e) {
                        System.out.println("Please, reinsert your message!");
                    }
                } else {
                    try {
                        RemoteMethods.getStub().sendMessage(obj.toJSONString(), (String) obj.get("receiver"), Nickname);
                    } catch (RemoteException e) {
                        System.out.println("Please, reinsert your message!");
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
                    boolean confirm = RemoteMethods.getStub().sendMove(cells, column, Nickname);
                    if (!confirm)
                        System.out.println("Error: Either it's not your turn or gameMove syntax is wrong");
                } catch (RemoteException e) {
                    System.out.println("Please, reinsert your message!");
                }
            }

            case ("@LOGN") -> {
                try {
                    return RemoteMethods.getStub().login(Nickname, NumOfPlayer, action, RemoteMethods.getPort());
                } catch (RemoteException e) {
                    System.out.println("Please, retry login!");
                }
            }
            default -> System.out.println("Unrecognized operation!");
        }
        return true;
    }

    ////////////
    //GRAPHICS//
    ////////////

    /**
     * Print all possible commands doable by user, eventually with MyShelfie logo only if it's first run
     *
     * @param firstRun if it is true, it means it's the first run
     */
    public void commands(boolean firstRun) {
        if (firstRun)
            logo();

        System.out.println(ANSI_RESET);
        System.out.println("+--------------------------------+");
        System.out.println("| Command                        |");
        System.out.println("+--------------------------------+");

        System.out.println("| " + ANSI_GREEN + "@CMND                          " + ANSI_RESET + "|");
        System.out.println("| " + ANSI_GREEN + "@VBOR                          " + ANSI_RESET + "|");
        System.out.println("| " + ANSI_GREEN + "@VSHE                          " + ANSI_RESET + "|");
        System.out.println("| " + ANSI_GREEN + "@VPLA                          " + ANSI_RESET + "|");
        System.out.println("| " + ANSI_GREEN + "@VCCA                          " + ANSI_RESET + "|");
        System.out.println("| " + ANSI_GREEN + "@VPCA                          " + ANSI_RESET + "|");
        System.out.println("| " + ANSI_GREEN + "@GAME gameMoveFormat           " + ANSI_RESET + "|");
        System.out.println("| " + ANSI_GREEN + "@CHAT 'nameOfReceiver' message " + ANSI_RESET + "|");
        System.out.println("| " + ANSI_CYAN + "@HELP for help                 " + ANSI_RESET + "|");

        System.out.println("+--------------------------------+");
    }

    /**
     * Used to show correct format for every command
     */
    public void help() {
        System.out.println("Input a command to know its correct usage!");
        System.out.println(">");
        String command = input.nextLine();

        switch (command.toUpperCase()) {
            case ("@CMND") -> System.out.println(ANSI_GREEN + "Used to show command table, EX: @CMND" + ANSI_RESET);
            case ("@VBOR") -> System.out.println(ANSI_GREEN + "Used to show gameboard, EX: @VBOR" + ANSI_RESET);
            case ("@VSHE") -> System.out.println(ANSI_GREEN + "Used to show your shelfie, EX: @VSHE" + ANSI_RESET);
            case ("@VPLA") ->
                    System.out.println(ANSI_GREEN + "Used to show players and their score, EX: @VPLA" + ANSI_RESET);
            case ("@VCCA") ->
                    System.out.println(ANSI_GREEN + "Used to show common cards used in match, EX: @VCCA" + ANSI_RESET);
            case ("@VPCA") ->
                    System.out.println(ANSI_GREEN + "Used to show your personal card used in match, EX: @VCCA" + ANSI_RESET);
            case ("@GAME") ->
                    System.out.println(ANSI_GREEN + "Used to do a game move, EX: @GAME (2,1),(3,0),0" + ANSI_RESET);
            case ("@CHAT") ->
                    System.out.println(ANSI_GREEN + "Used to send a chat message, EX: @CHAT 'all' Hello!" + ANSI_RESET);
            default -> System.out.println(ANSI_RED + "Unrecognized command :(" + ANSI_RESET);
        }
    }

    /**
     * Print MyShelfie logo
     */
    public void logo() {
        System.out.println(ANSI_GREEN + "  .___  ___. ____    ____         _______. __    __   _______  __       _______  __   _______    \n" +
                "  |   \\/   | \\   \\  /   /        /       ||  |  |  | |   ____||  |     |   ____||  | |   ____|   \n" +
                "  |  \\  /  |  \\   \\/   /        |   (----`|  |__|  | |  |__   |  |     |  |__   |  | |  |__      \n" +
                "  |  |\\/|  |   \\_    _/          \\   \\    |   __   | |   __|  |  |     |   __|  |  | |   __|     \n" +
                "  |  |  |  |     |  |        .----)   |   |  |  |  | |  |____ |  `----.|  |     |  | |  |____    \n" +
                "  |__|  |__|     |__|        |_______/    |__|  |__| |_______||_______||__|     |__| |_______|   \n" +
                "                                                                                                 " +
                "" + ANSI_RESET);
    }

    /**
     * Print loading animation
     */
    public void loadingScreen() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(1000);
        if (GameIsOn) return;
        System.out.println("[          ]");
        if (GameIsOn) return;

        TimeUnit.MILLISECONDS.sleep(1000);
        if (GameIsOn) return;
        System.out.println("[===       ]");
        if (GameIsOn) return;

        TimeUnit.MILLISECONDS.sleep(1000);
        if (GameIsOn) return;
        System.out.println("[=====     ]");
        if (GameIsOn) return;

        TimeUnit.MILLISECONDS.sleep(1000);
        if (GameIsOn) return;
        System.out.println("[=======   ]");
        if (GameIsOn) return;

        TimeUnit.MILLISECONDS.sleep(1000);
        if (GameIsOn) return;
        System.out.println("[==========]");
    }

    /**
     * Visualize player's board
     *
     * @param board        is playerBoard
     * @param notAvailable is notAvailable cells
     */
    @Override
    public void boardVisualizer(Tile[][] board, ArrayList<Cell> notAvailable) {
        Tile.TileColor tileColor;
        boolean notAv;

        System.out.println(ANSI_RESET + "BOARD:");
        System.out.println(ANSI_GREY + "     0  1  2  3  4  5  6  7  8"); //print column index
        System.out.println("    ---------------------------");
        for (int i = 0; i < boardRows; i++) { //loop for rows
            System.out.print(i + "  |"); //print row index
            for (int j = 0; j < boardColumns; j++) {
                notAv = false;
                for (Cell cell1 : notAvailable) { //if not Available
                    if (cell1.getRow() == i && cell1.getColumn() == j) {
                        notAv = true;
                        break;
                    }
                }
                if (notAv) {
                    System.out.print(ANSI_GREY + "   ");
                } else {
                    if (board[i][j] != null) {
                        tileColor = board[i][j].getColor();
                        System.out.print(" " + tileColor.coloredText() + "#" + " ");
                    } else {
                        System.out.print(ANSI_GREY + "   ");
                    }
                }
            }
            System.out.print(ANSI_GREY + "|\n");
        }
        System.out.println(ANSI_GREY + "    ---------------------------" + ANSI_RESET);
    }

    /**
     * Visual representation of shelfie
     *
     * @param shelfie is userShelfie
     */
    @Override
    public void shelfieVisualizer(Tile[][] shelfie) {
        Tile.TileColor tileColor;
        if (shelfie != null) {
            System.out.println(ANSI_RESET + "SHELFIE:");
            System.out.println(ANSI_GREY + "    ---------------");
            for (int i = shelfieRows - 1; i >= 0; i--) {
                System.out.print(ANSI_GREY + i + "  |");
                for (int j = 0; j < shelfieColumns; j++) {
                    if (shelfie[i][j] != null) {
                        tileColor = shelfie[i][j].getColor();
                        System.out.print(tileColor.coloredText() + " " + "#" + " ");
                    } else {
                        System.out.print(ANSI_GREY + "   ");
                    }
                }
                System.out.println(ANSI_GREY + "|");
            }
            System.out.println(ANSI_GREY + "    ---------------");
            System.out.println(ANSI_GREY + "     0  1  2  3  4" + Tile.TileColor.WHITE.coloredText());

        }
    }

    /**
     * Visual representation of common card
     *
     * @param commonCard is commonCards used during match
     */
    @Override
    public void commonCardsVisualizer(String commonCard) {
        switch (commonCard) {
            case "ColumnsOfMaxDiffTypes" -> {
                System.out.println(ANSI_BLUE + "ColumnsOfMaxDiffTypes:");
                System.out.println(ANSI_RESET + "Three columns each formed by 6 tiles of maximum three different types.");
                System.out.println("One column can show the same or a different combination of another column.");
                System.out.println(ANSI_GREY + "               +-----+");
                for (int i = 0; i < shelfieRows; i++) {
                    System.out.println(ANSI_GREY + "               | " + ANSI_GREEN + "[•]" + ANSI_GREY + " |");
                }
                System.out.println("               +-----+");
                System.out.println(ANSI_GREEN + "    x" + target_ColumnsOfMaxDiffTypes + " columns of max " + maxDiffTypes_ColumnsOfMaxDiffTypes + " different types");
            }
            case "CornersOfEquals" -> {
                System.out.println(ANSI_BLUE + "CornersOfEquals:");
                System.out.println(ANSI_RESET + "Four tiles of the same type in the four corners of the bookshelf. ");
                System.out.println(ANSI_GREY + "               +---------------+");
                System.out.print(ANSI_GREY + "               | " + ANSI_GREEN + "[=] ");
                for (int j = 0; j < shelfieColumns - 2; j++) { //2 are the corners
                    System.out.print("• ");
                }
                System.out.println("[=]" + ANSI_GREY + " |");
                for (int i = 0; i < shelfieRows - 2; i++) { //2 are the corners
                    System.out.println(ANSI_GREY + "               | " + ANSI_GREEN + " •         • " + ANSI_GREY + " |");
                }
                System.out.println(ANSI_GREY + "               | " + ANSI_GREEN + "[=] • • • [=]" + ANSI_GREY + " |");
                System.out.println(ANSI_GREY + "               +---------------+");
            }
            case "DiagonalOfEquals" -> {
                System.out.println(ANSI_BLUE + "DiagonalOfEquals:");
                System.out.println(ANSI_RESET + "Five tiles of the same type forming a diagonal.");
                System.out.println(ANSI_GREY + "               +---------------------+");
                for (int i = 0; i < shelfieColumns; i++) {
                    System.out.print(ANSI_GREY + "               | " + ANSI_GREEN);
                    for (int j = 0; j < shelfieColumns; j++) {
                        if (i != j) {
                            System.out.print("    ");
                        } else System.out.print("[=] ");
                    }
                    System.out.println(ANSI_GREY + "| ");
                }
                System.out.println(ANSI_GREY + "               +---------------------+");
            }
            case "FourGroupsOfFourEquals" -> {
                System.out.println(ANSI_BLUE + "FourGroupsOfFourEquals:");
                System.out.println(ANSI_RESET + "Four groups each containing at least 4 tiles of the same type (not necessarily in the depicted shape).");
                System.out.println("The tiles of one group can be different from those of another group.");
                System.out.println(ANSI_GREY + "               +-----+");
                for (int i = 0; i < dimension_FourGroupsOfFourEquals; i++) {
                    System.out.println(ANSI_GREY + "               | " + ANSI_GREEN + "[=]" + ANSI_GREY + " |");
                }
                System.out.println("               +-----+");
                System.out.println(ANSI_GREEN + "                 x" + groups_FourGroupsOfFourEquals);
            }
            case "NEqualTiles" -> {
                System.out.println(ANSI_BLUE + "NEqualTiles:");
                System.out.println(ANSI_RESET + "Eight tiles of the same type. There’s no restriction about the position of these tiles.");
                System.out.println(ANSI_GREY + "               +-------------+");
                System.out.println("               | " + ANSI_GREEN + "  [•] [•]" + ANSI_GREY + "   |");
                System.out.println("               | " + ANSI_GREEN + "[•] [•] [•]" + ANSI_GREY + " |");
                System.out.println("               | " + ANSI_GREEN + "[•] [•] [•]" + ANSI_GREY + " |");
                System.out.println(ANSI_GREY + "               +-------------+");
            }
            case "RowsOfMaxDiffTypes" -> {
                System.out.println(ANSI_BLUE + "RowsOfMaxDiffTypes:");
                System.out.println(ANSI_RESET + "Four lines each formed by 5 tiles of maximum three different types.");
                System.out.println("One line can show the same or a different combination of another line.");
                System.out.println(ANSI_GREY + "               +---------------------+");
                System.out.print(ANSI_GREY + "               | " + ANSI_GREEN);
                for (int i = 0; i < shelfieColumns; i++) {
                    System.out.print("[•] ");
                }
                System.out.println(ANSI_GREY + "|");
                System.out.println("               +---------------------+");
                System.out.println(ANSI_GREEN + "          x" + target_RowsOfMaxDiffTypes + " rows of max " + maxDiffTypes_RowsOfMaxDiffTypes + " different types");
            }
            case "SixGroupsOfTwoEquals" -> {
                System.out.println(ANSI_BLUE + "SixGroupsOfTwoEquals:");
                System.out.println(ANSI_RESET + "Six groups each containing at least 2 tiles of the same type (not necessarily in the depicted shape).");
                System.out.println("The tiles of one group can be different from those of another group.");
                System.out.println(ANSI_GREY + "               +-----+");
                for (int i = 0; i < dimension_SixGroupsOfTwoEquals; i++) {
                    System.out.println(ANSI_GREY + "               | " + ANSI_GREEN + "[=]" + ANSI_GREY + " |");
                }
                System.out.println("               +-----+");
                System.out.println(ANSI_GREEN + "                 x" + groups_SixGroupsOfTwoEquals);
            }
            case "Stairs" -> {
                System.out.println(ANSI_BLUE + "Stairs:");
                System.out.println(ANSI_RESET + "Five columns of increasing or decreasing height.");
                System.out.println("Starting from the first column on the left or on the right, each next column must be made of exactly one more tile.");
                System.out.println("Tiles can be of any type.");
                System.out.println(ANSI_GREY + "               +---------------------+");
                for (int i = 0; i < shelfieColumns; i++) {
                    System.out.print(ANSI_GREY + "               | " + ANSI_GREEN);
                    for (int j = 0; j < shelfieColumns; j++) {
                        if (j <= i) {
                            System.out.print("[•] ");
                        } else System.out.print("    ");
                    }
                    System.out.println(ANSI_GREY + "| ");
                }
                System.out.println(ANSI_GREY + "               +---------------------+");
            }
            case "TwoColumnsOfSixDifferent" -> {
                System.out.println(ANSI_BLUE + "TwoColumnsOfSixDifferent:");
                System.out.println(ANSI_RESET + "Two columns each formed by 6 different types of tiles.");
                System.out.println(ANSI_GREY + "               +-----+");
                for (int i = 0; i < shelfieRows; i++) {
                    System.out.println(ANSI_GREY + "               | " + ANSI_GREEN + "[≠]" + ANSI_GREY + " |");
                }
                System.out.println(ANSI_GREY + "               +-----+");
                System.out.println(ANSI_GREEN + "                 x" + target_TwoColumnsOfSixDifferent);
            }
            case "TwoRowsOfFiveDifferent" -> {
                System.out.println(ANSI_BLUE + "TwoRowsOfFiveDifferent:");
                System.out.println(ANSI_RESET + "Two lines each formed by 5 different types of tiles.");
                System.out.println("One line can show the same or a different combination of the other line.");
                System.out.println(ANSI_GREY + "               +---------------------+");
                System.out.print(ANSI_GREY + "               | " + ANSI_GREEN);
                for (int i = 0; i < shelfieColumns; i++) {
                    System.out.print("[≠] ");
                }
                System.out.println(ANSI_GREY + "|");
                System.out.println(ANSI_GREY + "               +---------------------+");
                System.out.println(ANSI_GREEN + "                         x" + target_TwoRowsOfFiveDifferent);
            }
            case "TwoSquaresOfEquals" -> {
                System.out.println(ANSI_BLUE + "TwoSquaresOfEquals:");
                System.out.println(ANSI_RESET + "Two groups each containing 4 tiles of the same type in a 2x2 square.");
                System.out.println("The tiles of one square can be different from those of the other square.");
                System.out.println(ANSI_GREY + "               +---------+");
                System.out.println(ANSI_GREY + "               | " + ANSI_GREEN + "[=] [=]" + ANSI_GREY + " |");
                System.out.println(ANSI_GREY + "               | " + ANSI_GREEN + "[=] [=]" + ANSI_GREY + " |");
                System.out.println(ANSI_GREY + "               +---------+");
                System.out.println(ANSI_GREEN + "                   x" + groups_TwoSquaresOfEquals);
            }
            case "XOfEquals" -> {
                System.out.println(ANSI_BLUE + "XOfEquals:");
                System.out.println(ANSI_RESET + "Five tiles of the same type forming an X.");
                System.out.println(ANSI_GREY + "               +-------------+");
                System.out.println(ANSI_GREY + "               | " + ANSI_GREEN + "[=]     [=]" + ANSI_GREY + " |");
                System.out.println(ANSI_GREY + "               | " + ANSI_GREEN + "    [=]    " + ANSI_GREY + " |");
                System.out.println(ANSI_GREY + "               | " + ANSI_GREEN + "[=]     [=]" + ANSI_GREY + " |");
                System.out.println(ANSI_GREY + "               +-------------+");
            }
        }

    }

    @Override
    public void commonCardsVisualizer(CommonCards commonCard) {
    }

    /**
     * Visual representation of personal card
     *
     * @param personalCard it's user personal card
     */
    @Override
    public void personalCardVisualizer(PersonalCards personalCard) {
        boolean tile;
        System.out.println(ANSI_GREY + "    ---------------");
        for (int i = shelfieRows - 1; i >= 0; i--) {
            System.out.print(ANSI_GREY + i + "  |");
            for (int j = 0; j < shelfieColumns; j++) {
                tile = false;
                for (PersonalCards.ObjectiveCell objectiveCell : personalCard.getObjective()) {
                    if (objectiveCell.getRow() == i && objectiveCell.getColumn() == j) {
                        System.out.print(objectiveCell.getColor().coloredText() + " # ");
                        tile = true;
                    }
                }
                if (!tile) {
                    System.out.print(ANSI_GREY + "   ");
                }
            }
            System.out.println(ANSI_GREY + "|");
        }
        System.out.println(ANSI_GREY + "    ---------------");
        System.out.println(ANSI_GREY + "     0  1  2  3  4" + Tile.TileColor.WHITE.coloredText());
    }

    /**
     * Chat visualizer
     *
     * @param jsonMessage is received message
     */
    @Override
    public void chatVisualizer(JSONObject jsonMessage) {
        ChatParser chatParser = new ChatParser();
        chatParser.chatParser(jsonMessage.toJSONString());
        System.out.println(chatParser.getRequester() + ": " + chatParser.getMessage());
    }

    @Override
    public void scoreVisualizer(ArrayList<Player> players) { //player and score
        for (Player player : players) {
            System.out.println(player.getNickname() + ": " + player.getCurrentScore());
        }
    }

    /**
     * Called by messageHandler or actionToJSON with various event
     *
     * @param event is received event fired by MessageHandler
     */
    public void eventManager(String event) {
        switch (event) {
            //Client-side errors
            case ("chatError") -> System.out.println("Error in chat message syntax, try again!");
            case ("gameMoveError") -> System.out.println("Error in game move syntax, try again!");

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
            }

            //Servers-side errors
            case (NICKNAME_NOT_UNIQUE) -> {
                System.out.println(NICKNAME_NOT_UNIQUE);
                okNickname = false;
            }
            case (JOINING_LOBBY) -> System.out.println(JOINING_LOBBY);
            case (PLAYER_DISCONNECTED) -> System.out.println(PLAYER_DISCONNECTED);
            case (INVALID_NUMBER_OF_PLAYERS) -> System.out.println(INVALID_NUMBER_OF_PLAYERS);
            case (INVALID_CHOICE_OF_TILES) -> System.out.println(INVALID_CHOICE_OF_TILES);
            case (INVALID_COLUMN) -> System.out.println(INVALID_COLUMN);
            case (INVALID_RECEIVER) -> System.out.println(INVALID_RECEIVER);
            case (ALREADY_LOGGED_IN) -> System.out.println(ALREADY_LOGGED_IN);
            case (YOU_ARE_RECEIVER) -> System.out.println(YOU_ARE_RECEIVER);
            case (ERROR_IN_GAMEMOVE) -> System.out.println((ERROR_IN_GAMEMOVE));
            case (ALL_PLAYERS_DISCONNECTED) -> System.out.println(ALL_PLAYERS_DISCONNECTED);

            default -> System.out.println("Unrecognized event!");
        }
    }

    /**
     * Called when game is end, show player scoreboard and winner
     */
    public void endGame(boolean winner) {
        //End game
        GameIsOn = false;
        System.out.println("Game is ended!");

        if (winner) {
            System.out.println(ANSI_GREEN + "██    ██  ██████  ██    ██     ██     ██  ██████  ███    ██ ██ \n" +
                    " ██  ██  ██    ██ ██    ██     ██     ██ ██    ██ ████   ██ ██ \n" +
                    "  ████   ██    ██ ██    ██     ██  █  ██ ██    ██ ██ ██  ██ ██ \n" +
                    "   ██    ██    ██ ██    ██     ██ ███ ██ ██    ██ ██  ██ ██    \n" +
                    "   ██     ██████   ██████       ███ ███   ██████  ██   ████ ██ \n" +
                    "                                                               \n" +
                    "                                                               \n" +
                    "\n" +
                    "                                                                                 " + ANSI_RESET);
        } else {
            System.out.println(ANSI_RED + "██    ██  ██████  ██    ██     ██       ██████  ███████ ████████ ██ \n" +
                    " ██  ██  ██    ██ ██    ██     ██      ██    ██ ██         ██    ██ \n" +
                    "  ████   ██    ██ ██    ██     ██      ██    ██ ███████    ██    ██ \n" +
                    "   ██    ██    ██ ██    ██     ██      ██    ██      ██    ██       \n" +
                    "   ██     ██████   ██████      ███████  ██████  ███████    ██    ██ \n" +
                    "                                                                    \n" +
                    "                                                                    " + ANSI_RESET);
        }

    }

    ///////////////////
    //UPDATER METHODS//
    ///////////////////

    /**
     * Called by RMI or Socket for board update
     *
     * @param b is new Board
     */
    @Override
    public void boardUpdater(GameBoard b) {
        this.UserGameBoard = b;
    }

    /**
     * Called by RMI or Socket for shelfie update
     *
     * @param s is new Shelfie
     */
    @Override
    public void shelfieUpdater(Shelfie s) {
        this.UserShelfie = s;
    }

    @Override
    public void shelfieUpdater(Shelfie s, String nickname) {
    }

    /**
     * Called by RMI or Socket for score update
     *
     * @param s is new score
     */
    @Override
    public void scoreUpdater(int s) {
        this.UserScore = s;
    }

    /**
     * Called by RMI or Socket for personal car update -> only at the beginning
     *
     * @param pc is personal card
     */
    @Override
    public void personalCardUpdater(PersonalCards pc) {
        this.PersonalCard = pc;
    }

    /**
     * Update common card
     *
     * @param nameOfCommonCard is name of common card
     * @param whatCommonCard   is 1 or 2 depending on card updated
     */
    @Override
    public void commonCardUpdater(String nameOfCommonCard, int whatCommonCard) {
        if (whatCommonCard == 1)
            this.CommonCard1 = nameOfCommonCard;
        else if (whatCommonCard == 2)
            this.CommonCard2 = nameOfCommonCard;
    }

    @Override
    public void setServerAddress(String serverAddress) {
        ServerAddress = serverAddress;
    }

    @Override
    public void setPort(int port) {
        Port = port;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setStartGame(int startGame) {
        StartGame = startGame;
    }
}
