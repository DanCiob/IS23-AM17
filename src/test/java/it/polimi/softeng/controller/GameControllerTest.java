package it.polimi.softeng.controller;

import it.polimi.softeng.client.view.CLI.CLI;
import it.polimi.softeng.client.view.UI;
import it.polimi.softeng.model.Game;
import it.polimi.softeng.model.GameBoard;
import it.polimi.softeng.model.Player;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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
    void startGameWithAllSocketTest() throws InterruptedException {
        Controller controller = new Controller();
        String enter = System.lineSeparator();

        ByteArrayInputStream input1 = new ByteArrayInputStream((1 + enter + "" + enter + "" + enter + 1 + enter + 2 + enter + 2 + enter + "Player_1" + enter).getBytes());
        ByteArrayInputStream input2 = new ByteArrayInputStream((1 + enter + "" + enter + "" + enter + 2 + enter + "Player_2" + enter).getBytes());
        CLI cli1 = new CLI(input1);
        CLI cli2 = new CLI(input2);

        cli1.setupCLI();
        cli2.setupCLI();

        assertNotNull(cli1.getNickname());
        assertNotNull(cli2.getNickname());
        assertEquals("Player_1", cli1.getNickname());
        assertEquals("Player_2", cli2.getNickname());

        TimeUnit.SECONDS.sleep(3);

        assertNotNull(cli1.getUserGameBoard());
        assertNotNull(cli2.getUserGameBoard());
        assertNotNull(cli1.getUserShelfie());
        assertNotNull(cli2.getUserShelfie());
        assertNotNull(cli1.getPersonalCard());
        assertNotNull(cli2.getPersonalCard());
        assertNotNull(cli1.getCommonCard1());
        assertNotNull(cli2.getCommonCard1());
        assertNotNull(cli1.getCommonCard2());
        assertNotNull(cli2.getCommonCard2());


    }

    /**
     * Check that every connected player (all with same connection method) has received all information
     */
    @Test
    void startGameWithAllRMITest() throws InterruptedException {
        Controller controller = new Controller();
        String enter = System.lineSeparator();

        ByteArrayInputStream input1 = new ByteArrayInputStream((3 + enter + "" + enter + 1 + enter + 2 + enter + 2 + enter + "Player_1" + enter).getBytes());
        ByteArrayInputStream input2 = new ByteArrayInputStream((3 + enter + "" + enter + 2 + enter + "Player_2" + enter).getBytes());
        CLI cli1 = new CLI(input1);
        CLI cli2 = new CLI(input2);

        cli1.setupCLI();
        cli2.setupCLI();

        assertNotNull(cli1.getNickname());
        assertNotNull(cli2.getNickname());
        assertEquals("Player_1", cli1.getNickname());
        assertEquals("Player_2", cli2.getNickname());

        TimeUnit.SECONDS.sleep(1);

        assertNotNull(cli1.getUserGameBoard());
        assertNotNull(cli2.getUserGameBoard());
        assertNotNull(cli1.getUserShelfie());
        assertNotNull(cli2.getUserShelfie());
        assertNotNull(cli1.getPersonalCard());
        assertNotNull(cli2.getPersonalCard());
        assertNotNull(cli1.getCommonCard1());
        assertNotNull(cli2.getCommonCard1());
        assertNotNull(cli1.getCommonCard2());
        assertNotNull(cli2.getCommonCard2());
    }

    /**
     * Check that every connected player (with mixed connection method) has received all information
     */
    @Test
    void startGameMixedConnection() throws InterruptedException {
        Controller controller = new Controller();
        String enter = System.lineSeparator();

        ByteArrayInputStream input1 = new ByteArrayInputStream((3 + enter + "" + enter + 1 + enter + 2 + enter + 2 + enter + "Player_1" + enter).getBytes());
        ByteArrayInputStream input2 = new ByteArrayInputStream((1 + enter + "" + enter + "" + enter + 2 + enter + "Player_2" + enter).getBytes());
        CLI cli1 = new CLI(input1);
        CLI cli2 = new CLI(input2);

        cli1.setupCLI();
        cli2.setupCLI();

        assertNotNull(cli1.getNickname());
        assertNotNull(cli2.getNickname());
        assertEquals("Player_1", cli1.getNickname());
        assertEquals("Player_2", cli2.getNickname());

        TimeUnit.SECONDS.sleep(1);

        assertNotNull(cli1.getUserGameBoard());
        assertNotNull(cli2.getUserGameBoard());
        assertNotNull(cli1.getUserShelfie());
        assertNotNull(cli2.getUserShelfie());
        assertNotNull(cli1.getPersonalCard());
        assertNotNull(cli2.getPersonalCard());
        assertNotNull(cli1.getCommonCard1());
        assertNotNull(cli2.getCommonCard1());
        assertNotNull(cli1.getCommonCard2());
        assertNotNull(cli2.getCommonCard2());
    }

}