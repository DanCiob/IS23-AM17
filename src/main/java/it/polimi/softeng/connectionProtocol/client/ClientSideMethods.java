package it.polimi.softeng.connectionProtocol.client;

import it.polimi.softeng.client.view.CLI.CLI;
import it.polimi.softeng.connectionProtocol.client.ClientRemoteInterface;
import it.polimi.softeng.model.*;
import it.polimi.softeng.model.commonCards.CommonCards;
import org.json.simple.JSONObject;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ClientSideMethods implements ClientRemoteInterface {
    private CLI cli;
    @Override
    public void playerUpdate(Player player) throws RemoteException {
        //TODO what does this function do?
    }

    @Override
    public void shelfieUpdate(Shelfie shelfie) throws RemoteException {
        cli.eventManager("shelfieEvent");
        cli.shelfieUpdater(shelfie);
        cli.shelfieVisualizer(shelfie.getGrid());
    }

    @Override
    public void gameBoardUpdate(GameBoard board) throws RemoteException {
        cli.eventManager("boardEvent");
        cli.boardUpdater(board);
        cli.boardVisualizer(board.getBoard(), board.getNotAvailable());
    }

    @Override
    public void sendPersonalCard(PersonalCards personalCards) throws RemoteException {
        cli.eventManager("personalCardEvent");
        cli.personalCardUpdater(personalCards);
        cli.personalCardVisualizer(personalCards);
    }

    @Override
    public void sendCommonCard(ArrayList<CommonCards> commonCards) throws RemoteException {
        cli.eventManager("commonCardEvent");
        for (CommonCards commonCard : commonCards) {
            cli.commonCardsVisualizer(commonCard.getName());
        }
    }

    @Override
    public void sendBadge(Badge badge) throws RemoteException {
        cli.scoreUpdater(badge.getScore());
    }

    @Override
    public void displayChatMessage(String message, String sender) throws RemoteException {
        int found = 0;
        String receiver = null;

        receiver = message.substring(message.indexOf("'" + 1));
        receiver = receiver.substring(0, message.indexOf("'"));

        message = message.replace("'"+receiver+"' ", "");

        if (receiver.equals("all"))
        {
            cli.eventManager("globalChatEvent");
            JSONObject obj = new JSONObject();
            obj.put("requester", sender);
            obj.put("message", message);
            cli.chatVisualizer(obj);
        }
        else
        {
            cli.eventManager("chatEvent");
            JSONObject obj = new JSONObject();
            obj.put("requester", sender);
            obj.put("message", message);
            cli.chatVisualizer(obj);
        }

    }

    @Override
    public void playerListUpdate(ArrayList<Player> playerList) throws RemoteException {
        cli.eventManager("playerEvent");
        cli.scoreVisualizer(playerList);
    }

    @Override
    public Boolean ping() throws RemoteException {
        return true;
    }

}
