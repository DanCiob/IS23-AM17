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
                    cli.chatVisualizer(objChat);
                    }

            /*TODO waiting boardParser
            case ("@BORD") -> {
                //TODO get board and notAvailable cells from JSON and print
                for (String s: NicknameCLI.keySet())
                {
                    JSONParser p1 = new JSONParser();
                    JSONObject objBoard = (JSONObject) p1.parse(message);
                    BoardParser bParser = new BoardParser();
                    CLI currentCLI = NicknameCLI.get(s);

                    //TODO clarifications on parser
                    //Update board of all player
                    bParser.boardParser(objBoard.toJSONString(), currentCLI.getUserBoard());
                    currentCLI.getOutput().print("Received board update!");
                    currentCLI.boardVisualizer(currentCLI.getUserBoard().getBoard(), currentCLI.getUserBoard().getNotAvailable());
                }
            }
           */
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
                cli.getOutput().println("Update of your shelfie!");
                cli.shelfieVisualizer(newShelfie.getGrid());
            }

            case ("@VCCA") -> {
                JSONParser p1 = new JSONParser();
                JSONObject objCC = (JSONObject) p1.parse(message);

                switch((int) objCC.get("numOfCommonCards"))
                {
                    case 1 -> {
                        cli.getOutput().println("Common Cards for this match!");
                        cli.commonCardsVisualizer(objCC.get("commonCard1").toString());
                    }
                    case 2 -> {
                        cli.getOutput().println("Common Cards for this match!");
                        cli.commonCardsVisualizer(objCC.get("commonCard1").toString());
                        cli.commonCardsVisualizer(objCC.get("commonCard1").toString());
                    }
                }
            }

            case ("@ERRO") -> {
                JSONParser p1 = new JSONParser();
                JSONObject objError = (JSONObject) p1.parse(message);

                objError.get("errorType");
                cli.getOutput().println("Error received :(");
                cli.getOutput().println(objError.get("content").toString());
            }

            default -> System.out.println("Unrecognized request");
        }
    }
}
