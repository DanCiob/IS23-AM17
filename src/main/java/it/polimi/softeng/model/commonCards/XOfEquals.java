package it.polimi.softeng.model.commonCards;

import it.polimi.softeng.model.Shelfie;

public class XOfEquals extends CommonCards{
    /**Five tiles of the same type forming an X
     * @param shelfie to check
     * @return boolean which is true if the shape is present in the shelfie or 0 if it's not
     */

    public boolean verifyShape(Shelfie shelfie){

        for(int i=1;i<5;i++){
            for(int j=1;j<4;j++){
                if(shelfie.getGrid()[i][j].getColor() == shelfie.getGrid()[i-1][j-1].getColor())
                    if(shelfie.getGrid()[i][j].getColor() == shelfie.getGrid()[i+1][j+1].getColor())
                        if(shelfie.getGrid()[i][j].getColor() == shelfie.getGrid()[i+1][j-1].getColor())
                            if(shelfie.getGrid()[i][j].getColor() == shelfie.getGrid()[i-1][j+1].getColor())
                                return true;
            }
        }

        return false;
    }


}
