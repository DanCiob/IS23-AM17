package it.polimi.softeng.model.commonCards;

import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;
import it.polimi.softeng.model.graphForScoreCount.graphNode;

import java.util.ArrayList;

public class FourGroupsOfFourEquals extends CommonCards {

    /**
     *
     * @param s is the shelfie
     * @return true if the shape is satisfied, false if not
     */
    @Override
    public boolean verifyShape(Shelfie s) {
        int tileCounted = 0;
        int groupCounter = 0;
        Tile[][] grid = s.getGrid();
        ArrayList<graphNode> Nodes = new ArrayList<>();

        //Building graph connecting every Tile with its right and upper one
        for (int i = 0; i < 6; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                graphNode temp = null;
                if (i == 5 && j != 4) {
                    temp = new graphNode(grid[i][j], null, new graphNode(grid[i][j + 1], null, null));
                    Nodes.add(temp);
                }
                if (i == 5 && j == 4) {
                    temp = new graphNode(grid[i][j], null, null);
                    Nodes.add(temp);
                }
                if (i != 5 && j == 4) {
                    temp = new graphNode(grid[i][j], new graphNode(grid[i+1][j], null, null), null);
                    Nodes.add(temp);
                }
                if (temp != null)
                    Nodes.add(temp);
            }
        }

        //Iterate in nodes to visit all nodes of the graph
        for (graphNode n: Nodes)
        {
            tileCounted = 0;
            if (!n.isVisited())
                tileCounted = n.visitGraphWithUpperBound(n, tileCounted, 4);
            if (tileCounted == 4)
                groupCounter++;
        }

        if (groupCounter >= 4)
            return true;
        else
            return false;
    }
}