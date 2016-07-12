/*******************************************************************************
 * Copyright (c) 2005, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.predicate;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * Singleton predicate that evaluates whether an object is
 * <em>not</em> <code>null</code>.
 * 
 * @see Equals
 * @see IsIdentical
 * @see IsNull
 */
public final class IsNotNull
	implements Predicate<Object>, Serializable
{
	public static final Predicate<Object> INSTANCE = new IsNotNull();

	@SuppressWarnings("unchecked")
	public static <V> Predicate<V> instance() {
		return (Predicate<V>) INSTANCE;
	}

	// ensure single instance
	private IsNotNull() {
		super();
	}

	/**
	 * Return whether the variable is <em>not</em> <code>null</code>.
	 */
	public boolean evaluate(Object variable) {
		return variable != null;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

	private static final long serialVersionUID = 1L;
	private Object readResolve() {
		// replace this object with the singleton
		return INSTANCE;
	}
}
