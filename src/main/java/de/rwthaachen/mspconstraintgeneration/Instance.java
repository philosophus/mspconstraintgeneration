package de.rwthaachen.mspconstraintgeneration;

import java.util.Map;
import java.util.Set;

/**
 *
 * @author Florian Dahms<dahms@or.rwth-aachen.de>
 */
public class Instance {
    private Set<Integer> a;
    private Set<String> b;
    private Map<Integer, Set<String>> edges;

    public Set<Integer> getA() {
        return a;
    }

    public void setA(Set<Integer> a) {
        this.a = a;
    }

    public Set<String> getB() {
        return b;
    }

    public void setB(Set<String> b) {
        this.b = b;
    }

    public Map<Integer, Set<String>> getEdges() {
        return edges;
    }

    public void setEdges(Map<Integer, Set<String>> edges) {
        this.edges = edges;
    }
}
