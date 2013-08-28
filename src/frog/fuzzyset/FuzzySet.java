package frog.fuzzyset;
 
/**
 * A set whose elements have degrees of membership.
 */
public abstract class FuzzySet {
	
    /**
     * The maximum degree of fullfilment that an element can have in this fuzzy
     * set.
     */
    public double height;
	
    /**
     * Degree of fullfilment of x.
     */
    public abstract double dof(double x);
    
    /**
     * An alpha-cut of the membership function A. 
     * Is the set of all x such that A(x) is greater than or equal to alpha (a).
     */
    public abstract FuzzySet alphaCut(double alpha);
    
    /**
     * The center of mass of the fuzzy set.
     */
    public abstract double centerOfGravity();
}
