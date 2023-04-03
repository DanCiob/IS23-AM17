package it.polimi.softeng.JSONParser;

import it.polimi.softeng.model.Tile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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

        System.out.println(msg);
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
        for(Tile tile : parser.getTilesToBeInserted()){
            System.out.println("tile : "+ tile.getId() + " " + colorToString(tile.getColor()));
        }
        System.out.println(parser.getColumn());

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
}