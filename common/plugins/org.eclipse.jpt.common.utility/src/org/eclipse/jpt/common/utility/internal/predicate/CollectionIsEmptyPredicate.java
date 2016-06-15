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
 */
public final class CollectionIsEmptyPredicate
	implements Predicate<Collection<?>>, Serializable
{
	public static final Predicate<Collection<?>> INSTANCE = new CollectionIsEmptyPredicate();

	public static Predicate<Collection<?>> instance() {
		return INSTANCE;
	}

	// ensure single instance
	private CollectionIsEmptyPredicate() {
		super();
	}

	/**
	 * Return whether the collection is empty.
	 */
	public boolean evaluate(Collection<?> collection) {
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