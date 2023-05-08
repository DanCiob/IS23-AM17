package it.polimi.softeng.controller;

import it.polimi.softeng.JSONWriter.*;
import it.polimi.softeng.customExceptions.IllegalInsertException;
import it.polimi.softeng.model.*;
import it.polimi.softeng.model.commonCards.CommonCards;

import java.util.ArrayList;

/**
 * Communicate gameMoves to model, communicate via interfaces
 */
public class GameController {
    private Game game;
    private Controller controller;

    public GameController (Controller controller)
    {
        this.controller = controller;
    }

    /**
     * Call model update with information collected by client
     * @param tilesToBeRemoved is positions of cell to be removed
     * @param column is column of insertion
     * @param requester is receiver of updates
     */
    public boolean sendGameMove(ArrayList<Cell> tilesToBeRemoved, int column, String requester) {
        //Reject request if it's not player's turn
        System.out.println("Current player: " + game.getCurrentPlayer().getNickname());
        if (!requester.equals(game.getCurrentPlayer().getNickname()))
        {
            System.out.println("Received gameMove request by " + requester + " but it's " + game.getCurrentPlayer().getNickname() + " turn");
            return false;
        }

        Tile[][] board= game.getGameBoard().getBoard();
        ArrayList<Tile> tiles = new ArrayList<>();
        for(Cell position : tilesToBeRemoved){
            tiles.add(board[position.getRow()][position.getColumn()]);
        }
        //GameBoard update
        System.out.println(tiles.size() + " will be removed");
        boolean confirm = game.getGameBoard().updateBoard(tilesToBeRemoved);

        if (!confirm)
            return false;

        System.out.println("Board updated");
        //Shelfie update
        try {
            game.getCurrentPlayer().getShelfie().insertTile(tiles, column);
            System.out.println("Shelfie updated");
        } catch (IllegalInsertException e) {
            throw new RuntimeException(e);
        }

        //Update board
        controller.getServerSide().sendMessageToAll(ServerSignatureWriter.serverSignObject(BoardWriter.boardChangeNotifier(game.getGameBoard()), "@BORD", "all").toJSONString());
        System.out.println("Updated board sent");
        //Update shelfie
        //TODO make shelfies visible by everybody
        controller.getServerSide().sendMessage(ServerSignatureWriter.serverSignObject(ShelfieWriter.shelfieChangeNotifier(game.getCurrentPlayer().getShelfie()), "@SHEL", requester).toJSONString(), requester);
        System.out.println("Updated shelfie sent");

        //Set next player
        game.setNextPlayer();

        //Notify next player
        controller.getServerSide().sendMessage(ServerSignatureWriter.serverSignObject(ConfirmWriter.writeConfirm(), "@CONF", game.getCurrentPlayer().getNickname()).toJSONString(), game.getCurrentPlayer().getNickname());

        return true;
    }

    /**
     * Manage setup of model and notifications of all setup items to clients
     */
    public void startGame (ArrayList<String> nameList)
    {
        game = new Game();
        game.beginGame(nameList);
        //Send board to everybody
        controller.getServerSide().sendMessageToAll(ServerSignatureWriter.serverSignObject(BoardWriter.boardChangeNotifier(game.getGameBoard()), "@BORD", "all").toJSONString());

        //Send empty shelfie to everybody
        for (String s : nameList)
            controller.getServerSide().sendMessage(ServerSignatureWriter.serverSignObject(ShelfieWriter.shelfieChangeNotifier(game.getCurrentPlayer().getShelfie()), "@SHEL", s).toJSONString(), s);

        //Send personal card to everybody
        ArrayList <Player> players = new ArrayList<>();
        players = game.getPlayers();
        for (String s : nameList)
        {
            //Get personal card related to player
            Player actualPlayer = players.stream().filter(p -> p.getNickname().equals(s)).findAny().get();
            PersonalCards pc = actualPlayer.getPersonalCard();
            //Send personal card to player view
            controller.getServerSide().sendMessage(ServerSignatureWriter.serverSignObject(PersonalCardWriter.writePersonalCard(pc), "@VPCA", s).toJSONString(), s);
        }

        //Send Common Cards to everyone
        ArrayList <CommonCards> cc = game.getCommonCards();

        if (cc.size() == 1)
            controller.getServerSide().sendMessageToAll(ServerSignatureWriter.serverSignObject(CommonCardWriter.writeCommonCard(cc.get(0).getName(), null), "@VCCA", "all").toJSONString());
        else
            controller.getServerSide().sendMessageToAll(ServerSignatureWriter.serverSignObject(CommonCardWriter.writeCommonCard(cc.get(0).getName(), cc.get(1).getName()), "@VCCA", "all").toJSONString());

        //Ended setup
        //Notify first player
        controller.getServerSide().sendMessage(ServerSignatureWriter.serverSignObject(ConfirmWriter.writeConfirm(), "@CONF", game.getCurrentPlayer().getNickname()).toJSONString(), game.getCurrentPlayer().getNickname());
    }

    public Game getCurrentGame() {
        return game;
    }
}
