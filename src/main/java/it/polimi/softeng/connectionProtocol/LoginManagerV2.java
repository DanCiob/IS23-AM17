package it.polimi.softeng.connectionProtocol;

import it.polimi.softeng.controller.ServerMessageHandler;

import java.util.ArrayList;

public class LoginManagerV2 {
    private ArrayList<String> nickNameList = new ArrayList<>();
    private ArrayList<String> disconnectedPlayerList = new ArrayList<>();
    private ArrayList<String> finalNickNameList = new ArrayList<>();
    private int playerNumber = 4;
    private Boolean numberOfPlayersNotConfirmed = true;
    private ServerMessageHandler serverMessageHandler = null;
    private String status;
    private String gameLobby = "gameLobby";
    private String gameStarted = "gameStarted";

    public LoginManagerV2(ServerMessageHandler serverMessageHandler) {
        status = gameLobby;
        this.serverMessageHandler = serverMessageHandler;
        //da mettere in serversideRMI

        /*Thread t = new Thread(() -> pingRMIUsers());
        t.start();*/
    }

    public void startGame(){
        System.out.println("Reached desired number of player");
        for (String player : nickNameList){
            finalNickNameList.add(player);
        }
        serverMessageHandler.getController().startGame();
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
    }

    public void addNickName(String nickName) {
        nickNameList.add(nickName);
        if(nickNameList.size() == playerNumber) startGame();
        System.out.println(nickName + " entered the game");
    }

    public void setPlayerNumber(int playerNumber) {
        if(numberOfPlayersNotConfirmed){
            this.playerNumber = playerNumber;
            numberOfPlayersNotConfirmed = false;
        }
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
