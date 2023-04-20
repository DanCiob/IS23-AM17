package it.polimi.softeng.client.view.CLI;

import it.polimi.softeng.model.Board;
import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;
import it.polimi.softeng.client.view.CommonOperationsFramework;
import it.polimi.softeng.client.view.UI;

import java.io.PrintStream;
import java.util.Scanner;

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
    public void boardVisualizer() {

    }

    /**
     * Visual representation of shelfie
     */
    @Override
    public void shelfieVisualizer() {
            Tile[][] shelfie = UserShelfie.getGrid();

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
        System.out.println("Test");
    }
}
