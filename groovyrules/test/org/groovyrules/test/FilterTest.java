package org.groovyrules.test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.rules.ObjectFilter;
import javax.rules.RuleRuntime;
import javax.rules.RuleServiceProvider;
import javax.rules.RuleServiceProviderManager;
import javax.rules.StatelessRuleSession;
import javax.rules.admin.RuleAdministrator;
import javax.rules.admin.RuleExecutionSet;

import org.groovyrules.core.RuleServiceProviderImpl;

import junit.framework.TestCase;

/**
 * Test for filtering.
 * 
 * @author Rob Newsome
 */
public class FilterTest extends TestCase {

	public void testFilterAtExecute() throws Exception {
		
		// Get rule session
		Class.forName(RuleServiceProviderImpl.class.getName());

		RuleServiceProvider serviceProvider = 
			RuleServiceProviderManager.getRuleServiceProvider(
					RuleServiceProviderImpl.FILE_RULE_SERVICE_PROVIDER_URI);

		RuleAdministrator ruleAdministrator = serviceProvider.getRuleAdministrator();
		
		InputStream inStream = InteractiveTest.class.getResourceAsStream(
				"/org/groovyrules/test/basic_rule_test.xml");

		RuleExecutionSet res1 = 
			ruleAdministrator.getLocalRuleExecutionSetProvider(null).createRuleExecutionSet(inStream, null);
		
		String uri = res1.getName();
		ruleAdministrator.registerRuleExecutionSet(uri, res1, null );

		RuleRuntime ruleRuntime = serviceProvider.getRuleRuntime();

		// create a StatelessRuleSession 
		StatelessRuleSession statelessRuleSession = 
			(StatelessRuleSession) ruleRuntime.createRuleSession(uri,
			new HashMap(), RuleRuntime.STATELESS_SESSION_TYPE);
		
		// Try without filtering
		List outputWithoutFilter = statelessRuleSession.executeRules(buildSourceData());
		assertEquals(5, outputWithoutFilter.size());
		assertTrue(outputWithoutFilter.get(0) instanceof Customer);
		assertTrue(outputWithoutFilter.get(1) instanceof Invoice);
		assertTrue(outputWithoutFilter.get(2) instanceof Invoice);
		assertTrue(outputWithoutFilter.get(3) instanceof Date);
		assertTrue(outputWithoutFilter.get(4) instanceof String);

		// And with filtering...
		List outputWithFilter = statelessRuleSession.executeRules(buildSourceData(), new InvoiceObjectFilter());
		assertEquals(2, outputWithFilter.size());
		assertTrue(outputWithFilter.get(0) instanceof Invoice);
		assertTrue(outputWithFilter.get(1) instanceof Invoice);
		
	}
	
	
	public void testDefaultFilterOnRuleExecutionSet() throws Exception {
		
		// Get rule session
		Class.forName(RuleServiceProviderImpl.class.getName());

		RuleServiceProvider serviceProvider = 
			RuleServiceProviderManager.getRuleServiceProvider(
					RuleServiceProviderImpl.FILE_RULE_SERVICE_PROVIDER_URI);

		RuleAdministrator ruleAdministrator = serviceProvider.getRuleAdministrator();
		
		InputStream inStream = InteractiveTest.class.getResourceAsStream(
				"/org/groovyrules/test/basic_rule_test.xml");

		RuleExecutionSet res1 = 
			ruleAdministrator.getLocalRuleExecutionSetProvider(null).createRuleExecutionSet(inStream, null);
		res1.setDefaultObjectFilter("org.groovyrules.test.InvoiceObjectFilter");
		
		String uri = res1.getName();
		ruleAdministrator.registerRuleExecutionSet(uri, res1, null );

		RuleRuntime ruleRuntime = serviceProvider.getRuleRuntime();

		// create a StatelessRuleSession 
		StatelessRuleSession statelessRuleSession = 
			(StatelessRuleSession) ruleRuntime.createRuleSession(uri,
			new HashMap(), RuleRuntime.STATELESS_SESSION_TYPE);
		
		// By default this should use the default filter...
		List outputWithDefaultFilter = statelessRuleSession.executeRules(buildSourceData());
		assertEquals(2, outputWithDefaultFilter.size());
		assertTrue(outputWithDefaultFilter.get(0) instanceof Invoice);
		assertTrue(outputWithDefaultFilter.get(1) instanceof Invoice);
		
		// Now ensure we can override the filter, which completely replaces it
		// (i.e. filters AREN'T cumulative)...
		// New filter only returns Strings
		ObjectFilter newFilter = new ObjectFilter() {
			public Object filter(Object obj) {
				return (obj instanceof String)?obj:null;
			};
			public void reset() {
			};
		};
		List outputWithNewFilter = statelessRuleSession.executeRules(buildSourceData(), newFilter);
		assertEquals(1, outputWithNewFilter.size());
		assertTrue(outputWithNewFilter.get(0) instanceof String);
		
	}
	
	
	private List buildSourceData() {
	
		// Create a Customer as specified by the TCK documentation.
		Customer inputCustomer = new Customer("Fred");
		inputCustomer.setCreditLimit(5000);
	
		// Create invoices as specified by the TCK documentation.
		Invoice inputInvoice1 = new Invoice("Invoice 1");
		inputInvoice1.setAmount(100);
		Invoice inputInvoice2 = new Invoice("Invoice 2");
		inputInvoice2.setAmount(200);
		
		// Create a input list.
		List input = new ArrayList();
		input.add(inputCustomer);
		input.add(inputInvoice1);
		input.add(inputInvoice2);
		input.add(new Date());
		input.add("Hello world");
		
		return input;
		
	}
	
}
