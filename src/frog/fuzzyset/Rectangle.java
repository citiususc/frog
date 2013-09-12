package frog.fuzzyset;

/**
 * A fuzzy set with the shape of a rectangle. This is the shape of a raw set,
 * that is, all points that belong to the fuzzy set, have a dof of 1, while
 * any other point has a dof of 0.
 */
public class Rectangle extends FuzzySet {
    public double a;
    public double b;

    public Rectangle() {
        this(0, 1, 1);
    }
    
    public Rectangle(double a, double b) {
        this(a, b, 1.);
    }
    
    public Rectangle(double a, double b, double height) {
        this.a = a;
        this.b = b;
        this.height = height;
    }
    
    @Override
    public double dof(double x) {
        if (this.a <= x && x <= this.b) {
            return 1.;
        } else {
            return 0.;
        }
    }

    @Override
    public FuzzySet alphaCut(double alpha) {
        return new Rectangle(this.a, this.b, alpha);
    }

    @Override
    public double centerOfGravity() {
        return (this.a + this.b) / 2.;
    }

    @Override
    public String toString() {
        return "[" + this.a + ", " + this.b + "]";
    }
        
        

}
