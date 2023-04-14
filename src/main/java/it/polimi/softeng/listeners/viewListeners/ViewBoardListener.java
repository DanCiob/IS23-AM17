package it.polimi.softeng.listeners.viewListeners;

import it.polimi.softeng.listeners.Listeners;

import java.beans.PropertyChangeEvent;

public class ViewBoardListener implements Listeners {

    /**
     * Method propertyChange notifies the Controller with the update regarding board
     *
     * @param evt of type PropertyChangeEvent - the event received.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //Send JSON message regarding board
    }
}
