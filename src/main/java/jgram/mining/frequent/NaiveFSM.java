/*** In The Name of Allah ***/
package jgram.mining.frequent;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    private final boolean ONLY_MAXIMAL;
    private final boolean ONLY_CONNECTED;
    private final boolean ALLOW_SINGLE_EDGE;
    
    private boolean miningDone;
    private Set<Graph<V,E>> finalResult;
    private List<Graph<V,E>> graphDataset;

    /**
     * Construct a new instance of naive frequent-subgraph-mining.
     * Frequent single-edges are not considered as patterns.
     * Only maximal frequent subgraphs are considered as patterns.
     * Only connected frequent subgraphs are considered as patterns.
     * 
     * @param minSup  the minimum support ratio (0.0 .. 1.0) to consider a subgraph as frequent
     */
    public NaiveFSM(float minSup) {
        this(minSup, true, false, true);
    }

    /**
     * Construct a new instance of naive frequent-subgraph-mining.
     * 
     * @param minSup       the minimum support ratio (0.0 .. 1.0) to consider a subgraph as frequent
     * @param maximal      specify if only maximal frequent patterns are desired
     * @param singleEdges  specify whether single-edges should be considered as patterns or not
     * @param connected    specify if only connected frequent patterns are desired
     */
    public NaiveFSM(float minSup, boolean maximal, boolean singleEdges, boolean connected) {
        MIN_SUP = minSup;
        ONLY_MAXIMAL = maximal;
        miningDone = false;
        ONLY_CONNECTED = connected;
        ALLOW_SINGLE_EDGE = singleEdges;
    }

    @Override
    public Set<Graph<V, E>> mine(List<Graph<V, E>> graphSet) {
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
        Set<Graph<V,E>> frequentPatterns = new LinkedHashSet<>(Math.max(MIN_LIST_CAPACITY, 2 * threshold));
        while (!frequentEdges.isEmpty()) {
        //for (Edge<V,E> edge: allEdgesFrequencies.keySet()) {
            Graph<V,E> baseGraph = new Graph<>(graphSet.get(0).IS_DIRECTED);
            Edge<V,E> edge = frequentEdges.remove();
            //frequentEdges.remove(edge);
            baseGraph.addVertex(edge.source);
            baseGraph.addVertex(edge.target);
            baseGraph.addEdge(edge);
            expandGraphs(baseGraph, frequentPatterns, frequentEdges);
            //frequentEdges.add(edge);
        }
        System.out.println("# of frequent patterns = " + frequentPatterns.size());
        // 5. Remove duplicates, non-maximals or un-connected patterns as desired
        //frequentPatterns = removeDuplicates(frequentPatterns);
        //System.out.println("# of distinct patterns = " + frequentPatterns.size());
        finalResult = new LinkedHashSet<>(Math.max(MIN_LIST_CAPACITY, 2 * threshold));
        Iterator<Graph<V,E>> iter = frequentPatterns.iterator();
        while (iter.hasNext()) {
            Graph<V,E> pattern = iter.next();
            boolean isMaximal = true;
            if (ONLY_MAXIMAL) {
                for (Graph<V, E> graph : frequentPatterns) {
                    if (pattern.isProperSubgraphOf(graph)) {
                        isMaximal = false;
                        break;
                    }
                }
            }
            if (isMaximal) {
                if ((!ONLY_CONNECTED) || pattern.isConnected())
                    finalResult.add(pattern);
            } else
                iter.remove();
        }
        miningDone = true;
        return finalResult;
    }
    
    /**
     * Expand the given base graph using the set of candidate edges and calculate the support.
     * If the calculated support is above the min threshold, add it to the set of frequents.
     */
    private boolean expandGraphs(Graph<V,E> base, Set<Graph<V,E>> freqSubgraphs, Deque<Edge<V,E>> candidateEdges) {
        boolean canBeMaximal = true;
        if (!candidateEdges.isEmpty()) {
            Edge<V, E> edge = candidateEdges.remove();
            // 1st, try to expand base graph including this edge
            if (!ONLY_CONNECTED || base.containsVertex(edge.source) || base.containsVertex(edge.target)) {
                base.addVertex(edge.source);
                base.addVertex(edge.target);
                base.addEdge(edge);
                if (countSupport(base) >= Math.round(MIN_SUP * graphDataset.size())) {
                    canBeMaximal = false;
                    expandGraphs(base, freqSubgraphs, candidateEdges);
                }
                // Remove the added edge from base
                base.removeEdge(edge);
                if (base.getInDegree(edge.source) + base.getOutDegree(edge.source) == 0)
                    base.removeVertex(edge.source);
                if (base.getInDegree(edge.target) + base.getOutDegree(edge.target) == 0)
                    base.removeVertex(edge.target);
            }
            // 2nd, try to expand base graph excluding this edge
            canBeMaximal &= expandGraphs(base, freqSubgraphs, candidateEdges);
            // put back the removed edge exactly where it was
            candidateEdges.push(edge);
        }
        // if it can be maximal add a copy of it
        if (canBeMaximal && (ALLOW_SINGLE_EDGE || base.edgeCount() > 1))
            freqSubgraphs.add(new Graph<>(base));
        return canBeMaximal;
    }
    
    /**
     * Remove duplicate graphs from the given collection and return a Set of unique graphs. 
     */
    private Set<Graph<V,E>> removeDuplicates(Collection<Graph<V,E>> collection) {
        Set<Graph<V,E>> result = new LinkedHashSet<>();
        for (Graph<V,E> graph: collection) {
            boolean unique = true;
            for (Graph<V,E> added: result) {
                if (added.equals(graph)) {
                    unique = false;
                    break;
                }
            }
            if (unique)
                result.add(graph);
        }
        return result;
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
    public Set<Graph<V, E>> getResult() {
        if (!miningDone)
            return null;
        return finalResult;
    }

}
