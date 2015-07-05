import frog.database.*;
import frog.fuzzyset.*;
import frog.io.Export;
import frog.io.Import;
import frog.rulebase.*;
import frog.proposition.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;

/**
 * Created by ismael.rodriguez on 23/06/15.
 */
public class Prueba {
    public static void main(String[] args) throws IOException {
        Yaml yaml = new Yaml();
        String text = new String(Files.readAllBytes(Paths.get("ele-1.yml")), StandardCharsets.UTF_8);
        KnowledgeBase<TSKRule> kb = (KnowledgeBase<TSKRule>) yaml.load(text);
        Export.guardarDatabase("ficheroPruebaYaml.yaml", kb);
        
        
    }

    public static KnowledgeBase<TSKRule> linguisticKnowledgeBase() {
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
        RuleBase rb = new RuleBase<TSKRule>();
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
        kb.name="ejemplo1";
        kb.type="L";
        return kb;
    }

    public static KnowledgeBase<TSKRule> approximativeKnowledgeBase() {
        // DataBase definition
        Variable[] inputs = new Variable[2];
        inputs[0] = new Variable("input1", 0, 10);
        inputs[1] = new Variable("input2", -5, 5);
        Variable[] outputs = new Variable[1];
        outputs[0] = new Variable("output", -50, 50);
        DataBase db = new DataBase(inputs, outputs);

        // RuleBase definition
        RuleBase rb = new RuleBase<TSKRule>();
        ArrayList<ApproximativeLabelProposition> antecedents = new ArrayList<>();
        antecedents.add(new ApproximativeLabelProposition(new Trapezium(0, 2, 8, 10)));
        antecedents.add(new ApproximativeLabelProposition(new Trapezium(-5, 3, 4, 5)));
        ArrayList<double[]> consequents = new ArrayList<>();
        consequents.add(new double[]{-1, 2, -0.3});
        rb.add(new TSKRule(antecedents, consequents));

        return new KnowledgeBase<>(db, rb);
    }
}
