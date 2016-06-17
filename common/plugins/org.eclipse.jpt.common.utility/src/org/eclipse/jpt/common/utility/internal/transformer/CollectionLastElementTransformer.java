/*******************************************************************************
 * Copyright (c) 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.transformer;

import java.io.Serializable;
import java.util.Collection;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Transformer that transforms a collection into its last element.
 * If the collection is empty, the transformer returns <code>null</code>.
 * 
 * @param <E> the type of elements held by the collection
 */
public final class CollectionLastElementTransformer<E>
	implements Transformer<Collection<? extends E>, E>, Serializable
{
	@SuppressWarnings("rawtypes")
	public static final Transformer INSTANCE = new CollectionLastElementTransformer();

	@SuppressWarnings("unchecked")
	public static <E> Transformer<Collection<? extends E>, E> instance() {
		return INSTANCE;
	}

	// ensure single instance
	private CollectionLastElementTransformer() {
		super();
	}

	public E transform(Collection<? extends E> collection) {
		return collection.isEmpty() ? null : IterableTools.last(collection);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

	private static final long serialVersionUID = 1L;
	private Object readResolve() {
		// replace this object with the singleton
		return INSTANCE;
	}
}