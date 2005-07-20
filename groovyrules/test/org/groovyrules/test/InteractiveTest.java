
package org.groovyrules.test;

import java.io.DataInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.rules.RuleRuntime;
import javax.rules.RuleServiceProvider;
import javax.rules.RuleServiceProviderManager;
import javax.rules.StatelessRuleSession;
import javax.rules.admin.RuleAdministrator;
import javax.rules.admin.RuleExecutionSet;

import org.groovyrules.core.RuleServiceProviderImpl;
import org.jcp.jsr94.tck.model.Customer;
import org.jcp.jsr94.tck.model.CustomerImpl;
import org.jcp.jsr94.tck.model.Invoice;
import org.jcp.jsr94.tck.model.InvoiceImpl;


/**
 * Simple interactive console test, using the same rule configuration
 * as the {@link BasicRuleTest}.
 * 
 * @author Rob Newsome
 */
public class InteractiveTest {

	public static void main(String[] args) throws Exception {
		
		Class.forName(RuleServiceProviderImpl.class.getName());

		RuleServiceProvider serviceProvider = 
			RuleServiceProviderManager.getRuleServiceProvider(
					RuleServiceProviderImpl.RULE_SERVICE_PROVIDER_URI);

		RuleAdministrator ruleAdministrator = serviceProvider.getRuleAdministrator();
		
		InputStream inStream = InteractiveTest.class.getResourceAsStream(
				"/org/groovyrules/test/basic_rule_test.xml");

		RuleExecutionSet res1 = 
			ruleAdministrator.getLocalRuleExecutionSetProvider(null).createRuleExecutionSet(inStream, null);

		String uri = res1.getName();
		ruleAdministrator.registerRuleExecutionSet(uri, res1, null );

		RuleRuntime ruleRuntime = serviceProvider.getRuleRuntime();

		// Create a Customer as specified by the TCK documentation.
		Customer customer = new CustomerImpl("Fred");
		customer.setCreditLimit(5000);
		
		int invoiceNum = 1;
		
		while(true) {
		
			// create a StatelessRuleSession 
			StatelessRuleSession statelessRuleSession = 
				(StatelessRuleSession) ruleRuntime.createRuleSession(uri,
				new HashMap(), RuleRuntime.STATELESS_SESSION_TYPE);
			
			System.out.println("Inputs - ");
			
			DataInputStream stream = new DataInputStream(System.in);
			System.out.print("- Invoice amount > ");
			String strAmount = stream.readLine();
			
			int invoiceAmount = Integer.parseInt(strAmount);
			
			// Create an Invoice as specified by the TCK documentation.
			Invoice inputInvoice = new InvoiceImpl("Invoice " + invoiceNum++);
			inputInvoice.setAmount(invoiceAmount);
			
			// Create a input list.
			List input = new ArrayList();
			input.add(customer);
			input.add(inputInvoice);
			
			System.out.println("- Customer: " 
					+ customer.getName() + " (Credit limit: $" 
					+customer.getCreditLimit() + ")");
			
			System.out.println("- Invoice: "
					+ inputInvoice.getDescription() + " ($"
					+ inputInvoice.getAmount() + " - "
					+ inputInvoice.getStatus() + ")");
	
			// Execute the rules without a filter.
			List results = statelessRuleSession.executeRules(input);
	
			// Get the results
			Iterator itr = results.iterator();
			customer = (Customer)itr.next();
			Invoice outputInvoice = (Invoice)itr.next();
	
			System.out.println("Outputs:");
			
			System.out.println("- Customer: " 
					+ customer.getName() + " (Credit limit: $" 
					+customer.getCreditLimit() + ")");
			
			System.out.println("- Invoice: "
					+ outputInvoice.getDescription() + " ($"
					+ outputInvoice.getAmount() + " - "
					+ outputInvoice.getStatus() + ")");
	
			// Release the session.
			statelessRuleSession.release();
				
		}
		
		
	}
}
