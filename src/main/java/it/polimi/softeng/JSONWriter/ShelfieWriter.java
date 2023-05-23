package it.polimi.softeng.JSONWriter;

import it.polimi.softeng.model.Shelfie;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import static it.polimi.softeng.Constants.shelfieRows;
import static it.polimi.softeng.Constants.shelfieColumns;

public class ShelfieWriter {

    public static JSONObject shelfieChangeNotifier(Shelfie shelfie){
        JSONObject jsonObject = new JSONObject();
        JSONArray shelfieGrid = new JSONArray();

        for(int i = 0; i < shelfieColumns; i++) {
            for (int j = 0; j < shelfieColumns; j++) {
                if (shelfie.getTile(i, j) != null) {
                    JSONObject posColorId = new JSONObject();
                    posColorId.put("row", i);
                    posColorId.put("column", j);
                    posColorId.put("id", shelfie.getTile(i, j).getId());
                    posColorId.put("color", shelfie.getTile(i, j).getColor().toString());
                    shelfieGrid.add(posColorId);
                }
            }
        }
        jsonObject.put("shelfieGrid", shelfieGrid);

        return jsonObject;
    }

    public static JSONObject shelfieChangeNotifier(Shelfie shelfie, String owner){
        JSONObject jsonObject = new JSONObject();
        JSONArray shelfieGrid = new JSONArray();

        for(int i = 0; i < shelfieColumns; i++) {
            for (int j = 0; j < shelfieColumns; j++) {
                if (shelfie.getTile(i, j) != null) {
                    JSONObject posColorId = new JSONObject();
                    posColorId.put("row", i);
                    posColorId.put("column", j);
                    posColorId.put("id", shelfie.getTile(i, j).getId());
                    posColorId.put("color", shelfie.getTile(i, j).getColor().toString());
                    shelfieGrid.add(posColorId);
                }
            }
        }
        jsonObject.put("shelfieGrid", shelfieGrid);
        jsonObject.put("owner", owner);

        return jsonObject;
    }
}
