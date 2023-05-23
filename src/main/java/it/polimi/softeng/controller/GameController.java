package it.polimi.softeng.controller;

import it.polimi.softeng.JSONWriter.*;
import it.polimi.softeng.connectionProtocol.client.ClientRemoteInterface;
import it.polimi.softeng.model.*;
import it.polimi.softeng.model.commonCards.CommonCards;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Communicate gameMoves to model, communicate via interfaces
 */
public class GameController {
    private Game game;
    private final Controller controller;

    public GameController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Call model update with information collected by client
     *
     * @param tilesToBeRemoved is positions of cell to be removed
     * @param column           is column of insertion
     * @param requester        is receiver of updates
     */
    public boolean sendGameMove(ArrayList<Cell> tilesToBeRemoved, int column, String requester) {
        //Reject request if it's not player's turn
        System.out.println("Current player: " + game.getCurrentPlayer().getNickname());
        if (!requester.equals(game.getCurrentPlayer().getNickname())) {
            System.out.println("Received gameMove request by " + requester + " but it's " + game.getCurrentPlayer().getNickname() + " turn");
            return false;
        }
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

                //Update board
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

                //Update shelfie
                //TODO make shelfies visible by everybody
                //TODO if meanwhile player disconnects ?
                //Is a RMI user
                if (controller.getServerSide().getServerSideRMI().getNameToStub().containsKey(requester)) {
                    ClientRemoteInterface temp = controller.getServerSide().getServerSideRMI().getNameToStub().get(requester);
                    try {
                        temp.shelfieUpdate(currentPlayer.getShelfie());
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }
                //Is TCP user
                else
                    controller.getServerSide().sendMessage(ServerSignatureWriter.serverSignObject(ShelfieWriter.shelfieChangeNotifier(game.getPlayers().stream().filter(p -> p.getNickname().equals(requester)).findFirst().get().getShelfie()), "@SHEL", requester).toJSONString(), requester);
                System.out.println("Updated shelfie sent");

                //Notify next player
                //it doesnt really work
                //here goes the code to skip players in case of disconnection
                //TODO control because this may break if disconnectedPlayerList is updated in the meantime
                Boolean connectedPlayerNotFound = true;
                while(connectedPlayerNotFound){
                    if(controller.getServerSide().getLoginManager().getDisconnectedPlayerList().contains(game.getCurrentPlayer().getNickname())){
                        game.setNextPlayer();
                    }else connectedPlayerNotFound = false;
                }
                //Is an RMI user
                if (controller.getServerSide().getServerSideRMI().getNameToStub().containsKey(game.getCurrentPlayer().getNickname())) {
                    ClientRemoteInterface temp = controller.getServerSide().getServerSideRMI().getNameToStub().get(game.getCurrentPlayer().getNickname());
                    try {
                        temp.displayChatMessage("It's your turn!", "System");
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }
                //Is TCP user
                else
                    controller.getServerSide().sendMessage(ServerSignatureWriter.serverSignObject(ConfirmWriter.writeConfirm(), "@CONF", game.getCurrentPlayer().getNickname()).toJSONString(), game.getCurrentPlayer().getNickname());

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
     * Manage setup of model and notifications of all setup items to clients
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
            //TODO add command for rmi users to show shelfie
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //Send Common Cards to everyone
        ArrayList<CommonCards> cc = game.getCommonCards();

        //Send to RMI users
        for (String s : controller.getServerSide().getServerSideRMI().getNameToStub().keySet()) {
            ClientRemoteInterface temp = controller.getServerSide().getServerSideRMI().getNameToStub().get(s);
            try {
                temp.sendCommonCard(cc);
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

    public Game getCurrentGame() {
        return game;
    }
}
