package it.polimi.softeng.client.view.CLI;

import it.polimi.softeng.model.Board;
import it.polimi.softeng.model.Cell;
import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;
import it.polimi.softeng.client.view.CommonOperationsFramework;
import it.polimi.softeng.client.view.UI;
import it.polimi.softeng.Constants.*;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import static it.polimi.softeng.Constants.*;

public class CLI extends CommonOperationsFramework implements UI, Runnable{
    /**
     * Local representation of Board
     */
    private Board UserBoard;
    /**
     * Local representation of Shelfie
     */
    private Shelfie UserShelfie;
    private int UserScore;
    private String Nickname;
    private String ServerAddress;
    private int Port;
    /**
     * 1 -> player want to create new game (FA: multi-game management)
     * 2 -> player want to join (if present) current game
     */
    private int CreateNewGame;
    /**
     * 1 -> Easy Mode
     * 2 -> Normal Mode
     */
    private int GameMode;
    private int NumOfPlayer;
    private boolean GameIsOn;
    private Scanner input;
    private PrintStream output;


    /**
     * CLI initialization, connection to server, choose of gameMode
     * After setup CLI is ready to be used
     */
    public void setupCLI(){
        int mode = 0;

        System.out.println("Initializing CLI...");
        System.out.println("Do you want to connect using Socket(1) or RMI(2)?");
        System.out.println(">");
        do {
            mode = input.nextInt();

            switch (mode) {
                case 1:
                    System.out.println("Connection with Socket...");
                    System.out.println("Digit server IP");
                    System.out.println(">");

                    ServerAddress = input.nextLine();
                    System.out.println("Digit server Port");
                    System.out.println(">");
                    Port = input.nextInt();

                    System.out.println("Want to create a game(1) or join (if present) a current one(2)?");
                    System.out.println("If you want to reconnect to a previous game choose 2 and use the same nickname");
                    System.out.println(">");
                    do {
                        CreateNewGame = input.nextInt();
                    }while(CreateNewGame != 1 || CreateNewGame != 2);

                    if (CreateNewGame == 1)
                    {
                        System.out.println("How many players do you want to play with (2-4)?");
                        System.out.println(">");
                        int NumOfPlayer = 0;
                        do {
                            input.nextInt();
                        }while (NumOfPlayer > 4 || NumOfPlayer < 2);

                        System.out.println("Do you want to play with Easy mode(1) or Normal mode(2)?");
                        System.out.println(">");
                        int GameMode = 0;
                        do {
                            input.nextInt();
                        }while (GameMode != 1 || GameMode != 2);
                    }

                    //do {
                    System.out.println("Insert nickname");
                    Nickname = input.nextLine();
                    //Connect to server
                    //}while(nicknameNotUnique)
                    break;
                case 2:
                    System.out.println("Connection with RMI...");
                    System.out.println("Digit server IP");
                    System.out.println(">");
                    ServerAddress = input.nextLine();

                    System.out.println("Digit server Port");
                    System.out.println(">");
                    Port = input.nextInt();

                    System.out.println("Want to create a game(1) or join (if present) a current one(2)?");
                    System.out.println("If you want to reconnect to a previous game choose 2 and use the same nickname");
                    System.out.println(">");
                    do {
                        CreateNewGame = input.nextInt();
                    }while(CreateNewGame != 1 || CreateNewGame != 2);

                    if (CreateNewGame == 1)
                    {
                        System.out.println("How many players do you want to play with (2-4)?");
                        System.out.println(">");
                        do {
                            NumOfPlayer = input.nextInt();
                        }while (NumOfPlayer > 4 || NumOfPlayer < 2);

                        System.out.println("Do you want to play with Easy mode(1) or Normal mode(2)?");
                        System.out.println(">");
                        do {
                            GameMode = input.nextInt();
                        }while (GameMode != 1 || GameMode != 2);
                    }

                    //do {
                    System.out.println("Insert nickname");
                    Nickname = input.nextLine();
                    //Connect to server
                    //}while(nicknameNotUnique)
                    break;
                default:
                    System.out.println("Unrecognized connection method, please digit 1 or 2...");
            }
        } while (mode != 1 || mode != 2);
    }


    @Override
    /**
     *
     * @param board
     * @param notAvailable
     */
    public void boardVisualizer(Tile[][] board, ArrayList<Cell> notAvailable){
        Tile.TileColor tileColor;
        String gray = "\u001B[37m";
        boolean notAv = false;

        System.out.println(gray + "     0  1  2  3  4  5  6  7  8"); //print column index
        System.out.println(gray + "    ━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        for(int i=0;i<boardRows;i++){ //loop for rows
            System.out.print(gray + i + "  ┃"); //print row index
            for (int j = 0; j < boardColumns; j++){
                notAv = false;
                for(Cell cell1 : notAvailable) { //if not Available
                    if (cell1.getRow() == i && cell1.getColumn() == j){
                        notAv = true;
                    }
                }
                if(notAv){
                    System.out.print(gray + " ▇ ");
                }
                else{
                    if (board[i][j] != null) {
                        tileColor = board[i][j].getColor();
                        System.out.print(" " + tileColor.coloredText() + tileColor.colorLetter() + " ");
                    } else {
                        System.out.print(gray +" ░ ");
                    }
                }
            }
            System.out.print(gray + "┃\n");
        }
        System.out.println(gray + "    ━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    /**
     * @param shelfie is userShelfie
     * Visual representation of shelfie
     */
    @Override
    public void shelfieVisualizer(Tile[][] shelfie) {
            Tile.TileColor tileColor;
            String gray = "\u001B[37m";
            if(shelfie != null){
                System.out.println(gray + "    ----------------");
                for(int i=5;i>=0;i--){
                    System.out.print(gray + i + "  | ");
                    for(int j=0;j<5;j++){
                        if(shelfie[i][j] !=null){
                            tileColor = shelfie[i][j].getColor();
                            System.out.print( tileColor.coloredText() + tileColor.colorLetter() + "  ");
                        }else{
                            System.out.print("   ");
                        }
                    }
                    System.out.println(gray + "|");
                }
                System.out.println(gray + "    ----------------");
                System.out.println(gray + "     0  1  2  3  4" + Tile.TileColor.WHITE.coloredText());

        }
    }

    @Override
    public void commonCardsVisualizer() {

    }

    @Override
    public void personalCardVisualizer() {

    }

    //May be unified with onlinePlayersVisualizer
    @Override
    public void scoreVisualizer() {

    }

    @Override
    public void chatVisualizer() {

    }

    @Override
    public void onlinePlayersVisualizer() {

    }

    public void beginGame (boolean value)
    {
        this.GameIsOn = value;
    }

    @Override
    public void run() {
        setupCLI();

        while(GameIsOn)
        {
            game();
        }

        input.close();
        output.close();
    }

    /**
     * Game routine that wait for commands
     */
    public void game() {
        //POSSIBLE COMMANDS
        commands();

        String command = input.nextLine();

        //First check of command good formatting
        if (command.charAt(5) != ' ')
        {
            System.out.println("Please write a command in @CMND text format!");
            return;
        }


        //Command is in @CMND format (every command is 4 letters), uppercase avoid case sensitivity
        String op = command.substring(0, 4).toUpperCase();
        String action = command.substring(6);

        //These actions need to communicate with server
        if (op.equals("@CHAT") || op.equals("@GAME") || op.equals("@VPLA") || op.equals("@VSCO") || op.equals("@VPCA") || op.equals("@VCCA"))
            actionToJSON(op ,action);
        //These actions are view-local
        else
        {
            switch (op) {
                case ("@VBOR") -> boardVisualizer();
                case ("@VSHE") -> shelfieVisualizer(UserShelfie.getGrid());
            }
        }



        System.out.println("Test");
    }

    /**
     * Print all possible commands doable by user
     */
    public void commands()
    {
        System.out.println(ANSI_GREEN + "  .___  ___. ____    ____         _______. __    __   _______  __       _______  __   _______    \n" +
                "  |   \\/   | \\   \\  /   /        /       ||  |  |  | |   ____||  |     |   ____||  | |   ____|   \n" +
                "  |  \\  /  |  \\   \\/   /        |   (----`|  |__|  | |  |__   |  |     |  |__   |  | |  |__      \n" +
                "  |  |\\/|  |   \\_    _/          \\   \\    |   __   | |   __|  |  |     |   __|  |  | |   __|     \n" +
                "  |  |  |  |     |  |        .----)   |   |  |  |  | |  |____ |  `----.|  |     |  | |  |____    \n" +
                "  |__|  |__|     |__|        |_______/    |__|  |__| |_______||_______||__|     |__| |_______|   \n" +
                "                                                                                                 " +
                "" + ANSI_RESET);

        System.out.println("\n" +
                "+---------------------------------------------------------------------------------+-----------------------------------------------------------------------------------+\n" +
                "|                                     Command                                     |                                       Effect                                      |\n" +
                "+---------------------------------------------------------------------------------+-----------------------------------------------------------------------------------+\n" +
                "| "+ANSI_GREEN+"@VBOR"+ANSI_RESET+"                                                                           | Visualize board status                                                            |\n" +
                "| "+ANSI_GREEN+"@VSHE"+ANSI_RESET+"                                                                           | Visualize shelfie status                                                          |\n" +
                "| "+ANSI_GREEN+"@VPLA"+ANSI_RESET+"                                                                           | Visualize currently connected players and score                                   |\n" +
                "| "+ANSI_GREEN+"@VCCA"+ANSI_RESET+"                                                                           | Visualize common objectives                                                       |\n" +
                "| "+ANSI_GREEN+"@VPCA"+ANSI_RESET+"                                                                           | Visualize your personal card                                                      |\n" +
                "| "+ANSI_GREEN+"@GAME gameMoveFormat"+ANSI_RESET+"                                                            | Do a game move                                                                    |\n" +
                "| "+ANSI_GREEN+"@CHAT nameOfReceiver message"+ANSI_RESET+"                                                    | Send a chat message (to send a message to everybody type 'all' in nameOfReceiver) |\n" +
                "+---------------------------------------------------------------------------------+-----------------------------------------------------------------------------------+\n" +
                "\n");
    }
}