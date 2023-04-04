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
        Game game = new Game();
        game.initializeTile();

        int whiteCount = 0;
        int blueCount = 0;
        int yellowCount = 0;
        int purpleCount = 0;
        int cyanCount = 0;
        int greenCount = 0;

        int i = 0;
        while(i < 132){
            switch(game.getTileBag().get(i).getColor()){
                case WHITE -> whiteCount++;
                case BLUE -> blueCount++;
                case YELLOW -> yellowCount++;
                case PURPLE -> purpleCount++;
                case CYAN -> cyanCount++;
                case GREEN -> greenCount++;
            }
            i++;
        }

        assertEquals(132,game.getTileBag().size());
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
        Game game = new Game();

        game.createNewPlayer("player1");
        game.createNewPlayer("player2");
        game.beginGame();
        assertEquals(2, game.getCommonCards().size());
        assertNotSame(game.getCommonCards().get(0), game.getCommonCards().get(1));
    }

    /**
     * testing for the right assignment of point badges
     */
    @Test
    public void chooseCommonCardsPointsTest(){
        Game game = new Game();

        game.createNewPlayer("player1");
        game.createNewPlayer("player2");
        game.beginGame();
        assertEquals(8,game.getCommonCards().get(0).getBadge().getScore());
        assertEquals(6,game.getCommonCards().get(0).getBadge().getScore());
        assertEquals(8,game.getCommonCards().get(1).getBadge().getScore());
        assertEquals(6,game.getCommonCards().get(1).getBadge().getScore());

        Game game2 = new Game();

        game2.createNewPlayer("player1");
        game2.createNewPlayer("player2");
        game2.createNewPlayer("player3");
        game2.beginGame();
        assertEquals(8,game2.getCommonCards().get(0).getBadge().getScore());
        assertEquals(6,game2.getCommonCards().get(0).getBadge().getScore());
        assertEquals(4,game2.getCommonCards().get(0).getBadge().getScore());
        assertEquals(8,game2.getCommonCards().get(1).getBadge().getScore());
        assertEquals(6,game2.getCommonCards().get(1).getBadge().getScore());
        assertEquals(4,game2.getCommonCards().get(1).getBadge().getScore());

        Game game3 = new Game();

        game3.createNewPlayer("player1");
        game3.createNewPlayer("player2");
        game3.createNewPlayer("player3");
        game3.createNewPlayer("player4");
        game3.beginGame();
        assertEquals(8,game3.getCommonCards().get(0).getBadge().getScore());
        assertEquals(6,game3.getCommonCards().get(0).getBadge().getScore());
        assertEquals(4,game3.getCommonCards().get(0).getBadge().getScore());
        assertEquals(2,game3.getCommonCards().get(0).getBadge().getScore());
        assertEquals(8,game3.getCommonCards().get(1).getBadge().getScore());
        assertEquals(6,game3.getCommonCards().get(1).getBadge().getScore());
        assertEquals(4,game3.getCommonCards().get(1).getBadge().getScore());
        assertEquals(2,game3.getCommonCards().get(1).getBadge().getScore());
    }

    /**
     * testing whether the personal cards are assigned and whether they're different or not
     */
    @Test
    public void ChoosePersonalCards(){
        Game game = new Game();
        game.createNewPlayer("player1");
        game.createNewPlayer("player2");
        game.beginGame();
        int i = 0;
        for(Player player : game.getPlayers()){
            if(player.getPersonalCard() != null) i++;
        }
        assertEquals(2, i);
        assertNotSame(game.getPlayers().get(0).getPersonalCard(),game.getPlayers().get(1).getPersonalCard());

        Game game2 = new Game();
        game2.createNewPlayer("player1");
        game2.createNewPlayer("player2");
        game2.createNewPlayer("player3");
        game2.beginGame();
        i = 0;
        for(Player player : game2.getPlayers()){
            if(player.getPersonalCard() != null) i++;
        }
        assertEquals(3, i);
        for(Player player : game2.getPlayers()){
            for(Player player2 : game2.getPlayers()){
                if(player != player2){
                    assertNotSame(player.getPersonalCard(), player2.getPersonalCard());
                }
            }
        }
        Game game3 = new Game();
        game3.createNewPlayer("player1");
        game3.createNewPlayer("player2");
        game3.createNewPlayer("player3");
        game3.createNewPlayer("player4");
        game3.beginGame();
        i = 0;
        for(Player player : game3.getPlayers()){
            if(player.getPersonalCard() != null) i++;
        }
        assertEquals(4, i);
        for(Player player : game3.getPlayers()){
            for(Player player2 : game3.getPlayers()){
                if(player != player2){
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
        Game game = new Game();
        game.createNewPlayer("player1");
        game.createNewPlayer("player2");
        game.beginGame();
        assertNotNull(game.getGameBoard());

        Game game2 = new Game();
        game2.createNewPlayer("player1");
        game2.createNewPlayer("player2");
        game2.createNewPlayer("player3");
        game2.beginGame();
        assertNotNull(game2.getGameBoard());

        Game game3 = new Game();
        game3.createNewPlayer("player1");
        game3.createNewPlayer("player2");
        game3.createNewPlayer("player3");
        game3.createNewPlayer("player4");
        game3.beginGame();
        assertNotNull(game3.getGameBoard());

    }

    /**
     * testing for badgeEndGame not null
     */
    @Test
    public void initializeBadgeEndGameTest(){
        Game game3 = new Game();
        game3.createNewPlayer("player1");
        game3.createNewPlayer("player2");
        game3.createNewPlayer("player3");
        game3.createNewPlayer("player4");
        game3.beginGame();

        assertNotNull(game3.getEndGameBadge());
    }

    /**
     * testing for a correct asssignment of a shelfie to each player
     */
    @Test
    public void giveShelfieTest(){
        Game game = new Game();
        game.createNewPlayer("player1");
        game.createNewPlayer("player2");
        game.createNewPlayer("player3");
        game.createNewPlayer("player4");

        game.beginGame();
        for(Player player : game.getPlayers()){
            assertNotNull(player.getShelfie());
        }
    }

    /**
     * testing that chooseFirstPlayer isn't null
     */
    @Test
    public void chooseFirstPlayerTest(){
        Game game = new Game();
        game.createNewPlayer("player1");
        game.createNewPlayer("player2");
        game.createNewPlayer("player3");
        game.createNewPlayer("player4");
        game.beginGame();

        assertNotNull(game.getFirstPlayer());
    }

    /**
     * for now just testing the creation, not how correct it is
     */
    @Test
    public void createNewPlayer(){
        Game game = new Game();

        game.createNewPlayer("player1");
        assertNotNull(game.getPlayers().get(0));
        assertEquals(1, game.getPlayers().size());

        game.createNewPlayer("player2");
        assertNotNull(game.getPlayers().get(1));
        assertEquals(2, game.getPlayers().size());

        game.createNewPlayer("player3");
        assertNotNull(game.getPlayers().get(2));
        assertEquals(3, game.getPlayers().size());

        game.createNewPlayer("player4");
        assertNotNull(game.getPlayers().get(3));
        assertEquals(4, game.getPlayers().size());


    }
    /**
     * testing for the correct functioning of getNextPlayer
     */
    @Test
    public void getNextPlayerTest(){
        Game game = new Game();
        game.createNewPlayer("player1");
        game.createNewPlayer("player2");
        game.createNewPlayer("player3");
        game.createNewPlayer("player4");

        int i = 0;
        game.setCurrentPlayer(game.getPlayers().get(0));
        assertEquals(game.getPlayers().get(i+1), game.getNextPlayer());
        i++;
        game.setNextPlayer();
        assertEquals(game.getPlayers().get(i+1), game.getNextPlayer());
        i++;
        game.setNextPlayer();
        assertEquals(game.getPlayers().get(i+1), game.getNextPlayer());
        i++;
        game.setNextPlayer();
        assertEquals(game.getPlayers().get(0), game.getNextPlayer());
    }
}