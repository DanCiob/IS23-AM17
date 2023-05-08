package it.polimi.softeng.model.commonCards;

import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;
import static it.polimi.softeng.Constants.*;


public class DiagonalOfEquals extends CommonCards {

    /**
     * The enum Direction indicates from which bottom angle (+offset) we start to analyze the shelfie
     * (bottom LEFT or bottom RIGHT)
     */
    private enum Direction{
        LEFT,
        RIGHT
    }
    /**
     * indicates whether I'm checking for a diagonal starting from row 0 (offset = 0) or row 1 (offset = 1)
     */
    private int offset;

    /**
     * method used to verify whether the common goal (in this case "diagonal of five equals") is achieved; it cycles
     * through the various possible pattern of diagonal to find one
     * @param s the shelfie on which you check the common goal
     * @return boolean value; true means the common goal is achieved on the shelfie s
     */
    public boolean verifyShape(Shelfie s){
        offset = 0;
        while(offset < 2){
            Direction direction = Direction.LEFT;
            if (singleCheck(s, direction, offset)) return true;
            direction = Direction.RIGHT;
            if (singleCheck(s, direction, offset)) return true;
            offset++;
        }
        return false;
    }
    /**
     * this method is the base of verifyShape; it has two internal ways to verify for a diagonal based on the required
     * direction, including an offset modifier
     * @param shelfie the shelfie on which you check the common goal
     * @param direction the direction we start to analyze the diagonal
     * @param offset vertical offset as explained above
     * @return boolean value representing whether the searched pattern is present or not
     */
    private boolean singleCheck(Shelfie shelfie, Direction direction, int offset){

        switch (direction) {
            case LEFT -> {
                if(leftCheck(shelfie, offset)) return true;
            }
            case RIGHT -> {
                if(rightCheck(shelfie,offset)) return true;
            }
        }
        return false;
    }

    /**
     * private method that controls for left direction diagonals
     * @param shelfie the shelfie on which you check the common goal
     * @param offset vertical offset as explained above
     * @return boolean value representing whether the searched pattern is present or not
     */
    private boolean leftCheck(Shelfie shelfie, int offset){
        int currentRow;
        int count = 0;
        Tile t;

        t = shelfie.getTile(4+offset, 0);
        if (t == null) return false;

        // i for columns; the cycle checks for every column whether there is a tile in the position needed for a diagonal
        for (int i = 0; i < shelfieColumns; i++) {
            currentRow = 4 - i + offset;

            if (shelfie.getTile(currentRow, i) != null && shelfie.getTile(currentRow,i).getColor() == t.getColor() ) {
                count++;
            }
        }
        return count == 5;
    }
    /**
     * private method that controls for right direction diagonals
     * @param shelfie the shelfie on which you check the common goal
     * @param offset vertical offset as explained above
     * @return boolean value representing whether the searched pattern is present or not
     */
    private boolean rightCheck(Shelfie shelfie, int offset){
        int currentRow;
        int count = 0;
        Tile t;

        t = shelfie.getTile(offset, 0);
        if (t == null) return false;

        // i for columns; the cycle checks for every column whether there is a tile in the position needed for a diagonal
        for (int i = 0; i < shelfieColumns; i++) {
            currentRow = i + offset;

            if (shelfie.getTile(currentRow, i) != null && shelfie.getTile(currentRow,i).getColor() == t.getColor() ) {
                count++;
            }
        }
        return count == shelfieColumns;
    }

    public String getName () {return "DiagonalOfEquals";}
}
