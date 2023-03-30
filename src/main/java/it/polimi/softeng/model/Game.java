package it.polimi.softeng.model;

import it.polimi.softeng.model.scoreCount.Score;
import it.polimi.softeng.model.commonCards.*;
import java.util.ArrayList;
import java.util.Random;

import static it.polimi.softeng.model.PersonalCard.FillPersonalCardsBag;

public class Game implements PlayerManager{
    //board section
    private Board gameBoard = new Board();
    public ArrayList<Tile> tileBag = new ArrayList<>();     //temporarily public for testing purpose, should be private
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

    //maybe finished
    //dovremmo controllare che non parta con meno del numero di giocatori scelto
    public void beginGame(){
        //vedi cosa serve
        initializeTile();
        initializeBoard();
        //inizializzare le carte prima dei badge
        chooseCommonCards();
        choosePersonalCards();
        initializebadgeScore();
        initializeBadgeEndGame();
        chooseFirstPlayer();
    }

    /**
     * method used to place the tiles on the board
     */
    public void initializeBoard(){
        gameBoard.positionTiles(tileBag);
    }

    /**
     * this method creates all the tiles and places them in the tileBag
     */
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

    /**
     * this method gets the reference to the singleton object BadgeEndGame
     */
    public void initializeBadgeEndGame(){
        endGameBadge = BadgeEndGame.getInstance();
    }

    /**
     * this method creates all the score badges and places them inside the commonCards objects
     */
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

    /**
     * method that randomly chooses 2 common objective cards
     */
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
    //cant find a way to make this useful other than testing
    /**
     * getter method for CommonCards array
     * @return list of commonCards choosen for the match
     */
    public ArrayList<CommonCards> getCommonCards(){
        return commonCards;
    }

    /**
     * this method select a personalCard for a player
     * @param player player receiving the personal card
     */
    /*public void choosePersonalCards(Player player){
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
    }*/

    /**
     * Set a different personal card for each player
     */
    public void choosePersonalCards(){

        ArrayList<PersonalCard> personalCardsBag = new ArrayList<>();
        personalCardsBag = FillPersonalCardsBag();
        Random Random = new Random();
        int i = 0;
        PersonalCard toBeAssigned;

        for (Player p: players)
        {
            i = Random.nextInt(12);
            toBeAssigned = personalCardsBag.get(i);
            //p.setPersonalCard(toBeAssigned);
            personalCardsBag.remove(toBeAssigned);
        }
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
        firstPlayer.setIsFirst();
    }

    /**
     * it handles the last turns after that one player has a full shelfie
     * this method is called after checkEndGame
     */
    public void lastTurn(){
        while(!(currentPlayer.isFirst())){ //current player is the one that has a full shelfie
            currentPlayer = getNextPlayer();
            //TO DO: game turn routine
        }
        calculateScore();
        selectWinner();
    }

    /**
     * it calculates and updates the score of personal card and set of equals tiles for every player, at the end of the game
     */
    public void calculateScore(){
        int pointsToAdd;
        for(Player p : players){
            pointsToAdd = p.getPersonalCard().getCurrentScore(p.getShelfie(), p.getPersonalCard());
            pointsToAdd = pointsToAdd + Score.ScoreForGroups(p.getShelfie());
            p.updateScore(pointsToAdd);
        }
    }

    /**
     * it checks the score of every player and select the winner
     * if two players have the same score, the winner is the last one of the two
     */
    public void selectWinner(){
        int maxScore = 0;
        for(Player p : players){
            if(p.getCurrentScore() >= maxScore) {
                maxScore = p.getCurrentScore();
                winner = p;
            }
        }
    }

    /**
     * method used to create a new player object for a game; such object is then placed in the players list in last place
     * @param nickName nickname of the player to be created
     */
    public void createNewPlayer(String nickName){
        players.add(new Player(nickName, 0));

    }

    //should we keep a reference to removed players as long as a Game object exists ?
    /**
     * method used to remove a player from the player list Players;
     * @param player reference to the player to be removed
     */
    public void removePlayer(Player player){
        int i = 0;
        while(i < players.size() && players.get(i) != player){
            i++;
        }
        players.remove(i);
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

    /**
     * method used to update the value of the attribute currentPlayer
     */
    @Override
    public void setNextPlayer() {
        setCurrentPlayer(getNextPlayer());
    }

    public String gameChangeNotifier(){
        return null;
    }
}
