package it.polimi.softeng.JSONParser;

import it.polimi.softeng.model.Cell;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Iterator;

public class GameMoveParser {
    //Arraylist containing tiles to be removed
    ArrayList <Cell> tilesToBeRemoved = new ArrayList<>();
    int column;
    String requester;

    /**
     *
     * @param gameMove contains from one to three tiles and column of insertion
     */
    public void gameMoveParser(String gameMove)
    {
        ArrayList<Cell> tilesToBeRemoved = new ArrayList<>();
        JSONParser parser = new JSONParser();
        JSONObject JSONObject = new JSONObject();
        JSONArray JSONArray = new JSONArray();
        try {
            JSONObject = (JSONObject) parser.parse(gameMove);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        column = (int) JSONObject.get("column");
        requester = (String) JSONObject.get("requester");
        JSONArray = (JSONArray) JSONObject.get("tileList");
        Iterator i = JSONArray.iterator();
        int index = 0;

        while(i.hasNext())
        {
            Cell c = new Cell();
            JSONObject tempCell = (JSONObject) JSONArray.get(index);
            c.setRow((int) tempCell.get("row"));
            c.setColumn((int) tempCell.get("column"));

            tilesToBeRemoved.add(c);
        }
    }

    public ArrayList<Cell> getTilesToBeRemoved() {
        return tilesToBeRemoved;
    }

    public int getColumn() {
        return column;
    }

    public String getRequester() {
        return requester;
    }
}
