/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.enumerations;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.NoSuchElementException;

/**
 * An <code>EmptyEnumeration</code> is just that.
 * 
 * @param <E> the type of elements returned by the enumeration
 */
public final class EmptyEnumeration<E>
	implements Enumeration<E>, Serializable
{

	// singleton
	@SuppressWarnings("rawtypes")
	private static final EmptyEnumeration INSTANCE = new EmptyEnumeration();

	/**
	 * Return the singleton.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Enumeration<T> instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EmptyEnumeration() {
		super();
	}

	public boolean hasMoreElements() {
		return false;
	}

	public E nextElement() {
		throw new NoSuchElementException();
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
