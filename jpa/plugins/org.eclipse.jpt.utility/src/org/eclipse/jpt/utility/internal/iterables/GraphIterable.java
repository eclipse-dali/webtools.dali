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

import java.util.Arrays;
import java.util.Iterator;

import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.GraphIterator;

/**
 * A <code>GraphIterable</code> is similar to a {@link TreeIterable}
 * except that it cannot be assumed that all nodes assume a strict tree
 * structure. For instance, in a tree, a node cannot be a descendent of
 * itself, but a graph may have a cyclical structure.
 * 
 * A <code>GraphIterable</code> simplifies the traversal of a
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
 * <li> either the initial node of the graph or an {@link Iterable}
 * of the initial collection of graph nodes
 * <li> a {@link GraphIterator.MisterRogers} that tells who the neighbors are
 * of each node
 * (alternatively, subclass <code>GraphIterable</code>
 * and override the {@link #neighbors(Object)} method)
 * </ul>
 * The {@link Iterator#remove()} operation is not supported. This behavior, if 
 * desired, must be implemented by the user of this class.
 * 
 * @param <E> the type of elements returned by the iterable's iterator
 * 
 * @see GraphIterator
 */
public class GraphIterable<E>
	implements Iterable<E>
{
	private final Iterable<? extends E> roots;
	private final GraphIterator.MisterRogers<E> misterRogers;


	/**
	 * Construct an iterable containing the nodes of a graph with the specified root
	 * and a default Mr. Rogers that calls back to the iterable.
	 * Use this constructor if you want to override the
	 * {@link #neighbors(Object)} method instead of building
	 * a {@link GraphIterator.MisterRogers}.
	 */
	public GraphIterable(E root) {
		this(new SingleElementIterable<E>(root));
	}

	/**
	 * Construct an iterable containing the nodes of a graph 
	 * with the specified root and Mr. Rogers.
	 */
	public GraphIterable(E root, GraphIterator.MisterRogers<E> misterRogers) {
		this(new SingleElementIterable<E>(root), misterRogers);
	}

	/**
	 * Construct an iterable containing the nodes of a graph 
	 * with the specified collection of roots
	 * and a default Mr. Rogers that calls back to the iterable.
	 * Use this constructor if you want to override the
	 * {@link #neighbors(Object)} method instead of building
	 * a {@link GraphIterator.MisterRogers}.
	 */
	public GraphIterable(E... roots) {
		this(Arrays.asList(roots));
	}

	/**
	 * Construct an iterable containing the nodes of a graph 
	 * with the specified roots and Mr. Rogers.
	 */
	public GraphIterable(E[] roots, GraphIterator.MisterRogers<E> misterRogers) {
		this(Arrays.asList(roots), misterRogers);
	}

	/**
	 * Construct an iterable containing the nodes of a graph 
	 * with the specified collection of roots
	 * and a default Mr. Rogers that calls back to the iterable.
	 * Use this constructor if you want to override the
	 * {@link #neighbors(Object)} method instead of building
	 * a {@link GraphIterator.MisterRogers}.
	 */
	public GraphIterable(Iterable<? extends E> roots) {
		super();
		this.roots = roots;
		this.misterRogers = this.buildDefaultMisterRogers();
	}

	/**
	 * Construct an iterable containing the nodes of a graph 
	 * with the specified roots and Mr. Rogers.
	 */
	public GraphIterable(Iterable<? extends E> roots, GraphIterator.MisterRogers<E> misterRogers) {
		super();
		this.roots = roots;
		this.misterRogers = misterRogers;
	}

	protected GraphIterator.MisterRogers<E> buildDefaultMisterRogers() {
		return new DefaultMisterRogers();
	}

	public Iterator<E> iterator() {
		return new GraphIterator<E>(this.roots, this.misterRogers);
	}

	/**
	 * Return the immediate neighbors of the specified object.
	 * <p>
	 * This method can be overridden by a subclass as an
	 * alternative to building a {@link GraphIterator.MisterRogers}.
	 */
	protected Iterator<? extends E> neighbors(@SuppressWarnings("unused") E next) {
		throw new RuntimeException("This method was not overridden."); //$NON-NLS-1$
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.roots);
	}


	//********** default Mr. Rogers **********

	protected class DefaultMisterRogers implements GraphIterator.MisterRogers<E> {
		public Iterator<? extends E> neighbors(E node) {
			return GraphIterable.this.neighbors(node);
		}
	}

}
