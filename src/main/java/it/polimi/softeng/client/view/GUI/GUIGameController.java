package it.polimi.softeng.client.view.GUI;

import it.polimi.softeng.client.view.CLI.CLI;
import it.polimi.softeng.client.view.MessageHandler;
import it.polimi.softeng.connectionProtocol.client.ClientSide;
import it.polimi.softeng.customExceptions.IllegalInsertException;
import it.polimi.softeng.model.Cell;
import it.polimi.softeng.model.GameBoard;
import it.polimi.softeng.model.Tile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.GridPane;
import org.json.simple.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static it.polimi.softeng.Constants.*;
import static it.polimi.softeng.JSONWriter.ClientSignatureWriter.clientSignObject;

public class GUIGameController implements Initializable{

    ArrayList<Cell> boardMoves = new ArrayList<>();
    int columnShelfie = -1;
    int firstFreeRowBeforeMoves = -1;

    @FXML GridPane shelfieGridPane;


    @FXML
    GridPane boardGrid;

    public void initialize(URL url, ResourceBundle rb) {
        GUIClientSide.getCli().setGuiGameController(this);
        updateBoard();
    }


    @FXML
    protected void removeTileFromBoard(javafx.scene.input.MouseEvent event){
        Node clickedNode = event.getPickResult().getIntersectedNode();

        for(Node node: boardGrid.getChildren()){
            if((GridPane.getColumnIndex(node) == GridPane.getColumnIndex(clickedNode))&&(GridPane.getRowIndex(node) == GridPane.getRowIndex(clickedNode))){
                createBoardMoves(GridPane.getRowIndex(clickedNode), GridPane.getColumnIndex(clickedNode));
                ImageView i = (ImageView) node;
                i.setOpacity(0.3);
                //i.setImage(null);
            }
        }
    }

    /**
     * This method creates the message with the chosen tiles and column, which will be sent to the clientSide
     * @param row
     * @param column
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
        String nickname = GUIClientSide.getCli().getNickname();
        String action = "";
        for(Cell cell : boardMoves){
             action = action + "(" + cell.getRow() + "," + cell.getColumn() + ")" + ",";
        }
        action = action + columnShelfie;
        System.out.println(action);
        if(GUIClientSide.getCli().getConnectionMode() == 1){ //socket
            JSONObject toBeSent = GUIClientSide.getCli().actionToJSON("@GAME", action);

            //Send message to server
            if (toBeSent != null)
                GUIClientSide.getClientSide().sendMessage(clientSignObject(toBeSent, "@GAME", nickname).toJSONString());
        }else{//RMI

        }

        //TODO: add the checklegalmoves control, if it's right take off the images from the board and if it's not take off the images from the shelfie

        resetMoves1();
    }

    @FXML
    GridPane shelfie1;
    @FXML
    protected void insertInShelfie(javafx.scene.input.MouseEvent event) {
        Node clickedNode = event.getPickResult().getIntersectedNode();
        int row = firstFree(clickedNode);
        Image img;
        for (Node node : shelfie1.getChildren()) {
            if ((GridPane.getColumnIndex(node) == GridPane.getColumnIndex(clickedNode)) && row==GridPane.getRowIndex(node)) {
                if((columnShelfie!=-1 && GridPane.getColumnIndex(clickedNode) == columnShelfie)||columnShelfie==-1){
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

    protected ImageView getImageInBoard(int row, int column) {
        for (Node node : boardGrid.getChildren()) {
            if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column){
                ImageView imageView =(ImageView) node;
                return imageView;
            }
        }
        return null;
    }

    @FXML
    Button resetButton;

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

    protected void resetMoves1() {
        ImageView imageView;
        Boolean legalChoiceBoard, legalChoiceShelfie;
        legalChoiceBoard = checkLegalChoiceBoard();
        legalChoiceShelfie = checkLegalChoiceShelfie();
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
            GUIClientSide.getCli().setYourTurn(false);
            /*while(!GUIClientSide.getCli().isYourTurn()){
                //TODO: this and end game
            }
            boardGrid.setOpacity(1);
            updateBoard();*/
        }
    }

    public void startTurn(){
        boardGrid.setOpacity(1);
        updateBoard();
    }

    public boolean checkLegalChoiceShelfie(){
        int i = firstFreeRowBeforeMoves;
        return boardMoves.size() + i <= shelfieRows;
    }

    /**
     *
     * @param clickedNode
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
    }

    public void updateBoard(){
        GameBoard gameBoard = GUIClientSide.getCli().getUserGameBoard();
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
    }


  /* @Override
    public void initialize(URL url, ResourceBundle rb) {
        //we create a correspondence between imageViews[][] matrix and gridPane
        for (int i=0; i < shelfieColumns; i++){
            for (int j=0; j < shelfieRows; j++){
                //indexes of gridPane start from top-left, while indexes of imageViews start from bottom-left as Shelfie
                //shelfieGridPane.add();//gridPane.add(imageViews[shelfieRows-j-1][i], i, j);
            }
        }
    }*/

   /* /**
     * This method update the Shelfie showing the images of the tiles inserted
     * @param tiles tiles to insert in the shelfie (must be already in the right order)
     * @param column column in which you want to insert the tiles
     * @throws IllegalInsertException in case of a move which is not possible
     */

    //TODO Column select by clicking on it
   /* public void updateShelfie(ArrayList<Tile> tiles, int column) throws IllegalInsertException {
        if (tiles.isEmpty()){
            throw new IllegalInsertException("Illegal insertion caused by empty array");
        } else if(tiles.size() > maxTilesForMove){
            throw new IllegalInsertException("Illegal insertion caused by oversized array");
        } else if ((column < 0) || (column >= shelfieColumns)) {
            throw new IllegalInsertException("Illegal insertion caused by not valid column");
        } else {
            int firstEmptyRow = firstEmptyRow(column);
            if (firstEmptyRow + tiles.size() <= shelfieRows) {
                while (!tiles.isEmpty()) {
                    Image image = new Image("/images/Tile_" + tiles.get(0).getColor().colorLetter() + (totalTiles % tiles.get(0).getId()));
                    imageViews[firstEmptyRow][column].setImage(image);
                    tiles.remove(0);
                }
            }
        }
    }*/

    /*/**
     * This method is used by upadateShelfie to find the first row available of a specific column
     * @param column is the column we analyze
     * @return the first empty row or shelfieRows costant if the column is full
     */
   /* private int firstEmptyRow(int column) {
        for (int i=0; i < shelfieRows; i++){
            if (imageViews[i][column].getImage() != null){
                return i;
            }
        }
        return shelfieRows; //so that
    }*/


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
}
