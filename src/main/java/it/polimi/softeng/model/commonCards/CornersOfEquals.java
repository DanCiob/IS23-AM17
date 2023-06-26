package it.polimi.softeng.model.commonCards;

import static it.polimi.softeng.Constants.*;
import it.polimi.softeng.model.Shelfie;

/**
 * This class implements the method verifyShape() to check if the four tiles of the corners of the shelfie have the same color.
 */

public class CornersOfEquals extends CommonCards {
    /*
    We assume that in each column, if a position has a tile, so do all the others under that position.
     */
    @Override
    public boolean verifyShape(Shelfie s) {
        //Check if top corners are empty or not.
        if ((s.getTile(shelfieRows - 1, 0)!= null) && ((s.getTile(shelfieRows - 1, shelfieColumns - 1)!= null))){

            //Check if top corners have the same color.
            if (s.getTile(shelfieRows - 1, 0).getColor() == s.getTile(shelfieRows - 1, shelfieColumns - 1).getColor()){

                //Check if right corners have the same color.
                if ((s.getTile(0, shelfieColumns - 1)!=null)&&(s.getTile(shelfieRows - 1, shelfieColumns - 1).getColor() == s.getTile(0, shelfieColumns - 1).getColor())){

                    //Check if bottom corners have the same color
                    if ((s.getTile(0, 0)!=null)&&(s.getTile(0, shelfieColumns - 1).getColor() == s.getTile(0, 0).getColor())){
                        return true;
                    }
                }
            }
        }

        return false;

    }

    public String getName () {return "CornersOfEquals";}
}