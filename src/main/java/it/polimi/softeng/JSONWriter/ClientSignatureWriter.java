package it.polimi.softeng.JSONWriter;

import org.json.simple.JSONObject;

public class ClientSignatureWriter {

    /**
     *
     * @param request is kind of request (@....)
     * @param nickname is nickname of requester
     * @return JSONObject signed sent by client with request and nickname
     */
    public static JSONObject clientSignObject (JSONObject obj, String request, String nickname) {

        obj.put("request", request);
        obj.put("requester", nickname);

        return obj;
    }
}
