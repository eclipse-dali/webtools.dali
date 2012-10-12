/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterator;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * A <code>GraphIterator</code> is similar to a {@link TreeIterator}
 * except that it cannot be assumed that all nodes assume a strict tree
 * structure. For instance, in a tree, a node cannot be a descendent of
 * itself, but a graph may have a cyclical structure.
 * 
 * A <code>GraphIterator</code> simplifies the traversal of a
 * graph of objects, where the objects' protocol(s) provides
 * a method for getting the next collection of nodes in the graph,
 * (or <em>neighbors</em>), but does not provide a method for
 * getting <em>all</em> of the nodes in the graph.
 * (e.g. a neighbor can return his neighbors, and those neighbors
 * can return their neighbors, which might also include the original
 * neighbor, but you only want to visit the original neighbor once.)
 * <p>
 * If a neighbor has already been visited (determined by using 
 * {@link #equals(Object)}), that neighbor is not visited again,
 * nor are the neighbors of that object.
 * <p>
 * It is up to the user of this class to ensure a <em>complete</em> graph.
 * <p>
 * To use, supply:<ul>
 * <li> either the initial node of the graph or an {@link Iterator}
 * over an initial collection of graph nodes
 * <li> a {@link MisterRogers} that tells who the neighbors are
 * of each node
 * (alternatively, subclass <code>GraphIterator</code>
 * and override the {@link #neighbors(Object)} method)
 * </ul>
 * {@link #remove()} is not supported. This behavior, if
 * desired, must be implemented by the user of this class.
 * 
 * @param <E> the type of elements returned by the iterator
 * 
 * @see org.eclipse.jpt.common.utility.internal.iterable.GraphIterable
 */
public class GraphIterator<E>
	implements Iterator<E>
{
	// use a LinkedList since we will be pulling off the front and adding to the end
	private final LinkedList<Iterator<? extends E>> iterators = new LinkedList<Iterator<? extends E>>();
	private final HashSet<E> visitedNeighbors = new HashSet<E>();
	private final MisterRogers<E> misterRogers;

	private Iterator<? extends E> currentIterator;

	private E nextNeighbor;
	private boolean done;


	/**
	 * Construct an iterator that returns the nodes of a graph
	 * with the specified collection of roots
	 * and a disabled Mr. Rogers.
	 * Use this constructor if you want to override the
	 * {@link #neighbors(Object)} method instead of building
	 * a {@link MisterRogers}.
	 */
	public GraphIterator(E... roots) {
		this(new ArrayIterator<E>(roots));
	}

	/**
	 * Construct an iterator that returns the nodes of a graph
	 * with the specified collection of roots
	 * and a disabled Mr. Rogers.
	 * Use this constructor if you want to override the
	 * {@link #neighbors(Object)} method instead of building
	 * a {@link MisterRogers}.
	 */
	public GraphIterator(Iterable<? extends E> roots) {
		this(roots.iterator());
	}

	/**
	 * Construct an iterator that returns the nodes of a graph
	 * with the specified collection of roots
	 * and a disabled Mr. Rogers.
	 * Use this constructor if you want to override the
	 * {@link #neighbors(Object)} method instead of building
	 * a {@link MisterRogers}.
	 */
	public GraphIterator(Iterator<? extends E> roots) {
		this(roots, MisterRogers.Disabled.<E>instance());
	}

	/**
	 * Construct an iterator that returns the nodes of a graph
	 * with the specified root
	 * and a disabled Mr. Rogers.
	 * Use this constructor if you want to override the
	 * <code>neighbors(Object)</code> method instead of building
	 * a <code>MisterRogers</code>.
	 */
	public GraphIterator(E root) {
		this(root, MisterRogers.Disabled.<E>instance());
	}

	/**
	 * Construct an iterator that returns the nodes of a graph
	 * with the specified root and Mr. Rogers.
	 */
	public GraphIterator(E root, MisterRogers<E> misterRogers) {
		this(new SingleElementIterator<E>(root), misterRogers);
	}

	/**
	 * Construct an iterator that returns the nodes of a graph
	 * with the specified collection of roots and Mr. Rogers.
	 */
	public GraphIterator(E[] roots, MisterRogers<E> misterRogers) {
		this(new ArrayIterator<E>(roots), misterRogers);
	}

	/**
	 * Construct an iterator that returns the nodes of a graph
	 * with the specified roots and Mr. Rogers.
	 */
	public GraphIterator(Iterable<? extends E> roots, MisterRogers<E> misterRogers) {
		this(roots.iterator(), misterRogers);
	}

	/**
	 * Construct an iterator that returns the nodes of a graph
	 * with the specified roots and Mr. Rogers.
	 */
	public GraphIterator(Iterator<? extends E> roots, MisterRogers<E> misterRogers) {
		super();
		if ((roots == null) || (misterRogers == null)) {
			throw new NullPointerException();
		}
		this.currentIterator = roots;
		this.misterRogers = misterRogers;
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
				this.iterators.add(this.neighbors(nextPossibleNeighbor));
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

	/**
	 * Return the immediate neighbors of the specified object.
	 */
	protected Iterator<? extends E> neighbors(E next) {
		return this.misterRogers.neighbors(next);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.currentIterator);
	}


	//********** inner classes **********

	/**
	 * Used by {@link GraphIterator} to retrieve
	 * the immediate neighbors of a node in the graph.
	 * "These are the people in your neighborhood..."
	 */
	public interface MisterRogers<T> {

		/**
		 * Return the immediate neighbors of the specified object.
		 */
		Iterator<? extends T> neighbors(T next);


		final class Null<S>
			implements MisterRogers<S>, Serializable
		{
			@SuppressWarnings("rawtypes")
			public static final MisterRogers INSTANCE = new Null();
			@SuppressWarnings("unchecked")
			public static <R> MisterRogers<R> instance() {
				return INSTANCE;
			}
			// ensure single instance
			private Null() {
				super();
			}
			// return no neighbors
			public Iterator<S> neighbors(S next) {
				return EmptyIterator.instance();
			}
			@Override
			public String toString() {
				return ObjectTools.singletonToString(this);
			}
			private static final long serialVersionUID = 1L;
			private Object readResolve() {
				// replace this object with the singleton
				return INSTANCE;
			}
		}

		/** The Mr. Rogers used when the {@link GraphIterator#neighbors(Object)} method is overridden. */
		final class Disabled<S>
			implements MisterRogers<S>, Serializable
		{
			@SuppressWarnings("rawtypes")
			public static final MisterRogers INSTANCE = new Disabled();
			@SuppressWarnings("unchecked")
			public static <R> MisterRogers<R> instance() {
				return INSTANCE;
			}
			// ensure single instance
			private Disabled() {
				super();
			}
			// throw an exception
			public Iterator<S> neighbors(S next) {
				throw new UnsupportedOperationException();  // GraphIterator.neighbors(Object) was not implemented
			}
			@Override
			public String toString() {
				return ObjectTools.singletonToString(this);
			}
			private static final long serialVersionUID = 1L;
			private Object readResolve() {
				// replace this object with the singleton
				return INSTANCE;
			}
		}
	}
}
