package it.polimi.softeng.model.commonCards;

import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;
import java.util.Vector;
/**
 * This class verifies if the shelfie has four rows each formed by 5 tiles of maximum three different colors (types)
 */

public class FourRowsOfOneTwoThreeTypes extends CommonCards {
    /**
     *
     * @param s is the shelfie we have to verify if it does complete the achievement
     * @return true if it does, else false
     * We assume the shelfie is 'correct': it is not possible to have empty positions, under positions with tiles
     *  (rows with smaller index).
     */
    @Override
    public boolean verifyShape(Shelfie s) {
        final int maxDifferentColor = 3; //maximum number of different colors for each row
        final int target = 4; //number of rows with different colors to complete the achievement
        int count = 0; //number of rows with different colors found in the shelfie
        final int maxC = 5; //number of columns of the shelfie
        final int maxR = 6; //number of rows of the shelfie
        boolean completed = false;
        Vector<Tile.TileColor> v = new Vector<>();

        /*we start visiting the shelfie from the bottom, because if we find an empty position, the current row and all
        the others on the top, cannot be completed rows with different colors, thus we stop immediately
        */
        row:
        for (int r = 0; r < maxR; r++) {
            column:
            for (int c = 0; c < maxC; c++) {
                //if a position is empty stop verifying because the shelfie cannot have more rows with different colors
                if (s.getTile(r, c) == null) {
                    break row;
                }
                //add color to vector if it doesn't already exist
                if (!v.contains(s.getTile(r, c).getColor())) {
                    v.add(s.getTile(r, c).getColor());
                    //if too many different colors in this row, go to the next one
                    if (v.size() > maxDifferentColor) {
                        break column;
                    }
                }
                //if we analyze the entire row without exceeding maxDifferentColor, we count + 1
                if (c == maxC - 1) {
                    count++;
                }
                //if we find 3 or more rows with different colors, we complete the achievement
                if (count >= target) {
                    completed = true;
                    break row;
                }
            }

            //reset to count different colors of next row
            v.clear();
        }

        return completed;

    }
}