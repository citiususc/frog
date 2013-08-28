package frog.proposition;

import frog.database.DataBase;
import frog.database.SingleData;
import frog.fuzzyset.FuzzySet;

public class ApproximativeLabelProposition implements LabelProposition {

    public FuzzySet label;
    
    public ApproximativeLabelProposition(FuzzySet label) {
        this.label = label;
    }
    
    @Override
    public double dof(SingleData data, DataBase db) {
        return label.dof(data.value);
    }
    
    @Override
    public FuzzySet getLabel(DataBase db) {
        return this.label;
    }
    
    @Override
    public String toString() {
        return label.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ApproximativeLabelProposition) {
            ApproximativeLabelProposition other = (ApproximativeLabelProposition)obj;
            if (this.label.equals(other.label)) {
                return true;
            }
        }
        return false;
    }

}
