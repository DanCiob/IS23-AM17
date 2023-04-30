package it.polimi.softeng.JSONWriter;

import org.json.simple.JSONObject;

public class SignatureWriter {

    /**
     *
     * @param request is kind of request (@....)
     * @param nickname is nickname of requester
     * @return JSONObject signed with request and nickname
     */
    public static JSONObject signObject (JSONObject obj, String request, String nickname) {

        obj.put("request", request);
        obj.put("requester", nickname);

        return obj;
    }
}
