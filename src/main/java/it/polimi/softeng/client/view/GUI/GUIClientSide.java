package it.polimi.softeng.client.view.GUI;

import it.polimi.softeng.client.view.CLI.CLI;
import it.polimi.softeng.client.view.MessageHandler;
import it.polimi.softeng.connectionProtocol.client.ClientSide;

import java.rmi.RemoteException;
import java.util.concurrent.TimeUnit;

public class GUIClientSide {
    MessageHandler messageHandler;
    static CLI cli;
    static ClientSide clientSide;

    public GUIClientSide() {
        cli = new CLI();
        messageHandler = new MessageHandler(cli);
        clientSide = new ClientSide(messageHandler);
        cli.setGuiClientSide(this);
    }

    public static void setupCliForGui(int connectionMode, String serverAddress, int port, int startGame, int numPlayers, int mode){
        cli.setConnectionMode(connectionMode);
        switch (cli.getConnectionMode()) {
            case 1 -> { //socket
                cli.setServerAddress(serverAddress);
                cli.setPort(port);
                cli.setStartGame(startGame);
                if(startGame == 1){
                    cli.setNumOfPlayer(numPlayers);
                    cli.setGameMode(mode);
                }
            }
            case 2 -> { //TODO: RMI
            }
        }
    }

    public static ClientSide getClientSide() {
        return clientSide;
    }

    public static CLI getCli() {
        return cli;
    }

    public static void run() {
        boolean firstRun = true;
        boolean GameIsOn = false;

        System.out.println("Waiting for other players to join...");
        //Waiting for beginning of game
        while (!GameIsOn) {
            System.out.println("Waiting for other players to join...");
        }

        while (GameIsOn) {
            try {
                //Wait for errors
                TimeUnit.SECONDS.sleep(1);
                cli.game(firstRun);
            } catch (InterruptedException | RemoteException e) {
                throw new RuntimeException(e);
            }
            firstRun = false;
        }

        cli.getInput().close();
    }
}
