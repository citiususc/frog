package frog.database;

import java.io.Serializable;
import java.util.Random;

/**
 * Characteristic or qualitie that the individuals of a population possess, 
 * expressed in numerical form, and with values between two numbers.
 */
public class Variable implements Serializable {

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
    public double norm_mean;
    
    /**
     * The standard deviation for normalization.
     */
    public double norm_std;
    
    /**
     * The partition of the variable into linguistic terms/labels (fuzzy sets).
     */
    public Partition partition;

    public Variable() {
    }
    
    public Variable(String name, double min, double max) {
        this(name, min, max, new Partition());
    }

    public Variable(String name, double min, double max, Partition partition) {
        this.name = name;
        this.min = min;
        this.max = max;
        this.partition = partition;
    }
    
    public double range() {
        return (this.max - this.min);
    }
    
    public double random(Random rnd) {
        return rnd.nextDouble() * this.range() + this.min;
    }

    @Override
    public String toString() {
        return name + " [" + min + ", " + max + "] " + partition; 
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getNorm_mean() {
        return norm_mean;
    }

    public void setNorm_mean(double norm_mean) {
        this.norm_mean = norm_mean;
    }

    public double getNorm_std() {
        return norm_std;
    }

    public void setNorm_std(double norm_std) {
        this.norm_std = norm_std;
    }

    public Partition getPartition() {
        return partition;
    }

    public void setPartition(Partition partition) {
        this.partition = partition;
    }
}
