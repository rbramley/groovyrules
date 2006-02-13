package org.groovyrules.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.rules.ObjectFilter;
import javax.rules.admin.RuleExecutionSet;

/**
 * Abstract class for all the rule execution sets. This mainly handles running
 * all the rules, which is common for all implementations, as all rules extend
 * from the {@link org.groovyrules.core.RuleAbstract} class.
 * 
 * @author Steve Jones
 * @since 15-Jan-2006
 */
public abstract class RuleExecutionSetAbstract implements RuleExecutionSet {
    
    // The default filter class is null if not set
    String defaultFilterClass = null;
    
    // The list of rules
    protected final List rules = new ArrayList();
    
    // The properties, requires J2SE 5.0 if use generics
    // Map properties = new HashMap<Integer, String>();
    protected Map properties = new HashMap();
    
    /**
     * Runs all rules in this execution set.
     */
    protected void runRules(RuleData data, ObjectFilter filter) {
    	    	
    	// Ensure that we use the default filter is none
    	// is specified and there is a default.
    	
    	if(filter==null && defaultFilterClass!=null) {
    		
    		// Try to build a new default filter and use that
    		try {
    			ObjectFilter defaultFilter = (ObjectFilter)Class.forName(defaultFilterClass).newInstance();
    			runRules(data, defaultFilter);
    		}
    		catch(Exception e) {
    			throw new RuntimeException("Unable to construct default object filter instance", e);
    		}
    		
    	}
    	else {
    			
    		Iterator rules = getRules().iterator();
    		
    		while(rules.hasNext()) {
    			RuleAbstract rule = (RuleAbstract)rules.next();	
                rule.setProperties(this.properties);
    			rule.execute(data);
    		}
    
    		// Determine if filtering is required
    		
    		if(filter==null) {
    			// no filtering
    		}
    		else {
    			// TODO Run the filter over the objects?
    		}
    		
    	}
    	
    }
    
    /**
     * @see javax.rules.admin.RuleExecutionSet#getRules()
     */
    public List getRules() {
        return rules;
    }
    
    /**
     * @see javax.rules.admin.RuleExecutionSet#setDefaultObjectFilter(java.lang.String)
     */
    public void setDefaultObjectFilter(String filterClass) {
        this.defaultFilterClass = filterClass;
    }

    /**
     * @see javax.rules.admin.RuleExecutionSet#getDefaultObjectFilter()
     */
    public String getDefaultObjectFilter() {
        return this.defaultFilterClass;
    }
    
    
    /**
     * Properties are used to map from the list position to a given name,
     * if there are no properties then the only mapping is the input list
     * to "data"
     * 
     * @see javax.rules.admin.RuleExecutionSet#getProperty(java.lang.Object)
     */
    public Object getProperty(Object arg0) {
        return this.properties.get(arg0);
    }

    /**
     * @see javax.rules.admin.RuleExecutionSet#setProperty(java.lang.Object,
     *      java.lang.Object)
     */
    public void setProperty(Object arg0, Object arg1) {
        this.properties.put(arg0,arg1);
    }
    
}
