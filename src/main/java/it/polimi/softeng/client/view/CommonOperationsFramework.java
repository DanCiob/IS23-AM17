package it.polimi.softeng.client.view;

import it.polimi.softeng.JSONWriter.ChatWriter;
import it.polimi.softeng.JSONWriter.GameMoveWriter;
import org.json.simple.JSONObject;

public class CommonOperationsFramework {

    /**
     * Write JSON file containing translation of command
     * @param op is command
     * @param action is command text sent by UI
     * @return true if operation ended successfully
     */
    public boolean actionToJSON(String op, String action)
    {
        switch(op)
        {
            case ("@CHAT"): {
                if (!ChatWriter.chatMessageRegex(action))
                {
                    System.out.println("Error in Chat message syntax, try again!");
                    return false;
                }
                //sendToServer(ChatMessageWriter.writeChatMessage());
            }
            case ("@GAME"): {
                if (!GameMoveWriter.gameMoveRegex(action))
                {
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


    public void sendToServer (JSONObject object)
    {

    }
}
