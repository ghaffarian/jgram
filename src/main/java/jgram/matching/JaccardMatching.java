/*** In The Name of Allah ***/
package jgram.matching;

import org.jgrapht.Graph;

/**
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class JaccardMatching implements MatchingAlgorithm {
    
    /**
     * Enumeration of different Jaccard matching types.
     */
    public enum Type {
        EDGE_MATCHING,
        VERTEX_MATCHING
    }
    
    public final Type TYPE;
    
    private Matching matchingResult;
    
    /**
     * 
     */
    public JaccardMatching() {
        this(Type.EDGE_MATCHING);
    }
    
    /**
     * 
     * @param type 
     */
    public JaccardMatching(Type type) {
        TYPE = type;
        matchingResult = null;
    }

    @Override
    public Matching match(Graph g1, Graph g2) {
        
    }

    @Override
    public Matching getResult() {
        return matchingResult;
    }
    
}
