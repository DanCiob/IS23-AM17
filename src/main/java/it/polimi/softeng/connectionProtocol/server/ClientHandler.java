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

import static it.polimi.softeng.Constants.NICKNAME_NOT_UNIQUE;
import static it.polimi.softeng.JSONWriter.ErrorWriter.writeError;
import static it.polimi.softeng.JSONWriter.ServerSignatureWriter.serverSignObject;

/**
 * This class manages the communication between Controller and ClientSide for a single Client
 */

public class ClientHandler implements Runnable{
    /**
     * clientHandler's input from client
     */
    private BufferedReader in = null;
    /**
     * clientHandler's output to client
     */
    private PrintWriter out = null;
    /**
     * socket of the single client that connected
     */
    private final Socket clientSocket;
    /**
     * serverside reference
     */
    private final ServerSide serverSide;
    /**
     * boolean flag for login
     */
    private Boolean nickNameNotConfirmed = true;
    /**
     * server message handler to read messages
     */
    private final ServerMessageHandler serverMessageHandler;
    /**
     * serverside tcp reference
     */
    private final ServerSideTCP serverSideTCP;
    /**
     * player number attribute used to set the match player number
     */
    private int playerNumber;
    /**
     * used to manage logins
     */
    private final LoginManagerV2 loginManager;
    /**
     * flag used to say that the clientHandler got the pong from client
     */
    Boolean pong = false;
    /**
     * player's nickname
     */
    String nickname;

    /**
     * constructor method for clienthanlder
     * @param clientSocket client's socket
     * @param serverSide serverside reference
     * @param serverMessageHandler servermessagehandler reference
     * @param serverSideTCP serverside tcp reference
     * @param loginManager login manager reference to manage logins
     */
    public ClientHandler(Socket clientSocket, ServerSide serverSide, ServerMessageHandler serverMessageHandler,ServerSideTCP serverSideTCP,LoginManagerV2 loginManager){
        this.clientSocket = clientSocket;
        this.serverSide = serverSide;
        this.serverMessageHandler = serverMessageHandler;
        this.serverSideTCP = serverSideTCP;
        this.loginManager = loginManager;
    }

    /**
     * run method of client handler that establishes input and output to single client
     */
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

        t = new Thread(() -> pingUsers());
        t.start();

    }

    /**
     * method used to read messages
     * @param in server's input from client
     */
    public void readMessage(BufferedReader in){
        String s = "";
        try {
            while ((s = in.readLine()) != null) {

                //part responsible for answering a ping from the server
                if(s.equals("ping")){

                }
                else{
                    serverMessageHandler.parsingMessage(s);
                    if(nickNameNotConfirmed){
                        System.out.println("scannin for nick");
                        scanForNickName(s);
                    }
                }
            }
        } catch (IOException | ParseException e) {

            System.out.println("utente " + nickname + " si è disconnesso");
            serverSideTCP.addDisconnectedPlayer(nickname);
        }
    }

    /**
     * method used to send message to the client managed by this clienthanler object
     * @param message message to be sent
     */
    public void sendMessage(String message){
        out.println(message);
    }

    /**
     * method that intercepts a message and finds whether it is a login message; if that's true he manages the login
     * @param message
     */
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
        if(obj != null && Objects.equals(obj.get("request"), "@LOGN")){

            playerNumber = (int)(long) obj.get("numOfPlayer");
            loginManager.setPlayerNumber(playerNumber);   //TODO questo può essere fatto direttamente da loginManager
            nickname = (String) obj.get("nickname");
            if(!loginManager.getNickNameList().contains(nickname) && loginManager.getStatus().equals("gameLobby")){
                System.out.println(nickname);
                serverSideTCP.addUser(this,nickname);
                nickNameNotConfirmed = false;
            } else if (loginManager.getStatus().equals("gameStarted")) {

                if (loginManager.getDisconnectedPlayerList().contains(nickname)) {
                    serverSideTCP.addUser(this,nickname);
                    nickNameNotConfirmed = false;
                    System.out.println(nickname + " reconnected !");
                }
                else {
                    System.out.println("nickname is not acceptable ");
                    out.println(serverSignObject(writeError(NICKNAME_NOT_UNIQUE), "@ERRO", nickname).toJSONString());
                }

            } else{
                out.println(serverSignObject(writeError(NICKNAME_NOT_UNIQUE), "@ERRO", nickname).toJSONString());
                System.out.println("nickName già usato");
            }
        }
    }

    /**
     * getter method to get player number
     * @return int player number
     */
    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * method running on its own thread pinging the user related to this clienthandler
     */
    public void pingUsers(){

        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            out.println("ping");
        }
    }
}
