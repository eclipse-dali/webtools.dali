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

import java.util.Arrays;
import java.util.Iterator;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;

/**
 * Maintain a thread-safe list of listeners that does not allow adding
 * duplicate listeners or removing non-listeners.
 * 
 * @param <L> the type of listeners held by the list
 */
public final class ListenerList<L>
	implements Iterable<L>
{
	/**
	 * We can mark this <code>volatile</code> and not synchronize the read
	 * methods because we never change the <em>contents</em> of the array.
	 */
	@SuppressWarnings("unchecked")
	private transient volatile L[] listeners = (L[]) ObjectTools.EMPTY_OBJECT_ARRAY;


	/**
	 * Construct a listener list initialized with the specified listener.
	 */
	@SuppressWarnings("unchecked")
	public ListenerList(L listener) {
		this();
		if (listener == null) {
			throw new NullPointerException();
		}
		this.listeners = (L[]) new Object[] { listener };
	}

	/**
	 * Construct an empty listener list.
	 */
	public ListenerList() {
		super();
	}

	/**
	 * Return the listeners. The returned list will be a "snapshot" of the list
	 * of listeners at the time this method is called. There is no possibility
	 * of a {@link java.util.ConcurrentModificationException} with the returned
	 * iterator.
	 */
	public Iterator<L> iterator() {
		return IteratorTools.iterator(this.listeners);
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
	public void add(L listener) {
		if (listener == null) {
			throw new NullPointerException();
		}
		this.add_(listener);
	}

	private synchronized void add_(L listener) {
		if (ArrayTools.contains(this.listeners, listener)) {
			throw new IllegalArgumentException("duplicate listener: " + listener); //$NON-NLS-1$
		}
		this.listeners = ArrayTools.add(this.listeners, listener);
	}

	/**
	 * Remove the specified listener from the listener list.
	 * Removing a listener that is not on the list is not allowed.
	 */
	public void remove(L listener) {
		this.remove_(listener);
	}

	private synchronized void remove_(L listener) {
		int index = ArrayTools.indexOf(this.listeners, listener);
		if (index == -1) {
			throw new IllegalArgumentException("unregistered listener: " + listener); //$NON-NLS-1$
		}
		this.listeners = ArrayTools.removeElementAtIndex(this.listeners, index);
	}

	/**
	 * Clear the listener list.
	 */
	@SuppressWarnings("unchecked")
	public synchronized void clear() {
		this.listeners = (L[]) ObjectTools.EMPTY_OBJECT_ARRAY;
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
