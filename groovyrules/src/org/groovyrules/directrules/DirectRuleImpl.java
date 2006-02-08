package org.groovyrules.directrules;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.Script;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.groovyrules.core.RuleAbstract;

/**
 * Rule that is created directly from a string, not from a script.
 * @author Steve Jones
 * @since 14-Jan-2006
 */
public class DirectRuleImpl extends RuleAbstract {
    
    private final static String DESCRIPTION = "Direct Rule Impl";
    
    private final Class rulesClass;
    private final String ruleName;
    
    // TODO: Merge properties with file impl, move to superclass.
    private Map properties = new HashMap(); 

    /**
     * Static helper to create a Groovy class for the rule
     */
    private final static Class createRulesClass(String rulesString) {
        Class returnValue = null;
        GroovyClassLoader groovyLoader = new GroovyClassLoader();
        try {
            returnValue = groovyLoader.parseClass(rulesString);
        } catch (CompilationFailedException exception) {
            throw new RuntimeException("Compliation Failed for " + rulesString, exception);
        }

        return returnValue;
    }
    
    /**
     * Create the binding for this rule
     */
    private final Binding createBinding(List input){
        Binding returnValue  = new Binding();
        // Set the default element
        returnValue.setVariable("data", input);
        //  Now we have to loop through the properties (which should be Integers as keys and Strings as values)
        // grabbing the appropriate element from the input list
        Iterator inputIterator = properties.keySet().iterator();
        while (inputIterator.hasNext()){
            Integer key = (Integer) inputIterator.next();
            returnValue.setVariable((String)properties.get(key), input.get(key.intValue()));
        }
        
        return returnValue;
    }
    
    /**
     * Constructor, this creates the rule
     */
    public DirectRuleImpl(String ruleName, String rule){
        rulesClass = createRulesClass(rule); 
        this.ruleName = ruleName;
    }
    
    /**
     * Execute the rules against the inputs
     * @param inputs
     * @return the returned data from the rules
     */
    public List execute(List inputs) {
        List returnValue = null;
        Binding binding = createBinding(inputs);
        // Now we have to execute the class we have created
        Script scriptObject = InvokerHelper.createScript(rulesClass, binding);
        scriptObject.run();
        
        returnValue = (List) binding.getVariable("data");
        
        return returnValue;
    }

    /**
     * @see javax.rules.admin.Rule#getName()
     */
    public String getName() {
        return this.ruleName;
    }

    /**
     * @see javax.rules.admin.Rule#getDescription()
     */
    public String getDescription() {
        return DESCRIPTION;
    }

    /**
     * @see javax.rules.admin.Rule#getProperty(java.lang.Object)
     */
    public Object getProperty(Object arg0) {
        return this.properties.get(arg0);
    }

    /**
     * @see javax.rules.admin.Rule#setProperty(java.lang.Object, java.lang.Object)
     */
    public void setProperty(Object arg0, Object arg1) {
        this.properties.put(arg0,arg1);

    }

    /**
     * Set all the properties on mass
     * @see org.groovyrules.core.RuleAbstract#setProperties(java.util.Map)
     */
    public void setProperties(Map properties) {
        this.properties = properties;
        
    }

}
