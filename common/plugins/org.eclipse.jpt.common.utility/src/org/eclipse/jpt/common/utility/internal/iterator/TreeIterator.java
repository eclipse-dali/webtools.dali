/*******************************************************************************
 * Copyright (c) 2005, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterator;

import java.util.Iterator;
import java.util.LinkedList;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * A <code>TreeIterator</code> simplifies the traversal of a
 * tree of objects, where the objects' protocol(s) provides
 * a method for getting the immediate children of the given
 * node but does not provide a method for getting all the
 * descendants (children, grandchildren, etc.) of the given node.
 * <p>
 * To use, supply:<ul>
 * <li> either the root element of the tree or, if the tree has
 * multiple roots, an {@link Iterator} over the set of roots
 * <li> a {@link Transformer} that delivers the children
 * of each child
 * </ul>
 * 
 * @param <E> the type of elements returned by the iterator
 * 
 * @see org.eclipse.jpt.common.utility.internal.iterable.IterableTools#treeIterable(Iterable, Transformer)
 */
public class TreeIterator<E>
	implements Iterator<E>
{
	private final LinkedList<Iterator<? extends E>> iterators;
	private final Transformer<? super E, ? extends Iterator<? extends E>> transformer;
	private Iterator<? extends E> currentIterator;


	/**
	 * Construct an iterator that returns the nodes of a tree
	 * with the specified roots and transformer.
	 */
	public TreeIterator(Iterator<? extends E> roots, Transformer<? super E, ? extends Iterator<? extends E>> transformer) {
		super();
		if ((roots == null) || (transformer == null)) {
			throw new NullPointerException();
		}
		this.currentIterator = roots;
		// use a LinkedList since we will be pulling off the front and adding to the end
		this.iterators = new LinkedList<>();
		this.transformer = transformer;
	}

	public boolean hasNext() {
		if (this.currentIterator.hasNext()) {
			return true;
		}
		for (Iterator<? extends E> iterator : this.iterators) {
			if (iterator.hasNext()) {
				return true;
			}
		}
		return false;
	}

	public E next() {
		if (this.currentIterator.hasNext()) {
			return this.nextInternal();
		}
		for (Iterator<Iterator<? extends E>> stream = this.iterators.iterator(); stream.hasNext(); ) {
			this.currentIterator = stream.next();
			if (this.currentIterator.hasNext()) {
				break;
			}
			stream.remove();
		}
		return this.nextInternal();
	}

	/**
	 * Fetch the children of the next node before returning it.
	 */
	private E nextInternal() {
		E next = this.currentIterator.next();
		this.iterators.add(this.transformer.transform(next));
		return next;
	}

	public void remove() {
		this.currentIterator.remove();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.currentIterator);
	}
}
