package it.polimi.softeng.client.view.GUI;

import it.polimi.softeng.customExceptions.IllegalInsertException;
import it.polimi.softeng.model.*;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import static it.polimi.softeng.Constants.*;
import static it.polimi.softeng.JSONWriter.ClientSignatureWriter.clientSignObject;

/**
 * Manage game screen for GUI
 */
public class GUIGameController implements Initializable{

    /**
     * Boolean which is set to true when the user's move is not legal
     */
    boolean moveError = false;

    /**
     * Boolean which is set to true when the move is confirmed (which is when boardEvent arrives)
     */
    boolean moveConfirmed = false;

    GUIClientSide guiClientSide;

    /**
     * This is used to have a cell associated with a boolean to know if it has already been inserted in the shelfie or not
     */
    public class Moves{
        Cell cell;
        boolean toInsert;

        public Moves(Cell cell, boolean toInsert) {
            this.cell = cell;
            this.toInsert = toInsert;
        }
    }

    /**
     * ArrayList with every cell that has been selected from the board
     */
    ArrayList<Moves> boardMoves = new ArrayList<>();

    /**
     * The shelfie column chosen by the user
     */
    int columnShelfie = -1;

    /**
     * First row of the chosen column which is free before adding the new tiles
     */
    int firstFreeRowBeforeMoves = -1;

    @FXML
    GridPane boardGrid;

    @FXML
    ImageView personalCard;

    @FXML
    ImageView commoncard1;

    @FXML
    ImageView commoncard2;

    @FXML
    Pane panePlayer3;

    @FXML
    Pane panePlayer4;

    @FXML
    ImageView player1badge1;

    @FXML
    ImageView player1badge2;

    @FXML
    ImageView player1badge3;

    @FXML
    Label nickname2;

    @FXML
    Label nickname3;

    @FXML
    Label nickname4;

    @FXML
    Button sendBoardMovesButton;

    @FXML
    Button resetButton;

    @FXML
    GridPane shelfie1;

    @FXML
    GridPane shelfie2;

    @FXML
    GridPane shelfie3;

    @FXML
    GridPane shelfie4;

    @FXML
    TextField chatMessage;

    @FXML
    TextField receiver;
    @FXML
    Button sendMessage;

    @FXML
    Label receivedMessage;

    public GUIGameController (GUIClientSide guiClientSide) {
        this.guiClientSide = guiClientSide;
    }

    public GUIGameController () {
    }

    @FXML
    public void setFirstPlayer(){
        player1badge1.setImage(new Image("/images/firstplayertoken.png"));
    }

    /**
     * It sets every item of the game screen
     * @param url
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param rb
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    public void initialize(URL url, ResourceBundle rb) {
        guiClientSide = GUIRegistry.guiList.get(0);
        guiClientSide.setGameController(this);
        GUIRegistry.numberOfGUI++;

        if(guiClientSide.isFirst)
            player1badge1.setImage(new Image("/images/firstplayertoken.png"));
        if(!guiClientSide.isYourTurn){
            boardGrid.setDisable(true);
            sendBoardMovesButton.setDisable(true);
            resetButton.setDisable(true);
        }
        updateBoard();
        if(guiClientSide.getCommonCard1() != null){
            commoncard1.setImage(new Image("/images/CommonCard_" + guiClientSide.getCommonCard1() + ".jpg"));
            if(guiClientSide.getCommonCard2()!=null) {
                commoncard2.setVisible(true);
                commoncard2.setImage(new Image("/images/CommonCard_" + guiClientSide.getCommonCard2() + ".jpg"));
            }
        }
        personalCard.setImage(new Image("/images/PC" + getNumberPersonalCard() + ".jpg"));
        initializeShelfies();
        updatePersonalShelfie();
        updateShelfies();
    }

    protected void initializeShelfies(){
        int i = 0;
        for (String p : guiClientSide.nicknameShelfie.keySet()) {
            if(!p.equals(guiClientSide.getNickname())){
                switch (i){
                    case 0 -> nickname2.setText(p);
                    case 1 -> {
                        nickname3.setText(p);
                        panePlayer3.setVisible(true);
                    }
                    case 2 -> {
                        nickname4.setText(p);
                        panePlayer4.setVisible(true);
                    }
                }
                i++;
            }
        }
    }

    /**
     *
     * @return the number of the personal card
     */
    protected int getNumberPersonalCard(){
        int i = 0;
        PersonalCards pc = guiClientSide.getPersonalCard();
        for(PersonalCards pctemp : PersonalCards.FillPersonalCardsBag()){
            if(pc==null)
                return 1;
            PersonalCards.ObjectiveCell[] pcCell = pc.getObjective();
            int j=0;
            boolean equal = true;
            for(PersonalCards.ObjectiveCell cell: pctemp.getObjective()){
                if(!(cell.getColor() == pcCell[j].getColor() && cell.getColumn() == pcCell[j].getColumn() && cell.getRow() == pcCell[j].getRow()))
                    equal = false;
                j++;
            }
            if(equal)
                return i+1;
            i++;
        }
        return 1;
    }

    /**
     * This method saves the clicked tile in boardMoves and set the opacity of that tile at 0.3 to make visible that it's selected
     * @param event which is when the user select a tile from the board
     */
    @FXML
    protected void removeTileFromBoard(javafx.scene.input.MouseEvent event){
        Node clickedNode = event.getPickResult().getIntersectedNode();

        for(Node node: boardGrid.getChildren()){
            if((GridPane.getColumnIndex(node) == GridPane.getColumnIndex(clickedNode))&&(GridPane.getRowIndex(node) == GridPane.getRowIndex(clickedNode))){
                ImageView i = (ImageView) node;
                if(i.getImage()!=null){
                    createBoardMoves(GridPane.getRowIndex(clickedNode), GridPane.getColumnIndex(clickedNode));
                    i.setOpacity(0.3);
                }
            }
        }
    }

    /**
     * This method creates the message with the chosen tiles and column, which will be sent to the clientSide
     * @param row of the board
     * @param column of the board
     */
    public void createBoardMoves(int row, int column){
        Cell cell = new Cell();
        cell.setRow(row);
        cell.setColumn(column);
        boardMoves.add(new Moves(cell, true));
    }

    /**
     * This method sends the message with the chosen tiles and column to the ClientSide
     */
    @FXML
    protected void sendBoardMoves() {
        String nickname = guiClientSide.getNickname();
        String action = "";
        for(Moves moves : boardMoves){
            action = action + "(" + moves.cell.getRow() + "," + moves.cell.getColumn() + ")" + ",";
        }
        action = action + columnShelfie;
        if(guiClientSide.getConnectionMode() == 1){ //socket
            JSONObject toBeSent=null;
            try {
                toBeSent = guiClientSide.actionToJSON("@GAME", action);
            } catch (IllegalInsertException e) {
                System.out.println("Error");
            }

            //Send message to server
            if (toBeSent != null) {
                guiClientSide.getClientSide().sendMessage(clientSignObject(toBeSent, "@GAME", nickname).toJSONString());
            }
        }else{
            guiClientSide.RMIInvoker("@GAME",action);
        }
    }

    /**
     *
     * @return true if there is at least a tile chosen from the board to insert in the shelfie, false if not
     */
    protected boolean boardMovesToInsert(){
        for(Moves m: boardMoves){
            if(m.toInsert)
                return true;
        }
        return false;
    }

    /**
     * This method insert the last selected tile from the board in the shelfie
     * @param event which is when the user select a column of the shelfie
     */
    @FXML
    protected void insertInShelfie(javafx.scene.input.MouseEvent event) {
        Node clickedNode = event.getPickResult().getIntersectedNode();
        int row = firstFree(clickedNode);
        Image img;
        if(boardMovesToInsert()){
            for (Node node : shelfie1.getChildren()) {
                if ((GridPane.getColumnIndex(node) == GridPane.getColumnIndex(clickedNode)) && row==GridPane.getRowIndex(node)) {
                    if(columnShelfie == -1 || GridPane.getColumnIndex(clickedNode) == columnShelfie){
                        if(columnShelfie == -1) { //it's the first tile and we also need to save the column to check if the other tiles are put in the same column
                            columnShelfie = GridPane.getColumnIndex(node);
                            firstFreeRowBeforeMoves = row;
                        }
                        ImageView tileImageView = (ImageView) node;
                        int i = 0;
                        while(i<boardMoves.size()){
                            if(boardMoves.get(i).toInsert) {
                                if (tileImageView.getImage() == null) {
                                    img = getImageInBoard(boardMoves.get(boardMoves.size() - 1).cell.getRow(), boardMoves.get(boardMoves.size() - 1).cell.getColumn()).getImage();
                                    tileImageView.setImage(img);
                                }
                                boardMoves.get(i).toInsert = false; //if the image is not null it means that there is already an image in that position, so we are in the first row from the top and the column is full
                            }
                            i++;
                        }
                    }
                }

            }
        }
    }

    /**
     *
     * @param row of the board
     * @param column of the board
     * @return the ImageView at the position (i,j) of the board
     */
    protected ImageView getImageInBoard(int row, int column) {
        for (Node node : boardGrid.getChildren()) {
            if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column){
                return (ImageView) node;
            }
        }
        return null;
    }

    /**
     * This method is called when the user press the "Reset your move" button and put the tiles again in the board
     */
    @FXML
    protected void resetMoves() {
        ImageView imageView;
        for (Moves moves : boardMoves) {
            for (Node node : boardGrid.getChildren()) {
                if ((GridPane.getColumnIndex(node) == moves.cell.getColumn()) && (GridPane.getRowIndex(node) == moves.cell.getRow())) {
                    ImageView i = (ImageView) node;
                    i.setOpacity(1);
                }
            }
            if (columnShelfie != -1) {
                for (Node node : shelfie1.getChildren()) {
                    if (GridPane.getColumnIndex(node) == columnShelfie && GridPane.getRowIndex(node) <= firstFreeRowBeforeMoves) {
                        imageView = (ImageView) node;
                        imageView.setImage(null);
                    }
                }
            }
        }
        boardMoves.clear();
        columnShelfie = -1;
        firstFreeRowBeforeMoves = -1;
    }

    /**
     * This method is called by sendBoardMoves when the user press the Send Your Moves button. It checks the move and if it is it removes the tile from the board
     */
    protected void resetAfterMove() {
        ImageView imageView;
        if(moveError)
            resetMoves();
        for (Moves moves : boardMoves) {
            for (Node node : boardGrid.getChildren()) {
                if ((GridPane.getColumnIndex(node) == moves.cell.getColumn()) && (GridPane.getRowIndex(node) == moves.cell.getRow())) {
                    ImageView i = (ImageView) node;
                    if (columnShelfie != -1){
                        if(moveConfirmed){
                            i.setImage(null);
                        }else if(moveError){
                            i.setOpacity(1);
                        }
                    }else{ //there is no column selected in the shelfie
                        i.setOpacity(1);
                    }
                }
            }
            if (columnShelfie != -1) {
                for (Node node : shelfie1.getChildren()) {
                    if (GridPane.getColumnIndex(node) == columnShelfie && GridPane.getRowIndex(node) <= firstFreeRowBeforeMoves) {
                        imageView = (ImageView) node;
                        if(moveError)
                            imageView.setImage(null);
                    }
                }
            }
        }
        boardMoves.clear();
        columnShelfie = -1;
        firstFreeRowBeforeMoves = -1;
        moveError = false;
        if(moveConfirmed){
            boardGrid.setOpacity(0.3);
            guiClientSide.setYourTurn(false);
            boardGrid.setDisable(true);
            sendBoardMovesButton.setDisable(true);
            resetButton.setDisable(true);
            moveConfirmed = false;
        }
        moveError = false;

    }

    /**
     * This method is called by the CLI when it's the turn of this player
     */
    public void startTurn(){
        boardMoves.clear();
        boardGrid.setOpacity(1);
        updateBoard();
        moveError = false;
        moveConfirmed = false;
    }


    /**
     *
     * @param clickedNode which is the selected tile
     * @return the first row of the shelfie which isn't full
     */
    private int firstFree(Node clickedNode){
        int row;
        for (int i=shelfieRows-1; i>=0; i--){
            for(Node node: shelfie1.getChildren()){
                if(GridPane.getColumnIndex(node) == GridPane.getColumnIndex(clickedNode) && i==GridPane.getRowIndex(node)){
                    ImageView tileImageView = (ImageView) node;
                    if (tileImageView.getImage() == null){
                        row = i;
                        return row;
                    }
                }
            }
        }
        return -1;
    }

    /**
     * This method is called at the beginning of the game and at the beginning of every turn, to update the gui board according to the game board
     */
    public void updateBoard(){
        GameBoard gameBoard = guiClientSide.getUserGameBoard();
        ImageView imageView;
        Image image;
        for(int i=0;i<boardRows;i++){
            for(int j=0;j<boardColumns;j++){
                imageView = getImageInBoard(i, j);
                if(gameBoard!=null){
                    if (gameBoard.getBoard()[i][j] != null) {
                        //create a number n depending on tile id, to have tiles of the same color with different pictures
                        int n = ((gameBoard.getBoard()[i][j].getId() / picturesForEachTile) % picturesForEachTile) + 1;
                        image = new Image("/images/Tile_" + gameBoard.getBoard()[i][j].getColor().colorLetter() + n + ".png");
                        imageView.setOpacity(1);
                        imageView.setImage(image);
                    } else {
                        if (imageView != null && imageView.getImage() != null) {
                            imageView.setImage(null);
                        }
                    }
                }
            }
        }
        if(!guiClientSide.isYourTurn)
            boardGrid.setOpacity(0.3);
        else{
            boardGrid.setOpacity(1);
            boardGrid.setDisable(false);
            sendBoardMovesButton.setDisable(false);
            resetButton.setDisable(false);
        }
    }

    /**
     * It updates the shelfie of the user
     */
    @FXML
    protected void updatePersonalShelfie(){
        Tile[][] s;
        int n;
        for(int i=0;i<shelfieRows;i++) {
            for (int j = 0; j < shelfieColumns; j++) {
                if(guiClientSide.getUserShelfie()!=null){
                    s = guiClientSide.getUserShelfie().getGrid();
                    ImageView imageView = getImageViewInShelfie(Integer.toString(1), shelfieRows - 1 - i, j);
                    if(imageView==null && s[i][j]!=null){
                        n = ((s[i][j].getId() / picturesForEachTile) % picturesForEachTile) + 1;
                        imageView = new ImageView(new Image("/images/Tile_" + s[i][j].getColor().colorLetter() + n + ".png"));
                    }
                    if(imageView!=null && s[i][j]!=null){
                        n = ((s[i][j].getId() / picturesForEachTile) % picturesForEachTile) + 1;
                        imageView.setImage(new Image("/images/Tile_" + s[i][j].getColor().colorLetter() + n + ".png"));
                    }
                }
            }
        }
    }

    /**
     * It updates the shelfies of the other players
     */
    @FXML
    public void updateShelfies(){
        ImageView imageView;
        Image image;
        Tile[][] grid;

        for(String s: guiClientSide.nicknameShelfie.keySet()){
            grid = guiClientSide.nicknameShelfie.get(s).getGrid();
            for(int i=0;i<shelfieRows;i++){
                for(int j=0;j<shelfieColumns;j++){
                    if(grid[i][j] != null){
                        int n = ((grid[i][j].getId() / picturesForEachTile) % picturesForEachTile) + 1;
                        image = new Image("/images/Tile_" + grid[i][j].getColor().colorLetter() + n + ".png");
                        imageView = getImageViewInShelfie(s, shelfieRows - 1 - i, j);
                        if(imageView!=null)
                            imageView.setImage(image);
                    }
                }
            }
        }
    }

    /**
     *
     * @param nickname of the player
     * @param row of the shelfie
     * @param column of the shelfie
     * @return the image view of the tile at position (row, column) of the shelfie of the player specified in nickname
     */
    public ImageView getImageViewInShelfie(String nickname, int row, int column){
        if(Objects.equals(nickname, Integer.toString(1))){
            for (Node node : shelfie1.getChildren()) {
                if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column){
                    return (ImageView) node;
                }
            }
        }
        if(Objects.equals(nickname, nickname2.getText())){
            for (Node node : shelfie2.getChildren()) {
                if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column){
                    return (ImageView) node;
                }
            }
        }
        if(Objects.equals(nickname, nickname3.getText())) {
            for (Node node : shelfie3.getChildren()) {
                if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column){
                    return (ImageView) node;
                }
            }
        }
        if(Objects.equals(nickname, nickname4.getText())) {
            for (Node node : shelfie4.getChildren()) {
                if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column){
                    return (ImageView) node;
                }
            }
        }
        return null;
    }

    /**
     * It sets the stage to the End game screen
     */
    public void switchToEndGame(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/it.polimi.softeng.client.view.GUI/endGame.fxml"));
            Scene scene = new Scene(root);
            guiClientSide.getStage().setScene(scene);
            guiClientSide.getStage().show();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }


    public void setMoveError(boolean moveError) {
        this.moveError = moveError;
    }

    public void setMoveConfirmed(boolean moveConfirmed) {
        this.moveConfirmed = moveConfirmed;
    }


    @FXML
    public void onSendMessage() throws IllegalInsertException {
        String message = chatMessage.getText();
        String action = "'" + receiver.getText() + "' " + message;


        if(guiClientSide.getConnectionMode() == 1) { //socket
            JSONObject toBeSent = guiClientSide.actionToJSON("@CHAT", action);
            if (toBeSent != null) {
                guiClientSide.getClientSide().sendMessage(clientSignObject(toBeSent, "@CHAT", guiClientSide.getNickname()).toJSONString());
                chatMessage.setText("");
                receiver.setText("");
            }
        }else{//RMI
            guiClientSide.RMIInvoker("@CHAT", action);
            chatMessage.setText("");
            receiver.setText("");
        }
    }

    @FXML
    public void setChatMessage(String message){
        Service New_Service = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Object call() {
                        Platform.runLater(() -> {
                            receivedMessage.setText(message);
                        });
                        return null;
                    }
                };
            }
        };
        New_Service.start();
    }
}