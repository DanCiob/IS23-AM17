package it.polimi.softeng.client.view.GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class ShelfieController implements Initializable {
    @FXML ImageView iv00;
    @FXML ImageView iv10;
    @FXML ImageView iv20;
    @FXML ImageView iv30;
    @FXML ImageView iv40;

    @FXML ImageView iv01;
    @FXML ImageView iv11;
    @FXML ImageView iv21;
    @FXML ImageView iv31;
    @FXML ImageView iv41;

    @FXML ImageView iv02;
    @FXML ImageView iv12;
    @FXML ImageView iv22;
    @FXML ImageView iv32;
    @FXML ImageView iv42;

    @FXML ImageView iv03;
    @FXML ImageView iv13;
    @FXML ImageView iv23;
    @FXML ImageView iv33;
    @FXML ImageView iv43;

    @FXML ImageView iv04;
    @FXML ImageView iv14;
    @FXML ImageView iv24;
    @FXML ImageView iv34;
    @FXML ImageView iv44;

    @FXML ImageView iv05;
    @FXML ImageView iv15;
    @FXML ImageView iv25;
    @FXML ImageView iv35;
    @FXML ImageView iv45;


    @FXML ImageView shelfieView;
    @FXML ImageView panelLeftView;
    @FXML ImageView panelRightView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image shelfieImage = new Image("/images/PC_back.jpg");
        shelfieView.setImage(shelfieImage);
        Image panelImage = new Image("/images/sfondo_parquet.jpg");
        panelRightView.setImage(panelImage);
        panelLeftView.setImage(panelImage);
        Image t1 = new Image("/images/Tile_G2.png");
        Image t2 = new Image("/images/Tile_B3.png");
        iv00.setImage(t1);
        iv03.setImage(t2);
        iv45.setImage(t2);

    }
}