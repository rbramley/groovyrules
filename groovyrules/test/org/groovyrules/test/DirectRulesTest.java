package org.groovyrules.test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.rules.ConfigurationException;
import javax.rules.InvalidRuleSessionException;
import javax.rules.RuleExecutionSetNotFoundException;
import javax.rules.RuleRuntime;
import javax.rules.RuleServiceProvider;
import javax.rules.RuleServiceProviderManager;
import javax.rules.RuleSessionCreateException;
import javax.rules.RuleSessionTypeUnsupportedException;
import javax.rules.StatelessRuleSession;
import javax.rules.admin.LocalRuleExecutionSetProvider;
import javax.rules.admin.RuleAdministrator;
import javax.rules.admin.RuleExecutionSet;
import javax.rules.admin.RuleExecutionSetCreateException;
import javax.rules.admin.RuleExecutionSetRegisterException;

import junit.framework.TestCase;

/**
 * Simple test case to ensure that the direct rules element works
 * 
 * @author Steve Jones
 * @since 15-Jan-2006
 */
public class DirectRulesTest extends TestCase {

    private RuleServiceProvider serviceProvider;

    private RuleAdministrator ruleAdministrator;

    private LocalRuleExecutionSetProvider provider;

    public void setUp() throws Exception {
        super.setUp();
        // Load the rule service provider of the implementation.
        // Loading this class will automatically register this
        // provider with the provider manager.
        Class.forName("org.groovyrules.core.RuleServiceProviderImpl");

        // Get the rule service provider from the provider manager.
        serviceProvider = RuleServiceProviderManager
                .getRuleServiceProvider("org.groovyrules.core.DirectRuleServiceProviderImpl");

        // Get the RuleAdministrator
        ruleAdministrator = serviceProvider.getRuleAdministrator();

        assertNotNull(ruleAdministrator);

        // Parse the ruleset
        provider = ruleAdministrator.getLocalRuleExecutionSetProvider(null);

    }

    /**
     * Test a simple rule case where we just pass in simple information
     */
    public void testSimpleDirectRules() {
        try {
            // This is the bit where we create some rules
            String rule1 = "simpleInt = data.get(0);\nresultValue = simpleInt + 1;\ndata.add(resultValue);";
            String rule2 = "\nsimpleInt = data.get(0);\nresultValue = simpleInt + 2;\ndata.add(resultValue);";
            HashMap map = new HashMap();
            map.put("rule1", rule1);
            map.put("rule2", rule2);
            RuleExecutionSet res1 = provider.createRuleExecutionSet((Object) null, map);
            ruleAdministrator.registerRuleExecutionSet("Testing", res1, null);

            RuleRuntime ruleRuntime = serviceProvider.getRuleRuntime();
            assertNotNull(ruleRuntime);

            List registrations = ruleRuntime.getRegistrations();
            assertEquals(1, registrations.size());
            assertEquals(String.class, registrations.get(0).getClass());

            // Create a StatelessRuleSession
            StatelessRuleSession statelessRuleSession = (StatelessRuleSession) ruleRuntime.createRuleSession("Testing",
                    new HashMap(), RuleRuntime.STATELESS_SESSION_TYPE);

            assertNotNull(statelessRuleSession);

            Integer simple = new Integer(1);
            List input = new ArrayList();
            input.add(simple);

            // Execute the rules without a filter.
            List results = statelessRuleSession.executeRules(input);

            // Its the second element each time
            Integer resultsValue1 = (Integer) results.get(1);
            Iterator x = results.iterator();

            assertEquals(new Integer(2), resultsValue1);
            Integer resultsValue2 = (Integer) results.get(2);
            assertEquals(new Integer(3), resultsValue2);

            // Release the session.
            statelessRuleSession.release();

        } catch (RuleExecutionSetCreateException exception) {
            // TODO Auto-generated catch block
            exception.printStackTrace();
            throw new RuntimeException("", exception);
        } catch (ConfigurationException exception) {
            // TODO Auto-generated catch block
            exception.printStackTrace();
            throw new RuntimeException("", exception);
        } catch (RemoteException exception) {
            // TODO Auto-generated catch block
            exception.printStackTrace();
            throw new RuntimeException("", exception);
        } catch (RuleSessionTypeUnsupportedException exception) {
            // TODO Auto-generated catch block
            exception.printStackTrace();
            throw new RuntimeException("", exception);
        } catch (RuleSessionCreateException exception) {
            // TODO Auto-generated catch block
            exception.printStackTrace();
            throw new RuntimeException("", exception);
        } catch (RuleExecutionSetNotFoundException exception) {
            // TODO Auto-generated catch block
            exception.printStackTrace();
            throw new RuntimeException("", exception);
        } catch (RuleExecutionSetRegisterException exception) {
            // TODO Auto-generated catch block
            exception.printStackTrace();
            throw new RuntimeException("", exception);
        } catch (InvalidRuleSessionException exception) {
            // TODO Auto-generated catch block
            exception.printStackTrace();
            throw new RuntimeException("", exception);
        }
    }

    // Complex rule definitions
    // Being passed in are four values, a string, two integers and 
    // a simple class
    /**
     * Private class used for testing
     */
    private final class TestClass{
        private String name;
        private int firstValue;
        private int secondValue;
        private Date date;
        
        /**
         * Parameterised Constructor
         * @param date
         * @param value
         * @param name
         * @param value2
         */
        public TestClass(Date date, int value, String name, int value2) {
            this.date = date;
            this.firstValue = value;
            this.name = name;
            this.secondValue = value2;
        }
        /**
         * @return Returns the date.
         */
        public final Date getDate() {
            return this.date;
        }
        /**
         * @param date The date to set.
         */
        public final void setDate(Date date) {
            this.date = date;
        }
        /**
         * @return Returns the firstValue.
         */
        public final int getFirstValue() {
            return this.firstValue;
        }
        /**
         * @param firstValue The firstValue to set.
         */
        public final void setFirstValue(int firstValue) {
            this.firstValue = firstValue;
        }
        /**
         * @return Returns the name.
         */
        public final String getName() {
            return this.name;
        }
        /**
         * @param name The name to set.
         */
        public final void setName(String name) {
            this.name = name;
        }
        /**
         * @return Returns the secondValue.
         */
        public final int getSecondValue() {
            return this.secondValue;
        }
        /**
         * @param secondValue The secondValue to set.
         */
        public final void setSecondValue(int secondValue) {
            this.secondValue = secondValue;
        }
        

    }
    // First rule aims to check that the first input (fred) has a value of 1
    private final static String COMPLEX_RULE_1 = "if (fred == 1){data.add(\"SUCCESS\");}else{data.add(\"FAIL\");}";
    // The next rule aims to check that the second value (barney) is the same as the SUM of the values in the 
    // fourth element (betty).
    private final static String COMPLEX_RULE_2 = "sumTotal = betty.getFirstValue() + betty.getSecondValue(); " +
                                                 "if (sumTotal == barney){data.add(\"SUCCESS\");}else{data.add(\"FAIL\");}";
    // The next rule aims to confirm that the third element (wilma) has only
    // eight characters in the format 4 capital then 4 digits
    private final static String COMPLEX_RULE_3 = "import java.util.regex.Matcher;\n" +
                                                 "import java.util.regex.Pattern;\n" +
                                                 "def pattern = ~/[A-Z][A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9]/;" +
                                                 "if (pattern.matcher(wilma).matches() && wilma.length() == 8){\n" +
                                                 "data.add(\"SUCCESS\");\n "+
                                                 "}else{\n" +
                                                 "data.add(\"FAIL\");\n " +
                                                 "}";
    // Next rule checks that the date is before now.
    private final static String COMPLEX_RULE_4 = "if (betty.getDate().before(new Date())){\n" +
                                                 "data.add(\"SUCCESS\");\n "+
                                                 "}else{\n" +
                                                 "data.add(\"FAIL\");\n " +
                                                 "}";
    // The next rule aims to confirm that the third element (wilma) has only
    // eight characters in the format 4 capital then 4 digits
    private final static String COMPLEX_RULE_5 = "if (wilma ==~/[A-Z]{4}[0-9]{4}/){\n" +
                                                 "data.add(\"SUCCESS\");\n "+
                                                 "}else{\n" +
                                                 "data.add(\"FAIL\");\n " +
                                                 "}";
                                                 
    
    /**
     * More complex test that takes a number of inputs, performs a series of
     * validation rules, this uses the properties matches for the rules
     */
    public final void testComplexDirectRules() {
        HashMap map = new HashMap();
        map.put("rule1", COMPLEX_RULE_1);
        map.put("rule2", COMPLEX_RULE_2);
        map.put("rule3", COMPLEX_RULE_5);
        map.put("rule4", COMPLEX_RULE_4);
        Map bindingMap = new HashMap();
        bindingMap.put(new Integer(0),"fred");
        bindingMap.put(new Integer(1),"barney");
        bindingMap.put(new Integer(2),"wilma");
        bindingMap.put(new Integer(3),"betty");
        try {
            RuleExecutionSet res1 = provider.createRuleExecutionSet((Object) bindingMap, map);
            ruleAdministrator.registerRuleExecutionSet("Testing", res1, null);

            RuleRuntime ruleRuntime = serviceProvider.getRuleRuntime();
            assertNotNull(ruleRuntime);

            List registrations = ruleRuntime.getRegistrations();
            assertEquals(1, registrations.size());
            assertEquals(String.class, registrations.get(0).getClass());

            // Create a StatelessRuleSession
            StatelessRuleSession statelessRuleSession = (StatelessRuleSession) ruleRuntime.createRuleSession("Testing",
                    new HashMap(), RuleRuntime.STATELESS_SESSION_TYPE);

            assertNotNull(statelessRuleSession);

            Integer fred = new Integer(1);
            Integer barney = new Integer(5);
            String wilma = "ABCD1234";
            TestClass betty = new TestClass(new Date(), 1, "1234ABCD", 4);
            List input = new ArrayList();
            input.add(fred);
            input.add(barney);
            input.add(wilma);
            input.add(betty);

            // Execute the rules without a filter.
            List results = statelessRuleSession.executeRules(input);
            
            assertEquals("SUCCESS", results.get(4));
            assertEquals("SUCCESS", results.get(5));
            assertEquals("SUCCESS", results.get(6));
            assertEquals("SUCCESS", results.get(7));
            
        } catch (RuleExecutionSetCreateException exception) {
            throw new RuntimeException(exception);
        } catch (RuleExecutionSetRegisterException exception) {
            throw new RuntimeException(exception);
        } catch (RuleSessionTypeUnsupportedException exception) {
            throw new RuntimeException(exception);
        } catch (RuleSessionCreateException exception) {
            throw new RuntimeException(exception);
        } catch (RuleExecutionSetNotFoundException exception) {
            throw new RuntimeException(exception);
        } catch (InvalidRuleSessionException exception) {
            throw new RuntimeException(exception);
        } catch (RemoteException exception) {
            throw new RuntimeException(exception);
        } catch (ConfigurationException exception) {
            throw new RuntimeException(exception);
        }

    }
}
