package de.rwthaachen.mspconstraintgeneration;

import de.rwthaachen.mspconstraintgeneration.utils.Utils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import junit.framework.TestCase;

/**
 *
 * @author Florian Dahms<dahms@or.rwth-aachen.de>
 */
public class MSPConstraintGeneratorTest extends TestCase {

    private Set<String> A1 = new HashSet();
    private Set<String> B1 = new HashSet();
    private Map<String, Set<String>> edges1 = new HashMap();

    public MSPConstraintGeneratorTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        A1.add("1");
        A1.add("2");
        A1.add("3");
        A1.add("4");
        A1.add("5");
        A1.add("6");
        B1.add("a");
        B1.add("b");
        B1.add("c");

        Utils.multiMapInsert(edges1, "1", "a");
        Utils.multiMapInsert(edges1, "2", "a");
        Utils.multiMapInsert(edges1, "1", "b");
        Utils.multiMapInsert(edges1, "2", "b");
        Utils.multiMapInsert(edges1, "3", "b");
        Utils.multiMapInsert(edges1, "4", "b");
        Utils.multiMapInsert(edges1, "5", "b");
        Utils.multiMapInsert(edges1, "6", "b");
        Utils.multiMapInsert(edges1, "5", "c");
        Utils.multiMapInsert(edges1, "6", "c");
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGammainv() {
        MSPConstraintGenerator<String, String> generator = new MSPConstraintGenerator<String, String>(A1, B1, edges1);

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

    public void testGammaInvEmptyEdges() {
        Map<String, Set<String>> edges = new HashMap();
        MSPConstraintGenerator<String, String> generator = new MSPConstraintGenerator<String, String>(A1, B1, edges);

        Set expResult = new HashSet();
        Set testSet1 = new HashSet();
        testSet1.add("b");
        Set result = generator.gammainv(testSet1);
        assertEquals(expResult, result);

        Set testSet2 = new HashSet();
        testSet2.add("a");
        testSet2.add("b");
        result = generator.gammainv(testSet2);
        assertEquals(expResult, result);
    }

    public void testSetUpWorkingSet() {
        MSPConstraintGenerator<String, String> generator = new MSPConstraintGenerator<String, String>(A1, B1, edges1);

        Set expResult = new HashSet();
        Set subset1 = new HashSet();
        subset1.add("a");
        subset1.add("b");
        expResult.add(subset1);
        Set subset2 = new HashSet();
        subset2.add("b");
        expResult.add(subset2);
        Set subset3 = new HashSet();
        subset3.add("b");
        subset3.add("c");
        expResult.add(subset3);

        Set result = new HashSet(generator.setUpWorkingSet());
        assertEquals(expResult, result);
    }

    public void testAddContained() {
        MSPConstraintGenerator<String, String> generator = new MSPConstraintGenerator<String, String>(A1, B1, edges1);
        
        Map<Integer, Set<Set<Integer>>> containedIn = new HashMap();
        Set<Integer> newSet = new HashSet();
        newSet.add(1);
        newSet.add(2);
        
        boolean result = MSPConstraintGenerator.addContained(containedIn, newSet);      
        assertEquals(true, result);
        
        Set<Set<Integer>> Set2 = new HashSet();
        Set2.add(newSet);
        assertEquals(Set2, containedIn.get(2));
        
        result = MSPConstraintGenerator.addContained(containedIn, newSet);
        assertEquals(false, result);
        
        Set<Integer> newSet2 = new HashSet();
        newSet2.add(2);
        newSet2.add(3);
        result = MSPConstraintGenerator.addContained(containedIn, newSet2);
        assertEquals(true, result);
        
        Set2.add(newSet2);
        assertEquals(Set2, containedIn.get(2));
        
    }

    public void testGenerateMSPConstraintSets() {
        MSPConstraintGenerator<String, String> generator = new MSPConstraintGenerator<String, String>(A1, B1, edges1);
        Set expResult = new HashSet();
        Set subset1 = new HashSet();
        subset1.add("b");
        expResult.add(subset1);
        Set subset2 = new HashSet();
        subset2.add("a");
        subset2.add("b");
        expResult.add(subset2);
        Set subset3 = new HashSet();
        subset3.add("b");
        subset3.add("c");
        expResult.add(subset3);
        Set subset4 = new HashSet();
        subset4.add("a");
        subset4.add("b");
        subset4.add("c");
        expResult.add(subset4);

        Set result = generator.generateMSPConstraintSets();
        assertEquals(expResult, result);
    }
}
