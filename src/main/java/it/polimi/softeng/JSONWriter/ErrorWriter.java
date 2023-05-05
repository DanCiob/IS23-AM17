package it.polimi.softeng.JSONWriter;

import org.json.simple.JSONObject;

public class ErrorWriter {

    public static JSONObject writeError (String error) {
        JSONObject obj = new JSONObject();

        obj.put("errorType", error);

        return obj;
    }
}
