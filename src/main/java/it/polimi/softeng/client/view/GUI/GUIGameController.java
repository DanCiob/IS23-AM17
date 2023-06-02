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
import java.util.concurrent.TimeUnit;

import static it.polimi.softeng.Constants.*;
import static it.polimi.softeng.JSONWriter.ClientSignatureWriter.clientSignObject;

public class GUIGameController implements Initializable{

    boolean moveError = false;
    boolean moveConfirmed = false;

    GUIClientSide guiClientSide;

    public class Moves{
        Cell cell;
        boolean toInsert;

        public Moves(Cell cell, boolean toInsert) {
            this.cell = cell;
            this.toInsert = toInsert;
        }
    }
    ArrayList<Moves> boardMoves = new ArrayList<>();
    int columnShelfie = -1;

    int firstFreeRowBeforeMoves = -1;

    @FXML GridPane shelfieGridPane;

    @FXML
    GridPane boardGrid;

    @FXML
    ImageView personalCard;

    @FXML
    ImageView commoncard1;

    @FXML
    ImageView badgeCommonCard1;

    @FXML
    ImageView commoncard2;

    @FXML
    ImageView badgeCommonCard2;

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

    public GUIGameController (GUIClientSide guiClientSide) {
        this.guiClientSide = guiClientSide;
    }

    public GUIGameController () {
    }

    public void initialize(URL url, ResourceBundle rb) {
        guiClientSide = GUIRegistry.guiList.get(0);
        guiClientSide.setGameController(this);
        GUIRegistry.numberOfGUI++;
        if(guiClientSide.isYourTurn){
            player1badge1.setImage(new Image("/images/firstplayertoken.png"));
        }else{
            boardGrid.setDisable(true);
        }
        updateBoard();
        if(guiClientSide.getCommonCard1() == null) {
            //todo: send error
        }else{
            //System.out.println("/images/CommonCard_" + guiClientSide.getCommonCard1() + ".jpg");
            commoncard1.setImage(new Image("/images/CommonCard_" + guiClientSide.getCommonCard1() + ".jpg"));
            if(guiClientSide.getCommonCard2()!=null) {
                commoncard2.setVisible(true);
                badgeCommonCard2.setVisible(true);
                commoncard2.setImage(new Image("/images/CommonCard_" + guiClientSide.getCommonCard2() + ".jpg"));
            }
        }
        if(getNumberPersonalCard() == -1){
            //TODO: send error
        }else{
            personalCard.setImage(new Image("/images/PC" + getNumberPersonalCard() + ".jpg"));
        }
        initializeShelfies();
    }

    protected void initializeShelfies(){
        int i = 0;
        System.out.println("Current player " + guiClientSide.getNickname());
        for (String p : guiClientSide.nicknameShelfie.keySet()) {
            System.out.println(p);
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

    protected int getNumberPersonalCard(){
        int i = 0;
        PersonalCards pc = guiClientSide.getPersonalCard();
        for(PersonalCards pctemp : PersonalCards.FillPersonalCardsBag()){
            if(pc==null)
                return -1;
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
        return -1;
    }

    /**
     * this method saves the clicked tile in boardMoves and set the opacity of that tile at 0.3 to make visible that it's selected
     * @param event which is when the user select a tile from the board
     */
    @FXML
    protected void removeTileFromBoard(javafx.scene.input.MouseEvent event){
        Node clickedNode = event.getPickResult().getIntersectedNode();

        for(Node node: boardGrid.getChildren()){
            if((GridPane.getColumnIndex(node) == GridPane.getColumnIndex(clickedNode))&&(GridPane.getRowIndex(node) == GridPane.getRowIndex(clickedNode))){
                createBoardMoves(GridPane.getRowIndex(clickedNode), GridPane.getColumnIndex(clickedNode));
                ImageView i = (ImageView) node;
                i.setOpacity(0.3);
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
     * this method sends the message with the chosen tiles and column to the ClientSide
     */
    @FXML
    protected void sendBoardMoves() throws InterruptedException {
        String nickname = guiClientSide.getNickname();
        String action = "";
        for(Moves moves : boardMoves){
             action = action + "(" + moves.cell.getRow() + "," + moves.cell.getColumn() + ")" + ",";
        }
        action = action + columnShelfie;
       // System.out.println(action);
        if(guiClientSide.getConnectionMode() == 1){ //socket
            JSONObject toBeSent = null;
            try {
                toBeSent = guiClientSide.actionToJSON("@GAME", action);
            } catch (IllegalInsertException e) {
                throw new RuntimeException(e);
            }

            //Send message to server
            if (toBeSent != null)
                guiClientSide.getClientSide().sendMessage(clientSignObject(toBeSent, "@GAME", nickname).toJSONString());
        }else{
            guiClientSide.RMIInvoker("@GAME",action);
        }
        //if(moveConfirmed){
            //resetAfterMove();
            //updatePersonalShelfie();
        //}
    }

    @FXML
    GridPane shelfie1;

    /**
     * this method insert the last selected tile from the board in the shelfie
     * @param event which is when the user select a column of the shelfie
     */
    @FXML
    protected void insertInShelfie(javafx.scene.input.MouseEvent event) {
        Node clickedNode = event.getPickResult().getIntersectedNode();
        int row = firstFree(clickedNode);
        Image img;
        //TODO: insert all the selected tiles (more than 1)?
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
                            boardMoves.get(i).toInsert = false;
                            img = getImageInBoard(boardMoves.get(boardMoves.size() - 1).cell.getRow(), boardMoves.get(boardMoves.size() - 1).cell.getColumn()).getImage();
                            tileImageView.setImage(img);
                        }
                        i++;
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

    @FXML
    Button resetButton;

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
                    if (GridPane.getColumnIndex(node) == columnShelfie && GridPane.getRowIndex(node) <= firstFreeRowBeforeMoves) { //TODO: controlla se è giusto il minore
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
     * this method is called by sendBoardMoves when the user press the Send Your Moves button. It checks the move and if it is it removes the tile from the board
     */
    protected void resetAfterMove() {
        ImageView imageView;
        if(moveError && moveConfirmed)
            System.out.println("Errore! entrambi true");
        if(!moveError && !moveConfirmed)
            System.out.println("Errore! entrambi false");
        if(moveConfirmed)
            System.out.println("Move confirmed is true");
        Boolean legalChoiceBoard, legalChoiceShelfie;
        legalChoiceBoard = checkLegalChoiceBoard();
        legalChoiceShelfie = checkLegalChoiceShelfie();
        if(!(legalChoiceBoard && legalChoiceShelfie))
            resetMoves();
        //if(moveError)
        //    resetMoves();
        for (Moves moves : boardMoves) {
            for (Node node : boardGrid.getChildren()) {
                if ((GridPane.getColumnIndex(node) == moves.cell.getColumn()) && (GridPane.getRowIndex(node) == moves.cell.getRow())) {
                    ImageView i = (ImageView) node;
                    if (columnShelfie != -1){
                        if(legalChoiceBoard && legalChoiceShelfie){//if(moveConfirmed){
                            i.setImage(null);
                        }else { //if(moveError)
                            i.setOpacity(1);
                        }//TODO: else? it's not arrived error/board update
                    }else{ //there is no column selected in the shelfie
                        i.setOpacity(1);
                    }
                }
            }
            if (columnShelfie != -1) {
                for (Node node : shelfie1.getChildren()) {
                    if (GridPane.getColumnIndex(node) == columnShelfie && GridPane.getRowIndex(node) <= firstFreeRowBeforeMoves) {
                        imageView = (ImageView) node;
                        if(!legalChoiceShelfie) //if(moveError)
                            imageView.setImage(null);
                    }
                }
            }
        }
        boardMoves.clear();
        columnShelfie = -1;
        firstFreeRowBeforeMoves = -1;
        if(legalChoiceBoard && legalChoiceShelfie){//if(moveConfirmed){
            boardGrid.setOpacity(0.3);
            guiClientSide.setYourTurn(false);
            boardGrid.setDisable(true);
            //TODO: this and end game
        }
    }

    /**
     * this method is called by the CLI when it's the turn of this player
     */
    public void startTurn(){
        boardGrid.setOpacity(1);
        updateBoard();
        moveError = false;
        moveConfirmed = false;
       /* Service New_Service = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Object call() throws Exception {
                        Platform.runLater(() -> {
                            //updateShelfies(guiClientSide.nicknameShelfie.);
                        });
                        return null;
                    }
                };
            }
        };
        New_Service.start();*/
    }


    /**
     *
     * @param clickedNode which is the selected tile
     * @return the first row of the shelfie which isn't full
     */
    private int firstFree(Node clickedNode){
        int row =0;
        for (int i=0; i < shelfieRows; i++){
            for(Node node: shelfie1.getChildren()){
                if(GridPane.getColumnIndex(node) == GridPane.getColumnIndex(clickedNode) && i==GridPane.getRowIndex(node)){
                    ImageView tileImageView = (ImageView) node;
                    if (tileImageView.getImage() == null){
                        if(i > row)
                             row = GridPane.getRowIndex(node);
                    }
                }
            }
        }
        return row;
        //TODO: if the column is full
    }

    /**
     * this method is called at the beginning of the game and at the beginning of every turn, to update the gui board according to the game board
     */
    public void updateBoard(){
        GameBoard gameBoard = guiClientSide.getUserGameBoard();
        ImageView imageView;
        Image image;
        for(int i=0;i<boardRows;i++){
            for(int j=0;j<boardColumns;j++){
                imageView = getImageInBoard(i, j);
                if(gameBoard==null) {
                    //TODO:send error
                }else{
                    if (gameBoard.getBoard()[i][j] != null) {
                        if (imageView.getImage() == null) {
                            //create a number n depending on tile id, to have tiles of the same color with different pictures
                            int n = ((gameBoard.getBoard()[i][j].getId() / picturesForEachTile) % picturesForEachTile) + 1;
                            image = new Image("/images/Tile_" + gameBoard.getBoard()[i][j].getColor().colorLetter() + n + ".png");
                            imageView.setOpacity(1);
                            imageView.setImage(image);
                        }
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
        }
    }

    @FXML
    public void updateCommonCardBadges(int i){//TODO: correct this
        System.out.println("Update common card badges");
        switch (i){
            case 1 -> {
                if(badgeCommonCard1.getImage().getUrl() == "/images/BadgeScore8.jpg") {
                    System.out.println("Entra in if badgescore8 commoncard1");
                    badgeCommonCard1.setImage(new Image("/images/BadgeScore6.jpg"));
                }if(Objects.equals(badgeCommonCard1.getImage().getUrl(), "/images/BadgeScore6.jpg"))
                    badgeCommonCard1.setImage(new Image("/images/BadgeScore4.jpg"));
                if(Objects.equals(badgeCommonCard1.getImage().getUrl(), "/images/BadgeScore4.jpg"))
                    badgeCommonCard1.setImage(new Image("/images/BadgeScore2.jpg"));
                if(Objects.equals(badgeCommonCard1.getImage().getUrl(), "/images/BadgeScore2.jpg"))
                    badgeCommonCard1.setVisible(false);
            }
            case 2 -> {
                if (badgeCommonCard2.getImage().getUrl() == "/images/BadgeScore8.jpg"){
                    System.out.println("Entra in if badgescore8 commoncard2");
                    badgeCommonCard2.setImage(new Image("/images/BadgeScore6.jpg"));
            }if(Objects.equals(badgeCommonCard2.getImage().getUrl(), "/images/BadgeScore6.jpg"))
                    badgeCommonCard2.setImage(new Image("/images/BadgeScore4.jpg"));
                if(Objects.equals(badgeCommonCard2.getImage().getUrl(), "/images/BadgeScore4.jpg"))
                    badgeCommonCard2.setImage(new Image("/images/BadgeScore2.jpg"));
                if(Objects.equals(badgeCommonCard2.getImage().getUrl(), "/images/BadgeScore2.jpg"))
                    badgeCommonCard2.setVisible(false);
            }
        }
    }

    @FXML
    protected void updatePersonalShelfie(){
        for(int i=0;i<shelfieRows;i++) {
            for (int j = 0; j < shelfieColumns; j++) {
                ImageView imageView = getImageViewInShelfie(Integer.toString(1), shelfieRows - 1 - i, j);
                if(imageView!=null && guiClientSide.getUserShelfie().getGrid()[i][j]!=null)
                    imageView.setImage(new Image("/images/Tile_" + guiClientSide.getUserShelfie().getGrid()[i][j].getColor().colorLetter() + "1.png"));
            }
        }
    }

    @FXML
    GridPane shelfie2;

    @FXML
    GridPane shelfie3;

    @FXML
    GridPane shelfie4;


    @FXML
    public void updateShelfies(){
        ImageView imageView;
        Image image;

        for(String s: guiClientSide.nicknameShelfie.keySet()){
            if(!s.equals(guiClientSide.getNickname())){
                for(int i=0;i<shelfieRows;i++){
                    for(int j=0;j<shelfieColumns;j++){
                        if(guiClientSide.nicknameShelfie.get(s).getGrid()[i][j] != null){
                            image = new Image("/images/Tile_" + guiClientSide.nicknameShelfie.get(s).getGrid()[i][j].getColor().colorLetter() + "1.png"); //TODO: change the object in the image
                            imageView = getImageViewInShelfie(s, shelfieRows - 1 - i, j);
                            if(imageView!=null)
                                imageView.setImage(image);
                            //shelfie2.add(imageView, j, shelfieRows-i);
                        }
                    }
                }
            }
        }
    }

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
     * @return true if the move is legal, false otherwise
     */
    public boolean checkLegalChoiceBoard() {
        int i, j;
        Cell cell2, cell3, cell4;
        if(boardMoves.isEmpty())
            return false;

        for (Moves moves : boardMoves) {
            i = moves.cell.getRow();
            j = moves.cell.getColumn();

            if((i!=0 && i!=8 && j!=0 && j!=8)) {
                if((getImageInBoard(i+1, j) != null) && (getImageInBoard(i, j+1) != null) && (getImageInBoard(i-1, j) != null) && (getImageInBoard(i, j-1) != null))
                    if((getImageInBoard(i+1, j).getImage() != null) && (getImageInBoard(i, j+1).getImage() != null) && (getImageInBoard(i-1, j).getImage() != null) && (getImageInBoard(i, j-1).getImage() != null))
                        return false; /*false if the tile is completely surrounded by other tiles (up, down, left and right)*/
            }

        }
        if(boardMoves.size() == 1){
            return true; //if the move takes one single tile, we don't need to verify it is aligned with others
        }
        if(boardMoves.size() == 2){
            cell2 = boardMoves.get(0).cell;
            cell3 = boardMoves.get(1).cell;
            if(cell2.getRow() == cell3.getRow()) {
                if(cell2.getColumn() == cell3.getColumn() + 1)
                    return true;
                if(cell2.getColumn() == cell3.getColumn() - 1)
                    return true;
            }
            if(cell2.getColumn() == cell3.getColumn()){
                if(cell2.getRow() == cell3.getRow() + 1)
                    return true;
                if(cell2.getRow() == cell3.getRow() - 1)
                    return true;

            }
        }
        if(boardMoves.size() == 3){
            cell2 = boardMoves.get(0).cell;
            cell3 = boardMoves.get(1).cell;
            cell4 = boardMoves.get(2).cell;
            if(cell2.getRow() == cell3.getRow() && cell2.getRow() == cell4.getRow()){
                int c2 = cell2.getColumn(), c3 = cell3.getColumn(), c4 = cell4.getColumn();
                if(c2==c3-1 && c3==c4-1)
                    return true;
                if(c2==c4-1 && c4==c3-1)
                    return true;
                if(c3==c2-1 && c2==c4-1)
                    return true;
                if(c3==c4-1 && c4==c2-1)
                    return true;
                if(c4==c2-1 && c2==c3-1)
                    return true;
                if(c4==c3-1 && c3==c2-1)
                    return true;
            }
            if(cell2.getColumn() == cell3.getColumn() && cell2.getColumn() == cell4.getColumn()){
                int r2 = cell2.getRow(), r3 = cell3.getRow(), r4 = cell4.getRow();
                if(r2==r3-1 && r3==r4-1)
                    return true;
                if(r2==r4-1 && r4==r3-1)
                    return true;
                if(r3==r2-1 && r2==r4-1)
                    return true;
                if(r3==r4-1 && r4==r2-1)
                    return true;
                if(r4==r2-1 && r2==r3-1)
                    return true;
                if(r4==r3-1 && r3==r2-1)
                    return true;
            }
        }

        return false;
    }

    /**
     * @return true if the move is legal, false otherwise
     */
    public boolean checkLegalChoiceShelfie(){
        int i = shelfieRows - firstFreeRowBeforeMoves;
        //System.out.println("ChecklegalchoiceShelfie first = " + firstFreeRowBeforeMoves);
        return boardMoves.size() + i <= shelfieRows;
    }

    @FXML
    public void giveBadge(int s){
        if(player1badge1.getImage() == null){
            player1badge1.setImage(new Image("/images/BadgeScore" + s + ".jpg"));
        }else if(player1badge2.getImage() == null) {
            player1badge2.setImage(new Image("/images/BadgeScore" + s + ".jpg"));

        } else if (player1badge3.getImage() == null) {
            player1badge3.setImage(new Image("/images/BadgeScore" + s + ".jpg"));
        }
    }

    public void switchToEndGame(){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/it.polimi.softeng.client.view.GUI/endGame.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        guiClientSide.getStage().setScene(scene);
        guiClientSide.getStage().show();
    }


    public void setMoveError(boolean moveError) {
        this.moveError = moveError;
    }

    public void setMoveConfirmed(boolean moveConfirmed) {
        this.moveConfirmed = moveConfirmed;
    }
}
