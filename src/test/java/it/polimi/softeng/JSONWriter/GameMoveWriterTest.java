package it.polimi.softeng.JSONWriter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import static it.polimi.softeng.JSONWriter.GameMoveWriter.gameMoveRegex;
import static junit.framework.Assert.*;

class GameMoveWriterTest {

    /**
     * Series of test for Regex parser of gameMove
     */
    @Test
    void gameMoveRegexTest() {
        assertTrue(gameMoveRegex("(0,1),2"));
        assertTrue(gameMoveRegex("(0,1 ),(0, 1),2"));
        assertTrue(gameMoveRegex("(0,1),(0,1),(0,1),2"));
        assertFalse(gameMoveRegex("(0,1),(0,1),(0,1),(0,1),(0,1),(0,1),2"));
        assertFalse(gameMoveRegex("2"));
        assertFalse(gameMoveRegex("(0,1),(0,1),(0,1),(0,1),(0,1),(0,1),6)"));
    }

    @Test
    void writeGameMove() {
    }
}