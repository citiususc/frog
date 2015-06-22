package frog.fuzzyset;

import org.apache.commons.lang.ArrayUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnionFuzzySet extends FuzzySet {

	List<FuzzySet> sets;
	
	public UnionFuzzySet(List<FuzzySet> _sets) {
		this.sets = _sets;
	}
	
	@Override
	public double dof(double x) {
		double dof = 0;
		for (FuzzySet fs : sets) {
			double tmp = fs.dof(x);
			if (tmp > dof) dof = tmp;
		}
		return dof;
	}

	@Override
	public FuzzySet alphaCut(double alpha) {
		List<FuzzySet> alphaCutSets = new ArrayList<>();
		for (FuzzySet fs : sets) alphaCutSets.add(fs.alphaCut(alpha));
		return new UnionFuzzySet(alphaCutSets);
	}

	@Override
	public double centerOfGravity() {
		double num = 0;
		double den = 0;
		for (FuzzySet fs : sets) {
			num += fs.centerOfGravity() * fs.height;
			den += fs.height;
		}
		return num / den;
	}

	@Override
	public double[] getPoints() {
		double[] points = new double[0];
		for (FuzzySet fs : sets) points = ArrayUtils.addAll(points, fs.getPoints());
        return points;
	}

	@Override
	public void setPoints(double[] points) {
		throw new UnsupportedOperationException();
	}

	public String toJSON(){
		String points="[";
		for(int i=0; i<sets.size();i++){
			String s=sets.get(i).toJSON();
			points+=s.substring(1, s.length()-1)+",";
		}
		points+="]";
		return points;
	}

}
