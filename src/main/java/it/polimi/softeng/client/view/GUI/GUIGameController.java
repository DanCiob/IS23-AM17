package it.polimi.softeng.client.view.GUI;

import it.polimi.softeng.customExceptions.IllegalInsertException;
import it.polimi.softeng.model.Cell;
import it.polimi.softeng.model.GameBoard;
import it.polimi.softeng.model.PersonalCards;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.json.simple.JSONObject;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import static it.polimi.softeng.Constants.*;
import static it.polimi.softeng.JSONWriter.ClientSignatureWriter.clientSignObject;

public class GUIGameController implements Initializable{

    GUIClientSide guiClientSide;
    ArrayList<Cell> boardMoves = new ArrayList<>();
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
    ImageView commoncard2;

    @FXML
    Pane panePlayer3;

    @FXML
    Pane panePlayer4;

    public GUIGameController (GUIClientSide guiClientSide) {
        this.guiClientSide = guiClientSide;
    }

    public GUIGameController () {
    }

    public void initialize(URL url, ResourceBundle rb) {
        guiClientSide = GUIRegistry.guiList.get(GUIRegistry.numberOfGUI);
        guiClientSide.setGameController(this);
        GUIRegistry.numberOfGUI++;
        updateBoard();
        commoncard1.setImage(new Image("/images/CommonCard_" + guiClientSide.getCommonCard1() + ".jpg"));
        if(guiClientSide.getCommonCard2()!=null)
            commoncard2.setImage(new Image("/images/CommonCard_" + guiClientSide.getCommonCard2() + ".jpg"));
        if(getNumberPersonalCard() == -1){
            //TODO: send error
        }else{
            personalCard.setImage(new Image("/images/PC" + getNumberPersonalCard() + ".jpg"));
        }
        initializeShelfies();
    }

    protected void initializeShelfies(){
        if(guiClientSide.getNumOfPlayer() >= 3){
            panePlayer3.setVisible(true);
            panePlayer4.setVisible(true);
        }

        //TODO:set nicknames
    }

    protected int getNumberPersonalCard(){
        int i = 0;
        PersonalCards pc = guiClientSide.getPersonalCard();
        for(PersonalCards pctemp : PersonalCards.FillPersonalCardsBag()){
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
        boardMoves.add(cell);
    }

    /**
     * this method sends the message with the chosen tiles and column to the ClientSide
     */
    @FXML
    protected void sendBoardMoves(){
        String nickname = guiClientSide.getNickname();
        String action = "";
        for(Cell cell : boardMoves){
             action = action + "(" + cell.getRow() + "," + cell.getColumn() + ")" + ",";
        }
        action = action + columnShelfie;
        System.out.println(action);
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
            //TODO:RMI
        }

        resetAfterMove();
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
                    img = getImageInBoard(boardMoves.get(boardMoves.size()-1).getRow(), boardMoves.get(boardMoves.size()-1).getColumn()).getImage();
                    tileImageView.setImage(img);
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
        for (Cell cell : boardMoves) {
            for (Node node : boardGrid.getChildren()) {
                if ((GridPane.getColumnIndex(node) == cell.getColumn()) && (GridPane.getRowIndex(node) == cell.getRow())) {
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
        Boolean legalChoiceBoard, legalChoiceShelfie;
        legalChoiceBoard = checkLegalChoiceBoard();
        legalChoiceShelfie = checkLegalChoiceShelfie();
        if(!(legalChoiceBoard && legalChoiceShelfie))
            resetMoves();
        for (Cell cell : boardMoves) {
            for (Node node : boardGrid.getChildren()) {
                if ((GridPane.getColumnIndex(node) == cell.getColumn()) && (GridPane.getRowIndex(node) == cell.getRow())) {
                    ImageView i = (ImageView) node;
                    if (columnShelfie != -1){
                        if(legalChoiceBoard && legalChoiceShelfie){
                            i.setImage(null);
                        }else{
                            i.setOpacity(1);
                        }
                    }else{
                        i.setOpacity(1);
                    }
                }
            }
            if (columnShelfie != -1) {
                for (Node node : shelfie1.getChildren()) {
                    if (GridPane.getColumnIndex(node) == columnShelfie && GridPane.getRowIndex(node) <= firstFreeRowBeforeMoves) {
                        imageView = (ImageView) node;
                        if(!legalChoiceShelfie)
                            imageView.setImage(null);
                    }
                }
            }
        }
        boardMoves.clear();
        columnShelfie = -1;
        firstFreeRowBeforeMoves = -1;
        if(legalChoiceBoard && legalChoiceShelfie){
            boardGrid.setOpacity(0.3);
            guiClientSide.setYourTurn(false);
            //TODO: this and end game
        }
    }

    /**
     * this method is called by the CLI when it's the turn of this player
     */
    public void startTurn(){
        boardGrid.setOpacity(1);
        updateBoard();
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
                if(gameBoard.getBoard()[i][j] != null){
                    if(imageView.getImage() == null){
                        image = new Image("/images/Tile_" + gameBoard.getBoard()[i][j].getColor().colorLetter() + "1.png"); //TODO: change the object in the image
                        imageView.setImage(image);
                    }
                }else{
                    if(imageView != null && imageView.getImage() != null){
                        imageView.setImage(null);
                    }
                }
            }
        }
        if(!guiClientSide.isYourTurn)
            boardGrid.setOpacity(0.3);
        else
            boardGrid.setOpacity(1);
    }

    @FXML
    GridPane shelfie2;

   /* public void updateShelfies(){
        String nickname = (String) GUIClientSide.getCli().getShelfies().get(0); //TODO: aggiungere nickname sulla schermata gamescreen e far vedere nella shelfie giusta
        Shelfie shelfie = (Shelfie) GUIClientSide.getCli().getShelfies().get(1);
        ImageView imageView;
        Image image;

        for(int i=0;i<shelfieRows;i++){
            for(int j=0;j<shelfieColumns;j++){
                if(shelfie.getGrid()[i][j] != null){
                    image = new Image("/images/Tile_" + shelfie.getGrid()[i][j].getColor().colorLetter() + "1.png"); //TODO: change the object in the image
                    imageView = getImageViewInShelfie(nickname, i, j);
                    imageView.setImage(image);
                    shelfie2.add(imageView, j, i);
                }
            }
        }
    }*/

    public ImageView getImageViewInShelfie(String id, int row, int column){
        for (Node node : shelfie2.getChildren()) { //TODO:change shelfie2
            if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column){
                return (ImageView) node;
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

        for (Cell cell : boardMoves) {
            i = cell.getRow();
            j = cell.getColumn();

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
            cell2 = boardMoves.get(0);
            cell3 = boardMoves.get(1);
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
            cell2 = boardMoves.get(0);
            cell3 = boardMoves.get(1);
            cell4 = boardMoves.get(2);
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
        System.out.println("ChecklegalchoiceShelfie first = " + firstFreeRowBeforeMoves);
        return boardMoves.size() + i <= shelfieRows;
    }
}
