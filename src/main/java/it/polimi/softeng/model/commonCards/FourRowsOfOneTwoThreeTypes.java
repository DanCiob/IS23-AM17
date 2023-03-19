package it.polimi.softeng.model.commonCards;

import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;
import java.util.Vector;
/**
 * This class verifies if the shelfie has four rows each formed by 5 tiles of maximum three different types
 */

public class FourRowsOfOneTwoThreeTypes extends CommonCards {
    /**
     *
     * @param s is the shelfie we have to verify if it does complete the achievement
     * @return true if it does, else false
     */
    @Override
    public boolean verifyShape(Shelfie s) {
        final int maxDifferentColor = 3; //maximum number of different colors for each row
        final int target = 4; //number of rows with different colors to complete the achievement
        int count = 0; //number of rows with different colors found
        final int maxC = 5; //number of columns of the shelfie
        final int maxR = 6; //number of rows of the shelfie
        boolean completed = false;
        Vector<Tile.TileColor> v = new Vector<>(maxDifferentColor);

        row:
        for (int r = 0; r < maxR; r++) {
            column:
            for (int c = 0; c < maxR; c++) {
                //if empty stop verifying shelfie becuase the shelfie cannot have more rows of different colors
                if (s.getTile(r, c) == null) {
                    break row;
                }
                //add color to vector if not present
                if (!v.contains(s.getTile(r, c).getColor())) {
                    v.add(s.getTile(r, c).getColor());
                    //if too many colors, go to next row
                    if (v.size() > maxDifferentColor) {
                        break column;
                    }
                }
                //if we analyze the entire row without exceeding maxDifferentColor
                if (c == maxC - 1) {
                    count++;
                }
                if (count >= target) {
                    completed = true;
                    break row;
                }
            }

            //reset to count different colors of next column
            v.clear();
        }

        return completed;

    }
}