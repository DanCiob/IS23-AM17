package it.polimi.softeng.model;

import org.junit.jupiter.api.Test;
import it.polimi.softeng.model.*;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    @Test
    public void initializeTilesTest(){
        Game game = new Game();
        game.initializeTile();

        int whiteCount = 0;
        int blueCount = 0;
        int yellowCount = 0;
        int purpleCount = 0;
        int cyanCount = 0;
        int greenCount = 0;

        int i = 0;
        while(i < 132){
            switch(game.tileBag.get(i).getColor()){
                case WHITE -> whiteCount++;
                case BLUE -> blueCount++;
                case YELLOW -> yellowCount++;
                case PURPLE -> purpleCount++;
                case CYAN -> cyanCount++;
                case GREEN -> greenCount++;
            }
            i++;
        }

        assertEquals(132,game.tileBag.size());
        assertEquals(22, whiteCount);
        assertEquals(22, blueCount);
        assertEquals(22, yellowCount);
        assertEquals(22, purpleCount);
        assertEquals(22, cyanCount);
        assertEquals(22, greenCount);
    }
}