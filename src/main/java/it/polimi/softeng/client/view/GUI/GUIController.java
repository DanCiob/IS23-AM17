package it.polimi.softeng.client.view.GUI;

import it.polimi.softeng.client.view.GUIInterface;
import it.polimi.softeng.client.view.UI;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



public class GUIController {
    @FXML
    private Label welcomeText;
    @FXML
    TextField nickname;

    @FXML
    TextField socketOrRmi;
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
    protected void onNewGame(){
        if(game.getValue() == "Create a new game")
            numberOfPlayer.show();
    }



    @FXML
    protected void onLoginButtonClick() {
        welcomeText.setText(nickname.getText());
        Image titleImage = new Image("/images/Title.png");
        this.TitleView = new ImageView();
        this.TitleView.setImage(titleImage);
        loginNotifier();
    }

    public void loginNotifier(){

    }
}