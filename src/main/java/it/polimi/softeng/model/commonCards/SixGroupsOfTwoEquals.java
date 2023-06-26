package it.polimi.softeng.model.commonCards;
import it.polimi.softeng.model.Shelfie;

import static it.polimi.softeng.model.scoreCount.Score.GroupsOfEqualTiles;

/**
 * This class implements the method verifyShape() to check if the shelfie contains six groups each containing at least two tiles of the same color.
 * The number and the size of groups are configurable parameters.
 */

public class SixGroupsOfTwoEquals extends CommonCards {

    @Override
    public boolean verifyShape(Shelfie s) {
        int numOfGroups = 6;
        int sizeOfGroups = 2;

        return GroupsOfEqualTiles(s, numOfGroups, sizeOfGroups);
    }

    public String getName () {return "SixGroupsOfTwoEquals";}
}