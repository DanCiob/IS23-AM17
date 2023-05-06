package it.polimi.softeng.JSONParser;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class LoginParser {
    String nickname;
    int numOfPlayer;
    int gameMode;
    int startGame;

    /**
     * @param message is login JSON file
     */
    public void loginParser(String message) throws ParseException {

        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(message);

        nickname = (String) obj.get("nickname");
        numOfPlayer = (int) (long) obj.get("numOfPlayer");
        gameMode = (int) (long) obj.get("gameMode");
        startGame = (int) (long) obj.get("startGame");
    }

    public String getNickname() {
        return nickname;
    }

    public int getNumOfPlayer() {
        return numOfPlayer;
    }

    public int getGameMode() {
        return gameMode;
    }

    public int getStartGame() {
        return startGame;
    }
}