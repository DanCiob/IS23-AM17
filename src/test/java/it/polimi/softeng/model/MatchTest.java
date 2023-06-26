package it.polimi.softeng.model;

import it.polimi.softeng.client.view.CLI.CLI;
import it.polimi.softeng.customExceptions.IllegalInsertException;
import it.polimi.softeng.model.commonCards.CommonCards;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static it.polimi.softeng.Constants.*;
import static junit.framework.Assert.*;

/**
 * Simulate full match
 */
public class MatchTest {
    Game game = new Game();

    public void fullMatch() {
        ArrayList<String> nameList = new ArrayList<>();
        nameList.add("Alice");
        nameList.add("Andrea");
        nameList.add("Daniel");
        nameList.add("Nicolas");
        int column = 0;
        ArrayList<Cell> positionsToBeRemoved = new ArrayList<>();
        ArrayList<Tile> tilesToInsert = new ArrayList<>();

        game.beginGame(nameList);

        while (!(game.checkEndGame())) {
            game.turn(positionsToBeRemoved, column);
        }
    }

    public String match1() {
        fullMatch();
        CLI cli = new CLI();
        cli.scoreVisualizer(game.getPlayers());
        String vincitore = game.getWinner().getNickname();
        return vincitore;
    }

    @Test
    public void partialTest() {
        ArrayList<String> nameList = new ArrayList<>();
        nameList.add("Alice");
        nameList.add("Andrea");
        nameList.add("Daniel");
        nameList.add("Nicolas");

        game.beginGame(nameList);
        visualizers();

        //first player
        Cell cell = new Cell();
        cell.setRow(0);
        cell.setColumn(3);
        Cell cell1 = new Cell();
        cell1.setRow(0);
        cell1.setColumn(4);
        ArrayList<Cell> move = new ArrayList<>();
        move.add(cell);
        move.add(cell1);
        ArrayList<Tile> tiles = new ArrayList<>();
        Tile tile = new Tile(0, game.getGameBoard().getBoard()[0][3].getColor());
        Tile tile1 = new Tile(1, game.getGameBoard().getBoard()[0][4].getColor());
        tiles.add(tile);
        tiles.add(tile1);
        if (game.getGameBoard().checkLegalChoice(move)) {
            game.getGameBoard().updateBoard(move);
            try {
                if (game.getCurrentPlayer().getShelfie().checkLegalInsert(tiles, 0))
                    game.getCurrentPlayer().getShelfie().insertTile(tiles, 0);
            } catch (IllegalInsertException e) {
                throw new RuntimeException(e);
            }
        }
        visualizers();
        game.turn();
        game.setNextPlayer();


        //second player
        cell = new Cell();
        cell.setRow(1);
        cell.setColumn(3);
        cell1 = new Cell();
        cell1.setRow(1);
        cell1.setColumn(4);
        Cell cell2 = new Cell();
        cell2.setRow(1);
        cell2.setColumn(5);
        move.clear();
        move.add(cell);
        move.add(cell1);
        move.add(cell2);
        tiles.clear();
        tile = new Tile(2, game.getGameBoard().getBoard()[1][3].getColor());
        tile1 = new Tile(3, game.getGameBoard().getBoard()[1][4].getColor());
        Tile tile2 = new Tile(4, game.getGameBoard().getBoard()[1][5].getColor());

        tiles.add(tile);
        tiles.add(tile1);
        tiles.add(tile2);
        if (game.getGameBoard().checkLegalChoice(move)) {
            game.getGameBoard().updateBoard(move);
            try {
                game.getCurrentPlayer().getShelfie().insertTile(tiles, 0);
            } catch (IllegalInsertException e) {
                throw new RuntimeException(e);
            }
        }
        visualizers();
        game.turn();
        game.setNextPlayer();

        //third player
        cell = new Cell();
        cell.setRow(5);
        cell.setColumn(0);
        move.clear();
        move.add(cell);
        tiles.clear();
        tile = new Tile(5, game.getGameBoard().getBoard()[5][0].getColor());
        tiles.add(tile);
        if (game.getGameBoard().checkLegalChoice(move)) {
            game.getGameBoard().updateBoard(move);
            try {
                game.getCurrentPlayer().getShelfie().insertTile(tiles, 0);
            } catch (IllegalInsertException e) {
                throw new RuntimeException(e);
            }
        }
        visualizers();
        game.turn();
        game.setNextPlayer();

        //fourth player
        cell = new Cell();
        cell.setRow(8);
        cell.setColumn(5);
        move.clear();
        move.add(cell);
        tiles.clear();
        tile = new Tile(5, game.getGameBoard().getBoard()[8][5].getColor());
        tiles.add(tile);
        if (game.getGameBoard().checkLegalChoice(move)) {
            game.getGameBoard().updateBoard(move);
            try {
                game.getCurrentPlayer().getShelfie().insertTile(tiles, 0);
            } catch (IllegalInsertException e) {
                throw new RuntimeException(e);
            }
        }
        visualizers();
        game.turn();
        game.setNextPlayer();

        //2d turn - first player
        cell = new Cell();
        cell.setRow(4);
        cell.setColumn(8);
        move.clear();
        move.add(cell);
        tiles.clear();
        tile = new Tile(7, game.getGameBoard().getBoard()[4][8].getColor());
        tiles.add(tile);
        if (game.getGameBoard().checkLegalChoice(move)) {
            game.getGameBoard().updateBoard(move);
            try {
                game.getCurrentPlayer().getShelfie().insertTile(tiles, 0);
            } catch (IllegalInsertException e) {
                throw new RuntimeException(e);
            }
        }
        visualizers();
        game.turn();
        game.setNextPlayer();


        //2d turn - second player
        cell = new Cell();
        cell.setRow(2);
        cell.setColumn(2);
        cell1 = new Cell();
        cell1.setRow(2);
        cell1.setColumn(3);
        cell2 = new Cell();
        cell2.setRow(2);
        cell2.setColumn(4);
        move.clear();
        move.add(cell);
        move.add(cell1);
        move.add(cell2);
        tiles.clear();
        tile = new Tile(8, game.getGameBoard().getBoard()[2][2].getColor());
        tile1 = new Tile(9, game.getGameBoard().getBoard()[2][3].getColor());
        tile2 = new Tile(10, game.getGameBoard().getBoard()[2][4].getColor());
        tiles.add(tile);
        tiles.add(tile1);
        tiles.add(tile2);
        if (game.getGameBoard().checkLegalChoice(move)) {
            game.getGameBoard().updateBoard(move);
            try {
                game.getCurrentPlayer().getShelfie().insertTile(tiles, 0);
            } catch (IllegalInsertException e) {
                throw new RuntimeException(e);
            }
        }
        visualizers();
        game.turn();
        game.setNextPlayer();

        //2d turn - third player
        cell = new Cell();
        cell.setRow(2);
        cell.setColumn(5);
        cell1 = new Cell();
        cell1.setRow(2);
        cell1.setColumn(6);
        move.clear();
        move.add(cell);
        move.add(cell1);
        tiles.clear();
        tile = new Tile(11, game.getGameBoard().getBoard()[2][5].getColor());
        tile1 = new Tile(12, game.getGameBoard().getBoard()[2][6].getColor());
        tiles.add(tile);
        tiles.add(tile1);
        if (game.getGameBoard().checkLegalChoice(move)) {
            game.getGameBoard().updateBoard(move);
            try {
                game.getCurrentPlayer().getShelfie().insertTile(tiles, 1);
            } catch (IllegalInsertException e) {
                throw new RuntimeException(e);
            }
        }
        visualizers();
        game.turn();
        game.setNextPlayer();

        //2d turn - fourth player
        cell = new Cell();
        cell.setRow(3);
        cell.setColumn(1);
        cell1 = new Cell();
        cell1.setRow(3);
        cell1.setColumn(2);
        move.clear();
        move.add(cell);
        move.add(cell1);
        tiles.clear();
        tile = new Tile(13, game.getGameBoard().getBoard()[3][1].getColor());
        tile1 = new Tile(14, game.getGameBoard().getBoard()[3][2].getColor());
        tiles.add(tile);
        tiles.add(tile1);
        if (game.getGameBoard().checkLegalChoice(move)) {
            game.getGameBoard().updateBoard(move);
            try {
                game.getCurrentPlayer().getShelfie().insertTile(tiles, 1);
            } catch (IllegalInsertException e) {
                throw new RuntimeException(e);
            }
        }
        visualizers();
        game.turn();
        game.setNextPlayer();

        //3d turn - first player
        cell = new Cell();
        cell.setRow(3);
        cell.setColumn(3);
        cell1 = new Cell();
        cell1.setRow(3);
        cell1.setColumn(4);
        move.clear();
        move.add(cell);
        move.add(cell1);
        tiles.clear();
        tile = new Tile(11, game.getGameBoard().getBoard()[3][3].getColor());
        tile1 = new Tile(12, game.getGameBoard().getBoard()[3][4].getColor());
        tiles.add(tile);
        tiles.add(tile1);
        if (game.getGameBoard().checkLegalChoice(move)) {
            game.getGameBoard().updateBoard(move);
            try {
                game.getCurrentPlayer().getShelfie().insertTile(tiles, 1);
            } catch (IllegalInsertException e) {
                throw new RuntimeException(e);
            }
        }
        visualizers();
        game.turn();
        game.setNextPlayer();

        //3d turn - second player
        cell = new Cell();
        cell.setRow(3);
        cell.setColumn(5);
        cell1 = new Cell();
        cell1.setRow(3);
        cell1.setColumn(6);
        cell2 = new Cell();
        cell2.setRow(3);
        cell2.setColumn(7);
        move.clear();
        move.add(cell);
        move.add(cell1);
        move.add(cell2);
        tiles.clear();
        tile = new Tile(11, game.getGameBoard().getBoard()[3][5].getColor());
        tile1 = new Tile(12, game.getGameBoard().getBoard()[3][6].getColor());
        tile2 = new Tile(12, game.getGameBoard().getBoard()[3][7].getColor());
        tiles.add(tile);
        tiles.add(tile1);
        tiles.add(tile2);
        if (game.getGameBoard().checkLegalChoice(move)) {
            game.getGameBoard().updateBoard(move);
            try {
                game.getCurrentPlayer().getShelfie().insertTile(tiles, 1);
            } catch (IllegalInsertException e) {
                throw new RuntimeException(e);
            }
        }
        visualizers();
        game.turn();
        game.setNextPlayer();

        //3d turn - third player
        cell = new Cell();
        cell.setRow(4);
        cell.setColumn(0);
        cell1 = new Cell();
        cell1.setRow(4);
        cell1.setColumn(1);
        cell2 = new Cell();
        cell2.setRow(4);
        cell2.setColumn(2);
        move.clear();
        move.add(cell);
        move.add(cell1);
        move.add(cell2);
        tiles.clear();
        tile = new Tile(11, game.getGameBoard().getBoard()[4][0].getColor());
        tile1 = new Tile(12, game.getGameBoard().getBoard()[4][1].getColor());
        tile2 = new Tile(12, game.getGameBoard().getBoard()[4][2].getColor());
        tiles.add(tile);
        tiles.add(tile1);
        tiles.add(tile2);
        if (game.getGameBoard().checkLegalChoice(move)) {
            game.getGameBoard().updateBoard(move);
            try {
                game.getCurrentPlayer().getShelfie().insertTile(tiles, 2);
            } catch (IllegalInsertException e) {
                throw new RuntimeException(e);
            }
        }
        visualizers();
        game.turn();
        game.setNextPlayer();

        //3d turn - fourth player
        cell = new Cell();
        cell.setRow(4);
        cell.setColumn(3);
        cell1 = new Cell();
        cell1.setRow(4);
        cell1.setColumn(4);
        move.clear();
        move.add(cell);
        move.add(cell1);
        tiles.clear();
        tile = new Tile(11, game.getGameBoard().getBoard()[4][3].getColor());
        tile1 = new Tile(12, game.getGameBoard().getBoard()[4][4].getColor());
        tiles.add(tile);
        tiles.add(tile1);
        if (game.getGameBoard().checkLegalChoice(move)) {
            game.getGameBoard().updateBoard(move);
            try {
                game.getCurrentPlayer().getShelfie().insertTile(tiles, 2);
            } catch (IllegalInsertException e) {
                throw new RuntimeException(e);
            }
        }
        visualizers();
        game.turn();
        game.setNextPlayer();

        //4th turn - first player
        cell = new Cell();
        cell.setRow(4);
        cell.setColumn(5);
        cell1 = new Cell();
        cell1.setRow(4);
        cell1.setColumn(6);
        move.clear();
        move.add(cell);
        move.add(cell1);
        tiles.clear();
        tile = new Tile(11, game.getGameBoard().getBoard()[4][5].getColor());
        tile1 = new Tile(12, game.getGameBoard().getBoard()[4][6].getColor());
        tiles.add(tile);
        tiles.add(tile1);
        if (game.getGameBoard().checkLegalChoice(move)) {
            game.getGameBoard().updateBoard(move);
            try {
                game.getCurrentPlayer().getShelfie().insertTile(tiles, 2);
            } catch (IllegalInsertException e) {
                throw new RuntimeException(e);
            }
        }
        visualizers();
        game.turn();
        game.setNextPlayer();


        //4th turn - first player
        cell = new Cell();
        cell.setRow(5);
        cell.setColumn(1);
        cell1 = new Cell();
        cell1.setRow(5);
        cell1.setColumn(2);
        move.clear();
        move.add(cell);
        move.add(cell1);
        tiles.clear();
        tile = new Tile(11, game.getGameBoard().getBoard()[5][1].getColor());
        tile1 = new Tile(12, game.getGameBoard().getBoard()[5][2].getColor());
        tiles.add(tile);
        tiles.add(tile1);
        if (game.getGameBoard().checkLegalChoice(move)) {
            game.getGameBoard().updateBoard(move);
            try {
                game.getCurrentPlayer().getShelfie().insertTile(tiles, 1);
            } catch (IllegalInsertException e) {
                throw new RuntimeException(e);
            }
        }
        visualizers();
        game.turn();


        //4th turn - first player
        cell = new Cell();
        cell.setRow(5);
        cell.setColumn(3);
        cell1 = new Cell();
        cell1.setRow(5);
        cell1.setColumn(4);
        move.clear();
        move.add(cell);
        move.add(cell1);
        tiles.clear();
        tile = new Tile(11, game.getGameBoard().getBoard()[5][3].getColor());
        tile1 = new Tile(12, game.getGameBoard().getBoard()[5][4].getColor());
        tiles.add(tile);
        tiles.add(tile1);
        if (game.getGameBoard().checkLegalChoice(move)) {
            game.getGameBoard().updateBoard(move);
            try {
                game.getCurrentPlayer().getShelfie().insertTile(tiles, 1);
                visualizers();
                game.turn();
                game.setNextPlayer();
            } catch (IllegalInsertException e) {
                game.getGameBoard().reinsertTiles(tiles, move);
                for (Cell cell3 : move) {
                    if (game.getGameBoard().getBoard()[cell3.getRow()][cell3.getColumn()] == null)
                        fail();
                }
            }
            if (game.getCurrentPlayer().getShelfie().getGrid()[5][1] != null)
                fail();
            tiles.clear();
            move.clear();
            cell = new Cell();
            cell.setRow(5);
            cell.setColumn(3);
            move.clear();
            move.add(cell);
            tiles.clear();
            //game.getCurrentPlayer().getShelfie().setGrid(5, 1, 11, game.getGameBoard().getBoard()[5][3].getColor());

            tile = new Tile(11, game.getGameBoard().getBoard()[5][3].getColor());
            tiles.add(tile);
            visualizers();
            game.turn();
            game.setNextPlayer();
        }

    }

    @Test
    public void endOgGameTest() {
        partialTest();
        ArrayList<Cell> move = new ArrayList<>();
        ArrayList<Tile> tiles = new ArrayList<>();
        game.beginGame();

        game.setCurrentPlayer(game.getFirstPlayer());
        game.setCurrentPlayer(game.getNextPlayer());
        tiles.clear();
        Tile tile = new Tile(10, game.getGameBoard().getBoard()[3][1].getColor());
        Tile tile1 = new Tile(11, game.getGameBoard().getBoard()[3][2].getColor());
        Tile tile2 = new Tile(12, game.getGameBoard().getBoard()[3][3].getColor());
        Tile tile3 = new Tile(13, game.getGameBoard().getBoard()[2][5].getColor());
        Tile tile4 = new Tile(14, game.getGameBoard().getBoard()[2][6].getColor());
        Tile tile5 = new Tile(15, game.getGameBoard().getBoard()[4][0].getColor());

        tiles.add(tile);
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        tiles.add(tile4);
        tiles.add(tile5);
        //game.getGameBoard().updateBoard(move);
        game.getCurrentPlayer().getShelfie().insertTileForTesting(tiles, 1);

        tiles.clear();
        tile = new Tile(16, game.getGameBoard().getBoard()[4][1].getColor());
        tile1 = new Tile(17, game.getGameBoard().getBoard()[4][2].getColor());
        tile2 = new Tile(18, game.getGameBoard().getBoard()[4][3].getColor());
        tile3 = new Tile(19, game.getGameBoard().getBoard()[4][5].getColor());
        tile4 = new Tile(20, game.getGameBoard().getBoard()[4][6].getColor());
        tile5 = new Tile(21, game.getGameBoard().getBoard()[4][7].getColor());

        tiles.add(tile);
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        tiles.add(tile4);
        tiles.add(tile5);
        //game.getGameBoard().updateBoard(move);
        game.getCurrentPlayer().getShelfie().insertTileForTesting(tiles, 2);

        tiles.clear();
        tile = new Tile(16, game.getGameBoard().getBoard()[5][1].getColor());
        tile1 = new Tile(17, game.getGameBoard().getBoard()[5][2].getColor());
        tile2 = new Tile(18, game.getGameBoard().getBoard()[5][3].getColor());
        tile3 = new Tile(19, game.getGameBoard().getBoard()[5][5].getColor());
        tile4 = new Tile(20, game.getGameBoard().getBoard()[5][6].getColor());
        tile5 = new Tile(21, game.getGameBoard().getBoard()[5][7].getColor());

        tiles.add(tile);
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        tiles.add(tile4);
        tiles.add(tile5);
        //game.getGameBoard().updateBoard(move);
        game.getCurrentPlayer().getShelfie().insertTileForTesting(tiles, 3);

        tiles.clear();
        tile = new Tile(16, game.getGameBoard().getBoard()[8][4].getColor());
        tile1 = new Tile(17, game.getGameBoard().getBoard()[6][2].getColor());
        tile2 = new Tile(18, game.getGameBoard().getBoard()[6][3].getColor());
        tile3 = new Tile(19, game.getGameBoard().getBoard()[6][5].getColor());
        tile4 = new Tile(20, game.getGameBoard().getBoard()[6][6].getColor());
        tile5 = new Tile(21, game.getGameBoard().getBoard()[7][4].getColor());

        tiles.add(tile);
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        tiles.add(tile4);
        tiles.add(tile5);
        //game.getGameBoard().updateBoard(move);
        game.getCurrentPlayer().getShelfie().insertTileForTesting(tiles, 4);
        shelfieVisualizer(game.getCurrentPlayer().getShelfie().getGrid());

        game.turn();

        if (game.checkEndGame()) {
            game.getCurrentPlayer().updateScore(game.getEndGameBadge().getScore());
            //game.lastTurn();
            if (!(game.getCurrentPlayer().isFirst()))//current player is the one that has a full shelfie
                game.setCurrentPlayer(game.getNextPlayer());
            if (!(game.getCurrentPlayer().isFirst())) {
                tile = new Tile(22, game.getGameBoard().getBoard()[4][7].getColor());
                tiles.clear();
                tiles.add(tile);
                Cell cell = new Cell();
                cell.setRow(4);
                cell.setColumn(7);
                move = new ArrayList<>();
                move.add(cell);
                game.getGameBoard().updateBoard(move);
                try {
                    if (game.getGameBoard().checkLegalChoice(move))
                        game.getCurrentPlayer().getShelfie().insertTile(tiles, 2);
                } catch (IllegalInsertException e) {
                    throw new RuntimeException(e);
                }
                game.turn();
            }
            visualizers();
            game.setCurrentPlayer(game.getNextPlayer());
            if (!(game.getCurrentPlayer().isFirst())) {
                tile = new Tile(23, game.getGameBoard().getBoard()[5][7].getColor());
                tiles.clear();
                tiles.add(tile);
                Cell cell = new Cell();
                cell.setRow(5);
                cell.setColumn(7);
                move = new ArrayList<>();
                move.add(cell);
                game.getGameBoard().updateBoard(move);
                try {
                    if (game.getGameBoard().checkLegalChoice(move))
                        game.getCurrentPlayer().getShelfie().insertTile(tiles, 2);
                } catch (IllegalInsertException e) {
                    throw new RuntimeException(e);
                }
                game.turn();
            }
            visualizers();
            game.calculateScore();
            game.selectWinner();
            //end of lastTurn
        }

    }


    private void boardVisualizer(Tile[][] board, ArrayList<Cell> notAvailable) {
        Tile.TileColor tileColor;
        boolean notAv;

        for (int i = 0; i < boardRows; i++) { //loop for rows
            for (int j = 0; j < boardColumns; j++) {
                notAv = false;
                for (Cell cell1 : notAvailable) { //if not Available
                    if (cell1.getRow() == i && cell1.getColumn() == j) {
                        notAv = true;
                        break;
                    }
                }
                if (notAv) {

                } else {
                    if (board[i][j] != null) {
                        tileColor = board[i][j].getColor();
                        assertTrue(tileColor != null);
                    } else {
                    }
                }
            }
        }
    }

    private void shelfieVisualizer(Tile[][] shelfie) {
        Tile.TileColor tileColor;
        if (shelfie != null) {
            for (int i = shelfieRows - 1; i >= 0; i--) {
                for (int j = 0; j < shelfieColumns; j++) {
                    if (shelfie[i][j] != null) {
                        tileColor = shelfie[i][j].getColor();
                        assertTrue(tileColor != null);
                    } else {
                    }
                }
            }

        }
    }

    private void visualizers() {
        boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        shelfieVisualizer(game.getPlayers().get(0).getShelfie().getGrid());
        shelfieVisualizer(game.getPlayers().get(1).getShelfie().getGrid());
        if(game.getPlayers().size()>2)
            shelfieVisualizer(game.getPlayers().get(2).getShelfie().getGrid());
        if(game.getPlayers().size()>3)
            shelfieVisualizer(game.getPlayers().get(3).getShelfie().getGrid());
    }


    @Test
    public void twoPlayersTest() {
        Game game = new Game();
        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<Cell> move = new ArrayList<>();
        ArrayList<Tile> tiles = new ArrayList<>();
        nameList.add("Alice");
        nameList.add("Andrea");
        game.beginGame(nameList);

        CLI cli = new CLI();
        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());

        Cell cell = new Cell();
        cell.setRow(1);
        cell.setColumn(3);
        Cell cell1 = new Cell();
        cell1.setRow(1);
        cell1.setColumn(4);
        move.clear();
        move.add(cell);
        move.add(cell1);
        tiles.clear();
        Tile tile = new Tile(1, game.getGameBoard().getBoard()[1][3].getColor());
        Tile tile1 = new Tile(2, game.getGameBoard().getBoard()[1][4].getColor());
        tiles.add(tile);
        tiles.add(tile1);
        if (game.getGameBoard().checkLegalChoice(move)) {
            game.getGameBoard().updateBoard(move);
            try {
                game.getCurrentPlayer().getShelfie().insertTile(tiles, 3);
            } catch (IllegalInsertException e) {
                throw new RuntimeException(e);
            }
        }
        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        cli.shelfieVisualizer(game.getCurrentPlayer().getShelfie().getGrid());
        game.turn();
        game.setNextPlayer();

        cell = new Cell();
        cell.setRow(2);
        cell.setColumn(3);
        cell1 = new Cell();
        cell1.setRow(2);
        cell1.setColumn(4);
        Cell cell2 = new Cell();
        cell2.setRow(2);
        cell2.setColumn(5);
        move.clear();
        move.add(cell);
        move.add(cell1);
        move.add(cell2);
        tiles.clear();
        tile = new Tile(3, game.getGameBoard().getBoard()[2][3].getColor());
        tile1 = new Tile(4, game.getGameBoard().getBoard()[2][4].getColor());
        Tile tile2 = new Tile(5, game.getGameBoard().getBoard()[2][5].getColor());
        tiles.add(tile);
        tiles.add(tile1);
        tiles.add(tile2);
        if (game.getGameBoard().checkLegalChoice(move)) {
            game.getGameBoard().updateBoard(move);
            try {
                game.getCurrentPlayer().getShelfie().insertTile(tiles, 3);
            } catch (IllegalInsertException e) {
                throw new RuntimeException(e);
            }
        }
        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        cli.shelfieVisualizer(game.getCurrentPlayer().getShelfie().getGrid());
        game.turn();
        game.setNextPlayer();

        cell = new Cell();
        cell.setRow(3);
        cell.setColumn(2);
        cell1 = new Cell();
        cell1.setRow(3);
        cell1.setColumn(3);
        cell2 = new Cell();
        cell2.setRow(3);
        cell2.setColumn(4);
        move.clear();
        move.add(cell);
        move.add(cell1);
        move.add(cell2);
        tiles.clear();
        tile = new Tile(6, game.getGameBoard().getBoard()[3][2].getColor());
        tile1 = new Tile(7, game.getGameBoard().getBoard()[3][3].getColor());
        tile2 = new Tile(8, game.getGameBoard().getBoard()[3][4].getColor());
        tiles.add(tile);
        tiles.add(tile1);
        tiles.add(tile2);
        if (game.getGameBoard().checkLegalChoice(move)) {
            game.getGameBoard().updateBoard(move);
            try {
                game.getCurrentPlayer().getShelfie().insertTile(tiles, 2);
            } catch (IllegalInsertException e) {
                throw new RuntimeException(e);
            }
        }
        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        cli.shelfieVisualizer(game.getCurrentPlayer().getShelfie().getGrid());
        game.turn();
        game.setNextPlayer();

        cell = new Cell();
        cell.setRow(3);
        cell.setColumn(5);
        cell1 = new Cell();
        cell1.setRow(3);
        cell1.setColumn(6);
        cell2 = new Cell();
        cell2.setRow(3);
        cell2.setColumn(7);
        move.clear();
        move.add(cell);
        move.add(cell1);
        move.add(cell2);
        tiles.clear();
        tile = new Tile(6, game.getGameBoard().getBoard()[3][5].getColor());
        tile1 = new Tile(7, game.getGameBoard().getBoard()[3][6].getColor());
        tile2 = new Tile(8, game.getGameBoard().getBoard()[3][7].getColor());
        tiles.add(tile);
        tiles.add(tile1);
        tiles.add(tile2);
        if (game.getGameBoard().checkLegalChoice(move)) {
            game.getGameBoard().updateBoard(move);
            try {
                game.getCurrentPlayer().getShelfie().insertTile(tiles, 2);
            } catch (IllegalInsertException e) {
                throw new RuntimeException(e);
            }
        }
        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        cli.shelfieVisualizer(game.getCurrentPlayer().getShelfie().getGrid());
        game.turn();
        game.setNextPlayer();

        cell = new Cell();
        cell.setRow(4);
        cell.setColumn(5);
        cell1 = new Cell();
        cell1.setRow(4);
        cell1.setColumn(6);
        cell2 = new Cell();
        cell2.setRow(4);
        cell2.setColumn(7);
        move.clear();
        move.add(cell);
        move.add(cell1);
        move.add(cell2);
        tiles.clear();
        tile = new Tile(9, game.getGameBoard().getBoard()[4][5].getColor());
        tile1 = new Tile(10, game.getGameBoard().getBoard()[4][6].getColor());
        tile2 = new Tile(11, game.getGameBoard().getBoard()[4][7].getColor());
        tiles.add(tile);
        tiles.add(tile1);
        tiles.add(tile2);
        if (game.getGameBoard().updateBoard(move) == false)//it controls if the choice il legal and if so it removes them
            fail();
        try {
            game.getCurrentPlayer().getShelfie().insertTile(tiles, 1);
        } catch (IllegalInsertException e) {
            game.getGameBoard().reinsertTiles(tiles, move);
        }
        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        cli.shelfieVisualizer(game.getCurrentPlayer().getShelfie().getGrid());
        game.turn();
        game.setNextPlayer();


        cell = new Cell();
        cell.setRow(4);
        cell.setColumn(1);
        cell1 = new Cell();
        cell1.setRow(4);
        cell1.setColumn(2);
        cell2 = new Cell();
        cell2.setRow(4);
        cell2.setColumn(3);
        move.clear();
        move.add(cell);
        move.add(cell1);
        move.add(cell2);
        tiles.clear();
        tile = new Tile(12, game.getGameBoard().getBoard()[4][1].getColor());
        tile1 = new Tile(13, game.getGameBoard().getBoard()[4][2].getColor());
        tile2 = new Tile(14, game.getGameBoard().getBoard()[4][3].getColor());
        tiles.add(tile);
        tiles.add(tile1);
        tiles.add(tile2);
        if (game.getGameBoard().updateBoard(move) == false)//it controls if the choice il legal and if so it removes them
            fail();
        try {
            game.getCurrentPlayer().getShelfie().insertTile(tiles, 1);
        } catch (IllegalInsertException e) {
            for (Cell temp : move) {
                game.getCurrentPlayer().getShelfie().setGridAtNull(temp.getRow(), temp.getColumn());
            }
            game.getGameBoard().reinsertTiles(tiles, move);
            tiles.clear();
            move.clear();
        }
        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        cli.shelfieVisualizer(game.getCurrentPlayer().getShelfie().getGrid());
        game.turn();
        game.setNextPlayer();


        cell = new Cell();
        cell.setRow(5);
        cell.setColumn(1);
        cell1 = new Cell();
        cell1.setRow(5);
        cell1.setColumn(2);
        cell2 = new Cell();
        cell2.setRow(5);
        cell2.setColumn(3);
        move.clear();
        move.add(cell);
        move.add(cell1);
        move.add(cell2);
        tiles.clear();
        tile = new Tile(15, game.getGameBoard().getBoard()[5][1].getColor());
        tile1 = new Tile(16, game.getGameBoard().getBoard()[5][2].getColor());
        tile2 = new Tile(17, game.getGameBoard().getBoard()[5][3].getColor());
        tiles.add(tile);
        tiles.add(tile1);
        tiles.add(tile2);
        if (game.getGameBoard().updateBoard(move) == false)//it controls if the choice il legal and if so it removes them
            fail();
        try {
            game.getCurrentPlayer().getShelfie().insertTile(tiles, 0);
        } catch (IllegalInsertException e) {
            for (Cell temp : move) {
                game.getCurrentPlayer().getShelfie().setGridAtNull(temp.getRow(), temp.getColumn());
            }
            game.getGameBoard().reinsertTiles(tiles, move);
            tiles.clear();
            move.clear();
        }
        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        cli.shelfieVisualizer(game.getCurrentPlayer().getShelfie().getGrid());
        game.turn();
        game.setNextPlayer();

        cell = new Cell();
        cell.setRow(4);
        cell.setColumn(4);
        cell1 = new Cell();
        cell1.setRow(5);
        cell1.setColumn(4);
        move.clear();
        move.add(cell);
        move.add(cell1);
        tiles.clear();
        tile = new Tile(17, game.getGameBoard().getBoard()[4][4].getColor());
        tile1 = new Tile(18, game.getGameBoard().getBoard()[5][4].getColor());
        tiles.add(tile);
        tiles.add(tile1);
        if (game.getGameBoard().updateBoard(move) == false)//it controls if the choice il legal and if so it removes them
            fail();
        try {
            game.getCurrentPlayer().getShelfie().insertTile(tiles, 0);
        } catch (IllegalInsertException e) {
            for (Cell temp : move) {
                game.getCurrentPlayer().getShelfie().setGridAtNull(temp.getRow(), temp.getColumn());
            }
            game.getGameBoard().reinsertTiles(tiles, move);
            tiles.clear();
            move.clear();
        }
        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        cli.shelfieVisualizer(game.getCurrentPlayer().getShelfie().getGrid());
        game.turn();
        game.setNextPlayer();

        cell = new Cell();
        cell.setRow(5);
        cell.setColumn(5);
        cell1 = new Cell();
        cell1.setRow(5);
        cell1.setColumn(6);
        move.clear();
        move.add(cell);
        move.add(cell1);
        tiles.clear();
        tile = new Tile(19, game.getGameBoard().getBoard()[5][5].getColor());
        tile1 = new Tile(20, game.getGameBoard().getBoard()[5][6].getColor());
        tiles.add(tile);
        tiles.add(tile1);
        if (game.getGameBoard().updateBoard(move) == false)//it controls if the choice il legal and if so it removes them
            fail();
        try {
            game.getCurrentPlayer().getShelfie().insertTile(tiles, 0);
        } catch (IllegalInsertException e) {
            for (Cell temp : move) {
                game.getCurrentPlayer().getShelfie().setGridAtNull(temp.getRow(), temp.getColumn());
            }
            game.getGameBoard().reinsertTiles(tiles, move);
            tiles.clear();
            move.clear();
        }
        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        cli.shelfieVisualizer(game.getCurrentPlayer().getShelfie().getGrid());
        game.turn();
        game.setNextPlayer();

        cell = new Cell();
        cell.setRow(6);
        cell.setColumn(3);
        cell1 = new Cell();
        cell1.setRow(6);
        cell1.setColumn(4);
        cell2 = new Cell();
        cell2.setRow(6);
        cell2.setColumn(5);
        move.clear();
        move.add(cell);
        move.add(cell1);
        move.add(cell2);
        tiles.clear();
        tile = new Tile(21, game.getGameBoard().getBoard()[6][3].getColor());
        tile1 = new Tile(22, game.getGameBoard().getBoard()[6][4].getColor());
        tile2 = new Tile(23, game.getGameBoard().getBoard()[6][5].getColor());
        tiles.add(tile);
        tiles.add(tile1);
        tiles.add(tile2);
        if (game.getGameBoard().updateBoard(move) == false)//it controls if the choice il legal and if so it removes them
            fail();
        try {
            game.getCurrentPlayer().getShelfie().insertTile(tiles, 0);
        } catch (IllegalInsertException e) {
            for (Cell temp : move) {
                game.getCurrentPlayer().getShelfie().setGridAtNull(temp.getRow(), temp.getColumn());
            }
            game.getGameBoard().reinsertTiles(tiles, move);
            tiles.clear();
            move.clear();
        }
        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        cli.shelfieVisualizer(game.getCurrentPlayer().getShelfie().getGrid());
        game.turn();
        game.setNextPlayer();

        cell = new Cell();
        cell.setRow(7);
        cell.setColumn(4);
        move.clear();
        move.add(cell);
        tiles.clear();
        tile = new Tile(23, game.getGameBoard().getBoard()[7][4].getColor());
        tiles.add(tile);
        if (game.getGameBoard().updateBoard(move) == false)//it controls if the choice il legal and if so it removes them
            fail();
        try {
            game.getCurrentPlayer().getShelfie().insertTile(tiles, 1);
        } catch (IllegalInsertException e) {
            for (Cell temp : move) {
                game.getCurrentPlayer().getShelfie().setGridAtNull(temp.getRow(), temp.getColumn());
            }
            game.getGameBoard().reinsertTiles(tiles, move);
            tiles.clear();
            move.clear();
        }
        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        cli.shelfieVisualizer(game.getCurrentPlayer().getShelfie().getGrid());
        game.turn();
        game.setNextPlayer();

        cell = new Cell();
        cell.setRow(1);
        cell.setColumn(3);
        cell1 = new Cell();
        cell1.setRow(1);
        cell1.setColumn(4);
        move.clear();
        move.add(cell);
        move.add(cell1);
        tiles.clear();
        tile = new Tile(24, game.getGameBoard().getBoard()[1][3].getColor());
        tile1 = new Tile(25, game.getGameBoard().getBoard()[1][4].getColor());
        tiles.add(tile);
        tiles.add(tile1);
        if (game.getGameBoard().updateBoard(move) == false)//it controls if the choice il legal and if so it removes them
            fail();
        try {
            game.getCurrentPlayer().getShelfie().insertTile(tiles, 4);
        } catch (IllegalInsertException e) {
            for (Cell temp : move) {
                game.getCurrentPlayer().getShelfie().setGridAtNull(temp.getRow(), temp.getColumn());
            }
            game.getGameBoard().reinsertTiles(tiles, move);
            tiles.clear();
            move.clear();
        }
        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        cli.shelfieVisualizer(game.getCurrentPlayer().getShelfie().getGrid());
        game.turn();
        game.setNextPlayer();

        cell = new Cell();
        cell.setRow(2);
        cell.setColumn(3);
        move.clear();
        move.add(cell);
        tiles.clear();
        tile = new Tile(25, game.getGameBoard().getBoard()[2][3].getColor());
        tiles.add(tile);
        if (game.getGameBoard().updateBoard(move) == false)//it controls if the choice il legal and if so it removes them
            fail();
        try {
            game.getCurrentPlayer().getShelfie().insertTile(tiles, 4);
        } catch (IllegalInsertException e) {
            for (Cell temp : move) {
                game.getCurrentPlayer().getShelfie().setGridAtNull(temp.getRow(), temp.getColumn());
            }
            game.getGameBoard().reinsertTiles(tiles, move);
            tiles.clear();
            move.clear();
        }
        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        cli.shelfieVisualizer(game.getCurrentPlayer().getShelfie().getGrid());
        game.turn();
        game.setNextPlayer();

        cell = new Cell();
        cell.setRow(2);
        cell.setColumn(4);
        move.clear();
        move.add(cell);
        tiles.clear();
        tile = new Tile(21, game.getGameBoard().getBoard()[2][4].getColor());
        tiles.add(tile);
        if (game.getGameBoard().updateBoard(move) == false)//it controls if the choice il legal and if so it removes them
            fail();
        try {
            game.getCurrentPlayer().getShelfie().insertTile(tiles, 0);
        } catch (IllegalInsertException e) {
            for (Cell temp : move) {
                game.getCurrentPlayer().getShelfie().setGridAtNull(temp.getRow(), temp.getColumn());
            }
            game.getGameBoard().reinsertTiles(tiles, move);
            tiles.clear();
            move.clear();
        }
        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        cli.shelfieVisualizer(game.getCurrentPlayer().getShelfie().getGrid());
        game.turn();
        game.setNextPlayer();

        cell = new Cell();
        cell.setRow(3);
        cell.setColumn(2);
        cell1 = new Cell();
        cell1.setRow(3);
        cell1.setColumn(3);
        cell2 = new Cell();
        cell2.setRow(3);
        cell2.setColumn(4);
        move.clear();
        move.add(cell);
        move.add(cell1);
        move.add(cell2);
        tiles.clear();
        tile = new Tile(21, game.getGameBoard().getBoard()[3][2].getColor());
        tile1 = new Tile(22, game.getGameBoard().getBoard()[3][3].getColor());
        tile2 = new Tile(23, game.getGameBoard().getBoard()[3][4].getColor());
        tiles.add(tile);
        tiles.add(tile1);
        tiles.add(tile2);
        if (game.getGameBoard().updateBoard(move) == false)//it controls if the choice il legal and if so it removes them
            fail();
        try {
            game.getCurrentPlayer().getShelfie().insertTile(tiles, 4);
        } catch (IllegalInsertException e) {
            for (Cell temp : move) {
                game.getCurrentPlayer().getShelfie().setGridAtNull(temp.getRow(), temp.getColumn());
            }
            game.getGameBoard().reinsertTiles(tiles, move);
            tiles.clear();
            move.clear();
        }
        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        cli.shelfieVisualizer(game.getCurrentPlayer().getShelfie().getGrid());
        game.turn();
        game.setNextPlayer();

        cell = new Cell();
        cell.setRow(2);
        cell.setColumn(5);
        move.clear();
        move.add(cell);
        tiles.clear();
        tile = new Tile(21, game.getGameBoard().getBoard()[2][5].getColor());
        tiles.add(tile);
        if (game.getGameBoard().updateBoard(move) == false)//it controls if the choice il legal and if so it removes them
            fail();
        try {
            game.getCurrentPlayer().getShelfie().insertTile(tiles, 2);
        } catch (IllegalInsertException e) {
            for (Cell temp : move) {
                game.getCurrentPlayer().getShelfie().setGridAtNull(temp.getRow(), temp.getColumn());
            }
            game.getGameBoard().reinsertTiles(tiles, move);
            tiles.clear();
            move.clear();
        }
        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        cli.shelfieVisualizer(game.getCurrentPlayer().getShelfie().getGrid());
        game.turn();
        game.setNextPlayer();

        cell = new Cell();
        cell.setRow(3);
        cell.setColumn(5);
        cell1 = new Cell();
        cell1.setRow(3);
        cell1.setColumn(6);
        cell2 = new Cell();
        cell2.setRow(3);
        cell2.setColumn(7);
        move.clear();
        move.add(cell);
        move.add(cell1);
        move.add(cell2);
        tiles.clear();
        tile = new Tile(21, game.getGameBoard().getBoard()[3][5].getColor());
        tile1 = new Tile(22, game.getGameBoard().getBoard()[3][6].getColor());
        tile2 = new Tile(23, game.getGameBoard().getBoard()[3][7].getColor());
        tiles.add(tile);
        tiles.add(tile1);
        tiles.add(tile2);
        if (game.getGameBoard().updateBoard(move) == false)//it controls if the choice il legal and if so it removes them
            fail();
        try {
            game.getCurrentPlayer().getShelfie().insertTile(tiles, 2);
        } catch (IllegalInsertException e) {
            for (Cell temp : move) {
                game.getCurrentPlayer().getShelfie().setGridAtNull(temp.getRow(), temp.getColumn());
            }
            game.getGameBoard().reinsertTiles(tiles, move);
            tiles.clear();
            move.clear();
        }
        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        cli.shelfieVisualizer(game.getCurrentPlayer().getShelfie().getGrid());
        game.turn();
        game.setNextPlayer();


        cell = new Cell();
        cell.setRow(7);
        cell.setColumn(4);
        cell1 = new Cell();
        cell1.setRow(7);
        cell1.setColumn(5);
        move.clear();
        move.add(cell);
        tiles.clear();
        tile = new Tile(21, game.getGameBoard().getBoard()[6][3].getColor());
        tile1 = new Tile(22, game.getGameBoard().getBoard()[6][4].getColor());
        tiles.add(tile);
        tiles.add(tile1);
        if (game.getGameBoard().updateBoard(move) == false)//it controls if the choice il legal and if so it removes them
            fail();
        try {
            game.getCurrentPlayer().getShelfie().insertTile(tiles, 1);
        } catch (IllegalInsertException e) {
            for (Cell temp : move) {
                game.getCurrentPlayer().getShelfie().setGridAtNull(temp.getRow(), temp.getColumn());
            }
            game.getGameBoard().reinsertTiles(tiles, move);
            tiles.clear();
            move.clear();
        }
        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        cli.shelfieVisualizer(game.getCurrentPlayer().getShelfie().getGrid());
        game.turn();
        game.setNextPlayer();

        cell = new Cell();
        cell.setRow(4);
        cell.setColumn(1);
        cell1 = new Cell();
        cell1.setRow(5);
        cell1.setColumn(1);
        move.clear();
        move.add(cell);
        tiles.clear();
        tile = new Tile(21, game.getGameBoard().getBoard()[4][1].getColor());
        tile1 = new Tile(22, game.getGameBoard().getBoard()[5][1].getColor());
        tiles.add(tile);
        tiles.add(tile1);
        if (game.getGameBoard().updateBoard(move) == false)//it controls if the choice il legal and if so it removes them
            fail();
        try {
            game.getCurrentPlayer().getShelfie().insertTile(tiles, 1);
        } catch (IllegalInsertException e) {
            for (Cell temp : move) {
                game.getCurrentPlayer().getShelfie().setGridAtNull(temp.getRow(), temp.getColumn());
            }
            game.getGameBoard().reinsertTiles(tiles, move);
            tiles.clear();
            move.clear();
        }
        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        cli.shelfieVisualizer(game.getCurrentPlayer().getShelfie().getGrid());
        game.turn();
        game.setNextPlayer();

        cell = new Cell();
        cell.setRow(4);
        cell.setColumn(5);
        cell1 = new Cell();
        cell1.setRow(4);
        cell1.setColumn(6);
        cell2 = new Cell();
        cell2.setRow(4);
        cell2.setColumn(7);
        move.clear();
        move.add(cell);
        move.add(cell1);
        move.add(cell2);
        tiles.clear();
        tile = new Tile(21, game.getGameBoard().getBoard()[4][5].getColor());
        tile1 = new Tile(22, game.getGameBoard().getBoard()[4][6].getColor());
        tile2 = new Tile(23, game.getGameBoard().getBoard()[4][7].getColor());
        tiles.add(tile);
        tiles.add(tile1);
        tiles.add(tile2);
        if (game.getGameBoard().updateBoard(move) == false)//it controls if the choice il legal and if so it removes them
            fail();
        try {
            game.getCurrentPlayer().getShelfie().insertTile(tiles, 3);
        } catch (IllegalInsertException e) {
            for (Cell temp : move) {
                game.getCurrentPlayer().getShelfie().setGridAtNull(temp.getRow(), temp.getColumn());
            }
            game.getGameBoard().reinsertTiles(tiles, move);
            tiles.clear();
            move.clear();
        }
        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        cli.shelfieVisualizer(game.getCurrentPlayer().getShelfie().getGrid());
        game.turn();
        game.setNextPlayer();

        cell = new Cell();
        cell.setRow(7);
        cell.setColumn(5);
        move.clear();
        move.add(cell);
        tiles.clear();
        tile = new Tile(21, game.getGameBoard().getBoard()[7][5].getColor());
        tiles.add(tile);
        if (game.getGameBoard().updateBoard(move) == false)//it controls if the choice il legal and if so it removes them
            fail();
        try {
            game.getCurrentPlayer().getShelfie().insertTile(tiles, 0);
        } catch (IllegalInsertException e) {
            for (Cell temp : move) {
                game.getCurrentPlayer().getShelfie().setGridAtNull(temp.getRow(), temp.getColumn());
            }
            game.getGameBoard().reinsertTiles(tiles, move);
            tiles.clear();
            move.clear();
        }
        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        cli.shelfieVisualizer(game.getCurrentPlayer().getShelfie().getGrid());
        game.turn();
        game.setNextPlayer();

        cell = new Cell();
        cell.setRow(6);
        cell.setColumn(3);
        cell1 = new Cell();
        cell1.setRow(6);
        cell1.setColumn(4);
        cell2 = new Cell();
        cell2.setRow(6);
        cell2.setColumn(5);
        move.clear();
        move.add(cell);
        move.add(cell1);
        move.add(cell2);
        tiles.clear();
        tile = new Tile(21, game.getGameBoard().getBoard()[6][3].getColor());
        tile1 = new Tile(22, game.getGameBoard().getBoard()[6][4].getColor());
        tile2 = new Tile(23, game.getGameBoard().getBoard()[6][5].getColor());
        tiles.add(tile);
        tiles.add(tile1);
        tiles.add(tile2);
        if (game.getGameBoard().updateBoard(move) == false)//it controls if the choice il legal and if so it removes them
            fail();
        try {
            game.getCurrentPlayer().getShelfie().insertTile(tiles, 4);
        } catch (IllegalInsertException e) {
            for (Cell temp : move) {
                game.getCurrentPlayer().getShelfie().setGridAtNull(temp.getRow(), temp.getColumn());
            }
            game.getGameBoard().reinsertTiles(tiles, move);
            tiles.clear();
            move.clear();
        }
        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        cli.shelfieVisualizer(game.getCurrentPlayer().getShelfie().getGrid());
        game.turn();
        game.setNextPlayer();

        cell = new Cell();
        cell.setRow(5);
        cell.setColumn(1);
        move.clear();
        move.add(cell);
        tiles.clear();
        tile = new Tile(21, game.getGameBoard().getBoard()[5][1].getColor());
        tiles.add(tile);
        if (game.getGameBoard().updateBoard(move) == false)//it controls if the choice il legal and if so it removes them
            fail();
        try {
            game.getCurrentPlayer().getShelfie().insertTile(tiles, 2);
        } catch (IllegalInsertException e) {
            for (Cell temp : move) {
                game.getCurrentPlayer().getShelfie().setGridAtNull(temp.getRow(), temp.getColumn());
            }
            game.getGameBoard().reinsertTiles(tiles, move);
            tiles.clear();
            move.clear();
        }
        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        cli.shelfieVisualizer(game.getCurrentPlayer().getShelfie().getGrid());
        game.turn();
        game.setNextPlayer();

        cell = new Cell();
        cell.setRow(5);
        cell.setColumn(2);
        move.clear();
        move.add(cell);
        tiles.clear();
        tile = new Tile(21, game.getGameBoard().getBoard()[5][2].getColor());
        tiles.add(tile);
        if (game.getGameBoard().updateBoard(move) == false)//it controls if the choice il legal and if so it removes them
            fail();
        try {
            game.getCurrentPlayer().getShelfie().insertTile(tiles, 0);
        } catch (IllegalInsertException e) {
            for (Cell temp : move) {
                game.getCurrentPlayer().getShelfie().setGridAtNull(temp.getRow(), temp.getColumn());
            }
            game.getGameBoard().reinsertTiles(tiles, move);
            tiles.clear();
            move.clear();
        }
        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        cli.shelfieVisualizer(game.getCurrentPlayer().getShelfie().getGrid());
        game.turn();
        game.setNextPlayer();

        cell = new Cell();
        cell.setRow(4);
        cell.setColumn(2);
        move.clear();
        move.add(cell);
        tiles.clear();
        tile = new Tile(21, game.getGameBoard().getBoard()[4][2].getColor());
        tiles.add(tile);
        if (game.getGameBoard().updateBoard(move) == false)//it controls if the choice il legal and if so it removes them
            fail();
        try {
            game.getCurrentPlayer().getShelfie().insertTile(tiles, 4);
        } catch (IllegalInsertException e) {
            for (Cell temp : move) {
                game.getCurrentPlayer().getShelfie().setGridAtNull(temp.getRow(), temp.getColumn());
            }
            game.getGameBoard().reinsertTiles(tiles, move);
            tiles.clear();
            move.clear();
        }
        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        cli.shelfieVisualizer(game.getCurrentPlayer().getShelfie().getGrid());
        game.turn();
        game.setNextPlayer();

        cell = new Cell();
        cell.setRow(4);
        cell.setColumn(3);
        move.clear();
        move.add(cell);
        tiles.clear();
        tile = new Tile(21, game.getGameBoard().getBoard()[4][3].getColor());
        tiles.add(tile);
        if (game.getGameBoard().updateBoard(move) == false)//it controls if the choice il legal and if so it removes them
            fail();
        try {
            game.getCurrentPlayer().getShelfie().insertTile(tiles, 4);
        } catch (IllegalInsertException e) {
            for (Cell temp : move) {
                game.getCurrentPlayer().getShelfie().setGridAtNull(temp.getRow(), temp.getColumn());
            }
            game.getGameBoard().reinsertTiles(tiles, move);
            tiles.clear();
            move.clear();
        }
        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        cli.shelfieVisualizer(game.getCurrentPlayer().getShelfie().getGrid());
        game.turn();
        game.setNextPlayer();

        cell = new Cell();
        cell.setRow(4);
        cell.setColumn(4);
        move.clear();
        move.add(cell);
        tiles.clear();
        tile = new Tile(21, game.getGameBoard().getBoard()[4][4].getColor());
        tiles.add(tile);
        if (game.getGameBoard().updateBoard(move) == false)//it controls if the choice il legal and if so it removes them
            fail();
        try {
            game.getCurrentPlayer().getShelfie().insertTile(tiles, 1);
        } catch (IllegalInsertException e) {
            for (Cell temp : move) {
                game.getCurrentPlayer().getShelfie().setGridAtNull(temp.getRow(), temp.getColumn());
            }
            game.getGameBoard().reinsertTiles(tiles, move);
            tiles.clear();
            move.clear();
        }
        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        cli.shelfieVisualizer(game.getCurrentPlayer().getShelfie().getGrid());
        game.turn();
        game.setNextPlayer();

        cell = new Cell();
        cell.setRow(5);
        cell.setColumn(1);
        move.clear();
        move.add(cell);
        tiles.clear();
        tile = new Tile(21, game.getGameBoard().getBoard()[5][1].getColor());
        tiles.add(tile);
        if (game.getGameBoard().updateBoard(move) == false)//it controls if the choice il legal and if so it removes them
            fail();
        try {
            game.getCurrentPlayer().getShelfie().insertTile(tiles, 1);
        } catch (IllegalInsertException e) {
            for (Cell temp : move) {
                game.getCurrentPlayer().getShelfie().setGridAtNull(temp.getRow(), temp.getColumn());
            }
            game.getGameBoard().reinsertTiles(tiles, move);
            tiles.clear();
            move.clear();
        }
        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        cli.shelfieVisualizer(game.getCurrentPlayer().getShelfie().getGrid());
        game.turn();
        game.setNextPlayer();

        cell = new Cell();
        cell.setRow(5);
        cell.setColumn(2);
        move.clear();
        move.add(cell);
        tiles.clear();
        tile = new Tile(21, game.getGameBoard().getBoard()[5][2].getColor());
        tiles.add(tile);
        if (game.getGameBoard().updateBoard(move) == false)//it controls if the choice il legal and if so it removes them
            fail();
        try {
            game.getCurrentPlayer().getShelfie().insertTile(tiles, 3);
        } catch (IllegalInsertException e) {
            for (Cell temp : move) {
                game.getCurrentPlayer().getShelfie().setGridAtNull(temp.getRow(), temp.getColumn());
            }
            game.getGameBoard().reinsertTiles(tiles, move);
            tiles.clear();
            move.clear();
        }
        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        cli.shelfieVisualizer(game.getCurrentPlayer().getShelfie().getGrid());
        game.turn();
        game.setNextPlayer();

        cell = new Cell();
        cell.setRow(5);
        cell.setColumn(5);
        cell1 = new Cell();
        cell1.setRow(5);
        cell1.setColumn(6);
        move.clear();
        move.add(cell);
        move.add(cell1);
        tiles.clear();
        tile = new Tile(21, game.getGameBoard().getBoard()[5][5].getColor());
        tile1 = new Tile(21, game.getGameBoard().getBoard()[5][6].getColor());
        tiles.add(tile);
        tiles.add(tile1);
        if (game.getGameBoard().updateBoard(move) == false)//it controls if the choice il legal and if so it removes them
            fail();
        try {
            game.getCurrentPlayer().getShelfie().insertTile(tiles, 2);
        } catch (IllegalInsertException e) {
            for (Cell temp : move) {
                game.getCurrentPlayer().getShelfie().setGridAtNull(temp.getRow(), temp.getColumn());
            }
            game.getGameBoard().reinsertTiles(tiles, move);
            tiles.clear();
            move.clear();
        }
        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        cli.shelfieVisualizer(game.getCurrentPlayer().getShelfie().getGrid());
        game.turn();
        if (game.checkEndGame()) {
            game.getCurrentPlayer().updateScore(game.getEndGameBadge().getScore());
            if (!(game.getNextPlayer().isFirst())) {//current player is the one that has a full shelfie
                game.setCurrentPlayer(game.getNextPlayer());
            } else {
                game.calculateScore();
                game.selectWinner();
                cli.scoreVisualizer(game.getPlayers());
                cli.personalCardVisualizer(game.getPlayers().get(0).getPersonalCard());
                cli.personalCardVisualizer(game.getPlayers().get(1).getPersonalCard());
                for (CommonCards c : game.getCommonCards()) {
                    cli.commonCardsVisualizer(c.getName());
                }
            }
        }

    }


    @Test
    public void test() {
        Game game = new Game();
        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<Cell> cells = new ArrayList<>();
        ArrayList<Tile> tiles = new ArrayList<>();
        nameList.add("Alice");
        nameList.add("Andrea");
        game.beginGame(nameList);
        CLI cli = new CLI();

        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());


        Cell cell = new Cell();
        cell.setRow(1);
        cell.setColumn(3);
        cells.add(cell);
        Cell cell1 = new Cell();
        cell1.setRow(1);
        cell1.setColumn(4);
        cells.add(cell1);
        int i = game.turn(cells, 0);
        if (i == -1)
            fail();
        if (i == 1) {
            assertTrue(i == 1);
        }
        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        cli.shelfieVisualizer(game.getNextPlayer().getShelfie().getGrid());


        cells.clear();
        cell = new Cell();
        cell.setRow(2);
        cell.setColumn(3);
        cells.add(cell);
        cell1 = new Cell();
        cell1.setRow(2);
        cell1.setColumn(4);
        cells.add(cell1);
        Cell cell2 = new Cell();
        cell2.setRow(2);
        cell2.setColumn(5);
        cells.add(cell2);
        i = game.turn(cells, 0);
        if (i == -1)
            fail();
        if (i == 1) {
            assertTrue(i == 1);
        }
        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        cli.shelfieVisualizer(game.getNextPlayer().getShelfie().getGrid());


        cells.clear();
        cell = new Cell();
        cell.setRow(3);
        cell.setColumn(2);
        cells.add(cell);
        cell1 = new Cell();
        cell1.setRow(3);
        cell1.setColumn(3);
        cells.add(cell1);
        cell2 = new Cell();
        cell2.setRow(3);
        cell2.setColumn(4);
        cells.add(cell2);
        i = game.turn(cells, 0);
        if (i == -1)
            fail();
        if (i == 1) {
            assertTrue(i == 1);
        }
        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        cli.shelfieVisualizer(game.getNextPlayer().getShelfie().getGrid());


        cells.clear();
        cell = new Cell();
        cell.setRow(3);
        cell.setColumn(5);
        cells.add(cell);
        cell1 = new Cell();
        cell1.setRow(3);
        cell1.setColumn(6);
        cells.add(cell1);
        cell2 = new Cell();
        cell2.setRow(3);
        cell2.setColumn(7);
        cells.add(cell2);
        i = game.turn(cells, 0);
        if (i == -1)
            fail();
        if (i == 1) {
            assertTrue(i == 1);
        }
        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        cli.shelfieVisualizer(game.getNextPlayer().getShelfie().getGrid());


        cells.clear();
        cell = new Cell();
        cell.setRow(4);
        cell.setColumn(1);
        cells.add(cell);
        cell1 = new Cell();
        cell1.setRow(4);
        cell1.setColumn(1);
        cells.add(cell1);
        cell2 = new Cell();
        cell2.setRow(4);
        cell2.setColumn(3);
        cells.add(cell2);
        i = game.turn(cells, 0);
        if (i == 1) {
            assertTrue(i == 1);
        }
        if (i == -1) {
            assertNull(game.getCurrentPlayer().getShelfie().getGrid()[5][0]);

            cli.shelfieVisualizer(game.getCurrentPlayer().getShelfie().getGrid());

            cells.clear();
            cell = new Cell();
            cell.setRow(4);
            cell.setColumn(1);
            cells.add(cell);
            i = game.turn(cells, 0);
            if (i == -1)
                fail();
            cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
            cli.shelfieVisualizer(game.getNextPlayer().getShelfie().getGrid());
        }
    }


    @Test
    public void test2() {
        Game game = new Game();
        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<Cell> cells = new ArrayList<>();
        ArrayList<Tile> tiles = new ArrayList<>();
        nameList.add("Alice");
        nameList.add("Andrea");
        game.beginGame(nameList);
        CLI cli = new CLI();

        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());


        Cell cell = new Cell();
        cell.setRow(1);
        cell.setColumn(3);
        cells.add(cell);
        Cell cell1 = new Cell();
        cell1.setRow(1);
        cell1.setColumn(4);
        cells.add(cell1);
        int i = game.turn(cells, 0);
        if (i == -1)
            fail();
        if (i == 1) {
            fail();
        }
        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        cli.shelfieVisualizer(game.getNextPlayer().getShelfie().getGrid());

        cells.clear();
        cell = new Cell();
        cell.setRow(2);
        cell.setColumn(3);
        cells.add(cell);
        cell1 = new Cell();
        cell1.setRow(2);
        cell1.setColumn(4);
        cells.add(cell1);
        Cell cell2 = new Cell();
        cell2.setRow(2);
        cell2.setColumn(5);
        cells.add(cell2);
        i = game.turn(cells, 0);
        if (i == -1)
            fail();
        if (i == 1) {
            fail();
        }
        cli.boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        cli.shelfieVisualizer(game.getNextPlayer().getShelfie().getGrid());

    }

    @Test
    public void overflowTest(){
        ArrayList<String> nameList = new ArrayList<>();
        nameList.add("Alice");
        nameList.add("Andrea");
        ArrayList<Cell> cells = new ArrayList<>();
        game.beginGame(nameList);
        visualizers();

        Cell cell1 = new Cell();
        cell1.setRow(1);
        cell1.setColumn(3);
        Cell cell2 = new Cell();
        cell2.setRow(1);
        cell2.setColumn(4);
        cells.add(cell1);
        cells.add(cell2);
        if(game.turn(cells,0) != 0){
            fail();
        }
        visualizers();
        cells.clear();

        cell1.setRow(2);
        cell1.setColumn(3);
        cell2.setRow(2);
        cell2.setColumn(4);
        Cell cell3 = new Cell();
        cell3.setRow(2);
        cell3.setColumn(5);
        cells.add(cell1);
        cells.add(cell2);
        cells.add(cell3);
        if(game.turn(cells,0) != 0){
            fail();
        }
        visualizers();
        cells.clear();

        cell1.setRow(3);
        cell1.setColumn(2);
        cell2.setRow(3);
        cell2.setColumn(3);
        cell3.setRow(3);
        cell3.setColumn(4);
        cells.add(cell1);
        cells.add(cell2);
        cells.add(cell3);
        if(game.turn(cells,0) != 0){
            fail();
        }
        visualizers();
        cells.clear();

        cell1.setRow(4);
        cell1.setColumn(1);
        cells.add(cell1);
        if(game.turn(cells,0) != 0){
            fail();
        }
        visualizers();
        cells.clear();

        cell1.setRow(3);
        cell1.setColumn(5);
        cell2.setRow(3);
        cell2.setColumn(6);
        cell3.setRow(3);
        cell3.setColumn(7);
        cells.add(cell1);
        cells.add(cell2);
        cells.add(cell3);
        if(game.turn(cells,0) != -1){
            fail();
        }
        assertTrue(game.getGameBoard().getBoard()[3][5] != null);
        assertTrue(game.getGameBoard().getBoard()[3][6] != null);
        assertTrue(game.getGameBoard().getBoard()[3][7] != null);
        assertTrue(game.getNextPlayer().getShelfie().getGrid()[5][0] == null);

        cells.clear();

        cell1.setRow(3);
        cell1.setColumn(5);
        cells.add(cell1);
        if(game.turn(cells,0) != 0){
            fail();
        }
        visualizers();

        cells.clear();
        cell1.setRow(3);
        cell1.setColumn(6);
        cells.add(cell1);
        if(game.turn(cells,1) != 0){
            fail();
        }
        visualizers();

        cells.clear();
        cell1.setRow(3);
        cell1.setColumn(7);
        cells.add(cell1);
        if(game.turn(cells,1) != 0){
            fail();
        }
        visualizers();

    }
}
