package it.polimi.softeng.model.commonCards;

import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.scoreCount.Score;

/**
 * This class implements the method verifyShape() to check if the shelfie has two completed rows each formed by 5 different
 * types of tiles. One row can show the same or a different combination of the other row.
 */

public class  TwoRowsOfFiveDifferent extends CommonCards{

    public boolean verifyShape(Shelfie shelfie){
        return Score.twoOfDifferent(shelfie, false);
    }

    public String getName () {return "TwoRowsOfFiveDifferent";}

}
