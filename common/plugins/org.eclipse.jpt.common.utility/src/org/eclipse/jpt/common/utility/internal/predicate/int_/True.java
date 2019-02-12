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
 * Singleton predicate that always evaluates to
 * <code>true</code>.
 * 
 * @see False
 */
public final class True
	implements IntPredicate, Serializable
{
	public static final IntPredicate INSTANCE = new True();

	public static  IntPredicate instance() {
		return INSTANCE;
	}

	// ensure single instance
	private True() {
		super();
	}

	/**
	 * Return <code>true</code>.
	 */
	public boolean evaluate(int variable) {
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
