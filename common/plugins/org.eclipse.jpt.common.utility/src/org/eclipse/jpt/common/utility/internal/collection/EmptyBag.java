/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.collection;

import org.eclipse.jpt.common.utility.collection.Bag;
import org.eclipse.jpt.common.utility.internal.iterator.EmptyIterator;

public final class EmptyBag<E>
	extends java.util.AbstractCollection<E>
	implements Bag<E>, java.io.Serializable
{
	@SuppressWarnings("rawtypes")
	public static final Bag INSTANCE = new EmptyBag();
	@SuppressWarnings("unchecked")
	public static <T> Bag<T> instance() {
		return INSTANCE;
	}

	// ensure single instance
	private EmptyBag() {
		super();
	}

	@Override
	public java.util.Iterator<E> iterator() {
		return EmptyIterator.instance();
	}

	@Override
	public int size() {
		return 0;
	}

	public java.util.Iterator<E> uniqueIterator() {
		return EmptyIterator.instance();
	}

	public int uniqueCount() {
		return 0;
	}

	public int count(Object o) {
		return 0;
	}

	public java.util.Iterator<Bag.Entry<E>> entries() {
		return EmptyIterator.instance();
	}

	public boolean remove(Object o, int count) {
		return false;
	}

	public boolean add(E o, int count) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if ( ! (o instanceof Bag<?>)) {
			return false;
		}
		return ((Bag<?>) o).size() == 0;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	private static final long serialVersionUID = 1L;
	private Object readResolve() {
		// replace this object with the singleton
		return INSTANCE;
	}
}
