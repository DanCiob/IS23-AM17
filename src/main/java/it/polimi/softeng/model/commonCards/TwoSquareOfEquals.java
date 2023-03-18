package it.polimi.softeng.model.commonCards;

import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;

import java.util.ArrayList;

public class TwoSquareOfEquals extends CommonCards {
    private int countOfSquares = 0;
    private ArrayList<Tile> usedTiles = new ArrayList<>();
    @Override
    public boolean verifyShape(Shelfie s) {
        for(int i = 0; i < 4; i++){         //cycles the columns
            for(int j = 0; j < 5; j++){    //cycles groups of two tiles on the same column
                //i see where i have two tiles of the same colour in one column that i haven't used for squares
                if(s.getTile(j,i).getColor() == s.getTile(j+1,i).getColor() && !usedTiles.contains(s.getTile(j,i)) && !usedTiles.contains(s.getTile(j+1,i))){

                    if(s.getTile(j,i+1).getColor() == s.getTile(j+1,i+1).getColor() && !usedTiles.contains(s.getTile(j,i+1)) && !usedTiles.contains(s.getTile(j+1,i+1))){

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

}
