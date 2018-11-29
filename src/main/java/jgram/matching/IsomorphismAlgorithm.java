/*** In The Name of Allah ***/
package jgram.matching;

import jgram.graphs.Graph;

/**
 * Interface of graph isomorphism algorithms.
 * A graph isomorphism algorithm checks whether two given graphs are isomorphic or not.
 * The result of an isomorphism algorithm consists of a boolean output, 
 * and a mapping between nodes and edges of one graph to 
 * corresponding nodes and edges of the other graph.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public interface IsomorphismAlgorithm extends MatchingAlgorithm {
    
    @Override
    public Isomorphism match(Graph g1, Graph g2);
    
    @Override
    public Isomorphism getResult();

}
