/*** In The Name of Allah ***/
package jgram.utils;

import jgram.Edge;
import jgram.Vertex;
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
    public static boolean isSubgraph(Graph<Vertex, Edge> base, Graph<Vertex, Edge> sub) {
        boolean result = true;
        for (Vertex v: sub.vertexSet())
            if (!base.containsVertex(v)) {
                System.out.println("base not contains vertex " + v);
                result = false;
            } else 
                System.out.println("OK: vertex " + v);
        for (Edge e: sub.edgeSet()) {
            if (!base.containsEdge(e)) {
                System.out.println("base not contains edge " + e);
                result = false;
            }
            System.out.println("OK: edge " + e);
            //
            Vertex v_src = sub.getEdgeSource(e);
            Vertex v_trg = sub.getEdgeTarget(e);
            if (!base.containsEdge(v_src, v_trg)) {
                System.out.println("base not contains edge from " + v_src + " to " + v_trg);
                result = false;
            } else
                System.out.println("OK: edge from " + v_src + " to " + v_trg);
        }
        return result;
    }

}
