package it.polimi.softeng.client.view.CLI;

import it.polimi.softeng.model.Board;
import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CLITest {
    private CLI cli = new CLI();

    @Test
    void setupCLI() {
    }

    @Test
    void boardVisualizerTest4Players() {
        Board board = new Board();
        board.resetBoard(4);
        Tile[][] table = board.getBoard();

        table[1][4] = new Tile(0, Tile.TileColor.BLUE);
        table[4][7] = new Tile(0, Tile.TileColor.WHITE);
        table[7][5] = new Tile(0, Tile.TileColor.YELLOW);
        table[5][1] = new Tile(0, Tile.TileColor.CYAN);
        table[4][4] = new Tile(0, Tile.TileColor.GREEN);
        table[3][2] = new Tile(0, Tile.TileColor.PURPLE);

        CLI cli = new CLI();
        cli.boardVisualizer(table, board.getNotAvailable());


    }

    @Test
    void boardVisualizerTest3Players() {
        Board board = new Board();
        board.resetBoard(3);
        Tile[][] table = board.getBoard();

        table[1][4] = new Tile(0, Tile.TileColor.BLUE);
        table[4][7] = new Tile(0, Tile.TileColor.WHITE);
        table[7][5] = new Tile(0, Tile.TileColor.YELLOW);
        table[5][1] = new Tile(0, Tile.TileColor.CYAN);
        table[4][4] = new Tile(0, Tile.TileColor.GREEN);
        table[3][2] = new Tile(0, Tile.TileColor.PURPLE);

        CLI cli = new CLI();
        cli.boardVisualizer(table, board.getNotAvailable());
    }

    @Test
    void boardVisualizerTest2Players() {
        Board board = new Board();
        board.resetBoard(2);
        Tile[][] table = board.getBoard();

        table[1][4] = new Tile(0, Tile.TileColor.BLUE);
        table[4][7] = new Tile(0, Tile.TileColor.WHITE);
        table[7][5] = new Tile(0, Tile.TileColor.YELLOW);
        table[5][1] = new Tile(0, Tile.TileColor.CYAN);
        table[4][4] = new Tile(0, Tile.TileColor.GREEN);
        table[3][2] = new Tile(0, Tile.TileColor.PURPLE);

        CLI cli = new CLI();
        cli.boardVisualizer(table, board.getNotAvailable());
    }


    @Test
    void shelfieVisualizer() {
        Shelfie shelfie = new Shelfie();
        ArrayList<Tile> tilesToBeInserted = new ArrayList<>();
        Tile tile1, tile2, tile3, tile4, tile5, tile6;

        tile1 = new Tile(1, Tile.TileColor.BLUE);
        tilesToBeInserted.add(tile1);
        tile2 = new Tile(2, Tile.TileColor.GREEN);
        tilesToBeInserted.add(tile2);
        tile3 = new Tile(3, Tile.TileColor.WHITE);
        tilesToBeInserted.add(tile3);
        shelfie.insertTileForTesting(tilesToBeInserted, 0);
        tilesToBeInserted.clear();

        tile1 = new Tile(4, Tile.TileColor.CYAN);
        tilesToBeInserted.add(tile1);
        tile2 = new Tile(5, Tile.TileColor.PURPLE);
        tilesToBeInserted.add(tile2);
        tile3 = new Tile(6, Tile.TileColor.BLUE);
        tilesToBeInserted.add(tile3);
        tile4 = new Tile(7, Tile.TileColor.YELLOW);
        tilesToBeInserted.add(tile4);
        shelfie.insertTileForTesting(tilesToBeInserted, 1);
        tilesToBeInserted.clear();

        tile1 = new Tile(8, Tile.TileColor.PURPLE);
        tilesToBeInserted.add(tile1);
        tile2 = new Tile(9, Tile.TileColor.CYAN);
        tilesToBeInserted.add(tile2);
        shelfie.insertTileForTesting(tilesToBeInserted, 2);
        tilesToBeInserted.clear();

        tile1 = new Tile(10, Tile.TileColor.PURPLE);
        tilesToBeInserted.add(tile1);
        tile2 = new Tile(11, Tile.TileColor.CYAN);
        tilesToBeInserted.add(tile2);
        tile3 = new Tile(12, Tile.TileColor.BLUE);
        tilesToBeInserted.add(tile3);
        tile4 = new Tile(13, Tile.TileColor.YELLOW);
        tilesToBeInserted.add(tile4);
        tile5 = new Tile(14, Tile.TileColor.GREEN);
        tilesToBeInserted.add(tile5);
        tile6 = new Tile(15, Tile.TileColor.BLUE);
        tilesToBeInserted.add(tile6);
        shelfie.insertTileForTesting(tilesToBeInserted, 4);
        tilesToBeInserted.clear();

        CLI cli = new CLI();
        cli.shelfieVisualizer(shelfie.getGrid());
    }

    @Test
    void commonCardsVisualizer() {
    }

    @Test
    void personalCardVisualizer() {
    }

    @Test
    void scoreVisualizer() {
    }

    @Test
    void chatVisualizer() {
    }

    @Test
    void onlinePlayersVisualizer() {
    }

    @Test
    void beginGame() {
    }

    @Test
    void run() {
    }

    @Test
    void game() {
    }

    @Test
    void commandsTest() {
        cli.commands();
        assertTrue(true);
    }
}