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

import java.util.Comparator;
import java.util.Iterator;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;

/**
 * This comparator will use a list of comparators to compare two objects.
 * If the first comparator returns a non-zero value, that will be the
 * comparator's value; otherwise the next comparator will be called; and so on.
 * 
 * @param <E> the type of elements to be compared
 */
public class ComparatorChain<E>
	implements Comparator<E>
{
	private final Iterable<Comparator<? super E>> comparators;

	public ComparatorChain(Iterable<Comparator<? super E>> comparators) {
		super();
		if (IterableTools.isOrContainsNull(comparators)) {
			throw new NullPointerException();
		}
		if (IterableTools.isEmpty(comparators)) {
			throw new IllegalArgumentException("comparators must not empty"); //$NON-NLS-1$
		}
		this.comparators = comparators;
	}

	public int compare(E o1, E o2) {
		int result = 0;
		for (Iterator<Comparator<? super E>> stream = this.comparators.iterator(); stream.hasNext() && (result == 0); ) {
			result = stream.next().compare(o1, o2);
		}
		return result;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.comparators);
	}
}
