package it.polimi.softeng.model.commonCards;

import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;

import static it.polimi.softeng.Constants.typesOfTiles;

/**
 * This class implements the method verifyShape() to check if the shelfie contains "n" tiles of the same color.
 * In this version of the game "n" is equal to 8, but it's a parameter which can be easily modified.
 */

public class NEqualTiles extends CommonCards{

    public boolean verifyShape(Shelfie shelfie){
        int n = 8; //indicates the number of tiles of the same color needed to complete the achievement
        int numOfColors = typesOfTiles; //indicates the total number of colors of the tiles in the game
        int i;
        int[] numTilesForColors = new int[numOfColors];
        Tile[][] grid = shelfie.getGrid();
        Tile.TileColor tileColor;
        for(Tile[] tileVector : grid){
            for(Tile tile: tileVector){
                if(tile!=null){
                    tileColor = tile.getColor();
                    numTilesForColors[tileColor.colorPos()]++;
                }
            }
        }
        for(i=0;i<numOfColors;i++){
            if(numTilesForColors[i]>=n)
                return true;
        }
        return false;
    }

    public String getName () {return "NEqualTiles";}
}