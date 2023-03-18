package it.polimi.softeng.model;

public class Player {
    private String Nickname;
    private int CurrentScore;
    private boolean isFirst;


    public Player(String nickname, int currentScore, boolean isFirst) {
        Nickname = nickname;
        CurrentScore = currentScore;
        this.isFirst = isFirst;
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

    /**
     *
     */
    //public String playerChangeNotifier(){}
}
