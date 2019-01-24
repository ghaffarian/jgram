/*** In The Name of Allah ***/
package ghaffarian.jgram.mining.frequent;

import ghaffarian.graphs.Edge;
import ghaffarian.graphs.Graph;
import ghaffarian.graphs.Matcher;
import java.util.List;
import java.util.Set;

/**
 * General interface for frequent subgraph mining algorithms.
 *
 * @author Seyed Mohammad Ghaffarian
 */
public abstract class FrequentSubgraphMining<V, E> {

    protected Matcher<V> vertexMatcher;
    protected Matcher<Edge<V,E>> edgeMatcher;
    
    /**
     * Set the vertex-matcher object.
     */
    public void setVertexMatcher(Matcher<V> matcher) {
        vertexMatcher = matcher;
    }
    
    /**
     * Get the vertex-matcher object.
     */
    public Matcher<V> getVertexMatcher() {
        return vertexMatcher;
    }
    
    /**
     * Set the edge-matcher object.
     */
    public void setEdgeMatcher(Matcher<Edge<V,E>> matcher) {
        edgeMatcher = matcher;
    }
    
    /**
     * Get the edge-matcher object.
     */
    public Matcher<Edge<V,E>> getEdgeMatcher() {
        return edgeMatcher;
    }

    /**
     * Perform the mining for frequent subgraphs in the given graph set.
     * The result of this operation is a set of graphs 
     * which are frequent subgraphs in the input given graph set.
     * 
     * @param graphSet  the input set of graphs to be mined
     * @return          the result set of frequent subgraphs
     */
    public abstract Set<Graph<V,E>> mine(List<Graph<V,E>> graphSet);
    
    /**
     * Fetch the set of frequent subgraphs resulted after the mining operation.
     * 
     * @return  returns the result of the mine(...) method (without running it again);
     *          or null if the mine(...) method has not completed
     */
    public abstract Set<Graph<V,E>> getResult();
    
}
