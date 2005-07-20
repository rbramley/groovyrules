package org.groovyrules.core;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import javax.rules.admin.LocalRuleExecutionSetProvider;
import javax.rules.admin.RuleAdministrator;
import javax.rules.admin.RuleExecutionSet;
import javax.rules.admin.RuleExecutionSetDeregistrationException;
import javax.rules.admin.RuleExecutionSetProvider;
import javax.rules.admin.RuleExecutionSetRegisterException;

/**
 * <tt>RuleAdministrator</tt> implementation. Allows the registration of 
 * <tt>RuleExecutionSet</tt>s, against URIs. 
 * 
 * @author Rob Newsome
 */
public class RuleAdministratorImpl implements RuleAdministrator {

	protected static Map registeredRuleExecutionSets = new HashMap();
	
	/* (non-Javadoc)
	 * @see javax.rules.admin.RuleAdministrator#deregisterRuleExecutionSet(java.lang.String, java.util.Map)
	 */
	public void deregisterRuleExecutionSet(String arg0, Map arg1)
			throws RuleExecutionSetDeregistrationException, RemoteException {
		throw new UnsupportedOperationException();
	}
	/* (non-Javadoc)
	 * @see javax.rules.admin.RuleAdministrator#getLocalRuleExecutionSetProvider(java.util.Map)
	 */
	public LocalRuleExecutionSetProvider getLocalRuleExecutionSetProvider(
			Map arg0) throws RemoteException {
		return new RuleExecutionSetProviderImpl();
	}
	/* (non-Javadoc)
	 * @see javax.rules.admin.RuleAdministrator#getRuleExecutionSetProvider(java.util.Map)
	 */
	public RuleExecutionSetProvider getRuleExecutionSetProvider(Map arg0)
			throws RemoteException {
		throw new UnsupportedOperationException();
	}
	/* (non-Javadoc)
	 * @see javax.rules.admin.RuleAdministrator#registerRuleExecutionSet(java.lang.String, javax.rules.admin.RuleExecutionSet, java.util.Map)
	 */
	public void registerRuleExecutionSet(String uri, RuleExecutionSet res,
			Map arg2) throws RuleExecutionSetRegisterException, RemoteException {

		registeredRuleExecutionSets.put(uri, res);
		
	}
}
