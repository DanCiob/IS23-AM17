package it.polimi.softeng.JSONParser;

import it.polimi.softeng.model.PersonalCards;
import it.polimi.softeng.model.Tile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;

public class PersonalCardsParser {

    //Number of PersonalCards available in the game
    //final static int NumberOfPC = 12;
    //USARE LA COSTANTE 'totalPersonalCards'

    //Number of Cell inside PersonalCard
    //final static int NumberOfObjectiveCell = 6;
    //USARE LA COSTANTE 'colorsOfPersonalCards'

    /**
     *
     * @return an ArrayList of PersonalCards read from JSON file
     */
    public static ArrayList<PersonalCards> InitializePersonalCards(String path) {

        ArrayList<PersonalCards> tbr = new ArrayList<>();
        int rowTemp = 0;
        int columnTemp = 0;
        String color;
        Tile.TileColor colorTemp = null;
        int currentCard = 0;


        JSONParser parser = new JSONParser();

        try (Reader reader = new FileReader(path)) {

            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            //Loop in List of PersonalCards
            JSONArray PCList = (JSONArray) jsonObject.get("Personal_Cards_List");
            Iterator iterator = PCList.iterator();

            //Iterate all JSON file to read every PersonalCard
            while (iterator.hasNext()) {

                currentCard++;
                JSONObject current = (JSONObject) iterator.next();
                PersonalCards pcTemp = new PersonalCards();

                String currentPC = "PERSONAL_CARD_" + currentCard;
                JSONArray CurrentCardRead = (JSONArray) current.get(currentPC);
                Iterator iterator1 = CurrentCardRead.iterator();

                //Iterate inside single PersonalCard
                for (int i = 0; iterator1.hasNext(); i++)
                {
                    JSONObject currentCell = (JSONObject) iterator1.next();
                    rowTemp = (int)(long) currentCell.get("row");
                    columnTemp = (int)(long) currentCell.get("column");
                    colorTemp = StringToColor((String) currentCell.get("color"));

                    /*
                    System.out.println(xTemp);
                    System.out.println(yTemp);
                    System.out.println(colorTemp);
                     */
                    pcTemp.setObjective(i, rowTemp, columnTemp, colorTemp);
                }
                tbr.add(currentCard-1, pcTemp);
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
            default -> {return null;}


        }
    }

    /**
     * Read personal card from JSON file and return an object
     * @param personalCard
     * @return
     * @throws ParseException
     */
    public static PersonalCards personalCardsParser(String personalCard) throws ParseException {

        PersonalCards pcToBeRet = new PersonalCards();
        int rowTemp = 0;
        int columnTemp = 0;
        String color;
        Tile.TileColor colorTemp = null;
        int currentCard = 0;


        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(personalCard);

        //Iterate all JSON file to read every PersonalCard
                JSONArray CurrentCardRead = (JSONArray) jsonObject.get("personalCard");
                Iterator iterator1 = CurrentCardRead.iterator();

                //Iterate inside single PersonalCard
                for (int i = 0; iterator1.hasNext(); i++)
                {
                    JSONObject currentCell = (JSONObject) iterator1.next();
                    rowTemp = (int)(long) currentCell.get("row");
                    columnTemp = (int)(long) currentCell.get("column");
                    colorTemp = StringToColor((String) currentCell.get("color"));

                    pcToBeRet.setObjective(i, rowTemp, columnTemp, colorTemp);
                }
        return pcToBeRet;
    }
}
