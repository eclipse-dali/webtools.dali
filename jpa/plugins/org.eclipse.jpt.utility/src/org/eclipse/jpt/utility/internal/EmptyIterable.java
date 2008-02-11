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

import java.util.Iterator;

import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;

/**
 * An <code>EmptyIterable</code> is just that.
 */
public final class EmptyIterable<E>
	implements Iterable<E>
{

	// singleton
	@SuppressWarnings("unchecked")
	private static final EmptyIterable INSTANCE = new EmptyIterable();

	/**
	 * Return the singleton.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Iterable<T> instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EmptyIterable() {
		super();
	}

	public Iterator<E> iterator() {
		return EmptyIterator.instance();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this);
	}

}
