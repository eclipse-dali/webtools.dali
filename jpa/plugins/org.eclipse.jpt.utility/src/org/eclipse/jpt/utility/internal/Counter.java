/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

import java.io.Serializable;

/**
 * This class can be used wherever a mutable integer object is needed.
 * It is a cross between an int and an Integer. It can be stored in a standard
 * container (e.g. Collection) but can be modified.
 */
public final class Counter
	implements Cloneable, Serializable
{
	private int count = 0;

	private static final long serialVersionUID = 1L;


	/**
	 * Construct a counter with the specified initial value.
	 */
	public Counter(int count) {
		super();
		this.count = count;
	}

	/**
	 * Construct a counter with an initial value of zero.
	 */
	public Counter() {
		this(0);
	}

	/**
	 * Return the current count of the counter.
	 */
	public synchronized int count() {
		return this.count;
	}

	/**
	 * Increment and return the current count of the counter.
	 */
	public synchronized int increment() {
		return ++this.count;
	}

	/**
	 * Increment and return the current count of the counter.
	 */
	public synchronized int increment(int increment) {
		return this.count += increment;
	}

	/**
	 * Derement and return the current count of the counter.
	 */
	public synchronized int decrement() {
		return --this.count;
	}

	/**
	 * Derement and return the current count of the counter.
	 */
	public synchronized int decrement(int decrement) {
		return this.count -= decrement;
	}

	@Override
	public synchronized boolean equals(Object o) {
		if ( ! (o instanceof Counter)) {
			return false;
		}
		return this.count == ((Counter) o).count();
	}

	@Override
	public synchronized int hashCode() {
		return this.count;
	}

	@Override
	public synchronized Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new InternalError();
		}
	}

	@Override
	public synchronized String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(ClassTools.shortClassNameForObject(this));
		sb.append('(');
		sb.append(this.count);
		sb.append(')');
		return sb.toString();
	}

}
