package it.polimi.softeng.model.commonCards;

import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;

public class  EightEquals extends CommonCards{

    /**
     * it verifies if there are eight tiles of the same type
     * @param shelfie  to check
     * @return boolean which is true if the shape is present in the shelfie or 0 if it's not
     */
    public boolean verifyShape(Shelfie shelfie){
        int i;
        int[] numTilesForColors = {0, 0, 0, 0, 0 , 0}; //numTilesForColors has the number of tiles for every color
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
        for(i=0;i<6;i++){ // 6 is the number of colors
            if(numTilesForColors[i]>=8)
                return true;
        }
        return false;
    }
}