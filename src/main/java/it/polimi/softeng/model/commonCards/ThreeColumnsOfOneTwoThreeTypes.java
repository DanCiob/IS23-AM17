package it.polimi.softeng.model.commonCards;

import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile.TileColor;
import java.util.Vector;

/**
 * This class verifies if the shelfie has three columns each formed by 6 tiles of maximum three different types
 */
public class ThreeColumnsOfOneTwoThreeTypes extends CommonCards {
    /**
     *
     * @param s is the shelfie we have to verify if it does complete the achievement
     * @return true if it does, else false
     */
    @Override
    public boolean verifyShape(Shelfie s) {
        final int maxDifferentColor = 3; //maximum number of different colors for each column
        final int target = 3; //number of columns with different colors to complete the achievement
        int count = 0; //number of columns with different colors found
        final int maxC = 5; //number of columns of the shelfie
        final int maxR = 6; //number of rows of the shelfie
        boolean completed = false;
        Vector<TileColor> v = new Vector<>(maxDifferentColor);

        column:
        for(int c = 0; c < maxC; c++){
            row:
            for (int r = maxR - 1; r >= 0; r--){
                //if empty g to next column
                if(s.getTile(r, c) == null){
                    break row;
                }
                //add color to vector
                if(!v.contains(s.getTile(r, c).getColor())){
                    v.add(s.getTile(r, c).getColor());
                    //if too many colors, go to next column
                    if(v.size() > maxDifferentColor){
                        break row;
                    }
                }
                //if we analyze the entire column without exceeding maxDifferentColor
                if (r == 0){
                    count++;
                }
                if (count >= target){
                    completed = true;
                    break column;
                }
            }

            //reset vector to count different colors of next column
            v.clear();
        }

        return completed;

    }
}