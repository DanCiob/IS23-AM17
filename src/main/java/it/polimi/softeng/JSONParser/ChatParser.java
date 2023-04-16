package it.polimi.softeng.JSONParser;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ChatParser {
    String receiver;
    String message;

    public void chatParser(String chatMessage){
        JSONParser parser = new JSONParser();

        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) parser.parse(chatMessage);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        receiver = (String) jsonObject.get("receiver");
        message = (String) jsonObject.get("message");

    }

    public String getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return message;
    }
}
