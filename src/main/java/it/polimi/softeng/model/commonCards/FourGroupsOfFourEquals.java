package it.polimi.softeng.model.commonCards;

import it.polimi.softeng.model.Shelfie;

import static it.polimi.softeng.model.scoreCount.Score.GroupsOfEqualTiles;
/**
 * This class implements the method verifyShape() to check if the shelfie contains four groups each containing at least four tiles of the same color.
 * The number and the size of groups are configurable parameters.
 */

public class FourGroupsOfFourEquals extends CommonCards {

    @Override
    public boolean verifyShape(Shelfie s) {
        int numOfGroups = 4;
        int sizeOfGroups = 4;

        return GroupsOfEqualTiles(s, numOfGroups, sizeOfGroups);
    }

    public String getName () {return "FourGroupsOfFourEquals";}
}