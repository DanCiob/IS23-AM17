package it.polimi.softeng.model;

import java.util.ArrayList;

import java.util.Random;

import static it.polimi.softeng.Constants.*;

/**
 * This class contains a "bag" with every tile available in the game.
 */
public class TileBag {
    /**
     * ArrayList containing the tiles for the game.
     */
    private final static ArrayList<Tile> tileBag = new ArrayList<>();

    /**
     * Getter method for the tile bag.
     * @return arraylist of tiles.
     */
    public ArrayList<Tile> getTileBag() {
        return tileBag;
    }

    /**
     * This method is used to fill the tilebag with the right amount and type of tiles.
     */
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

    /**
     * This method picks a random tile from tile bag.
     * @return tile.
     */
    public Tile drawTile(){
        Random random = new Random();
        return tileBag.get(random.nextInt(tileBag.size()));
    }

}