package frog.database;

import java.io.Serializable;

/**
 * Input data consisting in a single double value
 */
public class SingleData implements Data, Serializable {
    public double value;

    public SingleData(double value) {
        this.value = value;
    }
}
