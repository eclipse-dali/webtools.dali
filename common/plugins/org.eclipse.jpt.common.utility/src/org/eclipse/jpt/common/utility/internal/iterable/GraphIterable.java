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

import java.util.Iterator;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * A <code>GraphIterable</code> is similar to a {@link TreeIterable}
 * except that it cannot be assumed that all nodes assume a strict tree
 * structure. For instance, in a tree, a node cannot be a descendent of
 * itself, but a graph may have a cyclical structure.
 * <p>
 * A <code>GraphIterable</code> simplifies the traversal of a
 * graph of objects, where the objects' protocol(s) provides
 * a method for getting the next collection of nodes in the graph,
 * (or <em>neighbors</em>), but does not provide a method for
 * getting <em>all</em> of the nodes in the graph.
 * (e.g. a node can return its neighbors, and those neighbors
 * can return their neighbors, which might also include the original
 * node, but you only want to visit the original node once.)
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
 * <li> a {@link Transformer} that returns the neighbors of a node
 * </ul>
 * 
 * @param <E> the type of elements returned by the iterable's iterator
 * 
 * @see IteratorTools#graphIterator(Object, Transformer)
 */
public class GraphIterable<E>
	implements Iterable<E>
{
	private final Iterable<? extends E> roots;
	private final Transformer<? super E, ? extends Iterator<? extends E>> transformer;


	/**
	 * Construct an iterable containing the nodes of a graph 
	 * with the specified roots and transformer.
	 */
	public GraphIterable(Iterable<? extends E> roots, Transformer<? super E, ? extends Iterator<? extends E>> transformer) {
		super();
		if ((roots == null) || (transformer == null)) {
			throw new NullPointerException();
		}
		this.roots = roots;
		this.transformer = transformer;
	}

	public Iterator<E> iterator() {
		return IteratorTools.graphIterator(this.roots.iterator(), this.transformer);
	}

	@Override
	public String toString() {
		return ListTools.list(this).toString();
	}
}
