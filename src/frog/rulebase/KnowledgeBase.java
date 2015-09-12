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

    public KnowledgeBase() {
        this.database = new DataBase();
        this.rulebase = new RuleBase<>();
    }

    public KnowledgeBase(DataBase database, RuleBase<T> rulebase) {
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
}
