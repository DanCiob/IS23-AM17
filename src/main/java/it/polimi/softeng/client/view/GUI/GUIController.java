package it.polimi.softeng.client.view.GUI;

import it.polimi.softeng.JSONWriter.ClientSignatureWriter;
import it.polimi.softeng.JSONWriter.LoginWriter;
import it.polimi.softeng.client.view.GUIInterface;
import it.polimi.softeng.client.view.MessageHandler;
import it.polimi.softeng.client.view.CLI.CLI;
import it.polimi.softeng.client.view.UI;
import it.polimi.softeng.connectionProtocol.ClientSide;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;


public class GUIController{
    CLI cli = new CLI();
    @FXML
    private Label welcomeText;

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
    TextField newGame;

    @FXML
    ImageView TitleView;



    @FXML
    protected void TitleView(){
        Image titleImage = new Image("/images/Title.png");
        this.TitleView = new ImageView();
        this.TitleView.setImage(titleImage);
    }

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



    @FXML
    protected void onLoginButtonClick(ActionEvent event) throws IOException{
        welcomeText.setText(nickname.getText());
        loginNotifier();
        switchToGame(event);
    }

    public void switchToGame(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/it.polimi.softeng.client.view.GUI/gamescreen.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void loginNotifier(){
        int startgame;
        int gamemode = 0;
        int numPlayers = 0;
        cli.setConnectionMode(socketOrRmi.getSelectionModel().getSelectedIndex() + 1);
        System.out.println(socketOrRmi.getSelectionModel().getSelectedIndex() + 1);
        cli.setServerAddress(serverIP.getText());
        int port = Integer.parseInt(serverPort.getText());
        cli.setPort(port);
        System.out.println(port);
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
        MessageHandler messageHandler = new MessageHandler(this);
        ClientSide clientSide = new ClientSide(messageHandler);
        clientSide.sendMessage(login);

    }
}