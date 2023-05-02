package it.polimi.softeng.controller;


import it.polimi.softeng.JSONParser.ChatParser;
import it.polimi.softeng.JSONParser.GameMoveParser;
import it.polimi.softeng.JSONParser.RequestParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import static it.polimi.softeng.controller.ChatController.sendChatMessage;
import static it.polimi.softeng.controller.GameController.sendGameMove;

public class MessageHandler {

    /**
     *
     * @param message containing JSON message in form of string with request
     */
    public void parsingMessage(String message) throws ParseException {
        RequestParser requestParser = new RequestParser();
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(message);
        //Get nickname of requester
        String requester = obj.get("requester").toString();
        //Get type of request
        String requestType = obj.get("request").toString();

        switch (requestType)
        {
            case ("@CHAT") -> {
                //Invoke chatController
                ChatParser cp = new ChatParser();
                cp.chatParser(message);
                sendChatMessage (cp.getReceiver(), cp.getMessage());
            }

            case ("@GAME") -> {
                //Invoke gameController
                GameMoveParser gp = new GameMoveParser();
                gp.GameMoveParser(message);
                sendGameMove (gp.getTilesToBeRemoved(), gp.getColumn(), gp.getRequester());
            }

            case ("@VPLA") -> {
                //Get players and their score
            }

            case ("@VCCA") -> {
                //Get commoncards
            }

            default -> System.out.println("Unrecognized request");
        }


    }
}
