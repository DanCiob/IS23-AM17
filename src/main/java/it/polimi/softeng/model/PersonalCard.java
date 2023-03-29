package it.polimi.softeng.model;

import java.util.ArrayList;
import java.util.Arrays;

public class PersonalCard {
    ObjectiveCell objective[] = new ObjectiveCell[6];

    public class ObjectiveCell {
        int x;
        int y;
        Tile.TileColor color;

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Tile.TileColor getColor() {
            return color;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public void setColor(Tile.TileColor color) {
            this.color = color;
        }
    }

    public ObjectiveCell[] getObjective() {
        return objective;
    }

    public void setObjective(int cell, int x, int y, Tile.TileColor color) {
        this.objective[cell].setX(x);
        this.objective[cell].setY(y);
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

    public static void PersonalCardToString(PersonalCard p) {
        System.out.println("PersonalCard: ");
        for (int i = 0; i < 6; i++)
        {
            System.out.println("Cell -> "+i);
            System.out.println("X: " + p.getObjective()[i].getX());
            System.out.println("Y: " + p.getObjective()[i].getY());
            System.out.println("Color: " + p.getObjective()[i].getColor());
        }
    }

}
