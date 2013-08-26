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
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * This comparator sort <code>null</code>s <em>after</em> any
 * non-<code>null</code> elements. Non-<code>null</code> elements will be
 * compared by the configured comparator.
 * 
 * @param <E> the type of elements to be compared
 */
public class NullsLastComparator<E>
	implements Comparator<E>
{
	private final Comparator<? super E> comparator;

	public NullsLastComparator(Comparator<? super E> comparator) {
		super();
		if (comparator == null) {
			throw new NullPointerException();
		}
		this.comparator = comparator;
	}

	public int compare(E e1, E e2) {
		return (e1 == null) ?
				((e2 == null) ? 0 : 1) :
				((e2 == null) ? -1 : this.comparator.compare(e1, e2));
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.comparator);
	}
}
