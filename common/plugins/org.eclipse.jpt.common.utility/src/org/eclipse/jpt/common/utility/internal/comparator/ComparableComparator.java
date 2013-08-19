/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.comparator;

import java.io.Serializable;
import java.util.Comparator;

/**
 * This comparator compares elements that implement the
 * {@link Comparable} interface.
 * @param <E> the type of elements to be compared
 */
public class ComparableComparator<E extends Comparable<E>>
	implements Comparator<E>, Serializable
{
	@SuppressWarnings("rawtypes")
	public static final Comparator INSTANCE = new ComparableComparator();

	@SuppressWarnings("unchecked")
	public static <E> Comparator<E> instance() {
		return INSTANCE;
	}

	// ensure single instance
	private ComparableComparator() {
		super();
	}

	public int compare(E e1, E e2) {
		return e1.compareTo(e2);
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
