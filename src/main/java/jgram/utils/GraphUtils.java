/*** In The Name of Allah ***/
package jgram.utils;

import org.jgrapht.Graph;

/**
 * A class providing utility methods for processing graphs.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class GraphUtils {
    
    /**
     * Checks whether the a given graph is a subgraph of another given graph.
     * 
     * @param base  the base-graph
     * @param sub   the sub-graph
     * @return      returns `true` if `sub` is a subgraph of `base`; otherwise `false`.
     */
    public static boolean isSubgraph(Graph base, Graph sub) {
        for (Object v: sub.vertexSet())
            if (!base.containsVertex(v))
                return false;
        for (Object e: sub.edgeSet())
            if (!base.containsEdge(e) || !base.containsEdge(sub.getEdgeSource(e), sub.getEdgeTarget(e)))
                return false;
        return true;
    }

}
