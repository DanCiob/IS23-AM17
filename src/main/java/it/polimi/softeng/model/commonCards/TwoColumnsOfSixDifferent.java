package it.polimi.softeng.model.commonCards;

import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.scoreCount.Score;

public class  TwoColumnsOfSixDifferent extends CommonCards{

    /**Two columns each formed by 6 different types of tiles
     * @param shelfie to check
     * @return boolean which is true if the shape is present in the shelfie or 0 if it's not
     */
    public boolean verifyShape(Shelfie shelfie){
        return Score.twoOfDifferent(shelfie, true);
    }
}