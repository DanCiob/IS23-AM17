package it.polimi.softeng.model;

import it.polimi.softeng.customExceptions.IllegalInsertException;
import it.polimi.softeng.model.scoreCount.Score;
import it.polimi.softeng.model.commonCards.*;
import java.util.ArrayList;
import java.util.Random;
import static it.polimi.softeng.Constants.*;
import static it.polimi.softeng.model.PersonalCards.FillPersonalCardsBag;

/**
 * main class of the model representing the game and connection all the subclasses
 */
public class Game{
    //board section
    /**
     * gameboard reference
     */
    private GameBoard gameBoard = new GameBoard();
    /**
     * tilebag reference
     */
    private final ArrayList<Tile> tileBag = new ArrayList<>();
    /**
     * badgeEndGame reference
     */
    private BadgeEndGame endGameBadge;
    /**
     * boolean value indicating wheter simple rules are used or not
     */
    private final boolean simpleRules = false;

    //player section
    /**
     * this arrayList contains the references to the players; the order of the elements in the array refers to the order of
     * entry in the game lobby (ie player 0 is who created the game). the game turns go from player in position 0 to position player.size()
     */
    private final ArrayList<Player> players = new ArrayList<>();
    /**
     * reference to the current player
     */
    private Player currentPlayer;
    /**
     * common cards reference
     */
    private final ArrayList<CommonCards> commonCards = new ArrayList<>();
    /**
     * winning player reference
     */
    private Player winner;
    /**
     * first player reference
     */
    private Player firstPlayer;
    /**
     * boolean attribute indicating wheter a shelfie is full or not
     */
    private Boolean fullShelfie = false;

    /**
     *this method initializes all the necessary components of a game
     * @param nameList list of players of the match (after this such list cannot be chanced)
     */
    public void beginGame(ArrayList<String> nameList){
        for(String name : nameList){
            createNewPlayer(name);
        }
        initializeTile();
        initializeBoard();
        chooseCommonCards();
        choosePersonalCards();
        initializebadgeScore();
        initializeBadgeEndGame();
        chooseFirstPlayer();
    }

    /**
     * testing version of the method beginGame
     */
    //here for testing purpose
    public void beginGame(){
        //vedi cosa serve
        initializeTile();
        initializeBoard();
        chooseCommonCards();
        choosePersonalCards();
        initializebadgeScore();
        initializeBadgeEndGame();
        chooseFirstPlayer();
    }

    /**
     * this method creates all the tiles and places them in the tileBag
     */
    public void initializeTile(){
        for(int i = 0; i < totalTiles; i=i+typesOfTiles){
            tileBag.add(new Tile(i, Tile.TileColor.WHITE));
            tileBag.add(new Tile(i+1, Tile.TileColor.BLUE));
            tileBag.add(new Tile(i+2, Tile.TileColor.YELLOW));
            tileBag.add(new Tile(i+3, Tile.TileColor.PURPLE));
            tileBag.add(new Tile(i+4, Tile.TileColor.CYAN));
            tileBag.add(new Tile(i+5, Tile.TileColor.GREEN));
        }
    }

    /**
     * method used to place the tiles on the board
     */
    public void initializeBoard(){
        gameBoard.resetBoard(players.size());
        gameBoard.positionTiles(tileBag);
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
        if(simpleRules){
            int k = random.nextInt(totalCommonCards);

            switch(k){
                case 0 -> commonCards.add(new DiagonalOfEquals());
                case 1 -> commonCards.add(new NEqualTiles());
                case 2 -> commonCards.add(new CornersOfEquals());
                case 3 -> commonCards.add(new FourGroupsOfFourEquals());
                case 4 -> commonCards.add(new RowsOfMaxDiffTypes());
                case 5 -> commonCards.add(new SixGroupsOfTwoEquals());
                case 6 -> commonCards.add(new Stairs());
                case 7 -> commonCards.add(new ColumnsOfMaxDiffTypes());
                case 8 -> commonCards.add(new TwoColumnsOfSixDifferent());
                case 9 -> commonCards.add(new TwoRowsOfFiveDifferent());
                case 10 -> commonCards.add(new TwoSquaresOfEquals());
                case 11 -> commonCards.add(new XOfEquals());
            }
        }else{
            //generation of two random ints between 0 and 11
            int i = random.nextInt(totalCommonCards);
            int j = random.nextInt(totalCommonCards);
            while(j == i){
                j = random.nextInt(totalCommonCards);
            }
            commonCardsNum.add(i);
            commonCardsNum.add(j);

            i = 0;
            while(i < 2){
                switch(commonCardsNum.get(i)){
                    case 0 -> commonCards.add(new DiagonalOfEquals());
                    case 1 -> commonCards.add(new NEqualTiles());
                    case 2 -> commonCards.add(new CornersOfEquals());
                    case 3 -> commonCards.add(new FourGroupsOfFourEquals());
                    case 4 -> commonCards.add(new RowsOfMaxDiffTypes());
                    case 5 -> commonCards.add(new SixGroupsOfTwoEquals());
                    case 6 -> commonCards.add(new Stairs());
                    case 7 -> commonCards.add(new ColumnsOfMaxDiffTypes());
                    case 8 -> commonCards.add(new TwoColumnsOfSixDifferent());
                    case 9 -> commonCards.add(new TwoRowsOfFiveDifferent());
                    case 10 -> commonCards.add(new TwoSquaresOfEquals());
                    case 11 -> commonCards.add(new XOfEquals());
                }
                i++;
            }
        }


    }

    /**
     * Set a different personal card for each player
     */
    public void choosePersonalCards(){

        ArrayList<PersonalCards> personalCardsBag = new ArrayList<>();
        personalCardsBag = FillPersonalCardsBag();
        Random Random = new Random();
        int i;
        PersonalCards toBeAssigned;
        int j = totalPersonalCards;
        for (Player p: players)
        {
            i = Random.nextInt(j);
            toBeAssigned = personalCardsBag.get(i);
            p.setPersonalCard(toBeAssigned);
            personalCardsBag.remove(toBeAssigned);
            j--;
        }
    }

    /**
     * this method chooses the first player of the game
     */
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
     * It's turn version used in controller: it receives the move of the current player, updating board and shelfie.
     * It also checks if the move is legal, if there are only islands, if a CommonCard is completed, if the
     *  shelfie is full and it's necessary to call last turn routine
     * @param positionsToBeRemoved contains coordinates of tiles to be removed from the board
     * @param column is column of insertion in shelfie, in which the player wants to insert the tiles in
     * @return 1 the game is ended, 0 if not, -1 if there's an error
     */
    public int turn(ArrayList<Cell> positionsToBeRemoved, int column){
        //find the first available row of the @param column
        int firstFree = 0;
       // Badge badge; for gui
        while(firstFree<shelfieRows && currentPlayer.getShelfie().getGrid()[firstFree][column] != null){
            firstFree++;
        }

        //create my array of tiles to insert from @param positionsToBeRemoved
        ArrayList<Tile> tilesToInsert = new ArrayList<>();
        for(Cell position : positionsToBeRemoved){
            tilesToInsert.add(getGameBoard().getBoard()[position.getRow()][position.getColumn()]);
        }

        //check if it is possible to remove the tiles from the board, then remove them if it is legal
        if(!gameBoard.updateBoard(positionsToBeRemoved)){
            return -1;
        }

        /*
        Try to insert the tiles in the current player shelfie; if it is not possible we reinsert the tiles in
         the board and set at null positions in the shelfie.
        */
        try{
           currentPlayer.getShelfie().insertTile(tilesToInsert, column);
        }catch (IllegalInsertException e){
            gameBoard.reinsertTiles(tilesToInsert, positionsToBeRemoved);
            for(int i=0; i<tilesToInsert.size() && firstFree<shelfieRows; i++){
                currentPlayer.getShelfie().setGridAtNull(firstFree, column);
                firstFree ++;
            }
            tilesToInsert.clear();
            positionsToBeRemoved.clear();

            return -1;
        }


        //if there are only islands on the board
        if(gameBoard.checkIslands()){
            gameBoard.positionTiles(tileBag);
        }

        Badge badge;
        //verify common cards
        for(CommonCards card : commonCards){
            if(card.verifyShape(currentPlayer.getShelfie())){
                badge = card.getBadge();
                if(badge != null){
                    //current player has completed a common card, so he receives the badge. The badge is removed from the arrayList
                    currentPlayer.updateScore(badge.getScore());
                }
            }
        }

        if(!fullShelfie){
            if(checkEndGame())
                fullShelfie = true;
        }
        if(fullShelfie){
            getCurrentPlayer().updateScore(getEndGameBadge().getScore()); //TODO:Check if it doesn't give a badge at every player after the one that has a full shelfie
            // if(!(getNextPlayer().isFirst())){//current player is the one that has a full shelfie
               // setCurrentPlayer(getNextPlayer());
            //}else{
            System.out.println("Checkendgame");
            if((getNextPlayer().isFirst())){//current player is the one that has a full shelfie
                calculateScore(); //it checks personal cards score: TODO: testing
                selectWinner();
                System.out.println("Turn - return 1");
                return 1;
            }
        }

        setNextPlayer();
        return 0;
    }




    /**
     * this method is used by MatchTest
     */
    public void turn(){

        //if there are only islands on the board
        if(gameBoard.checkIslands()){
            gameBoard.positionTiles(tileBag);
        }

        //verify common cards
        for(CommonCards card : commonCards){
            if(card.verifyShape(currentPlayer.getShelfie())){
                //current player has completed a common card, so he receives the badge. The badge is removed from the arrayList
                BadgeScore badgeScore = card.getBadge();
                if(badgeScore != null)
                    currentPlayer.updateScore(badgeScore.getScore());
            }
        }
    }

    /**
     * it calculates and updates the score of personal card and set of equals tiles for every player, at the end of the game
     */
    public void calculateScore(){
        int pointsToAdd;
        for(Player p : players){
            pointsToAdd = PersonalCards.getCurrentScore(p.getShelfie(), p.getPersonalCard());
            //System.out.println("Points from personal cards of " + p.getNickname() + " : " + pointsToAdd);
            pointsToAdd = pointsToAdd + Score.ScoreForGroups(p.getShelfie());
            //System.out.println("Points from personal cards and score for groups of " + p.getNickname() + " : " + pointsToAdd);

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
    public void setNextPlayer() {
        setCurrentPlayer(getNextPlayer());
    }

    //methods for testing purpose

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public ArrayList<Tile> getTileBag() {
        return tileBag;
    }

    public BadgeEndGame getEndGameBadge() {
        return endGameBadge;
    }

    public boolean isSimpleRules() {
        return simpleRules;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<CommonCards> getCommonCards() {
        return commonCards;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public boolean checkEndGame(){
        return currentPlayer.getShelfie().checkFull();
    }

    public Player getWinner() {
        return winner;
    }
}
