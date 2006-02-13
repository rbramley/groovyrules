/*
 * Created on 10-Feb-2006
 * 
 * TODO To change the template for this generated file go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
package org.groovyrules.core;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.rules.Handle;
import javax.rules.InvalidHandleException;
import javax.rules.ObjectFilter;

public class RuleData extends AbstractList {

	// TODO InvalidHandleException? Should use that here instead

	// TODO Reset on ObjectFilter?
	
	// This class is essentially a wrapper around two lists which
	// are kept in sync.
	
	private ArrayList objects;

	private ArrayList handles;

	public RuleData() {
		this.objects = new ArrayList();
		this.handles = new ArrayList();
	}

	public RuleData(List objects) {
		this.objects = new ArrayList(objects);
		// Sort out handles for stateless invocations - not needed
		this.handles = new ArrayList();
		for (int i = 0; i < objects.size(); i++) {
			this.handles.add(new HandleImpl());
		}
	}

	public synchronized void add(int index, Handle handle, Object obj) {
		objects.add(index, obj);
		handles.add(index, handle);
	}

	public synchronized void add(Handle handle, Object obj) {
		objects.add(obj);
		handles.add(handle);
	}

	public synchronized void removeByHandle(Handle handle) {
		int index = handles.indexOf(handle);
		if (index > -1) {
			handles.remove(index);
			objects.remove(index);
		}
	}

	public synchronized boolean containsHandle(Handle handle) {
		return handles.contains(handle);
	}

	public synchronized Object getByHandle(Handle handle)
			throws InvalidHandleException {
		int index = handles.indexOf(handle);
		if (index > -1) {
			return objects.get(index);
		} else {
			throw new InvalidHandleException("Handle '" + handle
					+ "' not found for retrieve");
		}
	}

	public synchronized List getHandles() {
		return handles;
	}

	public synchronized List getObjects() {
		return objects;
	}

	public synchronized void clear() {
		handles.clear();
		objects.clear();
	}

	public synchronized void updateByHandle(Handle handle, Object obj)
			throws InvalidHandleException {
		int index = handles.indexOf(handle);
		if (index > -1) {
			objects.remove(index);
			objects.add(index, obj);
		} else {
			throw new InvalidHandleException("Handle '" + handle
					+ "' not found for update");
		}
	}

	// METHODS FROM AbstractList BASE CLASS

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#get(int)
	 */
	public synchronized Object get(int index) {
		return objects.get(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#size()
	 */
	public synchronized int size() {
		return objects.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#add(int, java.lang.Object)
	 */
	public synchronized void add(int index, Object obj) {
		add(index, new HandleImpl(), obj);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#remove(int)
	 */
	public synchronized Object remove(int index) {
		Object obj = objects.remove(index);
		handles.remove(index);
		return obj;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#set(int, java.lang.Object)
	 */
	public synchronized Object set(int index, Object element) {
		Object prev = objects.set(index, element);
		handles.set(index, new HandleImpl());
		return prev;
	}

	
	public synchronized List getObjectsWithFilter(ObjectFilter filter) {
		
		if(filter==null) {
			return getObjects();			
		}
		else {
			
			List outputList = new ArrayList();
			
			Iterator it = iterator();
			while(it.hasNext()) {
				Object nextObj = it.next();
				Object filteredObj = filter.filter(nextObj);
				if(filteredObj!=null) {
					outputList.add(filteredObj);
				}
			}
			
			return outputList;
			
		}
		
	}
	
	
	public synchronized List getObjectsOfType(final Class cls) {
		
		return getObjectsWithFilter(new ObjectFilter() {
			public Object filter(Object object) {
				if(cls.isAssignableFrom(object.getClass())) {
					return object;
				}
				else {
					return null;
				}
			}

			public void reset() {
				// Nothing
			}
		});
		
	}
	
	
	public Object getFirstObjectOfType(final Class cls) {
		
		List allObjs = getObjectsOfType(cls);
		if(allObjs.size()>0) {
			return allObjs.get(0);
		}
		else {
			return null;
		}
		
	}
	
	
	public static class HandleImpl implements Handle {

	}

}