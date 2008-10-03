/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.iterators;

import java.util.Iterator;
import java.util.LinkedList;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * A <code>TreeIterator</code> simplifies the traversal of a
 * tree of objects, where the objects' protocol(s) provides
 * a method for getting the immediate children of the given
 * node but does not provide a method for getting all the
 * descendants (children, grandchildren, etc.) of the given node.
 * <p>
 * To use, supply:<ul>
 * <li> either the root element of the tree or, if the tree has
 * multiple roots, an <code>Iterator</code> over the set of roots
 * <li> a <code>Midwife</code> that delivers the children
 * of each child
 * (alternatively, subclass <code>TreeIterator</code>
 * and override the <code>children(Object)</code> method)
 * </ul>
 * <p>
 */
public class TreeIterator<E>
	implements Iterator<E>
{
	private final LinkedList<Iterator<? extends E>> iterators;
	private final Midwife<E> midwife;
	private Iterator<? extends E> currentIterator;


	/**
	 * Construct an iterator with the specified collection of roots
	 * and a disabled midwife.
	 * Use this constructor if you want to override the
	 * <code>children(Object)</code> method instead of building
	 * a <code>Midwife</code>.
	 */
	public TreeIterator(Iterator<? extends E> roots) {
		this(roots, Midwife.Disabled.<E>instance());
	}

	/**
	 * Construct an iterator with the specified root
	 * and a disabled midwife.
	 * Use this constructor if you want to override the
	 * <code>children(Object)</code> method instead of building
	 * a <code>Midwife</code>.
	 */
	public TreeIterator(E root) {
		this(root, Midwife.Disabled.<E>instance());
	}

	/**
	 * Construct an iterator with the specified root
	 * and midwife.
	 */
	public TreeIterator(E root, Midwife<E> midwife) {
		this(new SingleElementIterator<E>(root), midwife);
	}

	/**
	 * Construct an iterator with the specified roots
	 * and midwife.
	 */
	public TreeIterator(Iterator<? extends E> roots, Midwife<E> midwife) {
		super();
		this.currentIterator = roots;
		// use a LinkedList since we will be pulling off the front and adding to the end
		this.iterators = new LinkedList<Iterator<? extends E>>();
		this.midwife = midwife;
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
		this.iterators.add(this.children(next));
		return next;
	}

	public void remove() {
		this.currentIterator.remove();
	}

	/**
	 * Return the immediate children of the specified object.
	 */
	protected Iterator<? extends E> children(E next) {
		return this.midwife.children(next);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.currentIterator);
	}


	//********** inner classes **********

	/**
	 * Used by <code>TreeIterator</code> to retrieve
	 * the immediate children of a node in the tree.
	 */
	public interface Midwife<T> {

		/**
		 * Return the immediate children of the specified object.
		 */
		Iterator<? extends T> children(T o);


		final class Null<S> implements Midwife<S> {
			@SuppressWarnings("unchecked")
			public static final Midwife INSTANCE = new Null();
			@SuppressWarnings("unchecked")
			public static <R> Midwife<R> instance() {
				return INSTANCE;
			}
			// ensure single instance
			private Null() {
				super();
			}
			// return no neighbors
			public Iterator<S> children(S next) {
				return EmptyIterator.instance();
			}
			@Override
			public String toString() {
				return "TreeIterator.Midwife.Null"; //$NON-NLS-1$
			}
		}

		/** The midwife used when the #children(Object) method is overridden. */
		final class Disabled<S> implements Midwife<S> {
			@SuppressWarnings("unchecked")
			public static final Midwife INSTANCE = new Disabled();
			@SuppressWarnings("unchecked")
			public static <R> Midwife<R> instance() {
				return INSTANCE;
			}
			// ensure single instance
			private Disabled() {
				super();
			}
			// throw an exception
			public Iterator<S> children(S next) {
				throw new UnsupportedOperationException();  // TreeIterator.children(Object) was not implemented
			}
			@Override
			public String toString() {
				return "TreeIterator.Midwife.Disabled"; //$NON-NLS-1$
			}
		}

	}

}
