package it.polimi.softeng.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * We verify the instance created with Singleton is not null, and it is always the same
 * We also verify the Score value is 1
 */

class BadgeEndGameTest {

    @Test
    void getInstance() {
        BadgeEndGame instance1 = BadgeEndGame.getInstance();
        assertNotNull(instance1);

        BadgeEndGame instance2 = BadgeEndGame.getInstance();
        assertNotNull(instance2);

        assertSame(instance1, instance2);
    }

    @Test
    void getScoreTest() {
        int n = BadgeEndGame.getInstance().getScore();
        assertEquals(1, n);
    }
}