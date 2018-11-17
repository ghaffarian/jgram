/*** In The Name of Allah ***/
package jgram.mining.frequent;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import jgram.graphs.Edge;
import jgram.graphs.Graph;
import jgram.utils.CollectionUtils;

/**
 * A naive frequent subgraph mining (FSM) algorithm.
 * This algorithm is not efficient, nor scalable!
 * Just for getting started with FSM!
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class NaiveFSM<V,E> implements FrequentSubgraphMining<V,E> {
    
    private final int AVRG_EDGE_PER_GRAPH = 16;
    private final int MIN_LIST_CAPACITY = 16;
    private final float MIN_SUP;
    
    private boolean miningDone;
    private List<Graph<V,E>> result;
    private List<Graph<V,E>> graphDataset;

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
    public List<Graph<V, E>> mine(List<Graph<V, E>> graphSet) {
        this.graphDataset = graphSet;
        // 1. Calculate the frequency for all edges from all graphs
        Map<Edge, Integer> allEdgesFrequencies = new LinkedHashMap<>(graphDataset.size() * AVRG_EDGE_PER_GRAPH, 0.8f);
        for (Graph graph: graphDataset) {
            Enumeration<Edge<V,E>> edges = graph.enumerateAllEdges();
            while (edges.hasMoreElements()) {
                Edge<V,E> edge = edges.nextElement();
                Integer freq = allEdgesFrequencies.get(edge);
                if (freq == null)
                    allEdgesFrequencies.put(edge, 1);
                else
                    allEdgesFrequencies.put(edge, ++freq);
            }
        }
        // 2. Remove non-frequent edges
        int threshold = Math.round(MIN_SUP * graphDataset.size());
        Iterator<Map.Entry<Edge, Integer>> it = allEdgesFrequencies.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Edge, Integer> entry = it.next();
            if (entry.getValue() < threshold)
                it.remove();
        }
        // 3. Sort all frequent edges
        allEdgesFrequencies = CollectionUtils.sortByValue(allEdgesFrequencies);
        // 4. Start generating graphs using frequent edges and check their frequency
        Deque<Edge<V,E>> frequentEdges = new ArrayDeque<>(allEdgesFrequencies.size());
        for (Map.Entry<Edge, Integer> edge: allEdgesFrequencies.entrySet())
            frequentEdges.add(edge.getKey());
        Graph<V,E> emptyGraph = new Graph<>(graphSet.get(0).IS_DIRECTED);
        result = new ArrayList<>(Math.max(MIN_LIST_CAPACITY, 2 * threshold));
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
    private void expandGraphs(Graph<V,E> base, List<Graph<V,E>> freqSubgraphs, Deque<Edge<V,E>> candidateEdges) {
        boolean isExpanded = false;
        for (int i = 0; i < candidateEdges.size(); ++i) {
            Graph<V,E> expanded = new Graph<>(base);
            Edge<V,E> edge = candidateEdges.remove();
            expanded.addVertex(edge.source);
            expanded.addVertex(edge.target);
            expanded.addEdge(edge);
            if (countSupport(expanded) >= Math.round(MIN_SUP * graphDataset.size())) {
                isExpanded = true;
                expandGraphs(expanded, freqSubgraphs, candidateEdges);
            }
        }
        if (!isExpanded)
            freqSubgraphs.add(base);
    }
    
    /**
     * Return the support of the given graph in this dataset.
     * The support of a graph is the number of graphs in the dataset 
     * where g is its subgraph.
     */
    private int countSupport(Graph g) {
        int freq = 0;
        for (Graph base: graphDataset) {
            if (g.isSubgraphOf(base))
                ++freq;
        }
        return freq;
    }
    

    @Override
    public List<Graph<V, E>> getResult() {
        if (!miningDone)
            return null;
        return result;
    }

}
