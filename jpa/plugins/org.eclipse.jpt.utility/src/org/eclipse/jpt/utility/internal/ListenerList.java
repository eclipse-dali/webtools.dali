/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
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
 * Maintain a thread-safe list of listeners that does not allow duplicates.
 */
public class ListenerList<L extends EventListener>
	implements Serializable
{
	private transient volatile L[] listeners;

	private static final long serialVersionUID = 1L;


	public ListenerList(Class<L> listenerClass) {
		super();
		this.listeners = this.buildEmptyArray(listenerClass);
	}

	@SuppressWarnings("unchecked")
	private L[] buildEmptyArray(Class<L> listenerClass) {
		return (L[]) Array.newInstance(listenerClass, 0);
	}

	public L[] getListeners() {
		return this.listeners;
	}

	public int size() {
		return this.listeners.length;
	}

	public boolean isEmpty() {
		return this.listeners.length == 0;
	}

	public synchronized void add(L listener) {
		if (listener == null) {
			throw new NullPointerException();
		}
		if (CollectionTools.contains(this.listeners, listener)) {
			throw new IllegalArgumentException("duplicate listener: " + listener); //$NON-NLS-1$
		}
		this.listeners = CollectionTools.add(this.listeners, listener);
	}

	public synchronized void remove(L listener) {
		if (listener == null) {
			throw new NullPointerException();
		}
		int index = CollectionTools.indexOf(this.listeners, listener);
		if (index == -1) {
			throw new IllegalArgumentException("unregistered listener: " + listener); //$NON-NLS-1$
		}
		this.listeners = CollectionTools.removeElementAtIndex(this.listeners, index);
	}

	public synchronized void clear() {
		this.listeners = CollectionTools.clear(this.listeners);
	}

	@Override
	public String toString() {
		return Arrays.toString(this.listeners);
	}


	// ********** serialization **********

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
	private synchronized void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException {
		// read in any hidden stuff
		s.defaultReadObject();

		Class<L> listenerClass = (Class<L>) s.readObject();
		this.listeners = this.buildEmptyArray(listenerClass);
		Object o;
		while ((o = s.readObject()) != null) {
			this.add((L) o);
		}
	}

}
