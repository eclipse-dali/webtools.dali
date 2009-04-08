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
 * A <code>GraphIterable</code> is similar to a <code>TreeIterable</code>
 * except that it cannot be assumed that all nodes assume a strict tree
 * structure. For instance, in a tree, a node cannot be a descendent of
 * itself, but a graph may have a cyclical structure.
 * 
 * A <code>GraphIterable</code> simplifies the traversal of a
 * graph of objects, where the objects' protocol(s) provides
 * a method for getting the next collection of nodes in the graph,
 * (or *neighbors*), but does not provide a method for getting *all* 
 * of the nodes in the graph.
 * (e.g. a neighbor can return his neighbors, and those neighbors
 * can return their neighbors, which might also include the original
 * neighbor, but you only want to visit the original neighbor once.)
 * <p>
 * If a neighbor has already been visited (determined by using 
 * <code>equals(Object)</code>), that neighbor is not visited again,
 * nor are the neighbors of that object.
 * <p>
 * It is up to the user of this class to ensure a *complete* graph.
 * <p>
 * To use, supply:<ul>
 * <li> either the initial node of the graph or an <code>Iterable</code>
 * of the initial collection of graph nodes
 * <li> a <code>MisterRogers</code> that tells who the neighbors are
 * of each node
 * (alternatively, subclass <code>GraphIterable</code>
 * and override the <code>neighbors(Object)</code> method)
 * </ul>
 * <p>
 * <code>remove()</code> is not supported. This method, if 
 * desired, must be implemented by the user of this class.
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
	 * and a default mister rogers that calls back to the iterable.
	 * Use this constructor if you want to override the
	 * <code>neighbors(Object)</code> method instead of building
	 * a <code>GraphIterator.MisterRogers</code>.
	 */
	public GraphIterable(E root) {
		this(new SingleElementIterable<E>(root));
	}

	/**
	 * Construct an iterable containing the nodes of a graph 
	 * with the specified root and mister rogers.
	 */
	public GraphIterable(E root, GraphIterator.MisterRogers<E> misterRogers) {
		this(new SingleElementIterable<E>(root), misterRogers);
	}

	/**
	 * Construct an iterable containing the nodes of a graph 
	 * with the specified collection of roots
	 * and a default mister rogers that calls back to the iterable.
	 * Use this constructor if you want to override the
	 * <code>neighbors(Object)</code> method instead of building
	 * a <code>GraphIterator.MisterRogers</code>.
	 */
	public GraphIterable(E... roots) {
		this(Arrays.asList(roots));
	}

	/**
	 * Construct an iterable containing the nodes of a graph 
	 * with the specified roots and mister rogers.
	 */
	public GraphIterable(E[] roots, GraphIterator.MisterRogers<E> misterRogers) {
		this(Arrays.asList(roots), misterRogers);
	}

	/**
	 * Construct an iterable containing the nodes of a graph 
	 * with the specified collection of roots
	 * and a default mister rogers that calls back to the iterable.
	 * Use this constructor if you want to override the
	 * <code>neighbors(Object)</code> method instead of building
	 * a <code>GraphIterator.MisterRogers</code>.
	 */
	public GraphIterable(Iterable<? extends E> roots) {
		super();
		this.roots = roots;
		this.misterRogers = new DefaultMisterRogers();
	}

	/**
	 * Construct an iterable containing the nodes of a graph 
	 * with the specified roots and mister rogers.
	 */
	public GraphIterable(Iterable<? extends E> roots, GraphIterator.MisterRogers<E> misterRogers) {
		super();
		this.roots = roots;
		this.misterRogers = misterRogers;
	}

	public Iterator<E> iterator() {
		return new GraphIterator<E>(this.roots, this.misterRogers);
	}

	/**
	 * Return the immediate neighbors of the specified object.
	 * <p>
	 * This method can be overridden by a subclass as an
	 * alternative to building a <code>GraphIterator.MisterRogers</code>.
	 */
	protected Iterator<? extends E> neighbors(@SuppressWarnings("unused") E next) {
		// GraphIterable.neighbors(Object) was not overridden
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.roots);
	}


	//********** default Mr. Rogers **********

	class DefaultMisterRogers implements GraphIterator.MisterRogers<E> {
		public Iterator<? extends E> neighbors(E node) {
			return GraphIterable.this.neighbors(node);
		}
	}

}
