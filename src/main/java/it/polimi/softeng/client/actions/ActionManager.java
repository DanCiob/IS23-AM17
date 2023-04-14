package it.polimi.softeng.client.actions;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;

/**
 * Action manager defines all possible actions doable by user, illegal moves are blocked here (also in the controller, double control client-server)
 */
public class ActionManager implements PropertyChangeListener {
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    /**
     * Defines all possible actions (more can be introduced) doable by user:
     *  - syntax is @4letterscommand command
     *  - GAME -> @GAME position1[][],position2[][],position3[][],column
     *  - CHAT -> @CHAT receiver message
     * @param in is command inserted by user
     * @return true if command is recognized, false if not
     */
    public boolean action (String in)
    {
        String action = in.substring(0, 4).toUpperCase();

        /* If it isn't user turn reject gameMove but accept chat message
        if (action.equals("GAME") && !turn) {
            System.out.println("Not your turn!");
            return false;
        }
        */


        //Parsing command
        switch (action)
        {
            case "GAME":
                //Check if the move (picking empty tile...) and GAME format is legal (more than three positions, less than one, column full...), if not reject move
                //Parse command to JSON file and send to Server
                //return true
                break;
            case "CHAT":
                //Check if chat message is badly formatted (no receiver, empty message...)
                //Parse chat message to JSON file and send to Server
                //return true
                break;
            default:
                System.out.println("Unrecognized command");
                return false;

        }

        return false;
    }
}
