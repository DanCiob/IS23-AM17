package it.polimi.softeng.model;

/**
 * we use this class to collect all the constants that are useful in the game such as the dimension of the shelfie
 */

public abstract class Constants {
    //shelfie
    public static final int shelfieRows = 6;
    public static final int shelfieColumns = 5;
    //board
    public static final int boardRows = 9;
    public static final int boardColumns = 9;
    //tiles
    public static final int totalTiles = 132;
    public static final int typesOfTiles = 6;
    //cards
    public static final int totalCommonCards = 12;
    public static final int totalPersonalCards = 12;
    public static final int colorsOfPersonalCards = 6; //Number of Cells inside one PersonalCard
    //move
    public static final int maxTilesForMove = 3; //maximum number of Tiles you can have in a single move

    private Constants() {
    }
}
