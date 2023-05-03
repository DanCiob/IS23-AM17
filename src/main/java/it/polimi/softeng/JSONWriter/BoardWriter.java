package it.polimi.softeng.JSONWriter;

import it.polimi.softeng.model.Cell;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import it.polimi.softeng.model.Board;

import java.util.ArrayList;

import static it.polimi.softeng.Constants.boardColumns;
import static it.polimi.softeng.Constants.boardRows;

public class BoardWriter {
    public String boardChangeNotifier(Board board, ArrayList<Cell> notAvailable){
        JSONObject jsonObject = new JSONObject();
        JSONArray array;
        JSONObject posAndColor;

        for(int i=0;i<boardRows;i++){
            array = new JSONArray();
            array.clear();
            for(int j=0;j<boardColumns;j++){
                if(board.getBoard()[i][j]!=null){
                    posAndColor = new JSONObject();
                    posAndColor.put("row", i);
                    posAndColor.put("column", j);
                    posAndColor.put("color", board.getBoard()[i][j].getColor());
                    array.add(posAndColor);
                }
            }
            String row = "Row " + i;
            jsonObject.put(row, array);

            JSONArray positionsNotAvailable = new JSONArray();
            JSONObject pos;
            for(Cell cell : notAvailable){
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
        System.out.print(jsonObject);
        return null;
    }
}
