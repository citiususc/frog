package frog.fuzzyset;

/**
 * A fuzzy set with the shape of a triangle. 
 */
public class Triangle extends FuzzySet {

	public double a,b,c;
	
	public Triangle(double a, double b, double c) {
		this(a, b, c, 1.);
	}

	
	public Triangle(double a, double b, double c, double height) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.height = height;
	}
	
	@Override
	public double dof(double x) {
            if (x > this.a && x < this.c) {
                if (x < this.b) {
                    return ((x - this.a) / (this.b - this.a)) * this.height;
                } else if (x > this.b) {
                    return (1. -  (x - this.b) / (this.c - this.b)) * this.height;
                } else {
                    return this.height;
                }
            } else {
                return 0.;
            }
	}

	@Override
	public FuzzySet alphaCut(double alpha) {
            double b = this.a + (this.b - this.a) * alpha;
            double c = this.b + (this.c - this.b) * (1. - alpha);
            return new Trapezium(this.a, b, c, this.c, alpha);
	}

	@Override
	public double centerOfGravity() {
            double a = this.b - this.a;
            double b = this.c - this.a;
            return this.a + (a + b) / 3.;
	}
	
	@Override
	public String toString() {
		return "[" + this.a + ", " + this.b + ", " + this.c + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Triangle) {
			Triangle other = (Triangle)obj;
			if (this.a == other.a && this.b == other.b && this.c == other.c && this.height == other.height) {
				return true;
			}
		}
		return false;
	}
	
}
