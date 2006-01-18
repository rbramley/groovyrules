package org.groovyrules.core;

import java.rmi.RemoteException;
import java.util.List;

import javax.rules.InvalidRuleSessionException;
import javax.rules.ObjectFilter;
import javax.rules.RuleExecutionSetMetadata;
import javax.rules.RuleRuntime;
import javax.rules.StatelessRuleSession;

/**
 * Implementation of a <tt>StatelessRuleSession</tt>. This is created
 * with a <tt>RuleExecutionSet</tt>, and will execute the rules held within
 * that execution set against a <tt>List</tt> of data.
 * 
 * @author Rob Newsome
 */
public class StatelessRuleSessionImpl implements StatelessRuleSession {

	private String uri;
	private RuleExecutionSetImpl res;
	
	protected StatelessRuleSessionImpl(String uri, RuleExecutionSetImpl res) {
		this.uri = uri;
		this.res = res;
	}
	
	
	/* (non-Javadoc)
	 * @see javax.rules.StatelessRuleSession#executeRules(java.util.List, javax.rules.ObjectFilter)
	 */
	public List executeRules(List inputs, ObjectFilter filter)
			throws InvalidRuleSessionException, RemoteException {
		
		return res.runRules(inputs, filter);
		
	}
	
	
	/* (non-Javadoc)
	 * @see javax.rules.StatelessRuleSession#executeRules(java.util.List)
	 */
	public List executeRules(List inputs) throws InvalidRuleSessionException,
			RemoteException {

		return res.runRules(inputs, null);
		
	}
	
	/* (non-Javadoc)
	 * @see javax.rules.RuleSession#getRuleExecutionSetMetadata()
	 */
	public RuleExecutionSetMetadata getRuleExecutionSetMetadata()
			throws InvalidRuleSessionException, RemoteException {
		return new RuleExecutionSetMetadataImpl(res.getName(), res.getDescription(), this.uri);
	}
	
	/* (non-Javadoc)
	 * @see javax.rules.RuleSession#getType()
	 */
	public int getType() throws RemoteException, InvalidRuleSessionException {
		return RuleRuntime.STATELESS_SESSION_TYPE;
	}
	
	/* (non-Javadoc)
	 * @see javax.rules.RuleSession#release()
	 */
	public void release() throws RemoteException, InvalidRuleSessionException {
		// Nothing to do here
	}
	
	
	
	
}
