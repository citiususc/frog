package frog.fuzzyset;

import java.io.Serializable;

/**
 * A set whose elements have degrees of membership.
 */
public abstract class FuzzySet implements Serializable {
	
    /**
     * The maximum degree of fullfilment that an element can have in this fuzzy
     * set.
     */
    public double height;
	
    /**
     * Degree of fulfillment of x.
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

    /**
     * Obtains the representative points the X axis
     */
    public abstract double[] getPoints();

    /**
     * Sets the representative points of the X axis
     */
    public abstract void setPoints(double[] points);

    /**
     * Gets a JSON/YAML representation of the points
     */
    public abstract String toJSON();

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FuzzySet) {
            FuzzySet other = (FuzzySet) obj;
            boolean equals = other.getPoints().length == this.getPoints().length;
            for (int i = 0; equals && i < other.getPoints().length; i++) {
                equals &= other.getPoints()[i] == this.getPoints()[i];
            }
            return equals;
        }
        return false;
    }
}
