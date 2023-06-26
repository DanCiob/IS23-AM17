package it.polimi.softeng.model.commonCards;

import it.polimi.softeng.model.Shelfie;
import static it.polimi.softeng.Constants.*;

/**
 *  This class implements the method verifyShape() to check if the shelfie has five tiles of the same type forming an X.
 */

public class XOfEquals extends CommonCards{

    public boolean verifyShape(Shelfie shelfie){
        for(int i=1;i<shelfieRows-1;i++){
            for(int j=1;j<shelfieColumns-1;j++){
                if((shelfie.getGrid()[i][j] != null && shelfie.getGrid()[i-1][j-1] != null)&&(shelfie.getGrid()[i][j].getColor() == shelfie.getGrid()[i-1][j-1].getColor()))
                    if((shelfie.getGrid()[i+1][j+1] != null)&&(shelfie.getGrid()[i][j].getColor() == shelfie.getGrid()[i+1][j+1].getColor()))
                        if((shelfie.getGrid()[i+1][j-1] != null)&&(shelfie.getGrid()[i][j].getColor() == shelfie.getGrid()[i+1][j-1].getColor()))
                            if((shelfie.getGrid()[i-1][j+1] != null)&&(shelfie.getGrid()[i][j].getColor() == shelfie.getGrid()[i-1][j+1].getColor()))
                                return true;
            }
        }
        return false;
    }

    public String getName () {return "XOfEquals";}

}
