package org.groovyrules.core;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.rules.admin.Rule;

/**
 * Implementation of a <tt>Rule</tt>. Each rule is backed
 * by a <tt>.groovyrule</tt> file, which is a small Groovy script.
 * <p>
 * All rules share the same GroovyScriptEngine, which is passed to 
 * them; it deals with caching the Groovy scripts, and recompiling
 * them when they are updated. i.e. the script files can be updated
 * at runtime. 
 * 
 * @author Rob Newsome
 */
public class RuleImpl implements Rule {

	private GroovyScriptEngine scriptEngine;
	private String scriptFile;
	
	// TODO: Support properties properly
	private Map properties = new HashMap();
	
	public RuleImpl(GroovyScriptEngine scriptEngine, String scriptFile) {
		this.scriptEngine = scriptEngine;
		this.scriptFile = scriptFile;
	}
	
	public List execute(List inputs) {
		
		try {
			
			Binding binding = new Binding();
			binding.setVariable("data", inputs);
			
			scriptEngine.run(this.scriptFile, binding);
			
			return (List)binding.getVariable("data");
			
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
			
	}
	
	/* (non-Javadoc)
	 * @see javax.rules.admin.Rule#getDescription()
	 */
	public String getDescription() {
		return "GroovyRules rule from file: '" + scriptFile + "'";
	}
	/* (non-Javadoc)
	 * @see javax.rules.admin.Rule#getName()
	 */
	public String getName() {
		return "Rule(" + scriptFile + ")";
	}
	/* (non-Javadoc)
	 * @see javax.rules.admin.Rule#getProperty(java.lang.Object)
	 */
	public Object getProperty(Object key) {
		return properties.get(key);
	}
	/* (non-Javadoc)
	 * @see javax.rules.admin.Rule#setProperty(java.lang.Object, java.lang.Object)
	 */
	public void setProperty(Object key, Object value) {
		properties.put(key, value);
	}
}
