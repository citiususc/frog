package frog.inference;

import frog.fuzzyset.FuzzySet;

/**
 * An static class that contains the available methods for defuzzify the results
 * obtained by the fuzzy rule system. The defuzzification has to aggregate the 
 * information providad by the rules and obtain a crisp value from them. 
 */
public class Defuzzify {
    
    /**
     * Uses the center of gravity of the output fuzzy sets of the rules. Then, 
     * the crisp value is the mean of these centers weighted by the dof of
     * each rule antecedent.
     * @param sets The output sets of the rules
     * @return Crisp value
     */
    public static double centerOfGravity(FuzzySet[] sets) {
        double num = 0.;
        double den = 0.;
            
        for (FuzzySet set : sets) {
    		double cog = set.centerOfGravity();
        	num += cog * set.height;
        	den += set.height;
        }
            
        if (den == 0) {
            return Double.NaN;
        } else {
            return num/den;
        }            
    }
}
