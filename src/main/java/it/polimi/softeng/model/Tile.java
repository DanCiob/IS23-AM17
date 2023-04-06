package it.polimi.softeng.model;

public class Tile {
    int id;
    TileColor color;
    public enum TileColor {
        BLUE(0), WHITE(1), GREEN(2), YELLOW(3), PURPLE(4), CYAN(5);
        private int pos;
        TileColor(int pos){
            this.pos = pos;
        }
        private int pos(){
            return pos;
        }
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
