package de.rwthaachen.mspconstraintgeneration;

import de.rwthaachen.mspconstraintgeneration.utils.Utils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

    public void testGammainv() {
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

        Map<String, Set<String>> edges = new HashMap();
        Utils.multiMapInsert(edges, "1", "a");
        Utils.multiMapInsert(edges, "2", "a");
        Utils.multiMapInsert(edges, "1", "b");
        Utils.multiMapInsert(edges, "2", "b");
        Utils.multiMapInsert(edges, "3", "b");
        Utils.multiMapInsert(edges, "4", "b");
        Utils.multiMapInsert(edges, "5", "b");
        Utils.multiMapInsert(edges, "6", "b");
        Utils.multiMapInsert(edges, "5", "c");
        Utils.multiMapInsert(edges, "6", "c");
        
        MSPConstraintGenerator<String, String> generator = new MSPConstraintGenerator<String, String>(A,B,edges);
        
        Set expResult1 = new HashSet();
        expResult1.add("3");
        expResult1.add("4");
        Set testSet1 = new HashSet();
        testSet1.add("b");
        Set result = generator.gammainv(testSet1);
        assertEquals(expResult1, result);
        
        Set expResult2 = new HashSet();
        expResult2.add("1");
        expResult2.add("2");
        expResult2.add("3");
        expResult2.add("4");
        Set testSet2 = new HashSet();
        testSet2.add("a");
        testSet2.add("b");
        result = generator.gammainv(testSet2);
        assertEquals(expResult2, result);        
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
        for (String v : A) {
            graph.addVertex(v);
        }
        for (String v : B) {
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
        Set expResult = new HashSet();
        Set subset1 = new HashSet();
        subset1.add("a");
        expResult.add(subset1);
        Set subset2 = new HashSet();
        subset2.add("b");
        expResult.add(subset2);
        Set subset3 = new HashSet();
        subset3.add("c");
        expResult.add(subset3);
        Set subset4 = new HashSet();
        subset4.add("a");
        subset4.add("b");
        expResult.add(subset4);
        Set subset5 = new HashSet();
        subset5.add("b");
        subset5.add("c");
        expResult.add(subset5);
        Set subset6 = new HashSet();
        subset6.add("a");
        subset6.add("b");
        subset6.add("c");
        expResult.add(subset6);

        Set result = MSPConstraintGenerator.generateMSPConstraints(graph, A);
        assertEquals(expResult, result);
    }
}
