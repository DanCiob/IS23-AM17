package it.polimi.softeng.JSONWriter;

import it.polimi.softeng.model.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class PlayerWriter {

    /**
     * Translate player-score list into its JSON counterpart
     * @param list is player list
     * @return a JSON Object containing encoding of player-score list
     */
    public static JSONObject playerAndScoreWriter (ArrayList<Player> list) {

        JSONObject toBeRet = new JSONObject();
        JSONArray playerList = new JSONArray();

        for (int i = 0; i < list.size(); i++)
        {
            JSONObject obj = new JSONObject();
            obj.put("name", list.get(i).getNickname());
            obj.put("score", list.get(i).getCurrentScore());
            if(list.get(i).isFirst())
                obj.put("isFirst", true);
            else
                obj.put("isFirst", false);
            playerList.add(obj);
        }

        toBeRet.put("playerList", playerList);
        return toBeRet;
    }
}
