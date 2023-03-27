package it.polimi.softeng.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * We verify the enum BadgeScoreTest have they corresponding score
 */

class BadgeScoreTest {

    @Test
    void getScore() {
        BadgeScore two = BadgeScore.TWO;
        assertEquals(2, two.getScore());

        BadgeScore four = BadgeScore.FOUR;
        assertEquals(4, four.getScore());

        BadgeScore six = BadgeScore.SIX;
        assertEquals(6, six.getScore());

        BadgeScore eight = BadgeScore.EIGHT;
        assertEquals(8, eight.getScore());
    }
}