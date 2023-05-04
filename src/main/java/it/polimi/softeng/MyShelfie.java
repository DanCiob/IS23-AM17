package it.polimi.softeng;


import it.polimi.softeng.connectionProtocol.ClientSide;
import it.polimi.softeng.connectionProtocol.ServerSide;

import java.io.*;

public class MyShelfie {
    static int mode = 20;

    public static void main(String[] args) throws IOException {
        System.out.println("This is myShelfie. decide operating mode between:");
        System.out.println("0) server");
        System.out.println("1) client CLI");
        System.out.println("2) client GUI");
        System.out.println("> ");

        Reader in = null;

        if(args.length == 0) {
            in = new InputStreamReader(System.in);
        }
        BufferedReader stdIn = new BufferedReader(in);

        while(mode != 0 && mode != 1 && mode != 2){   //.read() returns the int ascii value, so 0 is 48(ascii) and 1 is 49(ascii)
            try {                                        // yes, it could be written better, but works
                mode = Integer.parseInt(stdIn.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        switch (mode){
            case 0 ->{
                System.out.println("You've chosen : server mode");

                //creates the server
                ServerSide serverSide = new ServerSide();

                String userInput;
                while ((userInput = stdIn.readLine()) != null) {
                    serverSide.sendMessageToAll(userInput);
                }
            }

            case 1 ->{
                System.out.println("You've chosen : client CLI");

                //creates the client CLI
                //CLI cli = new CLI();
                ClientSide clientSide = new ClientSide();
                String userInput;
                while ((userInput = stdIn.readLine()) != null) {
                    clientSide.sendMessage(userInput);
                }

            }
            case 2 ->{
                System.out.println("You've chosen : client GUI");

                //creates the client GUI
            }

            default -> System.out.println("Unrecognized mode, closing application...");

        }
    }
}
