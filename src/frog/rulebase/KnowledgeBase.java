/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frog.rulebase;

import frog.database.DataBase;

import java.io.Serializable;

/**
 *
 * @author ismael.rodriguez
 */
public class KnowledgeBase<T extends Rule> implements Serializable {
    public DataBase database;
    public RuleBase<T> rulebase;
    public String name;
    public String type;
    public static String LINGUISTIC = "L";
    public static String APPROXIMATIVE = "A";

    public KnowledgeBase() {
        this.database = new DataBase();
        this.rulebase = new RuleBase<>();
    }

    public KnowledgeBase(DataBase database, RuleBase<T> rulebase) {
        this.database = database;
        this.rulebase = rulebase;
    }

    public KnowledgeBase(String name, String type, DataBase database, RuleBase<T> rulebase) {
        this.name = name;
        this.type = type;
        this.database = database;
        this.rulebase = rulebase;
    }

    public DataBase getDatabase() {
        return database;
    }

    public void setDatabase(DataBase database) {
        this.database = database;
    }

    public RuleBase<T> getRulebase() {
        return rulebase;
    }

    public void setRulebase(RuleBase<T> rulebase) {
        this.rulebase = rulebase;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
