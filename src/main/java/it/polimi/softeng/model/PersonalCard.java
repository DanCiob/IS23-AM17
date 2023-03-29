package it.polimi.softeng.model;

import java.util.ArrayList;

import static it.polimi.softeng.JSONParser.PersonalCardsParser.InitializePersonalCards;
import static it.polimi.softeng.model.scoreCount.Score.PersonalCardsScore;

public class PersonalCard {
    ObjectiveCell objective[] = new ObjectiveCell[6];

    public class ObjectiveCell {
        int row;
        int column;
        Tile.TileColor color;

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }

        public Tile.TileColor getColor() {
            return color;
        }

        public void setRow(int x) {
            this.row = x;
        }

        public void setColumn(int y) {
            this.column = y;
        }

        public void setColor(Tile.TileColor color) {
            this.color = color;
        }
    }

    public ObjectiveCell[] getObjective() {
        return objective;
    }

    public void setObjective(int cell, int row, int column, Tile.TileColor color) {
        this.objective[cell].setRow(row);
        this.objective[cell].setColumn(column);
        this.objective[cell].setColor(color);
    }

    public PersonalCard() {
        this.objective[0] = new ObjectiveCell();
        this.objective[1] = new ObjectiveCell();
        this.objective[2] = new ObjectiveCell();
        this.objective[3] = new ObjectiveCell();
        this.objective[4] = new ObjectiveCell();
        this.objective[5] = new ObjectiveCell();
    }

    //For testing purposes
    public static void PersonalCardToString(PersonalCard p) {
        System.out.println("PersonalCard: ");
        for (int i = 0; i < 6; i++)
        {
            System.out.println("Cell -> "+i);
            System.out.println("Row: " + p.getObjective()[i].getRow());
            System.out.println("Column: " + p.getObjective()[i].getColumn());
            System.out.println("Color: " + p.getObjective()[i].getColor());
        }
    }

    /**
     *
     * @return list of all PersonalCards
     */
    public static ArrayList<PersonalCard> FillPersonalCardsBag ()
    {
        return InitializePersonalCards();
    }

    /**
     *
     * @param s is player's shelfie
     * @param p is player's personal card
     * @return score
     */
    public static int ActualScore (Shelfie s, PersonalCard p)
    {
        return PersonalCardsScore(p, s);
    }
}
