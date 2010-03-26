/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

import java.io.Serializable;

import org.eclipse.jpt.utility.Filter;

/**
 * This filter accepts only non-null objects.
 */
public final class NotNullFilter<T>
	implements Filter<T>, Serializable
{
	@SuppressWarnings("rawtypes")
	public static final Filter INSTANCE = new NotNullFilter();

	@SuppressWarnings("unchecked")
	public static <R> Filter<R> instance() {
		return INSTANCE;
	}

	// ensure single instance
	private NotNullFilter() {
		super();
	}

	// accept only non-null objects
	public boolean accept(T o) {
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
