package it.polimi.softeng.model.scoreCount;

import it.polimi.softeng.model.PersonalCards;
import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;

public class Score {
    static ArrayList<Tile> visited = new ArrayList<>();
    /**
     * @param s         is the shelfie
     * @param required  required group to achieve the objective
     * @param size      required size of the group
     * @return true if the objective is achieved, false if not
     * According to the rules even if the size of adjacent tiles is bigger than desired size the group is counted as one
     */
    public static boolean GroupsOfEqualTiles(Shelfie s, int required, int size) {
        Tile[][] grid = s.getGrid();
        int TileCounted;
        int NumberOfGroups = 0;

        visited.clear();

        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 6 && grid[i][j] != null; i++) {
                TileCounted = 0;

                if (!visited.contains(grid[i][j]))
                    TileCounted = DFS(grid, i, j);

                if (TileCounted > size) {
                    NumberOfGroups++;
                }
            }
        }
        //true if the max number of groups found is bigger than required number
        return NumberOfGroups >= required;
    }

    /**
     * @param grid  is the shelfie.getGrid()
     * @param r     current row
     * @param c     current column
     * @return number of counted tiles, adjacent and of the same color, beginning from current tile
     */
    public static int DFS(Tile[][] grid, int r, int c) {
        class TileWithCoordinates
        {
            Tile tile;
            final int r;
            final int c;

            public TileWithCoordinates(Tile tile, int r, int c) {
                this.tile = tile;
                this.r = r;
                this.c = c;
            }

            public Tile getTile() {
                return tile;
            }

            public int getR() {
                return r;
            }

            public int getC() {
                return c;
            }

            public void setTile(Tile tile) {
                this.tile = tile;
            }
        }
        Stack<TileWithCoordinates> stack = new Stack<>();
        int TileCounted = 0;
        Tile.TileColor currentColor = grid[r][c].getColor();

        stack.push(new TileWithCoordinates(grid[r][c], r, c));
        TileCounted++;

        while (!stack.empty())
        {
            TileWithCoordinates currentTile = stack.pop();
            if (!visited.contains(currentTile.tile) && currentTile.tile.getColor().equals(currentColor))
            {
                TileCounted++;
                visited.add(currentTile.tile);

                r = currentTile.r;
                c = currentTile.c;

                if (r != 5)
                {
                    if (grid[r+1][c] != null)
                        stack.push(new TileWithCoordinates(grid[r+1][c], r+1, c));
                }
                if (r != 0)
                {
                    if (grid[r-1][c] != null)
                        stack.push(new TileWithCoordinates(grid[r-1][c], r-1, c));
                }
                if (c != 4)
                {
                    if (grid[r][c+1] != null)
                        stack.push(new TileWithCoordinates(grid[r][c+1], r, c+1));
                }
                if (c != 0)
                {
                    if (grid[r][c-1] != null)
                        stack.push(new TileWithCoordinates(grid[r][c-1], r, c-1));
                }
            }
        }
        return TileCounted;
    }

    /**
     *
     * @param s is the shelfie
     * @return number of points earned by counting groups
     */
    public static int ScoreForGroups (Shelfie s)
    {
        Tile[][] grid = s.getGrid();
        int TileCounted;
        int NumberOfGroups = 0;
        int[] GroupDimension = {0, 0, 0, 0};

        visited.clear();

        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 6 && grid[i][j] != null; i++) {
                TileCounted = 0;

                if (!visited.contains(grid[i][j]))
                    TileCounted = DFS(grid, i, j);

                if (TileCounted == 3)
                    GroupDimension[0]++;
                else if (TileCounted == 4)
                    GroupDimension[1]++;
                else if (TileCounted == 5)
                    GroupDimension[2]++;
                else if (TileCounted >= 6)
                    GroupDimension[3]++;
            }
        }
        //Return achieved Score
        return 2 * GroupDimension[0] + 3 * GroupDimension[1] + 5 * GroupDimension[2] + 6 * GroupDimension[3];


    }



     //This method is used to check if the shelfie has two columns of six different or two rows of five different

    /**
     *
     * @param s It's the shelfie to check
     * @param columns It's true if the method is called from TwoColumnsOfSixDifferent, false if it's called from TwoRowsOfFiveDifferent
     * @return 0 if the shelfie doesn't respect the card or 1 if it does
     */
    public static boolean twoOfDifferent(Shelfie s, boolean columns){
        boolean notVerified = false;
        int i=0, countVerified = 0, j, z=0, iMax, jMax;
        if(!columns){
            iMax = 6;
            jMax = 5;
        }else{
            iMax = 5;
            jMax = 6;
        }

        while(i < iMax && countVerified<2) {
            j = 0;
            if(!columns)
                notVerified = false;
            while(((!columns && j<4) || (columns && j<5) )&& s.getGrid()[i][j] != null){
                z=j+1;
                if(columns)
                    notVerified = false;
                while((z<jMax) && ((!columns && s.getGrid()[i][z] != null) || (columns && s.getGrid()[z][i] != null)) &&(!notVerified)){
                    if((!columns)&&(s.getGrid()[i][j].getColor() == s.getGrid()[i][z].getColor()))
                        notVerified = true;
                    if((columns)&&(s.getGrid()[j][i].getColor() == s.getGrid()[z][i].getColor()))
                        notVerified = true;
                    z++;
                }
                if(columns && !notVerified && z==6)
                    countVerified++;
                j++;
            }
            if((!columns)&&(!notVerified)&&(z==5)&&(s.getGrid()[i][z-1] != null))
                countVerified++;
            i++;
        }
        return countVerified >= 2;
    }

    /**
     *
     * @param p is the PersonalCard
     * @param s is the shelfie
     * @return score achieved in PersonalCard
     */
    public static int PersonalCardsScore (PersonalCards p, Shelfie s) {
        int numObjectiveReached = 0;
        PersonalCards.ObjectiveCell obj[] = new PersonalCards.ObjectiveCell[6];
        obj = p.getObjective();
        //6 is the dimension of PersonalCard ScoreTable
        for (int i = 0; i < 6; i++) {
            //If color in PC matches color in shelfie
            if (s.getTile(obj[i].getRow(), obj[i].getColumn()) != null && s.getTile(obj[i].getRow(), obj[i].getColumn()).getColor().equals(obj[i].getColor()))
                numObjectiveReached++;
        }

        switch (numObjectiveReached)
        {
            case 1 -> {
                return 1;
            }
            case 2 -> {
                return 2;
            }
            case 3 -> {
                return 4;
            }
            case 4 -> {
                return 6;
            }
            case 5 -> {
                return 9;
            }
            case 6 -> {
                return 12;
            }
        }
        return 0;
    }

    /**
     * This method verifies FourRowsOfOneTwoThreeTypes and ThreeColumnsOfOneTwoThreeTypes CommonCards with the same algorithm
     * To distinguish them we use Mode.ROW for FourRowsOfOneTwoThreeTypes and Mode.COLUMN for ThreeColumnsOfOneTwoThreeTypes
     * @param s is the shelfie
     * @param maxDifferentColor indicates how many different color can a row / column contain
     * @param target indicates how many rows or column do you need to complete the CommonCard
     * @param maxOuter indicates the upper limit of outer cycle,
     *                 maxOuter = maxR for ROW, maxOuter = maxC for COLUMN
     * @param maxInner indicates the upper limit of inner cycle,
     *                 maxInner = maxC for ROW, maxInner = maxR for COLUMN
     * @param mode indicates which mode (ROW-COLUMN) the method have to verify
     * @return true if the shelfie completes the CommonCard
     */

    public static boolean verifyMaxDifferentColor (Shelfie s, int maxDifferentColor, int target, int maxOuter, int maxInner, Mode mode) {
        int count = 0; //number of rows with different colors found in the shelfie
        boolean completed = false;
        Vector<Tile.TileColor> v = new Vector<>();

        outer:
        for(int o = 0; o < maxOuter; o++) {
            inner:
            for (int i = 0; i < maxInner; i++) {
                //if empty, go to next row or column
                if(Score.getTileMode(mode, o, i, s) == null){
                    break inner;
                }
                //add color to vector if it doesn't already exist
                if(!v.contains(Score.getTileMode(mode, o, i, s).getColor())){
                    v.add(Score.getTileMode(mode, o, i, s).getColor());
                    //if too many different colors in this row, go to the next one
                    if (v.size() > maxDifferentColor) {
                        break inner;
                    }
                }
                //if we analyze the entire row or column without exceeding maxDifferentColor, we count + 1
                if (i == maxInner - 1) {
                    count++;
                }
                //if we find a number of rows or column with different colors which is the target, we complete the achievement
                if (count >= target) {
                    completed = true;
                    break outer;
                }
            }
            //reset to count different colors of next row or column
            v.clear();
        }
        return completed;

    }

    /**
     * enum to distinguish two different modes: ROW and COLUMN
     * It is used for verifyMaxDifferentColor() method
     */
    public enum Mode {
        ROW, COLUMN
    }

    /**
     * This method is used to get the tile in verifyMaxDifferentColor() method.
     * It gives the right TILE using o(uter) and i(nner) indexes as rows or columns
     * @param mode indicates which mode (ROW-COLUMN) the method have to verify
     * @param o outer iterator
     * @param i inner iterator
     * @param s shlefie
     * @return the Tile of the shelfie in a specific position
     */
    private static Tile getTileMode(Mode mode, int o, int i, Shelfie s) {
        //ROW -> outer iterator refers to rows, inner iterator refers to columns
        //COLUMN -> inner iterator refers to rows, outer iterator refers to columns
        if (mode == Mode.ROW) {
            return s.getTile(o, i);
        } else{ //mode == Mode.COLUMN
            return s.getTile(i, o);
        }
    }
}


