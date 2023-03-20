package it.polimi.softeng.controller;
import it.polimi.softeng.model.Cell;
import java.util.ArrayList;
import java.util.Arrays;

public class BoardController {
    private ArrayList<Cell> positionsToBeRemoved;

    public void boardParser(String s)
    {
        int numoftiles = (int) s.chars().filter(x -> x == '-').count() + 1;
        Cell t1 = new Cell();
        Cell t2 = new Cell();
        Cell t3 = new Cell();

        switch (numoftiles) {
            case 1:

                t1.setX((int)s.charAt(5));
                t1.setY((int)s.charAt(8));

                positionsToBeRemoved.add(t1);
            break;

            case 2:
                t1.setX((int)s.charAt(5));
                t1.setY((int)s.charAt(8));
                t1.setX((int)s.charAt(16));
                t1.setY((int)s.charAt(19));

                positionsToBeRemoved.add(t1);
                positionsToBeRemoved.add(t2);
                break;

            case 3:
                t1.setX((int)s.charAt(5));
                t1.setY((int)s.charAt(8));

                t2.setX((int)s.charAt(16));
                t2.setY((int)s.charAt(19));

                t3.setX((int)s.charAt(27));
                t3.setY((int)s.charAt(30));

                positionsToBeRemoved.add(t1);
                positionsToBeRemoved.add(t2);
                positionsToBeRemoved.add(t3);
                break;
        }

    //Call to interface BoardSetter


    }
}
