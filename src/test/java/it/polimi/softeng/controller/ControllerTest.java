package it.polimi.softeng.controller;

import it.polimi.softeng.Constants;
import it.polimi.softeng.client.view.CLI.CLI;
import it.polimi.softeng.model.*;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
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

        try {
            String move = "@GAME (7,4),0";
            if (controller.getGameController().getCurrentGame().getCurrentPlayer().getNickname().equals(cli1.getNickname()))
                cli1.game(move);
            else
                cli2.game(move);

            TimeUnit.SECONDS.sleep(1);

            move = "@GAME (7,5),0";
            if (controller.getGameController().getCurrentGame().getCurrentPlayer().getNickname().equals(cli1.getNickname()))
                cli1.game(move);
            else
                cli2.game(move);

            move = "@CHAT 'all' Test";
            cli2.game(move);
            cli1.game(move);

            move = "@VPLA";
            cli2.game(move);
            cli1.game(move);

            move = "@VCCA";
            cli2.game(move);
            cli1.game(move);

            move = "@VBOR";
            cli2.game(move);
            cli1.game(move);

            move = "@VSHE";
            cli2.game(move);
            cli1.game(move);

            //An error is issued
            move = "@GAME (7,5),0";
            if (controller.getGameController().getCurrentGame().getCurrentPlayer().getNickname().equals(cli1.getNickname()))
                cli1.game(move);
            else
                cli2.game(move);

            //Simulate disconnection
            controller.getServerSide().getServerSideTCP().addDisconnectedPlayer("Player_2");
            assertEquals(1, controller.getServerSide().getServerSideRMI().getNameToStub().size());
            assertEquals(0, controller.getServerSide().getServerSideTCP().getNickNameToClientHandler().size());
            CLI cli3 = new CLI(new ByteArrayInputStream((1 + enter + "" + enter + "" + enter + 2 + enter + "Player_2" + enter).getBytes()));
            cli3.setupCLI();
            TimeUnit.SECONDS.sleep(1);

            //An error is issued
            move = "@GAME (7,5),0";
            if (controller.getGameController().getCurrentGame().getCurrentPlayer().getNickname().equals(cli1.getNickname()))
                cli1.game(move);
            else
                cli3.game(move);

            //Simulate disconnection
            controller.getServerSide().getServerSideRMI().getLoginManager().addDisconnectedPlayer("Player_1");
            controller.getServerSide().getServerSideRMI().removeRMIClient("Player_1");
            assertEquals(0, controller.getServerSide().getServerSideRMI().getNameToStub().size());
            assertEquals(1, controller.getServerSide().getServerSideTCP().getNickNameToClientHandler().size());
            CLI cli4 = new CLI(new ByteArrayInputStream((3 + enter + "" + enter + 2 + enter + "Player_1" + enter).getBytes()));
            cli4.setupCLI();
            TimeUnit.SECONDS.sleep(1);

            move = "@GAME (1,3),(1,4),0";
            if (controller.getGameController().getCurrentGame().getCurrentPlayer().getNickname().equals(cli1.getNickname()))
                cli4.game(move);
            else
                cli3.game(move);

            assertNotNull(controller.getGameController().getCurrentGame().getGameBoard().getBoard()[1][3]);
            assertNotNull(controller.getGameController().getCurrentGame().getGameBoard().getBoard()[1][4]);

            //Simulate end game
            Shelfie fullShelfie = new Shelfie();
            int counter = 0;
            for (int i = 0; i < Constants.shelfieColumns; i++)
            {
                for (int j = 0; j < Constants.shelfieRows; j++)
                {
                    fullShelfie.setGrid(j, i, counter, Tile.TileColor.WHITE);
                    counter++;
                }
            }
            fullShelfie.setGridAtNull(Constants.shelfieRows - 1, Constants.shelfieColumns - 1);
            controller.getGameController().getCurrentGame().getPlayers().get(0).setShelfie(fullShelfie);
            controller.getGameController().getCurrentGame().getPlayers().get(1).setShelfie(fullShelfie);

            move = "@GAME (2,3),4";
            if (controller.getGameController().getCurrentGame().getCurrentPlayer().getNickname().equals(cli1.getNickname()))
                cli4.game(move);
            else
                cli3.game(move);

            move = "@GAME (2,4),4";
            if (controller.getGameController().getCurrentGame().getCurrentPlayer().getNickname().equals(cli1.getNickname()))
                cli4.game(move);
            else
                cli3.game(move);

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        TimeUnit.SECONDS.sleep(1);

        assertNull(controller.getGameController().getCurrentGame().getGameBoard().getBoard()[7][4]);
        assertNull(controller.getGameController().getCurrentGame().getGameBoard().getBoard()[7][5]);
    }
}