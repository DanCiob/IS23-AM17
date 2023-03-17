package it.polimi.softeng.model.CommonCards;
import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;
import java.util.ArrayList;

public class SixGroupOfTwoEquals extends CommonCards {

    //Group of two Tile
    public class GroupOfTwo {
        Tile t1;
        Tile t2;

        public GroupOfTwo(Tile t1, Tile t2) {
            this.t1 = t1;
            this.t2 = t2;
        }
    }


    /**
        Return true if the shelfie contains six or more groups of at least two tiles of the same color
     */
    @Override
    public boolean verifyShape(Shelfie s) {
        int groupCounter = 0;
        Tile[][] grid = s.getGrid();
        ArrayList<GroupOfTwo> AlreadySeen = new ArrayList<GroupOfTwo>();

        for (int i = 0; i < 6; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                GroupOfTwo temp1 = new GroupOfTwo(grid[i][j], grid[i+1][j]);
                GroupOfTwo temp2 = new GroupOfTwo(grid[i+1][j], grid[i][j]);
                //If the group was already counted -if- is bypassed
                if (grid[i][j].getColor().equals(grid[i+1][j].getColor()) && !AlreadySeen.contains(temp1) && !AlreadySeen.contains(temp2))
                {
                    groupCounter++;
                    AlreadySeen.add(new GroupOfTwo(grid[i][j], grid[i+1][j]));
                }

                temp1 = new GroupOfTwo(grid[i][j], grid[i-1][j]);
                temp2 = new GroupOfTwo(grid[i-1][j], grid[i][j]);
                if (grid[i][j].getColor().equals(grid[i-1][j].getColor()) && !AlreadySeen.contains(temp1) && !AlreadySeen.contains(temp2))
                {
                    groupCounter++;
                    AlreadySeen.add(new GroupOfTwo(grid[i][j], grid[i-1][j]));
                }

                temp1 = new GroupOfTwo(grid[i][j], grid[i][j+1]);
                temp2 = new GroupOfTwo(grid[i][j+1], grid[i][j]);
                if (grid[i][j].getColor().equals(grid[i][j+1].getColor()) && !AlreadySeen.contains(temp1) && !AlreadySeen.contains(temp2))
                {
                    groupCounter++;
                    AlreadySeen.add(new GroupOfTwo(grid[i][j], grid[i][j+1]));
                }

                temp1 = new GroupOfTwo(grid[i][j], grid[i][j-1]);
                temp2 = new GroupOfTwo(grid[i][j-1], grid[i][j]);
                if (grid[i][j].getColor().equals(grid[i][j-1].getColor()) && !AlreadySeen.contains(temp1) && !AlreadySeen.contains(temp2))
                {
                    groupCounter++;
                    AlreadySeen.add(new GroupOfTwo(grid[i][j], grid[i][j-1]));
                }
            }
        }

        if (groupCounter >= 6)
            return true;
        else
            return false;
    }
}
