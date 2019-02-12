/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.comparator;

import java.util.Comparator;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * This comparator will transform the elements to be compared and pass the
 * resulting outputs to a wrapped transformer.
 * @param <E> the type of elements to be compared
 * @param <O> the type of the result of transforming the elements and the type
 *   of the elements to be compared by the wrapped comaparator, if present
 */
public class TransformationComparator<E, O>
	implements Comparator<E>
{
	private final Transformer<? super E, ? extends O> transformer;
	private final Comparator<O> comparator;


	public TransformationComparator(Transformer<? super E, ? extends O> transformer, Comparator<O> comparator) {
		super();
		if ((transformer == null) || (comparator == null)) {
			throw new NullPointerException();
		}
		this.transformer = transformer;
		this.comparator = comparator;
	}

	public int compare(E e1, E e2) {
		return this.comparator.compare(this.transformer.transform(e1), this.transformer.transform(e2));
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.comparator);
	}
}
