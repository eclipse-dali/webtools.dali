/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.filter;

import java.io.Serializable;

import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * This filter accepts only non-null objects.
 * 
 * @param <T> the type of objects to be filtered
 */
public final class NotNullFilter<T>
	implements Predicate<T>, Serializable
{
	@SuppressWarnings("rawtypes")
	public static final Predicate INSTANCE = new NotNullFilter();

	@SuppressWarnings("unchecked")
	public static <R> Predicate<R> instance() {
		return INSTANCE;
	}

	// ensure single instance
	private NotNullFilter() {
		super();
	}

	// accept only non-null objects
	public boolean evaluate(T o) {
		return o != null;
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
