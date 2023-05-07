package it.polimi.softeng.model;

import it.polimi.softeng.client.view.CLI.CLI;
import it.polimi.softeng.customExceptions.IllegalInsertException;
import it.polimi.softeng.model.commonCards.CommonCards;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static it.polimi.softeng.Constants.*;

public class MatchTest {
    Game game = new Game();

    public void fullMatch(){
        game.createNewPlayer("Alice");
        game.createNewPlayer("Andrea");
        game.createNewPlayer("Daniel");
        game.createNewPlayer("Nicolas");

        game.beginGame();

        while(!(game.checkEndGame())){

            //mossa, update shelfie e board da inserire in turn

            game.turn(); //da continuare


            game.setNextPlayer();

        }
        game.getCurrentPlayer().updateScore(game.getEndGameBadge().getScore());
        game.lastTurn();
    }

    @Test
    public void match1(){
        fullMatch();
        CLI cli = new CLI();
        cli.scoreVisualizer(game.getPlayers());
        System.out.println("Il vincitore è " + game.getWinner().getNickname());
    }

    @Test
    public void partialTest(){

        game.createNewPlayer("Alice");
        game.createNewPlayer("Andrea");
        game.createNewPlayer("Daniel");
        game.createNewPlayer("Nicolas");

        game.beginGame();
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
        if(game.getGameBoard().checkLegalChoice(move)){
            game.getGameBoard().updateBoard(move);
            try{
                if(game.getCurrentPlayer().getShelfie().checkLegalInsert(tiles, 0))
                    game.getCurrentPlayer().getShelfie().insertTile(tiles, 0);
            }catch (IllegalInsertException e){
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
        if(game.getGameBoard().checkLegalChoice(move)){
            game.getGameBoard().updateBoard(move);
            try{
                game.getCurrentPlayer().getShelfie().insertTile(tiles, 0);
            }catch (IllegalInsertException e){
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
        if(game.getGameBoard().checkLegalChoice(move)){
            game.getGameBoard().updateBoard(move);
            try{
                game.getCurrentPlayer().getShelfie().insertTile(tiles, 0);
            }catch (IllegalInsertException e){
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
        if(game.getGameBoard().checkLegalChoice(move)){
            game.getGameBoard().updateBoard(move);
            try{
                game.getCurrentPlayer().getShelfie().insertTile(tiles, 0);
            }catch (IllegalInsertException e){
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
        if(game.getGameBoard().checkLegalChoice(move)){
            game.getGameBoard().updateBoard(move);
            try{
                game.getCurrentPlayer().getShelfie().insertTile(tiles, 0);
            }catch (IllegalInsertException e){
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
        if(game.getGameBoard().checkLegalChoice(move)){
            game.getGameBoard().updateBoard(move);
            try{
                game.getCurrentPlayer().getShelfie().insertTile(tiles, 0);
            }catch (IllegalInsertException e){
                throw new RuntimeException(e);
            }
        }
        visualizers();
        game.turn();
        game.setNextPlayer();


    }

    @Test
    public void endOgGameTest(){
        partialTest();
        ArrayList<Cell> move = new ArrayList<>();
        ArrayList<Tile> tiles = new ArrayList<>();

        /*Cell cell = new Cell();
        cell.setRow(3);
        cell.setColumn(1);
        Cell cell1 = new Cell();
        cell1.setRow(3);
        cell1.setColumn(2);
        Cell cell2 = new Cell();
        cell2.setRow(3);
        cell2.setColumn(3);
        move.clear();
        move.add(cell);
        move.add(cell1);
        move.add(cell2);*/
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
        System.out.println(game.getCurrentPlayer().getNickname());
        shelfieVisualizer(game.getCurrentPlayer().getShelfie().getGrid());

        game.turn();

        if(game.checkEndGame()){
            game.getCurrentPlayer().updateScore(game.getEndGameBadge().getScore());
            game.lastTurn();
            System.out.println("Il vincitore è " + game.getWinner().getNickname());

        }

    }


    private void boardVisualizer(Tile[][] board, ArrayList<Cell> notAvailable) {
        Tile.TileColor tileColor;
        boolean notAv;

        System.out.println(ANSI_RESET + "BOARD:");
        System.out.println(ANSI_GREY + "     0  1  2  3  4  5  6  7  8"); //print column index
        System.out.println("    ━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        for (int i = 0; i < boardRows; i++) { //loop for rows
            System.out.print(i + "  ┃"); //print row index
            for (int j = 0; j < boardColumns; j++) {
                notAv = false;
                for (Cell cell1 : notAvailable) { //if not Available
                    if (cell1.getRow() == i && cell1.getColumn() == j) {
                        notAv = true;
                        break;
                    }
                }
                if (notAv) {
                    System.out.print(ANSI_GREY + " ▇ ");
                } else {
                    if (board[i][j] != null) {
                        tileColor = board[i][j].getColor();
                        System.out.print(" " + tileColor.coloredText() + tileColor.colorLetter() + " ");
                    } else {
                        System.out.print(ANSI_GREY + " ░ ");
                    }
                }
            }
            System.out.print(ANSI_GREY + "┃\n");
        }
        System.out.println(ANSI_GREY + "    ━━━━━━━━━━━━━━━━━━━━━━━━━━━" + ANSI_RESET);
    }
    private void shelfieVisualizer(Tile[][] shelfie) {
        Tile.TileColor tileColor;
        if (shelfie != null) {
            System.out.println(ANSI_RESET + "SHELFIE:");
            System.out.println(ANSI_GREY + "    ━━━━━━━━━━━━━━━");
            for (int i = shelfieRows - 1; i >= 0; i--) {
                System.out.print(ANSI_GREY + i + "  ┃");
                for (int j = 0; j < shelfieColumns; j++) {
                    if (shelfie[i][j] != null) {
                        tileColor = shelfie[i][j].getColor();
                        System.out.print(tileColor.coloredText() + " " + tileColor.colorLetter() + " ");
                    } else {
                        System.out.print(ANSI_GREY + " ░ ");
                    }
                }
                System.out.println(ANSI_GREY + "┃");
            }
            System.out.println(ANSI_GREY + "    ━━━━━━━━━━━━━━━");
            System.out.println(ANSI_GREY + "     0  1  2  3  4" + Tile.TileColor.WHITE.coloredText());

        }
    }
    private void visualizers(){
        boardVisualizer(game.getGameBoard().getBoard(), game.getGameBoard().getNotAvailable());
        shelfieVisualizer(game.getPlayers().get(0).getShelfie().getGrid());
        shelfieVisualizer(game.getPlayers().get(1).getShelfie().getGrid());
        shelfieVisualizer(game.getPlayers().get(2).getShelfie().getGrid());
        shelfieVisualizer(game.getPlayers().get(3).getShelfie().getGrid());
    }

}
