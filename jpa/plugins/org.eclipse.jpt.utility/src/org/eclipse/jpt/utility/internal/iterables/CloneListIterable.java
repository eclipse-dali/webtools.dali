/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.iterables;

import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

/**
 * Pull together mutator state and behavior for subclasses.
 * 
 * @param <E> the type of elements returned by the list iterable's list iterator
 * 
 * @see SnapshotCloneListIterable
 * @see LiveCloneListIterable
 */
public abstract class CloneListIterable<E>
	implements ListIterable<E>
{
	final CloneListIterator.Mutator<E> mutator;


	// ********** constructors **********

	protected CloneListIterable() {
		super();
		this.mutator = this.buildDefaultMutator();
	}

	protected CloneListIterable(CloneListIterator.Mutator<E> mutator) {
		super();
		this.mutator = mutator;
	}

	protected CloneListIterator.Mutator<E> buildDefaultMutator() {
		return new DefaultMutator();
	}


	// ********** default mutations **********

	/**
	 * At the specified index, add the specified element to the original list.
	 * <p>
	 * This method can be overridden by a subclass as an
	 * alternative to building a {@link CloneListIterator.Mutator}.
	 */
	protected void add(@SuppressWarnings("unused") int index, @SuppressWarnings("unused") E element) {
		throw new RuntimeException("This method was not overridden."); //$NON-NLS-1$
	}

	/**
	 * Remove the element at the specified index from the original list.
	 * <p>
	 * This method can be overridden by a subclass as an
	 * alternative to building a {@link CloneListIterator.Mutator}.
	 */
	protected void remove(@SuppressWarnings("unused") int index) {
		throw new RuntimeException("This method was not overridden."); //$NON-NLS-1$
	}

	/**
	 * At the specified index, set the specified element in the original list.
	 * <p>
	 * This method can be overridden by a subclass as an
	 * alternative to building a {@link CloneListIterator.Mutator}.
	 */
	protected void set(@SuppressWarnings("unused") int index, @SuppressWarnings("unused") E element) {
		throw new RuntimeException("This method was not overridden."); //$NON-NLS-1$
	}


	//********** default mutator **********

	protected class DefaultMutator implements CloneListIterator.Mutator<E> {
		public void add(int index, E element) {
			CloneListIterable.this.add(index, element);
		}
		public void remove(int index) {
			CloneListIterable.this.remove(index);
		}
		public void set(int index, E element) {
			CloneListIterable.this.set(index, element);
		}
	}

}
