package it.polimi.softeng.JSONParser;

import it.polimi.softeng.model.Cell;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import it.polimi.softeng.model.Board;
import java.util.ArrayList;
import java.util.Iterator;

public class BoardParser {
    public static void boardParser(String s, Board board){
        Cell temp;
        JSONParser parser = new JSONParser();
        ArrayList<Cell> positionsToBeRemoved = new ArrayList<>();
        positionsToBeRemoved.clear();
        try{
            JSONObject jsonObject = (JSONObject) parser.parse(s);
            JSONArray boardTilesList = (JSONArray) jsonObject.get("board");
            Iterator iterator = boardTilesList.iterator();
            while(iterator.hasNext()){
                temp = new Cell();
                temp.setX((int) jsonObject.get("row"));
                temp.setY((int) jsonObject.get("column"));
                positionsToBeRemoved.add(temp);
            }
            board.updateBoard(positionsToBeRemoved);
        }catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
