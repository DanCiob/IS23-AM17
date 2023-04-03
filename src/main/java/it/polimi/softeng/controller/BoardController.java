package it.polimi.softeng.controller;
import it.polimi.softeng.JSONParser.PersonalCardsParser;
import it.polimi.softeng.model.Board;
import it.polimi.softeng.model.BoardSetter;
import it.polimi.softeng.model.Cell;
import it.polimi.softeng.model.Tile;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.*;

public class BoardController{
    private ArrayList<Cell> positionsToBeRemoved = new ArrayList<>();

    public void boardParser(String s){
        Cell temp;
        JSONParser parser = new JSONParser();
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

            // call BoardSetter
            Board board = new Board();
            board.updateBoard(positionsToBeRemoved);

        }catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}