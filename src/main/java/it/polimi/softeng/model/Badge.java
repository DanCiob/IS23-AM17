package it.polimi.softeng.model;

public abstract class Badge {
    private int score;
    
    public Badge(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
