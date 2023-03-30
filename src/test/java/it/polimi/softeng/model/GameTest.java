package it.polimi.softeng.model;

import org.junit.jupiter.api.Test;
import it.polimi.softeng.model.*;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    /**
     * testing for the right creation of colored tiles
     */
    @Test
    public void initializeTilesTest(){
        GameForTest game = new GameForTest();
        game.initializeTile();

        int whiteCount = 0;
        int blueCount = 0;
        int yellowCount = 0;
        int purpleCount = 0;
        int cyanCount = 0;
        int greenCount = 0;

        int i = 0;
        while(i < 132){
            switch(game.tileBag.get(i).getColor()){
                case WHITE -> whiteCount++;
                case BLUE -> blueCount++;
                case YELLOW -> yellowCount++;
                case PURPLE -> purpleCount++;
                case CYAN -> cyanCount++;
                case GREEN -> greenCount++;
            }
            i++;
        }

        assertEquals(132,game.tileBag.size());
        assertEquals(22, whiteCount);
        assertEquals(22, blueCount);
        assertEquals(22, yellowCount);
        assertEquals(22, purpleCount);
        assertEquals(22, cyanCount);
        assertEquals(22, greenCount);
    }

    /**
     * testing whether the commonCards are initialized and aren't the same
     */
    @Test
    public void chooseCommonCardsTest(){
        GameForTest game = new GameForTest();

        game.createNewPlayer("player1");
        game.createNewPlayer("player2");
        game.beginGame();
        assertEquals(2, game.commonCards.size());
        assertNotSame(game.commonCards.get(0), game.commonCards.get(1));
    }

    /**
     * testing for the right assignment of point badges
     */
    @Test
    public void chooseCommonCardsPointsTest(){
        GameForTest game = new GameForTest();

        game.createNewPlayer("player1");
        game.createNewPlayer("player2");
        game.beginGame();
        assertEquals(8,game.commonCards.get(0).getBadge().getScore());
        assertEquals(6,game.commonCards.get(0).getBadge().getScore());
        assertEquals(8,game.commonCards.get(1).getBadge().getScore());
        assertEquals(6,game.commonCards.get(1).getBadge().getScore());

        GameForTest game2 = new GameForTest();

        game2.createNewPlayer("player1");
        game2.createNewPlayer("player2");
        game2.createNewPlayer("player3");
        game2.beginGame();
        assertEquals(8,game2.commonCards.get(0).getBadge().getScore());
        assertEquals(6,game2.commonCards.get(0).getBadge().getScore());
        assertEquals(4,game2.commonCards.get(0).getBadge().getScore());
        assertEquals(8,game2.commonCards.get(1).getBadge().getScore());
        assertEquals(6,game2.commonCards.get(1).getBadge().getScore());
        assertEquals(4,game2.commonCards.get(1).getBadge().getScore());

        GameForTest game3 = new GameForTest();

        game3.createNewPlayer("player1");
        game3.createNewPlayer("player2");
        game3.createNewPlayer("player3");
        game3.createNewPlayer("player4");
        game3.beginGame();
        assertEquals(8,game3.commonCards.get(0).getBadge().getScore());
        assertEquals(6,game3.commonCards.get(0).getBadge().getScore());
        assertEquals(4,game3.commonCards.get(0).getBadge().getScore());
        assertEquals(2,game3.commonCards.get(0).getBadge().getScore());
        assertEquals(8,game3.commonCards.get(1).getBadge().getScore());
        assertEquals(6,game3.commonCards.get(1).getBadge().getScore());
        assertEquals(4,game3.commonCards.get(1).getBadge().getScore());
        assertEquals(2,game3.commonCards.get(1).getBadge().getScore());
    }

    /**
     * testing whether the personal cards are assigned and whether they're different or not
     */
    @Test
    public void ChoosePersonalCards(){
        GameForTest game = new GameForTest();
        game.createNewPlayer("player1");
        game.createNewPlayer("player2");
        game.beginGame();
        int i = 0;
        for(Player player : game.players){
            if(player.getPersonalCard() != null) i++;
        }
        assertEquals(2, i);
        System.out.println("testing " + game.players.get(0).getNickname() + " with "+ game.players.get(1).getNickname());
        assertNotSame(game.players.get(0).getPersonalCard(),game.players.get(1).getPersonalCard());

        GameForTest game2 = new GameForTest();
        game2.createNewPlayer("player1");
        game2.createNewPlayer("player2");
        game2.createNewPlayer("player3");
        game2.beginGame();
        i = 0;
        for(Player player : game2.players){
            if(player.getPersonalCard() != null) i++;
        }
        assertEquals(3, i);
        for(Player player : game2.players){
            System.out.println("testing " + player.getNickname() + " with : ");
            for(Player player2 : game2.players){
                if(player != player2){
                    System.out.println("-" + player2.getNickname());
                    assertNotSame(player.getPersonalCard(), player2.getPersonalCard());
                }
            }
        }
        GameForTest game3 = new GameForTest();
        game3.createNewPlayer("player1");
        game3.createNewPlayer("player2");
        game3.createNewPlayer("player3");
        game3.createNewPlayer("player4");
        game3.beginGame();
        i = 0;
        for(Player player : game3.players){
            if(player.getPersonalCard() != null) i++;
        }
        assertEquals(4, i);
        for(Player player : game3.players){
            System.out.println("testing " + player.getNickname() + " with : ");
            for(Player player2 : game3.players){
                if(player != player2){
                    System.out.println("-" + player2.getNickname());
                    assertNotSame(player.getPersonalCard(), player2.getPersonalCard());
                }
            }
        }
    }
    /**
     * testing just for the creation of the gameBoard in the various player numbers since board is already tested for
     */
    @Test
    public void initializeBoardTest(){
        GameForTest game = new GameForTest();
        game.createNewPlayer("player1");
        game.createNewPlayer("player2");
        game.beginGame();
        assertNotNull(game.gameBoard);

        GameForTest game2 = new GameForTest();
        game2.createNewPlayer("player1");
        game2.createNewPlayer("player2");
        game2.createNewPlayer("player3");
        game2.beginGame();
        assertNotNull(game2.gameBoard);

        GameForTest game3 = new GameForTest();
        game3.createNewPlayer("player1");
        game3.createNewPlayer("player2");
        game3.createNewPlayer("player3");
        game3.createNewPlayer("player4");
        game3.beginGame();
        assertNotNull(game3.gameBoard);

    }

    /**
     * testing for badgeEndGame not null
     */
    @Test
    public void initializeBadgeEndGameTest(){
        GameForTest game3 = new GameForTest();
        game3.createNewPlayer("player1");
        game3.createNewPlayer("player2");
        game3.createNewPlayer("player3");
        game3.createNewPlayer("player4");
        game3.beginGame();

        assertNotNull(game3.endGameBadge);
    }

    /**
     * testing for a correct asssignment of a shelfie to each player
     */
    @Test
    public void giveShelfieTest(){
        GameForTest game = new GameForTest();
        game.createNewPlayer("player1");
        game.createNewPlayer("player2");
        game.createNewPlayer("player3");
        game.createNewPlayer("player4");

        game.beginGame();
        for(Player player : game.players){
            assertNotNull(player.getShelfie());
        }
    }

    /**
     * testing that chooseFirstPlayer isn't null
     */
    @Test
    public void chooseFirstPlayerTest(){
        GameForTest game = new GameForTest();
        game.createNewPlayer("player1");
        game.createNewPlayer("player2");
        game.createNewPlayer("player3");
        game.createNewPlayer("player4");
        game.beginGame();

        assertNotNull(game.firstPlayer);
    }

    /**
     * for now just testing the creation, not how correct it is
     */
    @Test
    public void createNewPlayer(){
        GameForTest game = new GameForTest();

        game.createNewPlayer("player1");
        assertNotNull(game.players.get(0));
        assertEquals(1, game.players.size());

        game.createNewPlayer("player2");
        assertNotNull(game.players.get(1));
        assertEquals(2, game.players.size());

        game.createNewPlayer("player3");
        assertNotNull(game.players.get(2));
        assertEquals(3, game.players.size());

        game.createNewPlayer("player4");
        assertNotNull(game.players.get(3));
        assertEquals(4, game.players.size());


    }
    /**
     * testing for the correct functioning of getNextPlayer
     */
    @Test
    public void getNextPlayerTest(){
        GameForTest game = new GameForTest();
        game.createNewPlayer("player1");
        game.createNewPlayer("player2");
        game.createNewPlayer("player3");
        game.createNewPlayer("player4");

        int i = 0;
        game.currentPlayer = game.players.get(0);
        assertEquals(game.players.get(i+1), game.getNextPlayer());
        i++;
        game.setNextPlayer();
        assertEquals(game.players.get(i+1), game.getNextPlayer());
        i++;
        game.setNextPlayer();
        assertEquals(game.players.get(i+1), game.getNextPlayer());
        i++;
        game.setNextPlayer();
        assertEquals(game.players.get(0), game.getNextPlayer());
    }
}