package it.polimi.softeng.model.commonCards;

import it.polimi.softeng.model.Shelfie;

public class  TwoRowsOfFiveDifferent extends CommonCards{

    /**Two lines each formed by 5 different types of tiles. One line can show the same or a different combination of the other line
     * @param shelfie to check
     * @return boolean which is true if the shape is present in the shelfie or 0 if it's not
     */
    public boolean verifyShape(Shelfie shelfie){
        boolean notVerified;
        int i=0, verifiedRows = 0, j, z=0;

        while(i<6 && verifiedRows<2){ //it ends the loop when there are no more rows to check, or it has  already found two rows with five different colors
            j=0;
            notVerified = false;
            while(j<4 && shelfie.getGrid()[i][j] != null){ //column
                z=j+1;
                //notVerified = false;
                while((z<5) && (shelfie.getGrid()[i][z] != null) && (!notVerified)){
                    if(shelfie.getGrid()[i][j].getColor() == shelfie.getGrid()[i][z].getColor())
                        notVerified = true;
                    z++;
                }
                //if(!notVerified && z == 5)
                //  verifiedRows++;
                j++;
            }
            if((!notVerified)&&(z==5)&&(shelfie.getGrid()[i][z-1] != null))
                verifiedRows++;

            i++;
        }

        return verifiedRows == 2;

        //int[] numTilesForColors = {0, 0, 0, 0, 0 , 0}; /*numTilesForColors has the number of tiles for every color(position 0:BLUE, 1:WHITE, 2:GREEN, 3:YELLOW, 4:PURPLE, 5:CYAN)**/
         /* for(i=0;i<6;i++){
            notVerified = false;
            for(j=0;j<5;j++){
                if(shelfie.getGrid()[i][j] != null){
                    switch (shelfie.getGrid()[i][j].getColor()) {
                        case BLUE-> numTilesForColors[0]++;
                        case WHITE -> numTilesForColors[1]++;
                        case GREEN -> numTilesForColors[2]++;
                        case YELLOW -> numTilesForColors[3]++;
                        case PURPLE -> numTilesForColors[4]++;
                        case CYAN -> numTilesForColors[5]++;
                    }
                }
            }
            for(z=0;z<6;z++){
                if(numTilesForColors[z]>1){
                    notVerified = true;
                }
                numTilesForColors[z] = 0;
            }
            if(!notVerified){
                verifiedRows++;
            }

        }
        return verifiedRows>=2;*/
    }

}
