package frog.proposition;

import frog.database.DataBase;

/**
 * A meaningful declarative sentence that, given an input, is fullfilled with
 * a certain degree.
 * @param <D> The type of input that is used in the proposition
 */
public interface Proposition<D> {
    /**
     * Degree of fullfilment of the proposition.
     * @param data Input data.
     * @param db DataBase where the information of the fuzzy sets is located
     */
    public double dof(D data, DataBase db);
}
