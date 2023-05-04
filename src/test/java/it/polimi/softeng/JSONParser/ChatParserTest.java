package it.polimi.softeng.JSONParser;

import it.polimi.softeng.model.Tile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ChatParserTest {

    private JSONObject writeChatMessage(String recipient,String message){
        JSONObject msg = new JSONObject();

        msg.put("receiver",recipient);
        msg.put("message", message);

        return msg;
    }

    private JSONObject writeChatMessage(String message){
        JSONObject msg = new JSONObject();

        msg.put("message", message);

        return msg;
    }

    @Test
    public void publicMessageTest(){
        ChatParser chatParser = new ChatParser();
        JSONObject msg = writeChatMessage("helo");


        chatParser.chatParser(msg.toJSONString());
        assertEquals("helo", chatParser.getMessage());
        assertNull(chatParser.getReceiver());
    }

    @Test
    public void privateMessageTest(){
        ChatParser chatParser = new ChatParser();

        JSONObject msg = writeChatMessage( "user1","helo");


        chatParser.chatParser(msg.toJSONString());
        assertEquals("helo", chatParser.getMessage());
        assertEquals("user1", chatParser.getReceiver());
    }
}