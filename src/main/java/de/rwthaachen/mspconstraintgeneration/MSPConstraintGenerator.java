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

        // for every edge from A to B there shall be an edge from B to A
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

        // collect all the edges an element from bSet connects to
        Set<A> temp = new HashSet<A>();
        for (B b : bSet) {
            if (edges2.containsKey(b)) {
                assert (edges2.get(b) != null);
                temp.addAll(edges2.get(b));
            }
        }

        // only keep a's whose neighbours are all in bSet
        Set<A> result = new HashSet<A>();
        for (A a : temp) {
            if (bSet.containsAll(edges1.get(a))) {
                result.add(a);
            }
        }

        return result;
    }

    public Queue<Set<B>> setUpWorkingSet() {
        Queue<Set<B>> working = new LinkedList();

        for (A a : matchFrom) {
            working.add(gamma(a));
        }

        return working;
    }

    public static <B> boolean addContained(Map<B, Set<Set<B>>> containedIn, Set<B> newSet) {
        boolean result = false;
        for (B b : newSet) {
            if (Utils.multiMapInsert(containedIn, b, newSet)) {
                result = true;
            }
        }
        return result;
    }

    public Set< Set<B>> generateMSPConstraintSets() {
        Queue<Set<B>> working = setUpWorkingSet();
        Set<Set<B>> result = new HashSet(working);
        Map<B, Set<Set<B>>> containedIn = new HashMap();

        for (Set<B> newSet : result) {
            addContained(containedIn, newSet);
        }
        
        while(!working.isEmpty()) {
            Set<B> current = working.remove();
            
            // collect all sets the current one could possibly be joined with
            Set<Set<B>> possiblePartners = new HashSet();
            for (B b : current) {
                assert(containedIn.containsKey(b));
                possiblePartners.addAll(containedIn.get(b));
            }
            
            // add all joined sets which are actually new
            for (Set<B> possiblePartner : possiblePartners) {
                Set<B> newSet = new HashSet(current);
                newSet.addAll(possiblePartner);
                if (!result.contains(newSet)) {
                    result.add(newSet);
                    working.add(newSet);
                }
            }
        }

        return result;
    }
}
