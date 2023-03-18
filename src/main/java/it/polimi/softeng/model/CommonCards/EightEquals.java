package it.polimi.softeng.model.CommonCards;

import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;

public class  EightEquals extends CommonCards{
    /**
     * it verifies if there are eight tiles of the same type
     * @param shelfie  to check
     * @return boolean which is true if the shape is present in the shelfie or 0 if it's not
     */
    public boolean verifyShape(Shelfie shelfie){
        int i, j;
        int[] numTilesForColors = {0, 0, 0, 0, 0 , 0}; /*numTilesForColors has the number of tiles for every color(position 0:BLUE, 1:WHITE, 2:GREEN, 3:YELLOW, 4:PURPLE, 5:CYAN)**/
        for(i=0;i<6;i++){
            for(j=0;j<5;j++){
                if(shelfie.getGrid()[i][j] != null){
                    switch (shelfie.getGrid()[i][j].getColor()) {
                        case Tile.TileColor.BLUE-> numTilesForColors[0]++;
                        case Tile.TileColor.WHITE -> numTilesForColors[1]++;
                        case Tile.TileColor.GREEN -> numTilesForColors[2]++;
                        case Tile.TileColor.YELLOW -> numTilesForColors[3]++;
                        case Tile.TileColor.PURPLE -> numTilesForColors[4]++;
                        case Tile.TileColor.CYAN -> numTilesForColors[5]++;
                    }
                }
            }
        }
        for(i=0;i<8;i++){
            if(numTilesForColors[i]>=8)
                return true;
        }
        return false;
    }
}