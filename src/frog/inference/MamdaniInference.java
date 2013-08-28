package frog.inference;

import frog.database.Data;
import frog.fuzzyset.FuzzySet;
import frog.rulebase.KnowledgeBase;
import frog.rulebase.MamdaniRule;

/**
 * This component derives the outputs from the input values for a Mamdani rule
 * base system.
 */
public class MamdaniInference {
    
    /**
     * An entire inference engine where:
     * <ul>
     *  <li>Fuzzification operator is the point wise fuzzification</li>
     *  <li>Fuzzy implication is minimum t-norm</li>
     *  <li>Fuzzy conjunction is minimum t-norm</li>
     *  <li>Defuzzification is the center of gravity</li>
     * </ul>
     * 
     * @param kb Mamdani rule base system
     * @param data Array of input data, one for each antecedent
     * @return Crisp output value
     */
    public static double[] centerOfGravity(KnowledgeBase<MamdaniRule> kb, Data[] data) {
        int n_outputs = kb.rulebase.get(0).consequent.size();
        FuzzySet[][] output = new FuzzySet[n_outputs][kb.rulebase.size()];

        for (int i = 0; i < kb.rulebase.size(); i++) {
            FuzzySet[] tmp = kb.rulebase.get(i).inference(data, kb.database);
            for (int o = 0; o < n_outputs; o++) {
                output[o][i] = tmp[o];
            }
        }

        double[] result = new double[n_outputs];
        for (int o = 0; o < n_outputs; o++) {
            result[o] = Defuzzify.centerOfGravity(output[o]);
        }

        return result;
    }
}
