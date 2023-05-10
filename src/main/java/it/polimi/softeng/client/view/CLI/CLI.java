package it.polimi.softeng.client.view.CLI;

import it.polimi.softeng.JSONParser.ChatParser;
import it.polimi.softeng.JSONParser.GameMoveParser;
import it.polimi.softeng.JSONWriter.ChatWriter;
import it.polimi.softeng.JSONWriter.ClientSignatureWriter;
import it.polimi.softeng.JSONWriter.GameMoveWriter;
import it.polimi.softeng.JSONWriter.LoginWriter;
import it.polimi.softeng.client.view.MessageHandler;
import it.polimi.softeng.connectionProtocol.ClientSide;
import it.polimi.softeng.customExceptions.IllegalInsertException;
import it.polimi.softeng.model.*;
import it.polimi.softeng.client.view.CommonOperationsFramework;
import it.polimi.softeng.client.view.UI;
import it.polimi.softeng.model.PersonalCards;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static it.polimi.softeng.Constants.*;
import static it.polimi.softeng.JSONWriter.ClientSignatureWriter.clientSignObject;

public class CLI extends CommonOperationsFramework implements UI, Runnable {
    /**
     * Local representation of GameBoard
     */
    private GameBoard UserGameBoard;

    /**
     * Local representation of Shelfie
     */
    private Shelfie UserShelfie;

    /**
     * Player information
     */
    private int UserScore;
    private PersonalCards PersonalCard;
    private String Nickname;
    private String CommonCard1 = null;
    private String CommonCard2 = null;
    private String ServerAddress;
    private int Port;

    /**
     * 1 -> player want to create new game (FA: multi-game management)
     * 2 -> player want to join (if present) a game which is already started
     */
    private int StartGame;

    /**
     * 1 -> Easy Mode (only one Common Card is used the game)
     * 2 -> Normal Mode (two Common Cards are used during the game)
     */
    private int GameMode;

    /**
     * NumOfPlayer for current game
     */
    private int NumOfPlayer;

    /**
     * 1 -> Connected with Socket
     * 2 -> Connected with RMI
     */
    private int ConnectionMode = 0;

    private volatile boolean GameIsOn;
    private final Scanner input;

    private MessageHandler messageHandler;

    /**
     * Manage sending messages
     */
    private ClientSide clientSide;


    public CLI() {
        this.messageHandler = new MessageHandler(this);
        this.input = new Scanner(System.in);
    }


    /**
     * CLI initialization, connection to server, choose of gameMode
     * After setup CLI is ready to be used
     */
    public void setupCLI() {
        int mode;

        System.out.println("Initializing CLI...");
        System.out.println("Do you want to connect using Socket(1) or RMI(2)?");
        System.out.println(">");
        do {
            ConnectionMode = Integer.parseInt(input.nextLine());

            switch (ConnectionMode) {
                case 1 -> { //socket
                    System.out.println("Connection with Socket...");
                    System.out.println("Digit server IP");
                    System.out.println(">");
                    ServerAddress = input.nextLine();
                    System.out.println("Digit server Port");
                    System.out.println(">");
                    Port = input.nextInt();
                    input.nextLine();
                    System.out.println("Do you want to create a new game(1) or join a game which is already started(2)?");
                    System.out.println("If you want to reconnect to a previous game choose 2 and use the same nickname");
                    System.out.println(">");

                    do {
                        StartGame = input.nextInt();
                        input.nextLine();
                    } while (StartGame != 1 && StartGame != 2);
                    if (StartGame == 1) {
                        do {
                            System.out.println("Insert the number of players(2-4)");
                            System.out.println(">");
                            NumOfPlayer = input.nextInt();
                            input.nextLine();
                        } while (NumOfPlayer < 2 || NumOfPlayer > 4);

                        System.out.println("Do you want to play with Easy mode(1) or Normal mode(2)?");
                        System.out.println(">");
                        do {
                            GameMode = input.nextInt();
                            input.nextLine();
                        } while (GameMode != 1 && GameMode != 2);
                    }

                    //TODO nicknameUniqueness
                    //do {
                    do {
                        System.out.println("Insert nickname (ONLY characters a-z A-Z 0-9 and _ allowed)");
                        System.out.println(">");
                        Nickname = input.nextLine();

                        //TODO LoginWriter...
                    } while (!isOkNickname());

                    String login = ClientSignatureWriter.clientSignObject(LoginWriter.writeLogin(Nickname, GameMode, StartGame, NumOfPlayer), "@LOGN", Nickname).toJSONString();
                    System.out.println(login);
                    this.clientSide = new ClientSide(messageHandler);
                    clientSide.sendMessage(login);
                }


                //}while(nicknameNotUnique())
                case 2 -> {//RMI
                    System.out.println("Connection with RMI...");
                    System.out.println("Digit server IP");
                    System.out.println(">");
                    ServerAddress = input.nextLine();
                    System.out.println("Digit server Port");
                    System.out.println(">");
                    Port = input.nextInt();
                    input.nextLine();
                    System.out.println("Do you want to create a new game(1) or join a game which is already started(2)?");
                    System.out.println("If you want to reconnect to a previous game choose 2 and use the same nickname");
                    System.out.println(">");
                    do {
                        StartGame = input.nextInt();
                        input.nextLine();
                    } while (StartGame != 1 && StartGame != 2);
                    if (StartGame == 1) {
                        System.out.println("Insert the number of players(2-4)?");
                        System.out.println(">");
                        do {
                            NumOfPlayer = input.nextInt();
                            input.nextLine();

                            if (NumOfPlayer > 4 || NumOfPlayer < 2)
                                eventManager(INVALID_NUMBER_OF_PLAYERS);
                        } while (NumOfPlayer > 4 || NumOfPlayer < 2);

                        System.out.println("Do you want to play with Easy mode(1) or Normal mode(2)?");
                        System.out.println(">");
                        do {
                            GameMode = input.nextInt();
                            input.nextLine();
                        } while (GameMode != 1 && GameMode != 2);
                    }

                    //do {
                    System.out.println("Insert nickname");
                    Nickname = input.nextLine();
                }
                //Connect to server
                //}while(nicknameNotUnique)
                default -> System.out.println("Unrecognized connection method, please digit 1 or 2...");
            }
        } while (ConnectionMode != 1 && ConnectionMode != 2);
    }


    /**
     * Visualize player's board
     * @param board is playerBoard
     * @param notAvailable is notAvailable cells
     */
    @Override
    public void boardVisualizer(Tile[][] board, ArrayList<Cell> notAvailable) {
        Tile.TileColor tileColor;
        boolean notAv;

        System.out.println(ANSI_RESET + "BOARD:");
        System.out.println(ANSI_GREY + "     0  1  2  3  4  5  6  7  8"); //print column index
        System.out.println("    ━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        for (int i = 0; i < boardRows; i++) { //loop for rows
            System.out.print(i + "  ┃"); //print row index
            for (int j = 0; j < boardColumns; j++) {
                notAv = false;
                for (Cell cell1 : notAvailable) { //if not Available
                    if (cell1.getRow() == i && cell1.getColumn() == j) {
                        notAv = true;
                        break;
                    }
                }
                if (notAv) {
                    System.out.print(ANSI_GREY + " ░ ");
                } else {
                    if (board[i][j] != null) {
                        tileColor = board[i][j].getColor();
                        System.out.print(" " + tileColor.coloredText() + "▇" + " ");
                    } else {
                        System.out.print(ANSI_GREY + " ░ ");
                    }
                }
            }
            System.out.print(ANSI_GREY + "┃\n");
        }
        System.out.println(ANSI_GREY + "    ━━━━━━━━━━━━━━━━━━━━━━━━━━━" + ANSI_RESET);
    }

    /**
     * @param shelfie is userShelfie
     *                Visual representation of shelfie
     */
    @Override
    public void shelfieVisualizer(Tile[][] shelfie) {
        Tile.TileColor tileColor;
        if (shelfie != null) {
            System.out.println(ANSI_RESET + "SHELFIE:");
            System.out.println(ANSI_GREY + "    ━━━━━━━━━━━━━━━");
            for (int i = shelfieRows - 1; i >= 0; i--) {
                System.out.print(ANSI_GREY + i + "  ┃");
                for (int j = 0; j < shelfieColumns; j++) {
                    if (shelfie[i][j] != null) {
                        tileColor = shelfie[i][j].getColor();
                        System.out.print(tileColor.coloredText() + " " + "▇" + " ");
                    } else {
                        System.out.print(ANSI_GREY + " ░ ");
                    }
                }
                System.out.println(ANSI_GREY + "┃");
            }
            System.out.println(ANSI_GREY + "    ━━━━━━━━━━━━━━━");
            System.out.println(ANSI_GREY + "     0  1  2  3  4" + Tile.TileColor.WHITE.coloredText());

        }
    }

    /**
     * @param commonCard is commonCards used during match
     */
    @Override
    public void commonCardsVisualizer(String commonCard) {
        switch (commonCard) {
            case "ColumnsOfMaxDiffTypes" -> {
                System.out.println(ANSI_BLUE + "ColumnsOfMaxDiffTypes:");
                System.out.println(ANSI_RESET + "Three columns each formed by 6 tiles of maximum three different types.");
                System.out.println("One column can show the same or a different combination of another column.");
                System.out.println(ANSI_GREY + "+-----+");
                for (int i = 0; i < shelfieRows; i++) {
                    System.out.println(ANSI_GREY + "| " + ANSI_GREEN + "[•]" + ANSI_GREY + " |");
                }
                System.out.println("+-----+");
                System.out.println(ANSI_GREEN + "x" + target_ColumnsOfMaxDiffTypes + " columns of max " + maxDiffTypes_ColumnsOfMaxDiffTypes + " different types");
            }
            case "CornersOfEquals" -> {
                System.out.println(ANSI_BLUE + "CornersOfEquals:");
                System.out.println(ANSI_RESET + "Four tiles of the same type in the four corners of the bookshelf. ");
                System.out.println(ANSI_GREY + "+---------------+");
                System.out.print(ANSI_GREY + "| " + ANSI_GREEN + "[=] ");
                for (int j = 0; j < shelfieColumns - 2; j++) { //2 are the corners
                    System.out.print("• ");
                }
                System.out.println("[=]" + ANSI_GREY + " |");
                for (int i = 0; i < shelfieRows - 2; i++) { //2 are the corners
                    System.out.println(ANSI_GREY + "| " + ANSI_GREEN + " •         • " + ANSI_GREY + " |");
                }
                System.out.println(ANSI_GREY + "| " + ANSI_GREEN + "[=] • • • [=]" + ANSI_GREY + " |");
                System.out.println(ANSI_GREY + "+---------------+");
            }
            case "DiagonalOfEquals" -> {
                System.out.println(ANSI_BLUE + "DiagonalOfEquals:");
                System.out.println(ANSI_RESET + "Five tiles of the same type forming a diagonal.");
                System.out.println(ANSI_GREY + "+---------------------+");
                for (int i = 0; i < shelfieColumns; i++) {
                    System.out.print(ANSI_GREY + "| " + ANSI_GREEN);
                    for (int j = 0; j < shelfieColumns; j++) {
                        if (i != j) {
                            System.out.print("    ");
                        } else System.out.print("[=] ");
                    }
                    System.out.println(ANSI_GREY + "| ");
                }
                System.out.println(ANSI_GREY + "+---------------------+");
            }
            case "FourGroupsOfFourEquals" -> {
                System.out.println(ANSI_BLUE + "FourGroupsOfFourEquals:");
                System.out.println(ANSI_RESET + "Four groups each containing at least 4 tiles of the same type (not necessarily in the depicted shape).");
                System.out.println("The tiles of one group can be different from those of another group.");
                System.out.println(ANSI_GREY + "+-----+");
                for (int i = 0; i < dimension_FourGroupsOfFourEquals; i++) {
                    System.out.println(ANSI_GREY + "| " + ANSI_GREEN + "[=]" + ANSI_GREY + " |");
                }
                System.out.println("+-----+");
                System.out.println(ANSI_GREEN + "  x" + groups_FourGroupsOfFourEquals);
            }
            case "NEqualTiles" -> {
                System.out.println(ANSI_BLUE + "NEqualTiles:");
                System.out.println(ANSI_RESET + "Eight tiles of the same type. There’s no restriction about the position of these tiles.");
                System.out.println(ANSI_GREY + "+-------------+");
                System.out.println("| " + ANSI_GREEN + "  [•] [•]" + ANSI_GREY + "   |");
                System.out.println("| " + ANSI_GREEN + "[•] [•] [•]" + ANSI_GREY + " |");
                System.out.println("| " + ANSI_GREEN + "[•] [•] [•]" + ANSI_GREY + " |");
                System.out.println(ANSI_GREY + "+-------------+");
            }
            case "RowsOfMaxDiffTypes" -> {
                System.out.println(ANSI_BLUE + "RowsOfMaxDiffTypes:");
                System.out.println(ANSI_RESET + "Four lines each formed by 5 tiles of maximum three different types.");
                System.out.println("One line can show the same or a different combination of another line.");
                System.out.println(ANSI_GREY + "+---------------------+");
                System.out.print(ANSI_GREY + "| " + ANSI_GREEN);
                for (int i = 0; i < shelfieColumns; i++) {
                    System.out.print("[•] ");
                }
                System.out.println(ANSI_GREY + "|");
                System.out.println("+---------------------+");
                System.out.println(ANSI_GREEN + "x" + target_RowsOfMaxDiffTypes + " rows of max " + maxDiffTypes_RowsOfMaxDiffTypes + " different types");
            }
            case "SixGroupsOfTwoEquals" -> {
                System.out.println(ANSI_BLUE + "SixGroupsOfTwoEquals:");
                System.out.println(ANSI_RESET + "Six groups each containing at least 2 tiles of the same type (not necessarily in the depicted shape).");
                System.out.println("The tiles of one group can be different from those of another group.");
                System.out.println(ANSI_GREY + "+-----+");
                for (int i = 0; i < dimension_SixGroupsOfTwoEquals; i++) {
                    System.out.println(ANSI_GREY + "| " + ANSI_GREEN + "[=]" + ANSI_GREY + " |");
                }
                System.out.println("+-----+");
                System.out.println(ANSI_GREEN + "  x" + groups_SixGroupsOfTwoEquals);
            }
            case "Stairs" -> {
                System.out.println(ANSI_BLUE + "Stairs:");
                System.out.println(ANSI_RESET + "Five columns of increasing or decreasing height.");
                System.out.println("Starting from the first column on the left or on the right, each next column must be made of exactly one more tile.");
                System.out.println("Tiles can be of any type.");
                System.out.println(ANSI_GREY + "+---------------------+");
                for (int i = 0; i < shelfieColumns; i++) {
                    System.out.print(ANSI_GREY + "| " + ANSI_GREEN);
                    for (int j = 0; j < shelfieColumns; j++) {
                        if (j <= i) {
                            System.out.print("[•] ");
                        } else System.out.print("    ");
                    }
                    System.out.println(ANSI_GREY + "| ");
                }
                System.out.println(ANSI_GREY + "+---------------------+");
            }
            case "TwoColumnsOfSixDifferent" -> {
                System.out.println(ANSI_BLUE + "TwoColumnsOfSixDifferent:");
                System.out.println(ANSI_RESET + "Two columns each formed by 6 different types of tiles.");
                System.out.println(ANSI_GREY + "+-----+");
                for (int i = 0; i < shelfieRows; i++) {
                    System.out.println(ANSI_GREY + "| " + ANSI_GREEN + "[≠]" + ANSI_GREY + " |");
                }
                System.out.println(ANSI_GREY + "+-----+");
                System.out.println(ANSI_GREEN + "  x" + target_TwoColumnsOfSixDifferent);
            }
            case "TwoRowsOfFiveDifferent" -> {
                System.out.println(ANSI_BLUE + "TwoRowsOfFiveDifferent:");
                System.out.println(ANSI_RESET + "Two lines each formed by 5 different types of tiles.");
                System.out.println("One line can show the same or a different combination of the other line.");
                System.out.println(ANSI_GREY + "+---------------------+");
                System.out.print(ANSI_GREY + "| " + ANSI_GREEN);
                for (int i = 0; i < shelfieColumns; i++) {
                    System.out.print("[≠] ");
                }
                System.out.println(ANSI_GREY + "|");
                System.out.println(ANSI_GREY + "+---------------------+");
                System.out.println(ANSI_GREEN + "          x" + target_TwoRowsOfFiveDifferent);
            }
            case "TwoSquaresOfEquals" -> {
                System.out.println(ANSI_BLUE + "TwoSquaresOfEquals:");
                System.out.println(ANSI_RESET + "Two groups each containing 4 tiles of the same type in a 2x2 square.");
                System.out.println("The tiles of one square can be different from those of the other square.");
                System.out.println(ANSI_GREY + "+---------+");
                System.out.println(ANSI_GREY + "| " + ANSI_GREEN + "[=] [=]" + ANSI_GREY + " |");
                System.out.println(ANSI_GREY + "| " + ANSI_GREEN + "[=] [=]" + ANSI_GREY + " |");
                System.out.println(ANSI_GREY + "+---------+");
                System.out.println(ANSI_GREEN + "    x" + groups_TwoSquaresOfEquals);
            }
            case "XOfEquals" -> {
                System.out.println(ANSI_BLUE + "XOfEquals:");
                System.out.println(ANSI_RESET + "Five tiles of the same type forming an X.");
                System.out.println(ANSI_GREY + "+-------------+");
                System.out.println(ANSI_GREY + "| " + ANSI_GREEN + "[=]     [=]" + ANSI_GREY + " |");
                System.out.println(ANSI_GREY + "| " + ANSI_GREEN + "    [=]    " + ANSI_GREY + " |");
                System.out.println(ANSI_GREY + "| " + ANSI_GREEN + "[=]     [=]" + ANSI_GREY + " |");
                System.out.println(ANSI_GREY + "+-------------+");
            }
        }

    }

    @Override
    public void personalCardVisualizer(PersonalCards personalCard) {
        boolean tile;
        System.out.println(ANSI_GREY + "    ━━━━━━━━━━━━━━━");
        for (int i = shelfieRows - 1; i >= 0; i--) {
            System.out.print(ANSI_GREY + i + "  ┃");
            for (int j = 0; j < shelfieColumns; j++) {
                tile = false;
                for (PersonalCards.ObjectiveCell objectiveCell : personalCard.getObjective()) {
                    if (objectiveCell.getRow() == i && objectiveCell.getColumn() == j) {
                        System.out.print(objectiveCell.getColor().coloredText() + " ▇ ");
                        tile = true;
                    }
                }
                if (!tile) {
                    System.out.print(ANSI_GREY + " ░ ");
                }
            }
            System.out.println(ANSI_GREY + "┃");
        }
        System.out.println(ANSI_GREY + "    ━━━━━━━━━━━━━━━");
        System.out.println(ANSI_GREY + "     0  1  2  3  4" + Tile.TileColor.WHITE.coloredText());
    }

    @Override
    public void chatVisualizer(JSONObject jsonMessage) {
        ChatParser chatParser = new ChatParser();
        chatParser.chatParser(jsonMessage.toJSONString());
        System.out.println(chatParser.getRequester() + ": " + chatParser.getMessage());
    }

    @Override
    public void scoreVisualizer(ArrayList<Player> players) { //player and score
        CommandLineTable scoreTable = new CommandLineTable();
        //st.setRightAlign(true);//if true then cell text is right aligned
        scoreTable.setShowVerticalLines(true);//if false (default) then no vertical lines are shown
        scoreTable.setHeaders("Player", "Score");//optional - if not used then there will be no header and horizontal lines
        for (Player player : players) {
            scoreTable.addRow(player.getNickname(), Integer.toString(player.getCurrentScore()));
        }
        scoreTable.print();
    }

    /**
     * Notify CLI when game begins
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
        GameIsOn = false;

        System.out.println("Waiting for other players to join...");
        //Waiting for beginning of game
        while (!GameIsOn) {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
                if(GameIsOn) break;
                System.out.println("[          ]");
                if(GameIsOn) break;

                TimeUnit.MILLISECONDS.sleep(500);
                if(GameIsOn) break;
                System.out.println("[===       ]");
                if(GameIsOn) break;

                TimeUnit.MILLISECONDS.sleep(500);
                if(GameIsOn) break;
                System.out.println("[=====     ]");
                if(GameIsOn) break;

                TimeUnit.MILLISECONDS.sleep(500);
                if(GameIsOn) break;
                System.out.println("[=======   ]");
                if(GameIsOn) break;

                TimeUnit.MILLISECONDS.sleep(500);
                if(GameIsOn) break;
                System.out.println("[==========]");
                if(GameIsOn) break;

                System.out.println("Waiting for other players to join...");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        while (GameIsOn) {
            try {
                //Wait for errors
                TimeUnit.SECONDS.sleep(1);
                game(firstRun);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            firstRun = false;
        }

        input.close();
    }

    /**
     * Game routine that wait for commands, check syntax and send JSON/invoke method to server if needed
     * @param firstRun is true if it's the first call of game
     */
    public void game(boolean firstRun) {
        //POSSIBLE COMMANDS
        commands(firstRun);

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

        if (command.toUpperCase().equals("@CMND") || command.toUpperCase().equals("@VBOR") || command.toUpperCase().equals("@VSHE") || command.toUpperCase().equals("@VPCA") || command.toUpperCase().equals("@VPLA") || command.toUpperCase().equals("@VCCA")) {
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
                    //Placeholder for JSON request
                    JSONObject dummy = new JSONObject();
                    clientSide.sendMessage(clientSignObject(dummy, "@VPLA", Nickname).toJSONString());
                    return;
                }

                //TODO RMIInvoker for players and their score
                //TODO return;
            }
        }

        //Command is in @CMND format (every command is 4 letters), uppercase avoid case sensitivity
        String op = command.substring(0, 5).toUpperCase();
        String action = command.substring(6);

        if (!op.equals("@GAME") && !op.equals("@CHAT")) {
            System.out.println("Please write a command that you can see in the table");
            return;
        }

        switch (ConnectionMode) {
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
                JSONObject toBeSent = actionToJSON(op, action);

                //Send message to server
                if (toBeSent != null)
                    clientSide.sendMessage(clientSignObject(toBeSent, op, Nickname).toJSONString());
            }


            case 2 -> {
                if (op.equals("@GAME")) {
                    if (!isOkCommand(command, 3)) {
                        System.out.println("Please, check gameMove syntax");
                        return;
                    }

                    if (op.equals("@CHAT")) {
                        if (!isOkCommand(command, 2)) {
                            System.out.println("Please, check chat syntax");
                            return;
                        }
                    }
                }
                //TODO RMI Invoker
                RMIInvoker(op, action);
            }
        }
    }


    /**
     * @param firstRun Print all possible commands doable by user eventually with MyShelfie logo (only at CLI first start)
     */
    public void commands(boolean firstRun) {
        if (firstRun)
            logo();

        CommandLineTable scoreTable = new CommandLineTable();
        scoreTable.setShowVerticalLines(true);
        scoreTable.setHeaders("Command", "Effect", "Example of command");


        System.out.println("+--------------------------------+-----------------------------------------------------------------------------------+----------------------------------------------------------------------------------------------------+");
        System.out.println("| Command                        | Effect                                                                            | Example                                                                                            |");
        System.out.println("+--------------------------------+-----------------------------------------------------------------------------------+----------------------------------------------------------------------------------------------------+");

        System.out.println("| " + ANSI_GREEN + "@CMND                          " + ANSI_RESET + "| To show command table again                                                       | @CMND                                                                                              |");
        System.out.println("| " + ANSI_GREEN + "@VBOR                          " + ANSI_RESET + "| Visualize board status                                                            | @VBOR                                                                                              |");
        System.out.println("| " + ANSI_GREEN + "@VSHE                          " + ANSI_RESET + "| Visualize shelfie status                                                          | @VSHE                                                                                              |");
        System.out.println("| " + ANSI_GREEN + "@VPLA                          " + ANSI_RESET + "| Visualize currently connected players and score                                   | @VPLA                                                                                              |");
        System.out.println("| " + ANSI_GREEN + "@VCCA                          " + ANSI_RESET + "| Visualize common objectives                                                       | @VCCA                                                                                              |");
        System.out.println("| " + ANSI_GREEN + "@VPCA                          " + ANSI_RESET + "| Visualize personal objectives                                                     | @VPCA                                                                                              |");
        System.out.println("| " + ANSI_GREEN + "@GAME gameMoveFormat           " + ANSI_RESET + "| Do a game move                                                                    | @GAME (rowTile1,columnTil1),(rowTile2,columnTil2),(rowTile3,columnTil3),numColumnOfInsertion       |");
        System.out.println("| " + ANSI_GREEN + "--------------------           " + ANSI_RESET + "| --------------                                                                    | EX: (5,5),(5,6),2                                                                                  |");
        System.out.println("| " + ANSI_GREEN + "--------------------           " + ANSI_RESET + "| --------------                                                                    | You must select at least 1 but less than 3 (included), picking order is inserting order in shelfie |");
        System.out.println("| " + ANSI_GREEN + "@CHAT 'nameOfReceiver' message " + ANSI_RESET + "| Send a chat message (to send a message to everybody type 'all' in nameOfReceiver) | @CHAT 'userRec' HI!                                                                                |");

        System.out.println("+--------------------------------+-----------------------------------------------------------------------------------+----------------------------------------------------------------------------------------------------+");
    }

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

    /**
     * @param op     is command
     * @param action is command text sent by UI
     * @return a JSONObject containing encoded action
     */
    public JSONObject actionToJSON(String op, String action) {
        switch (op) {
            case ("@CHAT"): {
                if (!ChatWriter.chatMessageRegex(action) || ChatWriter.writeChatMessage(action) == null) {
                    eventManager("chatError");
                    return null;
                } else
                    return ChatWriter.writeChatMessage(action);
            }
            case ("@GAME"): {
                if (!GameMoveWriter.gameMoveRegex(action) || GameMoveWriter.writeGameMove(action) == null) {
                    eventManager("gameMoveError");
                    return null;
                } else
                    return GameMoveWriter.writeGameMove(action);
            }
            //TODO check login dynamic
            case ("@LOGN"): {

            }
            break;

            default:
                System.out.println("Unrecognized operation!");
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
            case ("@CHAT"): {
                if (!ChatWriter.chatMessageRegex(action)) {
                    System.out.println("Error in Chat message syntax, try again!");
                    return false;
                }
                //TODO InvokeMethodToChat
            }
            case ("@GAME"): {
                if (!GameMoveWriter.gameMoveRegex(action)) {
                    System.out.println("Error in Game Move syntax, try again!");
                    return false;
                }
                //TODO InvokeMethodToGameMove
            }

            case ("@LOGN"): {
                //sendLoginRequest(action);
            }
            //TODO InvokeLogin
            case ("@VCCA"): {

            }
            case ("@VPLA"): {

            }
            case ("@VSCO"): {

            }
            break;
            default:
                System.out.println("Unrecognized operation!");
        }

        System.out.println("Test");
        return true;
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
            case ("chatEvent") -> System.out.println("Received chat message");
            case ("globalChatEvent") -> System.out.println("Received global chat message");
            case ("boardEvent") -> System.out.println("Received board update");
            case ("shelfieEvent") -> System.out.println("Received shelfie update");
            case ("personalCardEvent") -> System.out.println("Your personal card for this game");
            case ("commonCardEvent") -> System.out.println("Common cards for this game");
            case ("playerEvent") -> System.out.println("List of connected players with score");
            case ("myTurn") -> System.out.println("It's your turn!");

            //Servers-side errors
            case (NICKNAME_NOT_UNIQUE) -> System.out.println(NICKNAME_NOT_UNIQUE);
            case (PLAYER_DISCONNECTED) -> System.out.println(PLAYER_DISCONNECTED);
            case (INVALID_NUMBER_OF_PLAYERS) -> System.out.println(INVALID_NUMBER_OF_PLAYERS);
            case (INVALID_CHOICE_OF_TILES) -> System.out.println(INVALID_CHOICE_OF_TILES);
            case (INVALID_COLUMN) -> System.out.println(INVALID_COLUMN);
            case (INVALID_RECEIVER) -> System.out.println(INVALID_RECEIVER);
            case (ALREADY_LOGGED_IN) -> System.out.println(ALREADY_LOGGED_IN);
            case (YOU_ARE_RECEIVER) -> System.out.println(YOU_ARE_RECEIVER);
            case (ERROR_IN_GAMEMOVE) -> System.out.println((ERROR_IN_GAMEMOVE));

            default -> System.out.println("Unrecognized event!");
        }
    }

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
     * @param nameOfCommonCard is name of commoncard
     * @param whatCommonCard is 1 or 2 depending on card updated
     */
    @Override
    public void commonCardUpdater (String nameOfCommonCard, int whatCommonCard) {
        if (whatCommonCard == 1)
            this.CommonCard1 = nameOfCommonCard;
        else if (whatCommonCard == 2)
            this.CommonCard2 = nameOfCommonCard;
    }
}
