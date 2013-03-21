package de.rwthaachen.mspconstraintgeneration.utils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Florian Dahms<dahms@or.rwth-aachen.de>
 */
public class Utils {

    public static <A,B> boolean multiMapInsert(Map<A,Set<B>> map, A a, B b) {
        if (map.containsKey(a)) {
            return map.get(a).add(b);
        } else {
            Set<B> newSet = new HashSet<B>();
            newSet.add(b);
            map.put(a, newSet);
            return true;
        }
    }
    
}
