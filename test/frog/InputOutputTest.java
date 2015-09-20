package frog;

import frog.database.*;
import frog.rulebase.*;
import junit.framework.TestCase;
import frog.proposition.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;

/**
 * Tests the Input/Output of a knowledge base from/to a YAML file
 */
public class InputOutputTest extends TestCase {

    /**
     * Tests if a #KnowledgeBase can be converted to a YAML format
     */
    public static void testOutput() {
        // DataBase definition
        Variable[] inputs = new Variable[2];
        inputs[0] = new Variable("input1", 0, 10);
        inputs[0].partition = PartitionBuilder.uniform(inputs[0], 2);
        inputs[1] = new Variable("input2", -5, 5);
        inputs[1].partition = PartitionBuilder.uniform(inputs[1], 2);
        Variable[] outputs = new Variable[1];
        outputs[0] = new Variable("output", -50, 50);
        DataBase db = new DataBase(inputs, outputs);

        // RuleBase definition
        RuleBase<TSKRule> rb = new RuleBase<>();
        for (int i = 0; i < inputs[0].partition.size(); i++) {
            for (int j = 0; j < inputs[1].partition.size(); j++) {
                ArrayList<LinguisticLabelProposition> antecedents = new ArrayList<>();
                antecedents.add(new LinguisticLabelProposition(0, i));
                antecedents.add(new LinguisticLabelProposition(1, j));
                ArrayList<double[]> consequents = new ArrayList<>();
                consequents.add(new double[]{i, j, 0.2});
                rb.add(new TSKRule(antecedents, consequents));
            }
        }
        KnowledgeBase<TSKRule> kb = new KnowledgeBase<>(db, rb);

        Yaml yaml = new Yaml();
        yaml.dump(kb);
        
        assertTrue(true);
    }

    /**
     * Tests if a #KnowledgeBase can be loadad from a YAML file
     * @throws FileNotFoundException
     */
    public static void testInput() throws FileNotFoundException {
    	Yaml yaml = new Yaml();

        // YAML file loaded from resources folder
    	String file = Thread.currentThread().getContextClassLoader().getResource("cool_load.kb").getFile();
    	FileInputStream input = new FileInputStream(file);
        Object obj = yaml.load(input);
        assertTrue(obj instanceof KnowledgeBase);
    }
}
