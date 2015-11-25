/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frog.inference;

import frog.rulebase.KnowledgeBase;
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
     * @return Crisp output value
     */
    public static double[] inference(KnowledgeBase<TSKRule> kb, double[] input) {
        int n_outputs = kb.database.outputs.length;

        double[] result = new double[n_outputs];
        double norm = 0.;
        for (int i = 0; i < kb.rulebase.size(); i++) {
            double[] tmp = kb.rulebase.get(i).inference(input);
            double dof = kb.rulebase.get(i).dof(input, kb.database);
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

    /**
     * An entire inference engine that works with normalized inputs where:
     * <ul>
     *  <li>Fuzzification operator is the point wise fuzzification</li>
     *  <li>Fuzzy implication is minimum t-norm</li>
     *  <li>The output of each rule is a linear combination of the inputs</li>
     *  <li>Defuzzification is the weighted sum of the outputs of each rule</li>
     * </ul>
     *
     * @param kb TSK rule base system normalized
     * @param input normalized input values
     * @return Crisp output value
     */
    public static double[] normalizedInference(KnowledgeBase<TSKRule> kb, double[] input) {
        int n_outputs = kb.database.outputs.length;

        double[] denormInput = new double[input.length];
        for (int j = 0; j < denormInput.length; j++) denormInput[j] = input[j] * kb.database.inputs[j].normStd + kb.database.inputs[j].normMean;

        double[] result = new double[n_outputs];
        double norm = 0.;
        for (int i = 0; i < kb.rulebase.size(); i++) {
            double[] tmp = kb.rulebase.get(i).inference(input);
            double dof = kb.rulebase.get(i).dof(denormInput, kb.database);
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
                result[o] = result[o] * kb.database.outputs[o].normStd + kb.database.outputs[o].normMean;
            }
        }

        return result;
    }

    /**
     * An entire inference engine that works with denormalized Knowledge Bases, where:
     * <ul>
     *  <li>Fuzzification operator is the point wise fuzzification</li>
     *  <li>Fuzzy implication is minimum t-norm</li>
     *  <li>The output of each rule is a linear combination of the inputs</li>
     *  <li>Defuzzification is the weighted sum of the outputs of each rule</li>
     * </ul>
     *
     * @param kb TSK rule base system
     * @return Crisp output value
     */
    public static double[] denormalizedInference(KnowledgeBase<TSKRule> kb, double[] input) {
        int n_outputs = kb.database.outputs.length;

        double[] normInput = new double[input.length];
        for (int j = 0; j < normInput.length; j++) normInput[j] = (input[j] - kb.database.inputs[j].normMean) /  kb.database.inputs[j].normStd;

        double[] result = new double[n_outputs];
        double norm = 0.;
        for (int i = 0; i < kb.rulebase.size(); i++) {
            double[] tmp = kb.rulebase.get(i).inference(normInput);
            double dof = kb.rulebase.get(i).dof(input, kb.database);
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
                result[o] = result[o]*kb.database.outputs[o].normStd + kb.database.outputs[o].normMean;
            }
        }

        return result;
    }
}
