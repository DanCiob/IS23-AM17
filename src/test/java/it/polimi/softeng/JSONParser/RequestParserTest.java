package it.polimi.softeng.JSONParser;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class RequestParserTest {

    @Test
    void requestParserTest() throws IOException, ParseException {

        JSONParser parser = new JSONParser();

        RequestParser p1 = new RequestParser();
        RequestParser p2 = new RequestParser();
        RequestParser.Request chat1;
        RequestParser.Request game1;
        JSONObject c1 = (JSONObject) parser.parse(new FileReader("src/main/java/it/polimi/softeng/JSONMessages/Test/ChatMessage1.json"));
        JSONObject c2 = (JSONObject) parser.parse(new FileReader("src/main/java/it/polimi/softeng/JSONMessages/Test/GameMove1.json"));

        chat1 = p1.requestParser(c1);
        game1 = p2.requestParser(c2);

        assertEquals("requester", chat1.getRequester());
        assertEquals("@CHAT", chat1.getRequest());
        assertEquals("requester", game1.getRequester());
        assertEquals("@GAME", game1.getRequest());
        assertNotNull(chat1);
        assertNotNull(game1);

    }
}