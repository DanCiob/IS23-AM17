package it.polimi.softeng.listeners.viewListeners;

import it.polimi.softeng.listeners.Listeners;

import java.beans.PropertyChangeEvent;

public class ViewChatListener implements Listeners {

    /**
     * Method propertyChange notifies the Controller if a chat message is received
     *
     * @param evt of type PropertyChangeEvent - the event received.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //Send JSON message regarding chat
    }
}
