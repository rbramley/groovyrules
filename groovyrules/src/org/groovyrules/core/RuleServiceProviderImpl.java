package org.groovyrules.core;

import javax.rules.ConfigurationException;
import javax.rules.RuleRuntime;
import javax.rules.RuleServiceProviderManager;
import javax.rules.admin.RuleAdministrator;

import org.groovyrules.directrules.DirectRuleServiceProviderImpl;
import org.groovyrules.filerules.FileRuleExecutionSetProviderImpl;

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
 *     // Here we're using the default file-based rule service.
 *		RuleServiceProvider serviceProvider = 
 *			RuleServiceProviderManager.getRuleServiceProvider(
 *					"org.groovyrules.core.RuleServiceProviderImpl");
 * </pre> 
 * Note that the above example shows retrieving the default file-based
 * rule service provider - the following URIs can be passed to the 
 * <tt>getRuleServiceProvider</tt> method:
 * <ul>
 * 		<li><tt>org.groovyrules.core.RuleServiceProviderImpl</tt> - The default
 * 		file-based provider - reads the rules from Groovy files defined by a ruleset
 *		descriptor XML.</li>
 *		<li><tt>org.groovyrules.core.DirectRuleServiceProviderImpl</tt> - The direct
 * 		rule provider - rules are defined as properties passed into the
 * 		<tt>createRuleExecutionSet</tt> method.</li>
 * </ul>
 * 
 * @author Rob Newsome
 */
public class RuleServiceProviderImpl extends javax.rules.RuleServiceProvider {

	public static final String FILE_RULE_SERVICE_PROVIDER_URI = "org.groovyrules.core.RuleServiceProviderImpl";
	public static final String DIRECT_RULE_SERVICE_PROVIDER_URI = "org.groovyrules.core.DirectRuleServiceProviderImpl";
	
	static {
		try {
			RuleServiceProviderManager.registerRuleServiceProvider(FILE_RULE_SERVICE_PROVIDER_URI, RuleServiceProviderImpl.class);
			RuleServiceProviderManager.registerRuleServiceProvider(DIRECT_RULE_SERVICE_PROVIDER_URI, DirectRuleServiceProviderImpl.class);
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
		return new RuleAdministratorImpl(FileRuleExecutionSetProviderImpl.class);
	}
	
	/* (non-Javadoc)
	 * @see javax.rules.RuleServiceProvider#getRuleRuntime()
	 */
	public RuleRuntime getRuleRuntime() throws ConfigurationException {
		return new RuleRuntimeImpl(); 
	}
	
}
