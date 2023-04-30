package it.polimi.softeng.JSONWriter;

import it.polimi.softeng.customExceptions.IllegalInsertException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static it.polimi.softeng.Constants.gameREGEX;

//To suppress warning related to JSON simple library structure insert following @SuppressWarning
//@SuppressWarnings("unchecked")
public class GameMoveWriter {

    /**
     * This function verifies if received gameMove follows regex format for gameMove
     *
     * @return true if move follows gameMoveRegex format
     */
    public static boolean gameMoveRegex(String gameMove) {
        String gameMoveFiltered = gameMove.replaceAll(" ", "");

        //Following regex identifies gameMove format EX: (0,1),(0,2),2
        Pattern pattern = Pattern.compile(gameREGEX);
        //Check if gameMove follows regex
        Matcher matcher = pattern.matcher(gameMoveFiltered);

        //Ensures that gameMove follows regex
        if (!matcher.matches())
            return false;

        class coupleInt {
            int row;
            int column;
        }
        coupleInt t1 = new coupleInt(), t2 = new coupleInt(), t3 = new coupleInt();
        int numOfTiles = switch (gameMoveFiltered.length()) {
            //One tile (x,y),z
            case 7 -> 1;
            //Two tile (x,y),(a,b),z
            case 13 -> 2;
            //Three tile (x,y),(a,b),(c,d),z
            case 19 -> 3;
            default -> 0;
        };

        switch (numOfTiles) {
            case 2 -> {
                t1.row = gameMoveFiltered.charAt(1);
                t1.column = gameMoveFiltered.charAt(3);
                t2.row = gameMoveFiltered.charAt(7);
                t2.column = gameMoveFiltered.charAt(9);

                //Removed tiles must be different
                if (t1.row == t2.row && t1.column == t2.column)
                    return false;
            }
            case 3 -> {
                t1.row = gameMoveFiltered.charAt(1);
                t1.column = gameMoveFiltered.charAt(3);
                t2.row = gameMoveFiltered.charAt(7);
                t2.column = gameMoveFiltered.charAt(9);
                t3.row = gameMoveFiltered.charAt(13);
                t3.column = gameMoveFiltered.charAt(15);

                //Removed tiles must be different
                if (t1.row == t2.row && t1.column == t2.column || t1.row == t3.row && t1.column == t3.column || t3.row == t2.row && t3.column == t2.column)
                    return false;
            }
        }
        return true;
    }

    /**
     * @param gameMove contains string with gamemove
     * @return a JSONObject containing gamemode translated in JSON format
     */
    public static JSONObject writeGameMove(String gameMove) throws IllegalInsertException {
        //Remove spaces in gameMove
        String gameMoveFiltered = gameMove.replace(" ", "");
        JSONObject GameMoveJSON = new JSONObject();
        JSONArray GameMoveArray = new JSONArray();
        JSONObject o1 = new JSONObject();
        JSONObject o2 = new JSONObject();
        JSONObject o3 = new JSONObject();

        //Double check of syntax correcteness
        if(!gameMoveRegex(gameMoveFiltered))
            throw new IllegalInsertException("Error, syntax not respected");
        class coupleInt {
            int row;
            int column;
        }
        coupleInt t1 = new coupleInt(), t2 = new coupleInt(), t3 = new coupleInt();
        int column;
        int numOfTiles = switch (gameMoveFiltered.length()) {
            //One tile (x,y),z
            case 7 -> 1;
            //Two tile (x,y),(a,b),z
            case 13 -> 2;
            //Three tile (x,y),(a,b),(c,d),z
            case 19 -> 3;
            default -> 0;
        };

        switch (numOfTiles) {
            case 1 -> {
                t1.row = gameMoveFiltered.charAt(1);
                t1.column = gameMoveFiltered.charAt(3);
                column = gameMoveFiltered.charAt(6);
                GameMoveJSON.put("column", column - 48);
                o1.put("row", t1.row - 48);
                o1.put("column", t1.column - 48);
                GameMoveArray.add(o1);
                GameMoveJSON.put("tileList", GameMoveArray);
            }
            case 2 -> {
                t1.row = gameMoveFiltered.charAt(1);
                t1.column = gameMoveFiltered.charAt(3);
                t2.row = gameMoveFiltered.charAt(7);
                t2.column = gameMoveFiltered.charAt(9);
                column = gameMoveFiltered.charAt(12);
                GameMoveJSON.put("column", column - 48);
                o1.put("row", t1.row - 48);
                o1.put("column", t1.column - 48);
                o2.put("row", t2.row - 48);
                o2.put("column", t2.column - 48);
                GameMoveArray.add(o1);
                GameMoveArray.add(o2);
                GameMoveJSON.put("tileList", GameMoveArray);
            }
            case 3 -> {
                t1.row = gameMoveFiltered.charAt(1);
                t1.column = gameMoveFiltered.charAt(3);
                t2.row = gameMoveFiltered.charAt(7);
                t2.column = gameMoveFiltered.charAt(9);
                t3.row = gameMoveFiltered.charAt(13);
                t3.column = gameMoveFiltered.charAt(15);
                column = gameMoveFiltered.charAt(18);
                GameMoveJSON.put("column", column - 48);
                o1.put("row", t1.row - 48);
                o1.put("column", t1.column - 48);
                o2.put("row", t2.row - 48);
                o2.put("column", t2.column - 48);
                o3.put("row", t3.row - 48);
                o3.put("column", t3.column - 48);
                GameMoveArray.add(o1);
                GameMoveArray.add(o2);
                GameMoveArray.add(o3);
                GameMoveJSON.put("tileList", GameMoveArray);
            }
        }


        return GameMoveJSON;
    }
}
