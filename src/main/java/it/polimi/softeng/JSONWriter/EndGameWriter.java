package it.polimi.softeng.JSONWriter;

import org.json.simple.JSONObject;

public class EndGameWriter {

    public static JSONObject writeEndGame (boolean value) {
        JSONObject obj = new JSONObject();

        obj.put("result", value);

        return obj;
    }
}
