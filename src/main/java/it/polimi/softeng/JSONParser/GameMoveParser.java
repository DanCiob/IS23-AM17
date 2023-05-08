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
        JSONArray JSONArr = new JSONArray();
        try {
            JSONObject = (JSONObject) parser.parse(gameMove);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        column = (int) (long) JSONObject.get("column");
        requester = (String) JSONObject.get("requester");
        JSONArr = (JSONArray) JSONObject.get("tileList");

        for (int index = 0; index < JSONArr.size(); index++)
        {
            System.out.println("Reading gameMove");

            Cell c = new Cell();
            JSONObject tempCell = (JSONObject) JSONArr.get(index);
            c.setRow((int) (long) tempCell.get("row"));
            c.setColumn((int) (long) tempCell.get("column"));

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
