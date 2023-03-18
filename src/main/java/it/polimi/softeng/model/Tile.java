package it.polimi.softeng.model;

public class Tile {
    int id;
    TileColor color;
    public enum TileColor {
        BLUE, WHITE, GREEN, YELLOW, PURPLE, CYAN
    }

    public Tile(int id, TileColor color) {
        this.id = id;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public TileColor getColor() {
        return color;
    }
}
