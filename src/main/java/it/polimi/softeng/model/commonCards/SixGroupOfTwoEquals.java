package it.polimi.softeng.model.commonCards;
import it.polimi.softeng.model.Shelfie;
import it.polimi.softeng.model.graphForScoreCount.graphNode;
import java.util.ArrayList;

import static it.polimi.softeng.model.graphForScoreCount.graphNode.mapShelfieToGraph;

public class SixGroupOfTwoEquals extends CommonCards {

    /**
     * @param s is the shelfie
     * @return true if the shape is satisfied, false if not
     */
    @Override
    public boolean verifyShape(Shelfie s)
    {
        int tileCounted = 0;
        int groupCounter = 0;
        int counter = 0;
        ArrayList<graphNode> Nodes = new ArrayList<>();
        Nodes = mapShelfieToGraph(s);
        //Iterate in nodes to visit all nodes of the graph
        for (graphNode n: Nodes)
        {
            counter = 0;

            if (!n.isVisited()) {
                try
                {
                    tileCounted = n.visitGraphWithUpperBound(n, counter, 2);
                }
                catch (Exception UpperBoundReached)
                {
                    tileCounted = 2;
                }
            }

            if (tileCounted == 2)
                groupCounter++;
        }

        if (groupCounter >= 6)
            return true;
        else
            return false;
        }
}