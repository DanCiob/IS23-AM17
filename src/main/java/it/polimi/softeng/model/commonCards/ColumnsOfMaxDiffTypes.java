package it.polimi.softeng.model.commonCards;

import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.scoreCount.Score;

import static it.polimi.softeng.Constants.*;
import static it.polimi.softeng.model.scoreCount.Score.verifyMaxDifferentColor;

/**
 * This class verifies if the shelfie has the target number (target_ColumnsOfMaxDiffTypes) of completed columns
 * with no more than a certain number of different colors (maxDiffTypes_ColumnsOfMaxDiffTypes)
 */
public class ColumnsOfMaxDiffTypes extends CommonCards {
    public boolean verifyShape(Shelfie shelfie) {
        final Score.Mode mode = Score.Mode.COLUMN; //specify the mode for this method

        return verifyMaxDifferentColor (shelfie, maxDiffTypes_ColumnsOfMaxDiffTypes, target_ColumnsOfMaxDiffTypes, shelfieColumns, shelfieRows, mode);
    }

    public String getName () {return "ColumnsOfMaxDiffTypes";}
}