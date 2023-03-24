package it.polimi.softeng.model;

import it.polimi.softeng.model.commonCards.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.random.*;

public class Game {
    //board section
    private Board gameBoard = new Board();
    public ArrayList<Tile> tileBag = new ArrayList<>();     //temporarily public, should be private
    private BadgeEndGame endGameBadge;
    //player section
    /**
     * this arrayList contains the references to the players; the order of the elements in the array refers to the order of
     * entry in the game lobby (ie player 0 is who created the game). the game turns go from player in position 0 to position player.size()
     */
    private ArrayList<Player> players = new ArrayList<>();
    private Player currentPlayer;
    private ArrayList<CommonCards> commonCards = new ArrayList<>();
    private Player winner;
    private Player firstPlayer;
    private ArrayList<Integer> personalCardsAlreadyUsedNum = new ArrayList<>();

    public void beginGame(){
        //vedi cosa serve
        initializeTile();
        initializeBoard();
        //inizializzare le carte prima dei badge
        initializebadgeScore();
        initializeBadgeEndGame();
    }

    public void initializeBoard(){
        gameBoard.positionTiles(tileBag);
    }

    public void initializeTile(){
        for(int i = 0; i < 132; i++){
            switch(i % 6){
                case 0->tileBag.add(new Tile(i, Tile.TileColor.WHITE));
                case 1->tileBag.add(new Tile(i, Tile.TileColor.BLUE));
                case 2->tileBag.add(new Tile(i, Tile.TileColor.YELLOW));
                case 3->tileBag.add(new Tile(i, Tile.TileColor.PURPLE));
                case 4->tileBag.add(new Tile(i, Tile.TileColor.CYAN));
                case 5->tileBag.add(new Tile(i, Tile.TileColor.GREEN));
                default -> System.out.println("FATAL ERROR DURING TILES CREATION !");
            }
        }
    }

    public void initializeBadgeEndGame(){
        endGameBadge = BadgeEndGame.getInstance();
    }

    public void initializebadgeScore(){
        switch(players.size()){
            case 2 -> {
                commonCards.get(0).setBadges(BadgeScore.EIGHT);
                commonCards.get(0).setBadges(BadgeScore.SIX);
                commonCards.get(1).setBadges(BadgeScore.EIGHT);
                commonCards.get(1).setBadges(BadgeScore.SIX);
            }
            case 3 -> {
                commonCards.get(0).setBadges(BadgeScore.EIGHT);
                commonCards.get(0).setBadges(BadgeScore.SIX);
                commonCards.get(0).setBadges(BadgeScore.FOUR);
                commonCards.get(1).setBadges(BadgeScore.EIGHT);
                commonCards.get(1).setBadges(BadgeScore.SIX);
                commonCards.get(1).setBadges(BadgeScore.FOUR);
            }
            case 4 -> {
                commonCards.get(0).setBadges(BadgeScore.EIGHT);
                commonCards.get(0).setBadges(BadgeScore.SIX);
                commonCards.get(0).setBadges(BadgeScore.FOUR);
                commonCards.get(0).setBadges(BadgeScore.TWO);
                commonCards.get(1).setBadges(BadgeScore.EIGHT);
                commonCards.get(1).setBadges(BadgeScore.SIX);
                commonCards.get(1).setBadges(BadgeScore.FOUR);
                commonCards.get(1).setBadges(BadgeScore.TWO);
            }
        }
    }

    public void chooseCommonCards(){
        Random random = new Random();
        ArrayList<Integer> commonCardsNum = new ArrayList<>();
        //generation of two random ints between 0 and 11
        int i = random.nextInt(12);
        int j = random.nextInt(12);
        while(j == i){
            j = random.nextInt(12);
        }
        commonCardsNum.add(i);
        commonCardsNum.add(j);

        i = 0;
        while(i < 2){
            switch(commonCardsNum.get(i)){
                case 0 -> commonCards.add(new DiagonalOfEquals());
                case 1 -> commonCards.add(new EightEquals());
                case 2 -> commonCards.add(new FourCornerOfEquals());
                case 3 -> commonCards.add(new FourGroupsOfFourEquals());
                case 4 -> commonCards.add(new FourRowsOfOneTwoThreeTypes());
                case 5 -> commonCards.add(new SixGroupOfTwoEquals());
                case 6 -> commonCards.add(new Stairs());
                case 7 -> commonCards.add(new ThreeColumnsOfOneTwoThreeTypes());
                case 8 -> commonCards.add(new TwoColumnsOfSixDifferent());
                case 9 -> commonCards.add(new TwoRowsOfFiveDifferent());
                case 10 -> commonCards.add(new TwoSquareOfEquals());
                case 11 -> commonCards.add(new XOfEquals());
            }
            i++;
        }

    }
    public void choosePersonalCards(Player player){
        Random random = new Random();
        //generation of a random int between 0 and 11
        int i = random.nextInt(12);
        while(personalCardsAlreadyUsedNum.contains(i)){
            i = random.nextInt(12);
        }

        switch(i) {
            case 0 -> player.setPersonalCard(PersonalCards.PERSONAL_CARD_1);
            case 1 -> player.setPersonalCard(PersonalCards.PERSONAL_CARD_2);
            case 2 -> player.setPersonalCard(PersonalCards.PERSONAL_CARD_3);
            case 3 -> player.setPersonalCard(PersonalCards.PERSONAL_CARD_4);
            case 4 -> player.setPersonalCard(PersonalCards.PERSONAL_CARD_5);
            case 5 -> player.setPersonalCard(PersonalCards.PERSONAL_CARD_6);
            case 6 -> player.setPersonalCard(PersonalCards.PERSONAL_CARD_7);
            case 7 -> player.setPersonalCard(PersonalCards.PERSONAL_CARD_8);
            case 8 -> player.setPersonalCard(PersonalCards.PERSONAL_CARD_9);
            case 9 -> player.setPersonalCard(PersonalCards.PERSONAL_CARD_10);
            case 10 -> player.setPersonalCard(PersonalCards.PERSONAL_CARD_11);
            case 11 -> player.setPersonalCard(PersonalCards.PERSONAL_CARD_12);
        }
        personalCardsAlreadyUsedNum.add(i);
    }

    /**
     * gives a new shelfie to a player
     * @param player the player to whom is given the new shelfie
     */
    public void giveShelfie(Player player){
        player.setShelfie(new Shelfie());
    }
    public void chooseFirstPlayer(){
        Random random = new Random();
        int i = random.nextInt(players.size());
        switch (i){
            case 0 -> firstPlayer = players.get(0);
            case 1 -> firstPlayer = players.get(1);
            case 2 -> firstPlayer = players.get(2);
            case 3 -> firstPlayer = players.get(3);
        }
        setCurrentPlayer(firstPlayer);
    }

    /**
     * it handles the last turns after that one player has a full shelfie
     */
    public void lastTurn(){
        //verifica chi è l'ultimo giocatore
        // ecc
        //! questo metodo viene chiamato dopo checkendgame o devo controllare se la shelfie è piena??
        while(!(currentPlayer.isFirst())){ //aggiungere metodo e !current player è quello che ha riempito la shelfie o il successivo?
            currentPlayer = getNextPlayer();
            //routine di turno

        }

        calculateScore();
        selectWinner();
    }

    /**
     * it calculates the score of personal card and set of equals tiles for every player, at the end of the game
     */
    public void calculateScore(){
        int pointsToAdd;
        for(Player p : players){
            pointsToAdd = p.getPersonalCard().getCurrentScore(p.getShelfie());
            //pointsToAdd = pointsToAdd + devo usare score per i blocchi???
            p.updateScore(pointsToAdd);
        }
    }

    /**
     * it sets the
     */
    public void selectWinner(){
        int maxScore = 0;
        for(Player p : players){
            if(p.getCurrentScore() > maxScore) {
                maxScore = p.getCurrentScore();
                winner = p;
            }else if(p.getCurrentScore() == maxScore){

                //winner = players.get();
            }
        }
    }


    public void createNewPlayer() /*implements PlayerManager*/ {

    }
    public void removePlayer(Player player){
        
    }

    /**
     * getter method for the currentPlayer attribute
     * @return Player object referencing the current player
     */
    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    /**
     * setter method used to set the current player
     * @param player player set to become currentPlayer
     */
    public void setCurrentPlayer(Player player){
        currentPlayer = player;
    }

    /**
     * method used to see who is the next player in the game
     * @return player object referencing the next player in the game
     */
    public Player getNextPlayer(){
        int i = 0;
        while(i < players.size() && players.get(i) != getCurrentPlayer()){
            i++;
        }
        if(i + 1 < players.size()){
            return players.get(i+1);
        }
        else return players.get(0);
    }
    public String gameChangeNotifier(){
        return null;
    }
}
