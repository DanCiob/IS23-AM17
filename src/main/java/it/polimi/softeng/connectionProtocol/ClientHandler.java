package it.polimi.softeng.connectionProtocol;

import it.polimi.softeng.controller.ServerMessageHandler;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

public class ClientHandler implements Runnable{
    private BufferedReader in = null;
    private PrintWriter out = null;
    private Socket clientSocket;
    private ServerSide serverSide;
    private Boolean nickNameNotConfirmed = true;
    private ServerMessageHandler serverMessageHandler;
    private int playerNumber ;

    public ClientHandler(Socket clientSocket, ServerSide serverSide, ServerMessageHandler serverMessageHandler){
        this.clientSocket = clientSocket;
        this.serverSide = serverSide;
        this.serverMessageHandler = serverMessageHandler;
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
                serverMessageHandler.parsingMessage(s);
                if(nickNameNotConfirmed){
                    scanForNickName(s);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
    public void sendMessage(String message){
        out.println(message);
    }

    public void scanForNickName(String message){
        System.out.println("im here");
        JSONParser parser = new JSONParser();
        JSONObject obj = null;
        if(message != null){
            try {
                obj = (JSONObject) parser.parse(message);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(obj != null && Objects.equals((String) obj.get("request"), "@LOGN")){

            playerNumber = (int)(long) obj.get("numOfPlayer");
            serverSide.setPlayerNumber(playerNumber);
            String nickname = (String) obj.get("nickname");
            if(!serverSide.getNickNameList().contains(nickname)){
                System.out.println(nickname);
                serverSide.addUser(this,nickname);
                nickNameNotConfirmed = false;
            }
            else{
                //da sostituire con una sendMessage di errore
                System.out.println("nickName già usato");
           }
        }
    }

    public int getPlayerNumber() {
        return playerNumber;
    }
}
