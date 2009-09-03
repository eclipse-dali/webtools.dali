/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.EventListener;

/**
 * Maintain a thread-safe list of listeners that does not allow adding
 * duplicate listeners or removing non-listeners.
 */
public class ListenerList<L extends EventListener>
	implements Serializable
{
	private transient volatile L[] listeners;

	private static final long serialVersionUID = 1L;


	/**
	 * Construct a listener list for listeners of the specified type.
	 */
	public ListenerList(Class<L> listenerClass) {
		super();
		this.listeners = this.buildListenerArray(listenerClass, 0);
	}

	/**
	 * Construct a listener list for listeners of the specified type.
	 * Add the specified listener to the list.
	 */
	public ListenerList(Class<L> listenerClass, L listener) {
		super();
		if (listener == null) {
			throw new NullPointerException();
		}
		this.listeners = this.buildListenerArray(listenerClass, 1);
		this.listeners[0] = listener;
	}

	@SuppressWarnings("unchecked")
	private L[] buildListenerArray(Class<L> listenerClass, int length) {
		return (L[]) Array.newInstance(listenerClass, length);
	}

	/**
	 * Return the listeners.
	 */
	public L[] getListeners() {
		return this.listeners;
	}

	/**
	 * Return the number of listeners.
	 */
	public int size() {
		return this.listeners.length;
	}

	/**
	 * Return whether the listener list has no listeners.
	 */
	public boolean isEmpty() {
		return this.listeners.length == 0;
	}

	/**
	 * Add the specified listener to the listener list.
	 * Duplicate listeners are not allowed.
	 */
	public synchronized void add(L listener) {
		if (listener == null) {
			throw new NullPointerException();
		}
		if (ArrayTools.contains(this.listeners, listener)) {
			throw new IllegalArgumentException("duplicate listener: " + listener); //$NON-NLS-1$
		}
		this.listeners = ArrayTools.add(this.listeners, listener);
	}

	/**
	 * Remove the specified listener from the listener list.
	 * Removing a listener that is not on the list is not allowed.
	 */
	public synchronized void remove(L listener) {
		if (listener == null) {
			throw new NullPointerException();
		}
		int index = ArrayTools.indexOf(this.listeners, listener);
		if (index == -1) {
			throw new IllegalArgumentException("unregistered listener: " + listener); //$NON-NLS-1$
		}
		this.listeners = ArrayTools.removeElementAtIndex(this.listeners, index);
	}

	/**
	 * Clear the listener list.
	 */
	public synchronized void clear() {
		this.listeners = ArrayTools.clear(this.listeners);
	}

	@Override
	public String toString() {
		return Arrays.toString(this.listeners);
	}


	// ********** serialization **********

	/**
	 * Silently drop any non-serializable listeners.
	 */
	private synchronized void writeObject(ObjectOutputStream s) throws IOException {
		// write out any hidden stuff
		s.defaultWriteObject();

		@SuppressWarnings("unchecked")
		Class<L> listenerClass = (Class<L>) this.listeners.getClass().getComponentType();
		s.writeObject(listenerClass);

		// only write out serializable listeners
		for (L listener : this.listeners) {
			if (listener instanceof Serializable) {
				s.writeObject(listener);
			}
		}

		s.writeObject(null);
    }

	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException {
		// read in any hidden stuff
		s.defaultReadObject();

		Class<L> listenerClass = (Class<L>) s.readObject();
		this.listeners = this.buildListenerArray(listenerClass, 0);
		Object o;
		while ((o = s.readObject()) != null) {
			this.listeners = ArrayTools.add(this.listeners, (L) o);
		}
	}

}
