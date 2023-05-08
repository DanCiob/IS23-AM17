package it.polimi.softeng.model.commonCards;

import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.scoreCount.Score;
import static it.polimi.softeng.Constants.*;

import static it.polimi.softeng.model.scoreCount.Score.verifyMaxDifferentColor;

/**
 * This class verifies if the shelfie has 'n' completed rows of maximum 'm' different colors (types)
 * 'n' and 'm' are two configurable parameters (target and maxDifferentColor)
 */

public class RowsOfMaxDiffTypes extends CommonCards {
    public boolean verifyShape(Shelfie shelfie) {
        final int maxDifferentColor = 3; //maximum number of different colors for each row
        final int target = 4; //number of rows with different colors to complete the achievement
        final Score.Mode mode = Score.Mode.ROW; //specify the mode for this method

        return verifyMaxDifferentColor (shelfie, maxDifferentColor, target, shelfieRows, shelfieColumns, mode);
    }

    public String getName () {return "RowsOfMaxDiffTypes";}
}