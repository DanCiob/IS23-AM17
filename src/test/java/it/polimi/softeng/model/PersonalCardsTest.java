package it.polimi.softeng.model;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

import it.polimi.softeng.customExceptions.IllegalInsertException;
import org.junit.jupiter.api.Test;


class PersonalCardsTest {
    /**
     * instruction test coverage for Objective cell class
     */
    @Test
    public void testObjectiveCell(){
        PersonalCards.ObjectiveCell cell = new PersonalCards.ObjectiveCell(0,0, Tile.TileColor.BLUE);

        assertEquals(Tile.TileColor.BLUE, cell.getColor());
        assertEquals(0,cell.getX());
        assertEquals(0,cell.getY());
    }

    /**
     * instruction coverage test for getCurrentScore
     */
    @Test
    public void testGetCurrentScore(){
        PersonalCards personalCard = PersonalCards.PERSONAL_CARD_1;
        ArrayList<Tile> tiles = new ArrayList<>();
        Shelfie shelfie = new Shelfie();
        Tile tile1;
        Tile tile2;
        Tile tile3;

        assertEquals(0,personalCard.getCurrentScore(shelfie));

        tile1 = new Tile(1, Tile.TileColor.WHITE);      //filling the first column to get a purple in grid[5][0]
        tile2 = new Tile(2, Tile.TileColor.WHITE);
        tile3 = new Tile(3, Tile.TileColor.WHITE);
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles,0);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        tiles.clear();
        tile1 = new Tile(4, Tile.TileColor.WHITE);
        tile2 = new Tile(5, Tile.TileColor.WHITE);
        tile3 = new Tile(6, Tile.TileColor.PURPLE);
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles,0);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        assertEquals(1,personalCard.getCurrentScore(shelfie));

        tiles.clear();
        tile1 = new Tile(6, Tile.TileColor.WHITE);
        tile2 = new Tile(7, Tile.TileColor.WHITE);
        tile3 = new Tile(8, Tile.TileColor.YELLOW);
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles,1);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        assertEquals(2,personalCard.getCurrentScore(shelfie));

        tile1 = new Tile(9, Tile.TileColor.CYAN);      //filling the first column to get a purple in grid[5][0]
        tile2 = new Tile(10, Tile.TileColor.WHITE);
        tile3 = new Tile(11, Tile.TileColor.WHITE);
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles,2);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        assertEquals(4,personalCard.getCurrentScore(shelfie));

        tiles.clear();
        tile1 = new Tile(12, Tile.TileColor.WHITE);
        tile2 = new Tile(13, Tile.TileColor.WHITE);
        tile3 = new Tile(14, Tile.TileColor.BLUE);
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles,2);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        assertEquals(6,personalCard.getCurrentScore(shelfie));

        tiles.clear();
        tile1 = new Tile(15, Tile.TileColor.WHITE);
        tile2 = new Tile(16, Tile.TileColor.WHITE);
        tile3 = new Tile(17, Tile.TileColor.WHITE);
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles,3);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        tiles.clear();
        tile1 = new Tile(18, Tile.TileColor.WHITE);
        tiles.add(tile1);
        try {
            shelfie.insertTile(tiles,3);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        assertEquals(9,personalCard.getCurrentScore(shelfie));

        tiles.clear();
        tile1 = new Tile(12, Tile.TileColor.WHITE);
        tile2 = new Tile(13, Tile.TileColor.WHITE);
        tile3 = new Tile(14, Tile.TileColor.WHITE);
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        try {
            shelfie.insertTile(tiles,4);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        tiles.clear();
        tile1 = new Tile(12, Tile.TileColor.WHITE);
        tile2 = new Tile(13, Tile.TileColor.GREEN);
        tiles.add(tile1);
        tiles.add(tile2);
        try {
            shelfie.insertTile(tiles,2);
        } catch (IllegalInsertException e) {
            System.out.println("errore !");
        }
        assertEquals(12,personalCard.getCurrentScore(shelfie));
    }
}