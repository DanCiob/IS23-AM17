package it.polimi.softeng.connectionProtocol.server;

import it.polimi.softeng.JSONWriter.ChatWriter;
import it.polimi.softeng.client.view.CLI.CLI;
import it.polimi.softeng.connectionProtocol.client.ClientRemoteInterface;
import it.polimi.softeng.connectionProtocol.client.ClientSideRMI;
import it.polimi.softeng.controller.ServerMessageHandler;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;
import org.json.simple.JSONObject;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static it.polimi.softeng.Constants.ALL_PLAYERS_DISCONNECTED;
import static it.polimi.softeng.Constants.chatREGEX;
import static it.polimi.softeng.JSONWriter.ErrorWriter.writeError;
import static it.polimi.softeng.JSONWriter.ServerSignatureWriter.serverSignObject;

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
     * this variable represents whether the countdown for the disconnection of all the client except one has begun
     */
    private boolean countdownStarted = false;
    /**
     * this value is the number of seconds the server is going to wait for reconnection of a player to a match that remained with only one player
     */
    int timerValue = 30;            //TODO this variable should be in a json file

    /**
     * constructor method for this class; sets the status to gamelobby
     *
     * @param serverMessageHandler server message handler object
     */
    public LoginManagerV2(ServerMessageHandler serverMessageHandler) {
        status = gameLobby;
        this.serverMessageHandler = serverMessageHandler;
    }

    /**
     * method that gives all the client the command to start the game
     */
    public void startGame() {
        System.out.println("Reached desired number of player");

        serverMessageHandler.getController().startGame();
        for (String player : nickNameList) {
            if (nameToStub.containsKey(player)) {
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
     *
     * @param player player nickname to be disconnected
     */
    public void addDisconnectedPlayer(String player) {
        //deleting such nickname from active nicknames
        boolean flag = false;
        int j = 0;
        for (int i = 0; i < nickNameList.size(); i++) {
            if (nickNameList.get(i).equals(player)) {
                j = i;
                System.out.println("found string to be removed from the list of active nickNames");
                flag = true;
            }
        }
        if (flag) {
            nickNameList.remove(j);
        }

        //removing from name to stub map if the player used rmi
        if (nameToStub.containsKey(player)) nameToStub.remove(player);

        //inserting the nickName in the list of nicknames that left the game
        if (status.equals("gameStarted")) {
            disconnectedPlayerList.add(player);
            System.out.println("added " + player + " to disconnected player list");

            String msg = writeUserDisconnectedMsg(player);
            serverMessageHandler.getController().getChatController().sendChatMessage("all",msg,serverMessageHandler.getController().getServerSide(), "server");

            //in case every player disconnects i kill the server
            if(playerNumber-disconnectedPlayerList.size() == 0){
                System.out.println("every player disconnected, the game ends");
                System.exit(0);
            }
            //checking whether the number of players present in the match is 1; if so the game starts a countdown and blocks the sending of game moves
            if (playerNumber - disconnectedPlayerList.size() == 1) {
                //here you start countdown and block the game
                countdownStarted = true;
                Thread t = new Thread(() -> countdown());
                t.start();
            }


            //notify turn
            if (serverMessageHandler.getController().getGameController().getCurrentGame().getCurrentPlayer().getNickname().equals(player)) {
                serverMessageHandler.getController().getGameController().selectNextPlayer();
                serverMessageHandler.getController().getGameController().notifyTurn();
            }
        }

    }

    /**
     * method used to add a nickname to the nickname list; if the number of players is now the desired one it calls the start of the game
     *
     * @param nickName nickname to add
     */
    public void addNickName(String nickName) {
        nickNameList.add(nickName);
        System.out.println(nickName + " entered the game");

        //command to start game when the number of player has reached the desired amount
        if (nickNameList.size() == playerNumber && !status.equals(gameStarted)) {
            startGame();
        }

        //removing the player from disconnected player list if he was previously there
        if (disconnectedPlayerList.contains(nickName)) {
            boolean flag = false;
            int j = 0;
            for (int i = 0; i < disconnectedPlayerList.size(); i++) {
                if (disconnectedPlayerList.get(i).equals(nickName)) {
                    j = i;
                    System.out.println("found nickName to be removed from the list of disconnected players");

                    String msg = writeUserReconnectedMsg(nickName);
                    serverMessageHandler.getController().getChatController().sendChatMessage("all",msg,serverMessageHandler.getController().getServerSide(), "server");

                    flag = true;
                }
            }
            if (flag) {
                disconnectedPlayerList.remove(j);
            }
            //this stops the countdown in case it started and a players rejoins
            if (countdownStarted) {
                countdownStarted = false;

                String msg = writeCountdownStoppedMsg();
                serverMessageHandler.getController().getChatController().sendChatMessage("all",msg,serverMessageHandler.getController().getServerSide(), "server");
            }

        }
    }

    /**
     * method used to set the number of players
     *
     * @param playerNumber number of players desired
     */
    public void setPlayerNumber(int playerNumber) {
        if (numberOfPlayersNotConfirmed) {
            this.playerNumber = playerNumber;
            System.out.println("number of players is: " + this.playerNumber + " from command:" + playerNumber);
            numberOfPlayersNotConfirmed = false;
        }
    }

    /**
     * method used to add a name to stab entry in the map
     *
     * @param nickName nickname of the client
     * @param stub     client's stub
     */
    public void addStub(String nickName, ClientRemoteInterface stub) {
        nameToStub.put(nickName, stub);
    }

    /////////getter methods

    /**
     * getter method
     *
     * @return list of nicknames of the match
     */
    public ArrayList<String> getNickNameList() {
        return nickNameList;
    }

    /**
     * getter method
     *
     * @return returns the list of disconnected players
     */
    public ArrayList<String> getDisconnectedPlayerList() {
        return disconnectedPlayerList;
    }

    /**
     * getter method
     *
     * @return number of players chosen
     */
    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * getter method
     *
     * @return string indicating game status
     */
    public String getStatus() {
        return status;
    }

    public boolean isCountdownStarted() {
        return countdownStarted;
    }

    private void countdown() {
        String lastPlayer = nickNameList.get(0);
        int secondsElapsed = 0;

        //sending a message informing the player the countdown has started
        String msg = writeCountdownStartedMsg(lastPlayer);
        serverMessageHandler.getController().getChatController().sendChatMessage(lastPlayer,msg,serverMessageHandler.getController().getServerSide(), "system");

        while (countdownStarted) {
            if (secondsElapsed == timerValue) {
                //set winner and kill game
                System.out.println("game ended because nobody rejoined");
                System.exit(0);
            } else {
                try {
                    Thread.sleep(1000);
                    secondsElapsed++;

                    //this will send a message every 5 seconds reminding the player of game end; this will happen every seconds if the time remaining is 5 seconds
                    if(timerValue-secondsElapsed < 5){
                         msg = writeCountdownMsg(secondsElapsed,lastPlayer);
                        serverMessageHandler.getController().getChatController().sendChatMessage(lastPlayer,msg,serverMessageHandler.getController().getServerSide(), "system");
                    }
                    else if(secondsElapsed % 5 == 0){
                         msg = writeCountdownMsg(secondsElapsed,lastPlayer);
                        serverMessageHandler.getController().getChatController().sendChatMessage(lastPlayer,msg,serverMessageHandler.getController().getServerSide(), "system");
                    }

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
    public String writeCountdownStartedMsg (String receiver) {
        JSONObject ChatJSON = new JSONObject();

        String msg = "you're the only remaining player, a countdown will start and if by " + timerValue + " seconds nobody rejoins the game will close";
        ChatJSON.put("message", msg);
        ChatJSON.put("requester","server");
        ChatJSON = serverSignObject(ChatJSON,"@CHAT",receiver);

        return ChatJSON.toJSONString();
    }
    public String writeCountdownMsg (int secondsElapsed, String receiver) {
        JSONObject ChatJSON = new JSONObject();

        String msg = (timerValue-secondsElapsed)+ " seconds before closing the game";
        ChatJSON.put("message", msg);
        ChatJSON.put("requester","server");
        ChatJSON = serverSignObject(ChatJSON,"@CHAT",receiver);

        return ChatJSON.toJSONString();
    }

    public String writeCountdownStoppedMsg () {
        JSONObject ChatJSON = new JSONObject();

        String msg = "a player rejoined, the game can continue";
        ChatJSON.put("message", msg);
        ChatJSON.put("requester","server");
        ChatJSON = serverSignObject(ChatJSON,"@CHAT","all");

        return ChatJSON.toJSONString();
    }

    public String writeUserDisconnectedMsg(String disconnectedUser){
        JSONObject ChatJSON = new JSONObject();

        String msg = "user " + disconnectedUser + " has left the game";
        ChatJSON.put("message", msg);
        ChatJSON.put("requester","server");
        ChatJSON = serverSignObject(ChatJSON,"@CHAT","all");

        return ChatJSON.toJSONString();
    }

    public String writeUserReconnectedMsg(String reconnectedUser){
        JSONObject ChatJSON = new JSONObject();

        String msg = "user " + reconnectedUser + " has rejoined the game";
        ChatJSON.put("message", msg);
        ChatJSON.put("requester","server");
        ChatJSON = serverSignObject(ChatJSON,"@CHAT","all");

        return ChatJSON.toJSONString();
    }
}
