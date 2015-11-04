package frog.rulebase;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a set of rules.

 * @param <T> The type of the rules (Mamdani or TSK)
 */
@SuppressWarnings("serial")
public class RuleBase<T extends Rule> extends ArrayList<T> {
}
