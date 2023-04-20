package it.polimi.softeng.model;

import java.util.ArrayList;
import static it.polimi.softeng.model.Constants.*;
import java.util.Random;

public class TileBag {
    private final static ArrayList<Tile> tileBag = new ArrayList<>();

    public ArrayList<Tile> getTileBag() {
        return tileBag;
    }

    public void initializeTile(){
        tileBag.clear();
        for(int i = 0; i < totalTiles/typesOfTiles; i+=typesOfTiles){
            tileBag.add(new Tile(i, Tile.TileColor.WHITE));
            tileBag.add(new Tile(i+1, Tile.TileColor.BLUE));
            tileBag.add(new Tile(i+2, Tile.TileColor.YELLOW));
            tileBag.add(new Tile(i+3, Tile.TileColor.PURPLE));
            tileBag.add(new Tile(i+4, Tile.TileColor.CYAN));
            tileBag.add(new Tile(i+5, Tile.TileColor.GREEN));
        }
    }

    public Tile drawTile(){
        Random random = new Random();
        return tileBag.get(random.nextInt(tileBag.size()));
    }

}
