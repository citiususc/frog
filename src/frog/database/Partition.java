package frog.database;

import frog.fuzzyset.FuzzySet;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Fuzzy partition, i.e., a split of the variable domain into a fuzzy sets  for each linguistic term
 */
public class Partition extends ArrayList<FuzzySet> implements Serializable {

    /**
     * Searchs for the most similar linguistic label for the input
     * @param x a real value
     * @return the index of the linguistic label most similar to the input
     */
    public int best_label(double x) {
            double max_dof = 0;
            int best_label = 0;
            for (int i = 0; i < this.size(); i++) {
                    double dof = this.get(i).dof(x);
                    if (dof > max_dof) {
                            max_dof = dof;
                            best_label = i;
                    }
            }

            return best_label;
    }
	
}
