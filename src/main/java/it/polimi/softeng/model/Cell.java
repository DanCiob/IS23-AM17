package it.polimi.softeng.model;

import java.io.Serializable;

/**
 * This class represents the cell in the Shelfie.
 */
public class Cell implements Serializable { private int row;
    private int column;

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
