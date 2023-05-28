package it.polimi.softeng.connectionProtocol.client;

import it.polimi.softeng.client.view.UI;
import it.polimi.softeng.model.*;
import it.polimi.softeng.model.commonCards.CommonCards;
import org.json.simple.JSONObject;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ClientSideMethods implements ClientRemoteInterface {
    private UI ui;

    public ClientSideMethods(UI ui){
        this.ui = ui;
    }
    @Override
    public void playerUpdate(Player player, Shelfie s) throws RemoteException {
        ui.eventManager("systemEvent");
        JSONObject obj = new JSONObject();
        obj.put("requester", "System");
        obj.put("message", "Received " + player.getNickname() + "'s shelfie");
        ui.chatVisualizer(obj);
        ui.shelfieVisualizer(s.getGrid());
    }

    @Override
    public void shelfieUpdate(Shelfie shelfie) throws RemoteException {
        ui.eventManager("shelfieEvent");
        ui.shelfieUpdater(shelfie);
        ui.shelfieVisualizer(shelfie.getGrid());
    }

    @Override
    public void gameBoardUpdate(GameBoard board) throws RemoteException {
        ui.eventManager("boardEvent");
        ui.boardUpdater(board);
        ui.boardVisualizer(board.getBoard(), board.getNotAvailable());
    }

    @Override
    public void sendPersonalCard(PersonalCards personalCards) throws RemoteException {
        ui.eventManager("personalCardEvent");
        ui.personalCardUpdater(personalCards);
        ui.personalCardVisualizer(personalCards);
    }

    @Override
    public void sendCommonCard(ArrayList<CommonCards> commonCards) throws RemoteException {
        ui.eventManager("commonCardEvent");
        int i = 1;
        for (CommonCards commonCard : commonCards) {
            ui.commonCardUpdater(commonCard.getName(), i);
            i++;
            ui.commonCardsVisualizer(commonCard.getName());
        }
    }

    @Override
    public void sendBadge(Badge badge) throws RemoteException {
        ui.scoreUpdater(badge.getScore());
    }

    @Override
    public void displayChatMessage(String message, String sender) throws RemoteException {

        String receiver = null;

        JSONObject obj = new JSONObject();
        obj.put("requester", sender);
        obj.put("message", message);
        ui.chatVisualizer(obj);
    }

    @Override
    public void playerListUpdate(ArrayList<Player> playerList) throws RemoteException {
        ui.eventManager("playerEvent");
        ui.scoreVisualizer(playerList);
    }

    @Override
    public void startGame() throws RemoteException {
        ui.beginGame(true);
    }

    @Override
    public void endGame(boolean winner) throws RemoteException {
        ui.endGame(winner);
    }

    @Override
    public void notifyTurn() throws RemoteException {
        ui.eventManager("myTurn");
    }


    @Override
    public Boolean ping() throws RemoteException {
        return true;
    }

}
