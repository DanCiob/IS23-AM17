package it.polimi.softeng.JSONParser;

import it.polimi.softeng.model.PersonalCard;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static it.polimi.softeng.JSONParser.PersonalCardsParser.InitializePersonalCards;
import static it.polimi.softeng.model.PersonalCard.PersonalCardToString;

class PersonalCardsParserTest {

    @Test
    void initializePersonalCardsTest() {
        ArrayList<PersonalCard> pc = new ArrayList<>();

        pc = InitializePersonalCards();

        int i = 1;
        for (PersonalCard p: pc)
        {
            System.out.println("Personal Card " + i);
            PersonalCardToString(p);
            i++;
        }
    }
}