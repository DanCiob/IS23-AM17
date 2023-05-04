package it.polimi.softeng.JSONParser;

import it.polimi.softeng.model.Tile;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import it.polimi.softeng.Constants;
import it.polimi.softeng.model.Shelfie;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * the class implements the parser for the commands regarding the shelfie
 */
public class ShelfieParser{
    private ArrayList<Tile> tilesToBeInserted = new ArrayList<>();
    private int column;

    /**
     * this is the main method of the class; when it's called it clears the list of the tiles to be inserted from the previous use and converts the JSON message containing the
     * infos into tiles and an int column
     * @param moveMessage string containing the JSON message; such message must follow the syntax that can be found in the file {@link it/polimi/softeng/JSONMessages/GameMoveMessage.json}
     *                    with a maximum of 3 tiles
     */
    public void shelfieParser(String moveMessage){
        tilesToBeInserted.clear();
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
            Long id = ((long) tileJson.get("id"));      //necessary workaround as JSON outputs a long value for some reason
            int idInt = id.intValue();

            Tile tile = new Tile(idInt , PersonalCardsParser.StringToColor((String)tileJson.get("color")));
            tilesToBeInserted.add(tile);
            iterator.next();
            i++;
        }
        long jsonColumn = (long) jsonObject.get("column");
        column = (int) jsonColumn;

    }


    /**
     *
     * @param shelfieMessage which is the json  of the entire shelfie
     * @return the object shelfie represented by the json string shelfieMessage
     */
    public Shelfie shelfieFullParser(String shelfieMessage){
        Shelfie shelfie = new Shelfie();
        int row, column;
        Long rowLong, columnLong;
        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) parser.parse(shelfieMessage);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JSONArray tiles = (JSONArray) jsonObject.get("shelfieGrid");
        Iterator<JSONObject> iterator = tiles.iterator();
        int i = 0;
        while(iterator.hasNext()){
            JSONObject tileJson = (JSONObject) tiles.get(i);
            Long id = ((long) tileJson.get("id"));      //necessary workaround as JSON outputs a long value for some reason
            int idInt = id.intValue();
            rowLong = (Long) tileJson.get("row");
            row = rowLong.intValue();
            columnLong = (Long) tileJson.get("column");
            column = columnLong.intValue();
            shelfie.setGrid(row, column, idInt, PersonalCardsParser.StringToColor((String)tileJson.get("color")));
            iterator.next();
            i++;
        }
        return shelfie;
    }

    // these methods may be useless when i make shelfieParser call the methods of interface to modify the model
    /**
     * getter method for the tiles to be inserted from the JSON message
     * @return Arraylist of tiles; the order of the tiles in the vector resembles the order of insertion in the shelfie (ie tile in position 0 goes in first)
     */
    public ArrayList<Tile> getTilesToBeInserted() {
        return tilesToBeInserted;
    }

    /**
     * getter method for the selected column of the insert, from the JSON message
     * @return int that indicates the selected column, numbered from 0 to 4 (sx to dx) (as per chosen convention)
     */
    public int getColumn() {
        return column;
    }

}
