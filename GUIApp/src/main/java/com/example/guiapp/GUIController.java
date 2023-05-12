package com.example.guiapp;

import javafx.fxml.FXML;
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
    public void initTitle(){
        Image titleImage = new Image("/Title.png");
        this.TitleView = new ImageView();
        this.TitleView.setImage(titleImage);
    }


    @FXML
    protected void onLoginButtonClick() {
        welcomeText.setText(nickname.getText());
        Image titleImage = new Image("/Title.png");
        this.TitleView = new ImageView();
        this.TitleView.setImage(titleImage);
    }

}