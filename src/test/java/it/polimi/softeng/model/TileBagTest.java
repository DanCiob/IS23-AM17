package it.polimi.softeng.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing tile bag functionalities
 */
class TileBagTest {

    @Test
    void drawTileTest() {
        TileBag tilebag = new TileBag();

        tilebag.initializeTile();

        Tile newTile = tilebag.drawTile();
        assertNotNull(newTile);
        assertNotNull(tilebag.getTileBag());
        assertNotNull(newTile.getColor().colorLetter());
    }
}