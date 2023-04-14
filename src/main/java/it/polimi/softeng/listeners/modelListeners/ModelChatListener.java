package it.polimi.softeng.listeners.modelListeners;

import it.polimi.softeng.listeners.Listeners;

import java.beans.PropertyChangeEvent;

/**
 * When an update regarding the board is registered, BoardListener notifies View (Client)
 */
public class ModelChatListener implements Listeners {

    /**
     * Method propertyChange notifies the View if a chat message is received
     *
     * @param evt of type PropertyChangeEvent - the event received.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //Send JSON message regarding chat
    }
}
