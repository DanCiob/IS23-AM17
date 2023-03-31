package it.polimi.softeng.JSONParser;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShelfieParserTest {
    private String writeMsg(int id1, String color1, int id2, String color2, int id3, String color3, int column){
        JSONObject msg = new JSONObject();

        msg.put("column", column);
    }
    @Test
    public void msgTest(){
    }
}