package frog.proposition;

import frog.database.DataBase;
import frog.database.SingleData;
import frog.fuzzyset.FuzzySet;
import frog.fuzzyset.Triangle;

public class ApproximativeLabelProposition implements LabelProposition {

    public FuzzySet label;

    public ApproximativeLabelProposition() {
        this.label = new Triangle();
    }
    
    public ApproximativeLabelProposition(FuzzySet label) {
        this.label = label;
    }
    
    public double dof(SingleData data, DataBase db) {
        return label.dof(data.value);
    }
    
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
