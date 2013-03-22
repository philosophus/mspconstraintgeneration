package de.rwthaachen.mspconstraintgeneration;

import de.rwthaachen.mspconstraintgeneration.utils.Utils;
import java.util.*;

/**
 *
 * @author Florian Dahms<dahms@or.rwth-aachen.de>
 */
public class Instance {

   private Set<Integer> as;
   private Set<Integer> bs;
   private Map<Integer, Set<Integer>> edges;

   public Set<Integer> getA() {
      return as;
   }

   public void setA(Set<Integer> a) {
      this.as = a;
   }

   public Set<Integer> getB() {
      return bs;
   }

   public void setB(Set<Integer> b) {
      this.bs = b;
   }

   public Map<Integer, Set<Integer>> getEdges() {
      return edges;
   }

   public void setEdges(Map<Integer, Set<Integer>> edges) {
      this.edges = edges;
   }

   public void setUpWithFeaturesAndSizes(int nA, int nB, int nFeatures, int nSizes) {
      assert (nA >= 0);
      assert (nB >= 1);
      assert (nFeatures >= 0);
      assert (nSizes >= 0);

      Random rand = new Random();
      as = new HashSet();
      bs = new HashSet();
      edges = new HashMap();

      Map<Integer, Set<Integer>> features = new HashMap();
      Map<Integer, Integer> size = new HashMap();

      for (Integer i = 1; i <= nB; ++i) {
         bs.add(i);
         size.put(i, rand.nextInt(nSizes) + 1);
      }

      for (Integer i = 1; i <= nFeatures; ++i) {
         Set<Integer> choosenB = new HashSet();
         while (choosenB.size() < 1 + (double) nB / (double) nFeatures) {
            choosenB.add(rand.nextInt(nB) + 1);
         }
         
         for (Integer b : choosenB) {
            Utils.multiMapInsert(features, b, i);
         }
      }

      for (Integer i = 1; i <= nA; ++i) {
         as.add(i);
         Integer demand = rand.nextInt(nSizes) + 1;
         Set<Integer> neededFeatures = new HashSet();
         for (Integer j = 1; j <= nFeatures; ++j) {
            if (rand.nextDouble() < (double)nFeatures / (double)nB) {
               neededFeatures.add(j);
            }
         }
         
         // add edges
         for (Integer b : bs) {
            if (size.get(b) >= demand) {
               if (neededFeatures.isEmpty() || (features.containsKey(b) && features.get(b).containsAll(neededFeatures))) {
                  Utils.multiMapInsert(edges, i, b);
               }
            }
         }
      }
      

   }

   public Set<Set<Integer>> getMSPConstraintSets() {
      MSPConstraintGenerator<Integer, Integer> mspGenerator = new MSPConstraintGenerator<Integer, Integer>(as, bs, edges);
      return mspGenerator.generateMSPConstraintSets();
   }
}
