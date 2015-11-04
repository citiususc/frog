package frog.database;

import java.io.Serializable;

/**
 * Characteristic or qualitie that the individuals of a population possess, 
 * expressed in numerical form, and with values between two numbers.
 */
public class Variable {

    /**
     * Name of the variable.
     */
    public String name;
    
    /**
     * The lower bound of the domain of the variable.
     */
    public double min;
    
    /**
     * The upper bound of the domain of the variable.
     */
    public double max;
    
    /**
     * The mean for normalization.
     */
    public double normMean;
    
    /**
     * The standard deviation for normalization.
     */
    public double normStd;
    
    /**
     * The partition of the variable into linguistic terms/labels (fuzzy sets).
     */
    public Partition partition;

    public Variable() {
        this("var", -Double.MAX_VALUE, Double.MAX_VALUE);
    }
    
    public Variable(String name, double min, double max) {
        this(name, min, max, new Partition());
    }

    public Variable(String name, double min, double max, Partition partition) {
        this(name, min, max, partition, 0., 1.);
    }
    
    public Variable(String name, double min, double max, Partition partition, double normMean, double normStd) {
        this.name = name;
        this.min = min;
        this.max = max;
        this.normMean = normMean;
        this.normStd = normStd;
        this.partition = partition;
    }

    /**
     * @return the domain of the variable
     */
    public double domain() {
        return (this.max - this.min);
    }

    @Override
    public String toString() {
        return name + " [" + min + ", " + max + "] " + partition; 
    }

    public String getName() {
        return name;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getNormMean() {
        return normMean;
    }

    public double getNormStd() {
        return normStd;
    }

    public Partition getPartition() {
        return partition;
    }
}
