package it.polimi.softeng.model;
import java.util.ArrayList;

/**
 * this enum defines all 12 personal objective cards with their own objectiveCell objects that get created as you use the enum constructor defined below.
 * internally the PERSONAL_CARD numbering follows the asset cards numbering; the new objectiveCells are written looking at asset cards from sx to dx, from bottom to top
 */
public enum PersonalCards {
    PERSONAL_CARD_1(
            new ObjectiveCell(0,5, Tile.TileColor.PURPLE),
            new ObjectiveCell(1,2, Tile.TileColor.YELLOW),
            new ObjectiveCell(2,0, Tile.TileColor.CYAN),
            new ObjectiveCell(2,5, Tile.TileColor.BLUE),
            new ObjectiveCell(3,3, Tile.TileColor.WHITE),
            new ObjectiveCell(4,4, Tile.TileColor.GREEN)
    ),
    PERSONAL_CARD_2(
            new ObjectiveCell(0,3, Tile.TileColor.GREEN),
            new ObjectiveCell(1,4, Tile.TileColor.PURPLE),
            new ObjectiveCell(2,3, Tile.TileColor.YELLOW),
            new ObjectiveCell(3,1, Tile.TileColor.CYAN),
            new ObjectiveCell(4,0, Tile.TileColor.BLUE),
            new ObjectiveCell(4,2, Tile.TileColor.WHITE)
    ),
    PERSONAL_CARD_3(
            new ObjectiveCell(0,0, Tile.TileColor.WHITE),
            new ObjectiveCell(0,4, Tile.TileColor.BLUE),
            new ObjectiveCell(1,2, Tile.TileColor.GREEN),
            new ObjectiveCell(2,3, Tile.TileColor.PURPLE),
            new ObjectiveCell(3,4, Tile.TileColor.YELLOW),
            new ObjectiveCell(4,2, Tile.TileColor.CYAN)
    ),
    PERSONAL_CARD_4(
            new ObjectiveCell(0,3, Tile.TileColor.CYAN),
            new ObjectiveCell(1,1, Tile.TileColor.WHITE),
            new ObjectiveCell(2,1, Tile.TileColor.GREEN),
            new ObjectiveCell(2,3, Tile.TileColor.BLUE),
            new ObjectiveCell(3,2, Tile.TileColor.PURPLE),
            new ObjectiveCell(4,5, Tile.TileColor.YELLOW)
    ),
    PERSONAL_CARD_5(
            new ObjectiveCell(0,0, Tile.TileColor.YELLOW),
            new ObjectiveCell(1,2, Tile.TileColor.BLUE),
            new ObjectiveCell(1,4, Tile.TileColor.CYAN),
            new ObjectiveCell(2,2, Tile.TileColor.WHITE),
            new ObjectiveCell(3,0, Tile.TileColor.GREEN),
            new ObjectiveCell(4,1, Tile.TileColor.PURPLE)
    ),
    PERSONAL_CARD_6(
            new ObjectiveCell(0,0, Tile.TileColor.PURPLE),
            new ObjectiveCell(1,1, Tile.TileColor.YELLOW),
            new ObjectiveCell(2,5, Tile.TileColor.CYAN),
            new ObjectiveCell(3,1, Tile.TileColor.BLUE),
            new ObjectiveCell(3,3, Tile.TileColor.WHITE),
            new ObjectiveCell(4,5, Tile.TileColor.GREEN)
    ),
    PERSONAL_CARD_7(
            new ObjectiveCell(0,2, Tile.TileColor.CYAN),
            new ObjectiveCell(0,5, Tile.TileColor.GREEN),
            new ObjectiveCell(1,3, Tile.TileColor.PURPLE),
            new ObjectiveCell(2,0, Tile.TileColor.WHITE),
            new ObjectiveCell(3,4, Tile.TileColor.BLUE),
            new ObjectiveCell(4,1, Tile.TileColor.YELLOW)
    ),
    PERSONAL_CARD_8(
            new ObjectiveCell(0,2, Tile.TileColor.PURPLE),
            new ObjectiveCell(1,4, Tile.TileColor.GREEN),
            new ObjectiveCell(2,3, Tile.TileColor.CYAN),
            new ObjectiveCell(3,0, Tile.TileColor.YELLOW),
            new ObjectiveCell(3,1, Tile.TileColor.WHITE),
            new ObjectiveCell(4,5, Tile.TileColor.BLUE)
    ),
    PERSONAL_CARD_9(
            new ObjectiveCell(0,0, Tile.TileColor.BLUE),
            new ObjectiveCell(1,1, Tile.TileColor.CYAN),
            new ObjectiveCell(2,3, Tile.TileColor.GREEN),
            new ObjectiveCell(2,5, Tile.TileColor.YELLOW),
            new ObjectiveCell(4,1, Tile.TileColor.PURPLE),
            new ObjectiveCell(4,2, Tile.TileColor.WHITE)
    ),
    PERSONAL_CARD_10(
            new ObjectiveCell(0,3, Tile.TileColor.WHITE),
            new ObjectiveCell(1,1, Tile.TileColor.BLUE),
            new ObjectiveCell(1,4, Tile.TileColor.YELLOW),
            new ObjectiveCell(3,0, Tile.TileColor.PURPLE),
            new ObjectiveCell(3,2, Tile.TileColor.GREEN),
            new ObjectiveCell(4,5, Tile.TileColor.CYAN)
    ),
    PERSONAL_CARD_11(
            new ObjectiveCell(0,3, Tile.TileColor.YELLOW),
            new ObjectiveCell(1,4, Tile.TileColor.WHITE),
            new ObjectiveCell(2,2, Tile.TileColor.BLUE),
            new ObjectiveCell(2,5, Tile.TileColor.PURPLE),
            new ObjectiveCell(3,0, Tile.TileColor.CYAN),
            new ObjectiveCell(4,1, Tile.TileColor.GREEN)
    ),
    PERSONAL_CARD_12(
            new ObjectiveCell(0,0, Tile.TileColor.GREEN),
            new ObjectiveCell(1,4, Tile.TileColor.PURPLE),
            new ObjectiveCell(2,3, Tile.TileColor.BLUE),
            new ObjectiveCell(2,5, Tile.TileColor.WHITE),
            new ObjectiveCell(3,2, Tile.TileColor.CYAN),
            new ObjectiveCell(4,1, Tile.TileColor.YELLOW)
    );
    /**
     * internal data structure used to store the objectiveCells representing the personal objectives
     */
    private ArrayList<ObjectiveCell> objective = new ArrayList<>();

    /**
     * constructor method that links the objectiveCells to the objective arrayList of the enum type
     * @param c1 objectiveCell parameter taken from the definition of the enum class
     * @param c2 objectiveCell parameter taken from the definition of the enum class
     * @param c3 objectiveCell parameter taken from the definition of the enum class
     * @param c4 objectiveCell parameter taken from the definition of the enum class
     * @param c5 objectiveCell parameter taken from the definition of the enum class
     * @param c6 objectiveCell parameter taken from the definition of the enum class
     */
    PersonalCards(ObjectiveCell c1, ObjectiveCell c2,ObjectiveCell c3, ObjectiveCell c4,ObjectiveCell c5, ObjectiveCell c6){
        objective.add(c1);
        objective.add(c2);
        objective.add(c3);
        objective.add(c4);
        objective.add(c5);
        objective.add(c6);
    }

    /**
     * class used to implement the singular personal objective tile; it extends the cell class adding a specif color for the cell
     */
    public static class ObjectiveCell extends Cell {
        private Tile.TileColor color;

        /**
         * constructor method for objectiveCell
         * @param x x position of the cell in shelfie
         * @param y y position of the cell in shelfie
         * @param color color of the cell in shelfie
         */
        public ObjectiveCell(int x, int y, Tile.TileColor color){
            setX(x);
            setY(y);
            this.color = color;
        }

        /**
         * getter method added to objectiveCell for the attribute "color" (ObjectiveCell inherits getX and getY methods)
         * @return Tile.tileColor color value (ie one of the six possible tile colors)
         */
        public Tile.TileColor getColor() {
            return color;
        }
    }

    /**
     * calculates the score based on the chosen personal objective card on a given shelfie
     * @param shelfie the shelfie on which you check the personal goal
     * @return int representing the score from the personal objective
     */
    public int getCurrentScore(Shelfie shelfie){
        int count = 0;
        int x,y;
        Tile.TileColor color;

        for(int i = 0; i < 6; i++){
            x = objective.get(i).getX();
            y = objective.get(i).getY();
            color = objective.get(i).getColor();

            //it seems counterintuitive but getTile works on a (row, column) basis aka (y,x)
            if(shelfie.getTile(y,x).getColor() == color){
                count++;
            }
        }
        return switch (count) {
            case 1 -> 1;
            case 2 -> 2;
            case 3 -> 4;
            case 4 -> 6;
            case 5 -> 9;
            case 6 -> 12;
            default -> 0;
        };
    }

}

