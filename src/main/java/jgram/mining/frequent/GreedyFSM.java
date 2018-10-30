/*** In The Name of Allah ***/
package jgram.mining.frequent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import jgram.utils.CollectionUtils;
import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;

/**
 * A simple greedy frequent subgraph mining (FSM) algorithm designed by the author.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class GreedyFSM implements FrequentSubgraphMining {
    
    private float minSup;
    private HashSet<Graph> result = null;

    public void init(float minSup) {
        FrequentSubgraphMining.super.init(minSup);
    }

    @Override
    public <V,E> Set<Graph<V,E>> mine(Set<Graph<V,E>> graphSet) {
        // 1. Calculate the frequency for all edges from all graphs
        Map<E, Integer> allEdges = new HashMap<>(graphSet.size() * 16, 0.8f);
        for (Graph<V,E> graph: graphSet) {
            for (E edge: graph.edgeSet()) {
                Integer freq = allEdges.get(edge);
                if (freq == null)
                    allEdges.put(edge, 1);
                else
                    allEdges.put(edge, ++freq);
            }
        }
        // 2. Remove non-frequent edges
        int threshold = Math.round(minSup * graphSet.size());
        Iterator<Map.Entry<E, Integer>> it = allEdges.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<E, Integer> entry = it.next();
            if (entry.getValue() < threshold)
                it.remove();
        }
        // 3. Sort all frequent edges
        allEdges = CollectionUtils.sortByValue(allEdges);
        // 4. start generating graphs using frequent edges and test their frequency
        Graph<V,E> g;
        result = new HashSet<>();
        return result;
    }
    
    

    @Override
    public Set<Graph> getResult() {
        return result;
    }

}
