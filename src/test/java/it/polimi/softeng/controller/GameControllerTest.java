package it.polimi.softeng.controller;

import it.polimi.softeng.client.view.CLI.CLI;
import it.polimi.softeng.client.view.UI;
import it.polimi.softeng.model.Game;
import it.polimi.softeng.model.Player;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
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

    /**
     * Check that every connected player (all with same connection method) has received all information
     */
    @Ignore
    void startGameWithAllSocketTest() {
        Controller controller = new Controller();
        String enter = System.lineSeparator();

        ByteArrayInputStream input1 = new ByteArrayInputStream((1 + enter + "127.0.0.1" + enter + 2540 + enter + 1 + enter + 2 + enter + 2 + enter + "Player 1").getBytes());
        ByteArrayInputStream input2 = new ByteArrayInputStream((1 + enter + "127.0.0.1" + enter + 2540 + enter + 2 + enter + "Player 2").getBytes());
        CLI cli1 = new CLI(input1);
        CLI cli2 = new CLI(input2);

        cli1.run();
        cli2.run();

    }
}