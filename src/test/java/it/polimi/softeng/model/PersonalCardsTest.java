package it.polimi.softeng.model;

import java.util.ArrayList;
import java.util.ArrayList.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


class PersonalCardsTest {
    /**
     * instruction test coverage for Objective cell class
     */
    @Test
    public void testObjectiveCell(){
        PersonalCards.ObjectiveCell cell = new PersonalCards.ObjectiveCell(0,0, Tile.TileColor.BLUE);

        assertEquals(Tile.TileColor.BLUE, cell.getColor());
        assertEquals(0,cell.getX());
        assertEquals(0,cell.getY());
    }

    /**
     * instruction coverage test for getCurrentScore
     */
    @Test
    public void testGetCurrentScore(){
        Shelfie shelfie = new Shelfie();
        ArrayList<Tile> tiles = new ArrayList<>();

        //shelfie.insertTile();
    }
}