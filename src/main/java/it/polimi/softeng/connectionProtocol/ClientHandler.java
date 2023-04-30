package it.polimi.softeng.connectionProtocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable{
    Socket clientSocket;
    Boolean nickNameNotConfirmed = true;

    public ClientHandler(Socket clientSocket){
            this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

        //routine that gets the output stream for the server to a single client
        PrintWriter out = null; // allocate to write answer to client.
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //routine that gets the input stream for the server to a single client
        BufferedReader in = null;
        try{
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("single input established");
        }catch (IOException e ){
            e.printStackTrace();
        }

        while (true) {
            String s;
            try {
                s = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(s != null) System.out.println(s);
        }


        /*
        //routine for login
        while(nickNameNotConfirmed){

        }*/
    }


}
