/*******************************************************************************
 * Copyright (c) 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.predicate;

import java.io.Serializable;
import java.util.Collection;
import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * Singleton predicate that evaluates whether a collection is empty.
 * 
 * @param <E> the type of elements held by the collection
 */
public final class CollectionIsEmptyPredicate<E>
	implements Predicate<Collection<E>>, Serializable
{
	@SuppressWarnings("rawtypes")
	public static final Predicate INSTANCE = new CollectionIsEmptyPredicate();

	@SuppressWarnings("unchecked")
	public static <E> Predicate<Collection<E>> instance() {
		return INSTANCE;
	}

	// ensure single instance
	private CollectionIsEmptyPredicate() {
		super();
	}

	/**
	 * Return whether the collection is empty.
	 */
	public boolean evaluate(Collection<E> collection) {
		return collection.isEmpty();
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