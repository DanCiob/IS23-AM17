package it.polimi.softeng.JSONParser;

import it.polimi.softeng.model.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Map;

public class PlayerParser {

    public static ArrayList<Player> PlayerAndScoreParser (String message) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(message);
        JSONArray arr = new JSONArray();
        ArrayList<Player> toBeRet = new ArrayList<>();

        arr = (JSONArray) obj.get("playerList");

        for (int i = 0; i < arr.size(); i++)
        {
            JSONObject obj1 = (JSONObject) arr.get(i);
            Player p = new Player((String) obj1.get("name"), (int) (long) obj1.get("score"));

            toBeRet.add(p);
        }

        return toBeRet;
    }
}
