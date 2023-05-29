package it.polimi.softeng.connectionProtocol.server;

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

import static it.polimi.softeng.Constants.ALREADY_LOGGED_IN;
import static it.polimi.softeng.Constants.NICKNAME_NOT_UNIQUE;
import static it.polimi.softeng.JSONWriter.ErrorWriter.writeError;
import static it.polimi.softeng.JSONWriter.ServerSignatureWriter.serverSignObject;

/**
 * This class manages the communication between Controller and ClientSide for a single Client
 */

public class ClientHandler implements Runnable{
    private BufferedReader in = null;
    private PrintWriter out = null;
    private Socket clientSocket;
    private ServerSide serverSide;
    private Boolean nickNameNotConfirmed = true;
    private ServerMessageHandler serverMessageHandler;
    private ServerSideTCP serverSideTCP;
    private int playerNumber;
    private LoginManagerV2 loginManager;
    String nickname;

    public ClientHandler(Socket clientSocket, ServerSide serverSide, ServerMessageHandler serverMessageHandler,ServerSideTCP serverSideTCP,LoginManagerV2 loginManager){
        this.clientSocket = clientSocket;
        this.serverSide = serverSide;
        this.serverMessageHandler = serverMessageHandler;
        this.serverSideTCP = serverSideTCP;
        this.loginManager = loginManager;
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
            System.out.println("utente " + nickname + " si è disconnesso");
            serverSideTCP.addDisconnectedPlayer(nickname);
        }
    }

    public void sendMessage(String message){
        out.println(message);
    }

    public void scanForNickName(String message){
        Boolean flag = false;
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
            loginManager.setPlayerNumber(playerNumber);   //TODO questo può essere fatto direttamente da loginManager
            nickname = (String) obj.get("nickname");
            if(!loginManager.getNickNameList().contains(nickname) && loginManager.getStatus().equals("gameLobby")){
                System.out.println(nickname);
                serverSideTCP.addUser(this,nickname);
                nickNameNotConfirmed = false;
            } else if (loginManager.getStatus().equals("gameStarted")) {
                for(String disconnectedPlayer : loginManager.getDisconnectedPlayerList()){
                    if(nickname.equals(disconnectedPlayer)){
                        flag = true;
                        System.out.println(nickname + " reconnected !");
                        serverSideTCP.addUser(this,nickname);
                    }
                }
                if(!flag){
                    System.out.println("nickname is not acceptable ");
                    //TODO popolare con send di messaggio di errore
                    out.println(serverSignObject(writeError(NICKNAME_NOT_UNIQUE), "@ERRO", nickname).toJSONString());
                }
            } else{
                //TODO aggiungere messaggio di errore da inviare al client
                out.println(serverSignObject(writeError(NICKNAME_NOT_UNIQUE), "@ERRO", nickname).toJSONString());
                System.out.println("nickName già usato");
            }
        }
    }

    public int getPlayerNumber() {
        return playerNumber;
    }
}
