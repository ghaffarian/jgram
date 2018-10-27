/*** In The Name of Allah ***/
package jgram.mining.frequent;

import java.util.HashSet;
import java.util.Set;
import org.jgrapht.Graph;

/**
 * A simple greedy frequent subgraph mining (FSM) algorithm designed by the author.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class GreedyFSM implements FrequentSubgraphMining {
    
    private HashSet<Graph> result = null;

    public void init(float minSup) {
        FrequentSubgraphMining.super.init(minSup);
    }

    @Override
    public Set<Graph> mine(Set<Graph> graphSet) {
        // 1. create a sorted set of all frequent edges in graphSet
        // 2. start generating graphs using frequent edges and test their frequency
        result = new HashSet<>();
        return result;
    }
    
    

    @Override
    public Set<Graph> getResult() {
        return result;
    }

}
