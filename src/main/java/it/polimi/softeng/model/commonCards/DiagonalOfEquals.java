package it.polimi.softeng.model.commonCards;

import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;

public class DiagonalOfEquals extends CommonCards {

    /**
     * the direction of the controlled diagonal is stored in this variable and is considered as from the lowest point of the diagonal to the highest (ie left
     * means I'm checking for a diagonal starting from cell[0+offset][5] to cell[4+offset][0], so on for RIGHT)
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
     * method used to verify whether the common goal (in this case "diagonal of five equals") is achieved; it cycles through the various possible pattern
     * of diagonal to find one
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
     * this method is the base of verifyShape; it has two internal ways to verify for a diagonal based on the required direction, including an
     * offset modifier
     * @param shelfie the shelfie on which you check the common goal
     * @param direction the direction of the stair searched as explained in the Direction doc
     * @param offset vertical offset as explained above
     * @return boolean value representing whether the searched pattern is present or not
     */
    private boolean singleCheck(Shelfie shelfie, Direction direction, int offset){
        int currentRow;
        int count = 0;
        Tile t;
        switch (direction) {
            case LEFT -> {      // sposta in funzione
                t = shelfie.getTile(4+offset, 0);
                if (t == null) return false;

                // i for columns; the cycle checks for every column whether there is a tile in the position needed for a diagonal
                for (int i = 0; i < 5; i++) {
                    currentRow = 4 - i + offset;

                    if (shelfie.getTile(currentRow, i) != null && shelfie.getTile(currentRow,i).getColor() == t.getColor() ) {
                        count++;
                    }
                }
                return count == 5;
            }
            case RIGHT -> {
                t = shelfie.getTile(offset, 0);
                if (t == null) return false;

                // i for columns; the cycle checks for every column whether there is a tile in the position needed for a diagonal
                for (int i = 0; i < 5; i++) {
                    currentRow = i + offset;

                    if (shelfie.getTile(currentRow, i) != null && shelfie.getTile(currentRow,i).getColor() == t.getColor() ) {
                        count++;
                    }
                }
                return count == 5;
            }
        }
        return false;
    }
}
