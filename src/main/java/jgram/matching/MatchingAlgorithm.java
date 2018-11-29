/*** In The Name of Allah ***/
package jgram.matching;

import jgram.graphs.Graph;

/**
 * Interface of graph matching algorithms.
 * A graph matching algorithm checks for a matching between two given graphs.
 * The result of a matching algorithm consists of a matching score, 
 * and a mapping between nodes and edges of one graph to 
 * corresponding nodes and edges of the other graph.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public interface MatchingAlgorithm<V,E> {
    
    /**
     * Perform the matching between two given graphs.
     * The result of this operation is a score and 
     * correspondence mapping of nodes and edges of the two graphs.
     * 
     * @param g1  the first given graph
     * @param g2  the second given graph
     * @return    the result of matching between g1 and g2
     */
    public Matching<V,E> match(Graph<V,E> g1, Graph<V,E> g2);
    
    /**
     * Fetch result of the matching algorithm.
     * 
     * @return  returns the result of the match(...) method (without running it again);
     *          or null if the match(...) method has not completed
     */
    public Matching<V,E> getResult();
    
}
