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

import org.eclipse.jpt.common.utility.closure.Closure;

/**
 * Pull together remover state and behavior for subclasses.
 * 
 * @param <E> the type of elements returned by the iterable's iterator
 * 
 * @see SnapshotCloneIterable
 * @see LiveCloneIterable
 */
public abstract class CloneIterable<E>
	implements Iterable<E>
{
	final Closure<? super E> removeClosure;


	protected CloneIterable(Closure<? super E> removeClosure) {
		super();
		if (removeClosure == null) {
			throw new NullPointerException();
		}
		this.removeClosure = removeClosure;
	}
}
