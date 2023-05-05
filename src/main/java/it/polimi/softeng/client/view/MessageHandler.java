package it.polimi.softeng.client.view;

import it.polimi.softeng.JSONParser.*;
import it.polimi.softeng.client.view.CLI.CLI;
import it.polimi.softeng.model.Shelfie;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 * Manage messages received by Socket and eventually update and visualize updated Shelfie, Board
 */
public class MessageHandler {
    /**
     * Assigned CLI and assigned SocketListener
     */
    CLI cli;
    //SocketListener socketListener;

    public MessageHandler(CLI cli) {
        this.cli = cli;
    }

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
                    JSONParser p1 = new JSONParser();
                    JSONObject objChat = (JSONObject) p1.parse(message);
                    cli.eventManager("chatEvent");
                    cli.chatVisualizer(objChat);
                    }


            case ("@BORD") -> {
                    JSONParser p1 = new JSONParser();
                    JSONObject objBoard = (JSONObject) p1.parse(message);
                    //TODO use correct parser
                    BoardParser bParser = new BoardParser();
                    //TODO GameBoard newBoard = bParser.boardParser();
                    cli.eventManager("boardEvent");
                    //TODO cli.boardVisualizer(newBoard);
                }

            case ("@SHEL") -> {
                JSONParser p1 = new JSONParser();
                JSONObject objShelfie = (JSONObject) p1.parse(message);
                Shelfie newShelfie;
                ShelfieParser sp = new ShelfieParser();

                //Create updated Shelfie from JSONMessage
                newShelfie = sp.shelfieParserClientSide(objShelfie.toJSONString());
                //Set new shelfie
                cli.shelfieUpdater(newShelfie);
                //Visualize new Shelfie
                cli.eventManager("shelfieEvent");
                cli.shelfieVisualizer(newShelfie.getGrid());
            }

            case ("@VCCA") -> {
                JSONParser p1 = new JSONParser();
                JSONObject objCC = (JSONObject) p1.parse(message);

                switch((int) objCC.get("numOfCommonCards"))
                {
                    case 1 -> {
                        cli.eventManager("commonCardEvent");
                        cli.commonCardsVisualizer(objCC.get("commonCard1").toString());
                    }
                    case 2 -> {
                        cli.eventManager("commonCardEvent");
                        cli.commonCardsVisualizer(objCC.get("commonCard1").toString());
                        cli.commonCardsVisualizer(objCC.get("commonCard1").toString());
                    }
                }
            }

            case ("@ERRO") -> {
                JSONParser p1 = new JSONParser();
                JSONObject objError = (JSONObject) p1.parse(message);

                String error = (String) objError.get("errorType");
                cli.eventManager(error);
            }

            case ("@CONF") -> {
                JSONParser p1 = new JSONParser();
                JSONObject objMess = (JSONObject) p1.parse(message);

                String mess = (String) objMess.get("confirm");
                cli.eventManager(mess);
            }
            default -> System.out.println("Unrecognized request");
        }
    }
}
