/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.reference;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.reference.ModifiableObjectReference;

/**
 * Provide a container for passing an object that can be changed by the recipient.
 * 
 * @param <V> the type of the reference's value
 * @see SynchronizedObject
 */
public class SimpleObjectReference<V>
	implements ModifiableObjectReference<V>, Cloneable, Serializable
{
	/** Backing value. */
	private volatile V value;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Create an object reference with the specified initial value.
	 */
	public SimpleObjectReference(V value) {
		super();
		this.value = value;
	}

	/**
	 * Create an object reference with an initial value of
	 * <code>null</code>.
	 */
	public SimpleObjectReference() {
		this(null);
	}


	// ********** value **********

	public V getValue() {
		return this.value;
	}

	public boolean valueEquals(Object object) {
		return ObjectTools.equals(this.value, object);
	}

	public boolean valueNotEqual(Object object) {
		return ObjectTools.notEquals(this.value, object);
	}

	public boolean isNull() {
		return this.value == null;
	}

	public boolean isNotNull() {
		return this.value != null;
	}

	public V setValue(V value) {
		V old = this.value;
		this.value = value;
		return old;
	}

	public V setNull() {
		return this.setValue(null);
	}


	// ********** standard methods **********

	@Override
	public SimpleObjectReference<V> clone() {
		try {
			@SuppressWarnings("unchecked")
			SimpleObjectReference<V> clone = (SimpleObjectReference<V>) super.clone();
			return clone;
		} catch (CloneNotSupportedException ex) {
			throw new InternalError();
		}
	}

	@Override
	public String toString() {
		return '[' + String.valueOf(this.value) + ']';
	}
}
