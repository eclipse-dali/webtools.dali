/*******************************************************************************
 * Copyright (c) 2012, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.closure;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.closure.BiClosure;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Closure that will throw an
 * {@link UnsupportedOperationException exception} when executed.
 * 
 * @param <A1> the type of the first object passed to the closure
 * @param <A2> the type of the second object passed to the closure
 */
public final class DisabledBiClosure<A1, A2>
	implements BiClosure<A1, A2>, Serializable
{
	@SuppressWarnings("rawtypes")
	public static final BiClosure INSTANCE = new DisabledBiClosure();

	@SuppressWarnings("unchecked")
	public static <A1, A2> BiClosure<A1, A2> instance() {
		return INSTANCE;
	}

	// ensure single instance
	private DisabledBiClosure() {
		super();
	}

	// throw an exception
	public void execute(A1 argument1, A2 argument2) {
		throw new UnsupportedOperationException();
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
