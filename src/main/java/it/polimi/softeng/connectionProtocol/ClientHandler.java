package it.polimi.softeng.connectionProtocol;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable{
    Socket clientSocket;

    public ClientHandler(Socket clientSocket){
            this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

        //routine that gets the output stream for the server
        PrintWriter out = null; // allocate to write answer to client.
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println("helo");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void ping(PrintWriter out){

        out.println();
    }

}
