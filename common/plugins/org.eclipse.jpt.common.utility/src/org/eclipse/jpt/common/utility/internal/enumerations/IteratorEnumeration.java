/*******************************************************************************
 * Copyright (c) 2005, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.enumerations;

import java.util.Enumeration;
import java.util.Iterator;
import org.eclipse.jpt.common.utility.internal.StringTools;

/**
 * An <code>IteratorEnumeration</code> wraps an
 * {@link Iterator} so that it can be treated like an
 * {@link Enumeration}.
 * Hopefully we don't have much need for this....
 * 
 * @param <E> the type of elements returned by the enumeration
 */
public class IteratorEnumeration<E>
	implements Enumeration<E>
{
	private final Iterator<? extends E> iterator;

	/**
	 * Construct an enumeration that wraps the specified iterable.
	 */
	public IteratorEnumeration(Iterable<? extends E> iterable) {
		this(iterable.iterator());
	}

	/**
	 * Construct an enumeration that wraps the specified iterator.
	 */
	public IteratorEnumeration(Iterator<? extends E> iterator) {
		super();
		this.iterator = iterator;
	}

	public boolean hasMoreElements() {
		return this.iterator.hasNext();
	}

	public E nextElement() {
		return this.iterator.next();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.iterator);
	}
	
}
