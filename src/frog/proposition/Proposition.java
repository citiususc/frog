package frog.proposition;

import frog.database.DataBase;

import java.io.Serializable;

/**
 * A meaningful declarative sentence that, given an input, is fullfilled with
 * a certain degree.
 * @param <D> The type of input that is used in the proposition
 */
public interface Proposition<D> extends Serializable {
    /**
     * Degree of fulfillment of the proposition.
     * @param data Input data.
     * @param db DataBase where the information of the fuzzy sets is located
     */
    double dof(D data, DataBase db);
}
