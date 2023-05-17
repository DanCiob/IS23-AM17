package it.polimi.softeng.model;

import java.io.Serializable;

/**
 * BadgeScore enumeration defines four different types of BadgeScore with their corresponding scores (2, 4, 6, 8)
 */

public enum BadgeScore implements Badge, Serializable{
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
