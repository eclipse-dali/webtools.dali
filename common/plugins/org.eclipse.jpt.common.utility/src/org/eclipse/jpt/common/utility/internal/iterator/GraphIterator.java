/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterator;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * A <code>GraphIterator</code> is similar to a {@link TreeIterator}
 * except that it cannot be assumed that all nodes assume a strict tree
 * structure. For instance, in a tree, a node cannot be a descendent of
 * itself, but a graph may have a cyclical structure.
 * <p>
 * A <code>GraphIterator</code> simplifies the traversal of a
 * graph of objects, where the objects' protocol(s) provides
 * a method for getting the next collection of nodes in the graph,
 * (or <em>neighbors</em>), but does not provide a method for
 * getting <em>all</em> of the nodes in the graph.
 * (e.g. a node can return its neighbors, and those neighbors
 * can return their neighbors, which might also include the original
 * node, but you only want to visit the original node once.)
 * <p>
 * If a node has already been visited (determined by using 
 * {@link #equals(Object)}), that node is not visited again,
 * nor are the neighbors of that node.
 * <p>
 * It is up to the client of this class to ensure a <em>complete</em> graph.
 * <p>
 * To use, supply:<ul>
 * <li> either the initial node of the graph or an {@link Iterator}
 * over an initial collection of graph nodes
 * <li> a {@link Transformer} that returns the neighbors of a node
 * </ul>
 * 
 * @param <E> the type of elements returned by the iterator
 * 
 * @see org.eclipse.jpt.common.utility.internal.iterable.IterableTools#graphIterable(Object, Transformer)
 */
public class GraphIterator<E>
	implements Iterator<E>
{
	// use a LinkedList since we will be pulling off the front and adding to the end
	private final LinkedList<Iterator<? extends E>> iterators = new LinkedList<Iterator<? extends E>>();
	private final HashSet<E> visitedNeighbors = new HashSet<E>();
	private final Transformer<? super E, ? extends Iterator<? extends E>> transformer;

	private Iterator<? extends E> currentIterator;

	private E nextNeighbor;
	private boolean done;


	/**
	 * Construct an iterator that returns the nodes of a graph
	 * with the specified roots and transformer.
	 */
	public GraphIterator(Iterator<? extends E> roots, Transformer<? super E, ? extends Iterator<? extends E>> transformer) {
		super();
		if ((roots == null) || (transformer == null)) {
			throw new NullPointerException();
		}
		this.currentIterator = roots;
		this.transformer = transformer;
		this.loadNextNeighbor();
	}

	/**
	 * Load next neighbor with the next entry from the current iterator.
	 * If the current iterator has none, load the next iterator.
	 * If there are no more, the {@link #done} flag is set.
	 */
	private void loadNextNeighbor() {
		if (this.currentIterator == EmptyIterator.instance()) {
			this.done = true;
		}
		else if (this.currentIterator.hasNext()) {
			E nextPossibleNeighbor = this.currentIterator.next();
			if (this.visitedNeighbors.contains(nextPossibleNeighbor)) {
				this.loadNextNeighbor();  // recurse
			} else {
				this.nextNeighbor = nextPossibleNeighbor;
				this.visitedNeighbors.add(nextPossibleNeighbor);
				this.iterators.add(this.transformer.transform(nextPossibleNeighbor));
			}
		}
		else {
			for (Iterator<? extends Iterator<? extends E>> stream = this.iterators.iterator(); ! this.currentIterator.hasNext() && stream.hasNext(); ) {
				this.currentIterator = stream.next();
				stream.remove();
			}
			if ( ! this.currentIterator.hasNext()) {
				this.currentIterator = EmptyIterator.instance();
			}
			this.loadNextNeighbor();  // recurse
		}
	}

	public boolean hasNext() {
		return ! this.done;
	}

	public E next() {
		if (this.done) {
			throw new NoSuchElementException();
		}
		E next = this.nextNeighbor;
		this.loadNextNeighbor();
		return next;
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.currentIterator);
	}
}
