import frog.database.*;
import frog.rulebase.*;
import junit.framework.TestCase;
import frog.proposition.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;

import javax.print.attribute.TextSyntax;

/**
 * Created by ismael.rodriguez on 23/06/15.
 */
@SuppressWarnings("unused")
public class InputOutputTest extends TestCase {

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
    
    public static void testInput() throws FileNotFoundException {
    	Yaml yaml = new Yaml();
    	
    	String file = Thread.currentThread().getContextClassLoader().getResource("cool_load.kb").getFile();
    	FileInputStream input = new FileInputStream(file);
        KnowledgeBase<TSKRule> kb = (KnowledgeBase<TSKRule>) yaml.load(input);
        
        assertTrue(true);
    }
}
