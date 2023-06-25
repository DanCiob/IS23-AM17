package it.polimi.softeng.client.view.GUI;

import java.util.ArrayList;

/**
 * Used to memorize reference to GUI chose by users
 */
public class GUIRegistry {
    protected static ArrayList<GUIClientSide> guiList = new ArrayList<>();
    protected static int numberOfGUI = 0;

    public GUIRegistry(ArrayList<GUIClientSide> guiList) {
        this.guiList = guiList;
    }

}
