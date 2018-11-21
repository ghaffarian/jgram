/*** In The Name of Allah ***/
package jgram.mining.frequent;

import java.util.List;
import java.util.Set;
import jgram.graphs.Graph;

/**
 * General interface for frequent subgraph mining algorithms.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public interface FrequentSubgraphMining<V,E> {
    
    /**
     * Perform the mining for frequent subgraphs in the given graph set.
     * The result of this operation is a set of graphs 
     * which are frequent subgraphs in the input given graph set.
     * 
     * @param graphSet  the input set of graphs to be mined
     * @return          the result set of frequent subgraphs
     */
    public Set<Graph<V,E>> mine(List<Graph<V,E>> graphSet);
    
    /**
     * Fetch the set of frequent subgraphs resulted after the mining operation.
     * 
     * @return  returns the result of the mine(...) method (without running it again);
     *          or null if the mine(...) method has not completed
     */
    public Set<Graph<V,E>> getResult();
    
}
