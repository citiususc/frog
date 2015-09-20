package frog.inference;

import frog.database.*;
import frog.proposition.LinguisticLabelProposition;
import frog.rulebase.KnowledgeBase;
import frog.rulebase.MamdaniRule;
import frog.rulebase.RuleBase;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests the inference method for mamdani knowledge bases
 */
public class MamdaniInferenceTest extends TestCase {

    private KnowledgeBase<MamdaniRule> kb;

    @Override
    protected void setUp() throws Exception {
        // DataBase definition
        Variable[] inputs = new Variable[2];
        inputs[0] = new Variable("input1", 0, 10);
        inputs[0].partition = PartitionBuilder.uniform(inputs[0], 2);
        inputs[1] = new Variable("input2", -5, 5);
        inputs[1].partition = PartitionBuilder.uniform(inputs[1], 2);
        Variable[] outputs = new Variable[1];
        outputs[0] = new Variable("output", -50, 50);
        outputs[0].partition = PartitionBuilder.uniform(outputs[0], 2);
        DataBase db = new DataBase(inputs, outputs);

        // RuleBase definition
        RuleBase<MamdaniRule> rb = new RuleBase<>();
        for (int i = 0; i < inputs[0].partition.size(); i++) {
            for (int j = 0; j < inputs[1].partition.size(); j++) {
                List<LinguisticLabelProposition> antecedents = new ArrayList<>();
                antecedents.add(new LinguisticLabelProposition(0, i));
                antecedents.add(new LinguisticLabelProposition(1, j));
                List<LinguisticLabelProposition> consequents = new ArrayList<>();
                consequents.add(new LinguisticLabelProposition(2, i==j?1:0));
                rb.add(new MamdaniRule(antecedents, consequents));
            }
        }
        this.kb = new KnowledgeBase<>(db, rb);
    }

    public void testInference() {
        MamdaniInference.centerOfGravity(kb, new double[]{2, 0});
    }
}
