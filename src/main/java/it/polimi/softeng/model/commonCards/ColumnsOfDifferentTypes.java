package it.polimi.softeng.model.commonCards;

import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.scoreCount.Score;

import static it.polimi.softeng.model.scoreCount.Score.verifyMaxDifferentColor;

/**
 * This class verifies if the shelfie has three columns each formed by 6 tiles of maximum three different types
 */
public class ColumnsOfDifferentTypes extends CommonCards {
    public boolean verifyShape(Shelfie shelfie) {
        final int maxDifferentColor = 3; //maximum number of different colors for each row
        final int target = 3; //number of rows with different colors to complete the achievement
        final int maxC = 5; //number of columns of the shelfie
        final int maxR = 6; //number of rows of the shelfie
        final Score.Mode mode = Score.Mode.COLUMN; //specify the mode for this method

        return verifyMaxDifferentColor (shelfie, maxDifferentColor, target, maxC, maxR, mode);
    }

}