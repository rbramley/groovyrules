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
		Class.forName("org.groovyrules.core.RuleServiceProviderImpl");
		
		// Get the rule service provider from the provider manager.
		RuleServiceProvider serviceProvider = 
			RuleServiceProviderManager.getRuleServiceProvider(
					"org.groovyrules.core.RuleServiceProviderImpl");

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
		
		List registrations = ruleRuntime.getRegistrations();
		assertTrue("No RuleSets registered", registrations.size()>=1);
		assertEquals(String.class, registrations.get(0).getClass());

		// Create a StatelessRuleSession 
		StatelessRuleSession statelessRuleSession = 
			(StatelessRuleSession) ruleRuntime.createRuleSession(uri,
			new HashMap(), RuleRuntime.STATELESS_SESSION_TYPE);

		assertNotNull(statelessRuleSession);

		// Create a Customer as specified by the TCK documentation.
		Customer inputCustomer = new Customer("Customer");
		inputCustomer.setCreditLimit(5000);

		// Create invoices as specified by the TCK documentation.
		Invoice inputInvoice1 = new Invoice("Invoice 1");
		inputInvoice1.setAmount(500);
		Invoice inputInvoice2 = new Invoice("Invoice 2");
		inputInvoice2.setAmount(200);
		Invoice inputInvoice3 = new Invoice("Invoice 3");
		inputInvoice3.setAmount(900);
		
		// Create a input list.
		List input = new ArrayList();
		input.add(inputCustomer);
		input.add(inputInvoice1);
		input.add(inputInvoice2);
		input.add(inputInvoice3);

		// Check the input
		assertEquals(5000, inputCustomer.getCreditLimit());
		assertEquals("unpaid", inputInvoice1.getStatus());
		assertEquals("unpaid", inputInvoice2.getStatus());
		assertEquals("unpaid", inputInvoice3.getStatus());
		assertEquals(500, inputInvoice1.getAmount());
		
		// Execute the rules without a filter.
		List results = statelessRuleSession.executeRules(input);

		// Get the results
		assertEquals(4, results.size());
		Iterator itr = results.iterator();
		Customer customer = (Customer)itr.next();
		Invoice invoice1 = (Invoice)itr.next();
		Invoice invoice2 = (Invoice)itr.next();
		Invoice invoice3 = (Invoice)itr.next();
		
		// Check the results
		assertEquals(4300, customer.getCreditLimit());
		assertEquals("paid", invoice1.getStatus());
		assertEquals(500, invoice1.getAmount());
		assertEquals("paid", invoice2.getStatus());
		assertEquals(200, invoice2.getAmount());
		assertEquals("excessive", invoice3.getStatus());

		// Release the session.
		statelessRuleSession.release();
		
	}
	
	
}
