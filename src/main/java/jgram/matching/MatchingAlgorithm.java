/*** In The Name of Allah ***/
package jgram.matching;

import org.jgrapht.Graph;

/**
 * Interface of graph matching algorithms.
 * A graph matching algorithm checks for a matching between two given graphs.
 * The result of a matching algorithm consists of a matching score, 
 * and a mapping between nodes and edges of one graph to 
 * corresponding nodes and edges of the other graph.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public interface MatchingAlgorithm {
    
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
     * Perform the matching between two given graphs.
     * The result of this operation is a score and 
     * correspondence mapping of nodes and edges of the two graphs.
     * 
     * @param g1  the first given graph
     * @param g2  the second given graph
     * @return    the result of matching between g1 and g2
     */
    public Matching match(Graph g1, Graph g2);
    
    /**
     * Fetch result of the matching algorithm.
     * 
     * @return  returns the result of the match(...) method (without running it again);
     *          or null if the match(...) method has not completed
     */
    public Matching getResult();
    
}
