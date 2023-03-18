package it.polimi.softeng.model.CommonCards;

import it.polimi.softeng.model.Shelfie;

public class  TwoRowsOfFiveDifferent extends CommonCards{

    public boolean verifyShape(Shelfie shelfie){
        boolean notVerified = false;
        int i=0, verifiedRows = 0, j, z=0;

        while(i<6 && verifiedRows<2){ //it ends the loop when there are no more rows to check or it has  already found two rows with five different colors
            j=0;
            while(j<4 && shelfie.getGrid()[i][j] != null){ //column
                z=j+1;
                notVerified = false;
                while((z<5) && (shelfie.getGrid()[i][z] != null) && (notVerified==false)){
                    if(shelfie.getGrid()[i][j].getColor() == shelfie.getGrid()[i][z].getColor())
                        notVerified = true;
                    z++;
                }
                if(notVerified == false && z == 5)
                    verifiedRows++;
                j++;
            }
        }
        if(notVerified == false)//if it has not found two tiles in the same row with the same color
            return true; //the shape is present in the shelfie
        else
            return false; //the shape is not present in the shelfie
    }
}
