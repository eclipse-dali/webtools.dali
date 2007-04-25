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
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.Transformer;


/**
 * A <code>TransformationIterator</code> wraps another <code>Iterator</code>
 * and transforms its results for client consumption. To use, supply a 
 * <code>Transformer</code> or subclass <code>TransformationIterator</code>
 * and override the <code>transform(Object)</code> method.
 * Objects of type E1 are transformed into objects of type E2;
 * i.e. the iterator returns objects of type E2.
 */
public class TransformationIterator<E1, E2>
	implements Iterator<E2>
{
	private final Iterator<? extends E1> nestedIterator;
	private final Transformer<E1, ? extends E2> transformer;


	/**
	 * Construct an iterator with the specified nested iterator
	 * and a transformer that simply returns the object, unchanged.
	 * Use this constructor if you want to override the
	 * <code>transform(Object)</code> method instead of building
	 * a <code>Transformer</code>.
	 */
	public TransformationIterator(Iterator<? extends E1> nestedIterator) {
		this(nestedIterator, Transformer.Null.<E1, E2>instance());
	}

	/**
	 * Construct an iterator with the specified nested iterator
	 * and transformer.
	 */
	public TransformationIterator(Iterator<? extends E1> nestedIterator, Transformer<E1, ? extends E2> transformer) {
		super();
		this.nestedIterator = nestedIterator;
		this.transformer = transformer;
	}

	public boolean hasNext() {
		// delegate to the nested iterator
		return this.nestedIterator.hasNext();
	}

	public E2 next() {
		// transform the object returned by the nested iterator before returning it
		return this.transform(this.nestedIterator.next());
	}

	public void remove() {
		// delegate to the nested iterator
		this.nestedIterator.remove();
	}

	/**
	 * Transform the specified object and return the result.
	 */
	protected E2 transform(E1 next) {
		return this.transformer.transform(next);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.nestedIterator);
	}

}
