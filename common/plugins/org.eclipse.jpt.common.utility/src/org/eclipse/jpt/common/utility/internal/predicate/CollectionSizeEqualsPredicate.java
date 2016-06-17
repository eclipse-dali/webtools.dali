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
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * A predicate that will evaluate whether a collection's
 * size is equal to a specified size.
 */
public class CollectionSizeEqualsPredicate
	implements Predicate<Collection<?>>, Serializable
{
	private final int size;
	private static final long serialVersionUID = 1L;

	/**
	 * Construct a predicate that will evaluate whether a collection's
	 * size is equal to the specified size.
	 */
	public CollectionSizeEqualsPredicate(int size) {
		super();
		this.size = size;
	}

	public boolean evaluate(Collection<?> collection) {
		return collection.size() == this.size;
	}

	/**
	 * Return the size used by the predicate.
	 */
	public int getSize() {
		return this.size;
	}

	@Override
	public boolean equals(Object o) {
		if ((o == null) || (o.getClass() != this.getClass())) {
			return false;
		}
		CollectionSizeEqualsPredicate other = (CollectionSizeEqualsPredicate) o;
		return this.size == other.size;
	}

	@Override
	public int hashCode() {
		return this.size;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.size);
	}
}
