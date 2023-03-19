package it.polimi.softeng.model.commonCards;

import it.polimi.softeng.model.Shelfie;

public class Stairs extends CommonCards{

    /**
     * the direction of the controlled stair is stored in this variable and is considered as from the lowest point of the stair to the highest (ie left
     * means I'm checking for a stair starting from cell[0+offset][5] to cell[4+offset][0], so on for RIGHT)
     */
    private enum Direction{
        LEFT,
        RIGHT
    }
    /**
     * indicates whether I'm checking for a stair starting from row 0 (offset = 0) or row 1 (offset = 1)
     */
    int offset = 0;

    /**
     * method used to verify whether the common goal (in this case "stairs") is achieved; it cycles through the various possible pattern
     * of stair to find one
     * @param s the shelfie on which you check the common goal
     * @return boolean value; true means the common goal is achieved on the shelfie s
     */
    public boolean verifyShape(Shelfie s){

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
     * this method is the base of verifyShape; it has two internal algorithms to verify for a stair based on the required direction, including an
     * offset modifier
     * @param shelfie the shelfie on which you check the common goal
     * @param direction the direction of the stair searched as explained in the Direction doc
     * @param offset vertical offset as explained above
     * @return boolean value representing whether the searched pattern is present or not
     */
    private boolean singleCheck(Shelfie shelfie, Direction direction,int offset){
        int currentRow;
        int count = 0;
        switch (direction) {
            case LEFT -> {
                // i for columns; the cycle checks for every column whether there is a tile in the position needed for a stair
                for (int i = 0; i < 5; i++) {
                    currentRow = 4 - i + offset;

                    if (shelfie.getTile(currentRow, i) != null) {
                        if (offset == 1 && i == 0) {                                        //controlling if I'm checking the last cell of a column to avoid outofboundEX
                            count++;
                        } else if (shelfie.getTile(currentRow + 1, i) == null) {        //checking there isn't a cell above the one needed for the stair
                            count++;
                        }
                    }
                }
                return count == 5;
            }
            case RIGHT -> {
                // i for columns; the cycle checks for every column whether there is a tile in the position needed for a stair
                for (int i = 0; i < 5; i++) {
                    currentRow = i + offset;

                    if (shelfie.getTile(currentRow, i) != null) {                        //controlling if I'm checking the last cell of a column to avoid outofboundEX
                        if (offset == 1 && i == 4) {
                            count++;
                        } else if (shelfie.getTile(currentRow + 1, i) == null) {    //checking there isn't a cell above the one needed for the stair
                            count++;
                        }
                    }
                }
                return count == 5;
            }
        }
        return false;
    }
}
