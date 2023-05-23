package it.polimi.softeng.client.view.CLI;

import it.polimi.softeng.JSONParser.ChatParser;
import it.polimi.softeng.JSONParser.GameMoveParser;
import it.polimi.softeng.JSONWriter.ChatWriter;
import it.polimi.softeng.JSONWriter.ClientSignatureWriter;
import it.polimi.softeng.JSONWriter.GameMoveWriter;
import it.polimi.softeng.JSONWriter.LoginWriter;
import it.polimi.softeng.client.view.MessageHandler;
import it.polimi.softeng.connectionProtocol.client.ClientSide;
import it.polimi.softeng.connectionProtocol.client.ClientSideRMI;
import it.polimi.softeng.connectionProtocol.server.ServerSideMethods;
import it.polimi.softeng.model.*;
import it.polimi.softeng.client.view.CommonOperationsFramework;
import it.polimi.softeng.client.view.UI;
import it.polimi.softeng.model.PersonalCards;
import org.json.simple.JSONObject;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static it.polimi.softeng.Constants.*;
import static it.polimi.softeng.JSONWriter.ClientSignatureWriter.clientSignObject;

public class CLITest extends CLI implements UI, Runnable {

    private final Scanner input;
    private final MessageHandler messageHandler;

    public CLITest() {
        this.messageHandler = new MessageHandler(this);
        this.input = new Scanner(System.in);
    }


    /**
     * CLI initialization, connection to server, choose of gameMode
     * After setup CLI is ready to be used
     */
    public void setupCLI() {
        int mode;

        System.out.println("Initializing CLI...");
        System.out.println("Do you want to connect using Socket(1) or RMI(2)?");
        System.out.println(">");
        do {
            ConnectionMode = Integer.parseInt(input.nextLine());

            switch (ConnectionMode) {
                case 1 -> { //socket
                    System.out.println("Connection with Socket...");
                    System.out.println("Digit server IP");
                    System.out.println(">");
                    ServerAddress = input.nextLine();
                    System.out.println("Digit server Port");
                    System.out.println(">");
                    Port = input.nextInt();
                    input.nextLine();
                    System.out.println("Do you want to create a new game(1) or join a game which is already started(2)?");
                    System.out.println("If you want to reconnect to a previous game choose 2 and use the same nickname");
                    System.out.println(">");

                    do {
                        StartGame = input.nextInt();
                        input.nextLine();
                    } while (StartGame != 1 && StartGame != 2);
                    if (StartGame == 1) {
                        do {
                            System.out.println("Insert the number of players(2-4)");
                            System.out.println(">");
                            NumOfPlayer = input.nextInt();
                            input.nextLine();
                        } while (NumOfPlayer < 2 || NumOfPlayer > 4);

                        System.out.println("Do you want to play with Easy mode(1) or Normal mode(2)?");
                        System.out.println(">");
                        do {
                            GameMode = input.nextInt();
                            input.nextLine();
                        } while (GameMode != 1 && GameMode != 2);
                    }

                    //TODO nicknameUniqueness
                    //do {
                    do {
                        System.out.println("Insert nickname (ONLY characters a-z A-Z 0-9 and _ allowed)");
                        System.out.println(">");
                        Nickname = input.nextLine();

                        //TODO LoginWriter...
                    } while (!isOkNickname());

                    //TODO Now connect to 127.0.0.1
                    String login = ClientSignatureWriter.clientSignObject(LoginWriter.writeLogin(Nickname, GameMode, StartGame, NumOfPlayer), "@LOGN", Nickname).toJSONString();
                    System.out.println(login);
                    this.clientSide = new ClientSide(messageHandler);
                    clientSide.sendMessage(login);
                }


                //}while(nicknameNotUnique())
                case 2 -> {//RMI
                    System.out.println("Connection with RMI...");
                    System.out.println("Digit server IP");
                    System.out.println(">");
                    ServerAddress = input.nextLine();
                    System.out.println("Digit client Port");
                    System.out.println(">");
                    Port = Integer.parseInt(input.nextLine());
                    System.out.println("Do you want to create a new game(1) or join a game which is already started(2)?");
                    System.out.println("If you want to reconnect to a previous game choose 2 and use the same nickname");
                    System.out.println(">");
                    do {
                        StartGame = input.nextInt();
                        input.nextLine();
                    } while (StartGame != 1 && StartGame != 2);
                    if (StartGame == 1) {
                        System.out.println("Insert the number of players(2-4)?");
                        System.out.println(">");
                        do {
                            NumOfPlayer = input.nextInt();
                            input.nextLine();

                            if (NumOfPlayer > 4 || NumOfPlayer < 2)
                                eventManager(INVALID_NUMBER_OF_PLAYERS);
                        } while (NumOfPlayer > 4 || NumOfPlayer < 2);

                        System.out.println("Do you want to play with Easy mode(1) or Normal mode(2)?");
                        System.out.println(">");
                        do {
                            GameMode = input.nextInt();
                            input.nextLine();
                        } while (GameMode != 1 && GameMode != 2);
                    }

                    do {
                        System.out.println("Insert nickname (ONLY characters a-z A-Z 0-9 and _ allowed)");
                        System.out.println(">");
                        Nickname = input.nextLine();
                        System.out.println(Nickname);
                        //TODO Nickname uniqueness
                    } while (!isOkNickname());

                    //TODO to be removed
                    String GameModeStringifed = GameMode == 1 ? "e" : "n";
                    this.RemoteMethods = new ClientSideRMI(Port,this);
                    System.out.println(GameModeStringifed);
                    RMIInvoker("@LOGN", GameModeStringifed);
                }
                default -> System.out.println("Unrecognized connection method, please digit 1 or 2...");
            }
        } while (ConnectionMode != 1 && ConnectionMode != 2);
    }

    /**
     * Notify CLI when game begins
     * @param value is boolean
     */
    public void beginGame(boolean value) {
        this.GameIsOn = value;
        System.out.println("i changed the value to : " + this.GameIsOn);
    }

    /**
     * Main class of CLI, continuously run from begin of game to the end
     */
    @Override
    public void run() {
        boolean firstRun = true;
        setupCLI();
        //GameIsOn = false;   questo assegnamento va fatto a costruzione

        System.out.println("Waiting for other players to join...");
        //Waiting for beginning of game
        while (!GameIsOn) {
            System.out.println("value of gameIsOn: " + this.GameIsOn);
            try {
                loadingScreen();
                System.out.println("Waiting for other players to join...");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        while (GameIsOn) {
            try {
                //Wait for errors
                TimeUnit.SECONDS.sleep(1);
                game(firstRun);
            } catch (InterruptedException | RemoteException e) {
                throw new RuntimeException(e);
            }
            firstRun = false;
        }

        input.close();
    }


    //TODO to modify
    /**
     * @param op     is command
     * @param action is command text sent by UI
     * @return true if action is correctly executed
     */
    public boolean RMIInvoker(String op, String action)  {
        System.out.println("action in rmiInvoker: " + action);
        switch (op) {
            case ("@CHAT"): {
                if (!ChatWriter.chatMessageRegex(action)) {
                    System.out.println("Error in Chat message syntax, try again!");
                    return false;
                }

                JSONObject obj = new JSONObject();
                obj = ChatWriter.writeChatMessage(action);

                if (obj.get("receiver").toString().equals("all")) {
                    try {
                        RemoteMethods.getStub().sendMessageToAll(obj.toJSONString(), Nickname);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    try {
                        RemoteMethods.getStub().sendMessage(obj.toJSONString(), (String) obj.get("receiver"), Nickname);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
            }

            case ("@GAME"): {
                if (!GameMoveWriter.gameMoveRegex(action)) {
                    System.out.println("Error in Game Move syntax, try again!");
                    return false;
                }
                GameMoveParser gmp = new GameMoveParser();
                gmp.gameMoveParser(GameMoveWriter.writeGameMove(action).toJSONString());

                /*ArrayList<Tile> tiles = new ArrayList<>();
                for (Cell c : gmp.getTilesToBeRemoved()) {
                    Tile newTile = UserGameBoard.getBoard()[c.getRow()][c.getColumn()];
                    tiles.add(newTile);
                }*/

                ArrayList<Cell> cells = gmp.getTilesToBeRemoved();
                int column = gmp.getColumn();
                try {
                    RemoteMethods.getStub().sendMove(cells,column,Nickname);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                break;
            }

            case ("@LOGN"): {
                //TODO Right now we don't receive GameMode, StartGame, NumOfPlayer...
                try {
                    RemoteMethods.getStub().login(Nickname, NumOfPlayer, action, Port);
                    System.out.println("exectuted login for: " + Nickname);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                break;
            }

            default:
                System.out.println("Unrecognized operation!");
        }

        System.out.println("Test");
        return true;
    }

}