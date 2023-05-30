package it.polimi.softeng.client.view.GUI;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GUIWaitingController implements Initializable {

    public void switchToGame() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/it.polimi.softeng.client.view.GUI/gamescreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       // GUIRegistry.guiList.get(GUIRegistry.numberOfGUI).setGuiWaitingController(this);
       // GUIRegistry.numberOfGUI++;
    }
}
