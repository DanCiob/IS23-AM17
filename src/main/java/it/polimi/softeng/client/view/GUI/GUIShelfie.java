package it.polimi.softeng.client.view.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIShelfie extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)  throws IOException {
        Image icon = new Image("/images/Icon.png");
        primaryStage.getIcons().add(icon);
        FXMLLoader fxmlLoader = new FXMLLoader(GUIAppl.class.getResource("/it.polimi.softeng.client.view.GUI/Shelfie.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);

        primaryStage.setTitle("My Shelfie");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
