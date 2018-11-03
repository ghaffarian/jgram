/*** In The Name of Allah ***/
package jgram.mining.frequent;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import jgram.utils.CollectionUtils;
import jgram.utils.GraphUtils;
import org.javatuples.Triplet;
import org.jgrapht.Graph;
import org.jgrapht.graph.AbstractBaseGraph;

/**
 * A simple greedy frequent subgraph mining (FSM) algorithm designed by the author.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class NaiveFSM implements FrequentSubgraphMining {
    
    private final int AVRG_EDGE_PER_GRAPH = 16;
    private final int MIN_LIST_CAPACITY = 16;
    private final float MIN_SUP;
    
    private boolean miningDone;
    private List<Graph> result;
    private List<Graph> graphDataset;

    /**
     * Construct a new instance of greedy-frequent-subgraph-mining.
     * 
     * @param minSup  the minimum support ratio (0.0 .. 1.0) to consider a subgraph as frequent
     */
    public NaiveFSM(float minSup) {
        result = null;
        graphDataset = null;
        MIN_SUP = minSup;
        miningDone = false;
    }

    @Override
    public List<Graph> mine(List<Graph> graphDataset) {
        this.graphDataset = graphDataset;
        // 1. Calculate the frequency for all edges from all graphs
        Map<Triplet, Integer> allEdges = new LinkedHashMap<>(graphDataset.size() * AVRG_EDGE_PER_GRAPH, 0.8f);
        for (Graph graph: graphDataset) {
            for (Object edge: graph.edgeSet()) {
                Triplet edgeInfo = Triplet.with(graph.getEdgeSource(edge), graph.getEdgeTarget(edge), edge);
                Integer freq = allEdges.get(edgeInfo);
                if (freq == null)
                    allEdges.put(edgeInfo, 1);
                else
                    allEdges.put(edgeInfo, ++freq);
            }
        }
        // 2. Remove non-frequent edges
        int threshold = Math.round(MIN_SUP * graphDataset.size());
        Iterator<Map.Entry<Triplet, Integer>> it = allEdges.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Triplet, Integer> entry = it.next();
            if (entry.getValue() < threshold)
                it.remove();
        }
        // 3. Sort all frequent edges
        allEdges = CollectionUtils.sortByValue(allEdges);
        // 4. start generating graphs using frequent edges and check their frequency
        Deque<Triplet> frequentEdges = new ArrayDeque<>(allEdges.size());
        for (Map.Entry<Triplet, Integer> edge: allEdges.entrySet())
            frequentEdges.add(edge.getKey());
        AbstractBaseGraph emptyGraph = (AbstractBaseGraph) ((AbstractBaseGraph) graphDataset.get(0)).clone();
        emptyGraph.removeAllVertices(emptyGraph.vertexSet());
        result = new ArrayList(Math.max(MIN_LIST_CAPACITY, 2 * threshold));
        expandGraphs(emptyGraph, result, frequentEdges);
        miningDone = true;
        return result;
    }
    
    /**
     * 
     * @param base
     * @param freqSubgraphs
     * @param candidateEdges 
     */
    private void expandGraphs(AbstractBaseGraph base, List<Graph> freqSubgraphs, Deque<Triplet> candidateEdges) {
        boolean isExpanded = false;
        for (int i = 0; i < candidateEdges.size(); ++i) {
            AbstractBaseGraph expanded = (AbstractBaseGraph) base.clone();
            Triplet edge = candidateEdges.remove();
            expanded.addVertex(edge.getValue0());
            expanded.addVertex(edge.getValue1());
            expanded.addEdge(edge.getValue0(), edge.getValue1(), edge.getValue2());
            if (countSupport(expanded) >= Math.round(MIN_SUP * graphDataset.size())) {
                isExpanded = true;
                expandGraphs(expanded, freqSubgraphs, candidateEdges);
            }
        }
        if (!isExpanded)
            freqSubgraphs.add(base);
    }
    
    /**
     * 
     * @param g
     * @return 
     */
    private int countSupport(Graph g) {
        int freq = 0;
        for (Graph base: graphDataset) {
            if (GraphUtils.isSubgraph(base, g))
                ++freq;
        }
        return freq;
    }
    

    @Override
    public List<Graph> getResult() {
        if (!miningDone)
            return null;
        return result;
    }

}
