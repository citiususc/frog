package frog.rulebase;

import frog.database.DataBase;
import frog.proposition.Proposition;
import frog.fuzzyset.FuzzySet;
import frog.proposition.LabelProposition;

import java.util.ArrayList;
import java.util.List;

/**
 * A rule whose antecedent and consequent are composed of linguistic variables.
 */
public class MamdaniRule implements Rule {
    /**
     * Array of antecedent propositions.
     */
	public List<? extends Proposition> antecedent;
    
    /**
     * Array of consequent propositions.
     */
    public List<? extends LabelProposition> consequent;

    public MamdaniRule() {
        this(new ArrayList<Proposition>(), new ArrayList<LabelProposition>());
    }
    
    
    public MamdaniRule(List<? extends Proposition> ant, List<? extends LabelProposition> con) {
        this.antecedent = ant;
        this.consequent = con;
    }
    
    /**
     * Infers a list of output fuzzy values from the input data. Each output is
     * the result of an implication (minimum t-norm) over the consequent and the 
     * conjunction of the antecedents (minimum t-norm).
     * @param data Input data
     * @param db DataBase where the information of the fuzzy sets is located
     */
    public FuzzySet[] inference(double[] data, DataBase db) {
        double min = Double.MAX_VALUE;
        for (int i = 0; i < this.antecedent.size(); i++) {
            double dof = this.antecedent.get(i).dof(data[i], db);
            if (dof < min) {
                min = dof;
            }
        }
        
        FuzzySet[] result = new FuzzySet[this.consequent.size()];
        for (int i = 0; i < this.consequent.size(); i++) {
            result[i] = this.consequent.get(i).getLabel(db).alphaCut(min);
        }
        
        return result;
    }
        
    /**
     * Degree of fullfilment of the rule. That is, the minimum t-norm over the
     * degree of fullfilment of the antecedents.
     * @param data Array of input data, one for each antecedent
     * @param db DataBase where the information of the fuzzy sets is located
     */
    public double dof(double[] data, DataBase db) {
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
