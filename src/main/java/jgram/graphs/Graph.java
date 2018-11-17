/*** In The Name of Allah ***/
package jgram.graphs;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * A generic class for labeled graphs.
 * This class supports both directed and undirected graphs.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class Graph<V,E> {
    
    public final boolean IS_DIRECTED;
    
    private Set<V> allVertices;
    private Set<Edge<V,E>> allEdges;
    private Map<V, Set<Edge<V,E>>> inEdges;
    private Map<V, Set<Edge<V,E>>> outEdges;
    
    /**
     * Construct a new empty Graph object with the given direction property.
     * 
     * @param directed specify if graph is directed (true) or undirected (false)
     */
    public Graph(boolean directed) {
        IS_DIRECTED = directed;
        allEdges = new LinkedHashSet<>(32);
        allVertices = new LinkedHashSet<>();
        inEdges = new LinkedHashMap<>();
        outEdges = new LinkedHashMap<>();
    }
    
    /**
     * Copy constructor: 
     * create a new Graph instance by copying the state of the given Graph object.
     * 
     * @param graph the Graph object to be copied
     */
    public Graph(Graph graph) {
        IS_DIRECTED = graph.IS_DIRECTED;
        inEdges = new LinkedHashMap<>(graph.inEdges);
        outEdges = new LinkedHashMap<>(graph.outEdges);
        allEdges = new LinkedHashSet<>(graph.allEdges);
        allVertices = new LinkedHashSet<>(graph.allVertices);
    }
    
    /**
     * Add the given vertex to this graph.
     * 
     * @return true if the vertex is added, or
     *         false if such vertex is already in the graph.
     */
    public boolean addVertex(V v) {
        if (allVertices.add(v)) {
            inEdges.put(v, new LinkedHashSet<>());
            outEdges.put(v, new LinkedHashSet<>());
            return true;
        }
        return false;
    }
    
    /**
     * Remove the given vertex from this graph.
     * 
     * @return true if the vertex is removed, or
     *         false if no such vertex is in the graph.
     */
    public boolean removeVertex(V v) {
        if (allVertices.remove(v)) {
            allEdges.removeAll(inEdges.remove(v));
            allEdges.removeAll(outEdges.remove(v));
            return true;
        }
        return false;
    }
    
    /**
     * Add the given edge to this graph.
     * Both vertices (source and target) of the edge must be in the graph
     * otherwise, an exception is thrown indicating this issue.
     * 
     * @return true if the edge is added, or
     *         false if the edge is already in the graph.
     */
    public boolean addEdge(Edge<V,E> e) {
        if (!allVertices.contains(e.source))
            throw new IllegalArgumentException("No such source-vertex in this graph!");
        if (!allVertices.contains(e.target))
            throw new IllegalArgumentException("No such target-vertex in this graph!");
        if (allEdges.add(e)) {
            inEdges.get(e.target).add(e);
            outEdges.get(e.source).add(e);
            return true;
        }
        return false;
    }
    
    /**
     * Remove the given edge from this graph.
     * 
     * @return true if the vertex is removed, or
     *         false if no such vertex is in the graph.
     */
    public boolean removeEdge(Edge<V,E> e) {
        if (allEdges.remove(e)) {
            inEdges.get(e.target).remove(e);
            outEdges.get(e.source).remove(e);
            return true;
        }
        return false;
    }
    
    /**
     * Remove all edges in this graph between the given source vertex and target vertex.
     * 
     * @return the set of edges removed from this graph as a result of this operation.
     */
    public Set<Edge<V,E>> removeEdges(V src, V trgt) {
        if (!allVertices.contains(src))
            throw new IllegalArgumentException("No such source-vertex in this graph!");
        if (!allVertices.contains(trgt))
            throw new IllegalArgumentException("No such target-vertex in this graph!");
        Set<Edge<V,E>> iterSet;
        Set<Edge<V,E>> removed = new LinkedHashSet<>();
        if (inEdges.get(trgt).size() > outEdges.get(src).size()) {
            iterSet = outEdges.get(src);
            Iterator<Edge<V,E>> it = iterSet.iterator();
            while (it.hasNext()) {
                Edge<V,E> next = it.next();
                if (next.target.equals(trgt)) {
                    it.remove();
                    allEdges.remove(next);
                    inEdges.get(trgt).remove(next);
                    removed.add(next);
                }
            }
        } else {
            iterSet = inEdges.get(trgt);
            Iterator<Edge<V,E>> it = iterSet.iterator();
            while (it.hasNext()) {
                Edge<V,E> next = it.next();
                if (next.source.equals(src)) {
                    it.remove();
                    allEdges.remove(next);
                    outEdges.get(src).remove(next);
                    removed.add(next);
                }
            }
        }
        return removed;
    }
    
    /**
     * Return a copy of the set of all edges in this graph.
     * This method has the overhead of creating of copy of the current set of edges.
     * Hence the returned collection is safe to use and modify (it is not linked to this graph).
     */
    public Set<Edge<V,E>> getEdgeSet() {
        return new LinkedHashSet<>(allEdges);
    }
    
    /**
     * Return a copy of the set of all vertices in this graph.
     * This method has the overhead of creating of copy of the current set of vertices.
     * Hence the returned collection is safe to use and modify (it is not linked to this graph).
     */
    public Set<V> getVertexSet() {
        return new LinkedHashSet<>(allVertices);
    }
    
    /**
     * Return a copy of the set of incoming edges to the given vertex.
     * This method has the overhead of creating of copy of the current set of incoming edges.
     * Hence the returned collection is safe to use and modify (it is not linked to this graph).
     */
    public Set<Edge<V,E>> getIncomingEdges(V v) {
        if (!allVertices.contains(v))
            throw new IllegalArgumentException("No such vertex in this graph!");
        return new LinkedHashSet<>(inEdges.get(v));
    }
    
    /**
     * Return a copy of the set of outgoing edges from the given vertex.
     * This method has the overhead of creating of copy of the current set of outgoing edges.
     * Hence the returned collection is safe to use and modify (it is not linked to this graph).
     */
    public Set<Edge<V,E>> getOutgoingEdges(V v) {
        if (!allVertices.contains(v))
            throw new IllegalArgumentException("No such vertex in this graph!");
        return new LinkedHashSet<>(outEdges.get(v));
    }
    
    /**
     * Return the count of incoming edges to the given vertex.
     */
    public int getInDegree(V v) {
        if (!allVertices.contains(v))
            throw new IllegalArgumentException("No such vertex in this graph!");
        return inEdges.get(v).size();
    }
    
    /**
     * Return the count of outgoing edges from the given vertex.
     */
    public int getOutDegree(V v) {
        if (!allVertices.contains(v))
            throw new IllegalArgumentException("No such vertex in this graph!");
        return outEdges.get(v).size();
    }
    
    /**
     * Return the set of edges with a label same as the given value.
     */
    public Set<Edge<V,E>> getEdgesWithLabel(E label) {
        Set<Edge<V,E>> edges = new LinkedHashSet<>();
        for (Edge e: allEdges) {
            if (e.label.equals(label))
                edges.add(e);
        }
        return edges;
    }
    
    /**
     * Check if this graph contains the given edge.
     */
    public boolean containsEdge(Edge<V,E> e) {
        return allEdges.contains(e);
    }
    
    /**
     * Check if this graph contains the given vertex.
     */
    public boolean containsVertex(V v) {
        return allVertices.contains(v);
    }
    
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (V vrtx: allVertices) {
            for (Edge<V,E> edge: outEdges.get(vrtx))
                str.append(edge).append('\n');
        }
        return str.toString();
    }
}
