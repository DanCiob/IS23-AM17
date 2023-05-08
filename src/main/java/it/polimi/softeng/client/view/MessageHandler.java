package it.polimi.softeng.client.view;

import it.polimi.softeng.JSONParser.*;
import it.polimi.softeng.client.view.CLI.CLI;
import it.polimi.softeng.model.GameBoard;
import it.polimi.softeng.model.PersonalCards;
import it.polimi.softeng.model.Shelfie;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import static it.polimi.softeng.JSONParser.PersonalCardsParser.personalCardsParser;


/**
 * Manage messages received by Socket and eventually update and visualize updated Shelfie, Board
 */
public class MessageHandler {
    /**
     * Assigned CLI and assigned SocketListener
     */
    CLI cli;

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
                    if (receiver.equals("all"))
                        cli.eventManager("globalChatEvent");
                    else
                        cli.eventManager("chatEvent");
                    cli.chatVisualizer(objChat);
                    }

            //Invoke board visualizer
            case ("@BORD") -> {
                    JSONParser p1 = new JSONParser();
                    JSONObject objBoard = (JSONObject) p1.parse(message);
                    BoardParser bParser = new BoardParser();

                    GameBoard newBoard = bParser.gameBoardFullParser(objBoard.toJSONString());
                    //First receiving of board begins game
                    cli.beginGame(true);
                    cli.eventManager("boardEvent");
                    cli.boardVisualizer(newBoard.getBoard(), newBoard.getNotAvailable());
                    cli.boardUpdater(newBoard);
                }

            //Invoke shelfie visualizer
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
                cli.shelfieUpdater(newShelfie);
            }

            case ("@VCCA") -> {
                JSONParser p1 = new JSONParser();
                JSONObject objCC = (JSONObject) p1.parse(message);
                JSONArray arrayCC = (JSONArray) objCC.get("commonCardsList");;

                //Read and visualize CommonCards
                switch((int) (long) objCC.get("numOfCommonCards"))
                {
                    case 1 -> {
                        cli.eventManager("commonCardEvent");

                        JSONObject cc1 = (JSONObject) arrayCC.get(0);

                        cli.commonCardsVisualizer(cc1.get("name").toString());
                        cli.commonCardUpdater(cc1.get("name").toString(), 1);
                    }
                    case 2 -> {
                        cli.eventManager("commonCardEvent");

                        JSONObject cc1 = (JSONObject) arrayCC.get(0);
                        JSONObject cc2 = (JSONObject) arrayCC.get(1);

                        cli.commonCardsVisualizer(cc1.get("name").toString());
                        cli.commonCardsVisualizer(cc2.get("name").toString());
                        cli.commonCardUpdater(cc1.get("name").toString(), 1);
                        cli.commonCardUpdater(cc2.get("name").toString(), 2);
                    }
                }
            }

            case ("@VPCA") -> {
                JSONParser p1 = new JSONParser();
                JSONObject objPC = (JSONObject) p1.parse(message);

                PersonalCards newPC = null;
                newPC = personalCardsParser(objPC.toJSONString());

                cli.eventManager("personalCardEvent");
                cli.personalCardVisualizer(newPC);
                cli.personalCardUpdater(newPC);
            }

            case ("@VPLA") -> {
                JSONParser p1 = new JSONParser();
                JSONObject objPC = (JSONObject) p1.parse(message);

                cli.eventManager("playerEvent");
                cli.scoreVisualizer(PlayerParser.PlayerAndScoreParser(message));
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
                cli.eventManager("myTurn");
            }
            default -> System.out.println("Unrecognized request");
        }
    }
}
