/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.reference.ObjectReference;

/**
 * Provide a thread-safe, reasonably performing container for holding an
 * object that will be "lazy-initialized" upon its first reference. This is
 * also useful for preventing direct references (accidental or otherwise) to
 * lazy-initialized state.
 * <p>
 * There are some penalties:<ul>
 * <li>The reference's use of generics will require casting (and the requisite
 *     VM testing) with every access
 * <li>If the value calculated during lazy initialization is <code>null</code>,
 *     access will be <code>synchronized</code> <em>every</em> time.
 * </ul>
 * @param <V> the type of the reference's value
 * @see SimpleObjectReference
 * @see SynchronizedObject
 */
public abstract class LazyObjectReference<V>
	implements ObjectReference<V>, Cloneable, Serializable
{
	/** Backing value. */
	private volatile V value = null;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Create a lazy object reference.
	 */
	protected LazyObjectReference() {
		super();
	}


	// ********** value **********

	/**
	 * In JDK 5 and later, this "double-checked locking" idiom works as long
	 * as the instance variable is marked <code>volatile</code>.
	 */
	public V getValue() {
		V result = this.value;
		if (result == null) {
			synchronized (this) {
				result = this.value;
				if (result == null) {
					this.value = result = this.buildValue();
				}
			}
		}
		return result;
	}

	protected abstract V buildValue();

	public boolean valueEquals(Object object) {
		return ObjectTools.equals(this.getValue(), object);
	}

	public boolean valueNotEqual(Object object) {
		return ObjectTools.notEquals(this.getValue(), object);
	}

	public boolean isNull() {
		return this.getValue() == null;
	}

	public boolean isNotNull() {
		return this.getValue() != null;
	}


	// ********** standard methods **********

	@Override
	public LazyObjectReference<V> clone() {
		try {
			@SuppressWarnings("unchecked")
			LazyObjectReference<V> clone = (LazyObjectReference<V>) super.clone();
			return clone;
		} catch (CloneNotSupportedException ex) {
			throw new InternalError();
		}
	}

	/**
	 * This method will <em>not</em> trigger the "lazy-initialization".
	 */
	@Override
	public String toString() {
		return '[' + String.valueOf(this.value) + ']';
	}
}
