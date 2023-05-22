package it.polimi.softeng.client.view.GUI;

import it.polimi.softeng.JSONWriter.ClientSignatureWriter;
import it.polimi.softeng.JSONWriter.LoginWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;


public class GUIController{

    @FXML
    Label np;
    @FXML
    TextField nickname;

    @FXML
    ChoiceBox<String> socketOrRmi;

    @FXML
    TextField serverIP;

    @FXML
    TextField serverPort;


    @FXML
    ChoiceBox<String> game;
    @FXML
    ChoiceBox<String> numberOfPlayer;

    @FXML
    ChoiceBox<String> mode;

    @FXML
    HBox hboxNumPlayers;

    @FXML
    HBox hboxMode;

    @FXML
    Button loginButton;


    @FXML
    protected void onNewGame(){
        if(game.getSelectionModel().getSelectedIndex() == 0){
            np.setText("Select the number of players: ");
            hboxNumPlayers.setVisible(true);
            hboxMode.setVisible(true);
        }
        if(game.getSelectionModel().getSelectedIndex() == 1){
            np.setText("Select the number of players: ");
            hboxNumPlayers.setVisible(false);
            hboxMode.setVisible(false);
        }
    }

    /**
     * This method sends the login message to the server
     * @param event which is when the user press the login button
     * @throws IOException called by load in switchToGame
     */
    @FXML
    protected void onLoginButtonClick(ActionEvent event) throws IOException{
        GUIClientSide.getCli().setNickname(nickname.getText());
        if(!GUIClientSide.getCli().isOkNickname()){
            nickname.setText("");
        }else{
            GUIClientSide.setupCliForGui(socketOrRmi.getSelectionModel().getSelectedIndex()+1, serverIP.getText(), Integer.parseInt(serverPort.getText()), game.getSelectionModel().getSelectedIndex() +1,numberOfPlayer.getSelectionModel().getSelectedIndex()+2, mode.getSelectionModel().getSelectedIndex()+1);
            loginNotifier();
            switchToGame(event);
        }
    }

    /**
     * This method change the scene from login to gamescreen
     * @param event which is the event of onLoginButtonClick
     * @throws IOException called by load
     */
    public void switchToGame(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/it.polimi.softeng.client.view.GUI/gamescreen.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method sends the login message to the server
     */
    public void loginNotifier(){
        int startgame;
        int gamemode = 0;
        int numPlayers = 0;

        if(game.getSelectionModel().getSelectedIndex() == 0)
            startgame = 1;
        else {
            startgame = 2;
        }

        if(numberOfPlayer.getSelectionModel().getSelectedIndex() == 0)
            numPlayers = 2;
        else if(numberOfPlayer.getSelectionModel().getSelectedIndex() == 1)
            numPlayers = 3;
            else if(numberOfPlayer.getSelectionModel().getSelectedIndex() == 2)
                numPlayers = 4;
        if(mode.getSelectionModel().getSelectedIndex() == 0)
            gamemode = 1;
            else if(mode.getSelectionModel().getSelectedIndex() == 1)
                gamemode = 2;
        String login = ClientSignatureWriter.clientSignObject(LoginWriter.writeLogin(nickname.getText(), gamemode, startgame, numPlayers), "@LOGN", nickname.getText()).toJSONString();
        System.out.println(login);
        GUIClientSide.getClientSide().sendMessage(login);
    }

}