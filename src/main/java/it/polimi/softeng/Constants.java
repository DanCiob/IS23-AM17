package it.polimi.softeng;

/**
 * We use this class to collect all the constants that are useful in the game
 */

public abstract class Constants {
    //Shelfie
    public static final int shelfieRows = 6;
    public static final int shelfieColumns = 5;
    //board
    public static final int boardRows = 9;
    public static final int boardColumns = 9;
    //tiles
    public static final int totalTiles = 132;
    public static final int typesOfTiles = 6;
    public static final int picturesForEachTile = 3; //different pictures on tiles of the same colour
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
    public static final String ANSI_GREY = "\u001B[37m";
    public static final int numOfCommands = 6;

    //Regex for JSON
    //Allows gameMove in JSON defined standard -> ex: (0,1),(2,3),2
    public static final String gameREGEX = "^([(][0-9][,][0-9][)][,]){1,3}[0-4]$";
    //Allows gameMove message command
    public static final String gameCommandREGEX = "^[@][Gg][Aa][Mm][Ee][ ]([(][0-9][,][0-9][)][,]){1,3}[0-4]$";
    //Allows chat message in JSON defined standard -> ex: 'receiver' message
    public static final String chatREGEX = "^['][a-zA-Z0-9_]+['][ ].+$";
    //Allows chat message command
    public static final String chatCommandREGEX = "^[@][Cc][Hh][Aa][Tt][ ]['][a-zA-Z0-9_]+['][ ]";
    //Allows nickname in specific standard
    public static final String nicknameREGEX = "^[a-zA-Z0-9_]+$";
    //Allows first part of commands only in specific standard
    public static final String commandREGEX = "^([@][Cc][Mm][Nn][Dd])|^([@][Vv][Bb][Oo][Rr])|^([@][Vv][Ss][Hh][Ee])|^([@][Vv][Pp][Cc][Aa])|^([@][Vv][Cc][Cc][Aa])|^([@][Vv][Pp][Ll][Aa])|^([@][Cc][Hh][Aa][Tt])|^([@][Gg][Aa][Mm][Ee])|^([@][Hh][Ee][Ll][Pp])";

    //Errors
    public static final String JOINING_LOBBY = "You will join existing lobby";
    public static final String NICKNAME_NOT_UNIQUE = "Error: Nickname not unique, try again";
    public static final String PLAYER_DISCONNECTED = "Error: Client disconnected";
    public static final String ERROR_IN_GAMEMOVE = "Error: Either it's not your turn or gameMove syntax is wrong!";
    public static final String ALL_PLAYERS_DISCONNECTED = "Error, you can't send game moves when you're the only player remaining";
    public static final String INVALID_NUMBER_OF_PLAYERS = "Error: Number of player must be between 2-4";
    public static final String INVALID_CHOICE_OF_TILES = "Error: You can't select these tiles, try again!";
    public static final String INVALID_COLUMN = "Error: You can't select these column (column cannot contain selected tiles)";
    public static final String INVALID_RECEIVER = "Error: Receiver doesn't exist";
    public static final String ALREADY_LOGGED_IN = "Error: You're already in a game";
    public static final String YOU_ARE_RECEIVER = "Error: You can't send a message to yourself!";


    //CommonCards
        //ColumnsOfMaxDiffTypes
        public static final int maxDiffTypes_ColumnsOfMaxDiffTypes = 3; //maximum number of different colors for each column
        public static final int target_ColumnsOfMaxDiffTypes = 3; //number of columns with different colors to complete the achievement
        //RowsOfMaxDiffTypes
        public static final int maxDiffTypes_RowsOfMaxDiffTypes = 3; //maximum number of different colors for each row
        public static final int target_RowsOfMaxDiffTypes = 4; //number of rows with different colors to complete the achievement
        //SixGroupsOfTwoEquals
        public static final int dimension_SixGroupsOfTwoEquals = 2; //dimension of every group
        public static final int groups_SixGroupsOfTwoEquals = 6; //number of groups
        //FourGroupsOfFourEquals
        public static final int dimension_FourGroupsOfFourEquals = 4; //dimension of every group
        public static final int groups_FourGroupsOfFourEquals = 4; //number of groups
        //TwoSquaresOfEquals
        public static final int groups_TwoSquaresOfEquals = 2;
        //TwoColumnsOfSixDifferent
        public static final int target_TwoColumnsOfSixDifferent = 2;
        //TwoRowsOfFiveDifferent
        public static final int target_TwoRowsOfFiveDifferent = 2;


    private Constants() {
    }
}
