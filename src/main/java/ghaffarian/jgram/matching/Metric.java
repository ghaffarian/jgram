/*** In The Name of Allah ***/
package ghaffarian.jgram.matching;

import ghaffarian.graphs.Matcher;

/**
 * Interface of distance metrics.
 * A distance metric is a function that defines a distance between a pair of objects.
 * For more information on the properties of distance metrics refer to:
 * https://en.wikipedia.org/wiki/Metric_(mathematics)
 * 
 * Note that a Metric is also a Matcher, 
 * so both equals and hashCode methods of the Matcher interface should also be implemented.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public interface Metric<T> extends Matcher<T> {

    public float distance(T o1, T o2);
 
}
