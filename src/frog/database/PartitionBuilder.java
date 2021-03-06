package frog.database;

import frog.fuzzyset.Rectangle;
import frog.fuzzyset.Trapezium;
import frog.fuzzyset.Triangle;
import java.util.Arrays;

/**
 * Class responsible of split a variable domain into linguistic labels
 * 
 * @author Ismael Rodríguez
 */
public class PartitionBuilder {

	/**
	 * Builds an uniform partition of an specific granularity.
	 * 
	 * @param var
	 *            Variable to be partitioned
	 * @param g
	 *            Granularity of the partition, i.e., the number of labels
	 */
	public static Partition uniform(Variable var, int g) {
		return PartitionBuilder.uniform(var, g, 0);
	}

	/**
	 * Builds an uniform partition of an specific granularity with a global
	 * symbolic displacement. The displacement is applied equally for each
	 * linguistic label, preserving a complete partition.
	 * 
	 * @param var
	 *            Variable to be partitioned
	 * @param g
	 *            Granularity of the partition, i.e., the number of labels
	 * @param alpha
	 *            Symbolic displacement for all linguistic label
	 */
	public static Partition uniform(Variable var, int g, double alpha) {
		Partition result = new Partition();

		if (g != 1) {
			double step = (var.max - var.min) / (double) (g - 1);

			for (int i = 0; i < g; i++) {
				double a = var.min + (step * i - step) + alpha * step;
				double b = var.min + step * i + alpha * step;
				double c = var.min + (step * i + step) + alpha * step;

				result.add(new Triangle(a, b, c));
			}
		} else {
			result.add(new Rectangle(var.min, var.max));
		}

		return result;
	}

	/**
	 * Builds a partition of an specific granularity with a symbolic
	 * desplacement for each linguistic label. With a displacement for each
	 * linguistic label, the resultant partition may be not complete.
	 * 
	 * @param var
	 *            Variable to be partitioned
	 * @param g
	 *            Granularity of the partition, i.e., the number of labels
	 * @param alpha
	 *            Symbolic displacement for each linguistic label
	 */
	public static Partition uniform(Variable var, int g, double[] alpha) {
		Partition result = new Partition();

		if (g != 1) {
			double step = (var.max - var.min) / (double) (g - 1);

			for (int i = 0; i < g; i++) {
				double a = var.min + (step * i - step) + alpha[i] * step;
				double b = var.min + step * i + alpha[i] * step;
				double c = var.min + (step * i + step) + alpha[i] * step;

				result.add(new Triangle(a, b, c));
			}
		} else {
			result.add(new Rectangle(var.min, var.max));
		}

		return result;
	}

	/**
	 * Builds a partition from a list of centroids in the domain of the
	 * variable, with the posibility of setting the fuzziness of the resulting
	 * fuzzy sets. The centroids are those points that must have a degree of
	 * fullfilment of 1 for one linguistic label.
	 * 
	 * @param centroids
	 *            array of centroid points in the domain of the variable
	 * @param var
	 *            Variable to be partitioned
	 * @param f
	 *            Fuzziness index, defined between 0 (raw) and 1 (full fuzzy)
	 */
	public static Partition fromCentroids(double[] centroids, Variable var,
			double f) {
		Arrays.sort(centroids);
		double[] cuts = new double[centroids.length + 1];
		cuts[0] = var.min;
		for (int i = 0; i < centroids.length - 1; i++) {
			cuts[i + 1] = (centroids[i] + centroids[i + 1]) / 2.;
		}
		cuts[centroids.length] = var.max;

		return fromCuts(cuts, f);
	}

	/**
	 * Builds a partition from a list of cuts over the domain of the variable.
	 * The cuts indicates the point where two adjacent linguistic labels are
	 * crossed, and, therefore, the degree of fullfilment of both fuzzy sets is
	 * 0.5
	 * 
	 * @param cuts
	 *            array of cut points in the domain of the variable
	 * @see #fromCuts(double[], double, double)
	 */
	public static Partition fromCuts(double[] cuts) {
		return fromCuts(cuts, 1, 0);
	}

	/**
	 * Builds a partition from a list of cuts in the domain of the variable,
	 * with the posibility of setting the fuzziness of the resulting fuzzy sets.
	 * The cuts indicates the point where two adjacent linguistic labels are
	 * crossed, and, therefore, the degree of fullfilment of both fuzzy sets is
	 * 0.5
	 * 
	 * @param cuts
	 *            array of cut points in the domain of the variable
	 * @param f
	 *            Fuzziness index, defined between 0 (raw) and 1 (full fuzzy)
	 * @see #fromCuts(double[], double, double)
	 */
	public static Partition fromCuts(double[] cuts, double f) {
		return fromCuts(cuts, f, 0);
	}

	/**
	 * Builds a partition from a list of cuts in the domain of the variable,
	 * with the possibility of setting the fuzziness of the resulting fuzzy sets
	 * and to set a symbolic displacement equal for all linguistic labels. The
	 * cuts indicates the point where two adjacent linguistic labels are
	 * crossed, and, therefore, the degree of fullfilment of both fuzzy sets is
	 * 0.5 </br></br> Ref: Ishibuchi, H. & Yamamoto, T. Performance evaluation
	 * of fuzzy partitions with different fuzzification grades Proceedings of
	 * the 2002 IEEE International Conference on Fuzzy Systems, 2002, 2,
	 * 1198-1203
	 * 
	 * @param cuts
	 *            array of cut points in the domain of the variable
	 * @param f
	 *            Fuzziness index, defined between 0 (raw) and 1 (full fuzzy)
	 * @param alpha
	 *            Symbolic displacement
	 */
	public static Partition fromCuts(double[] cuts, double f, double alpha) {
		double[] alphas = new double[cuts.length];
		Arrays.fill(alphas, alpha);
		return fromCuts(cuts, f, alphas);
	}

	/**
	 * Builds a partition from a list of cuts in the domain of the variable,
	 * with the possibility of setting the fuzziness of the resulting fuzzy sets
	 * and to set a symbolic displacement specific for each linguistic label. The
	 * cuts indicates the point where two adjacent linguistic labels are
	 * crossed, and, therefore, the degree of fullfilment of both fuzzy sets is
	 * 0.5 </br></br> Ref: Ishibuchi, H. & Yamamoto, T. Performance evaluation
	 * of fuzzy partitions with different fuzzification grades Proceedings of
	 * the 2002 IEEE International Conference on Fuzzy Systems, 2002, 2,
	 * 1198-1203
	 *
	 * @param cuts
	 *            array of cut points in the domain of the variable
	 * @param f
	 *            Fuzziness index, defined between 0 (raw) and 1 (full fuzzy)
	 * @param alpha
	 *            Symbolic displacement for each linguistic label
	 */
	public static Partition fromCuts(double[] cuts, double f, double[] alpha) {
		/**
		 * Desplacement of the cuts
		 */
		double[] tmp = new double[cuts.length];
		for (int i = 0; i < cuts.length; i++) {
			if (i == 0 || i == cuts.length-1 || alpha[i-1] == 0) {
				tmp[i] = cuts[i];
			} else if (alpha[i-1] < 0) {
				tmp[i] = cuts[i] + (cuts[i] - cuts[i-1]) * alpha[i-1];
			} else if (alpha[i-1] > 0) {
				tmp[i] = cuts[i] + (cuts[i+1] - cuts[i]) * alpha[i-1];
			}
		}
		cuts = tmp;
		
		int k = cuts.length - 1;

		/**
		 * Defines upper and lower limits. This limits have to be a degree of
		 * fullfilment of 0 except for the first and last linguistic labels,
		 * when in this case must be 1.
		 */
		double[] u = new double[k];
		double[] l = new double[k];
		for (int i = 0; i < k; i++) {
			u[i] = cuts[i + 1];
			l[i] = cuts[i];
		}

		/**
		 * Defines medium points. This points must have a degree of fullfilment
		 * of 1.
		 */
		double[] m = new double[k];
		m[0] = l[0];
		for (int j = 1; j < k - 1; j++) {
			m[j] = (l[j] + u[j]) / 2.;
		}
		m[k - 1] = u[k - 1];

		/**
		 * Defines the null fuzziness partition. This partition defines each
		 * linguistic variable as raw sets.
		 */
		double[][] p0 = new double[k][4];
		p0[0][0] = l[0] - 1e-3;
		p0[0][1] = l[0];
		for (int j = 0; j < k - 1; j++) {
			p0[j][2] = u[j];
			p0[j][3] = u[j];
			p0[j + 1][0] = l[j+1];
			p0[j + 1][1] = l[j+1];
		}
		p0[k - 1][2] = u[k - 1];
		p0[k - 1][3] = u[k - 1] + 1e-3;

		/**
		 * Defines the full fuzziness partition. This partition defines each
		 * linguistic variable with the minimum possible number of points with
		 * degree of fullfillment equal to 1.
		 */
		double[][] p1 = new double[k][4];
		p1[0][0] = l[0] - 1e-3;
		p1[0][1] = l[0];
		for (int j = 0; j < k - 1; j++) {
			if (u[j] <= (m[j] + m[j + 1]) / 2.) {
				p1[j][2] = m[j];
				p1[j][3] = u[j] + (u[j] - m[j]);
			} else {
				p1[j][2] = u[j] - (m[j + 1] - u[j]);
				p1[j][3] = m[j + 1];
			}
			p1[j + 1][0] = p1[j][2];
			p1[j + 1][1] = p1[j][3];
		}
		p1[k - 1][2] = u[k - 1];
		p1[k - 1][3] = u[k - 1] + 1e-3;

		/**
		 * Defines the final partition. This partition is built from the null
		 * and full fuzniess partition, with a proportion of the argument f.
		 */
		Partition result = new Partition();
		for (int i = 0; i < k; i++) {
			double[] pF = new double[4];
			for (int j = 0; j < 4; j++) {
				pF[j] = p0[i][j] + (p1[i][j] - p0[i][j]) * f;
			}
			result.add(new Trapezium(pF[0], pF[1], pF[2], pF[3]));
		}

		return result;
	}

	/**
	 * Builds a partition from a list of cuts in the domain of the variable,
	 * with the possibility to set a symbolic displacement specific for each
	 * linguistic label. The cuts indicates the point where two adjacent
	 * linguistic labels are crossed, and, therefore, the degree of fulfillment
	 * of both fuzzy sets is 0.5
	 * </br></br> Ref: Ishibuchi, H. & Yamamoto, T. Performance evaluation
	 * of fuzzy partitions with different fuzzification grades Proceedings of
	 * the 2002 IEEE International Conference on Fuzzy Systems, 2002, 2,
	 * 1198-1203
	 *
	 * @param cuts
	 *            array of cut points in the domain of the variable
	 * @param alpha
	 *            Symbolic displacement for each linguistic label
	 */
    public static Partition fromCuts(double[] cuts, double[] alpha) {
        Partition result = fromCuts(cuts);

        for (int i = 0; i < result.size(); i++) {
            Trapezium fs = (Trapezium)result.get(i);
            double displacement = (fs.d - fs.a) * 0.5 * alpha[i];
            fs.a = fs.a + displacement;
            fs.b = fs.b + displacement;
            fs.c = fs.c + displacement;
            fs.d = fs.d + displacement;
        }

        return result;
    }
}
