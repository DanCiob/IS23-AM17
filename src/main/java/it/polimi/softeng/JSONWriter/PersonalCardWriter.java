package it.polimi.softeng.JSONWriter;

import it.polimi.softeng.model.PersonalCards;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class PersonalCardWriter {

    /**
     *
     * @param pc is personal card to encode in JSON
     * @return a JSON object containing personal card
     */
    public static JSONObject writePersonalCard (PersonalCards pc) {
        JSONObject toBeRet = new JSONObject();
        JSONArray pcArray = new JSONArray();

        for (int i = 0; i < 6; i++) {
            JSONObject currentObj = new JSONObject();

            currentObj.put("row", pc.getObjective()[i].getRow());
            currentObj.put("column", pc.getObjective()[i].getColumn());
            currentObj.put("color", pc.getObjective()[i].getColor().toString());

            pcArray.add(currentObj);
        }

        toBeRet.put("personalCard", pcArray);

        return toBeRet;
    }

}
