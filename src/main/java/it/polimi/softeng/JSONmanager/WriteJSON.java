package it.polimi.softeng.JSONmanager;

import java.io.*;

import it.polimi.softeng.model.Tile;
import org.json.simple.*;

public class WriteJSON {

    public void WriteToJSON(){

        JSONObject PersonalObjectiveDesc = new JSONObject();
        PersonalObjectiveDesc.put("Cell 0", "0, 0");
        PersonalObjectiveDesc.put("Color", Tile.TileColor.PURPLE);

        JSONObject PersonalObjectiveCard = new JSONObject();
        PersonalObjectiveCard.put("POC", PersonalObjectiveDesc);

        JSONArray PersonalCardList = new JSONArray();
        PersonalCardList.add(PersonalObjectiveCard);

        try (FileWriter testFile = new FileWriter("PersonalCards.json")) {
            testFile.write(PersonalCardList.toJSONString());
            testFile.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }
}
