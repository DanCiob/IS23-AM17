package it.polimi.softeng.client.view.GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO: set nicknames and points
        player1.setText("Player 1"); pointsPlayer1.setText("0");
        player2.setText("Player 2"); pointsPlayer2.setText("1");
        player3.setText("Player 3"); pointsPlayer3.setText("2");
        player4.setText("Player 4"); pointsPlayer4.setText("3");
        winner.setText("10");
    }


}
