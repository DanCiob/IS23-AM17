package it.polimi.softeng.JSONParser;

import it.polimi.softeng.model.PersonalCards;
import it.polimi.softeng.model.Tile;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static it.polimi.softeng.JSONParser.PersonalCardsParser.InitializePersonalCards;
import static org.junit.jupiter.api.Assertions.*;

class PersonalCardsParserTest {

    //Check if all PersonalCards are stored
    @Test
    void initializePersonalCardsTest() {
        ArrayList<PersonalCards> pc;

        pc = InitializePersonalCards("src/main/resources/PersonalCards.json");

        //Size is 12
        assertEquals(12, pc.size());
        //Every PersonalCard has 6 cells
        for (int i = 0; i < 12; i++)
        {
            assertNotNull(pc.get(i).getObjective()[0]);
            assertNotNull(pc.get(i).getObjective()[1]);
            assertNotNull(pc.get(i).getObjective()[2]);
            assertNotNull(pc.get(i).getObjective()[3]);
            assertNotNull(pc.get(i).getObjective()[4]);
            assertNotNull(pc.get(i).getObjective()[5]);
        }
    }

    //Check PersonalCard1 and some of its cells
    @Test
    void PersonalCards1Test() {
        ArrayList<PersonalCards> pc;
        pc = InitializePersonalCards("src/main/resources/PersonalCards.json");
        PersonalCards currentPC = pc.get(0);

        //Check if ObjectiveCells follows PersonalCard1 structure
        assertEquals(5, currentPC.getObjective()[0].getRow());
        assertEquals(0, currentPC.getObjective()[0].getColumn());
        assertEquals(currentPC.getObjective()[0].getColor(), Tile.TileColor.PURPLE);

        assertEquals(4, currentPC.getObjective()[5].getRow());
        assertEquals(4, currentPC.getObjective()[5].getColumn());
        assertEquals(currentPC.getObjective()[5].getColor(), Tile.TileColor.GREEN);

        assertEquals(5, currentPC.getObjective()[3].getRow());
        assertEquals(2, currentPC.getObjective()[3].getColumn());
        assertEquals(currentPC.getObjective()[3].getColor(), Tile.TileColor.BLUE);
    }

    //Check PersonalCard6 and some of its cells
    @Test
    void PersonalCards6Test() {
        ArrayList<PersonalCards> pc;
        pc = InitializePersonalCards("src/main/resources/PersonalCards.json");
        PersonalCards currentPC = pc.get(5);

        //Check if ObjectiveCells follows PersonalCard1 structure
        assertEquals(1, currentPC.getObjective()[1].getRow());
        assertEquals(1, currentPC.getObjective()[1].getColumn());
        assertEquals(currentPC.getObjective()[1].getColor(), Tile.TileColor.YELLOW);

        assertEquals(1, currentPC.getObjective()[3].getRow());
        assertEquals(3, currentPC.getObjective()[3].getColumn());
        assertEquals(currentPC.getObjective()[3].getColor(), Tile.TileColor.BLUE);

        assertEquals(3, currentPC.getObjective()[4].getRow());
        assertEquals(3, currentPC.getObjective()[4].getColumn());
        assertEquals(currentPC.getObjective()[4].getColor(), Tile.TileColor.WHITE);
    }

    //Check PersonalCard9 and some of its cells
    @Test
    void PersonalCards9Test() {
        ArrayList<PersonalCards> pc;
        pc = InitializePersonalCards("src/main/resources/PersonalCards.json");
        PersonalCards currentPC = pc.get(8);

        //Check if ObjectiveCells follows PersonalCard1 structure
        assertEquals(1, currentPC.getObjective()[1].getRow());
        assertEquals(1, currentPC.getObjective()[1].getColumn());
        assertEquals(currentPC.getObjective()[1].getColor(), Tile.TileColor.CYAN);

        assertEquals(1, currentPC.getObjective()[4].getRow());
        assertEquals(4, currentPC.getObjective()[4].getColumn());
        assertEquals(currentPC.getObjective()[4].getColor(), Tile.TileColor.PURPLE);

        assertEquals(2, currentPC.getObjective()[5].getRow());
        assertEquals(4, currentPC.getObjective()[5].getColumn());
        assertEquals(currentPC.getObjective()[5].getColor(), Tile.TileColor.WHITE);
    }
}