package org.groovyrules.core;

import java.util.List;
import java.util.Map;

import javax.rules.admin.Rule;

/**
 * Interface for the rule over and above the standard JSR rule
 * @author Steve Jones
 * @since 15-Jan-2006
 */
public interface GroovyRule extends Rule{
    
    /**
     * Execute the rules against the inputs
     * @param inputs
     * @return the returned data from the rules
     */
    public List execute(List inputs);
    
    /**
     * Set the properties for the rule in bulk
     */
    public void setProperties(Map properties);
    
}
