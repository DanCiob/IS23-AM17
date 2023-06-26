package it.polimi.softeng.client.view.GUI;

import it.polimi.softeng.model.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.json.simple.JSONObject;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static it.polimi.softeng.JSONWriter.ClientSignatureWriter.clientSignObject;

/**
 * Manage end game screen in GUI
 */
public class EndGameController implements Initializable {

    @FXML
    Label winner;

    @FXML
    Label player1;
    @FXML
    Label pointsPlayer1;

    @FXML
    Label player2;
    @FXML
    Label pointsPlayer2;

    @FXML
    Label player3;
    @FXML
    Label pointsPlayer3;

    @FXML
    Label player4;
    @FXML
    Label pointsPlayer4;


    @FXML
    protected void setWinner(String nickname){
        winner.setText(nickname);
    }

    /**
     * This method retriev
     * @param location
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for(GUIClientSide guiClientSide:  GUIRegistry.guiList){
            guiClientSide.setEndGameController(this);
            GUIRegistry.numberOfGUI++;

            switch (guiClientSide.getConnectionMode()) {
                //Socket
                case 1 -> {
                    JSONObject dummy = new JSONObject();
                    guiClientSide.getClientSide().sendMessage(clientSignObject(dummy, "@VPLA", guiClientSide.getNickname()).toJSONString());

                }
                //RMI
                case 2 -> {
                    try {
                        scoreVisualizer(guiClientSide.getRemoteMethods().getStub().getPlayersAndScore());
                    } catch (RemoteException e) {
                        System.out.println("Error");
                    }
                }
            }
        }
    }

    @FXML
    public void scoreVisualizer(ArrayList<Player> players){
        player1.setText(players.get(0).getNickname());
        pointsPlayer1.setText(Integer.toString(players.get(0).getCurrentScore()));
        player2.setText(players.get(1).getNickname());
        pointsPlayer2.setText(Integer.toString(players.get(1).getCurrentScore()));
        if(players.size()>=3){
            player3.setText(players.get(2).getNickname());
            pointsPlayer3.setText(Integer.toString(players.get(2).getCurrentScore()));
        }
        if(players.size()==4){
            player4.setText(players.get(3).getNickname());
            pointsPlayer4.setText(Integer.toString(players.get(3).getCurrentScore()));
        }
    }
}