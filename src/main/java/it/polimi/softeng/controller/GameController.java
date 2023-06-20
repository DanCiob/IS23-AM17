package it.polimi.softeng.controller;

import it.polimi.softeng.JSONWriter.*;
import it.polimi.softeng.connectionProtocol.client.ClientRemoteInterface;
import it.polimi.softeng.model.*;
import it.polimi.softeng.model.commonCards.CommonCards;

import java.lang.reflect.Array;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static it.polimi.softeng.JSONWriter.PlayerWriter.playerAndScoreWriter;
import static it.polimi.softeng.JSONWriter.ServerSignatureWriter.serverSignObject;

/**
 * This class allows to communicate gameMoves and the start of a game to the Model.
 * ??? communicate via interfaces
 *
 */
public class GameController {
    private Game game;
    private final Controller controller;

    public GameController(Controller controller) {
        this.controller = controller;
    }

    /**
     * This method calls model update with information collected by client
     *
     * @param tilesToBeRemoved is positions of cell to be removed
     * @param column           is column of insertion
     * @param requester        is receiver of updates
     */
    public boolean sendGameMove(ArrayList<Cell> tilesToBeRemoved, int column, String requester) {

        ////////////////////////////////
        //CHECK IF IT'S REQUESTER TURN//
        ////////////////////////////////

        //Reject request if it's not player's turn
        System.out.println("Current player: " + game.getCurrentPlayer().getNickname());
        if (!requester.equals(game.getCurrentPlayer().getNickname())) {
            System.out.println("Received gameMove request by " + requester + " but it's " + game.getCurrentPlayer().getNickname() + " turn");
            return false;
        }

        /////////////////////////////
        //BEGINNING OF TURN ROUTINE//
        /////////////////////////////

        //Turn routine update
        Player currentPlayer = game.getCurrentPlayer(); //reference used to send the shelfie of the player who made a move
        int result = game.turn(tilesToBeRemoved, column);


        switch (result) {
            //Error
            case -1 -> {
                return false;
            }

            //Normal game turn
            case 0 -> {
                System.out.println("Board updated");
                System.out.println("Shelfie updated");

                ////////////////
                //BOARD UPDATE//
                ////////////////

                //Notifies every RMI User -> gameBoard notifications
                for (String s : controller.getServerSide().getServerSideRMI().getNameToStub().keySet()) {
                    ClientRemoteInterface temp = controller.getServerSide().getServerSideRMI().getNameToStub().get(s);
                    try {
                        temp.gameBoardUpdate(game.getGameBoard());
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }
                //Notifies every TCP user
                controller.getServerSide().sendMessageToAll(ServerSignatureWriter.serverSignObject(BoardWriter.boardChangeNotifier(game.getGameBoard()), "@BORD", "all").toJSONString());
                System.out.println("Updated board sent");

                //////////////////
                //SHELFIE UPDATE//
                //////////////////

                Map<Shelfie, Player> shelfiesToSend = new HashMap<>();
                //Collect all shelfies
                for (Player p: game.getPlayers())
                    shelfiesToSend.put(p.getShelfie(), p);

                //Send shelfies to everybody
                for (Player p: game.getPlayers()) {
                    for (Shelfie s : shelfiesToSend.keySet()) {
                        //Is a RMI user
                        if (controller.getServerSide().getServerSideRMI().getNameToStub().containsKey(p.getNickname())) {
                            ClientRemoteInterface temp = controller.getServerSide().getServerSideRMI().getNameToStub().get(p.getNickname());
                            try {
                                if (shelfiesToSend.get(s).getNickname().equals(p.getNickname())) {
                                    System.out.println(temp);
                                    System.out.println(s);
                                    temp.shelfieUpdate(s);
                                }
                                else
                                    temp.playerUpdate(shelfiesToSend.get(s), s);
                                System.out.println("Player " + p.getNickname() + " updated shelfie sent");
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        //Is TCP user
                        else
                        if (shelfiesToSend.get(s).getNickname().equals(p.getNickname()))
                            controller.getServerSide().sendMessage(ServerSignatureWriter.serverSignObject(ShelfieWriter.shelfieChangeNotifier(s), "@SHEL", p.getNickname()).toJSONString(), p.getNickname());
                        else
                            controller.getServerSide().sendMessage(ServerSignatureWriter.serverSignObject(ShelfieWriter.shelfieChangeNotifier(s, shelfiesToSend.get(s).getNickname()), "@OSHE", p.getNickname()).toJSONString(), p.getNickname());
                        System.out.println("Player " + p.getNickname() + " updated shelfie sent");
                    }
                }

                //////////////////////
                //BADGE COMMON CARDS//
                //////////////////////
                /*
                ArrayList <CommonCards> cc = game.getCommonCards();

                for (Player p: game.getPlayers()) {
                    System.out.println("Sending common cards to " + p.getNickname());
                    //Is a RMI user
                    if (controller.getServerSide().getServerSideRMI().getNameToStub().containsKey(p.getNickname())) {
                        ClientRemoteInterface temp = controller.getServerSide().getServerSideRMI().getNameToStub().get(p.getNickname());
                        try {
                            temp.sendCommonCard(cc, false);
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    //Is TCP user
                    else {
                        if (cc.size() == 1)
                            controller.getServerSide().sendMessageToAll(ServerSignatureWriter.serverSignObject(CommonCardWriter.writeCommonCard(cc.get(0).getName(), null), "@CCAU", "all").toJSONString());
                        else
                            controller.getServerSide().sendMessageToAll(ServerSignatureWriter.serverSignObject(CommonCardWriter.writeCommonCard(cc.get(0).getName(), cc.get(1).getName()), "@CCAU", "all").toJSONString());
                }
            }*/

            ////////////////////////////
            //NEXT PLAYER NOTIFICATION//
            ////////////////////////////

            //here goes the code to skip players in case of disconnection (in this point is managed the disconnection of a player that isn't the current one)
            selectNextPlayer();

            /*
            //Is an RMI user
            if (controller.getServerSide().getServerSideRMI().getNameToStub().containsKey(game.getCurrentPlayer().getNickname())) {
                ClientRemoteInterface temp = controller.getServerSide().getServerSideRMI().getNameToStub().get(game.getCurrentPlayer().getNickname());
                try {
                    temp.notifyTurn();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            //Is TCP user
            else
                controller.getServerSide().sendMessage(ServerSignatureWriter.serverSignObject(ConfirmWriter.writeConfirm(), "@CONF", game.getCurrentPlayer().getNickname()).toJSONString(), game.getCurrentPlayer().getNickname());*/
            notifyTurn();

            return true;
        }

        //Win phase
        case 1 -> {
            String winner = game.getWinner().getNickname();

            //Notify RMI users
            for (String s : controller.getServerSide().getServerSideRMI().getNameToStub().keySet()) {
                ClientRemoteInterface temp = controller.getServerSide().getServerSideRMI().getNameToStub().get(s);
                try {
                    temp.endGame(s.equals(winner));
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }

            //Notify TCP users
            for (String s : controller.getServerSide().getServerSideTCP().getNickNameToClientHandler().keySet()) {
                if (s.equals(winner))
                    controller.getServerSide().sendMessage(ServerSignatureWriter.serverSignObject(EndGameWriter.writeEndGame(true), "@ENDG", "System").toJSONString(), s);
                else
                    controller.getServerSide().sendMessage(ServerSignatureWriter.serverSignObject(EndGameWriter.writeEndGame(false), "@ENDG", "System").toJSONString(), s);
            }
        }
    }
        return true;
}

    /**
     * This method manages the game-start setup of the Model (preparation of board, shelfie, PC, CC...) and sends the
     *  information to Clients. It also notifies the first player for the beginning of its turn.
     * @param nameList of the players
     */
    public void startGame(ArrayList<String> nameList) {
        game = new Game();
        game.beginGame(nameList);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //Send board to everybody -> RMI
        for (String s : controller.getServerSide().getServerSideRMI().getNameToStub().keySet()) {
            ClientRemoteInterface temp = controller.getServerSide().getServerSideRMI().getNameToStub().get(s);
            try {
                temp.gameBoardUpdate(game.getGameBoard());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        //Send board to everybody -> TCP
        controller.getServerSide().sendMessageToAll(ServerSignatureWriter.serverSignObject(BoardWriter.boardChangeNotifier(game.getGameBoard()), "@BORD", "all").toJSONString());

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //Send empty shelfie to everybody -> RMI
        for (String s : controller.getServerSide().getServerSideRMI().getNameToStub().keySet()) {
            ClientRemoteInterface temp = controller.getServerSide().getServerSideRMI().getNameToStub().get(s);
            try {
                temp.shelfieUpdate(game.getCurrentPlayer().getShelfie());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        //Send empty shelfie to everybody -> TCP
        for (String s : controller.getServerSide().getServerSideTCP().getNickNameToClientHandler().keySet())
            controller.getServerSide().sendMessage(ServerSignatureWriter.serverSignObject(ShelfieWriter.shelfieChangeNotifier(game.getCurrentPlayer().getShelfie()), "@SHEL", s).toJSONString(), s);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //Send personal card to everybody
        ArrayList<Player> players;
        players = game.getPlayers();
        for (String s : nameList) {
            //Get personal card related to player
            Player actualPlayer = players.stream().filter(p -> p.getNickname().equals(s)).findAny().get();
            PersonalCards pc = actualPlayer.getPersonalCard();
            //If it's an RMI user
            if (controller.getServerSide().getServerSideRMI().getNameToStub().containsKey(actualPlayer.getNickname())) {
                ClientRemoteInterface temp = controller.getServerSide().getServerSideRMI().getNameToStub().get(s);
                try {
                    temp.sendPersonalCard(pc);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            //If it's a TCP  user
            else
                controller.getServerSide().sendMessage(ServerSignatureWriter.serverSignObject(PersonalCardWriter.writePersonalCard(pc), "@VPCA", s).toJSONString(), s);
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //Send Common Cards to everyone
        ArrayList<CommonCards> cc = game.getCommonCards();

        //Send to RMI users
        for (String s : controller.getServerSide().getServerSideRMI().getNameToStub().keySet()) {
            ClientRemoteInterface temp = controller.getServerSide().getServerSideRMI().getNameToStub().get(s);
            try {
                temp.sendCommonCard(cc, true);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        //Send to all TCP user
        if (cc.size() == 1)
            controller.getServerSide().sendMessageToAll(ServerSignatureWriter.serverSignObject(CommonCardWriter.writeCommonCard(cc.get(0).getName(), null), "@VCCA", "all").toJSONString());
        else
            controller.getServerSide().sendMessageToAll(ServerSignatureWriter.serverSignObject(CommonCardWriter.writeCommonCard(cc.get(0).getName(), cc.get(1).getName()), "@VCCA", "all").toJSONString());

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //Send all players and their score

        //Send to RMI users
        for (String s : controller.getServerSide().getServerSideRMI().getNameToStub().keySet()) {
            ClientRemoteInterface temp = controller.getServerSide().getServerSideRMI().getNameToStub().get(s);
            try {
                temp.playerListUpdate(game.getPlayers());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        //Send to all TCP user
        controller.getServerSide().sendMessageToAll(serverSignObject(playerAndScoreWriter(getCurrentGame().getPlayers()), "@VPLA", "System").toJSONString());

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Ended setup
        //Notify first player
        controller.getServerSide().sendMessage(ServerSignatureWriter.serverSignObject(ConfirmWriter.writeConfirm(), "@CONF", game.getCurrentPlayer().getNickname()).toJSONString(), game.getCurrentPlayer().getNickname());
        for(String player : controller.getServerSide().getServerSideRMI().getNameToStub().keySet()){
            if(player.equals(game.getCurrentPlayer().getNickname())){
                try {
                    controller.getServerSide().getServerSideRMI().getNameToStub().get(player).notifyTurn();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public void selectNextPlayer(){
        boolean connectedPlayerNotFound = true;
        while(connectedPlayerNotFound){
            if(controller.getServerSide().getLoginManager().getDisconnectedPlayerList().contains(game.getCurrentPlayer().getNickname())){
                game.setNextPlayer();
            }else connectedPlayerNotFound = false;
        }
    }

    public void notifyTurn(){
        if (controller.getServerSide().getServerSideRMI().getNameToStub().containsKey(game.getCurrentPlayer().getNickname())) {
            ClientRemoteInterface temp = controller.getServerSide().getServerSideRMI().getNameToStub().get(game.getCurrentPlayer().getNickname());
            try {
                temp.notifyTurn();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        //Is TCP user
        else
            controller.getServerSide().sendMessage(ServerSignatureWriter.serverSignObject(ConfirmWriter.writeConfirm(), "@CONF", game.getCurrentPlayer().getNickname()).toJSONString(), game.getCurrentPlayer().getNickname());

    }
    public Game getCurrentGame() {
        return game;
    }
}
