package it.polimi.softeng.client.view;

import it.polimi.softeng.customExceptions.IllegalInsertException;
import it.polimi.softeng.model.GameBoard;
import it.polimi.softeng.model.PersonalCards;
import it.polimi.softeng.model.Shelfie;
import org.json.simple.JSONObject;

public abstract class CommonOperationsFramework {

    /**
     * Write JSON file containing translation of command
     *
     * @param op     is command
     * @param action is command text sent by UI
     * @return JSONObject containing message that will be sent to server
     */
    public abstract JSONObject actionToJSON(String op, String action) throws IllegalInsertException;

    public abstract boolean RMIInvoker(String op, String action);

    public abstract void boardUpdater(GameBoard b);

    public abstract void shelfieUpdater(Shelfie s);

    public abstract void scoreUpdater(int s);

    public abstract void personalCardUpdater (PersonalCards pc);

    }
