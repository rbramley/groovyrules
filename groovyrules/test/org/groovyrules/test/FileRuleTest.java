package org.groovyrules.test;

import java.net.URL;

import javax.rules.RuleServiceProvider;
import javax.rules.RuleServiceProviderManager;
import javax.rules.admin.RuleAdministrator;
import javax.rules.admin.RuleExecutionSet;
import javax.rules.admin.RuleExecutionSetProvider;

import junit.framework.TestCase;

/**
 * Additional test cases for reading files of rules.
 * 
 * @author Rob Newsome
 */
public class FileRuleTest extends TestCase {

	/**
	 * This tests the createRuleExecutionSet by URI method
	 */
	public void testReadConfigurationFromURI() throws Exception {
		
		// First we contrive the URI (or a URL) from a resource off the classpath.
		URL fileURL = getClass().getResource(
		"/org/groovyrules/test/basic_rule_test.xml");
		
		// Load the rule service provider of the implementation.
		// Loading this class will automatically register this
		// provider with the provider manager.
		Class.forName("org.groovyrules.core.RuleServiceProviderImpl");
		
		// Get the rule service provider from the provider manager.
		RuleServiceProvider serviceProvider = 
			RuleServiceProviderManager.getRuleServiceProvider(
					"org.groovyrules.core.RuleServiceProviderImpl");

		// Get the RuleAdministrator
		RuleAdministrator ruleAdministrator = serviceProvider.getRuleAdministrator();
		   
		// Parse the ruleset from the config file
		RuleExecutionSetProvider provider = 
			ruleAdministrator.getRuleExecutionSetProvider(null);
		RuleExecutionSet res1 = 
			provider.createRuleExecutionSet(fileURL.toString(), null);

		// Will throw an exception if fails...
		assertNotNull(res1);
		
	}
	
}
