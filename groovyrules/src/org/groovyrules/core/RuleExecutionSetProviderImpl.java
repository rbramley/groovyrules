package org.groovyrules.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Map;

import javax.rules.admin.LocalRuleExecutionSetProvider;
import javax.rules.admin.RuleExecutionSet;
import javax.rules.admin.RuleExecutionSetCreateException;

/**
 * <tt>RuleExecutionSetProvider</tt> implementation.
 * <p>
 * This currently only supports the configuration of a rule execution
 * set from an XML configuration file as an InputStream. 
 * 
 * @author Rob Newsome
 */
public class RuleExecutionSetProviderImpl implements LocalRuleExecutionSetProvider {
	
	/* (non-Javadoc)
	 * @see javax.rules.admin.LocalRuleExecutionSetProvider#createRuleExecutionSet(java.io.InputStream, java.util.Map)
	 */
	public RuleExecutionSet createRuleExecutionSet(InputStream inputStream, Map arg1)
			throws RuleExecutionSetCreateException, IOException {

		RuleExecutionSetConfiguration config = 
			new RuleExecutionSetConfiguration(inputStream);
		
		return new RuleExecutionSetImpl(config);
		
	}
	
	/* (non-Javadoc)
	 * @see javax.rules.admin.LocalRuleExecutionSetProvider#createRuleExecutionSet(java.lang.Object, java.util.Map)
	 */
	
	public RuleExecutionSet createRuleExecutionSet(Object arg0, Map arg1)
			throws RuleExecutionSetCreateException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
	
	/* (non-Javadoc)
	 * @see javax.rules.admin.LocalRuleExecutionSetProvider#createRuleExecutionSet(java.io.Reader, java.util.Map)
	 */
	public RuleExecutionSet createRuleExecutionSet(Reader arg0, Map arg1)
			throws RuleExecutionSetCreateException, IOException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
	
}
