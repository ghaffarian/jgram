/*** In The Name of Allah ***/
package jgram.io;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import jgram.utils.StringUtils;
import org.jgrapht.Graph;

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
    public static void writeDOT(Graph graph, String filePath) throws IOException {
        if (!filePath.toLowerCase().endsWith(".dot"))
            throw new IllegalArgumentException("File-path does not end with .dot suffix!");
		String filename = new File(filePath).getName();
		try (PrintWriter dot = new PrintWriter(filePath, "UTF-8")) {
            String graphName = filename.substring(0, filename.lastIndexOf('.'));
            if (graph.getType().isDirected())
                dot.println("digraph " + graphName + " {\n");
            else
                dot.println("graph " + graphName + " {\n");
            dot.println("   // graph-vertices");
			Map<Object, String> nodeNames = new LinkedHashMap<>();
			int nodeCounter = 1;
			for (Object node: graph.vertexSet()) {
				String name = "n" + nodeCounter++;
				nodeNames.put(node, name);
				StringBuilder label = new StringBuilder("   [label=\"");
				if (!node.toString().isEmpty())
    				label.append(StringUtils.escape(node.toString())).append("\"];");
				dot.println("   " + name + label.toString());
			}
			dot.println("   // graph-edges");
			for (Object edge: graph.edgeSet()) {
				String src = nodeNames.get(graph.getEdgeSource(edge));
				String trg = nodeNames.get(graph.getEdgeTarget(edge));
				if (edge.toString().isEmpty())
					dot.println("   " + src + " -> " + trg + ";");
				else
					dot.println("   " + src + " -> " + trg + "   [label=\"" + StringUtils.escape(edge.toString()) + "\"];");
			}
			dot.println("   // end-of-graph\n}");
		}
    }
    
    public static void writeJSON(Graph graph, String filePath) {
        throw new UnsupportedOperationException("Writing Graphs to JSON is NOT Implemented Yet!");
    }
    
}
