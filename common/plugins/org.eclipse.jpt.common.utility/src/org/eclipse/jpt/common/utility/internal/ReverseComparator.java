/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

import java.io.Serializable;
import java.util.Comparator;

/**
 * This comparator will reverse the order of the specified comparator.
 * If the specified comparator is <code>null</code>,
 * the natural ordering of the objects will be used.
 */
public class ReverseComparator<E extends Comparable<? super E>>
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

	public int compare(E e1, E e2) {
		return (this.comparator != null) ? this.comparator.compare(e2, e1) : e2.compareTo(e1);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.comparator);
	}
}
