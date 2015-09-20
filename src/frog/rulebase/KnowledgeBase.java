package frog.rulebase;

import frog.database.DataBase;

/**
 * Represents a whole Knowledge Base composed by a data base and a rule base
 */
public class KnowledgeBase<T extends Rule>  {
    public DataBase database;
    public RuleBase<T> rulebase;

    public KnowledgeBase() {
        this(new DataBase(), new RuleBase<T>());
    }

    public KnowledgeBase(DataBase database, RuleBase<T> rulebase) {
        this.database = database;
        this.rulebase = rulebase;
    }
}
