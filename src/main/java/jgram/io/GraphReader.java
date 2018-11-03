/*** In The Name of Allah ***/
package jgram.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.jgrapht.Graph;

/**
 * Reads graph objects from various input sources.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class GraphReader {
    
    public static Graph readDOT(String filePath) throws IOException {
        if (!filePath.toLowerCase().endsWith(".dot"))
            throw new IllegalArgumentException("File-path does not end with .dot suffix!");
		String filename = new File(filePath).getName();
		try (BufferedReader dot = new BufferedReader(new FileReader(filePath))) {
            while (dot.ready()) {
                String line = dot.readLine();
                
            }
            dot.
            String graphName = filename.substring(0, filename.lastIndexOf('.'));
            if (graph.getType().isDirected())
                dot.println("digraph " + graphName + " {\n");
            else
                dot.println("graph " + graphName + " {\n");
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
			dot.println();
			for (Object edge: graph.edgeSet()) {
				String src = nodeNames.get(graph.getEdgeSource(edge));
				String trg = nodeNames.get(graph.getEdgeTarget(edge));
				if (edge.toString().isEmpty())
					dot.println("   " + src + " -> " + trg + ";");
				else
					dot.println("   " + src + " -> " + trg + "   [label=\"" + StringUtils.escape(edge.toString()) + "\"];");
			}
			dot.println("\n}");
		}
    }
    
    public static Graph readJSON(String filePath) {
        throw new UnsupportedOperationException("Reading Graphs from JSON is NOT Implemented Yet!");
    }
    
}
