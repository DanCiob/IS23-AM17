package it.polimi.softeng.client.view;

/**
 * UI interfaces shows method that CLI and GUI implements to show information about game
 */
public interface UI {
    /**
     * Visualizer of Board state
     */
    public void boardVisualizer();
    /**
     * Visualizer of Shelfie state
     */
    public void shelfieVisualizer();
    /**
     * Visualizer of CommonCards used in game
     */
    public void commonCardsVisualizer();
    /**
     * Visualizer of PersonalCard
     */
    public void personalCardVisualizer();
    /**
     * Visualizer of CurrentScore
     */
    public void scoreVisualizer();
    /**
     * Visualizer of Chat
     */
    public void chatVisualizer();
    /**
     * Visualizer of current connected players
     */
    public void onlinePlayersVisualizer();

}
