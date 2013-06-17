/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

import java.lang.reflect.Array;
import java.util.Arrays;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;

/**
 * Maintain a thread-safe list of listeners that does not allow adding
 * duplicate listeners or removing non-listeners.
 * 
 * @param <L> the type of listeners held by the list
 */
public class ListenerList<L> {
	/**
	 * We can mark this <code>volatile</code> and not synchronize the read
	 * methods because we never change the <em>contents</em> of the array.
	 */
	private transient volatile L[] listeners;


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
	public Iterable<L> getListeners() {
		return IterableTools.iterable(this.listeners);
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

	/**
	 * Return the type of listeners held by the listener list.
	 */
	@SuppressWarnings("unchecked")
	public Class<L> getListenerType() {
		return (Class<L>) this.listeners.getClass().getComponentType();
	}

	@Override
	public String toString() {
		return Arrays.toString(this.listeners);
	}

	/**
	 * @see #toString()
	 */
	public void toString(StringBuilder sb) {
		StringBuilderTools.append(sb, this.listeners);
	}
}
