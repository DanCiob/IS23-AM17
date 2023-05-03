package it.polimi.softeng.JSONWriter;

import org.json.simple.JSONObject;

public class ServerSignatureWriter {
    /**
     *
     * @param request is kind of request (@....)
     * @param nickname is nickname of receiver
     * @return JSONObject signed with request and nickname
     */
    public static JSONObject serverSignObject (JSONObject obj, String request, String nickname) {

        obj.put("request", request);
        obj.put("receiver", nickname);

        return obj;
    }
}
