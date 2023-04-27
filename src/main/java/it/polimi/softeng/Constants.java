package it.polimi.softeng;

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

    //CLI related constants
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final int numOfCommands = 6;

    private Constants() {
    }
}