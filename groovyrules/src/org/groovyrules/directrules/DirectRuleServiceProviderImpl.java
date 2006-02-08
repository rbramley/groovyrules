package org.groovyrules.directrules;

import javax.rules.ConfigurationException;
import javax.rules.admin.RuleAdministrator;

import org.groovyrules.core.RuleAdministratorImpl;
import org.groovyrules.core.RuleServiceProviderImpl;

/**
 * Implementation of <tt>RuleServiceProvider</tt>. This is used
 * to retrieve the <tt>RuleAdministrator</tt> and <tt>RuleRuntime</tt>, for the
 * <i>DIRECT</i> implementation of rules.
 * <p>
 * This simply overrides the <tt>getRuleAdministrator</tt> method to return
 * an administrator for the direct rule execution set provider.
 *
 * @see org.groovyrules.core.RuleServiceProviderImpl
 * @author Rob Newsome
 */
public class DirectRuleServiceProviderImpl extends RuleServiceProviderImpl {

	/* (non-Javadoc)
	 * @see javax.rules.RuleServiceProvider#getRuleAdministrator()
	 */
	public RuleAdministrator getRuleAdministrator()
			throws ConfigurationException {
		return new RuleAdministratorImpl(DirectRuleExecutionSetProviderImpl.class);
	}
	
}
