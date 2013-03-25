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
import org.eclipse.jpt.common.utility.internal.transformer.TransformerTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * A <code>TreeIterable</code> simplifies the traversal of a
 * tree of objects, where the objects' protocol(s) provides
 * a method for getting the immediate children of the given
 * node but does not provide a method for getting all the
 * descendants (children, grandchildren, etc.) of the given node.
 * <p>
 * To use, supply:<ul>
 * <li> either the root element of the tree or, if the tree has
 * multiple roots, an {@link Iterable} of the set of roots
 * <li> a {@link Transformer} that delivers the children of each child
 * </ul>
 * 
 * @param <E> the type of elements returned by the iterable's iterator
 * 
 * @see IteratorTools#treeIterator(Iterable, Transformer)
 */
public class TreeIterable<E>
	implements Iterable<E>
{
	private final Iterable<? extends E> roots;
	private final Transformer<? super E, ? extends Iterator<? extends E>> transformer;


	/**
	 * Construct an iterable containing the nodes of a tree with the specified roots
	 * and midwife.
	 */
	public TreeIterable(Iterable<? extends E> roots, Transformer<? super E, ? extends Iterable<? extends E>> transformer) {
		super();
		if ((roots == null) || (transformer == null)) {
			throw new NullPointerException();
		}
		this.roots = roots;
		this.transformer = TransformerTools.toIterator(transformer);
	}

	public Iterator<E> iterator() {
		return IteratorTools.treeIterator(this.roots.iterator(), this.transformer);
	}

	@Override
	public String toString() {
		return ListTools.list(this).toString();
	}
}
