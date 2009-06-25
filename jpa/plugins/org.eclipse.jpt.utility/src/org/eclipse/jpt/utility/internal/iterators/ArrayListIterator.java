/*******************************************************************************
 * Copyright (c) 2005, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.iterators;

import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * An <code>ArrayListIterator</code> provides a <code>ListIterator</code>
 * for an array of objects.
 * 
 * The name might be a bit confusing:
 * This is a <code>ListIterator</code> for an <code>Array</code>;
 * <em>not</em> an <code>Iterator</code> for an <code>ArrayList</code>.
 */
public class ArrayListIterator<E>
	extends ArrayIterator<E>
	implements ListIterator<E>
{
	private final int min;


	/**
	 * Construct a list iterator for the specified array.
	 */
	public ArrayListIterator(E... array) {
		this(array, 0, array.length);
	}
	
	/**
	 * Construct a list iterator for the specified array,
	 * starting at the specified start index and continuing for
	 * the specified length.
	 */
	public ArrayListIterator(E[] array, int start, int length) {
		super(array, start, length);
		this.min = start;
	}
	
	public int nextIndex() {
		return this.cursor;
	}
	
	public int previousIndex() {
		return this.cursor - 1;
	}
	
	public boolean hasPrevious() {
		return this.cursor != this.min;
	}
	
	public E previous() {
		if (this.hasPrevious()) {
			return this.array[--this.cursor];
		}
		throw new NoSuchElementException();
	}
	
	public void add(E e) {
		throw new UnsupportedOperationException();
	}
	
	public void set(E e) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	
}
