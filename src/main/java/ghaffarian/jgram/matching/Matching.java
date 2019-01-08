/*** In The Name of Allah ***/
package ghaffarian.jgram.matching;

import java.util.HashMap;
import java.util.Map;
import ghaffarian.graphs.Edge;

/**
 * Matching of two graphs.
 * Objects of this class can hold the result of a graph matching operation.
 * This class can be used both for exact (isomorphism) or inexact graph-matchings.
 * For exact graph-matching, the matching score is either 1 (isomorphic) or 0 (non-isomorphic).
 * For inexact graph-matching, the matching score can be any arbitrary floating-point value,
 * dependent on the matching algorithm.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class Matching<V,E> {
    
    /* package private */ float score;
    
    /* package private */ Map<V,V> vertexCorrespondence;
    
    /* package private */ Map<Edge<V,E>,Edge<V,E>> edgeCorrespondence;
    
    /**
     * Creates a new Matching object, 
     * with score set to zero, and 
     * empty correspondence for vertices and edges.
     */
    public Matching() {
        this(0);
    }
    
    /**
     * Creates a new Matching object, 
     * with the given score value, and 
     * empty correspondence for vertices and edges.
     * 
     * @param score  the matching score
     */
    public Matching(float score) {
        this(score, new HashMap<V,V>(), new HashMap<Edge<V,E>,Edge<V,E>>());
    }
    
    /**
     * Creates a new Matching object, 
     * with the given score, and correspondence for nodes and edges.
     * 
     * @param score           the matching score
     * @param vertexMappings  the correspondence of nodes from one graph to nodes of the other graph
     * @param edgeMappings    the correspondence of edges from one graph to edges of the other graph
     */
    public Matching(float score, Map<V,V> vertexMappings, Map<Edge<V,E>,Edge<V,E>> edgeMappings) {
        this.score = score;
        vertexCorrespondence = vertexMappings;
        edgeCorrespondence = edgeMappings;
    }
    
    /**
     * Get the matching score.
     * 
     * @return  the matching score as an integer value
     */
    public float getScore() {
       return score; 
    }
    
    /**
     * Get the number of vertex mappings in this matching instance.
     */
    public int getVertexCorrespondenceCount() {
        return vertexCorrespondence.size();
    }
    
    /**
     * Get the number of edge mappings in this matching instance.
     */
    public int getEdgeCorrespondenceCount() {
        return edgeCorrespondence.size();
    }
    
    /**
     * Get the corresponding node for the given node, based on this matching.
     * 
     * @param vertex  the node to find its correspondence
     * @return        the corresponding node for the given node, 
     *                or null if there is no correspondence
     */
    public V getCorrespondingVertex(V vertex) {
        return vertexCorrespondence.get(vertex);
    }
    
    /**
     * Get the corresponding edge for the given edge, based on this matching.
     * 
     * @param edge  the edge to find its correspondence
     * @return      the corresponding edge for the given edge
     *              or null if there is no correspondence
     */
    public Edge<V,E> getCorrespondingEdge(Edge<V,E> edge) {
        return edgeCorrespondence.get(edge);
    }
    
}
