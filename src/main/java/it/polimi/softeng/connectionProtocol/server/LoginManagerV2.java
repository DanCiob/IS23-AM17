package it.polimi.softeng.connectionProtocol.server;

import it.polimi.softeng.client.view.CLI.CLI;
import it.polimi.softeng.connectionProtocol.client.ClientRemoteInterface;
import it.polimi.softeng.connectionProtocol.client.ClientSideRMI;
import it.polimi.softeng.controller.ServerMessageHandler;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginManagerV2 {
    private ArrayList<String> nickNameList = new ArrayList<>();
    private ArrayList<String> disconnectedPlayerList = new ArrayList<>();
    private ArrayList<String> finalNickNameList = new ArrayList<>();
    private int playerNumber = 4;
    private Boolean numberOfPlayersNotConfirmed = true;
    private ServerMessageHandler serverMessageHandler = null;
    private Map<String, ClientRemoteInterface> nameToStub = new HashMap<>();
    private String status;
    private String gameLobby = "gameLobby";
    private String gameStarted = "gameStarted";

    public LoginManagerV2(ServerMessageHandler serverMessageHandler) {
        status = gameLobby;
        this.serverMessageHandler = serverMessageHandler;
    }

    public void startGame(){
        System.out.println("Reached desired number of player");
        for (String player : nickNameList){
            finalNickNameList.add(player);
            System.out.println(player);
        }
        serverMessageHandler.getController().startGame();
        for(String player : nickNameList){
            if(nameToStub.containsKey(player)){
                try {
                    nameToStub.get(player).startGame();
                    System.out.println("sendind to " + player + " command to start");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("game started !");
        status = gameStarted;
    }

    public void addDisconnectedPlayer(String player){
        //inserting the nickName in the list of nicknames that left the game
        if(status.equals("gameStarted")) {
            disconnectedPlayerList.add(player);
            System.out.println("added " + player + " to disconnected player list");
        }
        //TODO refactor of this code
        //deleting such nickname from active nicknames
        int j = 43;
        for(int i = 0; i < nickNameList.size(); i++){
            if(nickNameList.get(i).equals(player)){
                j = i;
                System.out.println("found string to be removed from the list of active nickNames");
            }
        }
        if(j != 43){
            nickNameList.remove(j);
        }

        if(nameToStub.containsKey(player)) nameToStub.remove(player);
    }

    public void addNickName(String nickName) {
        nickNameList.add(nickName);
        System.out.println(nickName + " entered the game");
        if(nickNameList.size() == playerNumber) startGame();

    }

    public void setPlayerNumber(int playerNumber) {
        if(numberOfPlayersNotConfirmed){
            this.playerNumber = playerNumber;
            System.out.println("number of players is: " + this.playerNumber + " from comand:" + playerNumber);
            numberOfPlayersNotConfirmed = false;
        }
    }

    public void addStub(String nickName, ClientRemoteInterface stub){
        nameToStub.put(nickName,stub);
    }

    /////////getter methods

    public ArrayList<String> getNickNameList() {
        return nickNameList;
    }

    public ArrayList<String> getDisconnectedPlayerList() {
        return disconnectedPlayerList;
    }

    public ArrayList<String> getFinalNickNameList() {
        return finalNickNameList;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public String getStatus() {
        return status;
    }

}
