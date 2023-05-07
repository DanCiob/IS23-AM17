package it.polimi.softeng.controller;


import it.polimi.softeng.JSONParser.ChatParser;
import it.polimi.softeng.JSONParser.GameMoveParser;
import it.polimi.softeng.JSONParser.LoginParser;
import it.polimi.softeng.JSONParser.RequestParser;
import it.polimi.softeng.connectionProtocol.ServerSide;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import static it.polimi.softeng.Constants.*;
import static it.polimi.softeng.JSONWriter.ErrorWriter.writeError;
import static it.polimi.softeng.JSONWriter.ServerSignatureWriter.serverSignObject;

public class ServerMessageHandler {
    private Controller controller;
    private ServerSide serverSide;

    public ServerMessageHandler(Controller controller) {
        this.controller = controller;

    }

    public Controller getController() {
        return controller;
    }

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
                boolean confirm = controller.fetchChatRequest (cp.getReceiver(), message, requester);

                if (cp.getReceiver().equals(requester))
                    controller.sendErrorMessage(serverSignObject(writeError(YOU_ARE_RECEIVER), "@ERRO", requester).toJSONString(), requester);
                else if (!confirm)
                    controller.sendErrorMessage(serverSignObject(writeError(INVALID_RECEIVER), "@ERRO", requester).toJSONString(), requester);
                else
                    //Descriptive output
                    System.out.println("Received chat message request from " + requester);
            }

            case ("@GAME") -> {
                //Invoke gameController
                GameMoveParser gp = new GameMoveParser();
                gp.gameMoveParser(message);
                boolean confirm = controller.fetchGameMoveRequest (gp.getTilesToBeRemoved(), gp.getColumn(), gp.getRequester());

                if (!confirm)
                    controller.sendErrorMessage(serverSignObject(writeError(INVALID_RECEIVER), "@ERRO", requester).toJSONString(), requester);
                else
                    //Descriptive output
                    System.out.println("Received game move request from " + requester);
            }

            case ("@LOGN") -> {
                LoginParser lp = new LoginParser();
                lp.loginParser(message);
                boolean confirm = controller.fetchLoginRequest(lp.getNickname(), lp.getGameMode(), lp.getStartGame(), lp.getNumOfPlayer());
                //TODO Multiple games -> startGame

                if (!confirm)
                    serverSide.sendMessage(serverSignObject(writeError(ALREADY_LOGGED_IN), "@ERRO", requester).toJSONString(), requester);

                //Descriptive output
                System.out.println("Received login request from " + requester);
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
