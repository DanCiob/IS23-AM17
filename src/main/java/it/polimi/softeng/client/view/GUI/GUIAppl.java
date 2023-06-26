package it.polimi.softeng.client.view.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Used to start GUI
 */
public class GUIAppl extends Application {
    GUIClientSide guiClientSide;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Image icon = new Image("/images/Icon.png");
        primaryStage.getIcons().add(icon);
        FXMLLoader fxmlLoader = new FXMLLoader(GUIAppl.class.getResource("/it.polimi.softeng.client.view.GUI/login.fxml"));
        guiClientSide = new GUIClientSide();
        GUIRegistry.guiList.add(guiClientSide);
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);

        primaryStage.setTitle("My Shelfie");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    @Override
    public void stop(){
        System.exit(0);
    }
}