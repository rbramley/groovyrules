package org.groovyrules.core;

import groovy.util.GroovyScriptEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	
	public RuleExecutionSetImpl(RuleExecutionSetConfiguration config) throws IOException {
		
		this.name = config.getName();
		this.description = config.getDescription();
		this.rules = new ArrayList();
		
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
		throw new UnsupportedOperationException();
	}	
	/* (non-Javadoc)
	 * @see javax.rules.admin.RuleExecutionSet#getProperty(java.lang.Object)
	 */
	public Object getProperty(Object arg0) {
		throw new UnsupportedOperationException();
	}
	/* (non-Javadoc)
	 * @see javax.rules.admin.RuleExecutionSet#setDefaultObjectFilter(java.lang.String)
	 */
	public void setDefaultObjectFilter(String arg0) {
		throw new UnsupportedOperationException();
	}
	/* (non-Javadoc)
	 * @see javax.rules.admin.RuleExecutionSet#setProperty(java.lang.Object, java.lang.Object)
	 */
	public void setProperty(Object arg0, Object arg1) {
		throw new UnsupportedOperationException();
	}
}
