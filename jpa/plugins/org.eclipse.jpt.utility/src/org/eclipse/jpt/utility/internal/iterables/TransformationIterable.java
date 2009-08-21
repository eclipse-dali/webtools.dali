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
import org.eclipse.jpt.utility.internal.Transformer;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

/**
 * A <code>TransformationIterable</code> wraps another {@link Iterable}
 * and transforms its elements for client consumption. To use, supply a 
 * {@link Transformer} or subclass <code>TransformationIterable</code>
 * and override the {@link #transform(Object)} method.
 * Objects of type <code>E1</code> are transformed into objects of type <code>E2</code>;
 * i.e. the iterable's iterator returns objects of type <code>E2</code>.
 * 
 * @param <E1> input: the type of elements to be transformed
 * @param <E2> output: the type of elements returned by the iterable's iterator
 * 
 * @see TransformationIterator
 */
public class TransformationIterable<E1, E2>
	implements Iterable<E2>
{
	private final Iterable<? extends E1> iterable;
	private final Transformer<E1, ? extends E2> transformer;


	/**
	 * Construct an iterable with the specified nested iterable
	 * and a default transformer that calls back to the iterable.
	 * Use this constructor if you want to override the
	 * {@link #transform(Object)} method instead of building
	 * a {@link Transformer}.
	 */
	public TransformationIterable(Iterable<? extends E1> iterable) {
		super();
		this.iterable = iterable;
		this.transformer = this.buildDefaultTransformer();
	}

	/**
	 * Construct an iterable with the specified nested iterable
	 * and transformer.
	 */
	public TransformationIterable(Iterable<? extends E1> iterable, Transformer<E1, ? extends E2> transformer) {
		super();
		this.iterable = iterable;
		this.transformer = transformer;
	}

	protected Transformer<E1, ? extends E2> buildDefaultTransformer() {
		return new DefaultTransformer();
	}

	public Iterator<E2> iterator() {
		return new TransformationIterator<E1, E2>(this.iterable.iterator(), this.transformer);
	}

	/**
	 * Transform the specified object and return the result.
	 */
	protected E2 transform(@SuppressWarnings("unused") E1 o) {
		throw new RuntimeException("This method was not overridden."); //$NON-NLS-1$
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.iterable);
	}


	//********** default linker **********

	protected class DefaultTransformer implements Transformer<E1, E2> {
		public E2 transform(E1 o) {
			return TransformationIterable.this.transform(o);
		}
	}

}
