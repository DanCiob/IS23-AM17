package it.polimi.softeng.model.commonCards;
import it.polimi.softeng.model.BadgeScore;
import it.polimi.softeng.model.Shelfie;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class is the abstract representation of the CommonCards of the game, it's extended by all the different types of CommonCards.
 */
public abstract class CommonCards implements Serializable {
    /**
     * This method is implemented by all the different types of CommonCards to verify if the specific shape is present in the shelfie.
     * @param s is the shelfie in which we verify the pattern.
     * @return true if the shape is found, false if not.
     */
    public boolean verifyShape(Shelfie s) {
        return true;
    }

    /**
     * ArrayList containing the score badges, which are in descending order (ie badges(0) contains the highest score remaining badge).
     */
    private ArrayList<BadgeScore> badges = new ArrayList<>();

    /**
     * Setter method for the list of score badges.
     * @param badge badge inserted
     */
    public void setBadges(BadgeScore badge){
        badges.add(badge);
    }

    /**
     * Method returning the highest remaining score badge.
     * @return score badge
     */
    public BadgeScore getBadge(){
        if(badges.isEmpty())
            return null;
        return badges.remove(0);
    }

    public abstract String getName();
}
