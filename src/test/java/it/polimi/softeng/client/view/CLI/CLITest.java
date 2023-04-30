package it.polimi.softeng.client.view.CLI;

import it.polimi.softeng.JSONParser.ChatParser;
import it.polimi.softeng.model.Board;
import it.polimi.softeng.model.PersonalCards;
import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;
import org.json.simple.JSONObject;
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
        cli.commands(true);
        cli.commands(false);
    }
}