package it.polimi.softeng.CLI;

import it.polimi.softeng.listeners.Listeners;
import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;

import java.io.PrintStream;
import java.net.http.WebSocket;
import java.util.ArrayList;
import java.util.Scanner;

public class CLI implements Runnable{

    //Output stream
    private final PrintStream output;
    //Input stream
    private final Scanner input;
    //Contains listeners used to invoke or receive changes
    private ArrayList<Listeners> listeners;
    //Define if game is running
    private boolean GameIsOn = true;
    protected String hostName;
    protected int portNumber;
    private String Nickname;

    public CLI() {
        input = new Scanner(System.in);
        output = new PrintStream(System.out);
    }


    public static void main() {
        CLI cli = new CLI();
        cli.run();
    }

    /**
     * This method is called when a new CLI is generated, it manages connection within client and server
     */
    public void connectCLI() {
        System.out.println("Welcome to MyShelfie!");
        System.out.println("Insert server IP:");
        System.out.println(">");
        setHostName(input.nextLine());
        System.out.println("Insert port:");
        System.out.print(">");
        setPort(input.nextInt());

        //Connection block

        //TO-DO return false (or raise exception) if connection fails
        }

    public void loginCLI() {
        System.out.println(">Insert your nickname: ");
        System.out.print(">");
        setNickname(input.nextLine());

        //Check uniqueness of nickname

        System.out.println("Welcome: " + Nickname + "!");
    }

    /**
     * This method override run() and make CLI continuously waiting for commands or notifications.
     */
    @Override
    public void run() {
        connectCLI();
        //loginCLI();


        while(GameIsOn == true)
        {
            //waitingCommands();
        }
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
    public void setGameIsOn (boolean value)
    {
        this.GameIsOn = value;
    }
    public void setHostName(String s) {hostName = s;}
    public void setPort(int p) {portNumber = p;}
    public void setNickname(String nickname) {Nickname = nickname;}
}
