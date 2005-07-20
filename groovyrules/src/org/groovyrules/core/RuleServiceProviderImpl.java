package org.groovyrules.core;

import javax.rules.ConfigurationException;
import javax.rules.RuleRuntime;
import javax.rules.RuleServiceProvider;
import javax.rules.RuleServiceProviderManager;
import javax.rules.admin.RuleAdministrator;

/**
 * Implementation of <tt>RuleServiceProvider</tt>. This is used
 * to retrieve the <tt>RuleAdministrator</tt> and <tt>RuleRuntime</tt>.
 * <p>
 * The service provider can be retrieved from the
 * <tt>javax.rules.RuleServiceProviderManager</tt> using the following code:
 * <pre>
 *		// Loading this class will automatically register this
 *		// provider with the provider manager.
 *		Class.forName("org.groovyrules.core.RuleServiceProviderImpl");
 *		
 *		// Get the rule service provider from the provider manager.
 *		RuleServiceProvider serviceProvider = 
 *			RuleServiceProviderManager.getRuleServiceProvider(
 *					"org.groovyrules.core.RuleServiceProviderImpl");
 *</pre> 
 * 
 * @author Rob Newsome
 */
public class RuleServiceProviderImpl extends RuleServiceProvider {

	public static final String RULE_SERVICE_PROVIDER_URI = "org.groovyrules.core.RuleServiceProviderImpl";
	
	static {
		try {
			RuleServiceProviderManager.registerRuleServiceProvider(RULE_SERVICE_PROVIDER_URI, RuleServiceProviderImpl.class);
		}
		catch(ConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see javax.rules.RuleServiceProvider#getRuleAdministrator()
	 */
	public RuleAdministrator getRuleAdministrator()
			throws ConfigurationException {
		return new RuleAdministratorImpl();
	}
	/* (non-Javadoc)
	 * @see javax.rules.RuleServiceProvider#getRuleRuntime()
	 */
	public RuleRuntime getRuleRuntime() throws ConfigurationException {
		return new RuleRuntimeImpl(); 
	}
	
}
