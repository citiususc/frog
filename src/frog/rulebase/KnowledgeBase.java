/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frog.rulebase;

import frog.database.DataBase;

/**
 *
 * @author ismael.rodriguez
 */
public class KnowledgeBase<T extends Rule> {
    public DataBase database;
    public RuleBase<T> rulebase;

    public KnowledgeBase(DataBase database, RuleBase<T> rulebase) {
        this.database = database;
        this.rulebase = rulebase;
    }


}
