package it.polimi.softeng.client.view;

import it.polimi.softeng.JSONParser.ChatParser;
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
                //Send to server
            }
            case ("@GAME"): {
                //Remove spaces to avoid parsing problem
                action = action.replace(" ", "");
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
