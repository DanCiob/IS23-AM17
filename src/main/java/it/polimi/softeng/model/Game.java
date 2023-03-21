package it.polimi.softeng.model;

import it.polimi.softeng.model.commonCards.CommonCards;

import java.util.ArrayList;

public class Game {

    private Board gameBoard = new Board();
    private ArrayList<Player> players = new ArrayList<>();
    private Player currentPlayer;
    private ArrayList<CommonCards> commonCards = new ArrayList<>();
    private Player winner;
    public ArrayList<Tile> tileBag = new ArrayList<>();
    private ArrayList<Badge> badgeBag = new ArrayList<>();
    public void beginGame(){

    }

    public void initializeBoard(){

    }

    public void initializeTile(){
        for(int i = 0; i < 132; i++){
            switch(i % 6){
                //commento
                case 0->tileBag.add(new Tile(i, Tile.TileColor.WHITE));
                case 1->tileBag.add(new Tile(i, Tile.TileColor.BLUE));
                case 2->tileBag.add(new Tile(i, Tile.TileColor.YELLOW));
                case 3->tileBag.add(new Tile(i, Tile.TileColor.PURPLE));
                case 4->tileBag.add(new Tile(i, Tile.TileColor.CYAN));
                case 5->tileBag.add(new Tile(i, Tile.TileColor.GREEN));
                default -> System.out.println("FATAL ERROR DURING TILES CREATION !");
            }
        }
    }

    public void fillBoard(){

    }

    public void initializeBadgeEndGame(){

    }

    public void initializebadgeScore(){

    }

    public void chooseCommonCards(){

    }

    public void stackScore(){

    }

    public void giveShelfie(){

    }
}
