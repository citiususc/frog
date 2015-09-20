package frog.rulebase;

import frog.database.DataBase;
import frog.fuzzyset.Trapezium;
import frog.fuzzyset.Triangle;
import frog.proposition.ApproximativeLabelProposition;
import frog.proposition.LabelProposition;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests the mamdani rule inference
 */
public class MamdaniRuleTest extends TestCase {
    private MamdaniRule rule;
    private DataBase db;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Antecedents
        List<ApproximativeLabelProposition> ant = new ArrayList<>();
        ant.add(new ApproximativeLabelProposition(new Triangle(0, 1, 2)));
        ant.add(new ApproximativeLabelProposition(new Triangle(0, 1, 2)));

        // Consequents
        List<LabelProposition> con = new ArrayList<>();
        con.add(new ApproximativeLabelProposition(new Triangle(-5, 0, 5)));

        // Rule
        this.rule = new MamdaniRule(ant, con);

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

        assertEquals(this.rule.inference(input, db)[0], new Trapezium(-5., -2.5, 2.5, 5));
    }
}
