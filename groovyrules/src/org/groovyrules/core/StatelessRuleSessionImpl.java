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
	private RuleExecutionSetAbstract res;
	
	protected StatelessRuleSessionImpl(String uri, RuleExecutionSetAbstract res) {
		this.uri = uri;
		this.res = res;
	}
	
	
	/* (non-Javadoc)
	 * @see javax.rules.StatelessRuleSession#executeRules(java.util.List, javax.rules.ObjectFilter)
	 */
	public List executeRules(List inputs, ObjectFilter filter)
			throws InvalidRuleSessionException, RemoteException {
		
		RuleData data = new RuleData(inputs);
		res.runRules(data);
		
		if(filter!=null) {
			// Run the specified filter
			data.applyObjectFilter(filter);
		}
		else {
			ObjectFilter defaultFilter = res.getDefaultObjectFilterInstance();
			if(defaultFilter!=null) {
				// Run the default filter
				data.applyObjectFilter(defaultFilter);
			}
			else {
				// No filter needs running
			}
		}

		return data.getObjects();
		
	}
	
	
	/* (non-Javadoc)
	 * @see javax.rules.StatelessRuleSession#executeRules(java.util.List)
	 */
	public List executeRules(List inputs) throws InvalidRuleSessionException,
			RemoteException {

		return executeRules(inputs, null);
		
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
