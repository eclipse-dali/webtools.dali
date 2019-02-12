/*******************************************************************************
 * Copyright (c) 2010, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.reference;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.factory.Factory;

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
public final class LazyObjectReference<V>
	extends AbstractObjectReference<V>
	implements Cloneable, Serializable
{
	private volatile V value = null;

	private final Factory<V> factory;

	private static final long serialVersionUID = 1L;


	/**
	 * Create a lazy object reference.
	 */
	public LazyObjectReference(Factory<V> factory) {
		super();
		this.factory = factory;
	}

	/*
	 * In JDK 5 and later, this "double-checked locking" idiom works as long
	 * as the instance variable is marked <code>volatile</code>.
	 */
	public V getValue() {
		V result = this.value;
		if (result == null) {
			synchronized (this) {
				result = this.value;
				if (result == null) {
					result = this.value = this.factory.create();
				}
			}
		}
		return result;
	}

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
}
