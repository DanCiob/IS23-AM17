package it.polimi.softeng.model.commonCards;

import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.scoreCount.Score;

/**
 * This class implements the method verifyShape() to check if the shelfie has two completed columns each formed by
 * 6 different types of tiles.
 */

public class  TwoColumnsOfSixDifferent extends CommonCards{

    public boolean verifyShape(Shelfie shelfie){
        return Score.twoOfDifferent(shelfie, true);
    }

    public String getName () {return "TwoColumnsOfSixDifferent";}

}