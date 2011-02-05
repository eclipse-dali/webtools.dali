/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

/**
 * Implement some of the methods in {@link Association} that can
 * be defined in terms of the other methods.
 */
public abstract class AbstractAssociation<K, V>
	implements Association<K, V>
{
	/**
	 * Default constructor.
	 */
	protected AbstractAssociation() {
		super();
	}

	@Override
	public synchronized boolean equals(Object o) {
		if ( ! (o instanceof Association<?, ?>)) {
			return false;
		}
		Association<?, ?> other = (Association<?, ?>) o;
		return this.keyEquals(other) && this.valueEquals(other);
	}

	protected boolean keyEquals(Association<?, ?> other) {
		Object key = this.getKey();
		return (key == null) ?
				(other.getKey() == null) :
				key.equals(other.getKey());
	}

	protected boolean valueEquals(Association<?, ?> other) {
		Object value = this.getValue();
		return (value == null) ?
				(other.getValue() == null) :
				value.equals(other.getValue());
	}

	@Override
	public synchronized int hashCode() {
		return this.keyHashCode() ^ this.valueHashCode();
	}

	protected int keyHashCode() {
		Object key = this.getKey();
		return (key == null) ? 0 : key.hashCode();
	}

	protected int valueHashCode() {
		Object value = this.getValue();
		return (value == null) ? 0 : value.hashCode();
	}

	@Override
	public synchronized String toString() {
		return this.getKey() + " => " + this.getValue(); //$NON-NLS-1$
	}

}
