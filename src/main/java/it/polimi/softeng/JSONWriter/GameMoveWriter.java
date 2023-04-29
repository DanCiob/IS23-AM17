package it.polimi.softeng.JSONWriter;

import org.json.simple.JSONObject;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameMoveWriter {
    private static final int maxGameMoveLength = 20;

    /**
     * This function verifies if received gameMove follows regex format for gameMove
     *
     * @return true if move follows gameMoveRegex format
     */
    public static boolean gameMoveRegex(String gameMove) {
        String gameMoveFiltered = gameMove.replaceAll(" ", "");

        //Following regex identifies gameMove format EX: (0,1),(0,2),2
        String regex = "^([(][0-9][,][0-9][)][,]){1,3}[0-4]$";
        Pattern pattern = Pattern.compile(regex);
        //Check if gameMove follows regex
        Matcher matcher = pattern.matcher(gameMoveFiltered);

        //Ensures that gameMove follows regex
        if (!matcher.matches())
            return false;

        class coupleInt {
            int r;
            int c;
        }
        coupleInt t1 = new coupleInt(), t2 = new coupleInt(), t3 = new coupleInt();
        int columnInsert;
        int numOfTiles = 0;

        switch (gameMoveFiltered.length()) {
            //One tile (x,y),z
            case 7:
                numOfTiles = 1;
                break;
            //Two tile (x,y),(a,b),z
            case 13:
                numOfTiles = 2;
                break;
            //Three tile (x,y),(a,b),(c,d),z
            case 19:
                numOfTiles = 3;
                break;
        }

        switch (numOfTiles) {
            case 1:
                t1.r = (int) gameMoveFiltered.charAt(1);
                t1.c = (int) gameMoveFiltered.charAt(3);
                columnInsert = (int) gameMoveFiltered.charAt(6);
                break;
            case 2:
                t1.r = (int) gameMoveFiltered.charAt(1);
                t1.c = (int) gameMoveFiltered.charAt(3);
                t2.r = (int) gameMoveFiltered.charAt(7);
                t2.c = (int) gameMoveFiltered.charAt(9);
                columnInsert = (int) gameMoveFiltered.charAt(12);

                //Removed tiles must be different
                if (t1.equals(t2))
                    return false;
                break;
            case 3:
                t1.r = (int) gameMoveFiltered.charAt(1);
                t1.c = (int) gameMoveFiltered.charAt(3);
                t2.r = (int) gameMoveFiltered.charAt(7);
                t2.c = (int) gameMoveFiltered.charAt(9);
                t3.r = (int) gameMoveFiltered.charAt(13);
                t3.c = (int) gameMoveFiltered.charAt(15);
                columnInsert = (int) gameMoveFiltered.charAt(18);

                //Removed tiles must be different
                if (t1.equals(t2) || t1.equals(t3) || t2.equals(t3))
                    return false;
                break;
        }
        return true;
    }

    public static JSONObject writeGameMove(String gameMove) {
        //Remove spaces in gameMove
        gameMove = gameMove.replace(" ", "");


        return null;
    }
}
