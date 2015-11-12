package frog.database;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Contains the linguistic variables considered in the fuzzy system
 */
public class DataBase implements Serializable {
    /** Input variables **/
    public Variable[] inputs;
    /** Output variables **/
    public Variable[] outputs;


    public DataBase() {
        this(new Variable[0], new Variable[0]);
    }

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

    /**
     * @param i The index of the variable to set
     * @param var Variable to be set
     * @return The variable the i-th variable. if i < inputs.length, then is an
     * input, otherwise is an output
     */
    public void set(int i, Variable var) {
        if (i < inputs.length) {
            inputs[i] = var;
        } else {
            outputs[i-inputs.length] = var;
        }
    }


    @Override
    public String toString() {
        return "{Inputs: " + Arrays.toString(inputs) + ", Outputs: " + Arrays.toString(outputs) + "}";
    }

    public Variable[] getInputs() {
        return inputs;
    }

    public Variable[] getOutputs() {
        return outputs;
    }
}
