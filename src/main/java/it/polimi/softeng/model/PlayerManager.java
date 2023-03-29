package it.polimi.softeng.model;

public interface PlayerManager {
    public void createNewPlayer (String nickname);

    public Player getCurrentPlayer();
    public void setNextPlayer();
}
