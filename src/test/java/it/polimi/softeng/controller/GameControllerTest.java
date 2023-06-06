package it.polimi.softeng.controller;

import it.polimi.softeng.model.Game;
import it.polimi.softeng.model.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    /**
     * Check that game controller game setup goes correctly
     */
    @Test
    void startGameTest() {
        Game game = new Game();
        ArrayList<String> nameList = new ArrayList<>();

        nameList.add("Player1");
        nameList.add("Player2");
        nameList.add("Player3");
        nameList.add("Player4");

        game.beginGame(nameList);

        assertNotNull(game.getGameBoard());
        assertNotNull(game.getPlayers());
        for (Player p : game.getPlayers()) {
            assertNotNull(p.getPersonalCard());
            assertNotNull(p.getShelfie());
            assertEquals(0, p.getCurrentScore());
        }
        assertNotNull(game.getCommonCards());
    }
}