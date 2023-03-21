package it.polimi.softeng.model;

/**
 * The Enum defines 4 different types of BadgeScore with 4 different scores (2, 4, 6, 8)
 */

public enum BadgeScore implements Badge{
    TWO(2), FOUR(4), SIX(6), EIGHT(8);

    private final int score;

    BadgeScore(int score) {
        this.score = score;
    }

    @Override
    public int getScore() {
        return score;
    }

}
