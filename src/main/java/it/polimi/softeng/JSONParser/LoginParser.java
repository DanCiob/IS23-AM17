package it.polimi.softeng.JSONParser;

import it.polimi.softeng.model.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class LoginParser {

    /**
     * @param path       is login JSON file
     * @param playerList is current player list
     */
    public static void updatePlayerListFromLogin(String path, ArrayList<Player> playerList) {

        JSONParser parser = new JSONParser();

        try (Reader reader = new FileReader(path)) {

            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            //No player in game
            if (playerList.size() == 0)
            {
                String nickname = (String) jsonObject.get("nickname");
                int numPlayer = (int)(long) jsonObject.get("numOfPlayer");

                playerList.add(new Player(nickname, 0));
            }
            //Players already in game -> tbd numOfPlayers in game
            /*else if (playerList.size() < getnumOfPlayers())
            {
                String nickname = (String) jsonObject.get("nickname");

                playerList.add(new Player(nickname, 0));
            }*/
            else
            {
                //refuse connection
            }


        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}