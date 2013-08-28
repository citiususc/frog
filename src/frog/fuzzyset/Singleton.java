package frog.fuzzyset;

/**
 * A fuzzy set that contains only one point.
 */
public class Singleton extends FuzzySet {

	private double point;
	
	public Singleton(double point) {
		this(point, 1.);
	}
	
	public Singleton(double point, double height) {
		this.point = point;
		this.height = height;
	}
	
	@Override
	public double dof(double x) {
		if (x == this.point) {
			return 1.;
		} else {
			return 0.;
		}
	}

	@Override
	public FuzzySet alphaCut(double alpha) {
		return new Singleton(this.point, alpha);
	}

	@Override
	public double centerOfGravity() {
		return this.point;
	}

}
