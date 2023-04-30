package it.polimi.softeng;


import it.polimi.softeng.client.view.CLI.CLI;
import it.polimi.softeng.connectionProtocol.ClientSide;
import it.polimi.softeng.connectionProtocol.ServerSide;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;

public class MyShelfie {
    static int mode = 20;

    public static void main(String[] args){
        System.out.println("This is myShelfie. decide operating mode between:");
        System.out.println("0) server");
        System.out.println("1) client CLI");
        System.out.println("2) client GUI");
        System.out.println("> ");


        while(mode != 48 && mode != 49 && mode != 50){   //.read() returns the int ascii value, so 0 is 48(ascii) and 1 is 49(ascii)
            try {                                        // yes, it could be written better, but works
                mode = System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        switch (mode){
            case 48 ->{
                System.out.println("You've chosen : server mode");

                //creates the server
                ServerSide.main(null);
            }
            case 49 ->{
                System.out.println("You've chosen : client CLI");

                //creates the client CLI
                //CLI cli = new CLI();
                ClientSide clientSide = new ClientSide();
                clientSide.setupConnection("socket");
                clientSide.sendMessage("helo");
            }
            case 50 ->{
                System.out.println("You've chosen : client GUI");

                //creates the client GUI
            }

            default -> System.out.println("Unrecognized mode, closing application...");
        }
    }
}
