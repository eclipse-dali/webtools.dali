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

import java.util.Enumeration;
import java.util.Iterator;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * An <code>IteratorEnumeration</code> wraps an
 * <code>Iterator</code> so that it can be treated like an
 * <code>Enumeration</code>.
 */
public class IteratorEnumeration<E>
	implements Enumeration<E>
{
	private final Iterator<? extends E> iterator;

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
