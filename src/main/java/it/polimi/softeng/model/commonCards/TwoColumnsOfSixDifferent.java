package it.polimi.softeng.model.commonCards;

import it.polimi.softeng.model.Shelfie;

public class  TwoColumnsOfSixDifferent extends CommonCards{

    /**Two columns each formed by 6 different types of tiles
     * @param shelfie to check
     * @return boolean which is true if the shape is present in the shelfie or 0 if it's not
     */
    public boolean verifyShape(Shelfie shelfie){
        boolean notVerified;
        int i=0, j, verifiedColumns=0, z;

        while((i<5) && (verifiedColumns<2)){//it ends the loop when there are no more columns to check, or it has  already found two columns with five different colors
            j=0;
            while(j<5 && shelfie.getGrid()[j][i] != null){
                z=j+1;
                notVerified = false;
                while((z<6) && (shelfie.getGrid()[z][i] != null) && (!notVerified)){
                    if(shelfie.getGrid()[j][i].getColor() == shelfie.getGrid()[z][i].getColor())
                        notVerified = true;
                    z++;
                }
                if(!notVerified && z==6)
                    verifiedColumns++;
                j++;
            }

            i++;
        }
        return verifiedColumns >= 2;

    }

}