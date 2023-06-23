package it.polimi.softeng.client.view.GUI;

import it.polimi.softeng.JSONWriter.ClientSignatureWriter;
import it.polimi.softeng.JSONWriter.LoginWriter;
import it.polimi.softeng.client.view.MessageHandler;
import it.polimi.softeng.connectionProtocol.client.ClientSide;
import it.polimi.softeng.connectionProtocol.client.ClientSideRMI;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;


public class GUILoginController implements Initializable {
    GUIClientSide guiClientSide;
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
    protected void onNewGame() {
        if (game.getSelectionModel().getSelectedIndex() == 0) { //option create new game
            np.setText("Select the number of players: ");
            hboxNumPlayers.setVisible(true);
            hboxMode.setVisible(true);
        }
        if (game.getSelectionModel().getSelectedIndex() == 1) { //option join a new game
            np.setText("Select the number of players: ");
            hboxNumPlayers.setVisible(false);
            hboxMode.setVisible(false);
        }
    }

    @FXML
    protected void onSocketOrRmi(){
        if(socketOrRmi.getSelectionModel().getSelectedIndex() + 1 == 2){//case rmi
            serverPort.setEditable(false);
        }
    }

    @FXML
    Label waiting;

    /**
     * This method sends the login message to the server
     *
     * @param event which is when the user press the login button
     * @throws IOException called by load in switchToGame
     */
    @FXML
    protected void onLoginButtonClick(ActionEvent event) throws IOException, InterruptedException {
        System.out.println(nickname.getText());
        guiClientSide.setNickname(nickname.getText());
        System.out.println(guiClientSide.Nickname);
        System.out.println(guiClientSide.isOkNickname());
        if (!(guiClientSide.isOkNickname()) || guiClientSide.Nickname.equalsIgnoreCase("system")) {
            nickname.setText("");
        } else {
            if(serverPort.getText()==null)
                serverPort.setText("");
            if(serverIP.getText()==null)
                serverIP.setText("");
            int connectionMode = socketOrRmi.getSelectionModel().getSelectedIndex() + 1;
            if(localCheckBox.isSelected())
                connectionMode = 3;

            //save values in guiClientSide
            if(!serverPort.getText().equals(""))
                guiClientSide.setupGUI(connectionMode, serverIP.getText(),
                        Integer.parseInt(serverPort.getText()), game.getSelectionModel().getSelectedIndex() + 1,
                        numberOfPlayer.getSelectionModel().getSelectedIndex() + 2, mode.getSelectionModel().getSelectedIndex() + 1);
            else
                guiClientSide.setupGUI(connectionMode, serverIP.getText(),
                        1099, game.getSelectionModel().getSelectedIndex() + 1,
                        numberOfPlayer.getSelectionModel().getSelectedIndex() + 2, mode.getSelectionModel().getSelectedIndex() + 1);
            //TODO:change port 1099
            if(loginNotifier()){
                guiClientSide.setStage((Stage) ((Node) event.getSource()).getScene().getWindow());
                Service New_Service = new Service() {
                    @Override
                    protected Task createTask() {
                        return new Task() {
                            @Override
                            protected Object call() throws Exception {
                                Platform.runLater(() -> {
                                    try {
                                        switchToWait(event);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                });
                                return null;
                            }
                        };
                    }
                };
                New_Service.start();
            }


           /* while(!guiClientSide.GameIsOn){
                //TODO: call switchToGame from GUIClientSide-beginGame
            }*/

        }
    }

    Stage stage;

    /**
     * This method change the scene from login to waitingScreen
     *
     * @param event which is when the user press the login button
     * @throws IOException called by load
     */
    public void switchToWait(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(GUILoginController.class.getResource("/it.polimi.softeng.client.view.GUI/WaitingScreen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToGame() throws IOException{
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        final int initWidth = 912;
        final int initHeight = 601;
        Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
        double width = resolution.getWidth();
        double height = resolution.getHeight();
        Pane root = FXMLLoader.load(getClass().getResource("/it.polimi.softeng.client.view.GUI/gamescreen.fxml"));

        Scale scale = new Scale(width/initWidth, height/initHeight, 0, 0);
        scale.xProperty().bind(root.widthProperty().divide(initWidth));
        scale.yProperty().bind(root.heightProperty().divide(initHeight));
        root.getTransforms().add(scale);
        stage = guiClientSide.getStage();
        Scene scene = new Scene(root, initWidth, initHeight);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    /**
     * This method sends the login message to the server
     * @return false if the nickname is not unique or there is an error
     */
    public boolean loginNotifier() {
        int startgame;
        int gamemode = 0;
        int numPlayers = 0;

        if (game.getSelectionModel().getSelectedIndex() == 0)
            startgame = 1;
        else {
            startgame = 2;
        }

        if (numberOfPlayer.getSelectionModel().getSelectedIndex() == 0)
            numPlayers = 2;
        else if (numberOfPlayer.getSelectionModel().getSelectedIndex() == 1)
            numPlayers = 3;
        else if (numberOfPlayer.getSelectionModel().getSelectedIndex() == 2)
            numPlayers = 4;
        if (mode.getSelectionModel().getSelectedIndex() == 0)
            gamemode = 1;
        else if (mode.getSelectionModel().getSelectedIndex() == 1)
            gamemode = 2;
        //nickname uniqueness
        if(guiClientSide.isOkNickname())
            switch (guiClientSide.getConnectionMode()) {
                case 1 -> {
                    guiClientSide.messageHandler = new MessageHandler(guiClientSide);
                    guiClientSide.setNickname(nickname.getText());

                    //this is so that if you press enter it connects to the server specified in the json file
                    if(!serverIP.getText().equals("") && !serverPort.getText().equals("")){
                        guiClientSide.clientSide = new ClientSide(serverIP.getText(),Integer.parseInt(serverPort.getText()), guiClientSide.messageHandler);
                    }else {
                        guiClientSide.clientSide = new ClientSide(guiClientSide.messageHandler);
                    }

                    //guiClientSide.clientSide = new ClientSide(guiClientSide.messageHandler);

                    String login = ClientSignatureWriter.clientSignObject(LoginWriter.writeLogin(nickname.getText(), gamemode, startgame, numPlayers), "@LOGN", nickname.getText()).toJSONString();
                    System.out.println(login);
                    guiClientSide.getClientSide().sendMessage(login);
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        System.out.println("Server did not respond, try again");
                        guiClientSide.setOkNickname(false);
                        return false;
                    }
                }
                case 2 -> {
                    //guiClientSide.RemoteMethods = new ClientSideRMI(guiClientSide);
                    if(!serverIP.getText().equals("") ){
                        guiClientSide.RemoteMethods = new ClientSideRMI(serverIP.getText(), guiClientSide);
                    }else guiClientSide.RemoteMethods = new ClientSideRMI(guiClientSide);

                    String gameModeString = gamemode == 1 ? "e" : "n";
                    guiClientSide.RMIInvoker("@LOGN", gameModeString);
                }
                case 3 -> {
                    String gameModeString = gamemode == 1 ? "e" : "n";
                    guiClientSide.RemoteMethods = new ClientSideRMI(guiClientSide);
                    try {
                        boolean okNickname = guiClientSide.RemoteMethods.getStub().localLogin(nickname.getText(),numPlayers,gameModeString,guiClientSide.RemoteMethods.getPort());
                        if(!okNickname){
                            nickname.setText("");
                            return false;
                        }
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    //TODO: check oknickname
                }
            }
        return true;
    }

    @FXML
    ImageView loadingIcon;

    public void initialize(URL location, ResourceBundle resources) {
        guiClientSide = GUIRegistry.guiList.get(0);
        guiClientSide.setLoginController(this);
        GUIRegistry.numberOfGUI++;

        //animation loading circle
        RotateTransition rotate = new RotateTransition();
        rotate.setNode(loadingIcon);
        rotate.setDuration(Duration.millis(1500));
        rotate.setCycleCount(TranslateTransition.INDEFINITE);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setByAngle(360);
        rotate.play();
    }

    @FXML
    CheckBox localCheckBox;
    @FXML
    protected void local(){
        if(localCheckBox.isSelected()){
            serverPort.setText("");
            serverPort.setEditable(false);
            serverIP.setText("");
            serverIP.setEditable(false);
        }
        if(!localCheckBox.isSelected())
            serverPort.setEditable(true);
        serverIP.setEditable(true);
    }
}