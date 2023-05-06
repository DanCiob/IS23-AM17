package it.polimi.softeng.JSONParser;

import it.polimi.softeng.model.Cell;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import it.polimi.softeng.model.GameBoard;
import it.polimi.softeng.model.Tile;
import java.util.ArrayList;
import java.util.Iterator;

public class BoardParser {
    private ArrayList<Cell> positionsToBeRemoved = new ArrayList<>();
    public void boardParser(String s, GameBoard board){
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
                temp.setRow(tempX.intValue());
                tempY = (long) cell.get("column");
                temp.setColumn(tempY.intValue());
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


    public GameBoard gameBoardFullParser(String boardMessage) {
        GameBoard gameBoard = new GameBoard();
        Cell cell = new Cell();
        JSONParser parser = new JSONParser();
        JSONObject jsonBoard;
        Long c, r;

        try {
            jsonBoard = (JSONObject) parser.parse(boardMessage);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        //notAvailable Cells
        JSONArray notAvailableArray = (JSONArray) jsonBoard.get("notAvailable"); //the array of not available
        Iterator<JSONArray> iterator = notAvailableArray.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            JSONObject notAvailableCell = (JSONObject) notAvailableArray.get(i);

            c = (long) notAvailableCell.get("column");
            cell.setColumn(c.intValue());
            r = (long) notAvailableCell.get("row");
            cell.setRow(r.intValue());
            gameBoard.getNotAvailable().add(cell);

            i++;
            iterator.next();
        }

        //Rows
        JSONArray rows = (JSONArray) jsonBoard.get("Tiles"); //rows = outer array of rows
//        JSONObject rows = (JSONObject) jsonBoard.get("Rows");
        Iterator<JSONObject> iterator1 = rows.iterator();
        int j = 0;
        int k;
        Long id, row, column;

        while (iterator1.hasNext()) { //j<9
            JSONObject jso = (JSONObject) rows.get(j);
            id = (long) jso.get("id");
            Tile tile = new Tile(id.intValue(), PersonalCardsParser.StringToColor((String) jso.get("color")));
            row = (long) jso.get("row");
            column = (long) jso.get("column");
            gameBoard.setBoard(row.intValue(), column.intValue(), tile);

            //JSONArray rowOfTiles = (JSONArray) jso.get(j); //rowOfTiles = array of tiles
            //System.out.println(rowOfTiles);
            //JSONObject rowOfTiles = (JSONObject) rows.get(j);
            //Iterator<JSONObject> iterator2 = rowOfTiles.iterator();
            //k = 0;

            //while (iterator2.hasNext()) {
                //JSONObject posColorId = (JSONObject) rowOfTiles.get(k);
                /*id = (long) posColorId.get("id");
                Tile tile = new Tile(id.intValue(), PersonalCardsParser.StringToColor((String) posColorId.get("color")));
                row = (long) posColorId.get("rowOfTiles");
                gameBoard.setBoard(row.intValue(), (int)posColorId.get("column"), tile);


                k++;
                iterator2.next();*/
           // }

            j++;
            iterator1.next();
        }

        return gameBoard;
    }
}
