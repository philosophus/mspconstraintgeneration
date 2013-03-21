package de.rwthaachen.mspconstraintgeneration;

import de.rwthaachen.mspconstraintgeneration.utils.Utils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.jgrapht.Graph;

/**
 *
 * @author Florian Dahms<dahms@or.rwth-aachen.de>
 */
public class MSPConstraintGenerator<A,B> {
    
    private Set<A> matchFrom;
    private Set<B> matchTo;
    private Map<A,Set<B>> edges1;
    private Map<B,Set<A>> edges2;
    
    public MSPConstraintGenerator(Set<A> matchFrom, Set<B> matchTo, Map<A,Set<B>> edges) {
        this.edges1 = edges;
        this.matchFrom = matchFrom;
        this.matchTo = matchTo;
        
        edges2 = new HashMap<B, Set<A>>();
        for (A a : matchFrom) {
            for (B b : edges.get(a)) {
                Utils.multiMapInsert(edges2, b, a);
            }
        }
    }
    
    public Set<B> gamma(A a) {
        return edges1.get(a);
    }
    
    public Set<A> gammainv(Set<B> bSet) {
        Set<A> temp = new HashSet<A>();
        for (B b : bSet) {
            temp.addAll(edges2.get(b));
        }
        System.out.println(temp.toString());
        
        Set<A> result = new HashSet<A>();
        for (A a : temp) {
            if (bSet.containsAll(edges1.get(a))) {
                result.add(a);
            }
        }
        
        return result;
    }

    public static <V, E> Set< Set<V>> generateMSPConstraints(Graph<V, E> graph, Set<V> matchFrom) {



        System.out.println(graph.toString());

        return null;
    }
}
