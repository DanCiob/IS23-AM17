package it.polimi.softeng.model.commonCards;

import it.polimi.softeng.model.Shelfie;

/**
 * This class verifies if the four tiles of the corners of the shelfie are of the same color(type)
 */

public class FourCornerOfEquals extends CommonCards {
    /**
     *
     * @param s is the shelfie we have to verify if it does complete the achievement
     * @return true if it does, else false
     */
    @Override
    public boolean verifyShape(Shelfie s) {
        final int maxC = 5; //set the number of columns
        final int maxR = 6; //set the number of rows

        /*Check if top corners are empty or not
          We assume that in each column, if one row is not empty, all the others under this row cannot be empty */
        if ((s.getTile(maxR - 1, 0)!= null) && ((s.getTile(maxR - 1, maxC - 1)!= null))){

            //check if top corners are the same color
            if (s.getTile(maxR - 1, 0).getColor() == s.getTile(maxR - 1, maxC - 1).getColor()){

                //check if right corners are the same color
                if (s.getTile(maxR - 1, maxC - 1).getColor() == s.getTile(0, maxC - 1).getColor()){

                    //check if bottom corners are the same color
                    if (s.getTile(0, maxC - 1).getColor() == s.getTile(0, maxC - 1).getColor()){
                        return true;
                    }
                }
            }
        }

        return false;

    }
}