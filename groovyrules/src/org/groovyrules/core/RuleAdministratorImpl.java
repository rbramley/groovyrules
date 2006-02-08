package org.groovyrules.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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

	protected Class ruleExecutionSetProviderClass;

	protected static Map registeredRuleExecutionSets = new HashMap();

	/**
	 * @param ruleExecutionSetClass
	 *                 The class of RuleExecutionSet to be created by the
	 *                 RuleExecutionSetProvider retrieved from this instance.
	 */
	public RuleAdministratorImpl(Class ruleExecutionSetProviderClass) {
		this.ruleExecutionSetProviderClass = ruleExecutionSetProviderClass;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.rules.admin.RuleAdministrator#deregisterRuleExecutionSet(java.lang.String,
	 *         java.util.Map)
	 */
	public void deregisterRuleExecutionSet(String uri, Map properties)
			throws RuleExecutionSetDeregistrationException, RemoteException {
		registeredRuleExecutionSets.remove(uri);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.rules.admin.RuleAdministrator#getLocalRuleExecutionSetProvider(java.util.Map)
	 */
	public LocalRuleExecutionSetProvider getLocalRuleExecutionSetProvider(
			Map properties) throws RemoteException {
		return (LocalRuleExecutionSetProvider)createRuleExecutionSetProvider(properties);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.rules.admin.RuleAdministrator#getRuleExecutionSetProvider(java.util.Map)
	 */
	public RuleExecutionSetProvider getRuleExecutionSetProvider(Map properties)
			throws RemoteException {		
		return (RuleExecutionSetProvider)createRuleExecutionSetProvider(properties);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.rules.admin.RuleAdministrator#registerRuleExecutionSet(java.lang.String,
	 *         javax.rules.admin.RuleExecutionSet, java.util.Map)
	 */
	public void registerRuleExecutionSet(String uri, RuleExecutionSet res,
			Map properties) throws RuleExecutionSetRegisterException, RemoteException {
		registeredRuleExecutionSets.put(uri, res);
	}

	private final Object createRuleExecutionSetProvider(Map properties) {

		try {

			Constructor constructor = ruleExecutionSetProviderClass
					.getConstructor(new Class[] { Map.class });

			Object provider = constructor
					.newInstance(new Object[] { properties });

			return provider;

		} catch (NoSuchMethodException e) {
			// If no valid constructor exists
			throw new RuntimeException(
					"Failed to create RuleExecutionSetProvider from class '"
							+ ruleExecutionSetProviderClass
							+ "' - no constructor taking a Map.", e);
		} catch (InstantiationException e) {
			// Couldn't instantiate
			throw new RuntimeException(
					"Failed to create RuleExecutionSetProvider from class '"
							+ ruleExecutionSetProviderClass
							+ "' - could not instantiate it.", e);
		} catch (IllegalAccessException e) {
			// Couldn't access
			throw new RuntimeException(
					"Failed to create RuleExecutionSetProvider from class '"
							+ ruleExecutionSetProviderClass
							+ "' - could not access it.", e);
		} catch (InvocationTargetException e) {
			// Other exception
			throw new RuntimeException(
					"Failed to create RuleExecutionSetProvider from class '"
							+ ruleExecutionSetProviderClass
							+ "' - Invocation Target Exception.", e);
		}

	}

}