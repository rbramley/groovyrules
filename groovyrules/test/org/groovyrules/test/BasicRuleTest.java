package org.groovyrules.test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.rules.RuleRuntime;
import javax.rules.RuleServiceProvider;
import javax.rules.RuleServiceProviderManager;
import javax.rules.StatelessRuleSession;
import javax.rules.admin.LocalRuleExecutionSetProvider;
import javax.rules.admin.RuleAdministrator;
import javax.rules.admin.RuleExecutionSet;

import junit.framework.TestCase;

import org.groovyrules.core.RuleServiceProviderImpl;


/**
 * JUnit basic test cases for GroovyRules demo.
 * 
 * This is heavily based on the example given in the 
 * JSR94 reference implementation.
 * 
 * @author Rob Newsome
 */
public class BasicRuleTest extends TestCase {

	public void testStatelessRuleSession() throws Exception {
		
		// Load the rule service provider of the implementation.
		// Loading this class will automatically register this
		// provider with the provider manager.
		Class.forName(RuleServiceProviderImpl.class.getName());
		
		// Get the rule service provider from the provider manager.
		RuleServiceProvider serviceProvider = 
			RuleServiceProviderManager.getRuleServiceProvider(
					RuleServiceProviderImpl.RULE_SERVICE_PROVIDER_URI);

		// Get the RuleAdministrator
		RuleAdministrator ruleAdministrator = serviceProvider.getRuleAdministrator();
		
		assertNotNull(ruleAdministrator);		   

		// Get an input stream to a test XML ruleset.
		// This rule execution set was modified from the TCK.
		InputStream inStream = getClass().getResourceAsStream(
				"/org/groovyrules/test/basic_rule_test.xml");

		// Parse the ruleset from the config file
		LocalRuleExecutionSetProvider provider = 
			ruleAdministrator.getLocalRuleExecutionSetProvider(null);
		RuleExecutionSet res1 = 
			provider.createRuleExecutionSet(inStream, null);
		inStream.close();

		// Register the RuleExecutionSet
		String uri = res1.getName();
		ruleAdministrator.registerRuleExecutionSet(uri, res1, null);

		RuleRuntime ruleRuntime = serviceProvider.getRuleRuntime();
		assertNotNull(ruleRuntime);

		// Create a StatelessRuleSession 
		StatelessRuleSession statelessRuleSession = 
			(StatelessRuleSession) ruleRuntime.createRuleSession(uri,
			new HashMap(), RuleRuntime.STATELESS_SESSION_TYPE);

		assertNotNull(statelessRuleSession);

		// Create a Customer as specified by the TCK documentation.
		Customer inputCustomer = new Customer("test");
		inputCustomer.setCreditLimit(5000);

		// Create an Invoice as specified by the TCK documentation.
		Invoice inputInvoice = new Invoice("Invoice 1");
		inputInvoice.setAmount(500);

		// Create a input list.
		List input = new ArrayList();
		input.add(inputCustomer);
		input.add(inputInvoice);

		// Check the input
		assertEquals(5000, inputCustomer.getCreditLimit());
		assertEquals("unpaid", inputInvoice.getStatus());
		assertEquals(500, inputInvoice.getAmount());
		
		// Execute the rules without a filter.
		List results = statelessRuleSession.executeRules(input);

		// Get the results
		assertEquals(2, results.size());
		Iterator itr = results.iterator();
		Customer customer = (Customer)itr.next();
		Invoice invoice = (Invoice)itr.next();
		
		// Check the results
		assertEquals(4500, customer.getCreditLimit());
		assertEquals("paid", invoice.getStatus());
		assertEquals(500, invoice.getAmount());

		// Release the session.
		statelessRuleSession.release();
		
	}
	
	
}
