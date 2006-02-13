package org.groovyrules.test;

import javax.rules.Handle;
import javax.rules.InvalidHandleException;

import junit.framework.TestCase;

import org.groovyrules.core.RuleData;

/**
 *
 * @author Rob Newsome 
 */
public class SessionDataTest extends TestCase {

	public void testBasics() throws Exception {
		
		RuleData session = new RuleData();
		
		Handle handle1 = new RuleData.HandleImpl();
		Handle handle2 = new RuleData.HandleImpl();
		String obj1 = "obj1";
		String obj2 = "obj2";
		
		assertNotSame(obj1, obj2);
		
		session.add(handle1, obj1);
		session.add(handle2, obj2);
		
		assertTrue(session.containsHandle(handle2));
		
		String get2 = (String)session.getByHandle(handle2);
		String get1 = (String)session.getByHandle(handle1);
		
		assertEquals(obj1, get1);
		assertEquals(obj2, get2);
		
		session.removeByHandle(handle1);
		
		// Should fail
		try {
			session.getByHandle(handle1);
			fail("Handle should now be invalid");
		}
		catch(InvalidHandleException e) {
			// Expected
		}
		
		// Should work
		session.getByHandle(handle2);
		
	}
	
	
	public void testDataAsList() {
		
		RuleData session = new RuleData();
		
		String obj1 = "obj1";
		String obj2 = "obj2";
		
		session.add(obj1);
		
		assertTrue(session.contains(obj1));
		
	}
	
	
}
