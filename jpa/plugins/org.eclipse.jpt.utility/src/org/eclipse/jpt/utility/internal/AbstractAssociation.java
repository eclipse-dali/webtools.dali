/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

/**
 * Implement some of the methods in Association that can
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
		if ( ! (o instanceof Association)) {
			return false;
		}
		Association<?, ?> other = (Association<?, ?>) o;
		return (this.key() == null ?
					other.key() == null : this.key().equals(other.key()))
			&& (this.value() == null ?
					other.value() == null : this.value().equals(other.value()));
	}

	@Override
	public synchronized int hashCode() {
		return (this.key() == null ? 0 : this.key().hashCode())
			^ (this.value() == null ? 0 : this.value().hashCode());
	}

	@Override
	public synchronized String toString() {
		return this.key() + " => " + this.value(); //$NON-NLS-1$
	}

}
