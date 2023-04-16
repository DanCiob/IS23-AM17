package it.polimi.softeng.connectionProtocol;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerSide {
    static int portNumber;
    int playerNumber;
    Map<String, Socket> playerToSocket = new HashMap<>();

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
        ExecutorService executor = Executors.newCachedThreadPool();
        while(true){
            System.out.print("waiting...");
            try{
                clientSocket = serverSocket.accept();
                executor.submit(new ClientHandler(clientSocket));
                System.out.println("client accepted !");
            }catch(IOException e){
                e.printStackTrace();
                System.out.println("client accept failed !");
            }
        }
    }
}
