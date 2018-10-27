/*** In The Name of Allah ***/
package jgram.mining.frequent;

import java.util.Set;
import org.jgrapht.Graph;

/**
 * General interface for frequent subgraph mining algorithms.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public interface FrequentSubgraphMining {
    
    /**
     * Initialization of the algorithm.
     * This method is optional.
     * 
     * @param params  any parameters required for initializing the algorithm
     */
    default void init(Object... params) {
        ; // NOP!
    }
    
    /**
     * Perform the mining for frequent subgraphs in the given graph set.
     * The result of this operation is a set of graphs 
     * which are frequent subgraphs in the input given graph set.
     * 
     * @param graphSet  the input set of graphs to be mined
     * @return          the result set of frequent subgraphs
     */
    public Set<Graph> mine(Set<Graph> graphSet);
    
    /**
     * Fetch the set of frequent subgraphs resulted after the mining operation.
     * 
     * @return  returns the result of the mine(...) method (without running it again);
     *          or null if the mine(...) method has not completed
     */
    public Set<Graph> getResult();
    
}
