package it.polimi.softeng.listeners.modelListeners;

import it.polimi.softeng.listeners.Listeners;

import java.beans.PropertyChangeEvent;

public class ModelShelfieListener implements Listeners {

    /**
     * Method propertyChange notifies the View with the update regarding shelfie
     *
     * @param evt of type PropertyChangeEvent - the event received.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //Send JSON message regarding shelfie
    }
}
