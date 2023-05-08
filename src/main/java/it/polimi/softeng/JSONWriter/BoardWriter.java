package it.polimi.softeng.JSONWriter;

import it.polimi.softeng.model.Cell;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import it.polimi.softeng.model.GameBoard;

import static it.polimi.softeng.Constants.boardColumns;
import static it.polimi.softeng.Constants.boardRows;

public class BoardWriter {
    public static JSONObject boardChangeNotifier(GameBoard gameBoard){
        JSONObject jsonObject = new JSONObject();
        JSONArray tilesOfARow= new JSONArray();
        JSONArray rows = new JSONArray();
        JSONObject posColorId, rowObject;

        for(int i=0;i<boardRows;i++){
            //tilesOfARow = new JSONArray();
            //tilesOfARow.clear();
            for(int j=0;j<boardColumns;j++){
                if(gameBoard.getBoard()[i][j]!=null){
                    posColorId = new JSONObject();
                    posColorId.put("row", i);
                    posColorId.put("column", j);
                    posColorId.put("id", gameBoard.getBoard()[i][j].getId());
                    posColorId.put("color", gameBoard.getBoard()[i][j].getColor().toString());
                    //tilesOfARow.add(posColorId);
                    rows.add(posColorId);
                }
            }

            String row = String.valueOf(i);//"Row " +
            //rowObject = new JSONObject();
            //rowObject.put(row, tilesOfARow);
            //rows.add(rowObject);
            jsonObject.put("Tiles", rows);

            JSONArray positionsNotAvailable = new JSONArray();
            JSONObject pos;
            for(Cell cell : gameBoard.getNotAvailable()){
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
