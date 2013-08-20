/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
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
