package it.polimi.softeng.model;

/**
 * We use Singleton pattern (Eager Singleton), because BadgeEndGame has only one instance.
 */

public class BadgeEndGame implements Badge {
    //we immediately create the single instance
    private static final BadgeEndGame instance = new BadgeEndGame();

    //we set the constructor as private to hide it and avoid other creation of the instance
    private BadgeEndGame(){}

    public static BadgeEndGame getInstance() {
        return instance;
    }

    @Override
    public int getScore() {
        return 1;
    }
}
