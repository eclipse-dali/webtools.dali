/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.transformer;

import java.util.Comparator;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * This comparator will transform the elements to be compared and pass the
 * resulting outputs to a wrapped transformer.
 * If the wrapped comparator is <code>null</code>,
 * the natural ordering of the outputs will be used (i.e. assume the outputs
 * implement the {@link Comparable} interface).
 * @param <E> the type of elements to be compared
 * @param <O> the type of the result of transforming the elements and the type
 *   of the elements to be compared by the wrapped comaparator, if present
 */
public class TransformationComparator<E, O>
	implements Comparator<E>
{
	private final Transformer<? super E, ? extends O> transformer;
	private final Comparator<O> comparator;


	/**
	 * Construct a comparator that will use the specified transformer to
	 * transform the elements to be compared. The resulting outputs will be
	 * passed to the specified comparator and the result returned by the
	 * transformation comparator as the result of comparing the original
	 * elements.
	 */
	public TransformationComparator(Transformer<? super E, ? extends O> transformer, Comparator<O> comparator) {
		super();
		if (transformer == null) {
			throw new NullPointerException();
		}
		this.transformer = transformer;
		this.comparator = comparator;
	}

	@SuppressWarnings("unchecked")
	public int compare(E e1, E e2) {
		O o1 = this.transformer.transform(e1);
		O o2 = this.transformer.transform(e2);
		return (this.comparator != null) ? this.comparator.compare(o1, o2) : ((Comparable<O>) o1).compareTo(o2);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.comparator);
	}
}
