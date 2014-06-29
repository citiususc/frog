/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frog.rulebase;

import java.util.ArrayList;

import frog.database.Data;
import frog.database.DataBase;
import frog.proposition.Proposition;

/**
 * A rule whose antecedent is composed of linguistic variables and the 
 * consequent is represented by a function of the input variables.
 */
@SuppressWarnings("rawtypes")
public class TSKRule implements Rule {
    /**
     * Array of antecedent propositions
     */
    public ArrayList<? extends Proposition> antecedent;
    
    /**
     * Array of a list of double for each consequent, representing the weights
     * for each input in the linear combination for the output
     */
    public ArrayList<double[]> consequent;

    public TSKRule() {
        this(new ArrayList<Proposition>(), new ArrayList<double[]>());
    }
    
    public TSKRule(ArrayList<? extends Proposition> ant, ArrayList<double[]> con) {
            this.antecedent = ant;
            this.consequent = con;
    }

    /**
     * Infers a list of output crisp values from the input data. Each output is
     * the result of a linear combination of the input data and the consequent
     * weights.
     * @param data
     * @return 
     */
    public double[] inference(double[] data) {
            double result[] = new double[consequent.size()];
            
            for (int o = 0; o < consequent.size(); o++) {
                for (int i = 0; i < data.length; i++) {
                    result[o] += data[i]*this.consequent.get(o)[i];
                }
                result[o] += this.consequent.get(o)[data.length];
            }

            return result;
    }

    /**
     * Degree of fullfilment of the rule. That is, the minimum t-norm over the
     * degree of fullfilment of the antecedents.
     * @param data Array of input data, one for each antecedent
     * @param db DataBase where the information of the fuzzy sets is located
     */
    public double dof(Data[] data, DataBase db) {
        double min = Double.MAX_VALUE;
        for (int i = 0; i < this.antecedent.size(); i++) {
            double dof = this.antecedent.get(i).dof(data[i], db);
            if (dof < min) {
                min = dof;
            }
        }

        return min;
    }

    @Override
    public String toString() {
            return "{A: " + this.antecedent + ", C: " + this.consequent + "}";
    }
}
