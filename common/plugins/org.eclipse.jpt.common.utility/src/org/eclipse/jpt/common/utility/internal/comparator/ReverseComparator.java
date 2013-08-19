/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * This comparator will reverse the order of the specified comparator.
 * If the specified comparator is <code>null</code>,
 * the natural ordering of the objects will be used (i.e. assume the elements
 * implement the {@link Comparable} interface.
 * @param <E> the type of elements to be compared
 */
public class ReverseComparator<E>
	implements Comparator<E>, Serializable
{
	private final Comparator<E> comparator;
	private static final long serialVersionUID = 1L;

	/**
	 * Construct a reverse comparator that will reverse the natural order of
	 * the compared elements.
	 */
	public ReverseComparator() {
		this(null);
	}

	/**
	 * Construct a reverse comparator that will reverse the order of
	 * the compared elements as calculated by the specified comparator.
	 */
	public ReverseComparator(Comparator<E> comparator) {
		super();
		this.comparator = comparator;
	}

	@SuppressWarnings("unchecked")
	public int compare(E e1, E e2) {
		return (this.comparator != null) ? this.comparator.compare(e2, e1) : ((Comparable<E>) e2).compareTo(e1);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.comparator);
	}
}
