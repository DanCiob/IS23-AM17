package it.polimi.softeng.model.commonCards;
import it.polimi.softeng.model.BadgeScore;
import it.polimi.softeng.model.Shelfie;

import java.util.ArrayList;

public abstract class CommonCards {
    public boolean verifyShape(Shelfie s) {
        return true;
    }

    /**
     * arrayList contains the score badges. the badges are in descending order
     * (ie badges(0) contains the highest score remaining badge)
     */
    private ArrayList<BadgeScore> badges = new ArrayList<>();

    /**
     * setter method for the list of score badges
     * @param badge badge inserted
     */
    public void setBadges(BadgeScore badge){
        badges.add(badge);
    }

    /**
     * method returning the highest remaining score badge
     * @return score badge
     */
    public BadgeScore getBadge(){
        return badges.remove(0);
    }
}
