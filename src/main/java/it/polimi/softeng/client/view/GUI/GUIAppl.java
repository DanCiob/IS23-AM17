package it.polimi.softeng.client.view.GUI;

import it.polimi.softeng.client.view.CLI.CLI;
import it.polimi.softeng.client.view.MessageHandler;
import it.polimi.softeng.connectionProtocol.client.ClientSide;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

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
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);

        guiClientSide = new GUIClientSide();
        GUIRegistry.guiList.add(guiClientSide);
        primaryStage.setTitle("My Shelfie");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
