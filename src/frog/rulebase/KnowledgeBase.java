package frog.rulebase;

import frog.database.DataBase;

import java.io.Serializable;

/**
 * Represents a whole Knowledge Base composed by a data base and a rule base
 */
public class KnowledgeBase<T extends Rule> implements Serializable {
    public DataBase database;
    public RuleBase<T> rulebase;

    public KnowledgeBase() {
        this(new DataBase(), new RuleBase<T>());
    }

    public KnowledgeBase(DataBase database, RuleBase<T> rulebase) {
        this.database = database;
        this.rulebase = rulebase;
    }

    public DataBase getDatabase() {
        return database;
    }

    public RuleBase<T> getRulebase() {
        return rulebase;
    }
}
