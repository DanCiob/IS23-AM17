package it.polimi.softeng.model;

import java.io.Serializable;

/**
 * We use Singleton pattern (Eager Singleton), because BadgeEndGame has only one instance.
 */

public class BadgeEndGame implements Badge, Serializable {
    //we immediately create the single instance
    private static final BadgeEndGame instance = new BadgeEndGame();

    //we set the constructor as private to hide it and avoid other creations of the instance
    private BadgeEndGame(){}

    public static BadgeEndGame getInstance() {
        return instance;
    }

    //the score of the End Game Badge is 1 according to requirements
    @Override
    public int getScore() {
        return 1;
    }
}
