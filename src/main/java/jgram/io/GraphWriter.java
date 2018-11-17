/*** In The Name of Allah ***/
package jgram.io;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import jgram.graphs.Edge;
import jgram.graphs.Graph;
import jgram.utils.StringUtils;

/**
 * Writes graph objects to various output sources.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class GraphWriter {
    
    /**
     * 
     * @param graph
     * @param filePath
     * @throws IOException 
     */
    public static <V,E> void writeDOT(Graph<V,E> graph, String filePath) throws IOException {
        if (!filePath.toLowerCase().endsWith(".dot"))
            throw new IllegalArgumentException("File-path does not end with .dot suffix!");
		String filename = new File(filePath).getName();
		try (PrintWriter dot = new PrintWriter(filePath, "UTF-8")) {
            String edgeSymbol;
            String graphName = filename.substring(0, filename.lastIndexOf('.'));
            if (graph.IS_DIRECTED) {
                dot.println("digraph " + graphName + " {");
                edgeSymbol = " -> ";
            } else {
                dot.println("graph " + graphName + " {");
                edgeSymbol = " -- ";
            }
            dot.println("  // graph-vertices");
			Map<V, String> nodeNames = new LinkedHashMap<>();
			int nodeCounter = 1;
            Enumeration<V> vertices = graph.enumerateAllVertices();
			while (vertices.hasMoreElements()) {
                V node = vertices.nextElement();
				String name = "v" + nodeCounter++;
				nodeNames.put(node, name);
				StringBuilder label = new StringBuilder("  [label=\"");
				if (!node.toString().trim().isEmpty())
    				label.append(StringUtils.escape(node.toString()));
				dot.println("  " + name + label.append("\"];").toString());
			}
			dot.println("  // graph-edges");
            Enumeration<Edge<V,E>> edges = graph.enumerateAllEdges();
			while (edges.hasMoreElements()) {
                Edge<V,E> edge = edges.nextElement();
				String src = nodeNames.get(edge.source);
				String trg = nodeNames.get(edge.target);
				if (edge.label == null || edge.label.toString().trim().isEmpty())
					dot.println("  " + src + edgeSymbol + trg + ";");
				else
					dot.println("  " + src + edgeSymbol + trg + 
                            "  [label=\"" + StringUtils.escape(edge.label.toString()) + "\"];");
			}
			dot.println("  // end-of-graph\n}");
		}
    }
    
    public static <V,E> void writeJSON(Graph<V,E> graph, String filePath) {
        throw new UnsupportedOperationException("Writing Graphs to JSON is NOT Implemented Yet!");
    }
    
}
