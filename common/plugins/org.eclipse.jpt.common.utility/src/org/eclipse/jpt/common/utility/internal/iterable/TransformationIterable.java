/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterable;

import java.util.Iterator;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * A <code>TransformationIterable</code> wraps another {@link Iterable}
 * and transforms its elements for client consumption.
 * Objects of type <code>E1</code> are transformed into objects of type <code>E2</code>;
 * i.e. the iterable's iterator returns objects of type <code>E2</code>.
 * 
 * @param <E1> input: the type of elements to be transformed
 * @param <E2> output: the type of elements returned by the iterable's iterator
 * 
 * @see IteratorTools#transform(Iterator, Transformer)
 * @see TransformationListIterable
 */
public class TransformationIterable<E1, E2>
	implements Iterable<E2>
{
	private final Iterable<? extends E1> iterable;
	private final Transformer<? super E1, ? extends E2> transformer;


	/**
	 * Construct an iterable with the specified nested iterable
	 * and transformer.
	 */
	public TransformationIterable(Iterable<? extends E1> iterable, Transformer<? super E1, ? extends E2> transformer) {
		super();
		if ((iterable == null) || (transformer == null)) {
			throw new NullPointerException();
		}
		this.iterable = iterable;
		this.transformer = transformer;
	}

	public Iterator<E2> iterator() {
		return IteratorTools.transform(this.iterable.iterator(), this.transformer);
	}

	@Override
	public String toString() {
		return ListTools.list(this).toString();
	}
}
