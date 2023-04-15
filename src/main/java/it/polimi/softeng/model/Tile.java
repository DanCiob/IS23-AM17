package it.polimi.softeng.model;

public class Tile {
    int id;
    TileColor color;
    public enum TileColor {
        BLUE(0, 'B', "\u001B[34m"), WHITE(1, 'W', "\u001B[0m"), GREEN(2, 'G', "\u001B[32m"), YELLOW(3, 'Y', "\u001B[33m"), PURPLE(4, 'P', "\u001B[35m"), CYAN(5, 'C', "\u001B[36m");
        private final int colorPos;
        private final char colorLetter;
        private final String coloredText;
        TileColor(int colorPos, char colorLetter, String coloredText){
            this.colorPos = colorPos;
            this.colorLetter = colorLetter;
            this.coloredText = coloredText;
        }
        public int colorPos(){
            return colorPos;
        }
        public char colorLetter(){
            return colorLetter;
        }

        public String coloredText() {
            return coloredText;
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
