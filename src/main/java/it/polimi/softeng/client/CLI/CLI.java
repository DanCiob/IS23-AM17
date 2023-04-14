package it.polimi.softeng.client.CLI;

import it.polimi.softeng.model.Player;

import java.beans.PropertyChangeEvent;
import java.io.PrintStream;
import java.util.Scanner;

public class CLI implements Runnable, UserInterface{

    //Output stream
    private final PrintStream output;

    //Input stream
    private final Scanner input;
    private String ip;
    private String port;
    private Player player;

    /**
     * Constructor of CLI
     */
    public CLI() {
        input = new Scanner(System.in);
        output = new PrintStream(System.out);
        ip = "tbd";
        port = "tbd";
    }

    /**
     * main class of CLI
     */
    public void main() {
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to MyShelfie!");

        System.out.println("Insert server IP:");
        System.out.println("> ");
        ip = input.nextLine();

        System.out.println("Insert port:");
        System.out.print("> ");
        port = input.nextLine();

        CLI cli = new CLI();
        cli.run();
    }

    /**
     * This method is called when a new CLI is generated
     */
    public void initializeCLI() {
        String currentNickname;

        //Connection with server...

        do {
            System.out.println("Insert your nickname: ");
            System.out.print("> ");
            currentNickname = input.nextLine();
            //Check uniqueness
        } while(false/*uniqueness of nickname*/);
        System.out.println("Welcome: " + currentNickname + "!");

        }

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

    //Method to show updated shelfies

    /**
     * Manage all possible events that Model sends to View
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //Switch EventReceived : "EventHandled"
        switch (evt.getPropertyName()) {

            }
    }

}
