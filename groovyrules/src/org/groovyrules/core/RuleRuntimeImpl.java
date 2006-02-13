package org.groovyrules.core;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.rules.RuleExecutionSetNotFoundException;
import javax.rules.RuleRuntime;
import javax.rules.RuleSession;
import javax.rules.RuleSessionCreateException;
import javax.rules.RuleSessionTypeUnsupportedException;

/**
 * Implementation of the <tt>RuleRuntime</tt>. This is used to fetch
 * the <tt>RuleSession</tt>. Currently this only supports stateless
 * rule sessions. 
 * 
 * @author Rob Newsome
 */
public class RuleRuntimeImpl implements RuleRuntime {

	/* (non-Javadoc)
	 * @see javax.rules.RuleRuntime#createRuleSession(java.lang.String, java.util.Map, int)
	 */
	public RuleSession createRuleSession(String uri, Map properties, int sessionType)
			throws RuleSessionTypeUnsupportedException,
			RuleSessionCreateException, RuleExecutionSetNotFoundException,
			RemoteException {
		
		if(sessionType==RuleRuntime.STATELESS_SESSION_TYPE) {
		
			return new StatelessRuleSessionImpl(uri, (RuleExecutionSetAbstract)RuleAdministratorImpl.registeredRuleExecutionSets.get(uri));
		
		} 
		else if(sessionType==RuleRuntime.STATEFUL_SESSION_TYPE) {
			
			return new StatefulRuleSessionImpl(uri, (RuleExecutionSetAbstract)RuleAdministratorImpl.registeredRuleExecutionSets.get(uri));
			
		}
		else {
		
			throw new IllegalArgumentException("Invalid session type specified.");
			
		}
		
		
	}

	/* (non-Javadoc)
	 * @see javax.rules.RuleRuntime#getRegistrations()
	 */
	public List getRegistrations() throws RemoteException {
		Map registrations = RuleAdministratorImpl.registeredRuleExecutionSets;
		List registrationURIs = new ArrayList(registrations.keySet());
		return registrationURIs;
	}

}
