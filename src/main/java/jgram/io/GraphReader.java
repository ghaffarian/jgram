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
     * Reads a DOT file and returns a single graph represented in the file.
     * Note that this method cannot read the general DOT language;
     * but only a small subset as written by the `GraphWriter.writeDOT(...)` method.
     * 
     * @param filePath  path of the DOT file to read
     * @return          graph object constructed from the given DOT file
     */
    public static Graph<String,String> readDOT(String filePath) throws IOException {
        if (!filePath.toLowerCase().endsWith(".dot"))
            throw new IllegalArgumentException("File-path does not end with .dot suffix!");
        Graph<String,String> graph;
		try (BufferedReader dot = new BufferedReader(new FileReader(filePath))) {
            // read graph type
            String line = dot.readLine().trim();
            if (line.startsWith("digraph"))
                graph = new DefaultDirectedGraph<>(String.class);
            else //if (line.startsWith("graph") && line.endsWith("{"))
                graph = new DefaultUndirectedGraph<>(String.class);
            // skip any blank lines
            while (!(line = dot.readLine()).trim().equals("// graph-vertices")) ;
            // read graph vertices
			Map<String, String> vertexNames = new LinkedHashMap<>();
            while (!(line = dot.readLine()).trim().equals("// graph-edges")) {
                line = line.trim();
                String[] tokens = line.split("\\s+");
                int start = line.indexOf("[label=\"") + 8;
                int end = line.lastIndexOf("\"];");
                String vertex = line.substring(start, end);
                vertexNames.put(tokens[0], vertex);
                //System.out.println(tokens[0] + ":  " + vertex);
                graph.addVertex(vertex);
            }
            // read graph edges
            while (!(line = dot.readLine()).trim().equals("// end-of-graph")) {
                line = line.trim();
                String[] tokens = line.split("\\s+");
                if (tokens.length > 3) {
                    int start = line.indexOf("[label=\"") + 8;
                    int end = line.lastIndexOf("\"];");
                    String edge = line.substring(start, end);
                    graph.addEdge(vertexNames.get(tokens[0]), vertexNames.get(tokens[2]), edge);
                    //System.out.println(tokens[0] + " -> " + tokens[2] + ":  " + edge);
                } else {
                    // remove semicolon
                    tokens[2] = tokens[2].substring(0, tokens[2].length() - 1);
                    graph.addEdge(vertexNames.get(tokens[0]), vertexNames.get(tokens[2]), "");
                    //System.out.println(tokens[0] + " -> " + tokens[2]);
                }
            }
			return graph;
		}
    }
    
    /**
     * Reads a DOT file and returns a single graph represented in the file.
     * NOT IMPLEMENTED YET!
     * 
     * @param filePath  path of the JSON file to read
     * @return          graph object constructed from the given JSON file
     */
    public static Graph readJSON(String filePath) {
        throw new UnsupportedOperationException("Reading Graphs from JSON is NOT Implemented Yet!");
    }
    
}
