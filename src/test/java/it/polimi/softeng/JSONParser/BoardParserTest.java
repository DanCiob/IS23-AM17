package it.polimi.softeng.JSONParser;

import it.polimi.softeng.JSONWriter.BoardWriter;
import it.polimi.softeng.model.GameBoard;
import it.polimi.softeng.model.Tile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardParserTest {

    @Test
    void boardParserTest1(){
        GameBoard gameBoard = new GameBoard();
        int [][] updatedPositions = new int[1][2];
        updatedPositions[0][0] = 0;
        updatedPositions[0][1] = 5;
        JSONObject msg = writeMsg(updatedPositions);
        BoardParser boardParser = new BoardParser();

        boardParser.boardParser(msg.toString(), gameBoard);
        assertEquals(0, boardParser.getPositionsToBeRemoved().get(0).getRow());
        assertEquals(5, boardParser.getPositionsToBeRemoved().get(0).getColumn());
    }

    @Test
    void boardParserTest2(){
        GameBoard gameBoard = new GameBoard();
        int [][] updatedPositions = new int[2][2];
        updatedPositions[0][0] = 5;
        updatedPositions[0][1] = 3;
        updatedPositions[1][0] = 6;
        updatedPositions[1][1] = 3;
        JSONObject msg = writeMsg(updatedPositions);
        BoardParser boardParser = new BoardParser();

        boardParser.boardParser(msg.toString(), gameBoard);
        assertEquals(5, boardParser.getPositionsToBeRemoved().get(0).getRow());
        assertEquals(3, boardParser.getPositionsToBeRemoved().get(0).getColumn());
        assertEquals(6, boardParser.getPositionsToBeRemoved().get(1).getRow());
        assertEquals(3, boardParser.getPositionsToBeRemoved().get(1).getColumn());
    }

    @Test
    void boardParserTest3(){
        GameBoard gameBoard = new GameBoard();
        int [][] updatedPositions = new int[3][2];
        updatedPositions[0][0] = 3;
        updatedPositions[0][1] = 0;
        updatedPositions[1][0] = 4;
        updatedPositions[1][1] = 7;
        updatedPositions[2][0] = 4;
        updatedPositions[2][1] = 6;
        JSONObject msg = writeMsg(updatedPositions);
        BoardParser boardParser = new BoardParser();

        boardParser.boardParser(msg.toString(), gameBoard);
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
        boardParser.boardParser(msg1.toString(), gameBoard);
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

    @Test
    void gameBoardFullParserTest() {
        GameBoard gameBoard = new GameBoard();
        String msg;

        gameBoard.resetBoard(2);

        //board
        Tile t1 = new Tile(0, Tile.TileColor.WHITE);
        gameBoard.setBoard(3, 7, t1);

        Tile t2 = new Tile(1, Tile.TileColor.BLUE);
        gameBoard.setBoard(6, 4, t2);

        Tile t3 = new Tile(2, Tile.TileColor.GREEN);
        gameBoard.setBoard(1, 3, t3);

        //msg = writeMsgFullBoard(gameBoard);
        BoardWriter boardWriter = new BoardWriter();
        msg = boardWriter.boardChangeNotifier(gameBoard).toString();

        BoardParser boardParser = new BoardParser();
        GameBoard gameBoard1 = boardParser.gameBoardFullParser(msg);

        assertEquals(gameBoard.getBoard()[3][7].getId(), gameBoard1.getBoard()[3][7].getId());
        assertEquals(gameBoard.getBoard()[6][4].getId(), gameBoard1.getBoard()[6][4].getId());
        assertEquals(gameBoard.getBoard()[1][3].getId(), gameBoard1.getBoard()[1][3].getId());

        assertEquals(gameBoard.getBoard()[3][7].getColor(), gameBoard1.getBoard()[3][7].getColor());
        assertEquals(gameBoard.getBoard()[6][4].getColor(), gameBoard1.getBoard()[6][4].getColor());
        assertEquals(gameBoard.getBoard()[1][3].getColor(), gameBoard1.getBoard()[1][3].getColor());

    }

   /* private String writeMsgFullBoard(GameBoard gameBoard){
        JSONObject msg = new JSONObject();

        JSONArray notAvailable = new JSONArray();
        for (Cell c : gameBoard.getNotAvailable()){
            JSONObject cell = new JSONObject();
            cell.put("column", c.getColumn());
            cell.put("row", c.getRow());
            notAvailable.add(cell);
        }
        msg.put("notAvailable", notAvailable);

        JSONArray rows = new JSONArray(); //rows: array of rows
        for (int i = 0; i<boardRows; i++){
            JSONArray rowOfTiles = new JSONArray(); // rowOfTiles: array of tiles
            for (int j = 0; j<boardColumns; j++){
                if (gameBoard.getBoard()[i][j] != null){
                    JSONObject posColorId = new JSONObject();
                    posColorId.put("color", gameBoard.getBoard()[i][j].getColor().toString());
                    posColorId.put("column", j);
                    posColorId.put("row", i);
                    posColorId.put("id", gameBoard.getBoard()[i][j].getId());
                    rowOfTiles.add(posColorId);
                }
            }
            rows.add("Row " + i);
        }
        msg.put("Rows", rows);

        return msg.toString();
    }*/
}
