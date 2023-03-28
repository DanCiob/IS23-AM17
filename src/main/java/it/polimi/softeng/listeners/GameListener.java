package it.polimi.softeng.listeners;

import java.util.Collection;
import java.util.HashSet;

public class GameListener implements GameListenerInterface{

    @Override
    public void onGameEvent() {
        System.out.println("Game event called");
    }
}
