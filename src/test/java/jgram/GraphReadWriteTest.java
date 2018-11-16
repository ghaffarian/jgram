/*** In The Name of Allah ***/
package jgram;

import java.io.IOException;
import java.util.Arrays;
import jgram.io.GraphReader;
import jgram.io.GraphWriter;
import jgram.utils.GraphUtils;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;

import static org.junit.Assert.*;
import org.junit.*;

/**
 *
 * @author Seyed Mohammad Ghaffarian
 */
public class GraphReadWriteTest {
    
    @Test
    public void dotWriterTest() throws IOException {
        DefaultDirectedGraph<Vertex,Edge> graph = new DefaultDirectedGraph<>(Edge.class);
        // add vertices
        Vertex  v1 = new Vertex("V1-test"),
                v2 = new Vertex("V2-test"),
                v3 = new Vertex("V3-test"),
                v4 = new Vertex("V4-test");
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);
        // add edges
        String  e1 = "E1-test",
//                e2 = "E2-test",
//                e3 = "E3-test",
//                e4 = "E4-test",
//                e5 = "E5-test",
//                e6 = "E6-test",
                e7 = "E7-test";
        graph.addEdge(v1, v2, new Edge(e1));
        graph.addEdge(v1, v3, new Edge(e7));
        graph.addEdge(v1, v4, new Edge(e1));
        graph.addEdge(v2, v3, new Edge(e7));
        graph.addEdge(v2, v4, new Edge(e1));
        graph.addEdge(v3, v4, new Edge(e7));
        graph.addEdge(v4, v1, new Edge(e7));
        // write to file
        GraphWriter.writeDOT(graph, "write_test_1.dot");
        // assertion
        assertTrue(true);
    }
    
    @Test
    public void dotReaderTest() throws IOException {
        // read graph from DOT file
        Graph<Vertex,Edge> graph = GraphReader.readDOT("read_test_1.dot");
        // check directed/undirected property
        assertTrue(graph.getType().isDirected());
        // vertices
        Vertex<String>  v1 = new Vertex("V1-test"),
                        v2 = new Vertex("V2-test"),
                        v3 = new Vertex("V3-test"),
                        v4 = new Vertex("V4-test");
        Vertex[] vertices = {v1, v2, v3, v4};
        // edges
        Edge    e1 = new Edge("E1-test"),
//                e2 = "E2-test",
//                e3 = "E3-test",
//                e4 = "E4-test",
//                e5 = "E5-test",
//                e6 = "E6-test",
                e7 = new Edge("E7-test");
        Edge[] edges = {new Edge(e1), new Edge(e7)};
        // check edge/vertex count
        assertEquals(edges.length, graph.edgeSet().size());
        assertEquals(vertices.length, graph.vertexSet().size());
        // check edges
        assertEquals(e1, graph.getEdge(v1, v2));
        assertEquals(e7, graph.getEdge(v1, v3));
        assertEquals(e1, graph.getEdge(v1, v4));
        assertEquals(e7, graph.getEdge(v2, v3));
        assertEquals(e1, graph.getEdge(v2, v4));
        assertEquals(e7, graph.getEdge(v3, v4));
        assertEquals(e7, graph.getEdge(v4, v1));
    }
    
    @Test
    public void subGraphTest() throws IOException {
        // read graph from DOT file
        Graph<String,String> cfg = GraphReader.readDOT("CFG.dot");
        Graph<String,String> sub = GraphReader.readDOT("sub-CFG.dot");
        // assertion
        assertTrue(GraphUtils.isSubgraph(cfg, sub));
    }
}
