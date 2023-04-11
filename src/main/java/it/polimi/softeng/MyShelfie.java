package it.polimi.softeng;

import it.polimi.softeng.connectionProtocol.ClientSide;
import it.polimi.softeng.connectionProtocol.ServerSide;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;

public class MyShelfie {
    static int mode = 20;

    public static void main(String[] args){
        System.out.println("this is myShelfie. decide operating mode between :");
        System.out.println("0) server");
        System.out.println("1) client");


        while(mode != 48 && mode != 49){   //.read() returns the int ascii value, so 0 is 48(ascii) and 1 is 49(ascii)
            try {                          // yes, it could be written better, but works
                mode = System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        switch (mode){
            case 48 ->{
                System.out.println("you've chosen : server mode");

                //creates the server
                ServerSide.main(null);
            }
            case 49 ->{
                System.out.println("you've chosen : client mode");

                //creates the client
                ClientSide.main(null);
            }
        }
    }
}