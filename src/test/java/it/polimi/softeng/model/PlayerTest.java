package it.polimi.softeng.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Check player main functionalities
 */
class PlayerTest {

    /**
     * Test if updateScore correctly add passed score
     */
    @Test
    void updateScoreTest() {
        Player player = new Player("TestPlayer", 0);
        int correctScore = player.updateScore(4);
        assertEquals(4, correctScore);
        correctScore = player.updateScore(2);
        assertEquals(6, correctScore);
    }


}