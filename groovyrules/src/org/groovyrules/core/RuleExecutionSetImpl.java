package org.groovyrules.core;

import groovy.util.GroovyScriptEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.rules.ObjectFilter;
import javax.rules.admin.RuleExecutionSet;

/**
 * Implementation of the <tt>RuleExecutionSet</tt>; this contains
 * a list of <tt>Rule</tt> instances, which share a 
 * <tt>GroovyScriptEngine</tt> to run within.
 * 
 * @author Rob Newsome
 */
public class RuleExecutionSetImpl implements RuleExecutionSet {

	private String description;
	private String name;
	private List rules;
	
	// TODO: Support properties properly - various scopes for set, rule, etc
	private Map properties;

	// The default filter class is null if not set
	private String defaultFilterClass = null;
	
	public RuleExecutionSetImpl(RuleExecutionSetConfiguration config, Map creationProperties) throws IOException {
		
		this.name = config.getName();
		this.description = config.getDescription();
		this.rules = new ArrayList();
		
		if(creationProperties!=null) {
			this.properties = creationProperties;
		}
		else {
			this.properties = new HashMap();
		}
		
		String[] roots = new String[] { config.getRuleRoot() };
		GroovyScriptEngine scriptEngine = new GroovyScriptEngine(roots);
		
		for(int i=0; i<config.getRuleFiles().size(); i++) {
		
			RuleImpl rule = new RuleImpl(scriptEngine, (String)config.getRuleFiles().get(i));
			rules.add(rule);
			
		}
		
	}
	
	/* (non-Javadoc)
	 * @see javax.rules.admin.RuleExecutionSet#getDescription()
	 */
	public String getDescription() {
		return description;
	}
	
	/* (non-Javadoc)
	 * @see javax.rules.admin.RuleExecutionSet#getName()
	 */
	public String getName() {
		return name;
	}
	
	/* (non-Javadoc)
	 * @see javax.rules.admin.RuleExecutionSet#getRules()
	 */
	public List getRules() {
		return rules;
	}
	
	/* (non-Javadoc)
	 * @see javax.rules.admin.RuleExecutionSet#getDefaultObjectFilter()
	 */
	public String getDefaultObjectFilter() {
		return this.defaultFilterClass;
	}	

	/* (non-Javadoc)
	 * @see javax.rules.admin.RuleExecutionSet#setDefaultObjectFilter(java.lang.String)
	 */
	public void setDefaultObjectFilter(String filterClass) {
		this.defaultFilterClass = filterClass;
	}
	
	/* (non-Javadoc)
	 * @see javax.rules.admin.RuleExecutionSet#getProperty(java.lang.Object)
	 */
	public Object getProperty(Object key) {
		return this.properties.get(key);
	}
	
	/* (non-Javadoc)
	 * @see javax.rules.admin.RuleExecutionSet#setProperty(java.lang.Object, java.lang.Object)
	 */
	public void setProperty(Object key, Object value) {
		this.properties.put(key, value);
	}

	/**
	 * Runs all rules in this execution set.
	 */
	List runRules(List objects, ObjectFilter filter) {
		
		// Ensure that we use the default filter is none
		// is specified and there is a default.
		
		if(filter==null && defaultFilterClass!=null) {
			
			// Try to build a new default filter and use that
			try {
				ObjectFilter defaultFilter = (ObjectFilter)Class.forName(defaultFilterClass).newInstance();
				return runRules(objects, defaultFilter);
			}
			catch(Exception e) {
				throw new RuntimeException("Unable to construct default object filter instance", e);
			}
			
		}
		else {
				
			Iterator rules = getRules().iterator();
			
			while(rules.hasNext()) {
				RuleImpl rule = (RuleImpl)rules.next();			
				rule.execute(objects);
			}
	
			// Determine if filtering is required
			
			if(filter==null) {
				return objects;
			}
			else {
				Iterator objIt = objects.iterator();
				List forReturn = new ArrayList();
				while(objIt.hasNext()) {
					Object returnedObject = objIt.next();
					Object filteredObject = filter.filter(returnedObject);
					if(filteredObject!=null) {
						forReturn.add(filteredObject);
					}
				}
				return forReturn;
			}
			
		}
		
	}
	
}
