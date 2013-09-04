package frog.database;

import java.util.Arrays;

/**
 * Contains the linguistic variables considered in the fuzzy system
 */
public class DataBase {
    /** Input variables **/
    public Variable[] inputs;
    /** Output variables **/
    public Variable[] outputs;

    public DataBase(Variable[] inputs, Variable[] outputs) {
            this.inputs = inputs;
            this.outputs = outputs;
    }
    
    /**
     * @return The number of input and output variables
     */
    public int length() {
        return this.inputs.length + this.outputs.length;
    }
    
    /**
     * @param i The index of the variable to return
     * @return The variable the i-th variable. if i < inputs.length, then is an 
     * input, otherwise is an output 
     */
    public Variable get(int i) {
        if (i < inputs.length) {
            return inputs[i];
        } else {
            return outputs[i-inputs.length];
        }
    }
    
    public void set(int i, Variable v) {
        if (i < inputs.length) {
            inputs[i] = v;
        } else {
            outputs[i-inputs.length] = v;
        }
    }

    @Override
    public String toString() {
        return "{Inputs: " + Arrays.toString(inputs) + ", Outputs: " + Arrays.toString(outputs) + "}";
    }
        
        
}