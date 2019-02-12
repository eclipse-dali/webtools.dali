/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterable;

import org.eclipse.jpt.common.utility.internal.iterator.CloneListIterator;
import org.eclipse.jpt.common.utility.iterable.ListIterable;

/**
 * Pull together adapter state and behavior for subclasses.
 * 
 * @param <E> the type of elements returned by the list iterable's list iterator
 * 
 * @see SnapshotCloneListIterable
 * @see LiveCloneListIterable
 */
public abstract class CloneListIterable<E>
	implements ListIterable<E>
{
	final CloneListIterator.Adapter<E> adapter;


	protected CloneListIterable(CloneListIterator.Adapter<E> adapter) {
		super();
		if (adapter == null) {
			throw new NullPointerException();
		}
		this.adapter = adapter;
	}
}
