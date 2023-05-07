package it.polimi.softeng.client.view.CLI;

import it.polimi.softeng.model.*;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

class CLITest {
    private CLI cli = new CLI();

    @Test
    void setupCLI() {
    }

    @Test
    void boardVisualizerTest4Players() {
        GameBoard gameBoard = new GameBoard();
        gameBoard.resetBoard(4);
        Tile[][] table = gameBoard.getBoard();

        table[1][4] = new Tile(0, Tile.TileColor.BLUE);
        table[4][7] = new Tile(0, Tile.TileColor.WHITE);
        table[7][5] = new Tile(0, Tile.TileColor.YELLOW);
        table[5][1] = new Tile(0, Tile.TileColor.CYAN);
        table[4][4] = new Tile(0, Tile.TileColor.GREEN);
        table[3][2] = new Tile(0, Tile.TileColor.PURPLE);

        CLI cli = new CLI();
        cli.boardVisualizer(table, gameBoard.getNotAvailable());

    }

    @Test
    void boardVisualizerTest3Players() {
        GameBoard gameBoard = new GameBoard();
        gameBoard.resetBoard(3);
        Tile[][] table = gameBoard.getBoard();

        table[1][4] = new Tile(0, Tile.TileColor.BLUE);
        table[4][7] = new Tile(0, Tile.TileColor.WHITE);
        table[7][5] = new Tile(0, Tile.TileColor.YELLOW);
        table[5][1] = new Tile(0, Tile.TileColor.CYAN);
        table[4][4] = new Tile(0, Tile.TileColor.GREEN);
        table[3][2] = new Tile(0, Tile.TileColor.PURPLE);

        CLI cli = new CLI();
        cli.boardVisualizer(table, gameBoard.getNotAvailable());
    }

    @Test
    void boardVisualizerTest2Players() {
        GameBoard gameBoard = new GameBoard();
        gameBoard.resetBoard(2);
        Tile[][] table = gameBoard.getBoard();

        table[1][4] = new Tile(0, Tile.TileColor.BLUE);
        table[4][7] = new Tile(0, Tile.TileColor.WHITE);
        table[7][5] = new Tile(0, Tile.TileColor.YELLOW);
        table[5][1] = new Tile(0, Tile.TileColor.CYAN);
        table[4][4] = new Tile(0, Tile.TileColor.GREEN);
        table[3][2] = new Tile(0, Tile.TileColor.PURPLE);

        CLI cli = new CLI();
        cli.boardVisualizer(table, gameBoard.getNotAvailable());
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
        commonCardsVisualizer_ColumnsOfMaxDiffTypes();
        commonCardsVisualizer_CornersOfEquals();
        commonCardsVisualizer_DiagonalOfEquals();
        commonCardsVisualizer_FourGroupsOfFourEquals();
        commonCardsVisualizer_NEqualTiles();
        commonCardsVisualizer_RowsOfMaxDiffTypes();
        commonCardsVisualizer_SixGroupsOfTwoEquals();
        commonCardsVisualizer_Stairs();
        commonCardsVisualizer_TwoColumnsOfSixDifferent();
        commonCardsVisualizer_TwoRowsOfFiveDifferent();
        commonCardsVisualizer_TwoSquaresOfEquals();
        commonCardsVisualizer_XOfEquals();
    }

    @Test
    void commonCardsVisualizer_SixGroupsOfTwoEquals() {
        String commonCard = "SixGroupsOfTwoEquals";
        cli.commonCardsVisualizer(commonCard);
    }

    @Test
    void commonCardsVisualizer_FourGroupsOfFourEquals() {
        String commonCard = "FourGroupsOfFourEquals";
        cli.commonCardsVisualizer(commonCard);
    }

    @Test
    void commonCardsVisualizer_CornersOfEquals() {
        String commonCard = "CornersOfEquals";
        cli.commonCardsVisualizer(commonCard);
    }

    @Test
    void commonCardsVisualizer_TwoSquaresOfEquals() {
        String commonCard = "TwoSquaresOfEquals";
        cli.commonCardsVisualizer(commonCard);
    }

    @Test
    void commonCardsVisualizer_ColumnsOfMaxDiffTypes() {
        String commonCard = "ColumnsOfMaxDiffTypes";
        cli.commonCardsVisualizer(commonCard);
    }

    @Test
    void commonCardsVisualizer_TwoColumnsOfSixDifferent(){
        String commonCard = "TwoColumnsOfSixDifferent";
        cli.commonCardsVisualizer(commonCard);
    }

    @Test
    void commonCardsVisualizer_NEqualTiles(){
        String commonCard = "NEqualTiles";
        cli.commonCardsVisualizer(commonCard);
    }

    @Test
    void commonCardsVisualizer_TwoRowsOfFiveDifferent(){
        String commonCard = "TwoRowsOfFiveDifferent";
        cli.commonCardsVisualizer(commonCard);
    }

    @Test
    void commonCardsVisualizer_RowsOfMaxDiffTypes(){
        String commonCard = "RowsOfMaxDiffTypes";
        cli.commonCardsVisualizer(commonCard);
    }

    @Test
    void commonCardsVisualizer_DiagonalOfEquals(){
        String commonCard = "DiagonalOfEquals";
        cli.commonCardsVisualizer(commonCard);
    }

    @Test
    void commonCardsVisualizer_XOfEquals(){
        String commonCard = "XOfEquals";
        cli.commonCardsVisualizer(commonCard);
    }

    @Test
    void commonCardsVisualizer_Stairs(){
        String commonCard = "Stairs";
        cli.commonCardsVisualizer(commonCard);
    }



    @Test
    void personalCardVisualizer() {
        ArrayList<PersonalCards> arrayPersonalCards = new ArrayList<>();
        arrayPersonalCards = PersonalCards.FillPersonalCardsBag();
        CLI cli = new CLI();
        cli.personalCardVisualizer(arrayPersonalCards.get(0));
        cli.personalCardVisualizer(arrayPersonalCards.get(1));
        cli.personalCardVisualizer(arrayPersonalCards.get(2));
        cli.personalCardVisualizer(arrayPersonalCards.get(3));
        cli.personalCardVisualizer(arrayPersonalCards.get(4));
        cli.personalCardVisualizer(arrayPersonalCards.get(5));
        cli.personalCardVisualizer(arrayPersonalCards.get(6));
        cli.personalCardVisualizer(arrayPersonalCards.get(7));
        cli.personalCardVisualizer(arrayPersonalCards.get(8));
        cli.personalCardVisualizer(arrayPersonalCards.get(9));
        cli.personalCardVisualizer(arrayPersonalCards.get(10));
        cli.personalCardVisualizer(arrayPersonalCards.get(11));
    }

    @Test
    void scoreVisualizer() {
        CLI cli = new CLI();
        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("Alice", 200);
        players.add(p1);
        Player p2 = new Player("Daniel", 100);
        players.add(p2);
        Player p3 = new Player("Andrea",0);
        players.add(p3);
        cli.scoreVisualizer(players);
    }

    @Test
    void chatVisualizer() {
        CLI cli = new CLI();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("receiver", "Tom");
        jsonObject.put("message", "Hello!");
        cli.chatVisualizer(jsonObject);
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
        cli.commands(true);
        cli.commands(false);
    }

    @Test
    void isOkCommand() {
        assertTrue(cli.isOkCommand("@CHAT 'all' HI!", 2));
        assertFalse(cli.isOkCommand("@CH AT", 2));
        assertTrue(cli.isOkCommand("@Chat 'all' hi", 2));

        assertTrue(cli.isOkCommand("@gAmE (3,3),(2,1),(1,2),2", 3));
        assertTrue(cli.isOkCommand("@GAME (1,2),(1,1),1", 3));
        assertFalse(cli.isOkCommand("@GAM (1,2)1", 3));
        assertFalse(cli.isOkCommand("@GAME (1,1),())", 3));
        assertFalse(cli.isOkCommand("@GAME (1,1)1", 3));
        assertFalse(cli.isOkCommand("@game(1,1),2", 3));
    }
}