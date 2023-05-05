package it.polimi.softeng.controller;

import it.polimi.softeng.model.Cell;

import java.util.ArrayList;

public class GameController {
    private String nickname;

    /**
     * Call model update with information collected by client
     * @param tilesToBeRemoved is positions of cell to be removed
     * @param column is column of insertion
     * @param requester is receiver of updates
     */
    public void sendGameMove(ArrayList<Cell> tilesToBeRemoved, int column, String requester) {

        //GameBoard update
        //Shelfie update
        //Update client ?
    }
}
