package it.polimi.softeng.CLI;

import it.polimi.softeng.listeners.Listeners;
import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;

import java.io.PrintStream;
import java.net.http.WebSocket;
import java.util.Scanner;

public class CLI implements Runnable{

    //Output stream
    private final PrintStream output;

    //Input stream
    private final Scanner input;

    public CLI() {
        input = new Scanner(System.in);
        output = new PrintStream(System.out);
    }


    public static void main() {
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to MyShelfie!");

        System.out.println("Insert server IP:");
        System.out.println(">");
        String ip = input.nextLine();
        System.out.println("Insert port:");
        System.out.print(">");
        int port = input.nextInt();
        //Save port
        //Save IP
        CLI cli = new CLI();
        cli.run();
    }

    /**
     * This method is called when a new CLI is generated
     */
    public void initializeCLI() {
        String currentNickname = null;

        System.out.println(">Insert your nickname: ");
        System.out.print(">");
        currentNickname = input.nextLine();
        System.out.println("Welcome: " + currentNickname + "!");

        }

        //Connection with server...

    /**
     * This method override run() and make CLI continuously waiting for commands or notifications.
     */
    @Override
    public void run() {
        initializeCLI();

        //Close input and output streams
        input.close();
        output.close();
    }

    //Method to show updated board


    /**
     * Method to show updated shelfie
     * @param shelfie
     */
    public void shelfieVisualizer(Tile[][] shelfie){
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


}
