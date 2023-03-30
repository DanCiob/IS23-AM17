package it.polimi.softeng.model;

import javax.swing.text.TableView;

public class Player {
    private String Nickname;
    private int CurrentScore;
    private boolean isFirst;
    private PersonalCard personalCard;
    private Shelfie shelfie;

    public Player(String nickname, int currentScore) {
        Nickname = nickname;
        CurrentScore = currentScore;
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

    public PersonalCard getPersonalCard() {
        return personalCard;
    }

    public Shelfie getShelfie() {
        return shelfie;
    }

    public void setPersonalCard(PersonalCard personalCard){
        this.personalCard = personalCard;
    }
    public void setShelfie(Shelfie shelfie){
        this.shelfie = shelfie;
    }
    /**
     *
     */
    //public String playerChangeNotifier(){}
}
