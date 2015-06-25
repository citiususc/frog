package frog.fuzzyset;

/**
 * A fuzzy set with the shape of a trapezium.
 */
public class Trapezium extends FuzzySet {

	public double a, b, c, d;

	public Trapezium() {this(0, 1, 2, 3, 1.);}

	public Trapezium(double a, double b, double c, double d) {
		this(a, b, c, d, 1.);
	}

	public Trapezium(double a, double b, double c, double d, double height) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.height = height;
	}

	@Override
	public double dof(double x) {
		if (x >= this.a && x <= this.d) {
			if (x < this.b) {
				return ((x - this.a) / (this.b - this.a)) * this.height;
			} else if (x > this.c) {
				return (1 - (x - this.c) / (this.d - this.c)) * this.height;
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
		double c = this.c + (this.d - this.c) * (1. - alpha);
		return new Trapezium(this.a, b, c, this.d, alpha);
	}

	@Override
	public double centerOfGravity() {
		double a = this.c - this.b;
		double b = this.d - this.a;
		double c = this.b - this.a;
		return this.a + (2 * a * c + a * a + c * b + a * b + b * b) / (3. * (a + b));
	}

	@Override
	public double[] getPoints() {
		return new double[]{a, b, c, d};
	}

	@Override
	public void setPoints(double[] points) {
		assert points.length == 4;
		this.a = points[0];
		this.b = points[1];
		this.c = points[2];
        this.d = points[3];
	}

	@Override
	public String toString() {
		return "[" + this.a + ", " + this.b + ", " + this.c + ", " + this.d + "]";
	}

	public String toJSON(){
		return "[["+this.a+",0],["+this.b+","+this.height+"],["+this.c+","+this.height+"],["+this.d+",0]]";
	}
}
