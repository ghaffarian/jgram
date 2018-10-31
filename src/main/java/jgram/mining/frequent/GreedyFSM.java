/*** In The Name of Allah ***/
package jgram.mining.frequent;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import jgram.utils.CollectionUtils;
import org.javatuples.Triplet;
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
    public Set<Graph> mine(Set<Graph> graphSet) {
        // 1. Calculate the frequency for all edges from all graphs
        Class edgeClass = null;
        Map<Triplet, Integer> allEdges = new LinkedHashMap<>(graphSet.size() * 16, 0.8f);
        for (Graph graph: graphSet) {
            for (Object edge: graph.edgeSet()) {
                edgeClass = edge.getClass();
                Triplet edgeInfo = Triplet.with(graph.getEdgeSource(edge), graph.getEdgeTarget(edge), edge);
                Integer freq = allEdges.get(edgeInfo);
                if (freq == null)
                    allEdges.put(edgeInfo, 1);
                else
                    allEdges.put(edgeInfo, ++freq);
            }
        }
        // 2. Remove non-frequent edges
        int threshold = Math.round(minSup * graphSet.size());
        Iterator<Map.Entry<Triplet, Integer>> it = allEdges.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Triplet, Integer> entry = it.next();
            if (entry.getValue() < threshold)
                it.remove();
        }
        // 3. Sort all frequent edges
        allEdges = CollectionUtils.sortByValue(allEdges);
        // 4. start generating graphs using frequent edges and test their frequency
        for (Map.Entry<Triplet, Integer> edge: allEdges.entrySet()) {
            // iterate until you pass the current edge
            it = allEdges.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Triplet, Integer> entry = it.next();
                if (entry.equals(edge))
                    break;
            }
            //
            
            LinkedHashMap
        }
        Graph g = new SimpleGraph(edgeClass);
        g.add
        result = new HashSet<Graph>();
        return result;
    }
    
    

    @Override
    public Set<Graph> getResult() {
        return result;
    }

}
