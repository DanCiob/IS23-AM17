package it.polimi.softeng.model.commonCards;

import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;

public class  EightEquals extends CommonCards{
    private final int numRows = 6;
    private final int numColumns = 5;

    /**
     * it verifies if there are eight tiles of the same type
     * @param shelfie  to check
     * @return boolean which is true if the shape is present in the shelfie or 0 if it's not
     */
    public boolean verifyShape(Shelfie shelfie){
        int i, j;
        int[] numTilesForColors = {0, 0, 0, 0, 0 , 0}; /*numTilesForColors has the number of tiles for every color(position 0:BLUE, 1:WHITE, 2:GREEN, 3:YELLOW, 4:PURPLE, 5:CYAN)**/
        Tile[][] grid = shelfie.getGrid();
        for(i=0;i<numRows;i++){
            for(j=0;j<numColumns;j++){
                if(grid[i][j] != null){
                    switch (grid[i][j].getColor()) {
                        case BLUE-> numTilesForColors[0]++;
                        case WHITE -> numTilesForColors[1]++;
                        case GREEN -> numTilesForColors[2]++;
                        case YELLOW -> numTilesForColors[3]++;
                        case PURPLE -> numTilesForColors[4]++;
                        case CYAN -> numTilesForColors[5]++;
                    }
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