/*** In The Name of Allah ***/
package jgram;

/**
 * Edge is a simple holder for actual edge objects.
 * This is required to cope with the use of JGraphT 
 * of `java.util.Set` for both edges and vertices of graphs.
 * 
 * NOTE that the `equals` and `hashcode` methods are deliberately NOT implemented.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class Edge<T> {
    
    public final T value;
    
    public Edge(T obj) {
        value = obj;
    }
    
}
