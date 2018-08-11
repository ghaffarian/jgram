package jGraM;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;
import org.jgrapht.util.SupplierUtil;

/**
 * Hello JGraphT!
 */
public class App {

    public void test() {

        Graph<String, DefaultEdge> graph = GraphTypeBuilder
                .directed()
                .allowingMultipleEdges(true)
                .allowingSelfLoops(true)
                .vertexSupplier(SupplierUtil.createStringSupplier())
                .edgeSupplier(SupplierUtil.createDefaultEdgeSupplier())
                .buildGraph();

        String v0 = graph.addVertex();
        String v1 = graph.addVertex();
        String v2 = graph.addVertex();

        graph.addEdge(v0, v1);
        graph.addEdge(v1, v2);
        graph.addEdge(v0, v2);

        for (String v : graph.vertexSet()) {
            System.out.println("vertex: " + v);
        }

        for (DefaultEdge e : graph.edgeSet()) {
            System.out.println("edge: " + e);
        }

    }

}
