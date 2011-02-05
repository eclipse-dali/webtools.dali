/*******************************************************************************
 * Copyright (c) 2005, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterators;

import java.util.Iterator;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.Transformer;


/**
 * A <code>TransformationIterator</code> wraps another {@link Iterator}
 * and transforms its results for client consumption. To use, supply a 
 * {@link Transformer} or subclass <code>TransformationIterator</code>
 * and override the {@link #transform(Object)} method.
 * Objects of type <code>E1</code> are transformed into objects of type <code>E2</code>;
 * i.e. the iterator returns objects of type <code>E2</code>.
 * 
 * @param <E1> input: the type of elements to be transformed
 * @param <E2> output: the type of elements returned by the iterator
 * 
 * @see org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable
 */
public class TransformationIterator<E1, E2>
	implements Iterator<E2>
{
	private final Iterator<? extends E1> iterator;
	private final Transformer<E1, ? extends E2> transformer;


	/**
	 * Construct an iterator with the specified iterable
	 * and a disabled transformer.
	 * Use this constructor if you want to override the
	 * {@link #transform(Object)} method instead of building
	 * a {@link Transformer}.
	 */
	public TransformationIterator(Iterable<? extends E1> iterable) {
		this(iterable.iterator());
	}

	/**
	 * Construct an iterator with the specified nested iterator
	 * and a disabled transformer.
	 * Use this constructor if you want to override the
	 * {@link #transform(Object)} method instead of building
	 * a {@link Transformer}.
	 */
	public TransformationIterator(Iterator<? extends E1> iterator) {
		this(iterator, Transformer.Disabled.<E1, E2>instance());
	}

	/**
	 * Construct an iterator with the specified iterable and transformer.
	 */
	public TransformationIterator(Iterable<? extends E1> iterable, Transformer<E1, ? extends E2> transformer) {
		this(iterable.iterator(), transformer);
	}

	/**
	 * Construct an iterator with the specified nested iterator
	 * and transformer.
	 */
	public TransformationIterator(Iterator<? extends E1> iterator, Transformer<E1, ? extends E2> transformer) {
		super();
		this.iterator = iterator;
		this.transformer = transformer;
	}

	public boolean hasNext() {
		// delegate to the nested iterator
		return this.iterator.hasNext();
	}

	public E2 next() {
		// transform the object returned by the nested iterator before returning it
		return this.transform(this.iterator.next());
	}

	public void remove() {
		// delegate to the nested iterator
		this.iterator.remove();
	}

	/**
	 * Transform the specified object and return the result.
	 */
	protected E2 transform(E1 next) {
		return this.transformer.transform(next);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.iterator);
	}

}
