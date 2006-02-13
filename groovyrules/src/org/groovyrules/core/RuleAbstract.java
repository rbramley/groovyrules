package org.groovyrules.core;

import java.util.Map;

import javax.rules.admin.Rule;

/**
 * Interface for the rule over and above the standard JSR rule
 * @author Steve Jones
 * @since 15-Jan-2006
 */
public abstract class RuleAbstract implements Rule{
    
    /**
     * Execute the rule against the data
     * @param data The data to process with the rule
     * @return the returned data from the rules
     */
    public abstract void execute(RuleData data);
    
    /**
     * Set the properties for the rule in bulk
     */
    public abstract void setProperties(Map properties);
    
}
