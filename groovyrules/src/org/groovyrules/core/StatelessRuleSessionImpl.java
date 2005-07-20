package org.groovyrules.core;

import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;

import javax.rules.InvalidRuleSessionException;
import javax.rules.ObjectFilter;
import javax.rules.RuleExecutionSetMetadata;
import javax.rules.StatelessRuleSession;

/**
 * Implementation of a <tt>StatelessRuleSession</tt>. This is created
 * with a <tt>RuleExecutionSet</tt>, and will execute the rules held within
 * that execution set against a <tt>List</tt> of data.
 * 
 * @author Rob Newsome
 */
public class StatelessRuleSessionImpl implements StatelessRuleSession {

	private RuleExecutionSetImpl res;
	
	protected StatelessRuleSessionImpl(RuleExecutionSetImpl res) {
		this.res = res;
	}
	
	
	/* (non-Javadoc)
	 * @see javax.rules.StatelessRuleSession#executeRules(java.util.List, javax.rules.ObjectFilter)
	 */
	public List executeRules(List arg0, ObjectFilter arg1)
			throws InvalidRuleSessionException, RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/* (non-Javadoc)
	 * @see javax.rules.StatelessRuleSession#executeRules(java.util.List)
	 */
	public List executeRules(List inputs) throws InvalidRuleSessionException,
			RemoteException {

		Iterator rules = res.getRules().iterator();
		
		while(rules.hasNext()) {
			RuleImpl rule = (RuleImpl)rules.next();			
			rule.execute(inputs);
		}
		
		return inputs;
		
	}
	
	
	
	/* (non-Javadoc)
	 * @see javax.rules.RuleSession#getRuleExecutionSetMetadata()
	 */
	public RuleExecutionSetMetadata getRuleExecutionSetMetadata()
			throws InvalidRuleSessionException, RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	/* (non-Javadoc)
	 * @see javax.rules.RuleSession#getType()
	 */
	public int getType() throws RemoteException, InvalidRuleSessionException {
		// TODO Auto-generated method stub
		return 0;
	}
	/* (non-Javadoc)
	 * @see javax.rules.RuleSession#release()
	 */
	public void release() throws RemoteException, InvalidRuleSessionException {
		// TODO Auto-generated method stub

	}
	
	
	
	
}
