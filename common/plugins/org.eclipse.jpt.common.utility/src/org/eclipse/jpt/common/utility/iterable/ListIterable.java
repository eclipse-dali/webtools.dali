/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.iterable;

import java.util.ListIterator;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * A <code>ListIterable</code> simply extends {@link Iterable}
 * to return a {@link ListIterator} of type <code>E</code>.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @param <E> the type of elements returned by the iterable's iterators
 */
public interface ListIterable<E>
	extends Iterable<E>
{
	/**
	 * Return a list iterator over a set of elements of type E.
	 */
	ListIterator<E> iterator();

	@SuppressWarnings("rawtypes")
	Transformer TRANSFORMER = new IteratorTransformer();
	class IteratorTransformer<E>
		extends TransformerAdapter<ListIterable<E>, ListIterator<E>>
	{
		@Override
		public ListIterator<E> transform(ListIterable<E> iterable) {
			return iterable.iterator();
		}
	}
}
