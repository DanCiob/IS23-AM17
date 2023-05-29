package it.polimi.softeng.connectionProtocol.server;

import it.polimi.softeng.JSONParser.RequestParser;
import it.polimi.softeng.connectionProtocol.client.ClientRemoteInterface;
import it.polimi.softeng.connectionProtocol.client.ClientSideMethods;
import it.polimi.softeng.controller.Controller;
import it.polimi.softeng.controller.ServerMessageHandler;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;


public class ServerSide {

    private int portNumber = 1234;

    private LoginManagerV2 loginManager;
    private ServerSideTCP serverSideTCP;
    private ServerSideRMI serverSideRMI;
    private ServerMessageHandler serverMessageHandler = null;
    private String gameLobby = "gameLobby";
    private String gameStarted = "gameStarted";


    public ServerSide(ServerMessageHandler serverMessageHandler, Controller controller) {
        this.serverMessageHandler = serverMessageHandler;
        loginManager = new LoginManagerV2(serverMessageHandler);
        serverSideTCP = new ServerSideTCP(loginManager, serverMessageHandler);
        serverSideRMI = new ServerSideRMI(loginManager,this,controller);
    }

    /**
     * This method is used to sent Move messages, the requests come from {@link it.polimi.softeng.controller.GameController}
     *  and are forwarded to {@link ServerSideTCP}.
     *  N.B. Chat messages are not processed by this method.
     * @param message move to do
     */
    //this doesn't need rmi because this should never be used for chat messages
    public void sendMessageToAll(String message){
        serverSideTCP.sendMessageToAll(message);
    }

    /**
     * This method is used to send a Chat message to a specific player, the requests come from {@link it.polimi.softeng.controller.ChatController}
     *  and are forwarded to {@link ServerSideTCP} and {@link ClientRemoteInterface}.
     * @param message chat message
     * @param nickName receiver
     */
    public void sendMessage(String message, String nickName){
        serverSideTCP.sendMessage(message,nickName);
        System.out.println(message);

        if(interceptChatMessage(message)){
            System.out.println("message intercepted");
            sendChatMessageRMI(message);
        }
    }

    /**
     * This method is used to send a Chat message to all players, the requests come from {@link it.polimi.softeng.controller.ChatController}
     *  and are forwarded to {@link ServerSideTCP} and {@link ClientRemoteInterface}.
     * @param message chat message
     * @param nickName sender
     */
    public void sendMessageExcept(String message, String nickName){
        serverSideTCP.sendMessageExcept(message,nickName);
        System.out.println(message);

        if(interceptChatMessage(message)){
            System.out.println("message intercepted");
            sendChatMessageRMI(message);
        }
    }

    private Boolean interceptChatMessage(String message){
        System.out.println("got into intercepting");
        JSONParser parser = new JSONParser();
        JSONObject obj = null;
        try {
            obj = (JSONObject) parser.parse(message);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String requestType = obj.get("request").toString();

        if(requestType.equals("@CHAT")){
            System.out.println("its trueeee");
            return true;
        }
        return false;
    }

    private void sendChatMessageRMI(String chatMessage){
        JSONParser parser = new JSONParser();

        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) parser.parse(chatMessage);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        String requester = (String) jsonObject.get("requester");
        String receiver = (String) jsonObject.get("receiver");
        String message = (String) jsonObject.get("message");

        switch (receiver){
            case("all") -> {
                for(String player : loginManager.getNickNameList()){
                    if(serverSideRMI.getNameToStub().containsKey(player) && !requester.equals(player)){
                        try {
                            serverSideRMI.getNameToStub().get(player).displayChatMessage(message,requester);
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
            default -> {
                for(String player : loginManager.getNickNameList()){
                    if(serverSideRMI.getNameToStub().containsKey(player) && receiver.equals(player)){
                        try {
                            serverSideRMI.getNameToStub().get(player).displayChatMessage(message,requester);
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
    }
    ////////// getter methods


    public ServerSideTCP getServerSideTCP() {
        return serverSideTCP;
    }

    public ServerSideRMI getServerSideRMI() {
        return serverSideRMI;
    }

    public ArrayList<String> getNickNameList() {
        return loginManager.getNickNameList();
    }

    public Map<String, ClientRemoteInterface> getPlayerStubs(){
        return serverSideRMI.getNameToStub();
    }

    public LoginManagerV2 getLoginManager() {
        return loginManager;
    }
}