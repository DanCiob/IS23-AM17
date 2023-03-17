package it.polimi.softeng.model;

public class  TwoColumnsOfSixDifferent extends CommonCards{
    public boolean verifyShape(Shelfie shelfie){
        boolean notVerified;
        int i=0, j, verifiedColumns=0;

        while((i<5) && (verifiedColumns<2)){//it ends the loop when there are no more columns to check or it has  already found two columns with five different colors
            j=0;
            while(j<5 && shelfie[i][j] != null){
                z=j+1;
                notVerified = false;
                while((z<6) && (shelfie[i][z] != null) && (notVerified==false)){
                    if(shelfie[i][j].getColor() == shelfie[i][z].getColor())
                        notVerified = true;
                    z++;
                }
                if(notVerified == false && z=6)
                    verifiedColumns++;
                j++;
            }

            i++;
        }

        if(notVerified == false)//if it has not found two tiles in the same column with the same color
            return 1; //the shape is present in the shelfie
        else
            return 0; //the shape is not present in the shelfie

    }

}