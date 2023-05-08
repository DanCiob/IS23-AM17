package it.polimi.softeng.model.commonCards;

import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.scoreCount.Score;

public class  TwoRowsOfFiveDifferent extends CommonCards{

    /**Two lines each formed by 5 different types of tiles. One line can show the same or a different combination of the other line
     * @param shelfie to check
     * @return boolean which is true if the shape is present in the shelfie or 0 if it's not
     */
    public boolean verifyShape(Shelfie shelfie){
        return Score.twoOfDifferent(shelfie, false);
    }

    public String getName () {return "TwoRowsOfFiveDifferent";}

}
