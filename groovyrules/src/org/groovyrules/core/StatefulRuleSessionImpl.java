package org.groovyrules.core;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.rules.Handle;
import javax.rules.InvalidHandleException;
import javax.rules.InvalidRuleSessionException;
import javax.rules.ObjectFilter;
import javax.rules.RuleExecutionSetMetadata;

/**
 * 
 * 
 * @author Rob Newsome
 */
public class StatefulRuleSessionImpl implements javax.rules.StatefulRuleSession {

	// TODO Handling invalid sessions?
	
	/**
	 * Map of Handles to objects.
	 */
	private RuleData session = new RuleData();
	
	
	private String uri;
	private RuleExecutionSetAbstract res;
	
	protected StatefulRuleSessionImpl(String uri, RuleExecutionSetAbstract res) {
		this.uri = uri;
		this.res = res;
	}
		
	/* (non-Javadoc)
	 * @see javax.rules.StatefulRuleSession#addObject(java.lang.Object)
	 */
	public Handle addObject(Object obj) throws RemoteException,
			InvalidRuleSessionException {
		
		Handle handle = new RuleData.HandleImpl();
		session.add(handle, obj);
		return handle;
		
	}
	
	/* (non-Javadoc)
	 * @see javax.rules.StatefulRuleSession#addObjects(java.util.List)
	 */
	public List addObjects(List objs) throws RemoteException,
			InvalidRuleSessionException {
		
		List handles = new ArrayList();
		Iterator objsIt = objs.iterator();
		while(objsIt.hasNext()) {
			Object obj = objsIt.next();
			handles.add(addObject(obj));
		}
		return handles;
		
	}
	
	/* (non-Javadoc)
	 * @see javax.rules.StatefulRuleSession#containsObject(javax.rules.Handle)
	 */
	public boolean containsObject(Handle handle) throws RemoteException,
			InvalidRuleSessionException, InvalidHandleException {
		
		return session.containsHandle(handle);
		
	}

	/* (non-Javadoc)
	 * @see javax.rules.StatefulRuleSession#getHandles()
	 */
	public List getHandles() throws RemoteException,
			InvalidRuleSessionException {
		
		return session.getHandles();
		
	}
	
	/* (non-Javadoc)
	 * @see javax.rules.StatefulRuleSession#getObject(javax.rules.Handle)
	 */
	public Object getObject(Handle handle) throws RemoteException,
			InvalidHandleException, InvalidRuleSessionException {
		
		return session.getByHandle(handle);
		
	}
	
	/* (non-Javadoc)
	 * @see javax.rules.StatefulRuleSession#getObjects()
	 */
	public List getObjects() throws RemoteException,
			InvalidRuleSessionException {

		return session.getObjects();
		
	}
	
	/* (non-Javadoc)
	 * @see javax.rules.StatefulRuleSession#getObjects(javax.rules.ObjectFilter)
	 */
	public List getObjects(ObjectFilter filter) throws RemoteException,
			InvalidRuleSessionException {

		return session.getObjectsWithFilter(filter);
		
	}
	
	/* (non-Javadoc)
	 * @see javax.rules.StatefulRuleSession#removeObject(javax.rules.Handle)
	 */
	public void removeObject(Handle handle) throws RemoteException,
			InvalidHandleException, InvalidRuleSessionException {
		
		session.removeByHandle(handle);
		
	}

	/* (non-Javadoc)
	 * @see javax.rules.StatefulRuleSession#updateObject(javax.rules.Handle, java.lang.Object)
	 */
	public void updateObject(Handle handle, Object obj) throws RemoteException,
			InvalidRuleSessionException, InvalidHandleException {
		
		// TODO Check Invalid Handles
		session.updateByHandle(handle, obj);
		
	}
	
	
	
	/* (non-Javadoc)
	 * @see javax.rules.StatefulRuleSession#executeRules()
	 */
	public void executeRules() throws RemoteException,
			InvalidRuleSessionException {
		
		res.runRules(session, null);
		

	}
	
	/* (non-Javadoc)
	 * @see javax.rules.StatefulRuleSession#reset()
	 */
	public void reset() throws RemoteException, InvalidRuleSessionException {
	
		session.clear();
		
	}

	/* (non-Javadoc)
	 * @see javax.rules.RuleSession#getRuleExecutionSetMetadata()
	 */
	public RuleExecutionSetMetadata getRuleExecutionSetMetadata()
			throws InvalidRuleSessionException, RemoteException {
		return new RuleExecutionSetMetadataImpl(res.getName(), res.getDescription(), this.uri);
	}
	
	/* (non-Javadoc)
	 * @see javax.rules.RuleSession#getType()
	 */
	public int getType() throws RemoteException, InvalidRuleSessionException {
		// TODO Auto-generated method stub
		return 0;
	}
	/* (non-Javadoc)
	 * @see javax.rules.RuleSession#release()
	 */
	public void release() throws RemoteException, InvalidRuleSessionException {
		// TODO Auto-generated method stub

	}
	
	

	
	
}
