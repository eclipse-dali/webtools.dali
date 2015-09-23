/*******************************************************************************
 * Copyright (c) 2013, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.deque;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import org.eclipse.jpt.common.utility.deque.InputRestrictedDeque;
import org.eclipse.jpt.common.utility.internal.ArrayTools;

/**
 * Abstract priority implementation of the {@link InputRestrictedDeque} interface.
 * Elements will dequeue from the deque's head in the order determined by a comparator
 * (i.e. {@link #dequeueHead} will return the element sorted first
 * while {@link #dequeueTail} will return the element sorted last).
 * @param <E> the type of elements maintained by the deque
 */
public abstract class AbstractPriorityDeque<E>
	implements InputRestrictedDeque<E>, Cloneable, Serializable
{
	protected final Comparator<? super E> comparator;

	/**
	 * Standard min-max heap implementation.
	 * To simplify our math, we leave the first slot [0] empty.
	 */
	protected transient E[] elements;

	protected int size = 0;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct an empty priority deque with the specified comparator
	 * and initial capacity.
	 */
	@SuppressWarnings("unchecked")
	protected AbstractPriorityDeque(Comparator<? super E> comparator, int initialCapacity) {
		super();
		if (comparator == null) {
			throw new NullPointerException();
		}
		this.comparator = comparator;
		if (initialCapacity < 0) {
			throw new IllegalArgumentException("Illegal capacity: " + initialCapacity); //$NON-NLS-1$
		}
		
		this.elements = (E[]) new Object[initialCapacity + 1];
	}


	// ********** Deque implementation **********

	/**
	 * The element is not enqueued strictly to the deque's tail;
	 * it is placed in its proper position, as determined by the
	 * deque's priority comparator.
	 */
	public void enqueueTail(E element) {
		this.enqueue(element);
	}

	public void enqueue(E element) {
		this.size++;
		int current = this.size;
		this.elements[current] = element;
		int parent = current >> 1;
		if (parent == 0) {
			return;
		}

		int level = 31 - Integer.numberOfLeadingZeros(current); // 'current' is never zero
		if ((level & 1) == 0) { // even levels are min levels (top level is zero)
			if (this.comparator.compare(this.elements[current], this.elements[parent]) > 0) {
				// move to max level before bubbling up
				ArrayTools.swap(this.elements, current, parent);
				current = parent;
				int gp = current >> 2;
				while ((gp != 0) && this.comparator.compare(this.elements[current], this.elements[gp]) > 0) {
					ArrayTools.swap(this.elements, current, gp);
					current = gp;
					gp = current >> 2;
				}
			} else {
				// bubble up min levels
				int gp = current >> 2;
				while ((gp != 0) && this.comparator.compare(this.elements[current], this.elements[gp]) < 0) {
					ArrayTools.swap(this.elements, current, gp);
					current = gp;
					gp = current >> 2;
				}
			}
		} else { // max level
			if (this.comparator.compare(this.elements[current], this.elements[parent]) < 0) {
				// move to min level before bubbling up
				ArrayTools.swap(this.elements, current, parent);
				current = parent;
				int gp = current >> 2;
				while ((gp != 0) && this.comparator.compare(this.elements[current], this.elements[gp]) < 0) {
					ArrayTools.swap(this.elements, current, gp);
					current = gp;
					gp = current >> 2;
				}
			} else {
				// bubble up max levels
				int gp = current >> 2;
				while ((gp != 0) && this.comparator.compare(this.elements[current], this.elements[gp]) > 0) {
					ArrayTools.swap(this.elements, current, gp);
					current = gp;
					gp = current >> 2;
				}
			}
		}
	}

// reduce the redundant code, but add more conditional logic
//	public void enqueue(E element) {
//		this.size++;
//		int current = this.size;
//		this.elements[current] = element;
//		int parent = current >> 1;
//		if (parent == 0) {
//			return;
//		}
//
//		int level = 31 - Integer.numberOfLeadingZeros(current); // 'current' is never zero
//		boolean minLevel = (level & 1) == 0;
//		int first = minLevel ? current : parent;
//		int second = minLevel ? parent : current;
//		if (this.comparator.compare(this.elements[first], this.elements[second]) > 0) {
//			ArrayTools.swap(this.elements, current, parent);
//			minLevel = ! minLevel;
//			current = parent;
//		}
//		int gp = current >> 2;
//		while (gp != 0) {
//			first = minLevel ? current : gp;
//			second = minLevel ? gp : current;
//			if (this.comparator.compare(this.elements[first], this.elements[second]) > 0) {
//				break;
//			}
//			ArrayTools.swap(this.elements, current, gp);
//			current = gp;
//			gp = current >> 2;
//		}
//	}

	/**
	 * Dequeue first/min element.
	 */
	public E dequeueHead() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		}
		E element = this.elements[1];
		if (this.size != 1) {
			// replace root with last node and move it to its new position
			ArrayTools.swap(this.elements, 1, this.size);
			this.trickleDownMin(1, this.size - 1);
		}
		this.elements[this.size] = null; // allow GC to work
		this.size--;
		return element;
	}

	private void trickleDownMin(int index, int newSize) {
		int minChildIndex = index << 1; // left child
		if (minChildIndex > newSize) {
			return; // no children
		}

		E element = this.elements[index];
		E minChild = this.elements[minChildIndex];
		int rightChildIndex = minChildIndex + 1;
		if (rightChildIndex > newSize) {
			// no right child; and, therefore, no possible grandchildren
			if (this.comparator.compare(minChild, element) < 0) {
				ArrayTools.swap(this.elements, minChildIndex, index);
			}
			return;
		}

		E rightChild = this.elements[rightChildIndex];
		if (this.comparator.compare(rightChild, minChild) < 0) {
			// right child exists and is less than left
			minChildIndex = rightChildIndex;
			minChild = rightChild;
		}

		// now find min grandchild
		int minGCIndex = -1;
		E minGC = null;
		int i = index << 2; // leftmost grandchild
		if (i <= newSize) {
			minGCIndex = i;
			minGC = this.elements[i];
			int last = Math.min(i + 3, newSize);
			while (++i <= last) {
				E temp = this.elements[i];
				if (this.comparator.compare(temp, minGC) < 0) {
					minGCIndex = i;
					minGC = temp;
				}
			}
		}

		if ((minGC != null) && (this.comparator.compare(minGC, minChild) < 0)) {
			// min descendant is a grandchild
			if (this.comparator.compare(minGC, element) < 0) {
				ArrayTools.swap(this.elements, minGCIndex, index);
				int parentIndex = minGCIndex >> 1; // 'element' is now at 'minGCIndex'
				if (this.comparator.compare(element, this.elements[parentIndex]) > 0) {
					// move element to max level
					ArrayTools.swap(this.elements, minGCIndex, parentIndex);
				}
				this.trickleDownMin(minGCIndex, newSize); // recurse - still on a min level
			}
		} else {
			// min is a direct child and, therefore, has no children itself (since it would have to be greater than its children)
			if (this.comparator.compare(minChild, element) < 0) {
				ArrayTools.swap(this.elements, minChildIndex, index);
			}
		}
	}

	/**
	 * Dequeue last/max element.
	 */
	public E dequeueTail() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		}
		int index = (this.size == 1) ? 1 : (this.size == 2) ? 2 : (this.comparator.compare(this.elements[2], this.elements[3]) > 0) ? 2 : 3;
		E element = this.elements[index];
		if (this.size != 1) {
			// replace removed element with last node and move it to its new position
			ArrayTools.swap(this.elements, index, this.size);
			this.trickleDownMax(index, this.size - 1);
		}
		this.elements[this.size] = null; // allow GC to work
		this.size--;
		return element;
	}

	private void trickleDownMax(int index, int newSize) {
		int maxChildIndex = index << 1; // left child
		if (maxChildIndex > newSize) {
			return; // no children
		}

		E element = this.elements[index];
		E maxChild = this.elements[maxChildIndex];
		int rightChildIndex = maxChildIndex + 1;
		if (rightChildIndex > newSize) {
			// no right child; and, therefore, no possible grandchildren
			if (this.comparator.compare(maxChild, element) > 0) {
				ArrayTools.swap(this.elements, maxChildIndex, index);
			}
			return;
		}

		E rightChild = this.elements[rightChildIndex];
		if (this.comparator.compare(rightChild, maxChild) > 0) {
			// right child exists and is greater than left
			maxChildIndex = rightChildIndex;
			maxChild = rightChild;
		}

		// now find max grandchild
		int maxGCIndex = -1;
		E maxGC = null;
		int i = index << 2; // leftmost grandchild
		if (i <= newSize) {
			maxGCIndex = i;
			maxGC = this.elements[i];
			int last = Math.min(i + 3, newSize);
			while (++i <= last) {
				E temp = this.elements[i];
				if (this.comparator.compare(temp, maxGC) > 0) {
					maxGCIndex = i;
					maxGC = temp;
				}
			}
		}

		if ((maxGC != null) && (this.comparator.compare(maxGC, maxChild) > 0)) {
			// max descendant is a grandchild
			if (this.comparator.compare(maxGC, element) > 0) {
				ArrayTools.swap(this.elements, maxGCIndex, index);
				int parentIndex = maxGCIndex >> 1; // 'element' is now at 'maxGCIndex'
				if (this.comparator.compare(element, this.elements[parentIndex]) < 0) {
					// move element to min level
					ArrayTools.swap(this.elements, maxGCIndex, parentIndex);
				}
				this.trickleDownMax(maxGCIndex, newSize); // recurse - still on a max level
			}
		} else {
			// max is a direct child and, therefore, has no children itself (since it would have to be less than its children)
			if (this.comparator.compare(maxChild, element) > 0) {
				ArrayTools.swap(this.elements, maxChildIndex, index);
			}
		}
	}

	/**
	 * Return first/min element.
	 */
	public E peekHead() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		}
		return this.elements[1];
	}

	/**
	 * Return last/max element.
	 */
	public E peekTail() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		}
		if (this.size == 1) {
			return this.elements[1];
		}
		E left = this.elements[2];
		if (this.size == 2) {
			return left;
		}
		E right = this.elements[3];
		return (this.comparator.compare(left, right) > 0) ? left : right;
	}

	public boolean isEmpty() {
		return this.size == 0;
	}


	// ********** standard methods **********

	@Override
	public AbstractPriorityDeque<E> clone() {
		try {
			@SuppressWarnings("unchecked")
			AbstractPriorityDeque<E> clone = (AbstractPriorityDeque<E>) super.clone();
			@SuppressWarnings("cast")
			E[] array = (E[]) this.elements.clone();
			clone.elements = array;
			return clone;
		} catch (CloneNotSupportedException ex) {
			throw new InternalError();
		}
	}

	@Override
	public String toString() {
		return Arrays.toString(ArrayTools.subArray(this.elements, 1, this.size + 1));
	}


	// ********** Serializable "implementation" **********

	private void writeObject(java.io.ObjectOutputStream stream) throws java.io.IOException {
		// write comparator and size (and any hidden stuff)
		stream.defaultWriteObject();
		stream.writeInt(this.elements.length);
		if (this.size == 0) {
			return;
		}
		for (int i = 1; i <= this.size; i++) { // skip 0
			stream.writeObject(this.elements[i]);
		}
	}

	@SuppressWarnings("unchecked")
	private void readObject(java.io.ObjectInputStream stream) throws java.io.IOException, ClassNotFoundException {
		// read comparator and size (and any hidden stuff)
		stream.defaultReadObject();
		int elementsLength = stream.readInt();
		Object[] array = new Object[elementsLength];
		for (int i = 1; i <= this.size; i++) { // skip 0
			array[i] = stream.readObject();
		}
		this.elements = (E[]) array;
	}
}
