/*** In The Name of Allah ***/
package jgram;

/**
 * Vertex is a simple holder for actual vertex objects.
 * This is required to cope with the use of JGraphT 
 * of `java.util.Set` for both edges and vertices of graphs.
 * 
 * NOTE that the `equals` and `hashcode` methods are deliberately NOT implemented.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class Vertex<T> {
    
    public final T value;
    
    public Vertex(T obj) {
        value = obj;
    }
    
}
