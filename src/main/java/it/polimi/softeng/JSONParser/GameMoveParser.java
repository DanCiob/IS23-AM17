package it.polimi.softeng.JSONParser;

import it.polimi.softeng.model.Board;
import it.polimi.softeng.model.Tile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GameMoveParser {
    //Integer identifies order of insertion
    Map<Integer, Tile> chosenTile = new HashMap<>();
    //Arraylist containing tiles to be removed
    ArrayList <Tile> tilesToBeRemoved = new ArrayList<>();

    /**
     *
     * @param gameMove contains from one to three tiles and column of insertion
     */
    public void GameMoveParser (String gameMove, Board board)
    {
        JSONParser parser = new JSONParser();

        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) parser.parse(gameMove);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }




    }
}
