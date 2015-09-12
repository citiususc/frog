/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frog.proposition;

import frog.database.DataBase;
import frog.fuzzyset.FuzzySet;

/**
 * A proposition that declares that a variable is a concrete label 
 * (fuzzy set)
 * @author ismael.rodriguez
 */
public interface LabelProposition extends Proposition<Double>  {
    /**
     * Returns the label that is indicated by this proposition
     * @param db DataBase where the information of the fuzzy sets is located
     */
    public FuzzySet getLabel(DataBase db);
}
