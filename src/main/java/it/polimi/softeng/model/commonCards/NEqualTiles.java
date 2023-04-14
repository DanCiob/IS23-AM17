package it.polimi.softeng.model.commonCards;

import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;

public class NEqualTiles extends CommonCards{

    /**
     * It verifies if there are n tiles of the same color, we also specify the total number of colors of the tiles in
     * the game (numOfColors)
     * @param shelfie  to check
     * @return boolean which is true if the shape is present in the shelfie or 0 if it's not
     */
    public boolean verifyShape(Shelfie shelfie){
        int n = 8; //indicates the number of tiles of the same color needed to complete the achievement
        int numOfColors = 6; //indicates the total number of colors of the tiles in the game
        int i;
        int[] numTilesForColors = new int[numOfColors];
        Tile[][] grid = shelfie.getGrid();
        Tile.TileColor tileColor;
        for(Tile[] tileVector : grid){
            for(Tile tile: tileVector){
                if(tile!=null){
                    tileColor = tile.getColor();
                    numTilesForColors[tileColor.pos()]++;
                }
            }
        }
        for(i=0;i<numOfColors;i++){
            if(numTilesForColors[i]>=n)
                return true;
        }
        return false;
    }
}