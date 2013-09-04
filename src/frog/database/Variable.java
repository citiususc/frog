package frog.database;

import java.util.Random;

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
    public double norm_mean;
    
    /**
     * The standard deviation for normalization.
     */
    public double norm_std;
    
    /**
     * The partition of the variable into linguistic terms/labels (fuzzy sets).
     */
    public Partition partition;

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
        
        
}
