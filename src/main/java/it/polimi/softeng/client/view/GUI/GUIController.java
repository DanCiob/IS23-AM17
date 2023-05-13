package it.polimi.softeng.client.view.GUI;

import com.sun.javafx.scene.control.IntegerField;
import it.polimi.softeng.JSONWriter.ClientSignatureWriter;
import it.polimi.softeng.JSONWriter.LoginWriter;
import it.polimi.softeng.client.view.GUIInterface;
import it.polimi.softeng.client.view.MessageHandler;
import it.polimi.softeng.client.view.UI;
import it.polimi.softeng.client.view.CLI.CLI;
import it.polimi.softeng.connectionProtocol.ClientSide;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.w3c.dom.Text;


public class GUIController implements GUIInterface {
    CLI cli = new CLI();
    @FXML
    private Label welcomeText;

    @FXML
    private Label np;
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
    protected void onNewGame(){
        if(game.getSelectionModel().getSelectedIndex() == 0){
            //np.setText("Select the number of players: ");
            numberOfPlayer.setVisible(true);
        }

    }



    @FXML
    protected void onLoginButtonClick() {
        welcomeText.setText(nickname.getText());
        Image titleImage = new Image("/images/Title.png");
        this.TitleView = new ImageView();
        this.TitleView.setImage(titleImage);
        loginNotifier();
    }


    @Override
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

        //cli.setPort(serverPort.getText().);
        /*cli.setPort(serverPort.getValue());
        System.out.println(serverPort.getValue());*/

    }
}