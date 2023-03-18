package it.polimi.softeng.model.CommonCards;

import it.polimi.softeng.model.Shelfie;

public class  TwoColumnsOfSixDifferent extends CommonCards{
    public boolean verifyShape(Shelfie shelfie){
        boolean notVerified = false;
        int i=0, j, verifiedColumns=0, z;

        while((i<5) && (verifiedColumns<2)){//it ends the loop when there are no more columns to check or it has  already found two columns with five different colors
            j=0;
            while(j<5 && shelfie.getGrid()[i][j] != null){
                z=j+1;
                notVerified = false;
                while((z<6) && (shelfie.getGrid()[i][z] != null) && (notVerified==false)){
                    if(shelfie.getGrid()[i][j].getColor() == shelfie.getGrid()[i][z].getColor())
                        notVerified = true;
                    z++;
                }
                if(notVerified == false && z==6)
                    verifiedColumns++;
                j++;
            }

            i++;
        }

        if(notVerified == false)//if it has not found two tiles in the same column with the same color
            return true; //the shape is present in the shelfie
        else
            return false; //the shape is not present in the shelfie

    }

}