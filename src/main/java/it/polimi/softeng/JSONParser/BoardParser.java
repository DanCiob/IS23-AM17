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
    private ArrayList<Cell> positionsToBeRemoved = new ArrayList<>();
    public void boardParser(String s, Board board){
        Cell temp;
        int i;
        Long tempX,tempY;
        JSONParser parser = new JSONParser();
        positionsToBeRemoved.clear();
        try{
            JSONObject jsonObject = (JSONObject) parser.parse(s);
            JSONArray boardPosList = (JSONArray) jsonObject.get("board");
            Iterator<JSONObject> iterator = boardPosList.iterator();
            i=0;
            while(iterator.hasNext() && i<boardPosList.size()){
                JSONObject cell = (JSONObject) boardPosList.get(i);
                tempX = (long) cell.get("row");
                temp = new Cell();
                temp.setX(tempX.intValue());
                tempY = (long) cell.get("column");
                temp.setY(tempY.intValue());
                positionsToBeRemoved.add(temp);
                i++;
            }
            board.updateBoard(positionsToBeRemoved);
        }catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Cell> getPositionsToBeRemoved() {
        return positionsToBeRemoved;
    }
}
