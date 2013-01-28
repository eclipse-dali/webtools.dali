/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterable;

import java.util.ArrayList;
import java.util.Iterator;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

public abstract class AbstractSimultaneousIterable<E, I extends Iterable<? extends E>> {

	final Iterable<? extends I> iterables;
	final int iterablesSize;  // hint


	/**
	 * Construct a "multiple" iterable for the specified iterables.
	 */
	<T extends I> AbstractSimultaneousIterable(Iterable<T> iterables) {
		this(iterables, -1);
	}

	/**
	 * Construct a "multiple" iterable for the specified iterables.
	 * Use the specified size as a performance hint.
	 */
	<T extends I> AbstractSimultaneousIterable(Iterable<T> iterables, int iterablesSize) {
		super();
		if (iterables == null) {
			throw new NullPointerException();
		}
		this.iterables = iterables;
		this.iterablesSize = iterablesSize;
	}

	<T extends Iterator<? extends E>> ArrayList<T> buildList() {
		return (this.iterablesSize < 0) ? new ArrayList<T>() : new ArrayList<T>(this.iterablesSize);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.iterables);
	}
}
