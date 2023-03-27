package it.polimi.softeng.listeners;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameListenerTest {
    GameListenerManager g = new GameListenerManager();
    @Test
    void testGameEvent() {

        g.addListener(new GameListener());
        System.out.println("Player 1 calls an event...");
        g.gameEventHappens();

    }
}