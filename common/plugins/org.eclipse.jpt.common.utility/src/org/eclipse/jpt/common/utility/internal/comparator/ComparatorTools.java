/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.comparator;

import java.util.Comparator;
import org.eclipse.jpt.common.utility.internal.transformer.TransformationComparator;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * {@link Comparator} utility methods.
 */
public final class ComparatorTools {

	// ********** transformation **********

	/**
	 * Return a comparator will transform the elements to be compared and
	 * compare the resulting outputs (i.e. assume the outputs
	 * implement the {@link Comparable} interface).
	 * 
	 * @param <E> the type of elements to be compared
	 * @param <O> the type of the result of transforming the elements and the type
	 *   of the elements to be compared by the wrapped comaparator, if present
	 *   
	 * @see TransformationComparator
	 */
	public static <E, O> Comparator<E> transformationComparator(Transformer<? super E, ? extends O> transformer) {
		return transformationComparator(transformer, null);
	}

	/**
	 * Return a comparator will transform the elements to be compared and pass the
	 * resulting outputs to a specified comparator.
	 * If the specified comparator is <code>null</code>,
	 * the natural ordering of the outputs will be used (i.e. assume the outputs
	 * implement the {@link Comparable} interface).
	 * 
	 * @param <E> the type of elements to be compared
	 * @param <O> the type of the result of transforming the elements and the type
	 *     of the elements to be compared by the wrapped comaparator, if present
	 * 
	 * @see TransformationComparator
	 */
	public static <E, O> Comparator<E> transformationComparator(Transformer<? super E, ? extends O> transformer, Comparator<O> comparator) {
		return new TransformationComparator<E, O>(transformer, comparator);
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private ComparatorTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
