package it.polimi.softeng.model;

import java.io.Serializable;

/**
 * Model representation of player
 */
public class Player implements Serializable {
    /**
     *
     */
    private final String Nickname;
    private int CurrentScore;
    private boolean isFirst;
    private PersonalCards personalCards;
    private Shelfie shelfie;

    public Player(String nickname, int currentScore) {
        Nickname = nickname;
        CurrentScore = currentScore;
        shelfie = new Shelfie();
    }


    public String getNickname() {
        return Nickname;
    }

    public int getCurrentScore() {
        return CurrentScore;
    }

    /**
     * Update Player score
     */
    public int updateScore(int pointsToAdd) {
        this.CurrentScore += pointsToAdd;
        return getCurrentScore();
    }
    public void setIsFirst(){
        isFirst = true;
    }
    public boolean isFirst() {
        return isFirst;
    }

    public PersonalCards getPersonalCard() {
        return personalCards;
    }

    public Shelfie getShelfie() {
        return shelfie;
    }

    public void setPersonalCard(PersonalCards personalCards){
        this.personalCards = personalCards;
    }
    public void setShelfie(Shelfie shelfie){
        this.shelfie = shelfie;
    }

    //public String playerChangeNotifier(){}
}
