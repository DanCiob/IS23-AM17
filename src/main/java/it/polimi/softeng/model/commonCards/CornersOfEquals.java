package it.polimi.softeng.model.commonCards;

import static it.polimi.softeng.Constants.*;
import it.polimi.softeng.model.Shelfie;

/**
 * This class verifies if the four tiles of the corners of the shelfie have the same color(type)
 */

public class CornersOfEquals extends CommonCards {
    /**
     *
     * @param s is the shelfie we have to verify if it does complete the achievement
     * @return true if it does, else false
     * We assume the shelfie is 'correct': it is not possible to have empty positions, under positions with tiles
     *  (rows with smaller index).
     */
    @Override
    public boolean verifyShape(Shelfie s) {
        /*Check if top corners are empty or not
          We assume that in each column, if one row is not empty, all the others under this row cannot be empty */
        if ((s.getTile(shelfieRows - 1, 0)!= null) && ((s.getTile(shelfieRows - 1, shelfieColumns - 1)!= null))){

            //check if top corners have the same color
            if (s.getTile(shelfieRows - 1, 0).getColor() == s.getTile(shelfieRows - 1, shelfieColumns - 1).getColor()){

                //check if right corners have the same color
                if (s.getTile(shelfieRows - 1, shelfieColumns - 1).getColor() == s.getTile(0, shelfieColumns - 1).getColor()){

                    //check if bottom corners have the same color
                    if (s.getTile(0, shelfieColumns - 1).getColor() == s.getTile(0, 0).getColor()){
                        return true;
                    }
                }
            }
        }

        return false;

    }
}