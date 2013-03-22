package de.rwthaachen.mspconstraintgeneration;

import de.rwthaachen.mspconstraintgeneration.utils.Utils;
import java.util.*;

/**
 *
 * @author Florian Dahms<dahms@or.rwth-aachen.de>
 */
public class MSPConstraintGenerator<A, B> {

    private Set<A> matchFrom;
    private Set<B> matchTo;
    private Map<A, Set<B>> edges1;
    private Map<B, Set<A>> edges2;

    public MSPConstraintGenerator(Set<A> matchFrom, Set<B> matchTo, Map<A, Set<B>> edges) {
        assert (matchFrom != null);
        assert (matchTo != null);
        assert (edges != null);

        this.edges1 = edges;
        this.matchFrom = matchFrom;
        this.matchTo = matchTo;

        edges2 = new HashMap<B, Set<A>>();
        for (A a : matchFrom) {
            if (edges.containsKey(a)) {
                assert (edges.get(a) != null);
                for (B b : edges.get(a)) {
                    Utils.multiMapInsert(edges2, b, a);
                }
            }
        }
    }

    public Set<B> gamma(A a) {
        if (edges1.containsKey(a)) {
            assert (edges1.get(a) != null);
            return edges1.get(a);
        }
        return new HashSet<B>();
    }

    public Set<A> gammainv(Set<B> bSet) {
        assert (bSet != null);

        Set<A> temp = new HashSet<A>();
        for (B b : bSet) {
            if (edges2.containsKey(b)) {
                assert (edges2.get(b) != null);
                temp.addAll(edges2.get(b));
            }
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

    public List<Set<B>> setUpWorkingSet() {
        List<Set<B>> working = new LinkedList();

        for (A a : matchFrom) {
            working.add(gamma(a));
        }

        return working;
    }
    
    public Set< Set<B>> generateMSPConstraintSets() {
        List<Set<B>> working = setUpWorkingSet();
        Set<Set<B>> result = new HashSet();
        

        return result;
    }
}
