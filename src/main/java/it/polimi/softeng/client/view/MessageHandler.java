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
     * Assigned CLI or GUI (implementing generic UI)
     */
    UI ui;

    public MessageHandler(UI ui) {
        this.ui = ui;
    }

    /**
     * Parse message receiving from server with socket communication
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
            //Received a chat message -> invoke visualizer
            case ("@CHAT") -> {
                    JSONParser p1 = new JSONParser();
                    JSONObject objChat = (JSONObject) p1.parse(message);
                    if (receiver.equals("all"))
                        ui.eventManager("globalChatEvent");
                    else
                        ui.eventManager("chatEvent");
                    ui.chatVisualizer(objChat);
                    }

            //Received board update -> update gameBoard and invoke visualizer
            case ("@BORD") -> {
                    JSONParser p1 = new JSONParser();
                    JSONObject objBoard = (JSONObject) p1.parse(message);
                    BoardParser bParser = new BoardParser();

                    GameBoard newBoard = bParser.gameBoardFullParser(objBoard.toJSONString());
                    //First receiving of board begins game
                    ui.beginGame(true);
                    ui.eventManager("boardEvent");
                    ui.boardVisualizer(newBoard.getBoard(), newBoard.getNotAvailable());
                    ui.boardUpdater(newBoard);
                }

            //Received shelfie update -> update Shelfie and invoke visualizer
            case ("@SHEL") -> {
                JSONParser p1 = new JSONParser();
                JSONObject objShelfie = (JSONObject) p1.parse(message);
                Shelfie newShelfie;
                ShelfieParser sp = new ShelfieParser();

                //Create updated Shelfie from JSONMessage
                newShelfie = sp.shelfieParserClientSide(objShelfie.toJSONString());
                //Set new shelfie
                ui.shelfieUpdater(newShelfie);
                //Visualize new Shelfie
                ui.eventManager("shelfieEvent");
                ui.shelfieVisualizer(newShelfie.getGrid());
                ui.shelfieUpdater(newShelfie);
            }

            //Received shelfie update for other players -> other shelfie visualizer
            case ("@OSHE") -> {
                JSONParser p1 = new JSONParser();
                JSONObject objShelfie = (JSONObject) p1.parse(message);
                Shelfie newShelfie;
                ShelfieParser sp = new ShelfieParser();
                String owner = (String) objShelfie.get("owner");

                //Create updated Shelfie from JSONMessage
                newShelfie = sp.shelfieParserClientSide(objShelfie.toJSONString());
                //Visualize new Shelfie
                ui.eventManager("systemEvent");

                JSONObject objMess = new JSONObject();
                objMess.put("requester", "System");
                objMess.put("message", "Received " + owner + "'s shelfie");
                ui.chatVisualizer(objMess);
                ui.shelfieVisualizer(newShelfie.getGrid());
                ui.shelfieUpdater(newShelfie, owner);
            }

            //First receiving of common cards -> update them and invoke visualizer
            case ("@VCCA") -> {
                JSONParser p1 = new JSONParser();
                JSONObject objCC = (JSONObject) p1.parse(message);
                JSONArray arrayCC = (JSONArray) objCC.get("commonCardsList");

                //Read and visualize CommonCards
                switch((int) (long) objCC.get("numOfCommonCards"))
                {
                    case 1 -> {
                        ui.eventManager("commonCardEvent");

                        JSONObject cc1 = (JSONObject) arrayCC.get(0);

                        ui.commonCardsVisualizer(cc1.get("name").toString());
                        ui.commonCardUpdater(cc1.get("name").toString(), 1);
                    }
                    case 2 -> {
                        ui.eventManager("commonCardEvent");

                        JSONObject cc1 = (JSONObject) arrayCC.get(0);
                        JSONObject cc2 = (JSONObject) arrayCC.get(1);

                        ui.commonCardsVisualizer(cc1.get("name").toString());
                        ui.commonCardsVisualizer(cc2.get("name").toString());
                        ui.commonCardUpdater(cc1.get("name").toString(), 1);
                        ui.commonCardUpdater(cc2.get("name").toString(), 2);
                    }
                }
            }

            //Receiving of common cards -> update them (for badge score)
            case ("@CCAU") -> {
                JSONParser p1 = new JSONParser();
                JSONObject objCC = (JSONObject) p1.parse(message);
                JSONArray arrayCC = (JSONArray) objCC.get("commonCardsList");

                //Read and visualize CommonCards
                switch((int) (long) objCC.get("numOfCommonCards"))
                {
                    case 1 -> {
                        ui.eventManager("commonCardEvent");

                        JSONObject cc1 = (JSONObject) arrayCC.get(0);

                        ui.commonCardUpdater(cc1.get("name").toString(), 1);
                    }
                    case 2 -> {
                        ui.eventManager("commonCardEvent");

                        JSONObject cc1 = (JSONObject) arrayCC.get(0);
                        JSONObject cc2 = (JSONObject) arrayCC.get(1);

                        ui.commonCardUpdater(cc1.get("name").toString(), 1);
                        ui.commonCardUpdater(cc2.get("name").toString(), 2);
                    }
                }
            }

            //Received personal card -> update it and invoke visualizer
            case ("@VPCA") -> {
                JSONParser p1 = new JSONParser();
                JSONObject objPC = (JSONObject) p1.parse(message);

                PersonalCards newPC = null;
                newPC = personalCardsParser(objPC.toJSONString());

                ui.eventManager("personalCardEvent");
                ui.personalCardVisualizer(newPC);
                ui.personalCardUpdater(newPC);
            }

            //Received players and their score -> invoke visualizer
            case ("@VPLA") -> {
                JSONParser p1 = new JSONParser();
                JSONObject objPC = (JSONObject) p1.parse(message);

                ui.eventManager("playerEvent");
                ui.scoreVisualizer(PlayerParser.PlayerAndScoreParser(message));
            }

            //Signal errors
            case ("@ERRO") -> {
                JSONParser p1 = new JSONParser();
                JSONObject objError = (JSONObject) p1.parse(message);

                String error = (String) objError.get("errorType");
                ui.eventManager(error);
            }

            //My turn
            case ("@CONF") -> {
                JSONParser p1 = new JSONParser();
                JSONObject objMess = (JSONObject) p1.parse(message);

                String mess = (String) objMess.get("confirm");
                ui.eventManager("myTurn");
            }

            //Trigger endgame
            case ("@ENDG") -> {
                JSONParser p1 = new JSONParser();
                JSONObject objMess = (JSONObject) p1.parse(message);

                boolean winner = (boolean) objMess.get("result");
                ui.endGame(winner);
            }
            default -> System.out.println("Unrecognized request");
        }
    }
}
