package it.polimi.softeng.model.commonCards;

import it.polimi.softeng.model.Shelfie;

public class  TwoRowsOfFiveDifferent extends CommonCards{

    /**Two lines each formed by 5 different types of tiles. One line can show the same or a different combination of the other line
     * @param shelfie to check
     * @return boolean which is true if the shape is present in the shelfie or 0 if it's not
     */
    public boolean verifyShape(Shelfie shelfie){
        boolean notVerified = false;
        int i=0, verifiedRows = 0, j, z;

        while(i<6 && verifiedRows<2){ //it ends the loop when there are no more rows to check, or it has  already found two rows with five different colors
            j=0;
            while(j<4 && shelfie.getGrid()[i][j] != null){ //column
                z=j+1;
                notVerified = false;
                while((z<5) && (shelfie.getGrid()[i][z] != null) && (!notVerified)){
                    if(shelfie.getGrid()[i][j].getColor() == shelfie.getGrid()[i][z].getColor())
                        notVerified = true;
                    z++;
                }
                if(!notVerified && z == 5)
                    verifiedRows++;
                j++;
            }
            i++;
        }

        return !notVerified;
    }
}