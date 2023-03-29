package it.polimi.softeng.JSONparser;

import it.polimi.softeng.model.PersonalCard;
import it.polimi.softeng.model.PersonalCards;
import it.polimi.softeng.model.Tile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;

public class PersonalCardsParser {

    //Number of PersonalCards available in the game
    final static int NumberOfPC = 12;
    //Number of Cell inside PersonalCard
    final static int NumberOfObjectiveCell = 6;

    /**
     *
     * @return an ArrayList of PersonalCards read from JSON file
     */
    public static ArrayList<PersonalCard> InitializePersonalCards() {

        ArrayList<PersonalCard> tbr = new ArrayList<>();
        PersonalCard pcTemp = new PersonalCard();
        int xTemp = 0;
        int yTemp = 0;
        String color;
        Tile.TileColor colorTemp = null;
        int currentCard = 0;


        JSONParser parser = new JSONParser();

        try (Reader reader = new FileReader("src/main/java/it/polimi/softeng/PersonalCards.json")) {

            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            //Loop in List of PersonalCards
            JSONArray PCList = (JSONArray) jsonObject.get("Personal_Cards_List");
            Iterator iterator = PCList.iterator();

            //Iterate all JSON file to read every PersonalCard
            while (iterator.hasNext()) {

                currentCard++;
                JSONObject current = (JSONObject) iterator.next();

                String currentPC = "PERSONAL_CARD_" + currentCard;
                JSONArray CurrentCardRead = (JSONArray) current.get(currentPC);
                Iterator iterator1 = CurrentCardRead.iterator();

                //Iterate inside single PersonalCard
                for (int i = 0; iterator1.hasNext(); i++)
                {
                    JSONObject currentCell = (JSONObject) iterator1.next();
                    xTemp = (int)(long) currentCell.get("X");
                    yTemp = (int)(long) currentCell.get("Y");
                    colorTemp = StringToColor((String) currentCell.get("color"));

                    /*
                    System.out.println(xTemp);
                    System.out.println(yTemp);
                    System.out.println(colorTemp);
                     */
                    pcTemp.setObjective(i, xTemp, yTemp, colorTemp);
                }
                tbr.add(pcTemp);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return tbr;
    }

    /**
     *
     * @param s is the string representing color
     * @return the same color casted to Tile.TileColor
     */
    public static Tile.TileColor StringToColor(String s)
    {
        switch (s)
        {
            case "PURPLE" -> {
                return Tile.TileColor.PURPLE;
            }
            case "WHITE" -> {
                return Tile.TileColor.WHITE;
            }
            case "YELLOW" -> {
                return Tile.TileColor.YELLOW;
            }
            case "CYAN" -> {
                return Tile.TileColor.CYAN;
            }
            case "BLUE" -> {
                return Tile.TileColor.BLUE;
            }
            case "GREEN" -> {
                return Tile.TileColor.GREEN;
            }
        }
        return null;
    }

}
