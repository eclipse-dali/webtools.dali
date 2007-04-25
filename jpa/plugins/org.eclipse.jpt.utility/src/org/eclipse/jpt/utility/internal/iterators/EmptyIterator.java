/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * A <code>NullIterator</code> is just that.
 */
public final class EmptyIterator<E>
	implements Iterator<E>
{

	// singleton
	@SuppressWarnings("unchecked")
	private static EmptyIterator INSTANCE = new EmptyIterator();

	/**
	 * Return the singleton.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Iterator<T> instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EmptyIterator() {
		super();
	}

	public boolean hasNext() {
		return false;
	}

	public E next() {
		throw new NoSuchElementException();
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this);
	}

}
