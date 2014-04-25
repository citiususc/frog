/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frog.proposition;

import frog.database.DataBase;
import frog.database.SingleData;
import frog.fuzzyset.FuzzySet;

/**
 *
 * @author ismael.rodriguez
 */
public class LinguisticLabelProposition implements LabelProposition {

    public int var;
    public int label;

    public LinguisticLabelProposition() {
        this(0, 0);
    }
    
    public LinguisticLabelProposition(int var, int label) {
        this.var = var;
        this.label = label;
    }
    
    public double dof(SingleData data, DataBase db) {
        return db.get(var).partition.get(label).dof(data.value);
    }
    
    public FuzzySet getLabel(DataBase db) {
        return db.get(this.var).partition.get(label);
    }
    
    @Override
    public String toString() {
        return "V" + var + "L" + label;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LinguisticLabelProposition) {
            LinguisticLabelProposition other = (LinguisticLabelProposition)obj;
            if (this.var == other.var && this.label == other.label) {
                return true;
            }
        }
        return false;
    }

}
