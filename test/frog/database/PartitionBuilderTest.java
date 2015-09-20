package frog.database;

import frog.fuzzyset.Trapezium;
import frog.fuzzyset.Triangle;
import junit.framework.TestCase;

/**
 * Tests the different ways of creating automatically fuzzy partitions
 */
public class PartitionBuilderTest extends TestCase {

    private int granularity;
    private Variable var;
    private double[] displacements;
    private double[] centroids;
    private double[] cuts;
    private double fuzziness;

    @Override
    protected void setUp() throws Exception {
        this.granularity = 3;
        this.var = new Variable("input", 0, 10);
        this.displacements = new double[]{0.5, -0.2, 0};
        this.centroids = new double[]{2.5, 5, 7.5};
        this.cuts = new double[]{0, 3.33, 6.66, 10};
        this.fuzziness = 1;
    }

    public void testUniform() throws Exception {
        Partition p = PartitionBuilder.uniform(this.var, this.granularity);
        assertEquals(3, p.size());
        assertEquals(new Triangle(-5, 0, 5), p.get(0));
        assertEquals(new Triangle(0, 5, 10), p.get(1));
        assertEquals(new Triangle(5, 10, 15), p.get(2));
        assertEquals(1, p.best_label(5));
    }

    public void testUniformDisplacement() throws Exception {
        Partition p = PartitionBuilder.uniform(this.var, this.granularity, this.displacements[0]);
        assertEquals(3, p.size());
        assertEquals(new Triangle(-2.5, 2.5, 7.5), p.get(0));
        assertEquals(new Triangle(2.5, 7.5, 12.5), p.get(1));
        assertEquals(new Triangle(7.5, 12.5, 17.5), p.get(2));
        assertEquals(0, p.best_label(5));
    }

    public void testUniformDisplacements() throws Exception {
        Partition p = PartitionBuilder.uniform(this.var, this.granularity, this.displacements);
        assertEquals(3, p.size());
        assertEquals(new Triangle(-2.5, 2.5, 7.5), p.get(0));
        assertEquals(new Triangle(-1, 4, 9), p.get(1));
        assertEquals(new Triangle(5, 10, 15), p.get(2));
        assertEquals(1, p.best_label(5));
    }

    public void testFromCentroids() throws Exception {
        Partition p = PartitionBuilder.fromCentroids(this.centroids, this.var, this.fuzziness);
        assertEquals(3, p.size());
        assertEquals(new Trapezium(-0.001, 0, 2.5, 5), p.get(0));
        assertEquals(new Trapezium(2.5, 5, 5, 7.5), p.get(1));
        assertEquals(new Trapezium(5, 7.5, 10, 10.001), p.get(2));
        assertEquals(1, p.best_label(5));
    }

    public void testFromCuts() throws Exception {
        Partition p = PartitionBuilder.fromCuts(this.cuts, this.fuzziness);
        assertEquals(3, p.size());
        assertEquals(p.get(0), new Trapezium(-0.001, 0, 1.665, 4.995));
        assertEquals(p.get(1), new Trapezium(1.665, 4.995, 4.995, 8.325));
        assertEquals(p.get(2), new Trapezium(4.995, 8.325, 10.0, 10.001));
        assertEquals(1, p.best_label(5));
    }

    public void testFromCutsDisplacement() throws Exception {
        Partition p = PartitionBuilder.fromCuts(this.cuts, this.fuzziness, this.displacements[0]);
        assertEquals(3, p.size());
        assertEquals(new Trapezium(-0.001, 0.0, 3.3275000000000006, 6.6625), p.get(0));
        assertEquals(new Trapezium(3.3275000000000006, 6.6625, 6.6625, 9.9975), p.get(1));
        assertEquals(new Trapezium(6.6625, 9.9975, 10.0, 10.001), p.get(2));
        assertEquals(1, p.best_label(5));
    }

    public void testFromCutsDisplacements() throws Exception {
        Partition p = PartitionBuilder.fromCuts(this.cuts, this.fuzziness, this.displacements);
        assertEquals(3, p.size());
        assertEquals(new Trapezium(-0.001, 0.0, 4.4955, 5.4945), p.get(0));
        assertEquals(new Trapezium(4.4955, 5.4945, 5.4945, 6.493499999999999), p.get(1));
        assertEquals(new Trapezium(5.4945, 6.493499999999999, 10.0, 10.001), p.get(2));
        assertEquals(1, p.best_label(5));
    }
}
