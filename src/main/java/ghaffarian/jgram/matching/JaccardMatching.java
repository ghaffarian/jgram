/*** In The Name of Allah ***/
package ghaffarian.jgram.matching;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import ghaffarian.graphs.Edge;
import ghaffarian.graphs.Graph;

/**
 * An inexact graph matching (similarity) algorithm based on the Jaccard-index for set similarity.
 * The Jaccard matching on graphs can be performed either based on edges or vertices.
 * When edge similarity is done, each graph is considered a set of edges.
 * When vertex similarity is done, each graph is considered a set of vertices.
 * 
 * Another type of Jaccard matching is subgraph-matching.
 * Subgraph-matching differs from similarity such that similarity tries to compute 
 * the overall similarity score of two graphs; where as subgraph matching computes
 * the matching score of the smaller graph with a subgraph of the larger graph.
 * 
 * More precisely, graph similarity computes intersection over union, where as
 * subgraph-matching computes intersection over size of the smaller graph.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class JaccardMatching<V,E> implements MatchingAlgorithm<V,E> {

    /**
     * Enumeration of different Jaccard matching types.
     * The matching is either edge-based or vertex-based.
     * Moreover, the matching is either graph similarity (overall similarity of two graphs),
     * or subgraph-matching which matches the smaller graph with a subgraph of the larger graph.
     */
    public enum Type {
        EDGE_SIMILARITY (true, true),
        VERTEX_SIMILARITY (false, true),
        EDGE_SUBGRAPH_MATCHING (true, false),
        VERTEX_SUBGRAPH_MATCHING (false, false);
        
        final boolean isEdgeBased;
        final boolean isSimilarity;
        
        private Type(boolean edgeBased, boolean similarity) {
            isEdgeBased = edgeBased;
            isSimilarity = similarity;
        }
    }
    
    public final Type TYPE;
    
    private Matching<V,E> matchingResult;
    
    /**
     * Constructs a new instance of Jaccard matching algorithm.
     * The matching type is edges-based similarity (JaccardMatching.Type.EDGE_SIMILARITY).
     */
    public JaccardMatching() {
        this(Type.EDGE_SIMILARITY);
    }
    
    /**
     * Constructs a new instance of Jaccard matching algorithm.
     * The matching type is determined via the given parameter.
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
        Graph<V, E> smallGraph;
        Graph<V,E> largGraph;
        if (TYPE.isEdgeBased) {
            // Edge based Jaccard matching
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
            if (TYPE.isSimilarity)
                union = smallGraph.edgeCount() + largGraph.edgeCount() - intersect;
            else 
                union = smallGraph.edgeCount();
        } else {
            // Vertex based Jaccard matching
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
            if (TYPE.isSimilarity)
                union = smallGraph.vertexCount() + largGraph.vertexCount() - intersect;
            else
                union = smallGraph.vertexCount();
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
