package it.polimi.softeng.model.ScoreCount;

import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;

import java.util.ArrayList;
import java.util.Stack;

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
                    System.out.println("Added group of " + grid[i][j].getColor());
                }
            }
        }
        System.out.println("Counted groups: " + NumberOfGroups);
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
        System.out.println("Counted: " + GroupDimension[0] + "groups of 3, " + GroupDimension[2] + "groups of 4, " + GroupDimension[2] + "groups of 5, " + GroupDimension[3] + "groups of 6 or more");

        //Return achieved Score
        return 2 * GroupDimension[0] + 3 * GroupDimension[1] + 5 * GroupDimension[2] + 6 * GroupDimension[3];


    }
}


