package org.groovyrules.test;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import javax.rules.RuleServiceProvider;
import javax.rules.RuleServiceProviderManager;
import javax.rules.admin.RuleAdministrator;
import javax.rules.admin.RuleExecutionSet;
import javax.rules.admin.RuleExecutionSetProvider;

import org.groovyrules.filerules.XMLRuleExecutionSetConfiguration;

import junit.framework.TestCase;

/**
 * Additional test cases for reading files of rules.
 * 
 * @author Rob Newsome
 */
public class FileRuleTest extends TestCase {

	public static final String RULESET_CONFIG_PATH = "/org/groovyrules/test/basic_rule_test.xml";
	
	/**
	 * Gives user a decent warning if they haven't set a valid path in the
	 * basic_rule_test.xml
	 */
	public void testPathSpecifiedProperlyInXML() throws Exception {

		InputStream configStream = getClass().getResourceAsStream(RULESET_CONFIG_PATH);
		XMLRuleExecutionSetConfiguration config = new XMLRuleExecutionSetConfiguration(configStream);
		String ruleFile1Path = config.getRuleRoot() + config.getRuleFiles().get(0);
		assertTrue("The path defined in the file '" + RULESET_CONFIG_PATH + "' cannot be found.\nPlease check the ruleroot is correct for your system - it is currently set to: " + config.getRuleRoot() + "\nNo rule file was found at: " + ruleFile1Path, new File(ruleFile1Path).canRead());
		
	}
	
	/**
	 * This tests the createRuleExecutionSet by URI method
	 */
	public void testReadConfigurationFromURI() throws Exception {
		
		// First we contrive the URI (or a URL) from a resource off the classpath.
		URL fileURL = getClass().getResource(RULESET_CONFIG_PATH);
		
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
