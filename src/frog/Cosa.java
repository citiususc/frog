package frog;

import frog.database.Partition;
import frog.database.PartitionBuilder;
import frog.database.Variable;
import frog.fuzzyset.FuzzySet;
import frog.fuzzyset.Trapezium;

/**
 * Created by ismael.rodriguez on 30/03/15.
 */
public class Cosa {
    public static void main(String[] args) {
        Variable var = new Variable("no", 0, 3);
        Partition par = PartitionBuilder.fromCuts(new double[]{0, 0.75, 2.625, 3}, var, 1, new double[]{0, 0, 0});
        for (FuzzySet fs : par) {
            System.out.println(fs.toString());
        }
        System.out.println();

        par = PartitionBuilder.fromCuts(new double[]{0, 0.75, 1.25, 2.625, 3}, var, 1, new double[]{0, 0, 0});
        for (FuzzySet fs : par) {
            System.out.println(fs.toString());
        }
        System.out.println();
        par = PartitionBuilder.fromCuts(new double[]{0, 0.75, 1.25, 2.625, 3}, var, 1, new double[]{-0.25, 0.1, -0.1});
        for (FuzzySet fs : par) {
            System.out.println(fs.toString());
        }
    }
}
