/*** In The Name of Allah ***/
package jgram.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultUndirectedGraph;

/**
 * Reads graph objects from various input sources.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class GraphReader {
    
    /**
     * 
     * @param filePath
     * @return
     * @throws IOException 
     */
    public static Graph readDOT(String filePath) throws IOException {
        if (!filePath.toLowerCase().endsWith(".dot"))
            throw new IllegalArgumentException("File-path does not end with .dot suffix!");
        Graph graph;
		try (BufferedReader dot = new BufferedReader(new FileReader(filePath))) {
            // read graph type
            String line = dot.readLine().trim();
            if (line.startsWith("digraph"))
                graph = new DefaultDirectedGraph(String.class);
            else //if (line.startsWith("graph") && line.endsWith("{"))
                graph = new DefaultUndirectedGraph(String.class);
            dot.readLine(); // skip empty line
            // read graph vertex
			Map<String, String> vertexNames = new LinkedHashMap<>();
            while (!(line = dot.readLine()).trim().isEmpty()) {
                String[] tokens = line.split("\\s+");
                int start = tokens[1].indexOf("[label=\"") + 8;
                int end = tokens[1].lastIndexOf("\"];");
                String vertex = tokens[1].substring(start, end);
                vertexNames.put(tokens[0], vertex);
                graph.addVertex(vertex);
            }
            // read graph edges
            while (!(line = dot.readLine()).trim().isEmpty()) {
                String[] tokens = line.split("\\s+");
                if (tokens.length > 3) {
                    int start = tokens[3].indexOf("[label=\"") + 8;
                    int end = tokens[3].lastIndexOf("\"];");
                    String edge = tokens[3].substring(start, end);
                    graph.addEdge(vertexNames.get(tokens[0]), vertexNames.get(tokens[2]), edge);
                } else {
                    // remove semicolon
                    tokens[2] = tokens[2].substring(0, tokens[2].length() - 1);
                    graph.addEdge(vertexNames.get(tokens[0]), vertexNames.get(tokens[2]));
                }
            }
			return graph;
		}
    }
    
    public static Graph readJSON(String filePath) {
        throw new UnsupportedOperationException("Reading Graphs from JSON is NOT Implemented Yet!");
    }
    
}
