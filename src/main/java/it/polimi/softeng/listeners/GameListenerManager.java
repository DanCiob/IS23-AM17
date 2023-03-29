package it.polimi.softeng.listeners;

import java.util.Collection;
import java.util.HashSet;

public class GameListenerManager {
    private Collection<GameListener> gameListeners = new HashSet<>();

    public void addListener (GameListener g)
    {
        gameListeners.add(g);
    }

    public void gameEventHappens()
    {
        for (GameListener g: gameListeners)
        {
            g.onGameEvent();
        }
    }
}
