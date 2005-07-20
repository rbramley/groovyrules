package org.groovyrules.test;

import java.io.InputStream;
import java.util.List;

import org.groovyrules.core.RuleExecutionSetConfiguration;


import junit.framework.TestCase;

/**
 * Test for the RuleExecutionSetConfiguration - ensures we can 
 * load an XML configration file correctly.
 * 
 * @author Rob Newsome
 */
public class RuleExecutionSetConfigurationTest extends TestCase {

	RuleExecutionSetConfiguration config;
	
	public void setUp() throws Exception {
		
		InputStream inStream = getClass().getResourceAsStream(
		"/org/groovyrules/test/basic_rule_test.xml");
		config = new RuleExecutionSetConfiguration(inStream);
		
	}
	
	public void testGetName() {
		assertEquals("Invoice Handling Rule Set", config.getName());
	}

	public void testGetDescription() {
		assertEquals("Handles invoices for customers", config.getDescription());
	}
	
	public void testGetRules() {
		List rules = config.getRuleFiles();
		assertEquals(2, rules.size());
		assertEquals("rule1.groovyrule", rules.get(0));
		assertEquals("rule2.groovyrule", rules.get(1));
	}

}
