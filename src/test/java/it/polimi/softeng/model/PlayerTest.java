package it.polimi.softeng.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    /**
     * Test if updateScore correctly add passed score
     */
    @Test
    void updateScore() {
        Player player = new Player("TestPlayer", 0, false);
        int correctScore = player.updateScore(4);
        assertEquals(4, correctScore);
    }
}