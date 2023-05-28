package it.polimi.softeng;

import it.polimi.softeng.client.view.CLI.CLI;
import it.polimi.softeng.client.view.GUI.GUIAppl;
import it.polimi.softeng.controller.Controller;

import java.io.*;

/**
 * This class with its main method, is used to run MyShelfie Application.
 * It allows to select among operating modes of server, client GUI and CLI
 */

public class MyShelfie {
    static int mode = 20;

    public static void main(String[] args) throws IOException {
        System.out.println("This is myShelfie. decide operating mode among:");
        System.out.println("0) server");
        System.out.println("1) client CLI");
        System.out.println("2) client GUI");
        System.out.println("> ");

        Reader in = null;

        if(args.length == 0) {
            in = new InputStreamReader(System.in);
        }
        BufferedReader stdIn = new BufferedReader(in);

        while(mode != 0 && mode != 1 && mode != 2){
            try {
                mode = Integer.parseInt(stdIn.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        switch (mode){
            case 0 ->{ //SERVER MODE
                System.out.println("You've chosen : server mode");

                //Creates controller
                Controller controller = new Controller();

                //creates the server
                //ServerSide serverSide = new ServerSide();

                /*
                String userInput;
                while ((userInput = stdIn.readLine()) != null) {
                    serverSide.sendMessageToAll(userInput);
                }*/
            }

            case 1 ->{ //CLIENT CLI MODE
                System.out.println("You've chosen : client CLI");

                //creates the client CLI
                CLI cli = new CLI();
                cli.run();
                //ClientSide clientSide = new ClientSide();
                /*String userInput;
                while ((userInput = stdIn.readLine()) != null) {
                    cli.getClientSide().sendMessage(userInput);
                }*/

            }
            case 2 ->{ //CLIENT GUI MODE
                System.out.println("You've chosen : client GUI");
                GUIAppl guiApplication = new GUIAppl();
                guiApplication.main(args);
                //creates the client GUI
            }

            default -> System.out.println("Unrecognized mode, closing application...");
        }
    }
}
