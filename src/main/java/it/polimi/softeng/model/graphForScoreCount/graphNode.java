package it.polimi.softeng.model.graphForScoreCount;
import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;
import it.polimi.softeng.customExceptions.UpperBoundReached;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class graphNode
{
    Tile thisTile;
    graphNode up;
    graphNode right;
    boolean visited;

    public graphNode(Tile thisTile, graphNode up, graphNode right) {
        this.thisTile = thisTile;
        this.up = up;
        this.right = right;
        this.visited = false;
    }

    public void setThisTile(Tile thisTile) {
        this.thisTile = thisTile;
    }

    public Tile getThisTile() {
        return thisTile;
    }

    public graphNode getUp() {
        return up;
    }

    public graphNode getRight() {
        return right;
    }

    public void setUp(graphNode up) {
        this.up = up;
    }

    public void setRight(graphNode right) {
        this.right = right;
    }

    public boolean isVisited() {
        return visited;
    }

    /**
     *
     * @param n -> graph node
     * @param count -> counter of visited nodes
     * @return number of tile of same color visited yet
     */
    public int visitGraph (graphNode n, int count)
    {
        n.visited = true;

        if (n.thisTile.getColor().equals(n.up.thisTile.getColor()))
        {
            count++;
            visitGraph(n.up, count);
        }
        if (n.thisTile.getColor().equals(n.right.thisTile.getColor()))
        {
            count++;
            visitGraph(n.right, count);
        }
        return count;
    }

    /**
     *
     * @param n -> graph node
     * @param count -> counter of visited nodes
     * @param limit -> stop the traversal if limit is reached
     * @return number of tile of same color visited yet
     */
    public int visitGraphWithUpperBound (graphNode n, int count, int limit) throws UpperBoundReached
    {
        count++;
        n.visited = true;
        if (count == limit)
            throw new UpperBoundReached();

        if (n.up != null && n.thisTile.getColor().equals(n.up.thisTile.getColor()))
            visitGraphWithUpperBound(n.up, count, limit);

        if (n.right != null && n.thisTile.getColor().equals(n.right.thisTile.getColor()))
            visitGraphWithUpperBound(n.right, count, limit);

        return count;
    }

    /**
     *
     * @param s is the shelfie
     * @return a List where each Tile.getID() of the grid is linked to a graphNode and the matrix is mapped to a graph
     */
    public static ArrayList<graphNode> mapShelfieToGraph(Shelfie s)
    {
        Map<Integer, graphNode> ID_graphmap = new HashMap<Integer, graphNode>();
        ArrayList<graphNode> nodes = new ArrayList<>();

        for (int i = 0; i < 6; i++)
        {
            for (int j = 0; j < 5; j++)
                //Maps only !null cell
                if(s.getTile(i, j) != null) {
                    graphNode n = new graphNode(s.getTile(i, j), null, null);
                    ID_graphmap.put(s.getTile(i, j).getId(), n);
                }
        }

        for (int j = 0; j < 5; j++)
        {
            for (int i = 0; i < 6 && s.getTile(i, j) != null; i++)
            {
                int temp_id = s.getTile(i, j).getId();
                graphNode temp_gn = ID_graphmap.get(temp_id);

                //Upmost row
                if (i == 5 && j != 4)
                {
                    temp_gn.thisTile = s.getTile(i, j);
                    temp_gn.setUp(null);
                    temp_gn.setRight(ID_graphmap.get(s.getTile(i, j+1).getId()));
                    nodes.add(temp_gn);
                }
                //Rightmost column
                if (i != 5 && j == 4)
                {
                    temp_gn.thisTile = s.getTile(i, j);
                    temp_gn.setUp(ID_graphmap.get(s.getTile(i+1, j).getId()));
                    temp_gn.setRight(null);
                    nodes.add(temp_gn);
                }
                //Upmost and rightmost cell
                if (i == 5 && j == 4)
                {
                    temp_gn.thisTile = s.getTile(i, j);
                    temp_gn.setUp(null);
                    temp_gn.setRight(null);
                    nodes.add(temp_gn);
                }
                //Middle of the shelfie
                if (i != 5 && j != 4)
                {
                    temp_gn.thisTile = s.getTile(i, j);
                    if (s.getTile(i+1, j) != null)
                        temp_gn.up = (ID_graphmap.get(s.getTile(i+1, j).getId()));
                    if (s.getTile(i, j+1) != null)
                        temp_gn.right = (ID_graphmap.get(s.getTile(i, j+1).getId()));
                    nodes.add(temp_gn);
                }
            }
        }
        return nodes;
    }
}
