package it.polimi.softeng.JSONParser;

import it.polimi.softeng.customExceptions.IllegalInsertException;
import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import it.polimi.softeng.model.Cell;
import java.util.ArrayList;

class ShelfieParserTest {
    private String colorToString(Tile.TileColor color){
        switch(color){
            case WHITE ->{
                return "WHITE";
            }
            case BLUE -> {
                return "BLUE";
            }
            case CYAN -> {
                return "CYAN";
            }
            case YELLOW -> {
                return "YELLOW";
            }
            case GREEN -> {
                return "GREEN";
            }
            case PURPLE -> {
                return "PURPLE";
            }
            default -> {
                return "ERROR";
            }
        }
    }

    private JSONObject writemsg(ArrayList<Tile> tileList, int column){
        JSONObject msg = new JSONObject();

        JSONArray list = new JSONArray();
        int i = 0;
        for(Tile tile : tileList){
            JSONObject jsonTile = new JSONObject();
            jsonTile.put("id", tileList.get(i).getId());
            jsonTile.put("color", colorToString(tileList.get(i).getColor()));
            list.add(jsonTile);
            i++;
        }
        msg.put("tileList",list);
        msg.put("column", column);

        return msg;
    }
    @Test
    public void msgTest(){
        ArrayList<Tile> tiles = new ArrayList<>();

        Tile tile1 = new Tile(1, Tile.TileColor.BLUE);
        Tile tile2 = new Tile(2, Tile.TileColor.WHITE);
        Tile tile3 = new Tile(3, Tile.TileColor.GREEN);
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);

        JSONObject jsonMsg = writemsg(tiles, 1);

        ShelfieParser parser = new ShelfieParser();

        parser.shelfieParser(jsonMsg.toString());

        assertEquals(1, parser.getTilesToBeInserted().get(0).getId());
        assertEquals(2, parser.getTilesToBeInserted().get(1).getId());
        assertEquals(3, parser.getTilesToBeInserted().get(2).getId());
        assertEquals("BLUE",colorToString(parser.getTilesToBeInserted().get(0).getColor()));
        assertEquals("WHITE",colorToString(parser.getTilesToBeInserted().get(1).getColor()));
        assertEquals("GREEN",colorToString(parser.getTilesToBeInserted().get(2).getColor()));
        assertEquals(1,parser.getColumn());
    }

    @Test
    public void clearTest(){
        ArrayList<Tile> tiles = new ArrayList<>();

        Tile tile1 = new Tile(1, Tile.TileColor.BLUE);
        Tile tile2 = new Tile(2, Tile.TileColor.WHITE);
        Tile tile3 = new Tile(3, Tile.TileColor.GREEN);
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);

        JSONObject jsonMsg = writemsg(tiles, 1);

        ShelfieParser parser = new ShelfieParser();

        parser.shelfieParser(jsonMsg.toString());

        tiles.clear();
        tile1 = new Tile(4, Tile.TileColor.BLUE);
        tile2 = new Tile(5, Tile.TileColor.WHITE);
        tiles.add(tile1);
        tiles.add(tile2);

        JSONObject jsonMsg2 = writemsg(tiles, 3);
        parser.shelfieParser(jsonMsg2.toString());

        assertEquals(4, parser.getTilesToBeInserted().get(0).getId());
        assertEquals(5, parser.getTilesToBeInserted().get(1).getId());
        assertEquals("BLUE",colorToString(parser.getTilesToBeInserted().get(0).getColor()));
        assertEquals("WHITE",colorToString(parser.getTilesToBeInserted().get(1).getColor()));
        assertEquals(3,parser.getColumn());
    }


    @Test
    public void shelfieFullParsertest(){
        Cell cell, cell1;
        ArrayList<Tile> tiles = new ArrayList<>();
        ArrayList<Cell> pos = new ArrayList<>();

        tiles.add(new Tile(0, Tile.TileColor.BLUE));
        cell = new Cell();
        cell.setRow(0);
        cell.setColumn(0);
        pos.add(0, cell);

        tiles.add(new Tile(1, Tile.TileColor.GREEN));
        cell = new Cell();
        cell.setRow(1);
        cell.setColumn(0);
        pos.add(1, cell);
        tiles.add(new Tile(2, Tile.TileColor.PURPLE));
        cell1 = new Cell();
        cell1.setRow(2);
        cell1.setColumn(0);
        pos.add(2, cell1);
        tiles.clear();

        tiles.add(new Tile(3, Tile.TileColor.WHITE));
        cell = new Cell();
        cell.setRow(0);
        cell.setColumn(1);
        pos.add(3, cell);
        tiles.add(new Tile(4, Tile.TileColor.BLUE));
        cell1 = new Cell();
        cell1.setRow(1);
        cell1.setColumn(1);
        pos.add(4, cell1);
        tiles.clear();

        tiles.add(new Tile(5, Tile.TileColor.CYAN));
        cell = new Cell();
        cell.setRow(0);
        cell.setColumn(4);
        pos.add(5, cell);
        tiles.add(new Tile(6, Tile.TileColor.BLUE));
        cell1 = new Cell();
        cell1.setRow(1);
        cell1.setColumn(4);
        pos.add(6, cell1);


        ShelfieParser shelfieParser = new ShelfieParser();
        Shelfie shelfie = shelfieParser.shelfieFullParser(writemsgFullShelfie(tiles, pos));

        int row, column;
        for(Tile tile : tiles){
            row = pos.get(tile.getId()).getRow();
            column = pos.get(tile.getId()).getColumn();
            assertTrue(shelfie.getGrid()[row][column]!=null && shelfie.getGrid()[row][column].getColor() == tile.getColor());
        }
    }

    private String writemsgFullShelfie(ArrayList<Tile> tileList, ArrayList<Cell> positions){
        JSONObject msg = new JSONObject();

        JSONArray list = new JSONArray();
        for(Tile tile : tileList){
            JSONObject jsonTile = new JSONObject();
            jsonTile.put("id", tile.getId());
            jsonTile.put("color", colorToString(tile.getColor()));
            jsonTile.put("row", positions.get(tile.getId()).getRow());
            jsonTile.put("column", positions.get(tile.getId()).getColumn());
            list.add(jsonTile);
        }
        msg.put("tileList",list);

        return msg.toString();
    }
}