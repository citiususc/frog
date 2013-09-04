/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frog.inference;

import frog.database.Data;
import frog.rulebase.KnowledgeBase;
import frog.rulebase.RuleBase;
import frog.rulebase.TSKRule;

/**
 * This component derives the outputs from the input values for a TSK rule
 * base system.
 */
public class TSKInference {

    /**
     * An entire inference engine where:
     * <ul>
     *  <li>Fuzzification operator is the point wise fuzzification</li>
     *  <li>Fuzzy implication is minimum t-norm</li>
     *  <li>The output of each rule is a linear combination of the inputs</li>
     *  <li>Defuzzification is the weighted sum of the outputs of each rule</li>
     * </ul>
     * 
     * @param kb TSK rule base system
     * @param data Array of input data, one for each antecedent
     * @return Crisp output value
     * @return 
     */
    public static double[] inference(KnowledgeBase<TSKRule> kb, Data[] data, double[] input) {
        int n_outputs = kb.database.outputs.length;

        double[] result = new double[n_outputs];
        double norm = 0.;
        for (int i = 0; i < kb.rulebase.size(); i++) {
            double[] tmp = kb.rulebase.get(i).inference(input);
            double dof = kb.rulebase.get(i).dof(data, kb.database);
            norm += dof;
            for (int o = 0; o < n_outputs; o++) {
                result[o] += tmp[o]*dof;
            }
        }
        
        for (int o = 0; o < n_outputs; o++) {
            if (norm == 0) {
                result[o] = Double.NaN;
            } else {
                result[o] /= norm;
            }
        }

        return result;
    }
}
