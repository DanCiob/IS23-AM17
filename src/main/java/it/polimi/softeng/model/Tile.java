package it.polimi.softeng.model;

public class Tile {
    int id;
    TileColor color;
    public enum TileColor {
        BLUE(0, 'B'), WHITE(1, 'W'), GREEN(2, 'G'), YELLOW(3, 'Y'), PURPLE(4, 'P'), CYAN(5, 'C');
        private final int colorPos;
        private final char colorLetter;
        TileColor(int colorPos, char colorLetter){
            this.colorPos = colorPos;
            this.colorLetter = colorLetter;
        }
        public int colorPos(){
            return colorPos;
        }
        public char colorLetter(){
            return colorLetter;
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
