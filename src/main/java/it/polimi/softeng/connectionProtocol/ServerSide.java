package it.polimi.softeng.connectionProtocol;

import it.polimi.softeng.connectionProtocol.ClientHandler;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerSide {
    private ServerSocket serverSocket = null;
    private int portNumber = 1234;

    private ArrayList<ClientHandler> clientList = new ArrayList<>();


    public ServerSide() {
        try {
            serverSocket = new ServerSocket(portNumber);
            System.out.println("server socket up");
        } catch (IOException e) {
            e.printStackTrace();
        }


        Thread t = new Thread(() -> clientAcceptor(serverSocket,clientList));
        t.start();
    }

    public void clientAcceptor(ServerSocket serverSocket, ArrayList<ClientHandler> clientList){
        Socket clientSocket = null;
        ExecutorService executor = Executors.newCachedThreadPool();

        while(true){
            try {
                clientSocket = serverSocket.accept();
                System.out.println("client accepted ");
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                executor.submit(clientHandler);
                clientList.add(clientHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessageToAll(String message){
        for(ClientHandler client : clientList){
            client.sendMessage(message);
        }
    }
}
