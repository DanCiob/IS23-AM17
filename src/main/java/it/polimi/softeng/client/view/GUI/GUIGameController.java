package it.polimi.softeng.client.view.GUI;

import it.polimi.softeng.client.view.CLI.CLI;
import it.polimi.softeng.client.view.MessageHandler;
import it.polimi.softeng.connectionProtocol.client.ClientSide;
import it.polimi.softeng.customExceptions.IllegalInsertException;
import it.polimi.softeng.model.Cell;
import it.polimi.softeng.model.Tile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

public class GUIGameController{

    ArrayList<Cell> boardMoves = new ArrayList<>();

    @FXML GridPane shelfieGridPane;


    @FXML
    GridPane boardGrid;


    @FXML
    protected void removeTileFromBoard(javafx.scene.input.MouseEvent event){
        Node clickedNode = event.getPickResult().getIntersectedNode();
        Image img = new Image("/images/Tile_B3.png");
        ImageView imageView = new ImageView(img);
        imageView.maxHeight(30);
        imageView.maxWidth(33);
        imageView.setFitHeight(30.0);
        imageView.setFitWidth(33.0);
        boardGrid.add(imageView, GridPane.getColumnIndex(clickedNode), GridPane.getRowIndex(clickedNode));

      for(Node node: boardGrid.getChildren()){
          if((GridPane.getColumnIndex(node) == GridPane.getColumnIndex(clickedNode))&&(GridPane.getRowIndex(node) == GridPane.getRowIndex(clickedNode))){
              ImageView i = (ImageView) node;
              i.setImage(null);
              createBoardMoves(GridPane.getRowIndex(clickedNode), GridPane.getColumnIndex(clickedNode));
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

    @FXML
    protected void sendBoardMoves(){
        CLI cli = new CLI();
        /*JSONObject toBeSent = cli.actionToJSON("@GAME", action);

        //Send message to server
        if (toBeSent != null)
            clientSide.sendMessage(clientSignObject(toBeSent, "@GAME", Nickname).toJSONString());
        //manda messaggio mossa, anche con colonna shelfie e verifica che ritorni ok (o aggiungere controllo?)
        boardMoves.clear();*/
    }

    @FXML
    GridPane shelfie1;
    @FXML
    protected void insertInShelfie(javafx.scene.input.MouseEvent event) {
        Node clickedNode = event.getPickResult().getIntersectedNode();
        int row = firstFree(clickedNode);
        Image img = new Image("/images/Tile_B3.png");
        for (Node node : shelfie1.getChildren()) {
            if ((GridPane.getColumnIndex(node) == GridPane.getColumnIndex(clickedNode)) && row==GridPane.getRowIndex(node)) {
                ImageView tileImageView = (ImageView) node;
                tileImageView.setImage(img);
            }

        }
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

    /**
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

    /**
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


}
