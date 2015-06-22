/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frog.io;

import frog.database.PartitionBuilder;
import frog.database.Variable;
import frog.fuzzyset.Rectangle;
import frog.fuzzyset.Singleton;
import frog.fuzzyset.Trapezium;
import frog.fuzzyset.Triangle;
import frog.proposition.ApproximativeLabelProposition;
import frog.proposition.LinguisticLabelProposition;
import frog.rulebase.KnowledgeBase;
import frog.rulebase.RuleBase;
import frog.rulebase.TSKRule;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DavidC
 */
public class Import {
    private static KnowledgeBase<TSKRule> kb;

    public static KnowledgeBase<TSKRule> importarBase(String nomFich){

        kb = new KnowledgeBase<>();
        File archivo;
        FileReader fr=null;
        BufferedReader br=null;
        try{
            archivo = new File(nomFich);
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
        }catch(Exception e){
            System.out.println("Error, no se encuentra el archivo");
            System.exit(0);
        }

        try {
            for(int i=0; i<2; i++){
                String linea = br.readLine();
                StringTokenizer datos = new StringTokenizer(linea," ");
                datos.nextToken();
                String miDato = datos.nextToken();
                if(i==0){
                    kb.name=miDato;
                }
                else{
                    kb.type=miDato;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Import.class.getName()).log(Level.SEVERE, null, ex);
        }
        importarInOut(br);
        if(kb.type.equals(KnowledgeBase.APPROXIMATIVE)){
            importarRulesAprox(br);
        }
        else if (kb.type.equals(KnowledgeBase.LINGUISTIC)){
            importarRulesLing(br);
        }
        try{
            fr.close();
        }catch(Exception e){
            System.out.println("Error, al cerrar el archivo");
            System.exit(0);
        }


        return kb;
    }

    private static StringTokenizer pasarLinea(BufferedReader br){
        StringTokenizer datos=null;
        try {
            String linea = br.readLine();
            datos = new StringTokenizer(linea," ");
            datos.nextToken();
        } catch (IOException ex) {
            System.out.println("ERROR");
        }
        return datos;
    }

    private static void importarInOut(BufferedReader br){
        ArrayList<Variable> inputs = new ArrayList<>();
        ArrayList<Variable> outputs = new ArrayList<>();
        Variable v; boolean flag=true,flag2=false,flag3=false;int partition=0;
        try {
            StringTokenizer datos=null;
            br.readLine();
            while(true){
                if(flag2==false){
                    datos = pasarLinea(br);
                }
                if(datos.hasMoreTokens()==false && (flag3==true || kb.type.equals(KnowledgeBase.APPROXIMATIVE))){
                    if(flag==false){
                        break;
                    }
                    else{
                        datos = pasarLinea(br);
                        flag2=false;
                        flag=false;
                    }
                }
                String nombreInput = datos.nextToken();
                datos = pasarLinea(br);
                Float min = Float.parseFloat(datos.nextToken());
                Float max = Float.parseFloat(datos.nextToken());
                datos = pasarLinea(br);
                //Float media = Float.parseFloat(datos.nextToken());
                datos = pasarLinea(br);
                //Float varianza = Float.parseFloat(datos.nextToken());

                if(flag==true){
                    v = new Variable(nombreInput,min,max);
                    if(kb.type.equals(KnowledgeBase.LINGUISTIC) && flag==true){
                        pasarLinea(br);
                        partition=0;
                        while(true){
                            String l=br.readLine();
                            if(l.matches("(.*)Name:(.*)")){
                                flag2=true;
                                datos = new StringTokenizer(l," ");
                                datos.nextToken();
                                break;
                            }
                            else if(l.equals("Outputs:")){
                                datos=new StringTokenizer(l," ");
                                datos.nextToken();
                                flag3=true;
                                break;
                            }
                            else{
                                partition++;
                            }

                        }
                        v.partition = PartitionBuilder.uniform(v, partition);

                    }
                    inputs.add(v);
                }
                else{
                    v= new Variable(nombreInput,min,max);
                    outputs.add(v);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Import.class.getName()).log(Level.SEVERE, null, ex);
        }
        Variable in[] = new Variable[inputs.size()];
        for(int i=0; i<inputs.size();i++){
            in[i]= inputs.get(i);
        }

        Variable out[] = new Variable[outputs.size()];
        for(int i=0; i<outputs.size();i++){
            out[i]= outputs.get(i);
        }

        kb.database.inputs = in.clone();
        kb.database.outputs = out.clone();
    }

    private static void importarRulesAprox(BufferedReader br){
        kb.rulebase = new RuleBase<>();
        ArrayList<ApproximativeLabelProposition> antecedentes;
        ArrayList<double[]> consecuentes;
        try {
            while(br.readLine()!=null){
                br.readLine();
                antecedentes=leerAntecedentesAprx(br);
                consecuentes=leerConsecuentes(br);
                kb.rulebase.add(new TSKRule(antecedentes,consecuentes));
            }
        } catch (IOException ex) {
            Logger.getLogger(Import.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void importarRulesLing(BufferedReader br){
        kb.rulebase = new RuleBase<>();
        ArrayList<LinguisticLabelProposition> antecedentes;
        ArrayList<double[]> consecuentes;
        try {
            while(br.readLine()!=null){
                br.readLine();
                antecedentes=leerAntecedentesLing(br);
                consecuentes=leerConsecuentes(br);
                kb.rulebase.add(new TSKRule(antecedentes,consecuentes));
            }
        } catch (IOException ex) {
            Logger.getLogger(Import.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static ArrayList<LinguisticLabelProposition> leerAntecedentesLing(BufferedReader br){
        ArrayList<LinguisticLabelProposition> antecedentes = new ArrayList<>();
        String linea;Integer input=0;
        try {
            linea = br.readLine();
            while(!linea.matches("(.*)Consequent:(.*)")){
                StringTokenizer datos = new StringTokenizer(linea," ");
                datos.nextToken();
                datos.nextToken();
                String nom = datos.nextToken();
                datos.nextToken();
                datos.nextToken();
                Integer num = Integer.parseInt(datos.nextToken());
                linea = br.readLine();
                for(int i=0; i<kb.database.inputs.length;i++){
                    String aux = kb.database.inputs[i].name;
                    if(aux.equals(nom)){
                        input=i;
                        break;
                    }
                }
                antecedentes.add(new LinguisticLabelProposition(num-1, input));
            }

        } catch (IOException ex) {
            Logger.getLogger(Import.class.getName()).log(Level.SEVERE, null, ex);
        }

        return antecedentes;
    }

    private static ArrayList<ApproximativeLabelProposition> leerAntecedentesAprx(BufferedReader br){
        int contador;
        String linea;
        ArrayList<ApproximativeLabelProposition> antecedentes = new ArrayList<>();
        try {
            linea = br.readLine();
            while(!linea.matches("(.*)Consequent:(.*)")){
                contador=0;
                ArrayList<String> an = new ArrayList<>();
                StringTokenizer datos = new StringTokenizer(linea," ");
                while(datos.hasMoreTokens()){
                    if(contador<2 || contador==3){
                        datos.nextToken();
                    }
                    else{
                        an.add(datos.nextToken());
                    }
                    contador++;
                }
                switch (an.size()){
                    case 3: //singleton
                        Float punto = Float.parseFloat(an.get(2));
                        antecedentes.add(new ApproximativeLabelProposition(new Singleton(punto)));
                        break;
                    case 4: //rectangle
                        Float a = Float.parseFloat(an.get(2));
                        Float b = Float.parseFloat(an.get(3));
                        antecedentes.add(new ApproximativeLabelProposition(new Rectangle(a, b)));
                        break;
                    case 5://triangle
                        Float a1 = Float.parseFloat(an.get(2));
                        Float b1 = Float.parseFloat(an.get(3));
                        Float c1 = Float.parseFloat(an.get(4));
                        antecedentes.add(new ApproximativeLabelProposition(new Triangle(a1, b1, c1)));
                        break;
                    case 6://trapezium
                        Float a2 = Float.parseFloat(an.get(2));
                        Float b2 = Float.parseFloat(an.get(3));
                        Float c2 = Float.parseFloat(an.get(4));
                        Float d2 = Float.parseFloat(an.get(5));
                        antecedentes.add(new ApproximativeLabelProposition(new Trapezium(a2, b2, c2, d2)));
                        break;
                }
                linea = br.readLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(Import.class.getName()).log(Level.SEVERE, null, ex);
        }
        return antecedentes;
    }

    private static ArrayList<double[]> leerConsecuentes(BufferedReader br){
        ArrayList<double[]> con = new ArrayList<>();
        ArrayList aux = new ArrayList<>();
        try {
            String linea = br.readLine();
            StringTokenizer datos = new StringTokenizer(linea," ");
            datos.nextToken();
            datos.nextToken();
            linea = datos.nextToken();
            datos = new StringTokenizer(linea,"+");
            while(datos.hasMoreTokens()){
                StringTokenizer datos2= new StringTokenizer(datos.nextToken(),")");
                datos2 = new StringTokenizer(datos2.nextToken(),"(");
                datos2 = new StringTokenizer(datos2.nextToken(),"*");
                aux.add(Double.parseDouble(datos2.nextToken()));
            }
        } catch (IOException ex) {
            Logger.getLogger(Import.class.getName()).log(Level.SEVERE, null, ex);
        }
        double[] c2 = new double[aux.size()];
        for(int i=0; i<aux.size();i++){
            c2[i]=(double)aux.get(i);
        }
        con.add(c2);
        return con;
    }

}
