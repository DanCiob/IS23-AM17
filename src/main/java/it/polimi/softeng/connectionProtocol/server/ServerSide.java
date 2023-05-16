package it.polimi.softeng.connectionProtocol.server;

import it.polimi.softeng.JSONParser.RequestParser;
import it.polimi.softeng.controller.ServerMessageHandler;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.rmi.RemoteException;
import java.util.ArrayList;


public class ServerSide {

    private int portNumber = 1234;

    private LoginManagerV2 loginManager;
    private ServerSideTCP serverSideTCP;
    private ServerSideRMI serverSideRMI;
    private ServerMessageHandler serverMessageHandler = null;
    private String gameLobby = "gameLobby";
    private String gameStarted = "gameStarted";

    public ServerSide(ServerMessageHandler serverMessageHandler) {
        this.serverMessageHandler = serverMessageHandler;
        loginManager = new LoginManagerV2(serverMessageHandler);
        serverSideTCP = new ServerSideTCP(loginManager, serverMessageHandler);
        serverSideRMI = new ServerSideRMI(loginManager,this);
    }

    public void sendMessageToAll(String message){
        serverSideTCP.sendMessageToAll(message);
        //this doesnt need rmi because this should never be used for chat messages
    }

    public void sendMessage(String message, String nickName){
        serverSideTCP.sendMessage(message,nickName);

        if(interceptChatMessage(message)){
            sendChatMessageRMI(message);
        }
    }

    public void sendMessageExcept(String message, String nickName){
        serverSideTCP.sendMessageExcept(message,nickName);

        if(interceptChatMessage(message)){
            sendChatMessageRMI(message);
        }
    }

    private Boolean interceptChatMessage(String message){
        RequestParser requestParser = new RequestParser();
        JSONParser parser = new JSONParser();
        JSONObject obj = null;
        try {
            obj = (JSONObject) parser.parse(message);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String requestType = obj.get("request").toString();

        if(requestType.equals("@CHAT")){
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

        switch (requester){
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

    public ArrayList<String> getNickNameList() {
        return loginManager.getNickNameList();
    }
    //////// methods for testing

}