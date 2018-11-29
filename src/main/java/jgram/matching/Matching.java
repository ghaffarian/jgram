/*** In The Name of Allah ***/
package jgram.matching;

import java.util.HashMap;
import java.util.Map;

/**
 * Matching of two graphs.
 * Objects of this class can hold the result of a graph matching operation.
 * This class can be used both for exact (isomorphism) or inexact graph-matchings.
 * For exact graph-matching, the matching score is either 1 (isomorphic) or 0 (non-isomorphic).
 * For inexact graph-matching, the matching score can be any arbitrary integer value,
 * dependent on the matching algorithm.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class Matching<V,E> {
    
    /* package private */ int score;
    
    /* package private */ Map<V,V> nodeCorrespondence;
    
    /* package private */ Map<E,E> edgeCorrespondence;
    
    /**
     * Creates a new Matching object, 
     * with score set to zero, and 
     * empty correspondence for nodes and edges.
     */
    public Matching() {
        this(0);
    }
    
    /**
     * Creates a new Matching object, 
     * with the given score value, and 
     * empty correspondence for nodes and edges.
     * 
     * @param score  the matching score
     */
    public Matching(int score) {
        this(score, new HashMap<V,V>(), new HashMap<E,E>());
    }
    
    /**
     * Creates a new Matching object, 
     * with the given score, and correspondence for nodes and edges.
     * 
     * @param score         the matching score
     * @param nodeMappings  the correspondence of nodes from one graph to nodes of the other graph
     * @param edgeMappings  the correspondence of edges from one graph to edges of the other graph
     */
    public Matching(int score, Map<V,V> nodeMappings, Map<E,E> edgeMappings) {
        this.score = score;
        nodeCorrespondence = nodeMappings;
        edgeCorrespondence = edgeMappings;
    }
    
    /**
     * Get the matching score.
     * 
     * @return  the matching score as an integer value
     */
    public int getScore() {
       return score; 
    }
    
    /**
     * Get the corresponding node for the given node, based on this matching.
     * 
     * @param node  the node to find its correspondence
     * @return      the corresponding node for the given node, 
     *              or null if there is no correspondence
     */
    public V getCorrespondingNode(V node) {
        return nodeCorrespondence.get(node);
    }
    
    /**
     * Get the corresponding edge for the given edge, based on this matching.
     * 
     * @param edge  the edge to find its correspondence
     * @return      the corresponding edge for the given edge
     *              or null if there is no correspondence
     */
    public E getCorrespondingEdge(E edge) {
        return edgeCorrespondence.get(edge);
    }
    
}
