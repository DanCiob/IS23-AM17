package it.polimi.softeng.JSONWriter;

import org.json.simple.JSONObject;

public class ConfirmWriter {

    public static JSONObject writeConfirm () {

        JSONObject obj = new JSONObject();

        obj.put("confirm", "Your turn");

        return obj;

    }
}
