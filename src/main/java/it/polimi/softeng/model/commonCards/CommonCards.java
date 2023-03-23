package it.polimi.softeng.model.commonCards;
import it.polimi.softeng.model.BadgeScore;
import it.polimi.softeng.model.Shelfie;

import java.util.ArrayList;

public abstract class CommonCards {
    public boolean verifyShape(Shelfie s) {
        return true;
    }

    private ArrayList<BadgeScore> badges = new ArrayList<>();

    public void setBadges(BadgeScore badge){
        badges.add(badge);
    }
}
