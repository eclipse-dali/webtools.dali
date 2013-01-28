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

import java.util.ListIterator;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * A <code>TransformationListIterator</code> wraps another {@link ListIterator}
 * and transforms its results for client consumption.
 * <p>
 * The methods {@link #set(Object)} and {@link #add(Object)}
 * are left unsupported in this class.
 * 
 * @param <E1> input: the type of elements to be transformed
 * @param <E2> output: the type of elements returned by the iterator
 * 
 * @see org.eclipse.jpt.common.utility.internal.iterable.IterableTools#transform(org.eclipse.jpt.common.utility.iterable.ListIterable, Transformer)
 */
public class TransformationListIterator<E1, E2>
	implements ListIterator<E2>
{
	private final ListIterator<? extends E1> listIterator;
	private final Transformer<? super E1, ? extends E2> transformer;


	/**
	 * Construct an iterator with the specified nested iterator
	 * and transformer.
	 */
	public TransformationListIterator(ListIterator<? extends E1> listIterator, Transformer<? super E1, ? extends E2> transformer) {
		super();
		if ((listIterator == null) || (transformer == null)) {
			throw new NullPointerException();
		}
		this.listIterator = listIterator;
		this.transformer = transformer;
	}

	public boolean hasNext() {
		// delegate to the nested iterator
		return this.listIterator.hasNext();
	}

	public E2 next() {
		// transform the object returned by the nested iterator before returning it
		return this.transformer.transform(this.listIterator.next());
	}

	public int nextIndex() {
		// delegate to the nested iterator
		return this.listIterator.nextIndex();
	}

	public boolean hasPrevious() {
		// delegate to the nested iterator
		return this.listIterator.hasPrevious();
	}

	public E2 previous() {
		// transform the object returned by the nested iterator before returning it
		return this.transformer.transform(this.listIterator.previous());
	}

	public int previousIndex() {
		// delegate to the nested iterator
		return this.listIterator.previousIndex();
	}

	public void add(E2 o) {
		throw new UnsupportedOperationException();
	}

	public void set(E2 o) {
		throw new UnsupportedOperationException();
	}

	public void remove() {
		// delegate to the nested iterator
		this.listIterator.remove();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.listIterator);
	}
}
