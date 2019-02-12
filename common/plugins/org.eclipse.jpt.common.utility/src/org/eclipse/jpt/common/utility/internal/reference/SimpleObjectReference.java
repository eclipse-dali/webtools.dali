/*******************************************************************************
 * Copyright (c) 2009, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.reference;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;
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


	// ********** ObjectReference **********

	public V getValue() {
		return this.value;
	}

	public boolean valueEquals(Object object) {
		return ObjectTools.equals(this.value, object);
	}

	public boolean valueNotEqual(Object object) {
		return ObjectTools.notEquals(this.value, object);
	}

	public boolean is(Object object) {
		return this.value == object;
	}

	public boolean isNot(Object object) {
		return this.value != object;
	}

	public boolean isNull() {
		return this.value == null;
	}

	public boolean isNotNull() {
		return this.value != null;
	}

	public boolean isMemberOf(Predicate<? super V> predicate) {
		return predicate.evaluate(this.value);
	}

	public boolean isNotMemberOf(Predicate<? super V> predicate) {
		return ! predicate.evaluate(this.value);
	}


	// ********** ModifiableObjectReference **********

	public V setValue(V value) {
		V old = this.value;
		this.value = value;
		return old;
	}

	public V setNull() {
		return this.setValue(null);
	}

	public boolean commit(V newValue, V expectedValue) {
		if (ObjectTools.equals(this.value, expectedValue)) {
			this.setValue(newValue);
			return true;
		}
		return false;
	}

	public V swap(ModifiableObjectReference<V> other) {
		if (other == this) {
			return this.value;
		}
		V otherValue = other.getValue();
		if (ObjectTools.equals(this.value, otherValue)) {
			return this.value;
		}
		other.setValue(this.value);
		this.setValue(otherValue);
		return otherValue;
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

	/**
	 * Object identity is critical to object references.
	 * There is no reason for two different object references to be
	 * <em>equal</em>.
	 * 
	 * @see #valueEquals(Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	/**
	 * @see #equals(Object)
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		return '[' + String.valueOf(this.value) + ']';
	}
}
