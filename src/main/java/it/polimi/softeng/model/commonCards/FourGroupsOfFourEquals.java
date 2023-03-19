package it.polimi.softeng.model.commonCards;

import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.Tile;
import it.polimi.softeng.model.graphForScoreCount.graphNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static it.polimi.softeng.model.graphForScoreCount.graphNode.mapShelfieToGraph;
import static it.polimi.softeng.model.graphForScoreCount.graphNode.mapTileToGraphNode;

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
        ArrayList<graphNode> Nodes = new ArrayList<>();
        Map<Integer, graphNode> Tile_GraphNode = mapTileToGraphNode(s);

        Nodes = mapShelfieToGraph(s);
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