/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frog.io;

import frog.database.Variable;
import frog.fuzzyset.FuzzySet;
import frog.proposition.ApproximativeLabelProposition;
import frog.proposition.LinguisticLabelProposition;
import frog.rulebase.KnowledgeBase;
import frog.rulebase.TSKRule;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
/**
 *
 * @author DavidC
 */
public class Export {

    private static KnowledgeBase<TSKRule> db;

    public static void guardarDatabase(String nombre,KnowledgeBase<TSKRule> db1){
        db=db1;
        FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter(nombre);
            pw = new PrintWriter(fichero);
            pw.println("Name: "+db.name);
            pw.println("Type: "+db.type);
            guardarInputs(pw);
            guardarOutputs(pw);
            guardarRules(pw);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (null != fichero)
                    fichero.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private static void guardarInputs(PrintWriter pw){
        pw.println("Inputs:");
        for (Variable input : db.database.inputs) {
            pw.println("  Name: "+input.name);
            pw.println("\tRange: "+input.min+" "+input.max);
            pw.println("\tMean: "+input.norm_mean);
            pw.println("\tVariance: "+input.norm_std);
            if(db.type.equals(KnowledgeBase.LINGUISTIC)){
                pw.println("\tPartition:");
                int j=1;
                for (FuzzySet partition : input.partition) {
                    pw.print("\t\t- Term "+j+": "+partition.getClass().getSimpleName()+":");
                    pw.println(partition.toString());
                    j++;
                }

            }
        }

    }

    private static void guardarOutputs(PrintWriter pw){
        pw.println("Outputs:");
        for(Variable output: db.database.outputs){
            pw.println("  Name: "+output.name);
            pw.println("\tRange: "+output.min+" "+output.max);
            pw.println("\tMean: "+output.norm_mean);
            pw.println("\tVariance: "+output.norm_std);
        }
    }

    private static void guardarRules(PrintWriter pw){
        pw.println("Rules:");

        for(int i=0; i<db.rulebase.size();i++){
            pw.println("  Rule "+(i+1)+":");
            pw.println("\tAntecedent:");
            if(db.type.equals(KnowledgeBase.APPROXIMATIVE)){
                ArrayList<ApproximativeLabelProposition> p=(ArrayList<ApproximativeLabelProposition>)db.rulebase.get(i).antecedent;
                for (int j=0; j<p.size();j++) {
                    pw.print("\t  - "+db.database.inputs[j].name +" is " + p.get(j).label.getClass().getSimpleName() + ":");
                    pw.println(p.get(i).label.toString());
                }
            }
            else if(db.type.equals(KnowledgeBase.LINGUISTIC)){
                ArrayList<LinguisticLabelProposition> p=(ArrayList<LinguisticLabelProposition>)db.rulebase.get(i).antecedent;
                for(int j=0; j<p.size();j++)
                    pw.println("\t  - "+db.database.inputs[p.get(j).label].name +" is term " + (p.get(j).var+1));
            }
            int j;
            for(int t=0; t<db.rulebase.get(i).consequent.size();t++){
                pw.print("\tConsequent:");
                pw.print("\n");
                double d[]= db.rulebase.get(i).consequent.get(t);
                pw.print("\t  - ");
                for(j=0; j<d.length-1;j++){
                    if(d[j]!=0)
                        pw.print("("+d[j]+"*"+db.database.inputs[j].name+")+");
                }
                if(d[j]!=0)
                    pw.println("("+d[j]+")");
            }
        }
    }


}
