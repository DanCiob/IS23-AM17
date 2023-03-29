package it.polimi.softeng.JSONparser;

import it.polimi.softeng.model.PersonalCard;
import org.junit.jupiter.api.Test;
import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.Arrays;

import static it.polimi.softeng.JSONparser.PersonalCardsParser.InitializePersonalCards;
import static it.polimi.softeng.model.PersonalCard.PersonalCardToString;
import static org.junit.jupiter.api.Assertions.*;

class PersonalCardsParserTest {

    @Test
    void initializePersonalCardsTest() {
        ArrayList<PersonalCard> pc = new ArrayList<>();

        pc = InitializePersonalCards();

        for (PersonalCard p: pc)
            PersonalCardToString(p);
    }
}