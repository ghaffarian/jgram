/*** In The Name of Allah ***/
package ghaffarian.jgram.matching;

import java.util.HashMap;
import java.util.Map;
import ghaffarian.graphs.Edge;

/**
 * Isomorphism of two graphs.
 * An isomorphism is a special case of a matching between two graphs.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class Isomorphism<V,E> extends Matching<V,E> {
    
    
    /**
     * Creates a new Isomorphism object, 
     * with isomorphism set to false, and 
     * empty correspondence for nodes and edges.
     */
    public Isomorphism() {
        this(false);
    }
    
    /**
     * Creates a new Isomorphism object, 
     * with the given isomorphism status, and 
     * empty correspondence for nodes and edges.
     * 
     * @param isomorphic  the isomorphism status
     */
    public Isomorphism(boolean isomorphic) {
        this(isomorphic, new HashMap<V,V>(), new HashMap<Edge<V,E>,Edge<V,E>>());
    }
    
    /**
     * Creates a new Isomorphism object, 
     * with the given score, and correspondence for nodes and edges.
     * 
     * @param isomorphic    the isomorphism status
     * @param nodeMappings  the correspondence of nodes from one graph to nodes of the other graph
     * @param edgeMappings  the correspondence of edges from one graph to edges of the other graph
     */
    public Isomorphism(boolean isomorphic, Map<V,V> nodeMappings, Map<Edge<V,E>,Edge<V,E>> edgeMappings) {
        this.score = isomorphic ? 1.0f : 0.0f;
        vertexCorrespondence = nodeMappings;
        edgeCorrespondence = edgeMappings;
    }
    
    /**
     * Check whether the matched graphs are isomorphic or not.
     * Exact graph matching (isomorphism) algorithms will set the score to 1
     * if the graphs are isomorphic, and 0 otherwise.
     * 
     * @return  true if the matching score is 1, otherwise false.
     */
    public boolean isIsomorphic() {
        return Math.abs(score - 1.0f) < 0.1f;
    }

}
