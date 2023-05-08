package it.polimi.softeng.JSONWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Create JSON containing Common Cards Info
 */
public class CommonCardWriter {

    /**
     * If you play in easy mode then nameOfCommonCard2 will be null
     * @param nameOfCommonCard1 is name of common card 1
     * @param nameOfCommonCard2 is name of common card 2 (null if not present)
     * @return a JSON Object containing common cards
     */
    public static JSONObject writeCommonCard (String nameOfCommonCard1, String nameOfCommonCard2) {
        JSONObject toBeRet = new JSONObject();
        JSONObject obj1 = new JSONObject();
        JSONObject obj2 = new JSONObject();
        JSONArray commonCardsList = new JSONArray();
        int numOfCards = 0;

        obj1.put("name", nameOfCommonCard1);
        commonCardsList.add(obj1);
        numOfCards++;

        if (nameOfCommonCard2 != null) {
            obj2.put("name", nameOfCommonCard2);
            commonCardsList.add(obj2);
            numOfCards++;
        }


        toBeRet.put("numOfCommonCards", numOfCards);
        toBeRet.put("commonCardsList", commonCardsList);

        return toBeRet;
    }
}
