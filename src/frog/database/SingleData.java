package frog.database;

/**
 * Input data consisting in a single double value
 */
public class SingleData implements Data {
    public double value;

    public SingleData(double value) {
        this.value = value;
    }
}
