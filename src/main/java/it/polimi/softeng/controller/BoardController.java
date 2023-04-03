package it.polimi.softeng.controller;
import it.polimi.softeng.JSONParser.PersonalCardsParser;
import it.polimi.softeng.model.Cell;
import it.polimi.softeng.model.Tile;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class BoardController {
    private ArrayList<Cell> positionsToBeRemoved = new ArrayList<>();

    public void boardParser(String s){
        int row, column, id;
        Tile.TileColor color;
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

                id = (int) jsonObject.get("id");
                color = PersonalCardsParser.StringToColor((String) jsonObject.get("color"));
                //!!!

                positionsToBeRemoved.add(temp);
            }

        }catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}





        /*int numoftiles = (int) s.chars().filter(x -> x == '-').count() + 1;
        Cell t1 = new Cell();
        Cell t2 = new Cell();
        Cell t3 = new Cell();

        switch (numoftiles) {
            case 1:

                t1.setX((int)s.charAt(5));
                t1.setY((int)s.charAt(8));

                positionsToBeRemoved.add(t1);
            break;

            case 2:
                t1.setX((int)s.charAt(5));
                t1.setY((int)s.charAt(8));
                t1.setX((int)s.charAt(16));
                t1.setY((int)s.charAt(19));

                positionsToBeRemoved.add(t1);
                positionsToBeRemoved.add(t2);
                break;

            case 3:
                t1.setX((int)s.charAt(5));
                t1.setY((int)s.charAt(8));

                t2.setX((int)s.charAt(16));
                t2.setY((int)s.charAt(19));

                t3.setX((int)s.charAt(27));
                t3.setY((int)s.charAt(30));

                positionsToBeRemoved.add(t1);
                positionsToBeRemoved.add(t2);
                positionsToBeRemoved.add(t3);
                break;*/
        //}

    //Call to interface BoardSetter



