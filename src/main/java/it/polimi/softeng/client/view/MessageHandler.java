package it.polimi.softeng.client.view;

import it.polimi.softeng.JSONParser.ChatParser;
import it.polimi.softeng.JSONParser.GameMoveParser;
import it.polimi.softeng.JSONParser.RequestParser;
import it.polimi.softeng.client.view.CLI.CLI;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Map;

import static it.polimi.softeng.controller.ChatController.sendChatMessage;
import static it.polimi.softeng.controller.GameController.sendGameMove;

public class MessageHandler {
    /**
     * Map Nickname with its CLI
     */
    Map<String, CLI> NicknameCLI = new HashMap<String, CLI>();


    /**
     * @param message containing JSON message in form of string with request
     */
    public void parsingMessage(String message) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(message);
        //Get nickname of receiver
        String receiver = obj.get("receiver").toString();
        //Get type of request
        String requestType = obj.get("request").toString();

        switch (requestType) {
            //Invoke chat visualizer
            case ("@CHAT") -> {
                if (receiver.equals("all"))
                {
                    for (String s: NicknameCLI.keySet())
                    {
                        JSONParser p1 = new JSONParser();
                        JSONObject objChat = (JSONObject) p1.parse(message);
                        NicknameCLI.get(s).chatVisualizer(objChat);
                    }
                }
                else
                {
                    JSONParser p1 = new JSONParser();
                    JSONObject objChat = (JSONObject) p1.parse(message);
                    NicknameCLI.get(receiver).chatVisualizer(objChat);
                }
            }

            case ("@BORD") -> {
                //TODO get board and notAvailable cells from JSON and print

            }

            case ("@SHEL") -> {
                //TODO get Shelfie from JSON and print
            }

            case ("@VCCA") -> {
                //Get commoncards
            }

            default -> System.out.println("Unrecognized request");
        }
    }
}
