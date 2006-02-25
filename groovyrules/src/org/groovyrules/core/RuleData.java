package org.groovyrules.core;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.rules.Handle;
import javax.rules.InvalidHandleException;
import javax.rules.ObjectFilter;

public class RuleData extends AbstractList {

	// TODO InvalidHandleException? Should use that here instead

	// TODO Should this call reset() on ObjectFilter?

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

	/**
	 * Applies the specified ObjectFilter to this rule data, removing or
	 * replacing any objects that the filter modifies.
	 * <p>
	 * Note that if the ObjectFilter returns an object that is not equal to the
	 * original object (using an equals() check) then the object will be
	 * replaced, but the handle will be unchanged.
	 * <p>
	 * If the ObjectFilter returns null for an object, that object is removed,
	 * as is its handle.
	 */
	public synchronized void applyObjectFilter(ObjectFilter filter) {

		// TODO Semantics for Handles if ObjectFilter returns modified object?

		if (filter != null) {

			// Previous version did a loop using int and array size, this doesn't work as we are removing
			// elements from the list.
			ListIterator objectIterator = (ListIterator) objects.listIterator();
			Iterator handleIterator = handles.iterator();
			
			while(objectIterator.hasNext()) {
				
				Object preFilter = objectIterator.next();
				
				Object filteredObj = filter.filter(preFilter);
				handleIterator.next();
				
				if(filteredObj==null) {
					// Object removed by filter
					objectIterator.remove();
					handleIterator.remove();
				}
				else if(filteredObj.equals(preFilter)) {
					// Object returned unmodified
				}
				else {				
					// Object returned by filter was modified
					objectIterator.set(filteredObj);
				}
				
			}
			
		}

	}

	public synchronized List getObjectsWithFilter(ObjectFilter filter) {

		if (filter == null) {
			return getObjects();
		} else {

			List outputList = new ArrayList();

			Iterator it = iterator();
			while (it.hasNext()) {
				Object nextObj = it.next();
				Object filteredObj = filter.filter(nextObj);
				if (filteredObj != null) {
					outputList.add(filteredObj);
				}
			}

			return outputList;

		}

	}

	public synchronized List getObjectsOfType(final Class cls) {

		return getObjectsWithFilter(new ObjectFilter() {
			public Object filter(Object object) {
				if (cls.isAssignableFrom(object.getClass())) {
					return object;
				} else {
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
		if (allObjs.size() > 0) {
			return allObjs.get(0);
		} else {
			return null;
		}

	}

	public static class HandleImpl implements Handle {

	}

}