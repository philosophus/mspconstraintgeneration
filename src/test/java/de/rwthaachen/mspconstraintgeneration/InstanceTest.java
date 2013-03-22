/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.rwthaachen.mspconstraintgeneration;

import java.util.Map;
import java.util.Set;
import junit.framework.TestCase;

/**
 *
 * @author florian
 */
public class InstanceTest extends TestCase {
    
    public InstanceTest(String testName) {
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
    
    public void testInstance() {
        Instance instance = new Instance();
        int a = 100;
        int b = 20;
        int k = 3;
        int s = 10;
        instance.setUpWithFeaturesAndSizes(a, b, k, s);
        Set result = instance.getMSPConstraintSets();
        System.out.println(result.size() + " should be less than " + Math.pow(2, k)*s);
        assert(result.size() <= Math.pow(2, k)*s);
    }
}
