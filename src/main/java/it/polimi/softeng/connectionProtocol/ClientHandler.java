package it.polimi.softeng.connectionProtocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private BufferedReader in = null;
    private PrintWriter out = null;
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        System.out.println("new clientHandler created !");
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            out = new PrintWriter(clientSocket.getOutputStream(),true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Thread t = new Thread(()-> readMessage(in));
        t.start();

    }

    public void readMessage(BufferedReader in){
        String s = "";
        try {
            while ((s = in.readLine()) != null) {
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendMessage(String message){
        out.println(message);
    }
}
