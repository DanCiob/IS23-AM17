package it.polimi.softeng.client.view;

import it.polimi.softeng.customExceptions.IllegalInsertException;
import it.polimi.softeng.model.GameBoard;
import it.polimi.softeng.model.PersonalCards;
import it.polimi.softeng.model.Shelfie;
import org.json.simple.JSONObject;

public abstract class CommonOperationsFramework {

    public abstract JSONObject actionToJSON(String op, String action) throws IllegalInsertException;

    public abstract boolean RMIInvoker(String op, String action);

}
