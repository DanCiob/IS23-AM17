package it.polimi.softeng.JSONWriter;

import org.json.simple.JSONObject;

public class LoginWriter {

    public static JSONObject writeLogin (String nickname, int gameMode, int startGame, int numOfPlayer) {
        JSONObject obj = new JSONObject();

        obj.put("nickname", nickname);
        obj.put("gameMode", gameMode);
        obj.put("startGame", startGame);
        obj.put("numOfPlayer", numOfPlayer);

        return obj;
    }
}
