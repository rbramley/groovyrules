package org.groovyrules.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.rules.admin.RuleExecutionSet;

/**
 * The objective of this class is to enable rules to be provided directly from
 * the application for execution by the rules engine.
 * 
 * @author Steve Jones
 * @since 14-Jan-2006
 */
public class DirectRuleExecutionSetImpl extends RuleExecutionSetAbstract  implements RuleExecutionSet {

    /**
     * Serialisation as some of the rules stuff could be remote
     */
    private static final long serialVersionUID = -6642762509979716400L;

    private final static String NAME_AND_DESCRIPTION = "Application Specified Ruleset";

    /**
     * Constructor for the class, takes in an object (not currently used) and a
     * map which contains the strings for the rules.
     * 
     * @param unused
     *            Object that isn't currently used
     * @param ruleMap
     *            the map of rules to operate on
     */
    public DirectRuleExecutionSetImpl(Object properties, Map ruleMap) {
        // Need to go through the map creating the rules
        Iterator iterator = ruleMap.keySet().iterator();
        
        while(iterator.hasNext()){
            Object key = iterator.next();
            rules.add(new DirectRuleImpl((String) key,(String) ruleMap.get(key)));
        }
        // Now need to check if there are any properties coming in
        if (properties instanceof Map){
            this.properties = (Map) properties;
        }
        

    }

    /**
     * @see javax.rules.admin.RuleExecutionSet#getName()
     */
    public String getName() {
        return NAME_AND_DESCRIPTION;
    }

    /**
     * @see javax.rules.admin.RuleExecutionSet#getDescription()
     */
    public String getDescription() {
        return NAME_AND_DESCRIPTION;
    }


    
}
