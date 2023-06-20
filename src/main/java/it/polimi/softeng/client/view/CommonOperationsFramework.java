package it.polimi.softeng.client.view;

import it.polimi.softeng.customExceptions.IllegalInsertException;
import org.json.simple.JSONObject;

public abstract class CommonOperationsFramework {

    public abstract JSONObject actionToJSON(String op, String action) throws IllegalInsertException;

    public abstract boolean RMIInvoker(String op, String action);

}
