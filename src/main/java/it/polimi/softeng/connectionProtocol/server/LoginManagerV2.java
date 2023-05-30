package it.polimi.softeng.connectionProtocol.server;

import it.polimi.softeng.client.view.CLI.CLI;
import it.polimi.softeng.connectionProtocol.client.ClientRemoteInterface;
import it.polimi.softeng.connectionProtocol.client.ClientSideRMI;
import it.polimi.softeng.controller.ServerMessageHandler;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * class that manages the logins from both rmi and socket users for a match
 */
public class LoginManagerV2 {
    /**
     * list of nicknames in this match
     */
    private ArrayList<String> nickNameList = new ArrayList<>();
    /**
     * name of players who disconnected mid game from this match
     */
    private ArrayList<String> disconnectedPlayerList = new ArrayList<>();
    /**
     * default number of players, if chosen otherwise this value changes
     */
    private int playerNumber = 4;
    /**
     * boolean flag
     */
    private Boolean numberOfPlayersNotConfirmed = true;
    /**
     * server message handler reference
     */
    private ServerMessageHandler serverMessageHandler = null;
    /**
     * map that connects the identity of a player to it's stub
     */
    private Map<String, ClientRemoteInterface> nameToStub = new HashMap<>();
    /**
     * string representing the status of the game; it can be "gameLobby" or "gameStarted"
     */
    private String status;
    /**
     * string stating that the game hasn't started yet
     */
    private String gameLobby = "gameLobby";
    /**
     * string stating that the game has started
     */
    private String gameStarted = "gameStarted";

    /**
     * constructor method for this class; sets the status to gamelobby
     * @param serverMessageHandler server message handler object
     */
    public LoginManagerV2(ServerMessageHandler serverMessageHandler) {
        status = gameLobby;
        this.serverMessageHandler = serverMessageHandler;
    }

    /**
     * method that gives all the client the command to start the game
     */
    public void startGame(){
        System.out.println("Reached desired number of player");

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

    /**
     * if a player disconnects during the match it's name it's inserted from this method to the list of disconnected players
     * @param player player nickname to be disconnected
     */
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

    /**
     * method used to add a nickname to the nickname list; if the number of players is now the desired one it calls the start of the game
     * @param nickName nickname to add
     */
    public void addNickName(String nickName) {
        nickNameList.add(nickName);
        System.out.println(nickName + " entered the game");
        if(nickNameList.size() == playerNumber && !status.equals(gameStarted)) {
            startGame();
        }

    }

    /**
     * method used to set the number of players
     * @param playerNumber number of players desired
     */
    public void setPlayerNumber(int playerNumber) {
        if(numberOfPlayersNotConfirmed){
            this.playerNumber = playerNumber;
            System.out.println("number of players is: " + this.playerNumber + " from command:" + playerNumber);
            numberOfPlayersNotConfirmed = false;
        }
    }

    /**
     * method used to add a name to stab entry in the map
     * @param nickName nickname of the client
     * @param stub client's stub
     */
    public void addStub(String nickName, ClientRemoteInterface stub){
        nameToStub.put(nickName,stub);
    }

    /////////getter methods

    /**
     * getter method
     * @return list of nicknames of the match
     */
    public ArrayList<String> getNickNameList() {
        return nickNameList;
    }

    /**
     * getter method
     * @return returns the list of disconnected players
     */
    public ArrayList<String> getDisconnectedPlayerList() {
        return disconnectedPlayerList;
    }

    /**
     * getter method
     * @return number of players chosen
     */
    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * getter method
     * @return string indicating game status
     */
    public String getStatus() {
        return status;
    }

}
