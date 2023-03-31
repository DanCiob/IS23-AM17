package it.polimi.softeng.JSONParser;

import it.polimi.softeng.model.Tile;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Iterator;

public class ShelfieParser{
    private ArrayList<Tile> tilesToBeInserted = new ArrayList<>();
    private int column;


    public void shelfieParser(String moveMessage){
        JSONParser parser = new JSONParser();

        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) parser.parse(moveMessage);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        JSONArray tiles = (JSONArray) jsonObject.get("tileList");
        Iterator<JSONObject> iterator = tiles.iterator();
        int i = 0;
        while(iterator.hasNext()){
            JSONObject tileJson = (JSONObject) tiles.get(i);
            Tile tile = new Tile((int)tileJson.get("id"), (Tile.TileColor) tileJson.get("color"));
            iterator.next();
            i++;
        }

        column = (int) jsonObject.get("column");
    }

    public ArrayList<Tile> getTilesToBeInserted() {
        return tilesToBeInserted;
    }

    public int getColumn() {
        return column;
    }
}
