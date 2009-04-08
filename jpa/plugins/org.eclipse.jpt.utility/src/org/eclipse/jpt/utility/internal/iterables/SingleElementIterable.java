/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.iterables;

import java.util.Iterator;

import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.SingleElementIterator;

/**
 * A <code>SingleElementIterable</code> returns an <code>Iterator</code>
 * that holds a single element
 * and returns it with the first call to <code>next()</code>, at
 * which point it will return <code>false</code> to any subsequent
 * call to <code>hasNext()</code>.
 * <p>
 * A <code>SingleElementIterable</code> is equivalent to the
 * <code>Iterable</code> returned by:
 * 	<code>java.util.Collections.singleton(element)</code>
 * 
 * @see SingleElementIterator
 */
public class SingleElementIterable<E>
	implements Iterable<E>
{
	private final E element;

	/**
	 * Construct an iterable that contains only the specified element.
	 */
	public SingleElementIterable(E element) {
		super();
		this.element = element;
	}

	public Iterator<E> iterator() {
		return new SingleElementIterator<E>(this.element);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.element);
	}

}
