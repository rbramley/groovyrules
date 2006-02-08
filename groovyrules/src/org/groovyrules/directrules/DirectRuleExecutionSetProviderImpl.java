package org.groovyrules.directrules;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import javax.rules.admin.LocalRuleExecutionSetProvider;
import javax.rules.admin.RuleExecutionSet;
import javax.rules.admin.RuleExecutionSetCreateException;
import javax.rules.admin.RuleExecutionSetProvider;

import org.w3c.dom.Element;

/**
 * <tt>RuleExecutionSetProvider</tt> implementation. Note that when this
 * is created, it allows the class of <tt>RuleExecutionSet</tt> to be specified - 
 * for example, to allow rules to be defined directly as properties, as well as 
 * being read from a rule. Different RuleExecutionSet implementations are used
 * for the different sources of the rules.
 * <p>
 * This currently only supports the configuration of a rule execution
 * set from an XML configuration file as an InputStream. 
 * 
 * @author Rob Newsome
 */
public class DirectRuleExecutionSetProviderImpl implements LocalRuleExecutionSetProvider, RuleExecutionSetProvider {
	
	// TODO: Support properties properly - various scopes for set, rule, etc
	private Map properties;
	
	public DirectRuleExecutionSetProviderImpl(Map properties) {
		if(properties!=null) {
			this.properties = properties;
		}
		else {
			this.properties = new HashMap();
		}
	}

	/* (non-Javadoc)
	 * @see javax.rules.admin.LocalRuleExecutionSetProvider#createRuleExecutionSet(java.io.InputStream, java.util.Map)
	 */
	public RuleExecutionSet createRuleExecutionSet(InputStream inputStream, Map properties)
			throws RuleExecutionSetCreateException, IOException {
		
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("DirectRuleExecutionSets cannot be created from an InputStream - they must be created from a map of properties.");
		
	}
	
	/* (non-Javadoc)
	 * @see javax.rules.admin.LocalRuleExecutionSetProvider#createRuleExecutionSet(java.lang.Object, java.util.Map)
	 */
	
	public RuleExecutionSet createRuleExecutionSet(Object arg0, Map arg1)
			throws RuleExecutionSetCreateException {
		return new DirectRuleExecutionSetImpl(arg0,arg1);
	}
	
	/* (non-Javadoc)
	 * @see javax.rules.admin.LocalRuleExecutionSetProvider#createRuleExecutionSet(java.io.Reader, java.util.Map)
	 */
	public RuleExecutionSet createRuleExecutionSet(Reader arg0, Map arg1)
			throws RuleExecutionSetCreateException, IOException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
	
	/* (non-Javadoc)
	 * @see javax.rules.admin.RuleExecutionSetProvider#createRuleExecutionSet(org.w3c.dom.Element, java.util.Map)
	 */
	public RuleExecutionSet createRuleExecutionSet(Element arg0, Map arg1)
			throws RuleExecutionSetCreateException, RemoteException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
	
	/* (non-Javadoc)
	 * @see javax.rules.admin.RuleExecutionSetProvider#createRuleExecutionSet(java.io.Serializable, java.util.Map)
	 */
	public RuleExecutionSet createRuleExecutionSet(Serializable arg0, Map arg1)
			throws RuleExecutionSetCreateException, RemoteException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
	
	/* (non-Javadoc)
	 * @see javax.rules.admin.RuleExecutionSetProvider#createRuleExecutionSet(java.lang.String, java.util.Map)
	 */
	public RuleExecutionSet createRuleExecutionSet(String arg0, Map arg1)
			throws RuleExecutionSetCreateException, IOException,
			RemoteException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}	
}
