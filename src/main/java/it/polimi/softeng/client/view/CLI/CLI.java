package it.polimi.softeng.client.view.CLI;

import it.polimi.softeng.JSONParser.ChatParser;
import it.polimi.softeng.client.view.MessageHandler;
import it.polimi.softeng.model.*;
import it.polimi.softeng.client.view.CommonOperationsFramework;
import it.polimi.softeng.client.view.UI;
import it.polimi.softeng.model.PersonalCards;
import it.polimi.softeng.model.commonCards.CommonCards;
import org.json.simple.JSONObject;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static it.polimi.softeng.Constants.*;

public class CLI extends CommonOperationsFramework implements UI, Runnable {
    /**
     * Local representation of GameBoard
     */
    private GameBoard userGameBoard;

    /**
     * Local representation of Shelfie
     */
    private Shelfie UserShelfie;

    /**
     * Player information
     */
    private int UserScore;
    private PersonalCards personalCard;
    private String Nickname;
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
     * 1 -> Connected with Socket
     * 2 -> Connected with RMI
     */
    private int ConnectionMode = 0;
    private int NumOfPlayer;
    private boolean GameIsOn;
    private Scanner input;
    private PrintStream output;

    public PrintStream getOutput() {
        return output;
    }

    private MessageHandler messageHandler;

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
            ConnectionMode = input.nextInt();

            switch (ConnectionMode) {
                case 1 -> { //socket
                    System.out.println("Connection with Socket...");
                    System.out.println("Digit server IP");
                    System.out.println(">");
                    ServerAddress = input.nextLine();
                    System.out.println("Digit server Port");
                    System.out.println(">");
                    Port = input.nextInt();
                    System.out.println("Do you want to create a new game(1) or join a game which is already started(2)?");
                    System.out.println("If you want to reconnect to a previous game choose 2 and use the same nickname");
                    System.out.println(">");

                   // connectCLI(ServerAddress, Port);

                    do {
                        StartGame = input.nextInt();
                    } while (StartGame != 1 && StartGame != 2);
                    if (StartGame == 1) {
                        System.out.println("Insert the number of players(2-4)");
                        System.out.println(">");
                        int NumOfPlayer;
                        do {
                            NumOfPlayer = input.nextInt();
                        } while (NumOfPlayer < 2 || NumOfPlayer > 4);

                        System.out.println("Do you want to play with Easy mode(1) or Normal mode(2)?");
                        System.out.println(">");
                        int GameMode;
                        do {
                            GameMode = input.nextInt();
                        } while (GameMode != 1 && GameMode != 2);
                    }

                    //TODO nicknameUniqueness
                    //do {
                    System.out.println("Insert nickname (ONLY characters a-z A-Z 0-9 and _ allowed)");
                    System.out.println(">");
                    do {
                        Nickname = input.nextLine();
                    } while (isOkNickname());
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
                    System.out.println("Do you want to create a new game(1) or join a game which is already started(2)?");
                    System.out.println("If you want to reconnect to a previous game choose 2 and use the same nickname");
                    System.out.println("If you want to reconnect to a previous game choose 2 and use the same nickname");
                    System.out.println(">");
                    do {
                        StartGame = input.nextInt();
                    } while (StartGame != 1 && StartGame != 2);
                    if (StartGame == 1) {
                        System.out.println("Insert the number of players(2-4)?");
                        System.out.println(">");
                        do {
                            NumOfPlayer = input.nextInt();
                        } while (NumOfPlayer > 4 || NumOfPlayer < 2);

                        System.out.println("Do you want to play with Easy mode(1) or Normal mode(2)?");
                        System.out.println(">");
                        do {
                            GameMode = input.nextInt();
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


    @Override
    /**
     * @param board is playerBoard
     * @param notAvailable is notAvailable cells
     */
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
                    System.out.print(ANSI_GREY + " ▇ ");
                } else {
                    if (board[i][j] != null) {
                        tileColor = board[i][j].getColor();
                        System.out.print(" " + tileColor.coloredText() + tileColor.colorLetter() + " ");
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
                        System.out.print(tileColor.coloredText() + " " + tileColor.colorLetter() + " ");
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
        System.out.println(chatParser.getReceiver() + ": " + chatParser.getMessage());
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

    public void beginGame(boolean value) {
        this.GameIsOn = value;
    }

    /**
     * Main class of CLI
     */
    @Override
    public void run() {
        boolean firstRun = true;
        setupCLI();

        while (GameIsOn) {
            game(firstRun);
            firstRun = false;
        }

        input.close();
        output.close();
    }

    /**
     * @param firstRun Game routine that wait for commands
     */
    public void game(boolean firstRun) {
        //POSSIBLE COMMANDS
        commands(firstRun);

        String command = input.nextLine();

        //First check of command good formatting
        if ((command.charAt(0) != '@') || (command.charAt(5) != ' ')) {
            System.out.println("Please write a command in @CMND text format!");
            return;
        }

        //Command is in @CMND format (every command is 4 letters), uppercase avoid case sensitivity
        String op = command.substring(0, 4).toUpperCase();
        String action = command.substring(6);

        boolean needServer = op.equals("@LOGN") || op.equals("@CHAT") || op.equals("@GAME") || op.equals("@VPLA") || op.equals("@VCCA");
        switch (ConnectionMode) {
            case 1 -> {
                //These actions need to communicate with server
                if (needServer)
                    actionToJSON(op, action);
                    //These actions are view-local
                else {
                    switch (op) {
                        case ("@VBOR") -> boardVisualizer(userGameBoard.getBoard(), userGameBoard.getNotAvailable());
                        case ("@VSHE") -> shelfieVisualizer(UserShelfie.getGrid());
                        case ("@VPCA") -> personalCardVisualizer(personalCard);
                        default -> System.out.println("Unrecognized command");
                    }
                }
            }
            case 2 -> {
                //These actions need to communicate with server
                if (needServer)
                    RMIInvoker(op, action);
                    //These actions are view-local
                else {
                    switch (op) {
                        case ("@VBOR") -> boardVisualizer(UserBoard.getBoard(), UserBoard.getNotAvailable());
                        case ("@VSHE") -> shelfieVisualizer(UserShelfie.getGrid());
                        case ("@VPCA") -> personalCardVisualizer(personalCard);
                        default -> System.out.println("Unrecognized command");
                    }
                }
            }
        }

        System.out.println("Test");
    }

    /**
     * @param firstRun Print all possible commands doable by user eventually with MyShelfie logo (only at CLI first start)
     */
    public void commands(boolean firstRun) {
        if (firstRun)
            logo();
        System.out.println("\n" +
                "+---------------------------------------------------------------------------------+-----------------------------------------------------------------------------------+\n" +
                "|                                     Command                                     |                                       Effect                                      |\n" +
                "+---------------------------------------------------------------------------------+-----------------------------------------------------------------------------------+\n" +
                "| " + ANSI_GREEN + "@VBOR" + ANSI_RESET + "                                                                           | Visualize board status                                                            |\n" +
                "| " + ANSI_GREEN + "@VSHE" + ANSI_RESET + "                                                                           | Visualize shelfie status                                                          |\n" +
                "| " + ANSI_GREEN + "@VPLA" + ANSI_RESET + "                                                                           | Visualize currently connected players and score                                   |\n" +
                "| " + ANSI_GREEN + "@VCCA" + ANSI_RESET + "                                                                           | Visualize common objectives                                                       |\n" +
                "| " + ANSI_GREEN + "@VPCA" + ANSI_RESET + "                                                                           | Visualize your personal card                                                      |\n" +
                "| " + ANSI_GREEN + "@GAME gameMoveFormat" + ANSI_RESET + "                                                            | Do a game move                                                                    |\n" +
                "| " + ANSI_GREEN + "@CHAT 'nameOfReceiver' message" + ANSI_RESET + "                                                    | Send a chat message (to send a message to everybody type 'all' in nameOfReceiver) |\n" +
                "+---------------------------------------------------------------------------------+-----------------------------------------------------------------------------------+\n" +
                "\n");
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
     * @return true if Nickname follow regex standard defined for nicknames
     */
    public boolean isOkNickname (){
        Pattern pattern = Pattern.compile(nicknameREGEX);
        Matcher matcher = pattern.matcher(this.Nickname);
        return matcher.matches();
    }

    /**
     * Called by RMI or Socket for board update
     * @param b is new Board
     */
    @Override
    public void boardUpdater(GameBoard b) {
        this.UserBoard = b;
    }
    public void boardUpdater(Board b) {

    }

    /**
     * Called by RMI or Socket for shelfie update
     * @param s is new Shelfie
     */
    @Override
    public void shelfieUpdater(Shelfie s) {
        this.UserShelfie = s;
    }

    /**
     * Called by RMI or Socket for score update
     * @param s is new score
     */
    @Override
    public void scoreUpdater(int s) {
        this.UserScore = s;
    }

    /**
     * Called by RMI or Socket for personal car update -> only at the beginning
     * @param pc is personal card
     */
    @Override
    public void personalCardUpdater(PersonalCards pc) {
        this.personalCard = pc;
    }

    @Override
    public void shelfieUpdaterFromJSON() {

    }
}
