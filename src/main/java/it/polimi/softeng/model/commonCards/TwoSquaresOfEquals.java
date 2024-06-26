package it.polimi.softeng.model.commonCards;

import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;

import java.util.ArrayList;
import static it.polimi.softeng.Constants.*;

/**
 *  This class implements the method verifyShape() to check if the shelfie has two 2x2 squares of tiles of the same type.
 */


public class TwoSquaresOfEquals extends CommonCards {
    /**
     * Count used to see how many squares I've found while searching.
     */
    private int countOfSquares = 0;
    /**
     * Array list used to memorize the used tiles for the squares
     */
    private final ArrayList<Tile> usedTiles = new ArrayList<>();

    @Override
    public boolean verifyShape(Shelfie s) {
        /*
         The method searches for 2x2 squares in shelfie by looking at two vertically contiguous tiles in the same column,
         asking whether they're the same colour; if true it looks at the horizontally contiguous right tiles to the ones
         found before and checks whether they're of the same colour as the ones before it and if those two are of the same
         colour; if again true it marks them as used and countOfSquares++.
         */
        for(int i = 0; i < shelfieColumns - 1; i++){         //cycles the columns
            for(int j = 0; j < shelfieRows - 1; j++){    //cycles groups of two tiles on the same column

                //I see where I have two tiles of the same colour in one column that I haven't used for squares
                if(s.getTile(j,i)!=null && s.getTile(j+1,i) != null && s.getTile(j,i).getColor() == s.getTile(j+1,i).getColor() && !usedTiles.contains(s.getTile(j,i)) && !usedTiles.contains(s.getTile(j+1,i))){

                    //I see where I have two tiles of the same colour in right next column that I haven't used for squares
                    if(s.getTile(j,i+1)!=null && s.getTile(j+1,i+1)!=null && s.getTile(j,i+1).getColor() == s.getTile(j+1,i+1).getColor() && !usedTiles.contains(s.getTile(j,i+1)) && !usedTiles.contains(s.getTile(j+1,i+1))){

                        //I check if the tiles I've found are the same colour
                        if(s.getTile(j,i+1).getColor() == s.getTile(j,i).getColor()){
                            countOfSquares++;
                            usedTiles.add(s.getTile(j,i));
                            usedTiles.add(s.getTile(j+1,i));
                            usedTiles.add(s.getTile(j,i+1));
                            usedTiles.add(s.getTile(j+1,i+1));
                        }
                    }
                }
                if(countOfSquares == 2) return true;
            }
        }
        return false;
    }

    public String getName () {return "TwoSquaresOfEquals";}

}
