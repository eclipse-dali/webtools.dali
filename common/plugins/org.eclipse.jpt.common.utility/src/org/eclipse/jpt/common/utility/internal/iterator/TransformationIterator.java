/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterator;

import java.util.Iterator;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * A <code>TransformationIterator</code> wraps another {@link Iterator}
 * and transforms its results for client consumption.
 * Objects of type <code>E1</code> are transformed into objects of type <code>E2</code>;
 * i.e. the iterator returns objects of type <code>E2</code>.
 * 
 * @param <E1> input: the type of elements to be transformed
 * @param <E2> output: the type of elements returned by the iterator
 * 
 * @see org.eclipse.jpt.common.utility.internal.iterable.IterableTools#transform(Iterable, Transformer)
 */
public class TransformationIterator<E1, E2>
	implements Iterator<E2>
{
	private final Iterator<? extends E1> iterator;
	private final Transformer<? super E1, ? extends E2> transformer;


	/**
	 * Construct an iterator with the specified nested iterator
	 * and transformer.
	 */
	public TransformationIterator(Iterator<? extends E1> iterator, Transformer<? super E1, ? extends E2> transformer) {
		super();
		if ((iterator == null) || (transformer == null)) {
			throw new NullPointerException();
		}
		this.iterator = iterator;
		this.transformer = transformer;
	}

	public boolean hasNext() {
		// delegate to the nested iterator
		return this.iterator.hasNext();
	}

	public E2 next() {
		// transform the object returned by the nested iterator before returning it
		return this.transformer.transform(this.iterator.next());
	}

	public void remove() {
		// delegate to the nested iterator
		this.iterator.remove();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.iterator);
	}
}
