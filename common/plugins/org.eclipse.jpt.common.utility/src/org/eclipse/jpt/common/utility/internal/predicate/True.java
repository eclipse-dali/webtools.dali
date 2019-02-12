/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.predicate;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * Singleton predicate that always evaluates to
 * <code>true</code>.
 * 
 * @param <V> the type of objects to be evaluated by the predicate
 * @see False
 */
public final class True<V>
	implements Predicate<V>, Serializable
{
	@SuppressWarnings("rawtypes")
	public static final Predicate INSTANCE = new True();

	@SuppressWarnings("unchecked")
	public static <V> Predicate<V> instance() {
		return INSTANCE;
	}

	// ensure single instance
	private True() {
		super();
	}

	/**
	 * Return <code>true</code>.
	 */
	public boolean evaluate(V variable) {
		return true;
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
