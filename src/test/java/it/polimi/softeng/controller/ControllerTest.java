package it.polimi.softeng.controller;

import it.polimi.softeng.Constants;
import it.polimi.softeng.client.view.CLI.CLI;
import it.polimi.softeng.model.*;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static it.polimi.softeng.JSONWriter.ClientSignatureWriter.clientSignObject;
import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    /**
     * Introduce getter and game methods for testing
     */
    private class CLIWithInputString extends CLI {
        /**
         * Used only for testing
         * Game routine that wait for commands, check syntax and send JSON/invoke method to server if needed -> just for testing purpose
         */
        private void game(String move) throws RemoteException {

            //Check empty command
            if (move == null) {
                System.out.println("Empty command!");
                return;
            }
            //Check if player inserted a command that is not on the table
            if (!isOkCommand(move, 1)) {
                System.out.println("Please write a command that you can see in the table");
                return;
            }

            if (move.equalsIgnoreCase("@CMND") || move.equalsIgnoreCase("@VBOR") || move.equalsIgnoreCase("@VSHE") || move.equalsIgnoreCase("@VPCA") || move.equalsIgnoreCase("@VPLA") || move.equalsIgnoreCase("@VCCA") || move.equalsIgnoreCase("@HELP")) {
                switch (move.toUpperCase()) {
                    case ("@HELP") -> {
                        help();
                        return;
                    }
                    case ("@CMND") -> {
                        return;
                    }
                    case ("@VBOR") -> {
                        boardVisualizer(UserGameBoard.getBoard(), UserGameBoard.getNotAvailable());
                        return;
                    }
                    case ("@VSHE") -> {
                        shelfieVisualizer(UserShelfie.getGrid());
                        return;
                    }
                    case ("@VPCA") -> {
                        personalCardVisualizer(PersonalCard);
                        return;
                    }
                    case ("@VCCA") -> {
                        commonCardsVisualizer(CommonCard1);
                        if (CommonCard2 != null) commonCardsVisualizer(CommonCard2);
                        return;
                    }
                    case ("@VPLA") -> {
                        switch (ConnectionMode) {
                            //Socket
                            case 1 -> {
                                JSONObject dummy = new JSONObject();
                                clientSide.sendMessage(clientSignObject(dummy, "@VPLA", Nickname).toJSONString());
                                return;
                            }
                            //RMI
                            case 2 -> {
                                eventManager("playerEvent");
                                scoreVisualizer(RemoteMethods.getStub().getPlayersAndScore());
                                return;
                            }
                        }
                    }
                }
            }

            if (!isOkCommand(move, 2) && !isOkCommand(move, 3)) {
                System.out.println("Please write a command that you can see in the table");
                return;
            }

            //Command is in @CMND format (every command is 4 letters), uppercase avoid case sensitivity
            String op = move.substring(0, 5).toUpperCase();
            String action = move.substring(6);

            if (!op.equals("@GAME") && !op.equals("@CHAT") && !op.equals("@VPLA")) {
                System.out.println("Please write a command that you can see in the table");
                return;
            }

            switch (ConnectionMode) {
                //Socket
                case 1 -> {
                    //Block illegal move
                    if (op.equals("@GAME")) {
                        if (!isOkCommand(move, 3)) {
                            System.out.println("Please, check gameMove syntax");
                            return;
                        }
                    }
                    //Block badly formatted messages
                    if (op.equals("@CHAT")) {
                        if (!isOkCommand(move, 2)) {
                            System.out.println("Please, check chat syntax");
                            return;
                        }
                    }

                    //Create JSON messages containing request
                    JSONObject toBeSent = actionToJSON(op, action);

                    //Send message to server
                    if (toBeSent != null)
                        clientSide.sendMessage(clientSignObject(toBeSent, op, Nickname).toJSONString());
                }


                case 2 -> {
                    if (op.equals("@GAME")) {
                        if (!isOkCommand(move, 3)) {
                            System.out.println("Please, check gameMove syntax");
                            return;
                        }
                    }

                    if (op.equals("@CHAT")) {
                        if (!isOkCommand(move, 2)) {
                            System.out.println("Please, check chat syntax");
                            return;
                        }
                    }
                    RMIInvoker(op, action);
                }
            }
        }

        public CLIWithInputString(ByteArrayInputStream inputStream) {
            super(inputStream);
        }

        /**
         * Used only for testing
         *
         * @return gameboard
         */
        public GameBoard getUserGameBoard() {
            return UserGameBoard;
        }

        /**
         * Used only for testing
         *
         * @return shelfie
         */
        public Shelfie getUserShelfie() {
            return UserShelfie;
        }

        /**
         * Used only for testing
         *
         * @return PersonalCard
         */
        public PersonalCards getPersonalCard() {
            return PersonalCard;
        }

        /**
         * Used only for testing
         *
         * @return Commoncard1
         */
        public String getCommonCard1() {
            return CommonCard1;
        }

        /**
         * Used only for testing
         *
         * @return Commoncard2
         */
        public String getCommonCard2() {
            return CommonCard2;
        }
    }

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
     * Check that every connected player (with mixed connection method) has received all information, a particularly stressful situation is simulated due to the fact that
     * every player send commands to server at the same time for every move
     */
    @Test
    void startGameMixedConnection() throws InterruptedException {
        Controller controller = new Controller();
        String enter = System.lineSeparator();

        ByteArrayInputStream input1 = new ByteArrayInputStream((3 + enter + "" + enter + 4 + enter + "Player_1" + enter).getBytes());
        ByteArrayInputStream input2 = new ByteArrayInputStream((1 + enter + "" + enter + "" + enter + 4 + enter + "Player_2" + enter).getBytes());
        ByteArrayInputStream input3 = new ByteArrayInputStream((3 + enter + "" + enter + 1 + enter + 4 + enter + "Player_3" + enter).getBytes());
        ByteArrayInputStream input4 = new ByteArrayInputStream((1 + enter + "" + enter + "" + enter + 4 + enter + "Player_4" + enter).getBytes());
        CLIWithInputString cli1 = new CLIWithInputString(input1);
        CLIWithInputString cli2 = new CLIWithInputString(input2);
        CLIWithInputString cli3 = new CLIWithInputString(input3);
        CLIWithInputString cli4 = new CLIWithInputString(input4);

        cli1.setupCLI();
        cli2.setupCLI();
        cli3.setupCLI();
        cli4.setupCLI();

        assertNotNull(cli1.getNickname());
        assertNotNull(cli2.getNickname());
        assertNotNull(cli3.getNickname());
        assertNotNull(cli4.getNickname());
        assertEquals("Player_1", cli1.getNickname());
        assertEquals("Player_2", cli2.getNickname());
        assertEquals("Player_3", cli3.getNickname());
        assertEquals("Player_4", cli4.getNickname());

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
        assertNotNull(cli3.getUserGameBoard());
        assertNotNull(cli4.getUserGameBoard());
        assertNotNull(cli3.getUserShelfie());
        assertNotNull(cli4.getUserShelfie());
        assertNotNull(cli3.getPersonalCard());
        assertNotNull(cli4.getPersonalCard());
        assertNotNull(cli3.getCommonCard1());
        assertNotNull(cli4.getCommonCard1());
        assertNotNull(cli3.getCommonCard2());
        assertNotNull(cli4.getCommonCard2());


        try {
            String move = "@GAME (0,3),0";
            cli1.game(move);
            cli2.game(move);
            cli3.game(move);
            cli4.game(move);

            TimeUnit.SECONDS.sleep(1);

            move = "@GAME (0,4),0";
            cli1.game(move);
            cli2.game(move);
            cli3.game(move);
            cli4.game(move);

            move = "@CHAT 'all' Test";
            cli1.game(move);
            cli2.game(move);
            cli3.game(move);
            cli4.game(move);

            move = "@VPLA";
            cli1.game(move);
            cli2.game(move);
            cli3.game(move);
            cli4.game(move);

            move = "@VCCA";
            cli1.game(move);
            cli2.game(move);
            cli3.game(move);
            cli4.game(move);

            move = "@VBOR";
            cli1.game(move);
            cli2.game(move);
            cli3.game(move);
            cli4.game(move);

            move = "@VSHE";
            cli1.game(move);
            cli2.game(move);
            cli3.game(move);
            cli4.game(move);

            //An error is issued
            move = "@GAME (0,3),0";
            cli1.game(move);
            cli2.game(move);
            cli3.game(move);
            cli4.game(move);

            //Simulate disconnection
            controller.getServerSide().getServerSideTCP().addDisconnectedPlayer("Player_2");
            controller.getServerSide().getLoginManager().addDisconnectedPlayer("Player_3");
            controller.getServerSide().getServerSideRMI().removeRMIClient("Player_3");
            controller.getServerSide().getServerSideTCP().addDisconnectedPlayer("Player_4");

            TimeUnit.SECONDS.sleep(1);

            assertEquals(1, controller.getServerSide().getServerSideRMI().getNameToStub().size());
            assertEquals(0, controller.getServerSide().getServerSideTCP().getNickNameToClientHandler().size());

            //An error is issued -> only one player connected
            move = "@GAME (7,5),0";
            cli1.game(move);

            //Reconnect two client
            cli2 = new CLIWithInputString(new ByteArrayInputStream((1 + enter + "" + enter + "" + enter + 4 + enter + "Player_2" + enter).getBytes()));
            cli3 = new CLIWithInputString(new ByteArrayInputStream((3 + enter + "" + enter + 1 + enter + 4 + enter + "Player_3" + enter).getBytes()));
            cli2.setupCLI();
            cli3.setupCLI();
            TimeUnit.SECONDS.sleep(1);
            assertEquals(2, controller.getServerSide().getServerSideRMI().getNameToStub().size());
            assertEquals(1, controller.getServerSide().getServerSideTCP().getNickNameToClientHandler().size());

            //Error is issued
            move = "@GAME (1,3),(1,4),0";
            cli1.game(move);
            cli2.game(move);
            cli3.game(move);

            assertNull(controller.getGameController().getCurrentGame().getGameBoard().getBoard()[1][3]);
            assertNull(controller.getGameController().getCurrentGame().getGameBoard().getBoard()[1][4]);

            //Simulate end game
            Shelfie fullShelfie = new Shelfie();
            int counter = 0;
            for (int i = 0; i < Constants.shelfieColumns; i++) {
                for (int j = 0; j < Constants.shelfieRows; j++) {
                    fullShelfie.setGrid(j, i, counter, Tile.TileColor.WHITE);
                    counter++;
                }
            }
            fullShelfie.setGridAtNull(Constants.shelfieRows - 1, Constants.shelfieColumns - 1);
            controller.getGameController().getCurrentGame().getPlayers().get(0).setShelfie(fullShelfie);
            controller.getGameController().getCurrentGame().getPlayers().get(1).setShelfie(fullShelfie);
            controller.getGameController().getCurrentGame().getPlayers().get(2).setShelfie(fullShelfie);

            move = "@CHAT 'all' Test";
            cli1.game(move);
            cli2.game(move);
            cli3.game(move);
            move = "@CHAT 'Player_1' Test";
            cli1.game(move);
            cli2.game(move);
            cli3.game(move);
            move = "@CHAT 'Player_5' Test";
            cli1.game(move);
            cli2.game(move);
            cli3.game(move);
            move = "@CHAT 'Player_4' Test";
            cli1.game(move);
            cli2.game(move);
            cli3.game(move);

            move = "@GAME (2,3),4";
            cli1.game(move);
            cli2.game(move);
            cli3.game(move);

            move = "@GAME (2,4),4";
            cli1.game(move);
            cli2.game(move);
            cli3.game(move);

            move = "@GAME (8,4),4";
            cli1.game(move);
            cli2.game(move);
            cli3.game(move);


        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        TimeUnit.SECONDS.sleep(1);

        assertNull(controller.getGameController().getCurrentGame().getGameBoard().getBoard()[0][3]);
        assertNull(controller.getGameController().getCurrentGame().getGameBoard().getBoard()[0][4]);
        assertNotNull(controller.getGameController().getCurrentGame().getGameBoard().getBoard()[5][3]);
        assertNotNull(controller.getGameController().getCurrentGame().getGameBoard().getBoard()[5][4]);
    }
}