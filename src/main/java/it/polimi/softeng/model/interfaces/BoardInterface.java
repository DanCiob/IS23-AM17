package it.polimi.softeng.model.interfaces;

import it.polimi.softeng.model.Cell;

import java.util.ArrayList;

/**
 * Communicate with board (CONTROLLER-MODEL)
 */
public interface BoardInterface {
    boolean updateBoard(ArrayList<Cell> positionsToBeRemoved);
}
