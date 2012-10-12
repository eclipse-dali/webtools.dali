/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterator;

import java.util.Enumeration;
import java.util.Iterator;

import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * An <code>EnumerationIterator</code> wraps an
 * {@link Enumeration} so that it can be treated like an
 * {@link Iterator}.
 * 
 * @param <E> the type of elements returned by the iterator
 */
public class EnumerationIterator<E>
	implements Iterator<E>
{
	private final Enumeration<? extends E> enumeration;

	/**
	 * Construct an iterator that wraps the specified enumeration.
	 */
	public EnumerationIterator(Enumeration<? extends E> enumeration) {
		super();
		if (enumeration == null) {
			throw new NullPointerException();
		}
		this.enumeration = enumeration;
	}

	public boolean hasNext() {
		return this.enumeration.hasMoreElements();
	}

	public E next() {
		return this.enumeration.nextElement();
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.enumeration);
	}
}
