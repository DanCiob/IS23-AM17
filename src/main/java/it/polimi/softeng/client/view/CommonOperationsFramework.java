package it.polimi.softeng.client.view;

import org.json.simple.JSONObject;

public class CommonOperationsFramework {

    /**
     * Write JSON file containing translation of command
     * @param op is command
     * @param action is command text sent by UI
     * @return a JSONObject translation of command
     */
    public JSONObject actionToJSON(String op, String action)
    {
        switch(op)
        {
            case ("@CHAT"): {

            }
            case ("@GAME"): {

            }
            case ("@VPCA"): {

            }
            case ("@VCCA"): {

            }
            case ("@VPLA"): {

            }
            case ("@VSCO"): {

            }
        }





        System.out.println("Test");
        return null;
    }
}
