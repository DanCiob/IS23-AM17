package it.polimi.softeng.model;
import java.util.ArrayList;

public abstract class PersonalCards {
    private ArrayList<ObjectiveCell> Objective;

    public class ObjectiveCell extends Cell {
        private Tile.TileColor color;
    }
}
