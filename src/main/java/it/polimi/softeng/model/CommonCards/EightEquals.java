package it.polimi.softeng.model.CommonCards;

import it.polimi.softeng.model.Shelfie;

public class  EightEquals extends CommonCards{
    public boolean verifyShape(Shelfie shelfie){
        int i, j;
        int []numTilesForColors = {0, 0, 0, 0, 0 , 0}; // has the number of tiles for every color(position 0: green, 1: blue, 2: light blue, 3: orange, 4:yellow, 5: pink
        for(i=0;i<6;i++){
            for(j=0;j<5;j++){
                switch(shelfie.getGrid()[i][j].getColor()) {
                    //case
                }
            }
        }
        return true; //temp
    }
}