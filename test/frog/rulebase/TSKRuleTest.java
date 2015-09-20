package frog.rulebase;

import frog.database.DataBase;
import frog.fuzzyset.Triangle;
import frog.proposition.ApproximativeLabelProposition;
import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Tests the TSK rule inference
 */
public class TSKRuleTest extends TestCase {

    private TSKRule rule;
    private DataBase db;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Antecedents
        ArrayList<ApproximativeLabelProposition> ant = new ArrayList<>();
        ant.add(new ApproximativeLabelProposition(new Triangle(0, 1, 2)));
        ant.add(new ApproximativeLabelProposition(new Triangle(0, 1, 2)));

        // Consequents
        ArrayList<double[]> con = new ArrayList<>();
        con.add(new double[]{2, 3, 1});

        // Rule
        this.rule = new TSKRule(ant, con);

        // DataBase
        this.db = new DataBase();
    }

    public void testDof() {
        // input data
        double[] input = new double[]{0.5, 1};

        assertEquals(0.5, this.rule.dof(input, db));
    }

    public void testInference() {
        // input data
        double[] input = new double[]{0.5, 1};

        assertEquals(5., this.rule.inference(input)[0]);
    }
}
