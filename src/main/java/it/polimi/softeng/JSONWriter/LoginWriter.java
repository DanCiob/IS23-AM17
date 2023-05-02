package it.polimi.softeng.JSONWriter;

import org.json.simple.JSONObject;

public class LoginWriter {

    public static JSONObject writeLogin (String nickname, int gameMode, int startGame, int numOfPlayer) {
        JSONObject obj = new JSONObject();

        obj.put("nickname", nickname);
        obj.put("gameMode", gameMode-48);
        obj.put("startGame", startGame-48);
        obj.put("numOfPlayer", numOfPlayer-48);

        return obj;
    }
}
