package it.polimi.softeng.model.commonCards;

import it.polimi.softeng.model.Shelfie;

import static it.polimi.softeng.model.scoreCount.Score.GroupsOfEqualTiles;

public class FourGroupsOfFourEquals extends CommonCards {

    /**
     *
     * @param s is the shelfie
     * @return true if the shape is satisfied, false if not
     */
    @Override
    public boolean verifyShape(Shelfie s) {
        int numOfGroups = 4;
        int sizeOfGroups = 4;

        return GroupsOfEqualTiles(s, numOfGroups, sizeOfGroups);
    }

    public String getName () {return "FourGroupsOfFourEquals";}
}