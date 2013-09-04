package frog.rulebase;

import java.util.ArrayList;

/**
 * Represents a set of rules.

 * @param <T> The type of the rules (Mamdani or TSK)
 */
public class RuleBase<T extends Rule> extends ArrayList<T> {
}