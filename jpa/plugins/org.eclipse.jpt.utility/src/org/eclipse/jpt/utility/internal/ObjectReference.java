/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
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
 * Provide a container for passing an object that can be changed by the recipient.
 * 
 * @see SynchronizedObject
 */
public class ObjectReference<V>
	implements Cloneable, Serializable
{
	/** Backing value. */
	private volatile V value;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Create an object reference with the specified initial value.
	 */
	public ObjectReference(V value) {
		super();
		this.value = value;
	}

	/**
	 * Create an object reference with an initial value of
	 * <code>null</code>.
	 */
	public ObjectReference() {
		this(null);
	}


	// ********** accessors **********

	/**
	 * Return the current value.
	 */
	public V getValue() {
		return this.value;
	}

	/**
	 * Return whether the current value is equal to the specified value.
	 */
	public boolean valueEquals(V v) {
		return Tools.valuesAreEqual(this.value, v);
	}

	/**
	 * Return whether the current value is not equal to the specified value.
	 */
	public boolean valueNotEqual(V v) {
		return Tools.valuesAreDifferent(this.value, v);
	}

	/**
	 * Return whether the current value is <code>null</code>.
	 */
	public boolean isNull() {
		return this.value == null;
	}

	/**
	 * Return whether the current value is not <code>null</code>.
	 */
	public boolean isNotNull() {
		return this.value != null;
	}

	/**
	 * Set the value.
	 * Return the previous value.
	 */
	public V setValue(V value) {
		V old = this.value;
		this.value = value;
		return old;
	}

	/**
	 * Set the value to <code>null</code>.
	 * Return the previous value.
	 */
	public V setNull() {
		return this.setValue(null);
	}


	// ********** standard methods **********

	@Override
	public ObjectReference<V> clone() {
		try {
			@SuppressWarnings("unchecked")
			ObjectReference<V> clone = (ObjectReference<V>) super.clone();
			return clone;
		} catch (CloneNotSupportedException ex) {
			throw new InternalError();
		}
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof ObjectReference<?>) &&
			Tools.valuesAreEqual(this.value, ((ObjectReference<?>) obj).value);
	}

	@Override
	public int hashCode() {
		return (this.value == null) ? 0 : this.value.hashCode();
	}

	@Override
	public String toString() {
		return '[' + String.valueOf(this.value) + ']';
	}

}
