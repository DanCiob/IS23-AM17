package it.polimi.softeng.model.graphForScoreCount;
import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

    //Stop the traversal if limit is reached
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

    /**
     *
     * @param s is the shelfie
     * @return a map where each Tile.getID() of the grid is linked to a graphNode
     */
    public static Map<Integer, graphNode> mapTileToGraphNode(Shelfie s)
    {
        Map<Integer, graphNode> toBeRet = new HashMap<>();
        for (int i = 0; i < 6; i++)
        {
            for (int j = 0; j < 5; j++)
                toBeRet.put(s.getTile(i, j).getId(), new graphNode(s.getTile(i, j), null, null));
        }

        return toBeRet;
        /*
        Possible functional version

        //From Tile[][] to Tile[] to single Tile
        ArrayList<Tile> temp = (ArrayList<Tile>) Arrays.stream(s.getGrid())
                                        .flatMap(Arrays::stream)
                                        .toList();

        //For every Tile creates a graphNode
        ArrayList<graphNode> temp1 = (ArrayList<graphNode>) Arrays.stream(s.getGrid())
                                                                  .flatMap(Arrays::stream)
                                                                  .map(x -> new graphNode(x, null, null))
                                                                  .toList();

        //Map Tile.getID() to a new graphNode
        return Map<Integer, graphNode> toBeRet = (Map<Integer, graphNode>)

         */
    }

    /**
     *
     * @param s is the shelfie
     * @return an ArrayList containing Shelfie mapped to an oriented graph
     */
    public static ArrayList<graphNode> mapShelfieToGraph(Shelfie s)
    {
        Map<Integer, graphNode> Tile_GraphNode = mapTileToGraphNode(s);
        ArrayList<graphNode> Nodes = new ArrayList<>();
        //Tile[][] grid = s.getGrid();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                //We are in the upmost row
                if (i == 5 && j != 4) {
                    Tile_GraphNode.get(s.getTile(i, j).getId()).setUp(null);
                    Tile_GraphNode.get(s.getTile(i, j).getId()).setRight(Tile_GraphNode.get(s.getTile(i, j+1).getId()));
                    Nodes.add(Tile_GraphNode.get(s.getTile(i, j).getId()));
                }
                //We are in the upmost and rightmost cell
                if (i == 5 && j == 4) {
                    Tile_GraphNode.get(s.getTile(i, j).getId()).setUp(null);
                    Tile_GraphNode.get(s.getTile(i, j).getId()).setRight(null);
                    Nodes.add(Tile_GraphNode.get(s.getTile(i, j).getId()));
                }
                //We are in the rightmost column
                if (i != 5 && j == 4) {
                    Tile_GraphNode.get(s.getTile(i, j).getId()).setUp(Tile_GraphNode.get(s.getTile(i+1, j).getId()));
                    Tile_GraphNode.get(s.getTile(i, j).getId()).setRight(null);
                    Nodes.add(Tile_GraphNode.get(s.getTile(i, j).getId()));
                }
                //We are in the middle of the shelfie
                if (i != 5 && j != 4) {
                    Tile_GraphNode.get(s.getTile(i, j).getId()).setUp(Tile_GraphNode.get(s.getTile(i+1, j).getId()));
                    Tile_GraphNode.get(s.getTile(i, j).getId()).setRight(Tile_GraphNode.get(s.getTile(i, j+1).getId()));
                    Nodes.add(Tile_GraphNode.get(s.getTile(i, j).getId()));
                }
            }
        }

        return Nodes;
    }
}
