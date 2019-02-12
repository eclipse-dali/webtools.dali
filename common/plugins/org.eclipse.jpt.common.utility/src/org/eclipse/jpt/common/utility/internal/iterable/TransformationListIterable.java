/*******************************************************************************
 * Copyright (c) 2009, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterable;

import java.util.ListIterator;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * A <code>TransformationListIterable</code> wraps another {@link ListIterable}
 * and transforms its elements for client consumption.
 * Objects of type <code>E1</code> are transformed into objects of type <code>E2</code>;
 * i.e. the list iterable's list iterator returns objects of type <code>E2</code>.
 * 
 * @param <E1> input: the type of elements to be transformed
 * @param <E2> output: the type of elements returned by the iterable's iterator
 * 
 * @see IteratorTools#transform(ListIterator, Transformer)
 * @see TransformationIterable
 */
public class TransformationListIterable<E1, E2>
	implements ListIterable<E2>
{
	private final ListIterable<? extends E1> iterable;
	private final Transformer<? super E1, ? extends E2> transformer;


	/**
	 * Construct a list iterable with the specified nested list iterable
	 * and transformer.
	 */
	public TransformationListIterable(ListIterable<? extends E1> iterable, Transformer<? super E1, ? extends E2> transformer) {
		super();
		if ((iterable == null) || (transformer == null)) {
			throw new NullPointerException();
		}
		this.iterable = iterable;
		this.transformer = transformer;
	}

	public ListIterator<E2> iterator() {
		return IteratorTools.transform(this.iterable.iterator(), this.transformer);
	}

	@Override
	public String toString() {
		return ListTools.arrayList(this).toString();
	}
}
