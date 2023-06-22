package it.polimi.softeng.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileBagTest {

    @Test
    void drawTileTest() {
        TileBag tilebag = new TileBag();

        tilebag.initializeTile();

        Tile newTile = tilebag.drawTile();
        assertNotNull(newTile);
    }
}