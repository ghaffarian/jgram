/*** In The Name of Allah ***/
package jgram.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A class providing utility methods for processing collections.
 *
 * @author Seyed Mohammad Ghaffarian
 */
public class CollectionUtils {
    
    /**
     * Sorts all entries in a given generic Map based on the values.
     * 
     * @param map  the Map collection to be sorted
     * @return     a new Map object with all entries sorted by value
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
    
}
