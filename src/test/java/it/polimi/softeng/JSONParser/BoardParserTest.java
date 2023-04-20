package it.polimi.softeng.JSONParser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import it.polimi.softeng.model.Board;
import static org.junit.jupiter.api.Assertions.*;

class BoardParserTest {

    @Test
    void boardParserTest1(){
        Board board = new Board();
        int [][] updatedPositions = new int[1][2];
        updatedPositions[0][0] = 0;
        updatedPositions[0][1] = 5;
        JSONObject msg = writeMsg(updatedPositions);
        BoardParser boardParser = new BoardParser();

        boardParser.boardParser(msg.toString(), board);
        assertEquals(0, boardParser.getPositionsToBeRemoved().get(0).getRow());
        assertEquals(5, boardParser.getPositionsToBeRemoved().get(0).getColumn());
    }

    @Test
    void boardParserTest2(){
        Board board = new Board();
        int [][] updatedPositions = new int[2][2];
        updatedPositions[0][0] = 5;
        updatedPositions[0][1] = 3;
        updatedPositions[1][0] = 6;
        updatedPositions[1][1] = 3;
        JSONObject msg = writeMsg(updatedPositions);
        BoardParser boardParser = new BoardParser();

        boardParser.boardParser(msg.toString(), board);
        assertEquals(5, boardParser.getPositionsToBeRemoved().get(0).getRow());
        assertEquals(3, boardParser.getPositionsToBeRemoved().get(0).getColumn());
        assertEquals(6, boardParser.getPositionsToBeRemoved().get(1).getRow());
        assertEquals(3, boardParser.getPositionsToBeRemoved().get(1).getColumn());
    }

    @Test
    void boardParserTest3(){
        Board board = new Board();
        int [][] updatedPositions = new int[3][2];
        updatedPositions[0][0] = 3;
        updatedPositions[0][1] = 0;
        updatedPositions[1][0] = 4;
        updatedPositions[1][1] = 7;
        updatedPositions[2][0] = 4;
        updatedPositions[2][1] = 6;
        JSONObject msg = writeMsg(updatedPositions);
        BoardParser boardParser = new BoardParser();

        boardParser.boardParser(msg.toString(), board);
        assertEquals(3, boardParser.getPositionsToBeRemoved().get(0).getRow());
        assertEquals(0, boardParser.getPositionsToBeRemoved().get(0).getColumn());
        assertEquals(4, boardParser.getPositionsToBeRemoved().get(1).getRow());
        assertEquals(7, boardParser.getPositionsToBeRemoved().get(1).getColumn());
        assertEquals(4, boardParser.getPositionsToBeRemoved().get(2).getRow());
        assertEquals(6, boardParser.getPositionsToBeRemoved().get(2).getColumn());

        int[][] updatedPositions1 = new int[1][2];
        updatedPositions1[0][0] = 4;
        updatedPositions1[0][1] = 4;
        JSONObject msg1 = writeMsg(updatedPositions1);
        boardParser.boardParser(msg1.toString(), board);
        assertEquals(4, boardParser.getPositionsToBeRemoved().get(0).getRow());
        assertEquals(4, boardParser.getPositionsToBeRemoved().get(0).getColumn());
    }



    private JSONObject writeMsg(int[][] updatedPositions){
        JSONObject msg = new JSONObject();
        JSONArray array = new JSONArray();

        for(int[] pos : updatedPositions){
            JSONObject posJSON =  new JSONObject();
            posJSON.put("row", pos[0]);
            posJSON.put("column", pos[1]);
            array.add(posJSON);
        }
        msg.put("board", array);
        return msg;
    }
}
