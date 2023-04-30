package it.polimi.softeng.connectionProtocol;

import it.polimi.softeng.model.Player;

import javax.swing.text.PlainDocument;
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
        int i = 0;
        while(true){
            System.out.print("waiting...");
            try{
                clientSocket = serverSocket.accept();
                executor.submit(new ClientHandler(clientSocket));
                System.out.println("client accepted !");
                i++;
            }catch(IOException e){
                e.printStackTrace();
                System.out.println("client accept failed !");
            }
        }
    }

    /**
     * setter method for the map playerToSocket which connects the player name string to its relative socket
     * @param playerName a string with the playername
     * @param socket the relative clientsocket
     */
    public void enterPlayerToSocket(String playerName, Socket socket) {
        playerToSocket.put(playerName,socket);
    }
}
