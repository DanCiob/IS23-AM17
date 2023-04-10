package it.polimi.softeng.connectionProtocol;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSide {
    static int portNumber;

    public static void main(String[] args){
        System.out.println("Server started !");
        if (args != null && args.length == 1){
            portNumber = Integer.parseInt(args[0]);
        }
        else{
            CommunicationProtocolParser communicationProtocolParser = new CommunicationProtocolParser();
            communicationProtocolParser.parser("server");
            portNumber = communicationProtocolParser.getPortNumber();
        }

        //routine of server socket creation
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(portNumber);
            System.out.println("Server socket up !");
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("errore alla creazione del server !");
        }

        //accepting the client connection
        Socket clientSocket = null;
        boolean connection = false;
        while(!connection){
            System.out.println("waiting...");
            try{
                clientSocket = serverSocket.accept();
                System.out.println("client accepted !");
                connection = true;
            }catch(IOException e){
                e.printStackTrace();
                System.out.println("client accept failed !");
            }
        }

        //routine that gets the output stream for the server
        PrintWriter out = null; // allocate to write answer to client.
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println("helo");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
