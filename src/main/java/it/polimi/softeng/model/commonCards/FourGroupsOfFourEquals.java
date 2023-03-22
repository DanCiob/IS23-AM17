package it.polimi.softeng.model.commonCards;

import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.ScoreCount.graphNode;

import java.util.ArrayList;

import static it.polimi.softeng.model.ScoreCount.Score.GroupsOfEqualTiles;
import static it.polimi.softeng.model.ScoreCount.graphNode.mapShelfieToGraph;

public class FourGroupsOfFourEquals extends CommonCards {

    /**
     *
     * @param s is the shelfie
     * @return true if the shape is satisfied, false if not
     */
    @Override
    public boolean verifyShape(Shelfie s) {
        return GroupsOfEqualTiles(s, 4, 4);
    }
}