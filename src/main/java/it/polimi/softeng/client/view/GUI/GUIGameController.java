package it.polimi.softeng.client.view.GUI;

import it.polimi.softeng.customExceptions.IllegalInsertException;
import it.polimi.softeng.model.Tile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static it.polimi.softeng.Constants.*;

public class GUIGameController implements Initializable{
    @FXML GridPane gridPane;
    @FXML ImageView[][] imageViews;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //we create a correspondence between imageViews[][] matrix and gridPane
        for (int i=0; i < shelfieColumns; i++){
            for (int j=0; j < shelfieRows; j++){
                //indexes of gridPane start from top-left, while indexes of imageViews start from bottom-left as Shelfie
                gridPane.add(imageViews[shelfieRows-j-1][i], i, j);
            }
        }
    }

    /**
     * This method update the Shelfie showing the images of the tiles inserted
     * @param tiles tiles to insert in the shelfie (must be already in the right order)
     * @param column column in which you want to insert the tiles
     * @throws IllegalInsertException in case of a move which is not possible
     */

    //TODO Column select by clicking on it
    public void updateShelfie(ArrayList<Tile> tiles, int column) throws IllegalInsertException {
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
    }

    /**
     * This method is used by upadateShelfie to find the first row available of a specific column
     * @param column is the column we analyze
     * @return the first empty row or shelfieRows costant if the column is full
     */
    private int firstEmptyRow(int column) {
        for (int i=0; i < shelfieRows; i++){
            if (imageViews[i][column].getImage() != null){
                return i;
            }
        }
        return shelfieRows; //so that
    }


}
