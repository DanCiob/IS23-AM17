package it.polimi.softeng.model.graphForScoreCount;
import it.polimi.softeng.model.Tile;

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

    //Stop the trasversal if limit is reached
    public int visitGraphWithUpperBound (graphNode n, int count, int limit)
    {
        if (count == limit)
            return count;

        n.visited = true;

        if (n.thisTile.getColor().equals(n.up.thisTile.getColor()))
        {
            count++;
            visitGraphWithUpperBound(n.up, count, limit);
        }
        if (n.thisTile.getColor().equals(n.right.thisTile.getColor()))
        {
            count++;
            visitGraphWithUpperBound(n.right, count, limit);
        }
        return count;
    }
}
