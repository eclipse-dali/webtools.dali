/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.predicate;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * Singleton predicate that always evaluates to
 * <code>true</code>.
 */
public final class TruePredicate<V>
	implements Predicate<V>, Serializable
{
	@SuppressWarnings("rawtypes")
	public static final Predicate INSTANCE = new TruePredicate();

	@SuppressWarnings("unchecked")
	public static <V> Predicate<V> instance() {
		return INSTANCE;
	}

	// ensure single instance
	private TruePredicate() {
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
		return ObjectTools.singletonToString(this);
	}

	private static final long serialVersionUID = 1L;
	private Object readResolve() {
		// replace this object with the singleton
		return INSTANCE;
	}
}
