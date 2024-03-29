/*******************************************************************************
 * Copyright (c) 2005, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.predicate.int_;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.predicate.IntPredicate;

/**
 * Singleton predicate that throws an 
 * {@link UnsupportedOperationException exception} if evaluated.
 */
public final class DisabledIntPredicate
	implements IntPredicate, Serializable
{
	public static final IntPredicate INSTANCE = new DisabledIntPredicate();

	public static IntPredicate instance() {
		return INSTANCE;
	}

	// ensure single instance
	private DisabledIntPredicate() {
		super();
	}

	/**
	 * Throw an {@link UnsupportedOperationException exception}.
	 */
	public boolean evaluate(int variable) {
		throw new UnsupportedOperationException();
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
