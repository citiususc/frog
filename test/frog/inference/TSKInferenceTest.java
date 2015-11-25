package frog.inference;

import frog.database.DataBase;
import frog.database.PartitionBuilder;
import frog.database.Variable;
import frog.proposition.LinguisticLabelProposition;
import frog.rulebase.KnowledgeBase;
import frog.rulebase.RuleBase;
import frog.rulebase.TSKRule;
import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Tests the inference methods for TSK knowledge bases
 */
public class TSKInferenceTest extends TestCase {

    private KnowledgeBase<TSKRule> kb;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // DataBase definition
        Variable[] inputs = new Variable[2];
        inputs[0] = new Variable("input1", 0, 10);
        inputs[0].partition = PartitionBuilder.uniform(inputs[0], 2);
        inputs[1] = new Variable("input2", -5, 5);
        inputs[1].partition = PartitionBuilder.uniform(inputs[1], 2);
        Variable[] outputs = new Variable[1];
        outputs[0] = new Variable("output", -50, 50);
        DataBase db = new DataBase(inputs, outputs);

        // RuleBase definition
        RuleBase<TSKRule> rb = new RuleBase<>();
        for (int i = 0; i < inputs[0].partition.size(); i++) {
            for (int j = 0; j < inputs[1].partition.size(); j++) {
                ArrayList<LinguisticLabelProposition> antecedents = new ArrayList<>();
                antecedents.add(new LinguisticLabelProposition(0, i));
                antecedents.add(new LinguisticLabelProposition(1, j));
                ArrayList<double[]> consequents = new ArrayList<>();
                consequents.add(new double[]{i, j, 0.2});
                rb.add(new TSKRule(antecedents, consequents));
            }
        }
        this.kb = new KnowledgeBase<>(db, rb);
    }

    public void testInference() {
        assertEquals(0.7714285714285716, TSKInference.inference(this.kb, new double[]{2, 0})[0]);
    }

    public void testNormInference() {
        assertEquals(0.7714285714285716, TSKInference.normalizedInference(kb, new double[]{2, 0})[0]);
    }

    public void testDenormInference() {
        assertEquals(0.7714285714285716, TSKInference.denormalizedInference(kb, new double[]{2, 0})[0]);
    }
}
