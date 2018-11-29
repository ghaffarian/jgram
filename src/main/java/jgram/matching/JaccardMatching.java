/*** In The Name of Allah ***/
package jgram.matching;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import jgram.graphs.Edge;
import jgram.graphs.Graph;

/**
 * An inexact graph matching (similarity) algorithm based on the Jaccard-index for set similarity.
 * The Jaccard matching on graphs can be performed either based on edges or vertices.
 * When edge matching is done, each graph is considered a set of edges.
 * When vertex matching is done, each graph is considered a set of vertices.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class JaccardMatching<V,E> implements MatchingAlgorithm<V,E> {

    /**
     * Enumeration of different Jaccard matching types.
     */
    public enum Type {
        EDGE_MATCHING,
        VERTEX_MATCHING
    }
    
    public final Type TYPE;
    
    private Matching<V,E> matchingResult;
    
    /**
     * Constructs a new instance of Jaccard matching algorithm.
     * This instance uses graph edges as the basis for matching.
     */
    public JaccardMatching() {
        this(Type.EDGE_MATCHING);
    }
    
    /**
     * Constructs a new instance of Jaccard matching algorithm.
     * This matching type is determined via the given parameter.
     */
    public JaccardMatching(Type type) {
        TYPE = type;
        matchingResult = null;
    }

    @Override
    public Matching match(Graph<V,E> g1, Graph<V,E> g2) {
        int intersect = 0, union;
        Map<V,V> vertexMapping = new LinkedHashMap<>();
        Map<Edge<V,E>,Edge<V,E>> edgeMapping = new LinkedHashMap<>();
        Graph<V,E> smallGraph, largGraph;
        if (TYPE.equals(Type.EDGE_MATCHING)) {
            // Jaccard matching on edges
            if (g1.edgeCount() < g2.edgeCount()) {
                smallGraph = g1;
                largGraph = g2;
            } else {
                smallGraph = g2;
                largGraph = g1;
            }
            Enumeration<Edge<V,E>> edgesEnum = smallGraph.enumerateAllEdges();
            while (edgesEnum.hasMoreElements()) {
                Edge<V,E> edge = edgesEnum.nextElement();
                if (largGraph.containsEdge(edge)) {
                    ++intersect;
                    Edge<V,E> matchingEdge = findMatchingEdge(largGraph, edge);
                    vertexMapping.put(edge.source, matchingEdge.source);
                    vertexMapping.put(edge.target, matchingEdge.target);
                    edgeMapping.put(edge, matchingEdge);
                }
            }
            union = smallGraph.edgeCount() + largGraph.edgeCount() - intersect;
        } else {
            // Jaccard matching on verteices
            if (g1.vertexCount() < g2.vertexCount()) {
                smallGraph = g1;
                largGraph = g2;
            } else {
                smallGraph = g2;
                largGraph = g1;
            }
            Enumeration<V> vertexEnum = smallGraph.enumerateAllVertices();
            while (vertexEnum.hasMoreElements()) {
                V vertex = vertexEnum.nextElement();
                if (largGraph.containsVertex(vertex)) {
                    ++intersect;
                    vertexMapping.put(vertex, findMatchingVertex(largGraph, vertex));
                }
            }
            union = smallGraph.vertexCount() + largGraph.vertexCount() - intersect;
        }
        float score = ((float) intersect) / union;
        matchingResult = new Matching<>(score, vertexMapping, edgeMapping);
        return matchingResult;
    }
    
    
    private V findMatchingVertex(Graph<V,E> graph, V vertex) {
        Enumeration<V> vertices = graph.enumerateAllVertices();
        while (vertices.hasMoreElements()) {
            V nextVertex = vertices.nextElement();
            if (nextVertex.equals(vertex))
                return nextVertex;
        }
        return null;
    }

    
    private Edge<V,E> findMatchingEdge(Graph<V,E> graph, Edge<V, E> edge) {
        Enumeration<Edge<V,E>> edges = graph.enumerateAllEdges();
        while (edges.hasMoreElements()) {
            Edge<V,E> nextEdge = edges.nextElement();
            if (nextEdge.equals(edge))
                return nextEdge;
        }
        return null;
    }
    
    @Override
    public Matching<V,E> getResult() {
        return matchingResult;
    }
    
}
