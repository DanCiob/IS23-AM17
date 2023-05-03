package it.polimi.softeng.client.view;

import it.polimi.softeng.JSONWriter.ChatWriter;
import it.polimi.softeng.JSONWriter.GameMoveWriter;
import it.polimi.softeng.model.Board;
import it.polimi.softeng.model.PersonalCards;
import it.polimi.softeng.model.Shelfie;
import org.json.simple.JSONObject;

public abstract class CommonOperationsFramework {

    /**
     * Write JSON file containing translation of command
     *
     * @param op     is command
     * @param action is command text sent by UI
     * @return true if operation ended successfully
     */
    public boolean actionToJSON(String op, String action) {
        switch (op) {
            case ("@CHAT"): {
                if (!ChatWriter.chatMessageRegex(action)) {
                    System.out.println("Error in Chat message syntax, try again!");
                    return false;
                }
                //sendToServer(ChatMessageWriter.writeChatMessage());
            }
            case ("@GAME"): {
                if (!GameMoveWriter.gameMoveRegex(action)) {
                    System.out.println("Error in Game Move syntax, try again!");
                    return false;
                }
                //sendToServer(GameMoveWriter.writeGameMove());
            }

            case ("@VCCA"): {

            }
            case ("@VPLA"): {

            }
            case ("@VSCO"): {

            }
            break;
            default:
                System.out.println("Unrecognized operation!");
        }

        System.out.println("Test");
        return true;
    }


    public void sendToServer(JSONObject object) {

    }

    public boolean RMIInvoker(String op, String action) {
        switch (op) {
            case ("@CHAT"): {
                if (!ChatWriter.chatMessageRegex(action)) {
                    System.out.println("Error in Chat message syntax, try again!");
                    return false;
                }
                //sendToServer(ChatMessageWriter.writeChatMessage().toString());
            }
            case ("@GAME"): {
                if (!GameMoveWriter.gameMoveRegex(action)) {
                    System.out.println("Error in Game Move syntax, try again!");
                    return false;
                }
                //sendToServer(GameMoveWriter.writeGameMove().toString());
            }

            case ("@LOGN") : {
                //sendLoginRequest(action);
            }
            case ("@VCCA"): {

            }
            case ("@VPLA"): {

            }
            case ("@VSCO"): {

            }
            break;
            default:
                System.out.println("Unrecognized operation!");
        }

        System.out.println("Test");
        return true;
    }

    public abstract void boardUpdater(Board b);

    public abstract void shelfieUpdater(Shelfie s);

    public abstract void scoreUpdater(int s);

    public abstract void personalCardUpdater (PersonalCards pc);

    public abstract void boardUpdaterFromJSON();

    public abstract void shelfieUpdaterFromJSON();

}
