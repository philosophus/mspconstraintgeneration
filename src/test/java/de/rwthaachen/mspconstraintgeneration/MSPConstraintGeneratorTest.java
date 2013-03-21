package de.rwthaachen.mspconstraintgeneration;

import java.util.HashSet;
import java.util.Set;
import junit.framework.TestCase;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

/**
 *
 * @author Florian Dahms<dahms@or.rwth-aachen.de>
 */
public class MSPConstraintGeneratorTest extends TestCase {
    
    public MSPConstraintGeneratorTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of generateMSPConstraints method, of class MSPConstraintGenerator.
     */
    public void testGenerateMSPConstraints() {
        Set<String> A = new HashSet();
        Set<String> B = new HashSet();
        
        A.add("1");
        A.add("2");
        A.add("3");
        A.add("4");
        A.add("5");
        A.add("6");
        B.add("a");
        B.add("b");
        B.add("c");
        Graph<String, DefaultEdge> graph = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
        for (String v: A) {
            graph.addVertex(v);
        }
        for (String v: B) {
            graph.addVertex(v);
        }
        
        graph.addEdge("1", "a");
        graph.addEdge("2", "a");
        graph.addEdge("1", "b");
        graph.addEdge("2", "b");
        graph.addEdge("3", "b");
        graph.addEdge("4", "b");
        graph.addEdge("5", "b");
        graph.addEdge("6", "b");
        graph.addEdge("5", "c");
        graph.addEdge("6", "c");
                    
        System.out.println("generateMSPConstraints");
        Set expResult = null;
        Set result = MSPConstraintGenerator.generateMSPConstraints(graph, A);
        assertEquals(expResult, result);
    }
}
