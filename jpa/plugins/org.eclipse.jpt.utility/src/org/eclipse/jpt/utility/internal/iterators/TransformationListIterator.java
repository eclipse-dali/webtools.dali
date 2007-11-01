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

import java.util.ListIterator;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.Transformer;

/**
 * A <code>TransformationListIterator</code> wraps another <code>ListIterator</code>
 * and transforms its results for client consumption. To use, supply a 
 * <code>Transformer</code> or subclass <code>TransformationIterator</code>
 * and override the <code>transform(Object)</code> method.
 * 
 * The methods <code>set(Object)</code> and <code>add(Object)</code>
 * are left unsupported in this class.
 */
public class TransformationListIterator<E1, E2>
	implements ListIterator<E2>
{
	private final ListIterator<? extends E1> nestedIterator;
	private final Transformer<E1, ? extends E2> transformer;


	/**
	 * Construct an iterator with the specified nested iterator
	 * and a disabled transformer.
	 * Use this constructor if you want to override the
	 * <code>transform(Object)</code> method instead of building
	 * a <code>Transformer</code>.
	 */
	public TransformationListIterator(ListIterator<? extends E1> nestedIterator) {
		this(nestedIterator, Transformer.Disabled.<E1, E2>instance());
	}

	/**
	 * Construct an iterator with the specified nested iterator
	 * and transformer.
	 */
	public TransformationListIterator(ListIterator<? extends E1> nestedIterator, Transformer<E1, ? extends E2> transformer) {
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

	public int nextIndex() {
		// delegate to the nested iterator
		return this.nestedIterator.nextIndex();
	}

	public boolean hasPrevious() {
		// delegate to the nested iterator
		return this.nestedIterator.hasPrevious();
	}

	public E2 previous() {
		// transform the object returned by the nested iterator before returning it
		return this.transform(this.nestedIterator.previous());
	}

	public int previousIndex() {
		// delegate to the nested iterator
		return this.nestedIterator.previousIndex();
	}

	public void add(E2 o) {
		throw new UnsupportedOperationException();
	}

	public void set(E2 o) {
		throw new UnsupportedOperationException();
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
