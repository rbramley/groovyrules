package org.groovyrules.core;

import groovy.util.GroovyScriptEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.rules.admin.RuleExecutionSet;

/**
 * Implementation of the <tt>RuleExecutionSet</tt>; this contains
 * a list of <tt>Rule</tt> instances, which share a 
 * <tt>GroovyScriptEngine</tt> to run within.
 * 
 * @author Rob Newsome
 */
public class RuleExecutionSetImpl extends RuleExecutionSetAbstract implements RuleExecutionSet {

	private String description;
	private String name;
	
	// TODO: Support properties properly - various scopes for set, rule, etc
	private Map properties;
	
	public RuleExecutionSetImpl(RuleExecutionSetConfiguration config, Map creationProperties) throws IOException {
		
		this.name = config.getName();
		this.description = config.getDescription();
		
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
	
}
