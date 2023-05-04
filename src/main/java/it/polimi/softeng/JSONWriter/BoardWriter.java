package it.polimi.softeng.JSONWriter;

import it.polimi.softeng.model.Cell;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import it.polimi.softeng.model.Board;

import java.util.ArrayList;

import static it.polimi.softeng.Constants.boardColumns;
import static it.polimi.softeng.Constants.boardRows;

public class BoardWriter {
    public JSONObject boardChangeNotifier(Board board){
        JSONObject jsonObject = new JSONObject();
        JSONArray tilesOfARow;
        JSONArray rows = new JSONArray();
        JSONObject posAndColor, rowObject;

        for(int i=0;i<boardRows;i++){
            tilesOfARow = new JSONArray();
            tilesOfARow.clear();
            for(int j=0;j<boardColumns;j++){
                if(board.getBoard()[i][j]!=null){
                    posAndColor = new JSONObject();
                    posAndColor.put("row", i);
                    posAndColor.put("column", j);
                    posAndColor.put("id", board.getBoard()[i][j].getId());
                    posAndColor.put("color", board.getBoard()[i][j].getColor().toString());
                    tilesOfARow.add(posAndColor);
                }
            }
            String row = "Row " + i;
            rowObject = new JSONObject();
            rowObject.put(row, tilesOfARow);
            rows.add(rowObject);
            jsonObject.put("Rows", rows);

            JSONArray positionsNotAvailable = new JSONArray();
            JSONObject pos;
            for(Cell cell : board.getNotAvailable()){
                pos = new JSONObject();
                pos.put("row", cell.getRow());
                pos.put("column", cell.getColumn());
                positionsNotAvailable.add(pos);
            }
            jsonObject.put("notAvailable", positionsNotAvailable);
            /*try (FileWriter file = new FileWriter("c:\\test.json")) {
                file.write(jsonObject.toJSONString());
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
        //System.out.print(jsonObject);
        return jsonObject;
    }
}
