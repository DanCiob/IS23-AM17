package it.polimi.softeng.client.view.GUI;

import java.util.ArrayList;

public class GUIRegistry {
    protected static ArrayList<GUIClientSide> guiList = new ArrayList<>();
    protected static int numberOfGUI = 0;

    public GUIRegistry(ArrayList<GUIClientSide> guiList) {
        this.guiList = guiList;
    }

}
